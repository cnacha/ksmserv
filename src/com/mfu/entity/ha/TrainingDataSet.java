package com.mfu.entity.ha;

import kmt.Point;
import kmt.Pp;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class TrainingDataSet {

	private Point[] initialPoint;
	private Point[] samplingPoint;
	
	private String initialFilePath;
	private String samplingFilePath;
	
	private Key key;
	private String symptomKey;
	
	public Point[] getInitialPoint() {
		return initialPoint;
	}
	public void setInitialPoint(Point[] initialPoint) {
		this.initialPoint = initialPoint;
	}
	public Point[] getSamplingPoint() {
		return samplingPoint;
	}
	public void setSamplingPoint(Point[] samplingPoint) {
		this.samplingPoint = samplingPoint;
	}
	/*
	private int[] x;
	private int[] y;
	private int[] sampledataX;
	private int[] sampledataY;
	public int[] getX() {
		return x;
	}
	public void setX(int[] x) {
		this.x = x;
	}
	public int[] getY() {
		return y;
	}
	public void setY(int[] y) {
		this.y = y;
	}
	public int[] getSampledataX() {
		return sampledataX;
	}
	public void setSampledataX(int[] sampledataX) {
		this.sampledataX = sampledataX;
	}
	public int[] getSampledataY() {
		return sampledataY;
	}
	public void setSampledataY(int[] sampledataY) {
		this.sampledataY = sampledataY;
	}
	*/
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
	}
	public String getKeyString() {
		return KeyFactory.keyToString(key);
	}
	public void setKeyString(String keyString) {
		key = KeyFactory.stringToKey(keyString);
	}
	public String getSymptomKey() {
		return symptomKey;
	}
	public void setSymptomKey(String symptomKey) {
		this.symptomKey = symptomKey;
	}
	public String getInitialFilePath() {
		return initialFilePath;
	}
	public void setInitialFilePath(String initialFilePath) {
		this.initialFilePath = initialFilePath;
	}
	public String getSamplingFilePath() {
		return samplingFilePath;
	}
	public void setSamplingFilePath(String samplingFilePath) {
		this.samplingFilePath = samplingFilePath;
	}

	
	
}