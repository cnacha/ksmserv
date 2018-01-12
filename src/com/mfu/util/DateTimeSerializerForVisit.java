package com.mfu.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class DateTimeSerializerForVisit extends JsonSerializer<Date> {

	private SimpleDateFormat fm = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	@Override
	public void serialize(Date d, JsonGenerator gen, SerializerProvider pro)
			throws IOException, JsonProcessingException {

		gen.writeString(fm.format(d));

	}

}
