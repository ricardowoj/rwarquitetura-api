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
import com.rwarquitetura.api.model.ClienteSecundario;
import com.rwarquitetura.api.model.EnderecoProjeto;
import com.rwarquitetura.api.repository.ClienteSecundarioRepository;
import com.rwarquitetura.api.repository.EnderecoProjetoRepository;

@RestController
@RequestMapping("/endereco-projeto")
@PreAuthorize("hasAuthority('ROLE_GERENCIAR_PROJETO')")
public class EnderecoProjetoController {
	
	@Autowired
	private EnderecoProjetoRepository enderecoProjetoRepository;

	@Autowired
	private ClienteSecundarioRepository clienteSecundarioRepository;

	@GetMapping
	public List<EnderecoProjeto> buscar() {
		return enderecoProjetoRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<EnderecoProjeto> salvar(@Valid @RequestBody EnderecoProjeto enderecoProjeto) {
		
		ClienteSecundario clienteSecundarioBase = clienteSecundarioRepository.findOne(enderecoProjeto.getIdClienteSecundario());
		if (clienteSecundarioBase == null) {
			throw new BusinessException(String.format("Cliente não localizado. Id Cliente: %s", enderecoProjeto.getIdClienteSecundario()));
		} 

		enderecoProjetoRepository.save(enderecoProjeto);
		return ResponseEntity.ok(enderecoProjeto);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EnderecoProjeto> buscarPorId(@PathVariable Integer id) {
		
		EnderecoProjeto enderecoProjetoBase = enderecoProjetoRepository.findOne(id);
		if (enderecoProjetoBase == null) {
			return ResponseEntity.notFound().build();
		} 

		return ResponseEntity.ok(enderecoProjetoBase);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<EnderecoProjeto> remover(@PathVariable Integer id) {
		
		EnderecoProjeto enderecoProjetoBase = enderecoProjetoRepository.findOne(id);
		if (enderecoProjetoBase == null) {
			return ResponseEntity.notFound().build();
		} 

		clienteSecundarioRepository.delete(id);
		return ResponseEntity.ok().build();
	}

	@PutMapping
	public ResponseEntity<EnderecoProjeto> atualizar(@Valid @RequestBody EnderecoProjeto enderecoProjeto) {
		
		EnderecoProjeto enderecoProjetoBase = enderecoProjetoRepository.findOne(enderecoProjeto.getId());
		if (enderecoProjetoBase == null) {
			return ResponseEntity.notFound().build();
		}

		enderecoProjetoBase.setIdClienteSecundario(enderecoProjeto.getIdClienteSecundario());
		enderecoProjetoBase.setCidade(enderecoProjeto.getCidade());
		enderecoProjetoBase.setEstado(enderecoProjeto.getEstado());
		enderecoProjetoBase.setRua(enderecoProjeto.getRua());
		enderecoProjetoBase.setNumero(enderecoProjeto.getNumero());
		enderecoProjetoBase.setBairro(enderecoProjeto.getBairro());
		enderecoProjetoBase.setCep(enderecoProjeto.getCep());
		enderecoProjetoBase.setComplemento(enderecoProjeto.getComplemento());
		
		enderecoProjetoRepository.save(enderecoProjetoBase);
		return ResponseEntity.ok(enderecoProjetoBase);
	}
}
