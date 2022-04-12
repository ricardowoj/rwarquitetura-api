package com.rwarquitetura.api.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.rwarquitetura.api.dto.EnderecoDTO;

public class Api {
	
	public static EnderecoDTO buscarCep(String cepDTO) throws Exception {
	    String webService = "https://viacep.com.br/ws/";
	    int codigoSucesso = 200;
	    
	    String urlParaChamada = webService + cepDTO + "/json";
		
	    try {
            URL url = new URL(urlParaChamada);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

            if (conexao.getResponseCode() != codigoSucesso)
                throw new RuntimeException("HTTP error code : " + conexao.getResponseCode());

            BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));
            String jsonEmString = Converte.converteJsonEmString(resposta);

            Gson gson = new Gson();
            EnderecoDTO endereco = gson.fromJson(jsonEmString, EnderecoDTO.class);

            return endereco;
        } catch (Exception e) {
            throw new Exception("ERRO: " + e);
        }
	}
}
