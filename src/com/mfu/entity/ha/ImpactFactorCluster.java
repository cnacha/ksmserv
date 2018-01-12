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
public class ImpactFactorCluster implements Serializable {
	
	public static int NORMAL_GROUP_LEVEL = 0; 
	public static int RISK_GROUP_LEVEL = 1; 
	public static int DISEASE_GROUP_LEVEL = 2; 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	private String ImpactID;
	
	private int syntomLevel;
	private String cluster;

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
	public String getImpactID() {
		return ImpactID;
	}
	public void setImpactID(String impactID) {
		ImpactID = impactID;
	}
	public int getSyntomLevel() {
		return syntomLevel;
	}
	public void setSyntomLevel(int syntomLevel) {
		this.syntomLevel = syntomLevel;
	}
	public String getCluster() {
		return cluster;
	}
	public void setCluster(String cluster) {
		this.cluster = cluster;
	}
}