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
public class ImpactFactor implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	
	private String symtomID;
	private String impactName;
	private String impactLabel;
//	private String Xaxis;
//	private String Yaxis;
	
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
	public String getimpactName() {
		return impactName;
	}
	public void setImpactName(String impactName) {
		this.impactName = impactName;
	}
	public String getImpactLabel() {
		return impactLabel;
	}
	public void setImpactLabel(String impactLabel) {
		this.impactLabel = impactLabel;
	}
	
//	public String getXaxis() {
//		return Xaxis;
//	}
//	public void setXaxis(String xaxis) {
//		Xaxis = xaxis;
//	}
//	public String getYaxis() {
//		return Yaxis;
//	}
//	public void setYaxis(String yaxis) {
//		Yaxis = yaxis;
//	}
}