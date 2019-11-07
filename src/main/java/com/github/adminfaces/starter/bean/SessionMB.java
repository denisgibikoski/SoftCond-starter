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
	
	private String sindico;
	
	private UsuarioSistema usuarioSistema;
	

	@PostConstruct
	public void init() {
		currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		usuarioSistema = (UsuarioSistema) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		statusUsuario = usuarioSistema.getUsuario().getMoradia().getStatusUnidadeMoradia().getDescricao();
		sindico = usuarioSistema.getUsuario().getPermissao().toString();
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

	public String getSindico() {
		return sindico;
	}

	public void setSindico(String sindico) {
		this.sindico = sindico;
	}

	public UsuarioSistema getUsuarioSistema() {
		return usuarioSistema;
	}

	public void setUsuarioSistema(UsuarioSistema usuarioSistema) {
		this.usuarioSistema = usuarioSistema;
	}
}
