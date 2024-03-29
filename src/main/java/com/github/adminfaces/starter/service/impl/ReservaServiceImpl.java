package com.github.adminfaces.starter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.adminfaces.starter.model.Reserva;
import com.github.adminfaces.starter.model.Usuario;
import com.github.adminfaces.starter.model.enums.StatusCadastro;
import com.github.adminfaces.starter.model.enums.TipoEvento;
import com.github.adminfaces.starter.repository.ReservaRepository;
import com.github.adminfaces.starter.service.ReservaService;
import com.github.adminfaces.starter.service.UsuarioService;
import com.github.adminfaces.starter.util.NegocioException;
import com.github.adminfaces.starter.util.RestricaoHorario;

@Service
public class ReservaServiceImpl implements ReservaService {

	@Autowired
	private ReservaRepository reservaRepository;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public List<Reserva> todos() {
		return reservaRepository.findAll();
	}

	@Override
	public void remover(Reserva reserva)throws NegocioException {
			RestricaoHorario.permite(reserva.getDataFinal());
			reservaRepository.delete(reserva);
	}

	@Override
	@Transactional
	public void salvar(Reserva reserva) {

		Usuario usuario = usuarioService.porId(reserva.getUsuario().getCodigo());
		verificaUsuarioAtivo(usuario);
		if (reserva.getCodigo() == null) {
			RestricaoHorario.verificaHorarioReserva(reserva ,reservaRepository.findAll() );
		}
		verificaOpcaoOutro(reserva);
		reserva.setUsuario(usuario);
		reservaRepository.saveAndFlush(reserva);
	}

	private void verificaOpcaoOutro(Reserva reserva) {
		if (reserva.getTipoevento().equals(TipoEvento.OUTROS) && reserva.getDescricao().isEmpty()) {
			throw new NegocioException("Quando o tipo de evento e 'Outro' \n Deverá comter a descrição do evento!!");
		}
		
	}

	private void verificaUsuarioAtivo(Usuario usuario) {
		if (usuario.getStatusUsuario() != StatusCadastro.ATIVO) {
			throw new NegocioException("Usuario nao Ativo, procuro o Administrador do sistema !!!");
		}
	}

	@Override
	public Reserva porId(Long id) {
		return reservaRepository.findById(id).orElse(new Reserva());
	}

	@Override
	public List<Reserva> evetosPorUsuario(Usuario usuario) {
		return reservaRepository.findByUsuario(usuario);
	}

}
