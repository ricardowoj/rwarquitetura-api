package com.rwarquitetura.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rwarquitetura.api.model.EmailRegistro;
import com.rwarquitetura.api.repository.EmailRegistroRepository;

@RestController
@RequestMapping("email-registro")
public class EmailRegistroController {
	
	EmailRegistroRepository emailRegistroRepository;

	@GetMapping
	public List<EmailRegistro> buscar() {
		return emailRegistroRepository.findAll();
	}
}
