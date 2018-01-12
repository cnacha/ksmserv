package com.mfu.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class DateTimeDeserializer extends JsonDeserializer<Date> {
	
	private SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	@Override
	public Date deserialize(JsonParser arg, DeserializationContext arg1)
			throws IOException, JsonProcessingException {
		String dateText = arg.getText();
		Date date = null;
		try {
			 date = fm.parse(dateText);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
		
	}

}
