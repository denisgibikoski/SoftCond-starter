package com.github.adminfaces.starter.security.model;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.github.adminfaces.starter.model.Usuario;


public class UsuarioSistema extends User {

	private static final long serialVersionUID = 1L;

	@Autowired
	private Usuario usuario;

	public UsuarioSistema(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
		super(usuario.getEmail(), usuario.getPassword(), authorities);
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

}
