package com.github.adminfaces.starter.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.springframework.security.core.context.SecurityContextHolder;

import com.github.adminfaces.starter.security.model.UsuarioSistema;

@Named
@ViewScoped
public class SessionMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String currentUser;
	
	private String statusUsuario;
	

	@PostConstruct
	public void init() {
		currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		UsuarioSistema usuarioSistema = (UsuarioSistema) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		statusUsuario = usuarioSistema.getUsuario().getMoradia().getStatusUnidadeMoradia().getDescricao();
	}

	public String getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}

	public String getStatusUsuario() {
		return statusUsuario;
	}

	public void setStatusUsuario(String statusUsuario) {
		this.statusUsuario = statusUsuario;
	}
}
