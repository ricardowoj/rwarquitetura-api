package com.rwarquitetura.api.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rwarquitetura.api.dto.ArquitetoDTO;
import com.rwarquitetura.api.dto.CepDTO;
import com.rwarquitetura.api.dto.EmailDTO;
import com.rwarquitetura.api.dto.EnderecoDTO;
import com.rwarquitetura.api.model.Arquiteto;
import com.rwarquitetura.api.model.Usuario;
import com.rwarquitetura.api.repository.ArquitetoRepository;
import com.rwarquitetura.api.repository.UsuarioRepository;
import com.rwarquitetura.api.util.Api;

@RestController
@RequestMapping("arquiteto")
public class ArquitetoController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ArquitetoRepository arquitetoRepository;

	@GetMapping
	public List<Arquiteto> buscar() {
		return arquitetoRepository.findAll();
	}

	@PostMapping("/buscarPorEmail")
	@PreAuthorize("hasAuthority('ROLE_GERENCIAR_ARQUITETO')")
	public ResponseEntity<Arquiteto> buscarPorEmail(@RequestBody EmailDTO emailDTO) {
		Arquiteto arquitetoBase = arquitetoRepository.findByEmail(emailDTO.getEmail());
		if (arquitetoBase == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(arquitetoBase);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAuthority('ROLE_GERENCIAR_ARQUITETO')")
	public ResponseEntity<Arquiteto> salvar(@RequestBody ArquitetoDTO arquitetoDTO) {
		Usuario usuarioBase = usuarioRepository.findByEmail(arquitetoDTO.getUsuario().getEmail());
		if (usuarioBase == null) {
			return ResponseEntity.notFound().build();
		} else {
			Arquiteto arquiteto = new Arquiteto();
			arquiteto.setUsuario(usuarioBase);
			arquiteto.setNome(arquitetoDTO.getNome());
			arquiteto.setTipoDocumento(arquitetoDTO.getTipoDocumento());
			arquiteto.setNumeroDoc(arquitetoDTO.getNumeroDoc());
			arquiteto.setCidade(arquitetoDTO.getCidade());
			arquiteto.setEstado(arquitetoDTO.getEstado());
			arquiteto.setRua(arquitetoDTO.getRua());
			arquiteto.setNumero(arquitetoDTO.getNumero());
			arquiteto.setBairro(arquitetoDTO.getBairro());
			arquiteto.setCep(arquitetoDTO.getCep());
			arquiteto.setComplemento(arquitetoDTO.getComplemento());
			arquiteto.setDhCadastro(LocalDateTime.now());
			arquitetoRepository.save(arquiteto);
			return ResponseEntity.ok(arquiteto);
		}
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_GERENCIAR_ARQUITETO')")
	public ResponseEntity<Arquiteto> buscarPorId(@PathVariable Integer id) {
		Arquiteto arquitetoBase = arquitetoRepository.findOne(id);
		if (arquitetoBase == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(arquitetoBase);
		}
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_GERENCIAR_ARQUITETO')")
	public ResponseEntity<Arquiteto> remover(@PathVariable Integer id) {
		
		Arquiteto arquitetoBase = arquitetoRepository.findOne(id);
		if (arquitetoBase == null) {
			return ResponseEntity.notFound().build();
		} else {
			arquitetoRepository.delete(id);
			return ResponseEntity.ok().build();
		}
	}

	@PutMapping("/editar")
	@PreAuthorize("hasAuthority('ROLE_GERENCIAR_ARQUITETO')")
	public ResponseEntity<Arquiteto> editar(@RequestBody ArquitetoDTO arquitetoDTO) {
		Arquiteto arquitetoBase = arquitetoRepository.findByEmail(arquitetoDTO.getUsuario().getEmail());
		if (arquitetoBase == null) {
			return ResponseEntity.notFound().build();
		} else {
			arquitetoBase.setNome(arquitetoDTO.getNome());
			arquitetoBase.setTipoDocumento(arquitetoDTO.getTipoDocumento());
			arquitetoBase.setNumeroDoc(arquitetoDTO.getNumeroDoc());
			arquitetoBase.setCidade(arquitetoDTO.getCidade());
			arquitetoBase.setEstado(arquitetoDTO.getEstado());
			arquitetoBase.setRua(arquitetoDTO.getRua());
			arquitetoBase.setNumero(arquitetoDTO.getNumero());
			arquitetoBase.setBairro(arquitetoDTO.getBairro());
			arquitetoBase.setCep(arquitetoDTO.getCep());
			arquitetoBase.setComplemento(arquitetoDTO.getComplemento());
			arquitetoRepository.save(arquitetoBase);
			return ResponseEntity.ok(arquitetoBase);
		}
	}
	
	@PostMapping("/cep")
	@PreAuthorize("hasAuthority('ROLE_GERENCIAR_ARQUITETO')")
	public EnderecoDTO cep(@RequestBody CepDTO cepDTO) throws Exception {
		try {
			EnderecoDTO enderecoDTO = Api.buscarCep(cepDTO.getCep());
			return enderecoDTO;
		} catch (Exception e) {
			throw new Exception("ERRO: " + e);
		}
	}
}
