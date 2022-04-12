package com.rwarquitetura.api.util;

import java.io.BufferedReader;
import java.io.IOException;

public class Converte {
	public static String converteJsonEmString(BufferedReader buffereReader) throws IOException {
        String resposta, jsonEmString = "";
        while ((resposta = buffereReader.readLine()) != null) {
            jsonEmString += resposta;
        }
        return jsonEmString;
	}
}
