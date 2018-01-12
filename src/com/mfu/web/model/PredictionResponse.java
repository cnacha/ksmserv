package com.mfu.web.model;

import java.util.List;

import com.google.api.client.util.Key;

public class PredictionResponse {

	@Key("predictions")
	private List<PredictionResult> predictions;

	public List<PredictionResult> getPredictions() {
		return predictions;
	}

	public void setPredictions(List<PredictionResult> predictions) {
		this.predictions = predictions;
	}

	
	
	
}
