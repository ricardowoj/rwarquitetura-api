package com.rwarquitetura.api.dto;

import java.math.BigDecimal;

public class ProjetoDTO {
	
	private Integer id;

	private Integer idClienteSecundario;

	private BigDecimal valorProjeto;

	private Integer idTipoProjeto;

	private String cidade;

	private String estado;

	private String rua;

	private String numero;

	private String bairro;

	private String cep;

	private String complemento;

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

	public BigDecimal getValorProjeto() {
		return valorProjeto;
	}

	public void setValorProjeto(BigDecimal valorProjeto) {
		this.valorProjeto = valorProjeto;
	}

	public Integer getIdTipoProjeto() {
		return idTipoProjeto;
	}

	public void setIdTipoProjeto(Integer idTipoProjeto) {
		this.idTipoProjeto = idTipoProjeto;
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

}
