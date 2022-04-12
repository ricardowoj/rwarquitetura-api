package com.rwarquitetura.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rwarquitetura.api.dto.EmailConfigDTO;
import com.rwarquitetura.api.exception.BusinessException;
import com.rwarquitetura.api.model.EmailConfig;
import com.rwarquitetura.api.model.Usuario;
import com.rwarquitetura.api.repository.EmailConfigRepository;
import com.rwarquitetura.api.repository.UsuarioRepository;

@RestController
@RequestMapping("email-config")
public class EmailConfigController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private EmailConfigRepository emailConfigRepository;

	@GetMapping
	public List<EmailConfig> buscar() {
		return emailConfigRepository.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<EmailConfig> salvar(@Valid @RequestBody EmailConfigDTO emailConfigDTO) {
		Usuario usuarioBase = usuarioRepository.findOne(emailConfigDTO.getIdUsuario());
		if (usuarioBase == null) {
			return ResponseEntity.notFound().build();
		}

		EmailConfig emailConfigBase = emailConfigRepository.findByIdUsuario(usuarioBase.getId());
		if (emailConfigBase != null) {
			throw new BusinessException(String.format("O e-mail %s já possui configuração", usuarioBase.getEmail()));
		}

		EmailConfig emailConfig = new EmailConfig();
		emailConfig.setIdUsuario(usuarioBase.getId());
		emailConfig.setEmail(emailConfigDTO.getEmail());
		emailConfig.setSenha(emailConfigDTO.getSenha());
		emailConfig.setTipoServidorEmail(emailConfigDTO.getTipoServidorEmail());
		emailConfigRepository.save(emailConfig);
		return ResponseEntity.ok(emailConfig);

	}

	@GetMapping("/buscar")
	public ResponseEntity<EmailConfig> buscarPorId(@RequestBody Integer idUsuario) {

		EmailConfig emailConfigBase = emailConfigRepository.findByIdUsuario(idUsuario);
		if (emailConfigBase == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(emailConfigBase);
		}
	}

	@DeleteMapping("/remover")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<EmailConfig> remover(@RequestBody Integer idUsuario) {

		EmailConfig emailConfigBase = emailConfigRepository.findByIdUsuario(idUsuario);
		if (emailConfigBase == null) {
			return ResponseEntity.notFound().build();
		} else {
			emailConfigRepository.delete(emailConfigBase.getId());
			return ResponseEntity.ok().build();
		}
	}

	@PutMapping
	public ResponseEntity<EmailConfig> atualizar(@Valid @RequestBody EmailConfig emailConfig) {

		EmailConfig emailConfigBase = emailConfigRepository.findByIdUsuario(emailConfig.getIdUsuario());

		if (emailConfigBase == null) {
			return ResponseEntity.notFound().build();
		}

		if (emailConfigBase.getEmail() != emailConfig.getEmail()) {
			throw new BusinessException(
					String.format("Não é permitido a troca de usuário. Id Usuário: %s", emailConfig.getIdUsuario()));
		}
		
		emailConfigBase.setEmail(emailConfig.getEmail());
		emailConfigBase.setSenha(emailConfig.getSenha());
		emailConfigBase.setTipoServidorEmail(emailConfig.getTipoServidorEmail());
		emailConfigRepository.save(emailConfigBase);
		return ResponseEntity.ok(emailConfigBase);

	}
}
