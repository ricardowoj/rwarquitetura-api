package com.rwarquitetura.api.enums;

public enum TipoServidorEmail {

	GMAIL(1, "Gmail"), 
	HOTMAIL(2, "Hotmail");

	private Integer id;
	private String desc;

	private TipoServidorEmail() {
	}

	private TipoServidorEmail(Integer id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static TipoServidorEmail valueOf(Integer id) {
		for (TipoServidorEmail value : TipoServidorEmail.values()) {
			if (value.getId() == id) {
				return value;
			}
		}
		throw new IllegalArgumentException("Inválido Id TipoServidorEmail");
	}

}
