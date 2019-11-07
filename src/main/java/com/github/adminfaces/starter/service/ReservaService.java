package com.github.adminfaces.starter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.adminfaces.starter.model.Reserva;
import com.github.adminfaces.starter.model.Usuario;

@Service
public interface ReservaService {

	List<Reserva> todos();

	void remover(Reserva reserva);

	void salvar(Reserva reserva);

	Reserva porId(Long id);

	List<Reserva> evetosPorUsuario(Usuario usuario);
		
}
