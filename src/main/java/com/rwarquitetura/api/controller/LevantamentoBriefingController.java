package com.rwarquitetura.api.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rwarquitetura.api.exception.BusinessException;
import com.rwarquitetura.api.model.LevantamentoBriefing;
import com.rwarquitetura.api.repository.LevantamentoBriefingRepository;

@RestController
@RequestMapping("briefing")
public class LevantamentoBriefingController {	
	
	@Value("${rwarquitetura.path.files.briefing}")
	private String levantamentoBriefingFolder;

	@Autowired
	private LevantamentoBriefingRepository levantamentoBriefingRepository;

	@GetMapping
	public List<LevantamentoBriefing> buscar() {
		return levantamentoBriefingRepository.findAll();
	}
//	{
//		"idUsuario": 1,
//		"idArquiteto": 1,
//		"idClienteSecundario": 1,
//		"dhTrabalhadaInicio": "2018-07-14T17:45:55.9483536",
//		"dhTrabalhadaFim": "2018-07-14T17:45:55.9483536",
//		"observacao": "Testando e-mail"
//	}

	@RequestMapping(method = RequestMethod.POST, path = "uploadLevantamentoBriefing", produces = { MediaType.APPLICATION_JSON_VALUE })
	public Boolean uploadBriefing(
			@RequestParam("idUsuario") @DateTimeFormat(pattern = "hh:mma") Integer idUsuario,
			@RequestParam("idArquiteto") Integer idArquiteto,
			@RequestParam("idClienteSecundario") Integer idClienteSecundario,
			@RequestParam("dhTrabalhadaInicio") @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime dhTrabalhadaInicio,
			@RequestParam("dhTrabalhadaFim") @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime dhTrabalhadaFim,
			@RequestParam("observacao") String observacao,
			@RequestParam("arquivos") MultipartFile arquivo) throws Exception {
		
		try{
			LevantamentoBriefing briefing = new LevantamentoBriefing();
			briefing.setIdUsuario(idUsuario);
			briefing.setIdArquiteto(idArquiteto);
			briefing.setIdClienteSecundario(idClienteSecundario);
			briefing.setDhTrabalhadaInicio(dhTrabalhadaInicio);
			briefing.setDhTrabalhadaFim(dhTrabalhadaFim);
			briefing.setObservacao(observacao);
			briefing.setDhCadastro(LocalDateTime.now());
			briefing.setFlArquivoPresente(true);

			/*Preparar arquivo*/
			File arquivoUploaded = saveOnDisk(arquivo);
			briefing.setCaminhoArquivo(arquivoUploaded.getAbsolutePath());
			briefing.setNomeArquivo(arquivoUploaded.getName());
			briefing.setTamanhoArquivo(arquivoUploaded.length() / 1024);
			
			levantamentoBriefingRepository.save(briefing);
			return Boolean.TRUE;
		}catch(Exception e){
			throw new BusinessException(String.format("Erro ao gravar Briefing %s", e.getMessage()));
		}
	}
	
	public File saveOnDisk(MultipartFile webFile) throws Exception {
		Path folderPath = Paths.get(levantamentoBriefingFolder);
		Path newFilePath = folderPath.resolve(webFile.getOriginalFilename());
		Files.copy(webFile.getInputStream(), newFilePath, StandardCopyOption.REPLACE_EXISTING);
		return newFilePath.toFile();
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_LEVANTAMENTO_BRIEFING')")
	public ResponseEntity<LevantamentoBriefing> buscarPorId(@PathVariable Integer id) {
		
		LevantamentoBriefing levantamentoBriefing = levantamentoBriefingRepository.findOne(id);
		if (levantamentoBriefing == null) {
			return ResponseEntity.notFound().build();
		} 

		return ResponseEntity.ok(levantamentoBriefing);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_LEVANTAMENTO_BRIEFING')")
	public ResponseEntity<LevantamentoBriefing> remover(@PathVariable Integer id) {
		
		LevantamentoBriefing levantamentoBriefing = levantamentoBriefingRepository.findOne(id);
		if (levantamentoBriefing == null) {
			return ResponseEntity.notFound().build();
		} 

		levantamentoBriefingRepository.delete(id);
		return ResponseEntity.ok().build();
	}

}
