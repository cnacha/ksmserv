package com.mfu.web.model;

public class WSResponse {
	
	public static int STATUS_SUCCESS = 1;
	public static int STATUS_FAIL = -1;

	private String key;
	private int status;

	
	public WSResponse(String key, int status) {
		super();
		this.key = key;
		this.status = status;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		if(this.status == STATUS_SUCCESS)
			return "Success";
		else if(this.status == STATUS_FAIL)
			return "Fail";
		else
			return "Unknown";
	}
	
}
