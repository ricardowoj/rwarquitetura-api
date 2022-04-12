package com.rwarquitetura.api.service;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailService {

//	@Autowired
//	private EmailRegistroRepository emailRegistroRepository;
//
//	@Autowired
//	private EmailConfigRepository emailConfigRepository;
//
//	@Autowired
//	private UsuarioRepository usuarioRepository;

//	public ResponseEntity<EmailRegistro> arquiteto(Usuario usuario) {
//		//Email Admin: rwdevsolucoes@gmail.com
//		Usuario usuarioAdmin = new Usuario();
//		usuarioAdmin.setEmail("rwdevsolucoes@gmail.com");
//		Usuario usuarioAdminBase = usuarioRepository.findByEmail(usuarioAdmin.getEmail());
//		if (usuarioAdminBase == null) {
//			throw new BusinessException("Usuário Admin não localizado na base");
//		}
//		
//		
//		Usuario usuarioBaseDestinatario = usuarioRepository.findByEmail(usuario.getEmail());
//		if (usuarioBaseDestinatario == null) {
//			throw new BusinessException("Usuário destinatário não localizado na base");
//		}
//		
//		//Envio de e-mail com Bem Vindo - ADMIN
//		EmailRegistro emailRegistro = new EmailRegistro();
//		emailRegistro.setIdUsuarioRemetente(usuarioAdmin.getId());
//		emailRegistro.setEmailUsuarioRemetente(usuarioAdmin.getEmail());
//		emailRegistro.setIdUsuarioDestinatario(usuarioBaseDestinatario.getId());
//		emailRegistro.setEmailUsuarioDestinatario(usuarioBaseDestinatario.getEmail());
//		
//		String[] cortarEmailArroba = usuario.getEmail().split("@");
//		String nomeLoginEmail = cortarEmailArroba[0];
//		
//		emailRegistro.setTituloEmail(String.format("Bem vindo %s ao sistema RWArquitetura", nomeLoginEmail));
//		emailRegistro.setTextoEmail("Bem vindo ao Sistema RWArquitetura - Nosso e-mail de contato: rwdevsolucoes@gmail.com");
//		emailRegistro.setDhEnvio(LocalDateTime.now());
//
//		// CONFIG GMAIL
//		EmailConfig emailConfigBaseAdmin = emailConfigRepository.findByIdUsuario(usuarioAdmin.getId());
//		if (emailConfigBaseAdmin == null) {
//			throw new BusinessException(
//					String.format("O e-mail %s não possui configuração para envio", usuarioAdmin.getEmail()));
//		}
//
//		if (emailConfigBaseAdmin.getTipoServidorEmail() == TipoServidorEmail.GMAIL) {
//			try {
//				configurarEmailGmail(emailRegistro, emailConfigBaseAdmin);
//				emailRegistro.setStatusEmail(StatusEmail.ENVIADO.getId());			
//				emailRegistroRepository.save(emailRegistro);
//				return ResponseEntity.ok(emailRegistro);
//			} catch (Exception e) {
//				emailRegistro.setStatusEmail(StatusEmail.ERRO.getId());			
//				emailRegistroRepository.save(emailRegistro);
//				return ResponseEntity.badRequest().build();
//			}
//
//		}
//
//		if (emailConfigBaseAdmin.getTipoServidorEmail() == TipoServidorEmail.HOTMAIL) {
//			try {
//				configurarEmailHotmail(emailRegistro, emailConfigBaseAdmin);
//				emailRegistro.setStatusEmail(StatusEmail.ENVIADO.getId());			
//				emailRegistroRepository.save(emailRegistro);
//				return ResponseEntity.ok(emailRegistro);
//			} catch (Exception e) {
//				emailRegistro.setStatusEmail(StatusEmail.ERRO.getId());			
//				emailRegistroRepository.save(emailRegistro);
//				return ResponseEntity.badRequest().build();
//			}
//		}
//		
//		throw new BusinessException(String.format("O tipo de servidor e-mail %s não é permitido", emailConfigBaseAdmin.getTipoServidorEmail()));
//	}
//
//	private void configurarEmailGmail(EmailRegistro emailRegistro, EmailConfig emailConfig) throws AddressException, MessagingException {
//		Properties props = new Properties();
//		props.put("mail.smtp.auth", "true");
//		props.put("mail.smtp.starttls.enable", "true");
//		props.put("mail.smtp.host", "smtp.gmail.com");
//		props.put("mail.smtp.port", "587");
//		
//		enviarEmail(props, emailRegistro, emailConfig);
//	}
//
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
//
//	private void enviarEmail(Properties props, EmailRegistro emailRegistro, EmailConfig emailConfig) throws AddressException, MessagingException {
//		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
//			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication(emailConfig.getEmail(), emailConfig.getSenha());
//			}
//		});
//		
//		Message msg = new MimeMessage(session);
//		msg.setFrom(new InternetAddress(emailConfig.getEmail(), false));
//		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailRegistro.getEmailUsuarioDestinatario()));
//		msg.setSubject(emailRegistro.getTituloEmail());
//		msg.setContent(emailRegistro.getTextoEmail(), "text/html");
//		msg.setSentDate(new Date());
//
//		Transport.send(msg);
//	}
}
