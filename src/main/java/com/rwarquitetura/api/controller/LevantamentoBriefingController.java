package com.rwarquitetura.api.controller;

import com.rwarquitetura.api.exception.BusinessException;
import com.rwarquitetura.api.model.LevantamentoBriefing;
import com.rwarquitetura.api.model.Projeto;
import com.rwarquitetura.api.repository.ClienteSecundarioRepository;
import com.rwarquitetura.api.repository.LevantamentoBriefingRepository;
import com.rwarquitetura.api.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

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
			Projeto projeto = projetoRepository.getOne(idProjeto);
			if(!arquivo.getOriginalFilename().endsWith("pdf")) {
				throw new BusinessException("Suporte somente para arquivo PDF");
			}

			File arquivoSalvo = salvarNoDisco(arquivo);
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"); 
			LocalDateTime localDateTimeInicio = LocalDateTime.parse(dataHoraInicioBriefing, formatter);
			LocalDateTime localDateTimeFim= LocalDateTime.parse(dataHoraFimBriefing, formatter);
			String horaTrabalhada = getDuracao(localDateTimeInicio, localDateTimeFim);

			LevantamentoBriefing briefing = new LevantamentoBriefing(projeto, arquivoSalvo);
			briefing.setDhTrabalhadaInicio(localDateTimeInicio);
			briefing.setDhTrabalhadaFim(localDateTimeFim);
			briefing.setHrTrabalhada(horaTrabalhada);
			briefing.setNome(nomeBriefing);

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

	@PostMapping("/remover/{id}")
	public void remover(@PathVariable Integer id) {
		LevantamentoBriefing levantamentoBriefing = levantamentoBriefingRepository.getOne(id);
		levantamentoBriefingRepository.delete(levantamentoBriefing);
	}

	@GetMapping("/download/{id}")
	public void donwload(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		LevantamentoBriefing levantamentoBriefing = levantamentoBriefingRepository.getOne(id);
		File file = new File(levantamentoBriefing.getCaminhoArquivo());
		if (file.exists()) {
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}

			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
			response.setContentLength((int) file.length());

			InputStream inputStream = new BufferedInputStream(Files.newInputStream(file.toPath()));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		}
	}
}