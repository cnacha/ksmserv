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
public class ImpacFactorCluster implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	private String ImpactFactorID;
	
	private double CentroidX;
	private double CentroidY;
	private String imName;
	private String symptomName;
	private int level;
	
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
	public String getImpactFactorID() {
		return ImpactFactorID;
	}
	public void setImpactFactorID(String impactFactorID) {
		ImpactFactorID = impactFactorID;
	}
	public double getCentroidX() {
		return CentroidX;
	}
	public void setCentroidX(double centroidX) {
		CentroidX = centroidX;
	}
	public double getCentroidY() {
		return CentroidY;
	}
	public void setCentroidY(double centroidY) {
		CentroidY = centroidY;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getImName() {
		return imName;
	}
	public void setImName(String imName) {
		this.imName = imName;
	}
	public String getSymptomName() {
		return symptomName;
	}
	public void setSymptomName(String symptomName) {
		this.symptomName = symptomName;
	}
}