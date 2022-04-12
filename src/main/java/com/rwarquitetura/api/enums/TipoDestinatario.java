package com.rwarquitetura.api.enums;

public enum TipoDestinatario {

	ADMINISTRADOR(1, "Administrador"), 
	ARQUITETO(2, "Arquiteto"), 
	CLIENTE_SECUNDARIO(3, "Cliente Secundário");

	private Integer id;
	private String descr;

	private TipoDestinatario() {
	}

	private TipoDestinatario(Integer id, String descr) {
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
	
	public static TipoDestinatario valueOf(Integer id) {
		for (TipoDestinatario value : TipoDestinatario.values()) {
			if (value.getId() == id) {
				return value;
			}
		}
		throw new IllegalArgumentException("Inválido Id TipoRemetente");
	}

}
