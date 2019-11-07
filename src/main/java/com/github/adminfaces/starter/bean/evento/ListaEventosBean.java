package com.github.adminfaces.starter.bean.evento;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.github.adminfaces.starter.model.Reserva;
import com.github.adminfaces.starter.model.enums.StatusReserva;
import com.github.adminfaces.starter.security.model.UsuarioSistema;
import com.github.adminfaces.starter.service.EmailService;
import com.github.adminfaces.starter.service.ReservaService;
import com.github.adminfaces.starter.util.FacesUtil;

@Named
@ViewScoped
public class ListaEventosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ReservaService service;

	@Autowired
	private EmailService emailService;
	private UsuarioSistema usuarioSistema;
	private List<Reserva> todosEventos;

	@PostConstruct
	public void inicializar() {
		usuarioSistema = (UsuarioSistema) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		consultar(usuarioSistema);
	}

	public void excluir(Reserva reserva) {
		if (reserva.getStatusReserva() == StatusReserva.CONCLUIDO || usuarioSistema.getUsuario().equals(reserva.getUsuario())) {
			service.remover(reserva);
			FacesUtil.addInfoMessage("Reserva excluído com sucesso!");
			consultar(usuarioSistema);
		} else {
			FacesUtil.addErrorMessage("O Status da reserva não permite a exclução!");
		}
	}

	public void salvarEvento(Reserva reserva) {
		try {
			service.salvar(reserva);
			emailService.enviar(reserva);
			FacesUtil.addInfoMessage("Reserva Atualizada com sucesso!!!");
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.addFatalMessage(e.getMessage());
		}
	}

	public StatusReserva[] getStatusReserva() {
		return StatusReserva.values();
	}

	public void consultar(UsuarioSistema usuarioSistema) {
		if (usuarioSistema.getUsuario().isSindico()) {
			setTodosEventos(service.todos());
		} else {
			setTodosEventos(service.evetosPorUsuario(usuarioSistema.getUsuario()));
		}

	}

	public List<Reserva> getTodosEventos() {
		return todosEventos;
	}

	public void setTodosEventos(List<Reserva> todosEventos) {
		this.todosEventos = todosEventos;
	}

}
