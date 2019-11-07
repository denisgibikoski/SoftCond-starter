package com.github.adminfaces.starter.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.adminfaces.starter.model.UnidadeMoradia;
import com.github.adminfaces.starter.model.Usuario;
import com.github.adminfaces.starter.repository.UnidadeMoradiaRepository;
import com.github.adminfaces.starter.service.UnidadeMoradiaService;

@Service
public class UnidadeMoradiaServiceImpl implements UnidadeMoradiaService {

	@Autowired
	private UnidadeMoradiaRepository repository;

	@Override
	public List<UnidadeMoradia> todos() {
		return repository.findAll();
	}

	@Override
	public UnidadeMoradia porUsurio(Usuario usuario) {
		return repository.findByUsuario(usuario);
	}

	@Override
	@Transactional
	public UnidadeMoradia salvar(UnidadeMoradia moradia) {
		return repository.saveAndFlush(moradia);
	}

	@Override
	public UnidadeMoradia porId(Long id) {
		return repository.findById(id).orElse(null);
	}

}
