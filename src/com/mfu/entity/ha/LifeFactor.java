package com.mfu.entity.ha;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class LifeFactor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	
	private String symtomID;
	private String factorName;
	private String factorLabel;
	private double targetGoodValue;
	private double targetBadValue;
	
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
	
	public String getSymtomID() {
		return symtomID;
	}
	public void setSymtomID(String symtomID) {
		this.symtomID = symtomID;
	}
	public String getFactorName() {
		return factorName;
	}
	public void setFactorName(String factorName) {
		this.factorName = factorName;
	}
	public double getTargetGoodValue() {
		return targetGoodValue;
	}
	public void setTargetGoodValue(double targetGoodValue) {
		this.targetGoodValue = targetGoodValue;
	}
	public double getTargetBadValue() {
		return targetBadValue;
	}
	public void setTargetBadValue(double targetBadValue) {
		this.targetBadValue = targetBadValue;
	}
	public String getFactorLabel() {
		return factorLabel;
	}
	public void setFactorLabel(String factorLabel) {
		this.factorLabel = factorLabel;
	}
	
}

