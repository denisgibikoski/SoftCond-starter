package com.github.adminfaces.starter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.adminfaces.starter.model.UnidadeMoradia;
import com.github.adminfaces.starter.model.Usuario;

@Service
public interface UnidadeMoradiaService {

	List<UnidadeMoradia> todos();
	
	UnidadeMoradia porId(Long id);

	UnidadeMoradia porUsurio(Usuario usuario);

	UnidadeMoradia salvar(UnidadeMoradia moradia);
	
}
