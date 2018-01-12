package com.mfu.entity.survey;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Entity 
@JsonIgnoreProperties(ignoreUnknown = true)
public class FilledItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	
	private String filledFormId;
	
	private String questionId;
	
	private String answerString;

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}



	public String getFilledFormId() {
		return filledFormId;
	}

	public void setFilledFormId(String filledFormId) {
		this.filledFormId = filledFormId;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getAnswerString() {
		return answerString;
	}

	public void setAnswerString(String answerString) {
		this.answerString = answerString;
	}
	
	public String getKeyString() {
		return KeyFactory.keyToString(key);
	}

	public void setKeyString(String keyString) {
		key = KeyFactory.stringToKey(keyString);
	}
}
