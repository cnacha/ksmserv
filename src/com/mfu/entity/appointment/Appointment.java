package com.mfu.entity.appointment;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.mfu.entity.record.Doctor;
import com.mfu.entity.record.Patient;
import com.mfu.util.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Appointment implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;

	public static int STATUS_LEAVE = -1;
	public static int STATUS_PENDING = 0;
	public static int STATUS_APPROVE = 1;
	public static int STATUS_REFUSE = 2;
	public static int STATUS_CANCEL = 3;
	public static int STATUS_CONFIRM = 4;
	public static int STATUS_VISIT = 5;
	
	
	private String preferDate1;
	private String preferTime1;
	private String preferDate2;
	private String preferTime2;
	private String preferDoctor;

	private int preferChoice;

	@JsonDeserialize(using = DateTimeDeserializer.class)
	@JsonSerialize(using = DateTimeSerializer.class)
	private Date startDateTime;

	@JsonDeserialize(using = DateTimeDeserializer.class)
	@JsonSerialize(using = DateTimeSerializer.class)
	private Date endDateTime;

	// private Date date;

	private String specialty;
	private String initialSymptom;
	private int status;

	// Mapping an appointment with particular event on google calendar
	private String eventId;

	@Transient
	private Patient patient;

	@Transient
	private Doctor doctor;

	@Transient
	private String codeHN;

	private String patientId;

	private String doctorId;

	private Date appointmentDateTime;

	public Date getAppointmentDateTime() {
		return appointmentDateTime;
	}

	public void setAppointmentDateTime(Date appointmentDateTime) {
		this.appointmentDateTime = appointmentDateTime;
	}

	public String getPreferDoctor() {
		return preferDoctor;
	}

	public void setPreferDoctor(String preferDoctor) {
		this.preferDoctor = preferDoctor;
	}

	public String getKeyString() {
		return KeyFactory.keyToString(key);
	}

	public void setKeyString(String keyString) {
		key = KeyFactory.stringToKey(keyString);
	}

	public Key getAppointmentId() {
		return key;
	}

	public void setAppointmentId(Key appointmentId) {
		this.key = appointmentId;
	}

	public String getPreferDate1() {
		return preferDate1;
	}

	public void setPreferDate1(String preferDate1) {
		this.preferDate1 = preferDate1;
	}

	public String getPreferTime1() {
		return preferTime1;
	}

	public void setPreferTime1(String preferTime1) {
		this.preferTime1 = preferTime1;
	}

	public String getPreferDate2() {
		return preferDate2;
	}

	public void setPreferDate2(String preferDate2) {
		this.preferDate2 = preferDate2;
	}

	public String getPreferTime2() {
		return preferTime2;
	}

	public void setPreferTime2(String preferTime2) {
		this.preferTime2 = preferTime2;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getInitialSymptom() {
		return initialSymptom;
	}

	public void setInitialSymptom(String initialSymptom) {
		this.initialSymptom = initialSymptom;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getPreferChoice() {
		return preferChoice;
	}

	public void setPreferChoice(int preferChoice) {
		this.preferChoice = preferChoice;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	public long getStartTimeLongValue() {
		if (this.startDateTime != null)
			return this.startDateTime.getTime();
		else
			return 0;
	}

	public String getCodeHN() {
		return codeHN;
	}

	public void setCodeHN(String codeHN) {
		this.codeHN = codeHN;
	}

	public void fetchStartEndDateTime() throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if (this.getPreferChoice() == 1) {
			Date startDateTime = formatter.parse(this.getPreferDate1() + " " + this.getPreferTime1().substring(0, 5));
			this.setStartDateTime(startDateTime);
			Date endDateTime = formatter.parse(this.getPreferDate1() + " " + this.getPreferTime1().substring(8));
			this.setEndDateTime(endDateTime);
		}
		if (this.getPreferChoice() == 2) {
			Date startDateTime = formatter.parse(this.getPreferDate2() + " " + this.getPreferTime2().substring(0, 5));
			this.setStartDateTime(startDateTime);
			Date endDateTime = formatter.parse(this.getPreferDate2() + " " + this.getPreferTime2().substring(8));
			this.setEndDateTime(endDateTime);

		}

	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

}