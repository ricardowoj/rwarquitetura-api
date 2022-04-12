package com.rwarquitetura.api.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.rwarquitetura.api.model.Permissao;

public class UsuarioEditarDTO {

	private Integer id;
	private String email;
	private String senha;
	private Integer tipoStatus;
	private Integer tipoUsuario;
	private List<Permissao> permissoes;
	private LocalDateTime dhCadastro;
	private LocalDateTime dhUltimoAcesso;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getTipoStatus() {
		return tipoStatus;
	}

	public void setTipoStatus(Integer tipoStatus) {
		this.tipoStatus = tipoStatus;
	}

	public Integer getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(Integer tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public List<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}

	public LocalDateTime getDhCadastro() {
		return dhCadastro;
	}

	public void setDhCadastro(LocalDateTime dhCadastro) {
		this.dhCadastro = dhCadastro;
	}

	public LocalDateTime getDhUltimoAcesso() {
		return dhUltimoAcesso;
	}

	public void setDhUltimoAcesso(LocalDateTime dhUltimoAcesso) {
		this.dhUltimoAcesso = dhUltimoAcesso;
	}

}
