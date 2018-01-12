package com.mfu.entity.survey;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Entity 
@JsonIgnoreProperties(ignoreUnknown = true)
public class FilledForm {
	
	public static int VOUCHER_STATE_USED = 1;
	public static int VOUCHER_STATE_UNUSED = 0;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	
	private String formId;
	private String userId;
	
	private String voucherCode;
	private String voucherDes;
	private int voucherState;
	
	@Transient
	private List<FilledItem> filledItem;
	
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	
	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getKeyString() {
		return KeyFactory.keyToString(key);
	}

	public void setKeyString(String keyString) {
		key = KeyFactory.stringToKey(keyString);
	}

	public List<FilledItem> getFilledItem() {
		return filledItem;
	}

	public void setFilledItem(List<FilledItem> filledItem) {
		this.filledItem = filledItem;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getVoucherCode() {
		return voucherCode;
	}

	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}

	public int getVoucherState() {
		return voucherState;
	}

	public void setVoucherState(int voucherState) {
		this.voucherState = voucherState;
	}

	public String getVoucherDes() {
		return voucherDes;
	}

	public void setVoucherDes(String voucherDes) {
		this.voucherDes = voucherDes;
	}

}