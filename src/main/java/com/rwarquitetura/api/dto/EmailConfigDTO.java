package com.rwarquitetura.api.dto;

public class EmailConfigDTO {

	private Integer idUsuario;

	private String email;

	private String senha;

	private Integer tipoServidorEmail;

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Integer getTipoServidorEmail() {
		return tipoServidorEmail;
	}

	public void setTipoServidorEmail(Integer tipoServidorEmail) {
		this.tipoServidorEmail = tipoServidorEmail;
	}

}
