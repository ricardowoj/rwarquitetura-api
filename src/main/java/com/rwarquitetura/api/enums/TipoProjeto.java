package com.rwarquitetura.api.enums;

public enum TipoProjeto {
 
	RESIDENCIAL(1, "Residêncial"), 
	MULTIFAMILIAR(2, "Multifamiliar"), 
	COMERCIAL(3, "Comercial"),
	EDUCACIONAL(4, "Educacional"),
	SAUDE(5, "Saúde"),
	ESTATAL(6, "Estatal");
	
	private Integer id;
	private String descr;

	private TipoProjeto() {
	}

	private TipoProjeto(Integer id, String descr) {
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
	
	public static TipoProjeto valueOf(Integer id) {
		for (TipoProjeto value : TipoProjeto.values()) {
			if (value.getId() == id) {
				return value;
			}
		}
		throw new IllegalArgumentException("Inválido Id TipoProjeto");
	}
}
