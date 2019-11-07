package com.github.adminfaces.starter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.adminfaces.starter.model.Usuario;

@Service
public interface UsuarioService {

	List<Usuario> todos();

	Usuario salvar(Usuario usuario);

	Usuario porId(Long id);

	void delete(Usuario usuario);

	Usuario porEmail(String email);

	Usuario getSindico();

	void redefinirSenha(Usuario usuario);

}
