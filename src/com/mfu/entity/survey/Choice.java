package com.mfu.entity.survey;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Entity 
@JsonIgnoreProperties(ignoreUnknown = true)
public class Choice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	private String answerString;
	private int orderNo;
	
	private String questionKey;
/*	
	@ManyToOne
	private Question question;
*/
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
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
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public String getQuestionKey() {
		return questionKey;
	}
	public void setQuestionKey(String questionKey) {
		this.questionKey = questionKey;
	}
	
}
