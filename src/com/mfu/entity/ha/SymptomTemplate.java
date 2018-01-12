package com.mfu.entity.ha;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class SymptomTemplate implements Serializable {
	
	public static int NORMAL_GROUP_LEVEL = 0; 
	public static int RISK_GROUP_LEVEL = 1; 
	public static int DISEASE_GROUP_LEVEL = 2; 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	private int symptomLevel;
	

	private String symptomName;
//	private String dataForXaxis;
//	private String dataForYaxis;
	public int getSymptomLevel() {
		return symptomLevel;
	}
	public void setSymptomLevel(int symptomLevel) {
		this.symptomLevel = symptomLevel;
	}
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
	public String getsymptomName() {
		return symptomName;
	}
	public void setsymptomName(String symptomName) {
		this.symptomName = symptomName;
	}
//	public String getDataForXaxis() {
//		return dataForXaxis;
//	}
//	public void setDataForXaxis(String dataForXaxis) {
//		this.dataForXaxis = dataForXaxis;
//	}
//	public String getDataForYaxis() {
//		return dataForYaxis;
//	}
//	public void setDataForYaxis(String dataForYaxis) {
//		this.dataForYaxis = dataForYaxis;
//	}
}