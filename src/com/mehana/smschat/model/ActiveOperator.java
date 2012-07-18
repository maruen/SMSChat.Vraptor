package com.mehana.smschat.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;


/**
 * @author maruen
 * email: maruen@gmail.com
 * 
 * The persistent class for the ActiveOperator database table.
 * 
 */
@Entity
public class ActiveOperator extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Column
	private String username;
	
	@Column
	private String connector;
	
	@Column
	private Date timeStarted;
	
	@Column
	private String servername;
	
	
	public ActiveOperator () {}
	
	public ActiveOperator(String username, String connector, Date timeStarted, String servername) {
		super();
		this.username 	 = username;
		this.connector	 = connector;
		this.timeStarted = timeStarted;
		this.servername  = servername;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getConnector() {
		return connector;
	}

	public void setConnector(String connector) {
		this.connector = connector;
	}

	public Date getTimeStarted() {
		return timeStarted;
	}

	public void setTimeStarted(Date timeStarted) {
		this.timeStarted = timeStarted;
	}

	public String getServername() {
		return servername;
	}

	public void setServername(String servername) {
		this.servername = servername;
	}
	
}