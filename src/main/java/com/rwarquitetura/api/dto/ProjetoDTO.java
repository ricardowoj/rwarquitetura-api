package com.rwarquitetura.api.dto;

import java.math.BigDecimal;

public class ProjetoDTO {

	private Integer idEnderecoProjeto;

	private BigDecimal valorProjeto;

	private Integer tipoProjeto;

	private String cidade;

	private String estado;

	private String rua;

	private String numero;

	private String bairro;

	private String cep;

	private String complemento;

	public Integer getIdEnderecoProjeto() {
		return idEnderecoProjeto;
	}

	public void setIdEnderecoProjeto(Integer idEnderecoProjeto) {
		this.idEnderecoProjeto = idEnderecoProjeto;
	}

	public BigDecimal getValorProjeto() {
		return valorProjeto;
	}

	public void setValorProjeto(BigDecimal valorProjeto) {
		this.valorProjeto = valorProjeto;
	}

	public Integer getTipoProjeto() {
		return tipoProjeto;
	}

	public void setTipoProjeto(Integer tipoProjeto) {
		this.tipoProjeto = tipoProjeto;
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
