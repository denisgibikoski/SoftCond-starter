package com.github.adminfaces.starter.service;

import org.springframework.stereotype.Service;

import com.github.adminfaces.starter.model.Usuario;

@Service
public interface UsuarioLogado {

	/**
	 * Retorna o usuário que se encontra na sessão.
	 * @return {@link Usuario}
	 */
	Usuario getUsuario();
}
