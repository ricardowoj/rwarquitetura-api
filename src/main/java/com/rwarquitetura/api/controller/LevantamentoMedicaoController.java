package com.rwarquitetura.api.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.rwarquitetura.api.model.LevantamentoBriefing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rwarquitetura.api.exception.BusinessException;
import com.rwarquitetura.api.model.LevantamentoMedicao;
import com.rwarquitetura.api.model.Projeto;
import com.rwarquitetura.api.repository.LevantamentoMedicaoRepository;
import com.rwarquitetura.api.repository.ProjetoRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/levantamentoMedicao")
public class LevantamentoMedicaoController {

	@Value("${rwarquitetura.path.files.medicao}")
	private String levantamentoMedicaoFolder;

	@Autowired
	private LevantamentoMedicaoRepository levantamentoMedicaoRepository;
	
	@Autowired
	private ProjetoRepository projetoRepository;

	@PostMapping( "/upload")
	public void upload(@RequestParam("arquivo") MultipartFile arquivo, @RequestParam("idProjeto")  int idProjeto, 
			@RequestParam("dataHoraInicioMedicao")  String  dataHoraInicioMedicao, @RequestParam("dataHoraFimMedicao")  String dataHoraFimMedicao, 
			@RequestParam("nomeMedicao")  String nomeMedicao) throws Exception {

		try {
			Projeto projeto = projetoRepository.getOne(idProjeto);
			if(!arquivo.getOriginalFilename().endsWith("pdf")) {
				throw new BusinessException("| Suporte somente para arquivo PDF");
			}

			File arquivoSalvo = salvarNoDisco(arquivo);
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"); 
			LocalDateTime localDateTimeInicio = LocalDateTime.parse(dataHoraInicioMedicao, formatter);
			LocalDateTime localDateTimeFim= LocalDateTime.parse(dataHoraFimMedicao, formatter);
			String horaTrabalhada = getDuracao(localDateTimeInicio, localDateTimeFim);

			LevantamentoMedicao medicao = new LevantamentoMedicao(projeto, arquivoSalvo);
			medicao.setDhTrabalhadaInicio(localDateTimeInicio);
			medicao.setDhTrabalhadaFim(localDateTimeFim);

			medicao.setHrTrabalhada(horaTrabalhada);
			medicao.setDhTrabalhadaInicio(localDateTimeInicio);
			medicao.setDhTrabalhadaFim(localDateTimeFim);
			medicao.setHrTrabalhada(horaTrabalhada);
			medicao.setNome(nomeMedicao);

			levantamentoMedicaoRepository.save(medicao);
		} catch (Exception e) {
			throw new BusinessException(String.format("Erro ao gravar Medição %s", e.getMessage()));
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
		File diretorio = new File(levantamentoMedicaoFolder);
		if (!diretorio.exists()){
			throw new Exception(String.format("Diretorio nao encontrado - Path %s", levantamentoMedicaoFolder));
		}
		
		Path folderPath = Paths.get(levantamentoMedicaoFolder);
		Path newFilePath = folderPath.resolve(webFile.getOriginalFilename());
		Files.copy(webFile.getInputStream(), newFilePath, StandardCopyOption.REPLACE_EXISTING);
		return newFilePath.toFile();
	}
	
	@GetMapping("/projeto/{id}")
	public List<LevantamentoMedicao> buscarPorProjeto(@PathVariable Integer id) {
		return levantamentoMedicaoRepository.findByIdProjeto(id);
	}

	@PostMapping("/remover/{id}")
	public void remover(@PathVariable Integer id) {
		LevantamentoMedicao levantamentoMedicao = levantamentoMedicaoRepository.getOne(id);
		levantamentoMedicaoRepository.delete(levantamentoMedicao);
	}

	@GetMapping("/download/{id}")
	public void donwload(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		LevantamentoMedicao levantamentoMedicao = levantamentoMedicaoRepository.getOne(id);
		File file = new File(levantamentoMedicao.getCaminhoArquivo());
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