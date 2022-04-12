package com.rwarquitetura.api.enums;

public enum TipoStatus {

	ATIVO(1, "Ativo"), 
	BLOQUEADO(2, "Bloqueado");

	private Integer id;
	private String descr;

	private TipoStatus() {
	}

	private TipoStatus(Integer id, String descr) {
		this.id = id;
		this.descr = descr;
	}

	public Integer getId() {
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

	public static TipoStatus valueOf(Integer id) {
		for (TipoStatus value : TipoStatus.values()) {
			if (value.getId() == id) {
				return value;
			}
		}
		throw new IllegalArgumentException("Inválido Id TipoStatus");
	}


}
