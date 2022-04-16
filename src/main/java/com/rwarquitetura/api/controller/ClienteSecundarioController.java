package com.rwarquitetura.api.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rwarquitetura.api.dto.ClienteSecundarioDTO;
import com.rwarquitetura.api.enums.TipoStatus;
import com.rwarquitetura.api.enums.TipoUsuario;
import com.rwarquitetura.api.model.Arquiteto;
import com.rwarquitetura.api.model.ClienteSecundario;
import com.rwarquitetura.api.model.Permissao;
import com.rwarquitetura.api.model.Usuario;
import com.rwarquitetura.api.repository.ArquitetoRepository;
import com.rwarquitetura.api.repository.ClienteSecundarioRepository;
import com.rwarquitetura.api.repository.PermissaoRepository;
import com.rwarquitetura.api.repository.UsuarioRepository;

@RestController
@RequestMapping("cliente")
@PreAuthorize("hasAuthority('ROLE_GERENCIAR_CLIENTE')")
public class ClienteSecundarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PermissaoRepository permissaoRepository;

	@Autowired
	private ArquitetoRepository arquitetoRepository;

	@Autowired
	private ClienteSecundarioRepository clienteSecundarioRepository;

	@GetMapping
	public List<ClienteSecundario> buscar() {
		return clienteSecundarioRepository.findAll();
	}

	@GetMapping("/arquiteto/{id}")
	public List<ClienteSecundario> buscarPorArquiteto(@PathVariable Integer id) {
		List<ClienteSecundario> clientes = clienteSecundarioRepository.findByIdArquiteto(id);
		return clientes;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<ClienteSecundario> salvar(@RequestBody ClienteSecundarioDTO clienteSecundarioDTO) {
		//Registrar CLiente Secundário
		Arquiteto arquitetoBase = arquitetoRepository.findOne(clienteSecundarioDTO.getIdArquiteto());
		ClienteSecundario clienteSecundario = new ClienteSecundario();
		clienteSecundario.setArquiteto(arquitetoBase);
		clienteSecundario.setNome(clienteSecundarioDTO.getNome());
		clienteSecundario.setTipoDocumento(clienteSecundarioDTO.getTipoDocumento());
		clienteSecundario.setNumeroDoc(clienteSecundarioDTO.getNumeroDoc());
		clienteSecundario.setCidade(clienteSecundarioDTO.getCidade());
		clienteSecundario.setEstado(clienteSecundarioDTO.getEstado());
		clienteSecundario.setRua(clienteSecundarioDTO.getRua());
		clienteSecundario.setNumero(clienteSecundarioDTO.getNumero());
		clienteSecundario.setBairro(clienteSecundarioDTO.getBairro());
		clienteSecundario.setCep(clienteSecundarioDTO.getCep());
		clienteSecundario.setComplemento(clienteSecundarioDTO.getComplemento());
		clienteSecundario.setTelefone(clienteSecundarioDTO.getTelefone());
		clienteSecundario.setDhCadastro(LocalDateTime.now());
		
		//Registrar Usuário
		Usuario usuario = new Usuario();
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		usuario.setSenha(bCryptPasswordEncoder.encode("123456"));
		
		usuario.setDhCadastro(LocalDateTime.now()); 
		usuario.setTipoStatus(TipoStatus.ATIVO.getId());
		usuario.setTipoUsuario(TipoUsuario.CLIENTE_SECUNDARIO.getId());
		usuario.setEmail(clienteSecundarioDTO.getUsuario().getEmail());
		
		Permissao permissao = permissaoRepository.findByDescricao("ROLE_GERENCIAR_CLIENTE");
		List<Permissao> permissoes = new ArrayList<Permissao>();
		permissoes.add(permissao);
		usuario.setPermissoes(permissoes);
		
		Usuario usuarioBase = usuarioRepository.save(usuario);
		clienteSecundario.setUsuario(usuarioBase);
		clienteSecundarioRepository.save(clienteSecundario);
		return ResponseEntity.ok(clienteSecundario);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClienteSecundario> buscarPorId(@PathVariable Integer id) {

		ClienteSecundario clienteSecundarioBase = clienteSecundarioRepository.findOne(id);
		if (clienteSecundarioBase == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(clienteSecundarioBase);
		}
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<ClienteSecundario> remover(@PathVariable Integer id) {

		ClienteSecundario clienteSecundarioBase = clienteSecundarioRepository.findOne(id);
		if (clienteSecundarioBase == null) {
			return ResponseEntity.notFound().build();
		} else {
			clienteSecundarioRepository.delete(id);
			return ResponseEntity.ok().build();
		}
	}

	@PutMapping
	public ResponseEntity<ClienteSecundario> editar(@RequestBody ClienteSecundarioDTO clienteSecundarioDTO) {
		//Registrar CLiente Secundário
		ClienteSecundario clienteSecundario = clienteSecundarioRepository.getOne(clienteSecundarioDTO.getId());
		clienteSecundario.setNome(clienteSecundarioDTO.getNome());
		clienteSecundario.setTipoDocumento(clienteSecundarioDTO.getTipoDocumento());
		clienteSecundario.setNumeroDoc(clienteSecundarioDTO.getNumeroDoc());
		clienteSecundario.setCidade(clienteSecundarioDTO.getCidade());
		clienteSecundario.setEstado(clienteSecundarioDTO.getEstado());
		clienteSecundario.setRua(clienteSecundarioDTO.getRua());
		clienteSecundario.setNumero(clienteSecundarioDTO.getNumero());
		clienteSecundario.setBairro(clienteSecundarioDTO.getBairro());
		clienteSecundario.setCep(clienteSecundarioDTO.getCep());
		clienteSecundario.setComplemento(clienteSecundarioDTO.getComplemento());
		clienteSecundario.setTelefone(clienteSecundarioDTO.getTelefone());
		clienteSecundario.setDhCadastro(LocalDateTime.now());
		clienteSecundarioRepository.save(clienteSecundario);
		
		if (clienteSecundarioDTO.getPassword() != null) {
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			Usuario usuario = usuarioRepository.getOne(clienteSecundarioDTO.getUsuario().getId());
			usuario.setSenha(bCryptPasswordEncoder.encode(clienteSecundarioDTO.getPassword()));
			usuarioRepository.save(usuario);
			
		}
		return ResponseEntity.ok(clienteSecundario);
	}

}
