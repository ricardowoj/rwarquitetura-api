package com.rwarquitetura.api.controller;

import com.rwarquitetura.api.dto.ArquitetoDTO;
import com.rwarquitetura.api.dto.CepDTO;
import com.rwarquitetura.api.dto.EmailDTO;
import com.rwarquitetura.api.dto.EnderecoDTO;
import com.rwarquitetura.api.model.Arquiteto;
import com.rwarquitetura.api.model.Usuario;
import com.rwarquitetura.api.repository.ArquitetoRepository;
import com.rwarquitetura.api.repository.UsuarioRepository;
import com.rwarquitetura.api.util.ConsultaCorreios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
		}

		Arquiteto arquiteto = new Arquiteto(usuarioBase, arquitetoDTO);
		arquitetoRepository.save(arquiteto);
		return ResponseEntity.ok(arquiteto);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_GERENCIAR_ARQUITETO')")
	public ResponseEntity<Arquiteto> buscarPorId(@PathVariable Integer id) {
		Arquiteto arquitetoBase = arquitetoRepository.findOne(id);
		if (arquitetoBase == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(arquitetoBase);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_GERENCIAR_ARQUITETO')")
	public ResponseEntity<Arquiteto> remover(@PathVariable Integer id) {
		Arquiteto arquitetoBase = arquitetoRepository.findOne(id);
		if (arquitetoBase == null) {
			return ResponseEntity.notFound().build();
		}

		arquitetoRepository.delete(id);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/editar")
	@PreAuthorize("hasAuthority('ROLE_GERENCIAR_ARQUITETO')")
	public ResponseEntity<Arquiteto> editar(@RequestBody ArquitetoDTO arquitetoDTO) {
		Arquiteto arquitetoBase = arquitetoRepository.findByEmail(arquitetoDTO.getUsuario().getEmail());
		if (arquitetoBase == null) {
			return ResponseEntity.notFound().build();
		}

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
	
	@PostMapping("/cep")
	@PreAuthorize("hasAuthority('ROLE_GERENCIAR_ARQUITETO')")
	public EnderecoDTO cep(@RequestBody CepDTO cepDTO) throws Exception {
		try {
			return ConsultaCorreios.buscarCep(cepDTO.getCep());
		} catch (Exception e) {
			throw new Exception("ERRO: " + e);
		}
	}
}
