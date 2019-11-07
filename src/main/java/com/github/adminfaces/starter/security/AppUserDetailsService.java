package com.github.adminfaces.starter.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.adminfaces.starter.model.Usuario;
import com.github.adminfaces.starter.security.model.UsuarioSistema;
import com.github.adminfaces.starter.service.UsuarioService;

@Service
public class AppUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UsuarioService usuarioService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
				
		Usuario usuario = usuarioService.porEmail(email);

		UsuarioSistema user = null;

		if (usuario != null) {
			user = new UsuarioSistema(usuario, getGrupos(usuario));
		} else {
			throw new UsernameNotFoundException("Usuário não encontrado.");
		}

		return user;
	}

	private Collection<? extends GrantedAuthority> getGrupos(Usuario usuario) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();

		authorities.add(new SimpleGrantedAuthority("ROLE_" + usuario.getPermissao()));

		return authorities;
	}
	
}
