package com.mfu.entity.ha;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;




import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gson.Gson;
import com.mfu.util.HashMapSerializer;


@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSymptom implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Key key;
	
	private String patientID;
	
	private String symptomKey;
	private String symptomName;
	private int symptomlevel;
	private double probability;
	private double probablityVariant;
	
	
	private String testInformation;
	
	
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
	public String getPatientID() {
		return patientID;
	}
	public void setPatientID(String patientID) {
		this.patientID = patientID;
	}
	public String getSymptomName() {
		return symptomName;
	}
	public void setSymptomName(String symptomName) {
		this.symptomName = symptomName;
	}
	public int getSymptomlevel() {
		return symptomlevel;
	}
	public void setSymptomlevel(int symptomlevel) {
		this.symptomlevel = symptomlevel;
	}
	public String getSymptomKey() {
		return symptomKey;
	}
	public void setSymptomKey(String symptomKey) {
		this.symptomKey = symptomKey;
	}
	public String getTestInformation() {
		return testInformation;
	}
	public void setTestInformation(String testInformation) {
		this.testInformation = testInformation;
	}
	public double getProbability() {
		return probability;
	}
	public void setProbability(double probability) {
		this.probability = probability;
	}
	public double getProbablityVariant() {
		return probablityVariant;
	}
	public void setProbablityVariant(double probablityVariant) {
		this.probablityVariant = probablityVariant;
	}
	
	

	
}
