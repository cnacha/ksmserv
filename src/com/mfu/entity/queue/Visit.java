package com.mfu.entity.queue;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mfu.util.DateTimeDeserializer;
import com.mfu.util.DateTimeDeserializerForVisit;
import com.mfu.util.DateTimeSerializer;
import com.mfu.util.DateTimeSerializerForVisit;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Visit {
	
	@JsonProperty("VisitDate")
	@JsonDeserialize(using = DateTimeDeserializerForVisit.class)
	@JsonSerialize(using = DateTimeSerializerForVisit.class)
	public Date visitDate;
	
	@JsonProperty("VN")
	public String visitNumber;
	
	@JsonProperty("HN")
	public String hospitalNumber;
	
	@JsonProperty("ClinicName")
	public String clinicName;
	
	@JsonProperty("ClinicCode")
	public String clinicCode;
	
	@JsonProperty("DoctorRoom")
	public String doctorRoom;
	
	@JsonProperty("SendToDiagRmsDateTime")
	@JsonDeserialize(using = DateTimeDeserializerForVisit.class)
	@JsonSerialize(using = DateTimeSerializerForVisit.class)
	public Date sendToDiagRmsDateTime;
	
	@JsonProperty("CurrenStage")
	public int currenStage;
	
	@JsonProperty("NurseReleaseDateTime")
	@JsonDeserialize(using = DateTimeDeserializerForVisit.class)
	@JsonSerialize(using = DateTimeSerializerForVisit.class)
	public Date nurseReleaseDateTime;
	
	@JsonProperty("DrugAcknowledge")
	public int drugAcknowledge;
	
	@JsonProperty("DrugReady")
	public int drugReady;
	
	@JsonProperty("DrugDispense")
	public int drugDispense;
	
	@JsonProperty("AlreadySettleBalance")
	public int alreadySettleBalance;
	
	@JsonProperty("NoneedDrugContractPayment")
	public int noneedDrugContractPayment;

}
