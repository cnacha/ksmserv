package com.mfu.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.google.gson.Gson;

public class HashMapSerializer extends JsonSerializer<Map> {

	@Override
	public void serialize(Map map, JsonGenerator generator, SerializerProvider serializerProvider)
			throws IOException, JsonProcessingException {
		Gson gson = new Gson(); 
		generator.writeString(gson.toJson(map));
		System.out.println("map..."+map);

	}

}
