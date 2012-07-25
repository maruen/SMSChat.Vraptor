package com.mehana.smschat.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author maruen email: maruen@gmail.com
 * 
 *         The persistent class for the MSG_HIST database table.
 * 
 */

@Entity
public class MsgHist extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Column
	private Date dt_sys;

	@Column
	private Long session_id;

	@Column
	private String msisdn;

	@Column
	private String operator;

	@Column
	private String msg_orig;

	@Column
	private String originator;

	@Column
	private String msg_norm;

	@Column
	private Date dt_plat;

	public Date getDt_sys() {
		return dt_sys;
	}

	public void setDt_sys(Date dt_sys) {
		this.dt_sys = dt_sys;
	}

	public Long getSession_id() {
		return session_id;
	}

	public void setSession_id(Long session_id) {
		this.session_id = session_id;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getMsg_orig() {
		return msg_orig;
	}

	public void setMsg_orig(String msg_orig) {
		this.msg_orig = msg_orig;
	}

	public String getOriginator() {
		return originator;
	}

	public void setOriginator(String originator) {
		this.originator = originator;
	}

	public String getMsg_norm() {
		return msg_norm;
	}

	public void setMsg_norm(String msg_norm) {
		this.msg_norm = msg_norm;
	}

	public Date getDt_plat() {
		return dt_plat;
	}

	public void setDt_plat(Date dt_plat) {
		this.dt_plat = dt_plat;
	}

}