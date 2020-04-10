package com.rdgcardoso.cursomc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rdgcardoso.cursomc.service.exception.ObjectNotFoundException;
import com.rdgcardoso.cursomc.model.Cliente;
import com.rdgcardoso.cursomc.repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
		
	public Cliente buscarPorId(Integer id) {
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		 "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	} 

}
