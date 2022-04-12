package com.rwarquitetura.api.enums;

public enum StatusEmail {

	ENVIADO(1, "ENVIADO"), 
	ERRO(2, "ERRO");

	private Integer id;
	private String descr;

	private StatusEmail() {
	}

	private StatusEmail(Integer id, String descr) {
		this.id = id;
		this.descr = descr;
	}

	public int getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public static StatusEmail valueOf(Integer id) {
		for (StatusEmail value : StatusEmail.values()) {
			if (value.getId() == id) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalido Id StatusEmail");
	}
}
