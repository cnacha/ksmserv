package com.mfu.entity.survey;

import java.util.List;

public class Result {
	private String formname;
	private String name;
	private List<FilledItem> answer;
	private List<Choice> choice;
	private List<Integer> peopleanswer;
	
	public List<FilledItem> getAnswer() {
		return answer;
	}
	public void setAnswer(List<FilledItem> answer) {
		this.answer = answer;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Choice> getChoice() {
		return choice;
	}
	public String getFormname() {
		return formname;
	}
	public void setFormname(String formname) {
		this.formname = formname;
	}
	public void setChoice(List<Choice> choice) {
		this.choice = choice;
	}
	public List<Integer> getPeopleanswer() {
		return peopleanswer;
	}
	public void setPeopleanswer(List<Integer> peopleanswer) {
		this.peopleanswer = peopleanswer;
	}
	
	

}
