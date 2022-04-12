package com.rwarquitetura.api.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.rwarquitetura.api.model.Permissao;
import com.rwarquitetura.api.model.Usuario;
import com.rwarquitetura.api.repository.PermissaoRepository;

@RestController
@RequestMapping("/permissao")
public class PermissaoController {

	@Autowired
	private PermissaoRepository permissaoRepository;

	@GetMapping
	public List<Permissao> buscar() {
		return permissaoRepository.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Permissao> salvar(@RequestBody Permissao permissao, HttpServletResponse response) {

		Permissao permissaoBase = permissaoRepository.findByDescricao(permissao.getDescricao());
		if (permissaoBase != null) {
			throw new BusinessException(String.format("Permissão %s já está cadastrada", permissao.getDescricao()));
		}
		
		permissao.setDhCadastro(LocalDateTime.now());
		permissaoRepository.save(permissao);

		return ResponseEntity.ok(permissao);
	}

	@PostMapping("/salvar-lista")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<List<Permissao>> salvarLista(@Valid @RequestBody List<Permissao> permissoes,
			HttpServletResponse response) {

		for (Permissao permissao : permissoes) {

			Permissao permissaoBase = permissaoRepository.findByDescricao(permissao.getDescricao());
			if (permissaoBase != null) {
				return ResponseEntity.badRequest().build();
			}

			permissaoRepository.save(permissao);
		}

		return ResponseEntity.ok().body(permissoes);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Permissao> buscarPorId(@PathVariable Integer id) {

		Permissao permissaoBase = permissaoRepository.findOne(id);
		if (permissaoBase == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(permissaoBase);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Permissao> remover(@PathVariable Integer id) {

		Permissao permissaoBase = permissaoRepository.getOne(id);
		if (permissaoBase == null) {
			return ResponseEntity.notFound().build();
		} else {
			permissaoRepository.delete(id);
			return ResponseEntity.ok().build();
		}
	}

	@PutMapping
	public ResponseEntity<Permissao> atualizar(@RequestBody Usuario usuario) {

		Permissao permissaoBase = permissaoRepository.getOne(usuario.getId());
		if (permissaoBase == null) {
			return ResponseEntity.notFound().build();
		}

		BeanUtils.copyProperties(usuario, permissaoBase, "id");
		permissaoRepository.save(permissaoBase);
		return ResponseEntity.ok(permissaoBase);
	}

}
