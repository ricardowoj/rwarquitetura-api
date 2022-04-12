package com.rwarquitetura.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rwarquitetura.api.repository.ParcelaPagamentoRepository;

@RestController
@RequestMapping("/parcela-pagamento")
@PreAuthorize("hasAuthority('ROLE_GERENCIAR_PARCELA_PAGAMENTO')")
public class FinanceiroParcelaController {

	@Autowired
	private ParcelaPagamentoRepository parcelaPagamentoRepository;

//	@GetMapping
//	public List<ParcelaPagamento> buscar() {
//		return parcelaPagamentoRepository.findAll();
//	}
//
//	@PostMapping
//	@ResponseStatus(HttpStatus.CREATED)
//	public ResponseEntity<FinanceiroParcela> salvar(@Valid @RequestBody FinanceiroParcela parcelaPagamento) {
//		parcelaPagamentoRepository.save(parcelaPagamento);
//		return ResponseEntity.ok(parcelaPagamento);
//	}
//
//	@GetMapping("/{id}")
//	public ResponseEntity<FinanceiroParcela> buscarPorId(@PathVariable Integer id) {
//		
//		FinanceiroParcela parcelaPagamentoBase = parcelaPagamentoRepository.getOne(id);
//		if (parcelaPagamentoBase == null) {
//			return ResponseEntity.notFound().build();
//		} else {
//			return ResponseEntity.ok(parcelaPagamentoBase);
//		}
//	}
//
//	@DeleteMapping("cliente/{id}")
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public ResponseEntity<FinanceiroParcela> remover(@PathVariable Integer id) {
//		
//		FinanceiroParcela parcelaPagamentoBase = parcelaPagamentoRepository.getOne(id);
//		if (parcelaPagamentoBase == null) {
//			return ResponseEntity.notFound().build();
//		} else {
//			parcelaPagamentoRepository.delete(id);
//			return ResponseEntity.ok().build();
//		}
//	}
//
//	@PutMapping
//	public ResponseEntity<FinanceiroParcela> atualizar(@Valid @RequestBody FinanceiroParcela parcelaPagamento) {
//		
//		FinanceiroParcela parcelaPagamentoBase = parcelaPagamentoRepository.getOne(parcelaPagamento.getId());
//		if (parcelaPagamentoBase == null) {
//			return ResponseEntity.notFound().build();
//		} else {
//			BeanUtils.copyProperties(parcelaPagamento, parcelaPagamentoBase, "id");
//			parcelaPagamentoRepository.save(parcelaPagamento);
//			return ResponseEntity.ok(parcelaPagamento);
//		}
//	}
}
