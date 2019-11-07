package com.github.adminfaces.starter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.adminfaces.starter.model.Reserva;
import com.github.adminfaces.starter.model.Usuario;
import com.github.adminfaces.starter.model.enums.StatusCadastro;
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
	public void remover(Reserva reserva) {
		RestricaoHorario.permite(reserva.getDataFinal());
		reservaRepository.delete(reserva);
	}

	@Override
	@Transactional
	public void salvar(Reserva reserva) {

		Usuario usuario = usuarioService.porId(reserva.getUsuario().getCodigo());
		verificaUsuarioAtivo(usuario);
		if (reserva.getCodigo() == null) {
			verificaHorarioReserva(reserva);
		}
		reserva.setUsuario(usuario);
		reservaRepository.saveAndFlush(reserva);
	}

	private void verificaHorarioReserva(Reserva reserva) {

		Long iniRESERVA = reserva.getDataInicial().getTime();
		Long fimRESERVA = reserva.getDataFinal().getTime();

		List<Reserva> reservas = reservaRepository.findAll();
		for (Reserva reserva2 : reservas) {

			Long iniRESERVA2 = reserva2.getDataInicial().getTime();
			Long fimRESERVA2 = reserva2.getDataFinal().getTime();

			if (iniRESERVA >= iniRESERVA2 && iniRESERVA <= fimRESERVA2) {
				throw new NegocioException("Ja Existe Reserva neste peiodo");
			}
			if (fimRESERVA >= iniRESERVA2 && fimRESERVA <= fimRESERVA2) {
				throw new NegocioException("Ja Existe Reserva neste peiodo");
			}
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
