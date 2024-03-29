package com.github.adminfaces.starter.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.github.adminfaces.starter.model.Usuario;
import com.github.adminfaces.starter.model.enums.StatusCadastro;
import com.github.adminfaces.starter.repository.UsuarioRepository;
import com.github.adminfaces.starter.security.model.Permissao;
import com.github.adminfaces.starter.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepositorio;

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Override
	public List<Usuario> todos() {
		return usuarioRepositorio.findAll();
	}

	@Override
	@Transactional
	public Usuario salvar(Usuario usuario) {
		if (usuario.getCodigo() == null) {
			if (usuario.getMoradia().getUnidade().equals(000)) {
				usuario.getMoradia().setStatusUnidadeMoradia(StatusCadastro.INATIVO);
			} else {
				usuario.getMoradia().setStatusUnidadeMoradia(StatusCadastro.PEDENTE);
			}
		}
		usuario.setPermissao(getPermissao(usuario));
		usuario.setPassword(encoder.encode(usuario.getPassword()));
		return usuarioRepositorio.saveAndFlush(usuario);
	}

	private Permissao getPermissao(Usuario usuario) {
		if (usuario.isSindico()) {
			return Permissao.SINDICO;
		} else {
			return Permissao.USER;
		}
	}

	@Override
	public Usuario porId(Long id) {
		return usuarioRepositorio.findById(id).orElse(new Usuario());
	}

	@Override
	public void delete(Usuario usuario) {
		usuarioRepositorio.delete(usuario);
	}

	@Override
	public Usuario porEmail(String email) {
		return usuarioRepositorio.findByEmail(email);
	}

	@Override
	public Usuario getSindico() {
		Usuario usuario = usuarioRepositorio.findBySindicoTrue();
		if (usuario != null) {
			return usuario;
		} else {
			return new Usuario();
		}
	}

	@Override
	@Transactional
	public void redefinirSenha(Usuario usuario) {
		usuario.setPassword(encoder.encode(usuario.getPassword()));
		usuarioRepositorio.saveAndFlush(usuario);
	}

	@Override
	public boolean existeSindico() {
		return usuarioRepositorio.findBySindico();
	}

}
