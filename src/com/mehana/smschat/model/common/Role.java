package com.mehana.smschat.model.common;

public enum Role {

	MEMBRO("Membro"),
	MODERADOR("Moderador"),
	ADMINISTRADOR("Administrador");

	private String label;

	private Role(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
