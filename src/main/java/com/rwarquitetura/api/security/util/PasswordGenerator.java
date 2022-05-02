package com.rwarquitetura.api.security.util;

import java.text.Normalizer;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("123456"));
		try {
			String teste = removeAcentos("DANIELE MÚLTIPLO  FUNDO DE INV");
			System.out.println(teste);
			System.out.println("DANIELE MÚLTIPLO  FUNDO DE INV");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String removeAcentos(String str) {
		str = Normalizer.normalize(str, Normalizer.Form.NFD);
		str = str.replaceAll("[^\\p{ASCII}]", "");
		return str;

	}
}
