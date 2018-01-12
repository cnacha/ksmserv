package com.mfu.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;

import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import com.mfu.dao.appointment.AppointmentDAO;
import com.mfu.entity.appointment.Appointment;
import com.mfu.entity.record.Doctor;
import com.mfu.entity.record.Patient;

public class CalendarAPI {

	private com.google.api.services.calendar.Calendar client;
	private GoogleCredential credentials;
	private final String APPLOCATION_NAME = "KSMSERV";
	private final String ACCOUNT_ID = "calendarserv@productserv-158207.iam.gserviceaccount.com";

	public CalendarAPI() throws Exception {
		credentials = new GoogleCredential.Builder().setTransport(GoogleNetHttpTransport.newTrustedTransport())
				.setJsonFactory(new GsonFactory()).setServiceAccountId(ACCOUNT_ID)
				.setServiceAccountScopes(Arrays.asList("https://www.googleapis.com/auth/calendar"))

				// Locate credentials file .p12
				.setServiceAccountPrivateKeyFromP12File(new File("WEB-INF/productserv-7b60cc9e9c4d.p12")).build();

		client = new com.google.api.services.calendar.Calendar.Builder(GoogleNetHttpTransport.newTrustedTransport(),
				new GsonFactory(), credentials).setApplicationName(APPLOCATION_NAME).build();
	}

	public boolean insertEventOnCalendar(Doctor doctor, Appointment appointment) {

		try {
			Calendar entry = new Calendar();
			entry.setSummary("Kasemrad Sriburin Hospital");
			CalendarList feed = client.calendarList().list().execute();

			Calendar calendar = client.calendars().get(feed.getItems().get(0).getId()).execute();


			boolean isLeaveDay = false;
			if (appointment.getStatus() == Appointment.STATUS_LEAVE) {
				isLeaveDay = true;
			}
			// Insert new event to calendar
			Event event = newEvent(doctor.getEmail(), appointment.getPatient(), appointment.getStartDateTime(),
					appointment.getEndDateTime(), appointment.getInitialSymptom(), isLeaveDay);
			
			Event result = client.events().insert(calendar.getId(), event).execute();

			insertEventIdOnAppointment(appointment, result.getId().toString());
			System.out.println("Sending event  to: " + doctor.getEmail());

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public void insertEventIdOnAppointment(Appointment appointment, String eventId) {
		AppointmentDAO appointmentService = new AppointmentDAO();
		appointment.setEventId(eventId);
		appointmentService.updateAppointment(appointment);
		appointmentService.closeEntityManager();
	}

	public boolean deleteEventOnCalendar(String eventId) throws IOException {

		try {
			Calendar entry = new Calendar();
			entry.setSummary("Kasemrad Sriburin Hospital");

			CalendarList feed = client.calendarList().list().execute();
			Calendar calendar = client.calendars().get(feed.getItems().get(0).getId()).execute();

			CalendarListEntry calEntry = feed.getItems().get(0);
			Events events = client.events().list(calEntry.getId()).execute();

			for (int i = 0; i < events.getItems().size(); i++) {
				Event event = events.getItems().get(i);
				System.out.println("Event i : " + event.getId() + " Event compare " + eventId);
				if (event.getId().equals(eventId)) {
					System.out.println("Deleting event...");
					client.events().delete(calendar.getId(), event.getId()).execute();
				}
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private Event newEvent(String email, Patient patient, Date startDateTime, Date endDateTime, String initialSymptom,
			boolean isLeaveDay) throws IOException {
		final long offsetHoursMilli = 7 * 60L * 60L * 1000L;
		Date startDate = new Date(startDateTime.getTime()-offsetHoursMilli);
		Date endDate = new Date(endDateTime.getTime()-offsetHoursMilli);
		
		DateTime start = new DateTime(startDate);
		DateTime end = new DateTime(endDate);
		System.out.println("start time: "+start.toStringRfc3339());
		System.out.println("end time: "+end.toStringRfc3339());
		Event event = new Event();

		// Check whether the given day is a doctor leave day or not.
		if (isLeaveDay) {
			event.setSummary("Leave Day");
			event.setColorId("11");
		} else {
			event.setSummary("KS HN:" + patient.getFirstname() + " " 
						+patient.getLastname() + " Appointment");
			event.setColorId("3");
			event.setDescription(initialSymptom);
		}
		EventDateTime startTimeEvent = new EventDateTime().setDateTime(start);
		EventDateTime endTimeEvent = new EventDateTime().setDateTime(end);
		event.setStart(startTimeEvent);
		event.setEnd(endTimeEvent );
		System.out.println("start event: "+event.getStart().toPrettyString());
		System.out.println("start event: "+event.getEnd().toPrettyString());
		event.setLocation("Kasemrad Sriburin Hospital");

		EventAttendee ea = new EventAttendee();
		ea.setEmail(email);
		List<EventAttendee> listea = new ArrayList<EventAttendee>();
		listea.add(ea);

		event.setAttendees(listea);

		return event;
	}

	// private void display(CalendarList feed) throws IOException {
	// if (feed.getItems() != null) {
	// for (CalendarListEntry entry : feed.getItems()) {
	// System.out.println();
	// System.out.println("----------------------CalendarList-------------------------");
	// display(entry);
	// }
	// }
	// }
	//
	// private void display(CalendarListEntry entry) throws IOException {
	// System.out.println("ID: " + entry.getId());
	// System.out.println("Summary: " + entry.getSummary());
	//
	// if (entry.getDescription() != null) {
	// System.out.println("Description: " + entry.getDescription());
	// }
	// Events feed = client.events().list(entry.getId()).execute();
	// display(feed);
	// }
	//
	// static void display(Events feed) {
	// if (feed.getItems() != null) {
	// for (Event entry : feed.getItems()) {
	// System.out.println();
	// System.out.println("--------------------Events---------------------------");
	// display(entry);
	// }
	// }
	// }
	//
	// static void display(Event event) {
	// System.out.println("event Id: " + event.getId());
	// System.out.println("event: " + event.getSummary());
	// if (event.getStart() != null) {
	// System.out.println("Start Time: " + event.getStart());
	// }
	// if (event.getEnd() != null) {
	// System.out.println("End Time: " + event.getEnd());
	// }
	// }
}
