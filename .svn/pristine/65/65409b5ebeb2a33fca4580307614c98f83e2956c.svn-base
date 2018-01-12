package com.mfu.test;

import java.util.Date;

import org.joda.time.DateTimeComparator;

public class TestDateTime {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Date date1 = new Date();
		date1.setDate(5);
		Date date2 = new Date();

		System.out.println(isMatchWithAppointmentDay(date1, date2));

	}

	private static int isMatchWithAppointmentDay(Date date1, Date date2) {
		return DateTimeComparator.getDateOnlyInstance().compare(date1, date2);
	}

}
