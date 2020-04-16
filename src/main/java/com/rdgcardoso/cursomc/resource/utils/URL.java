package com.rdgcardoso.cursomc.resource.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {
	
	// Exemplo: id "1,2,3" para 1 2 3
	public static List<Integer> decodeIntList(String s){	
		
		String[] vet = s.split(",");		
		List<Integer> list = new ArrayList<>();
		
		for(int i=0; i<vet.length; i++) {
			list.add(Integer.parseInt(vet[i]));
		}
		return list;
		//return Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
	}
	
	// Exemplo: "TV%20LED" para "TV LED"
	public static String decodeParam (String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
}
