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

import com.rwarquitetura.api.dto.ProjetoDTO;
import com.rwarquitetura.api.model.ClienteSecundario;
import com.rwarquitetura.api.model.Projeto;
import com.rwarquitetura.api.repository.ClienteSecundarioRepository;
import com.rwarquitetura.api.repository.ProjetoRepository;

@RestController
@RequestMapping("/projeto")
@PreAuthorize("hasAuthority('ROLE_GERENCIAR_PROJETO')")
public class ProjetoController {

	@Autowired
	private ProjetoRepository projetoRepository;

	@Autowired
	private ClienteSecundarioRepository clienteSecundarioRepository;

	@GetMapping
	public List<Projeto> buscar() {
		return projetoRepository.findAll();
	}
	
	@GetMapping("/arquiteto/{id}")
	public List<Projeto> buscarPorArquiteto(@PathVariable Integer id) {
		return projetoRepository.findByIdArquiteto(id);
	}

	@PostMapping
	public ResponseEntity<Projeto> salvar(@RequestBody ProjetoDTO projetoDTO) {
		ClienteSecundario clienteSecundarioBase = clienteSecundarioRepository.getOne(projetoDTO.getIdClienteSecundario());
		Projeto projeto = new Projeto();
		projeto.setCidade(projetoDTO.getCidade());
		projeto.setEstado(projetoDTO.getEstado());
		projeto.setRua(projetoDTO.getRua());
		projeto.setNumero(projetoDTO.getNumero());
		projeto.setBairro(projetoDTO.getBairro());
		projeto.setCep(projetoDTO.getCep());
		projeto.setComplemento(projetoDTO.getComplemento());
		projeto.setDhCadastro(LocalDateTime.now());
		projeto.setTipoProjeto(projetoDTO.getIdTipoProjeto());
		projeto.setTipoCaracteristica(projetoDTO.getIdTipoCaracteristica());
		projeto.setClienteSecundario(clienteSecundarioBase);
		projetoRepository.save(projeto);
		return ResponseEntity.ok(projeto);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Projeto> buscarPorId(@PathVariable Integer id) {
		
		Projeto projetoBase = projetoRepository.findOne(id);
		if (projetoBase == null) {
			return ResponseEntity.notFound().build();
		} 

		return ResponseEntity.ok(projetoBase);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Projeto> remover(@PathVariable Integer id) {
		
		Projeto projetoBase = projetoRepository.findOne(id);
		if (projetoBase == null) {
			return ResponseEntity.notFound().build();
		} 
		
		clienteSecundarioRepository.delete(id);
		return ResponseEntity.ok().build();
	}

	@PutMapping
	public ResponseEntity<Projeto> atualizar(@RequestBody ProjetoDTO projetoDTO) {
		Projeto projetoBase = projetoRepository.findOne(projetoDTO.getId());
		if (projetoBase == null) {
			return ResponseEntity.notFound().build();
		} 

		projetoBase.setCidade(projetoDTO.getCidade());
		projetoBase.setEstado(projetoDTO.getEstado());
		projetoBase.setRua(projetoDTO.getRua());
		projetoBase.setNumero(projetoDTO.getNumero());
		projetoBase.setBairro(projetoDTO.getBairro());
		projetoBase.setCep(projetoDTO.getCep());
		projetoBase.setComplemento(projetoDTO.getComplemento());
		projetoBase.setTipoProjeto(projetoDTO.getIdTipoProjeto());
		projetoBase.setTipoCaracteristica(projetoDTO.getIdTipoCaracteristica());
		
		ClienteSecundario clienteSecundarioBase = clienteSecundarioRepository.getOne(projetoDTO.getIdClienteSecundario());
		projetoBase.setClienteSecundario(clienteSecundarioBase);
		
		projetoRepository.save(projetoBase);
		return ResponseEntity.ok(projetoBase);
	}
}
