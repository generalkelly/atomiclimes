package io.atomiclimes.helper.jackson;

import java.io.IOException;
import java.io.Serializable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AtomicLimesJacksonHelper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ObjectMapper objectMapper = new ObjectMapper();
	private Class<?> classType;

	public AtomicLimesJacksonHelper(Class<?> classType) {
		this.classType = classType;
	}

	public String serialize(Object objectValue) {
		String output = null;
		try {
			output = objectMapper.writeValueAsString(objectValue);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}

	public Object deserialize(String jsonString) {
		Object output = null;
		try {
			output = objectMapper.readValue(jsonString, classType);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}
	
	

}