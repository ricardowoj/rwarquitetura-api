package com.rwarquitetura.api.enums;

public enum TipoCaracteristica {
 
	DESIGNER_INTERIORES(1, "Designer de Interiores"), 
	ARQUITETONICO(2, "Arquitetônico");
	
	private Integer id;
	private String descr;

	private TipoCaracteristica() {
	}

	private TipoCaracteristica(Integer id, String descr) {
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
	
	public static TipoCaracteristica valueOf(Integer id) {
		for (TipoCaracteristica value : TipoCaracteristica.values()) {
			if (value.getId() == id) {
				return value;
			}
		}
		throw new IllegalArgumentException("Inválido Id TipoCaracteristica");
	}
}
