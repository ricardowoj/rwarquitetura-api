package com.rwarquitetura.api.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "tab_usuario", uniqueConstraints = { @UniqueConstraint(columnNames = "email") })
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Email
	@Column(name = "email")
	private String email;

	@Column(name = "senha")
	private String senha;

	@Column(name = "id_tipo_status")
	private Integer tipoStatus;

	@Column(name = "id_tipo_usuario")
	private Integer tipoUsuario;

	@ManyToMany
	@JoinTable(
		name = "tab_usuario_permissao", 
		joinColumns = @JoinColumn(name = "id_usuario"), 
		inverseJoinColumns = @JoinColumn(name = "id_permissao"))
	private List<Permissao> permissoes = new ArrayList<Permissao>();

	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	@Column(name = "dh_cadastro")
	private LocalDateTime dhCadastro;

	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	@Column(name = "dh_ultimo_acesso")
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
	
	public void removePermissao(Permissao permissao) {
        this.permissoes.remove(permissao);
        permissao.getUsuarios().remove(this);
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
