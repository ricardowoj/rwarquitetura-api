package com.rwarquitetura.api.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "tab_endereco_projeto")
public class EnderecoProjeto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@NotNull(message = "idClienteSecundario é obrigatório")
	@Column(name = "id_cliente_secundario")
	private Integer idClienteSecundario;

	@NotBlank(message = "cidade é obrigatório")
	@Column(name = "cidade")
	private String cidade;

	@NotBlank(message = "estado é obrigatório")
	@Column(name = "estado")
	private String estado;

	@NotBlank(message = "rua é obrigatório")
	@Column(name = "rua")
	private String rua;

	@NotBlank(message = "numero é obrigatório")
	@Column(name = "numero")
	private String numero;

	@NotBlank(message = "bairro é obrigatório")
	@Column(name = "bairro")
	private String bairro;

	@NotBlank(message = "cep é obrigatório")
	@Column(name = "cep")
	private String cep;

	@Column(name = "complemento")
	private String complemento;

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

	public Integer getIdClienteSecundario() {
		return idClienteSecundario;
	}

	public void setIdClienteSecundario(Integer idClienteSecundario) {
		this.idClienteSecundario = idClienteSecundario;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public LocalDateTime getDhCadastro() {
		return dhCadastro;
	}

	public void setDhCadastro(LocalDateTime dhCadastro) {
		this.dhCadastro = dhCadastro;
	}

}
