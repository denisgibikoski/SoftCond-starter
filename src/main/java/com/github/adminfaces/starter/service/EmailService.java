package com.github.adminfaces.starter.service;

import org.springframework.stereotype.Service;

import com.github.adminfaces.starter.model.Reserva;
import com.github.adminfaces.starter.model.UnidadeMoradia;
import com.github.adminfaces.starter.model.Usuario;

@Service
public interface EmailService {
	
	void enviar( Reserva reserva);

	void enviarNovoUsuario(Usuario usuario);

	void enviarSindico(Reserva reserva);

	void enviarMoradia(UnidadeMoradia moradia);

	void enviarSenhaNova(Usuario usuario, String senha);

}
