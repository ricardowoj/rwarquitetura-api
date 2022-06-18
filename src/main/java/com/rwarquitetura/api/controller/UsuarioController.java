package com.rwarquitetura.api.controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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

import com.rwarquitetura.api.dto.EmailDTO;
import com.rwarquitetura.api.dto.UsuarioEditarDTO;
import com.rwarquitetura.api.enums.StatusEmail;
import com.rwarquitetura.api.exception.BusinessException;
import com.rwarquitetura.api.model.EmailConfig;
import com.rwarquitetura.api.model.EmailRegistro;
import com.rwarquitetura.api.model.Permissao;
import com.rwarquitetura.api.model.Usuario;
import com.rwarquitetura.api.repository.EmailConfigRepository;
import com.rwarquitetura.api.repository.EmailRegistroRepository;
import com.rwarquitetura.api.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private EmailRegistroRepository emailRegistroRepository;

	@Autowired
	private EmailConfigRepository emailConfigRepository;

	@GetMapping
	public List<Usuario> buscar() {
		return usuarioRepository.findAll();
	}

	@PostMapping("/salvar")
	@PreAuthorize("hasAuthority('ROLE_GERENCIAR_USUARIO')")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {

		Usuario usuarioBase = usuarioRepository.findByEmail(usuario.getEmail());
		if (usuarioBase != null) {
			throw new BusinessException(String.format("E-mail %s já está cadastrado", usuario.getEmail()));
		}

		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		usuario.setSenha(bCryptPasswordEncoder.encode(usuario.getSenha()));
		usuario.setDhCadastro(LocalDateTime.now()); 
		usuario.setTipoStatus(usuario.getTipoStatus());
		usuario.setTipoUsuario(usuario.getTipoUsuario());
		Usuario usuarioSalvo = usuarioRepository.save(usuario);

		// Enviar e-mail Bem Vindo ao Usuário
//		if (usuarioSalvo.getTipoUsuario() == TipoUsuario.ARQUITETO.getId()) {
//			arquitetoEmailBemVindo(usuarioSalvo);
//		}

		return ResponseEntity.ok(usuarioSalvo);
	}

	@PostMapping("/buscar")
	@PreAuthorize("hasAuthority('ROLE_GERENCIAR_USUARIO')")
	public ResponseEntity<Usuario> buscar(@RequestBody Usuario usuario) {

		Usuario usuarioBase = usuarioRepository.findOne(usuario.getId());
		if (usuarioBase == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(usuarioBase);
	}

	@PostMapping("/buscarPorEmail")
	public ResponseEntity<Usuario> buscarPorEmail(@RequestBody EmailDTO emailDTO) {
		Usuario usuarioBase = usuarioRepository.findByEmail(emailDTO.getEmail());
		if (usuarioBase == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(usuarioBase);
	}

	@PreAuthorize("hasAuthority('ROLE_GERENCIAR_USUARIO')")
	@DeleteMapping("/remover/{id}")
	public ResponseEntity<Usuario> remover(@PathVariable Integer id) {

		Usuario usuarioBase = usuarioRepository.findOne(id);
		if (usuarioBase == null) {
			return ResponseEntity.notFound().build();
		}

		usuarioRepository.delete(id);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/editar")
	@PreAuthorize("hasAuthority('ROLE_GERENCIAR_USUARIO')")
	public ResponseEntity<Usuario> editar(@RequestBody UsuarioEditarDTO usuarioEditarDTO) {
		
		Usuario usuarioBase = usuarioRepository.findOne(usuarioEditarDTO.getId());
		
		//Validação campo vazio
		if (usuarioEditarDTO.getEmail() == "" || usuarioEditarDTO.getEmail() == null) {
			throw new BusinessException("E-mail nulo não é permitido");
		}
		
		//Sem alteração de e-mail
		if (usuarioBase.getEmail().equals(usuarioEditarDTO.getEmail())) {
			usuarioBase.setTipoStatus(usuarioEditarDTO.getTipoStatus());
			usuarioBase.setTipoUsuario(usuarioEditarDTO.getTipoUsuario());
		} 
		
		//Com alteração de e-mail
		if (!usuarioBase.getEmail().equals(usuarioEditarDTO.getEmail())) {
			Usuario usuarioBaseEmail = usuarioRepository.findByEmail(usuarioEditarDTO.getEmail());
			if (usuarioBaseEmail != null) {
				throw new BusinessException(String.format("E-mail %s já está cadastrado", usuarioBaseEmail.getEmail()));
			}

			usuarioBase.setTipoStatus(usuarioEditarDTO.getTipoStatus());
			usuarioBase.setTipoUsuario(usuarioEditarDTO.getTipoUsuario());
			usuarioBase.setEmail(usuarioEditarDTO.getEmail());
		}
		
		usuarioRepository.save(usuarioBase);
		return ResponseEntity.ok(usuarioBase);
	}

	@PutMapping("/trocarSenha")
	@PreAuthorize("hasAuthority('ROLE_GERENCIAR_USUARIO')")
	public ResponseEntity<Usuario> trocarSenha(@RequestBody Usuario usuarioEditarDTO) {

		Usuario usuarioBase = usuarioRepository.findOne(usuarioEditarDTO.getId());
		if (usuarioBase == null) {
			return ResponseEntity.notFound().build();
		}

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		usuarioBase.setSenha(encoder.encode(usuarioEditarDTO.getSenha()));
		usuarioRepository.save(usuarioBase);
		return ResponseEntity.ok(usuarioBase);
	}

	@PutMapping("/salvarPermissao")
	@PreAuthorize("hasAuthority('ROLE_GERENCIAR_USUARIO')")
	public Usuario salvarPermissao(@RequestBody Usuario usuario) {

		Usuario usuarioBase = usuarioRepository.findOne(usuario.getId());
		if (usuarioBase == null) {
			throw new BusinessException("Usuário não localizado");
		}

		for (Permissao permissaoBase : usuarioBase.getPermissoes()) {
			if (permissaoBase == usuario.getPermissoes().get(0)) {
				throw new BusinessException(
						String.format("Permissão %s já está cadastrada", usuario.getPermissoes().get(0).getDescricao()));
			}
		}
		
		usuarioBase.getPermissoes().add(usuario.getPermissoes().get(0));
		usuarioRepository.save(usuarioBase);
		return usuario;
	}

	@PutMapping("/removerPermissao")
	@PreAuthorize("hasAuthority('ROLE_GERENCIAR_USUARIO')")
	public Usuario removerPermissao(@RequestBody UsuarioEditarDTO usuarioEditarDTO) {

		Usuario usuarioBase = usuarioRepository.findOne(usuarioEditarDTO.getId());
		if (usuarioBase == null) {
			throw new BusinessException("Usuário não localizado");
		}

		usuarioBase.setPermissoes(usuarioEditarDTO.getPermissoes());
		
		usuarioRepository.save(usuarioBase);
		return usuarioBase;
	}

	public void arquitetoEmailBemVindo(Usuario usuario) {
		// Email Admin: rwdevsolucoes@gmail.com
		String emailAdmin = "rwdevsolucoes@gmail.com";
		Usuario usuarioAdminBase = usuarioRepository.findByEmail(emailAdmin);
		if (usuarioAdminBase == null) {
			throw new BusinessException("Usuário Admin não localizado na base");
		}

		Usuario usuarioBaseDestinatario = usuarioRepository.findByEmail(usuario.getEmail());
		if (usuarioBaseDestinatario == null) {
			throw new BusinessException("Usuário destinatário não localizado na base");
		}

		// Envio de e-mail com Bem Vindo - ADMIN
		EmailRegistro emailRegistro = new EmailRegistro();
		emailRegistro.setIdUsuarioRemetente(usuarioAdminBase.getId());
		emailRegistro.setEmailUsuarioRemetente(usuarioAdminBase.getEmail());
		emailRegistro.setIdUsuarioDestinatario(usuarioBaseDestinatario.getId());
		emailRegistro.setEmailUsuarioDestinatario(usuarioBaseDestinatario.getEmail());

		String[] cortarEmailArroba = usuario.getEmail().split("@");
		String nomeLoginEmail = cortarEmailArroba[0];

		emailRegistro.setTituloEmail(String.format("Bem vindo %s ao sistema RWArquitetura", nomeLoginEmail));
		emailRegistro
				.setTextoEmail("Bem vindo ao Sistema RWArquitetura - Nosso e-mail de contato: rwdevsolucoes@gmail.com");
		emailRegistro.setDhEnvio(LocalDateTime.now());
		emailRegistro.setTipoRemetente(usuarioAdminBase.getTipoUsuario());
		emailRegistro.setTipoDestinatario(usuarioBaseDestinatario.getTipoUsuario());

		// CONFIG GMAIL
		EmailConfig emailConfigBaseAdmin = emailConfigRepository.findByIdUsuario(usuarioAdminBase.getId());
		if (emailConfigBaseAdmin == null) {
			throw new BusinessException(
					String.format("O e-mail %s não possui configuração para envio", usuarioAdminBase.getEmail()));
		}

		try {
			configurarEmailGmail(emailRegistro, emailConfigBaseAdmin);
			emailRegistro.setStatusEmail(StatusEmail.ENVIADO.getId());
			emailRegistroRepository.save(emailRegistro);
		} catch (Exception e) {
			emailRegistro.setStatusEmail(StatusEmail.ERRO.getId());
			emailRegistroRepository.save(emailRegistro);
		}

//		if (emailConfigBaseAdmin.getTipoServidorEmail() == TipoServidorEmail.HOTMAIL.getId()) {
//			try {
//				configurarEmailHotmail(emailRegistro, emailConfigBaseAdmin);
//				emailRegistro.setStatusEmail(StatusEmail.ENVIADO.getId());			
//				emailRegistroRepository.save(emailRegistro);
//			} catch (Exception e) {
//				emailRegistro.setStatusEmail(StatusEmail.ERRO.getId());			
//				emailRegistroRepository.save(emailRegistro);
//			}
//		}
//		
//		throw new BusinessException(String.format("O tipo de servidor e-mail %s não é permitido", emailConfigBaseAdmin.getTipoServidorEmail()));
	}

	private void configurarEmailGmail(EmailRegistro emailRegistro, EmailConfig emailConfig)
			throws AddressException, MessagingException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		enviarEmail(props, emailRegistro, emailConfig);
	}

//	private void configurarEmailHotmail(EmailRegistro emailRegistro, EmailConfig emailConfig) throws AddressException, MessagingException {
//		Properties props = new Properties();
//		props.put("mail.transport.protocol", "smtp");
//		props.put("mail.smtp.host", "smtp.live.com");
//		props.put("mail.smtp.socketFactory.port", "587");
//		props.put("mail.smtp.socketFactory.fallback", "false");
//		props.put("mail.smtp.starttls.enable", "true");
//		props.put("mail.smtp.auth", "true");
//		props.put("mail.smtp.port", "587");
//		
//		enviarEmail(props, emailRegistro, emailConfig);
//
//	}

	private void enviarEmail(Properties props, EmailRegistro emailRegistro, EmailConfig emailConfig)
			throws AddressException, MessagingException {
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailConfig.getEmail(), emailConfig.getSenha());
			}
		});

		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(emailConfig.getEmail(), false));
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailRegistro.getEmailUsuarioDestinatario()));
		msg.setSubject(emailRegistro.getTituloEmail());
		msg.setContent(emailRegistro.getTextoEmail(), "text/html");
		msg.setSentDate(new Date());

		Transport.send(msg);
	}

}
