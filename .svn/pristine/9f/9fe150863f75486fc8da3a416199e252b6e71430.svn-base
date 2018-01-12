package com.mfu.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class DateSerializer extends JsonSerializer<Date> {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public void serialize(Date date, JsonGenerator generator, SerializerProvider serializerProvider)
			throws IOException, JsonProcessingException {

		generator.writeString(dateFormat.format(date));

	}

}
