package io.atomiclimes.common.helper.maputils;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class MapComparator {

	private MapComparator() {
	}

	public static <K, V> Map<K, V> diffValuesWithCommonKeys(Map<K, V> leftMap, Map<K, V> rightMap) {
		return leftMap.entrySet().stream().filter(
				entry -> rightMap.containsKey(entry.getKey()) && !entry.getValue().equals(rightMap.get(entry.getKey())))
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
	}

	public static <K, V> Map<K, V> leftDiff(Map<K, V> leftMap, Map<K, V> rightMap) {
		return leftMap.entrySet().stream().filter(entry -> !rightMap.containsKey(entry.getKey()))
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
	}

	public static <K, V> Map<K, V> common(Map<K, V> leftMap, Map<K, V> rightMap) {
		return leftMap.entrySet().stream().filter(
				entry -> rightMap.containsKey(entry.getKey()) && entry.getValue().equals(rightMap.get(entry.getKey())))
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
	}

}
