package com.mehana.smschat.object;

import java.io.Serializable;

public class SMSMessage  implements Serializable {
	private static final long serialVersionUID = -9033201979515331940L;
	
	private Long id;
	private String status;
	private String modem3gMsisdn;
	private String mobileMsisdn;
	private String text;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getModem3gMsisdn() {
		return modem3gMsisdn;
	}
	public void setModem3gMsisdn(String modem3gMsisdn) {
		this.modem3gMsisdn = modem3gMsisdn;
	}
	public String getMobileMsisdn() {
		return mobileMsisdn;
	}
	public void setMobileMsisdn(String mobileMsisdn) {
		this.mobileMsisdn = mobileMsisdn;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
	
}
