package com.rwarquitetura.api.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "tab_email_registro")
public class EmailRegistro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@NotNull(message = "idUsuarioRemetente é obrigatório")
	@Column(name = "id_usuario_remetente")
	private Integer idUsuarioRemetente;

	@Email
	@NotBlank(message = "emailUsuarioRemetente é obrigatório")
	@Column(name = "email_usuario_remetente")
	private String emailUsuarioRemetente;

	@NotNull(message = "idUsuarioDestinatario é obrigatório")
	@Column(name = "id_usuario_destinatario")
	private Integer idUsuarioDestinatario;

	@Email
	@NotBlank(message = "emailUsuarioDestinatario é obrigatório")
	@Column(name = "email_usuario_destinatario")
	private String emailUsuarioDestinatario;

	@NotBlank(message = "tituloEmail é obrigatório")
	@Column(name = "titulo_email")
	private String tituloEmail;

	@NotBlank(message = "textoEmail é obrigatório")
	@Column(name = "texto_email")
	private String textoEmail;

	@NotNull(message = "dhEnvio é obrigatório")
	@Column(name = "dh_envio")
	private LocalDateTime dhEnvio;

	@NotBlank(message = "tipoRemetente é obrigatório")
	@Column(name = "id_tipo_remetente")
	private Integer tipoRemetente;

	@NotNull(message = "tipoDestinatario é obrigatório")
	@Column(name = "id_tipo_destinatario")
	private Integer tipoDestinatario;

	@NotNull(message = "statusEmail é obrigatório")
	@Column(name = "id_status_email")
	private Integer statusEmail;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdUsuarioRemetente() {
		return idUsuarioRemetente;
	}

	public void setIdUsuarioRemetente(Integer idUsuarioRemetente) {
		this.idUsuarioRemetente = idUsuarioRemetente;
	}

	public String getEmailUsuarioRemetente() {
		return emailUsuarioRemetente;
	}

	public void setEmailUsuarioRemetente(String emailUsuarioRemetente) {
		this.emailUsuarioRemetente = emailUsuarioRemetente;
	}

	public Integer getIdUsuarioDestinatario() {
		return idUsuarioDestinatario;
	}

	public void setIdUsuarioDestinatario(Integer idUsuarioDestinatario) {
		this.idUsuarioDestinatario = idUsuarioDestinatario;
	}

	public String getEmailUsuarioDestinatario() {
		return emailUsuarioDestinatario;
	}

	public void setEmailUsuarioDestinatario(String emailUsuarioDestinatario) {
		this.emailUsuarioDestinatario = emailUsuarioDestinatario;
	}

	public String getTituloEmail() {
		return tituloEmail;
	}

	public void setTituloEmail(String tituloEmail) {
		this.tituloEmail = tituloEmail;
	}

	public String getTextoEmail() {
		return textoEmail;
	}

	public void setTextoEmail(String textoEmail) {
		this.textoEmail = textoEmail;
	}

	public LocalDateTime getDhEnvio() {
		return dhEnvio;
	}

	public void setDhEnvio(LocalDateTime dhEnvio) {
		this.dhEnvio = dhEnvio;
	}

	public Integer getTipoRemetente() {
		return tipoRemetente;
	}

	public void setTipoRemetente(Integer tipoRemetente) {
		this.tipoRemetente = tipoRemetente;
	}

	public Integer getTipoDestinatario() {
		return tipoDestinatario;
	}

	public void setTipoDestinatario(Integer tipoDestinatario) {
		this.tipoDestinatario = tipoDestinatario;
	}

	public Integer getStatusEmail() {
		return statusEmail;
	}

	public void setStatusEmail(Integer statusEmail) {
		this.statusEmail = statusEmail;
	}

}