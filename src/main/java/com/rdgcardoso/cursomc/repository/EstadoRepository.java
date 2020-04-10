package com.rdgcardoso.cursomc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rdgcardoso.cursomc.model.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer>{
	
}
