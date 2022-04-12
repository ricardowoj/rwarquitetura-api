package com.rwarquitetura.api.enums;

public enum TipoDocumento {

	CPF(1, "CPF"), 
	CNPJ(2, "CNPJ");

	private Integer id;
	private String descr;

	private TipoDocumento() {
	}

	private TipoDocumento(Integer id, String descr) {
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

	public static TipoDocumento valueOf(Integer id) {
		for (TipoDocumento value : TipoDocumento.values()) {
			if (value.getId() == id) {
				return value;
			}
		}
		throw new IllegalArgumentException("Inválido Id TipoDocumento");
	}
}
