package com.mfu.entity.record;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.mfu.util.DateTimeDeserializer;
import com.mfu.util.DateTimeSerializer;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class LabTestResult {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	
	private String codeHN;
	
	@JsonDeserialize(using = DateTimeDeserializer.class)
	@JsonSerialize(using = DateTimeSerializer.class)
	private Date updateDate;
	
	private String testName;
	private double resultValue;
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public double getResultValue() {
		return resultValue;
	}
	public void setResultValue(double resultValue) {
		this.resultValue = resultValue;
	}
	public String getKeyString() {
		return KeyFactory.keyToString(key);
	}
	public void setKeyString(String keyString) {
		key = KeyFactory.stringToKey(keyString);
	}
	public String getCodeHN() {
		return codeHN;
	}
	public void setCodeHN(String codeHN) {
		this.codeHN = codeHN;
	}
	
}

