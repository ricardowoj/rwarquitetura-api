package com.rwarquitetura.api.model;

import java.io.File;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "tab_levantamento_medicao")
public class LevantamentoMedicao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "id_arquieto")
	private Integer idArquiteto;

	@Column(name = "id_cliente_secundario")
	private Integer idClienteSecundario;

	@Column(name = "id_projeto")
	private Integer idProjeto;

	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	@Column(name = "dh_trabalhada_inicio", updatable = false)
	private LocalDateTime dhTrabalhadaInicio;

	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	@Column(name = "dh_trabalhada_fim", updatable = false)
	private LocalDateTime dhTrabalhadaFim;

	@Column(name = "nome")
	private String nome;

	@Column(name = "caminho_arquivo")
	private String caminhoArquivo;

	@Column(name = "nome_arquivo")
	private String nomeArquivo;

	@Column(name = "tamanho_arquivo")
	private Long tamanhoArquivo;

	@Column(name = "fl_arquivo_presente")
	private boolean flArquivoPresente;

	@Column(name = "observacao")
	private String observacao;

	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	@Column(name = "dh_cadastro", updatable = false)
	private LocalDateTime dhCadastro;

	@Column(name = "hr_trabalhada")
	private String hrTrabalhada;

	public LevantamentoMedicao() {
	}

	public LevantamentoMedicao(Projeto projeto, File arquivoSalvo) {
		this.idProjeto = projeto.getId();
		this.idArquiteto = projeto.getClienteSecundario().getId();
		this.idClienteSecundario = projeto.getClienteSecundario().getId();
		this.nomeArquivo = arquivoSalvo.getName();
		this.caminhoArquivo = arquivoSalvo.getAbsolutePath();
		this.tamanhoArquivo = arquivoSalvo.length();
		this.flArquivoPresente = true;
		this.dhCadastro = LocalDateTime.now();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdArquiteto() {
		return idArquiteto;
	}

	public void setIdArquiteto(Integer idArquiteto) {
		this.idArquiteto = idArquiteto;
	}

	public Integer getIdClienteSecundario() {
		return idClienteSecundario;
	}

	public void setIdClienteSecundario(Integer idClienteSecundario) {
		this.idClienteSecundario = idClienteSecundario;
	}

	public LocalDateTime getDhTrabalhadaInicio() {
		return dhTrabalhadaInicio;
	}

	public Integer getIdProjeto() {
		return idProjeto;
	}

	public void setIdProjeto(Integer idProjeto) {
		this.idProjeto = idProjeto;
	}

	public void setDhTrabalhadaInicio(LocalDateTime dhTrabalhadaInicio) {
		this.dhTrabalhadaInicio = dhTrabalhadaInicio;
	}

	public LocalDateTime getDhTrabalhadaFim() {
		return dhTrabalhadaFim;
	}

	public void setDhTrabalhadaFim(LocalDateTime dhTrabalhadaFim) {
		this.dhTrabalhadaFim = dhTrabalhadaFim;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCaminhoArquivo() {
		return caminhoArquivo;
	}

	public void setCaminhoArquivo(String caminhoArquivo) {
		this.caminhoArquivo = caminhoArquivo;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public Long getTamanhoArquivo() {
		return tamanhoArquivo;
	}

	public void setTamanhoArquivo(Long tamanhoArquivo) {
		this.tamanhoArquivo = tamanhoArquivo;
	}

	public boolean isFlArquivoPresente() {
		return flArquivoPresente;
	}

	public void setFlArquivoPresente(boolean flArquivoPresente) {
		this.flArquivoPresente = flArquivoPresente;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public LocalDateTime getDhCadastro() {
		return dhCadastro;
	}

	public void setDhCadastro(LocalDateTime dhCadastro) {
		this.dhCadastro = dhCadastro;
	}

	public String getHrTrabalhada() {
		return hrTrabalhada;
	}

	public void setHrTrabalhada(String hrTrabalhada) {
		this.hrTrabalhada = hrTrabalhada;
	}

}
