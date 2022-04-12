package com.rwarquitetura.api.enums;

public enum TipoRemetente {

	ADMINISTRADOR(1, "Administrador"), 
	ARQUITETO(2, "Arquiteto"), 
	CLIENTE_SECUNDARIO(3, "Cliente Secundário");

	private Integer id;
	private String descr;

	private TipoRemetente() {
	}

	private TipoRemetente(Integer id, String descr) {
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
	
	public static TipoRemetente valueOf(Integer id) {
		for (TipoRemetente value : TipoRemetente.values()) {
			if (value.getId() == id) {
				return value;
			}
		}
		throw new IllegalArgumentException("Inválido Id TipoRemetente");
	}

}
