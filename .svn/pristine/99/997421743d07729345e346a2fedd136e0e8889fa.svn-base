package com.mfu.entity.queue;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.mfu.util.DateTimeDeserializer;
import com.mfu.util.DateTimeSerializer;

@Entity
public class VisitTransform {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;

	private String visitNumber;
	private String hospitalNumber;

	@JsonDeserialize(using = DateTimeDeserializer.class)
	@JsonSerialize(using = DateTimeSerializer.class)
	private Date visitDate;

	@JsonDeserialize(using = DateTimeDeserializer.class)
	@JsonSerialize(using = DateTimeSerializer.class)
	private Date sendToDiagRmsDateTime;
	private String clinicName;
	private String clinicCode;
	private String clinicMessage;
	private String doctorRoom;

	@JsonDeserialize(using = DateTimeDeserializer.class)
	@JsonSerialize(using = DateTimeSerializer.class)
	private Date nurseReleaseDateTime;
	private int remainingQueue;
	private int estimatedTime;

	@JsonDeserialize(using = DateTimeDeserializer.class)
	@JsonSerialize(using = DateTimeSerializer.class)
	private Date goGomeDateTime;

	@JsonDeserialize(using = DateTimeDeserializer.class)
	@JsonSerialize(using = DateTimeSerializer.class)
	private Date lastUpdate;
	private int currentStage;

	// Data: Show register estimated time depend on period of time on mobile
	// Image: Screening
	public static final int WAITING_SCREENING_STAGE = 1;

	// Data: Show Screening message
	// Image: Waiting visit
	public static final int SCREENING_STAGE = 2;

	// Data: Show queue remaining
	// Image: Visit doctor
	public static final int WAITING_VISIT_DOCTOR_STAGE = 3;

	// Data: Show visiting message
	// Image: Waiting payment
	public static final int VISITING_DCOTOR_STAGE = 4;

	// Data: Show
	// Image: Payment
	public static final int WAITING_PAYMENT_STAGE = 5;

	// Data: Waiting
	// Image: Receive drug
	public static final int WAITING_RECEIVE_DRUG_STAGE = 6;

	// Data: Thank you
	// Image: Home
	public static final int GO_HOME_STAGE = 7;

	public String getKeyString() {
		return KeyFactory.keyToString(key);
	}

	public void setKeyString(String keyString) {
		key = KeyFactory.stringToKey(keyString);
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getVisitNumber() {
		return visitNumber;
	}

	public void setVisitNumber(String visitNumber) {
		this.visitNumber = visitNumber;
	}

	public String getHospitalNumber() {
		return hospitalNumber;
	}

	public void setHospitalNumber(String hospitalNumber) {
		this.hospitalNumber = hospitalNumber;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public Date getSendToDiagRmsDateTime() {
		return sendToDiagRmsDateTime;
	}

	public void setSendToDiagRmsDateTime(Date sendToDiagRmsDateTime) {
		this.sendToDiagRmsDateTime = sendToDiagRmsDateTime;
	}

	public String getClinicName() {
		return clinicName;
	}

	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}

	public String getClinicCode() {
		return clinicCode;
	}

	public void setClinicCode(String clinicCode) {
		this.clinicCode = clinicCode;
	}

	public String getDoctorRoom() {
		return doctorRoom;
	}

	public void setDoctorRoom(String doctorRoom) {
		this.doctorRoom = doctorRoom;
	}

	public Date getNurseReleaseDateTime() {
		return nurseReleaseDateTime;
	}

	public void setNurseReleaseDateTime(Date nurseReleaseDateTime) {
		this.nurseReleaseDateTime = nurseReleaseDateTime;
	}

	public int getRemainingQueue() {
		return remainingQueue;
	}

	public void setRemainingQueue(int remainingQueue) {
		this.remainingQueue = remainingQueue;
	}

	public int getEstimatedTime() {
		return estimatedTime;
	}

	public void setEstimatedTime(int estimatedTime) {
		this.estimatedTime = estimatedTime;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate() {
		this.lastUpdate = new Date();
	}

	public int getCurrentStage() {
		return currentStage;
	}

	public void setCurrentStage(int currentStage) {
		this.currentStage = currentStage;
	}

	public String getClinicMessage() {
		return clinicMessage;
	}

	public void setClinicMessage(String clinicMessage) {
		this.clinicMessage = clinicMessage;
	}

	public Date getGoGomeDateTime() {
		return goGomeDateTime;
	}

	public void setGoGomeDateTime(Date goGomeDateTime) {
		this.goGomeDateTime = goGomeDateTime;
	}

}
