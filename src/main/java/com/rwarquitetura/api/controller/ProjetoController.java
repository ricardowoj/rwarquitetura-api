package com.rwarquitetura.api.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.rwarquitetura.api.exception.BusinessException;
import com.rwarquitetura.api.model.EnderecoProjeto;
import com.rwarquitetura.api.model.Projeto;
import com.rwarquitetura.api.repository.ClienteSecundarioRepository;
import com.rwarquitetura.api.repository.EnderecoProjetoRepository;
import com.rwarquitetura.api.repository.ProjetoRepository;

@RestController
@RequestMapping("/projeto")
@PreAuthorize("hasAuthority('ROLE_GERENCIAR_PROJETO')")
public class ProjetoController {

	@Autowired
	private ProjetoRepository projetoRepository;

	@Autowired
	private EnderecoProjetoRepository enderecoRepository;

	@Autowired
	private ClienteSecundarioRepository clienteSecundarioRepository;

	@GetMapping
	public List<Projeto> buscar() {
		return projetoRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<Projeto> salvar(@RequestBody Projeto projeto) {
		
		EnderecoProjeto enderecoProjetoBase = enderecoRepository.findOne(projeto.getEnderecoProjeto().getId());
		if (enderecoProjetoBase == null) {
			throw new BusinessException(String.format("Endereço de projeto não localizado: Id Endereço %s", projeto.getEnderecoProjeto().getId()));
		} 
		
		projeto.setEnderecoProjeto(enderecoProjetoBase);
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
	public ResponseEntity<Projeto> atualizar(@Valid @RequestBody Projeto projeto) {
		
		Projeto projetoBase = projetoRepository.findOne(projeto.getId());
		if (projetoBase == null) {
			return ResponseEntity.notFound().build();
		} 

		projetoBase.setEnderecoProjeto(projeto.getEnderecoProjeto());
		projetoBase.setTipoProjeto(projeto.getTipoProjeto());
		
		projetoRepository.save(projetoBase);
		return ResponseEntity.ok(projetoBase);
	}
}
