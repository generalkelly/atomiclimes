package io.atomiclimes.web.data.provider.connections;

import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDB.Cancellable;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Result;
import org.influxdb.dto.QueryResult.Series;
import org.springframework.util.Assert;

import io.atomiclimes.common.logging.AtomicLimesLogger;
import io.atomiclimes.web.data.provider.configuration.InfluxDBConfig;

public class AtomicLimesInfluxDBConnector implements AtomicLimesConnector<InfluxDB> {

	private static final AtomicLimesLogger LOG = new AtomicLimesLogger(AtomicLimesInfluxDBConnector.class);

	private String influxDbUrl;
	private String influxDbUsername;
	private String influxDbPassword;
	private String influxDbDatabase;

	private String destinationName;
	private ScheduledExecutorService pollingExecutor = Executors.newSingleThreadScheduledExecutor();

	public AtomicLimesInfluxDBConnector(String destinationName) {
		this.destinationName = destinationName;
	}

	@Override
	public InfluxDB getConnection() {
		Assert.notNull(influxDbUrl, "influxDbUrl must not be null!");
		Assert.notNull(influxDbDatabase, "influxDbDatabase must not be null!");
		InfluxDB influxDB = InfluxDBFactory.connect(this.influxDbUrl, this.influxDbUsername, this.influxDbPassword);
		Pong response = influxDB.ping();
		if (response.getVersion().equalsIgnoreCase("unknown")) {
			LOG.error("Error pinging server.");
			return null;
		}
		influxDB.enableBatch(100, 200, TimeUnit.MILLISECONDS);
		influxDB.setRetentionPolicy("autogen");
		influxDB.setDatabase(this.influxDbDatabase);
		return influxDB;
	}

	@Override
	public void configure(Object configuration) {
		Assert.isInstanceOf(InfluxDBConfig.class, configuration);
		InfluxDBConfig config = (InfluxDBConfig) configuration;
		this.influxDbUrl = config.getUrl();
		this.influxDbUsername = config.getUsername();
		this.influxDbPassword = config.getPassword();
		this.influxDbDatabase = config.getDatabase();
	}

	@Override
	public String getSourceName() {
		return null;
	}

	@Override
	public String getDestinationName() {
		return this.destinationName;
	}

	public void start(InfluxDbSubscriber influxDbSubscriber, String agentName, String measurement, String timeWindow) {
		InfluxDB influxDB = this.getConnection();

		String database = "atomiclimes";
		String command = generateTimeWindowQueryCommand(agentName, measurement, timeWindow);

		Query initialQuery = new Query(command, database);
		Consumer<Throwable> onFailure = throwable -> LOG.error("Ooooops" + throwable.getMessage());

		Runnable onComplete = () -> {
			System.out.println("complete");
		};

		LastTimeStampHolder lastTimeStampHolder = new LastTimeStampHolder();

		BiConsumer<Cancellable, QueryResult> onNext = (cancelable, queryResult) -> {
			if (queryResult.getResults() != null) {
				queryResult.getResults().stream().map(Result::getSeries)
						.collect(new ListAccumulatingCollector<Series>()).stream().map(new TimeSeriesValueMapper())
						.forEach(entry -> {
							lastTimeStampHolder.setLastTimeStamp((String) entry.get(entry.size() - 1).get(0));
							influxDbSubscriber.accept(entry);
						});
			}
		};

		influxDB.query(initialQuery, 1000, onNext, onComplete, onFailure);

		Runnable pollCommand = () -> {
			System.out.println("polling");

			String pollQueryString = generatePollQueryCommand(agentName, measurement,
					lastTimeStampHolder.getLastTimeStamp());
			Query pollQuery = new Query(pollQueryString, database);
			influxDB.query(pollQuery, 1000, onNext, onComplete, onFailure);
		};

		this.pollingExecutor.scheduleAtFixedRate(pollCommand, 500, 500, TimeUnit.MILLISECONDS);
	}

	public void stop() {
		this.pollingExecutor.shutdown();
	}

	private String generatePollQueryCommand(String agentName, String measurement, String lastTimeStamp) {
		return "SELECT " + measurement + " FROM \"" + agentName.toUpperCase() + "\" WHERE time > " + "'" + lastTimeStamp
				+ "'" + "";
	}

	private String generateTimeWindowQueryCommand(String agentName, String measurement, String timeWindow) {
		if (timeWindow != null) {
			return "SELECT " + measurement + " FROM \"" + agentName.toUpperCase() + "\" WHERE time >= now() - "
					+ timeWindow + "";
		} else {
			return "SELECT " + measurement + " FROM \"" + agentName.toUpperCase() + "\"";
		}
	}

	private class ListAccumulatingCollector<L> implements Collector<List<L>, List<L>, List<L>> {
		@Override
		public Supplier<List<L>> supplier() {
			return LinkedList::new;
		}

		@Override
		public BiConsumer<List<L>, List<L>> accumulator() {
			return (list, listToAdd) -> {
				if (listToAdd != null) {
					list.addAll(listToAdd);
				}
			};
		}

		@Override
		public BinaryOperator<List<L>> combiner() {
			return (listOne, listTwo) -> listOne;
		}

		@Override
		public Function<List<L>, List<L>> finisher() {
			return Function.identity();
		}

		@Override
		public Set<Characteristics> characteristics() {
			return EnumSet.of(Characteristics.CONCURRENT);
		}
	}

	private class LastTimeStampHolder {
		private String lastTimeStamp;

		public String getLastTimeStamp() {
			return lastTimeStamp;
		}

		public void setLastTimeStamp(String lastTimeStamp) {
			this.lastTimeStamp = lastTimeStamp;
		}

	}

	private class TimeSeriesValueMapper implements Function<Series, List<List<Object>>> {

		final Pattern doublePattern = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?");
		final Pattern booleanPattern = Pattern.compile("(true|false)?");

		@Override
		public List<List<Object>> apply(Series t) {
			return t.getValues().stream().map(entry -> {
				List<Object> result = new LinkedList<>();
				Matcher doubleMatcher = doublePattern.matcher(entry.get(1).toString());
				Matcher booleanMatcher = booleanPattern.matcher(entry.get(1).toString());

				if (doubleMatcher.matches()) {
					result.add(entry.get(0));
					result.add(Double.parseDouble(entry.get(1).toString()));
				} else if (booleanMatcher.matches()) {
					result.add(entry.get(0));
					result.add(Boolean.parseBoolean(entry.get(1).toString()));
				}
				return result;
			}).collect(Collectors.toList());
		}

	}

}
