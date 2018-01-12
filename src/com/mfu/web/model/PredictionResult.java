package com.mfu.web.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public class PredictionResult {

	/*@Key("classes")
	private double classes;
	*/
	@Key
	private double[] probabilities;
/**
	public double getClasses() {
		return classes;
	}

	public void setClasses(double classes) {
		this.classes = classes;
	}
**/
	public double[] getProbabilities() {
		return probabilities;
	}

	public void setProbabilities(double[] probabilities) {
		this.probabilities = probabilities;
	}

	public double getTargetResult(){
		double mindis = Double.MAX_VALUE;
		double tmpdis;
		double targetclass = 0;
		
		for(int i=0; i< probabilities.length; i++){
			tmpdis = Math.abs(1.0 - probabilities[i]);
			if(tmpdis < mindis){
				mindis = tmpdis;
				targetclass = i;
			}
			
		}
		return targetclass;
	}
	
	
	
	
}
