package com.rwarquitetura.api.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rwarquitetura.api.exception.BusinessException;
import com.rwarquitetura.api.model.LevantamentoBriefing;
import com.rwarquitetura.api.model.Projeto;
import com.rwarquitetura.api.repository.ClienteSecundarioRepository;
import com.rwarquitetura.api.repository.LevantamentoBriefingRepository;
import com.rwarquitetura.api.repository.ProjetoRepository;

@RestController
@RequestMapping("/levantamentoBriefing")
public class LevantamentoBriefingController {

	@Value("${rwarquitetura.path.files.briefing}")
	private String levantamentoBriefingFolder;

	@Autowired
	private LevantamentoBriefingRepository levantamentoBriefingRepository;
	
	@Autowired
	private ProjetoRepository projetoRepository;
	
	@Autowired
	private ClienteSecundarioRepository clienteSecundarioRepository;

	@PostMapping( "/upload")
	public void upload(@RequestParam("arquivo") MultipartFile arquivo, @RequestParam("idProjeto")  int idProjeto, 
			@RequestParam("dataHoraInicioBriefing")  String  dataHoraInicioBriefing, @RequestParam("dataHoraFimBriefing")  String dataHoraFimBriefing, 
			@RequestParam("nomeBriefing")  String nomeBriefing) throws Exception {

		try {
			LevantamentoBriefing briefing = new LevantamentoBriefing();
			Projeto projeto = projetoRepository.getOne(idProjeto);
			briefing.setIdProjeto(projeto.getId());
			briefing.setIdClienteSecundario(projeto.getClienteSecundario().getId());
			briefing.setIdArquiteto(projeto.getClienteSecundario().getArquiteto().getId());
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"); 
			LocalDateTime localDateTimeInicio = LocalDateTime.parse(dataHoraInicioBriefing, formatter);
			LocalDateTime localDateTimeFim= LocalDateTime.parse(dataHoraFimBriefing, formatter);
			briefing.setDhTrabalhadaInicio(localDateTimeInicio);
			briefing.setDhTrabalhadaFim(localDateTimeFim);
			String horaTrabalhada = getDuracao(localDateTimeInicio, localDateTimeFim);
			briefing.setHrTrabalhada(horaTrabalhada);
			
			File arquivoSalvo = salvarNoDisco(arquivo);
			briefing.setCaminhoArquivo(arquivoSalvo.getAbsolutePath());
			briefing.setNomeArquivo(nomeBriefing);
			briefing.setTamanhoArquivo(arquivoSalvo.length());
			briefing.setFlArquivoPresente(true);
			briefing.setDhCadastro(LocalDateTime.now());
			levantamentoBriefingRepository.save(briefing);
		} catch (Exception e) {
			throw new BusinessException(String.format("Erro ao gravar Briefing %s", e.getMessage()));
		}
	}
	
	public String getDuracao(LocalDateTime localDateTimeInicio, LocalDateTime localDateTimeFim) {
		long difInMinutes = ChronoUnit.MINUTES.between(localDateTimeInicio, localDateTimeFim);
		
		Integer hours = (int) difInMinutes / 60;
		Integer rest = (int) difInMinutes % 60;

		String duracao;
		
		if (hours < 10 && rest >= 10) {
			String parseHours = "0" + hours.toString();
			duracao = parseHours + ":" + rest;
		}
		else if (hours < 10 && rest < 10) {
			String parseHours = "0" + hours.toString();
			String parseRest = "0" + rest.toString();
			duracao = parseHours + ":" + parseRest;
		} 
		else if (hours >= 10 && rest < 10) {
			String parseRest = "0" + rest.toString();
			duracao = hours + ":" + parseRest;
		} 
		else {
			duracao = hours + ":" + rest;
		}
		
	    return duracao;
	}

	public File salvarNoDisco(MultipartFile webFile) throws Exception {
		File diretorio = new File(levantamentoBriefingFolder);
		if (!diretorio.exists()){
			throw new Exception(String.format("Diretorio nao encontrado - Path %s", levantamentoBriefingFolder));
		}
		
		Path folderPath = Paths.get(levantamentoBriefingFolder);
		Path newFilePath = folderPath.resolve(webFile.getOriginalFilename());
		Files.copy(webFile.getInputStream(), newFilePath, StandardCopyOption.REPLACE_EXISTING);
		return newFilePath.toFile();
	}

	@GetMapping("/projeto/{id}")
	public List<LevantamentoBriefing> buscarPorProjeto(@PathVariable Integer id) {
		return levantamentoBriefingRepository.findByIdProjeto(id);
	}
}