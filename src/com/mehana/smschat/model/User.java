package com.mehana.smschat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hibernate.validator.Email;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;


/**
 * @author maruen
 * email: maruen@gmail.com
 * 
 * The persistent class for the SECURITY database table.
 * 
 */
@Entity
public class User extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Column(unique = true)
	private String  username;
	
	@Column	
	@NotNull
	@NotEmpty
	private String  password;
	
	@Column
	private String  name;
	
	@Email
	@NotNull
	@NotEmpty
	@Column(unique = true)
	private String  email;
	
	@Column
	private String  rg;
	
	@Column
	private String  cpf;
	
	@Column
	private Integer modem3gMsisdn;
	
	@Column
	private String  modem3gAlias;
	
	@NotNull
	@Column(length = 13)
	@Enumerated(EnumType.STRING)
	private Role role;
	
	
	public Integer getModem3gMsisdn() {
		return modem3gMsisdn;
	}
	public void setModem3gMsisdn(Integer modem3gMsisdn) {
		this.modem3gMsisdn = modem3gMsisdn;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername() {
		return username;
	}
	public void setUsernamen(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getModem3gAlias() {
		return modem3gAlias;
	}
	public void setModem3gAlias(String modem3gAlias) {
		this.modem3gAlias = modem3gAlias;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
		
}