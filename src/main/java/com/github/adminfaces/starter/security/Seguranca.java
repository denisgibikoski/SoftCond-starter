package com.github.adminfaces.starter.security;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.github.adminfaces.starter.security.model.UsuarioSistema;

@Named
@RequestScoped
public class Seguranca {

	@Autowired(required = false)
	UsuarioSistema usuario;

	public String getNomeUsuario() {
		String nome = null;

		UsuarioSistema usuarioLogado = getUsuarioLogado();

		if (usuarioLogado != null) {
			nome = usuarioLogado.getUsuario().getNome();
		}

		return nome;
	}


	public UsuarioSistema getUsuarioLogado() {
		UsuarioSistema usuario = null;

		if (SecurityContextHolder.getContext() != null) {

			Authentication auth = (Authentication) SecurityContextHolder.getContext().getAuthentication();

			if (auth != null && auth.getPrincipal() != null) {
				usuario = (UsuarioSistema) auth.getPrincipal();
			}
		}
		return usuario;
	}

}
