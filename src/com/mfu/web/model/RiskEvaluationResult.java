package com.mfu.web.model;

import java.util.HashMap;

public class RiskEvaluationResult {
	
	private static int RESULT_POSITIVE = 1;
	private static int RESULT_NEGATIVE = 0;
	
	private int result;
	private HashMap<String, Double> measureValues;
	
	private double distanceToGood;
	private double distanceToBad;
	
	
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public HashMap<String, Double> getMeasureValues() {
		return measureValues;
	}
	public void setMeasureValues(HashMap<String, Double> measureValues) {
		this.measureValues = measureValues;
	}
	
	public void addMeasureValue(String factorName, Double value){
		if(measureValues == null)
			measureValues = new HashMap<String, Double>();
		
		measureValues.put(factorName, value);
	}
	public double getDistanceToGood() {
		return distanceToGood;
	}
	public void setDistanceToGood(double distanceToGood) {
		this.distanceToGood = distanceToGood;
	}
	public double getDistanceToBad() {
		return distanceToBad;
	}
	public void setDistanceToBad(double distanceToBad) {
		this.distanceToBad = distanceToBad;
	}
	
	
}
