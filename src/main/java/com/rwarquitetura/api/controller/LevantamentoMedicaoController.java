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
import com.rwarquitetura.api.model.LevantamentoMedicao;
import com.rwarquitetura.api.model.Projeto;
import com.rwarquitetura.api.repository.LevantamentoMedicaoRepository;
import com.rwarquitetura.api.repository.ProjetoRepository;

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
			LevantamentoMedicao medicao = new LevantamentoMedicao();
			Projeto projeto = projetoRepository.getOne(idProjeto);
			medicao.setIdProjeto(projeto.getId());
			medicao.setIdClienteSecundario(projeto.getClienteSecundario().getId());
			medicao.setIdArquiteto(projeto.getClienteSecundario().getArquiteto().getId());
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"); 
			LocalDateTime localDateTimeInicio = LocalDateTime.parse(dataHoraInicioMedicao, formatter);
			LocalDateTime localDateTimeFim= LocalDateTime.parse(dataHoraFimMedicao, formatter);
			medicao.setDhTrabalhadaInicio(localDateTimeInicio);
			medicao.setDhTrabalhadaFim(localDateTimeFim);
			String horaTrabalhada = getDuracao(localDateTimeInicio, localDateTimeFim);
			medicao.setHrTrabalhada(horaTrabalhada);
			
			File arquivoSalvo = salvarNoDisco(arquivo);
			medicao.setCaminhoArquivo(arquivoSalvo.getAbsolutePath());
			medicao.setNomeArquivo(nomeMedicao);
			medicao.setTamanhoArquivo(arquivoSalvo.length());
			medicao.setFlArquivoPresente(true);
			medicao.setDhCadastro(LocalDateTime.now());
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
		List<LevantamentoMedicao> medicao = levantamentoMedicaoRepository.findByIdProjeto(id);
		return medicao;
	}
}