package com.rwarquitetura.api.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "tab_email_config")
public class EmailConfig {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@NotNull(message = "idUsuario é obrigatório")
	@Column(name = "id_usuario")
	private Integer idUsuario;

	@Email
	@NotBlank(message = "email é obrigatório")
	@Size(max = 50)
	@Column(name = "email")
	private String email;

	@NotBlank(message = "senha é obrigatório")
	@Size(max = 120)
	@Column(name = "senha")
	private String senha;

	@NotNull(message = "tipoServidorEmail é obrigatório")
	@Column(name = "id_tipo_serv_email")
	private Integer tipoServidorEmail;

	@NotNull
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	@Column(name = "dh_cadastro", updatable = false)
	private LocalDateTime dhCadastro;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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
