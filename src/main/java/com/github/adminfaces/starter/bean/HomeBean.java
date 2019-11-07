package com.github.adminfaces.starter.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;

import com.github.adminfaces.starter.model.Reserva;
import com.github.adminfaces.starter.model.enums.StatusReserva;
import com.github.adminfaces.starter.security.Seguranca;
import com.github.adminfaces.starter.security.model.UsuarioSistema;
import com.github.adminfaces.starter.service.ReservaService;
import com.github.adminfaces.starter.util.FacesUtil;
import com.github.adminfaces.starter.util.ScheduleUtil;

@Named
@ViewScoped
public class HomeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired(required = false)
	private Seguranca seguranca;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private ReservaService service;
	private Reserva reserva;
	private List<Reserva> listaReservas;
	private ScheduleModel reservas;
	private ScheduleEvent event;
	private Date hoje;
	private UsuarioSistema sistema;

	@PostConstruct
	public void inicializar() {
		try {
			if (SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser") {
				sistema = (UsuarioSistema) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			}
			
			setHoje(new Date());
			reservas = new DefaultScheduleModel();
			reserva = new Reserva();
			if (sistema != null) {
				reserva.setUsuario(sistema.getUsuario());
			}

			listaReservas = service.todos();

			for (Reserva reserva : listaReservas) {
				DefaultScheduleEvent evt = new DefaultScheduleEvent();
				evt.setStyleClass(ScheduleUtil.getClass(reserva.getStatusReserva()));
				evt.setEndDate(reserva.getDataFinal());
				evt.setStartDate(reserva.getDataInicial());
				evt.setDescription(reserva.getDescricao());
				evt.setTitle(reserva.getUsuario().getNome());
				reservas.addEvent(evt);
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.addFatalMessage("Erro FATAL " + e.getMessage());
		}
	}

	public void onDateSelect(SelectEvent selectEvent) {
		reserva = new Reserva();
		event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
		reserva.setUsuario(seguranca.getUsuarioLogado().getUsuario());
		reserva.setDataInicial(event.getStartDate());
		reserva.setDataFinal(event.getEndDate());
	}

	public void onEventSelect(SelectEvent selectEvent) {
		event = (DefaultScheduleEvent) selectEvent.getObject();
		listaReservas.forEach(e -> {
			if (event.getDescription() == e.getDescricao() && event.getTitle().equals(e.getUsuario().getNome())
					&& event.getStartDate().equals(e.getDataInicial()) && event.getEndDate().equals(e.getDataFinal())) {
				reserva = e;
			}
		});
	}

	public void salvarEvento(ActionEvent actionEvent) {
		try {
			service.salvar(reserva);
			publisher.publishEvent(reserva);
			FacesUtil.addInfoMessage("Reserva Atualizada com sucesso!!!");
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.addFatalMessage(e.getMessage());
		}
	}

	public void removerEvento(ActionEvent actionEvent) {
		try {
			service.remover(reserva);
			reserva.setStatusReserva(StatusReserva.EXCUIDO);
			publisher.publishEvent(reserva);
			inicializar();
			FacesUtil.addInfoMessage("Reserva Excluida com sucesso!!!");
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.addFatalMessage(e.getMessage());
		}
	}

	public boolean podeRemoverEvento() {
		if (reserva.getCodigo() != null) {
			if (sistema.getUsuario().isSindico()) {
				return true;
			} else if (reserva.getStatusReserva() != StatusReserva.CONCLUIDO
					&& reserva.getUsuario().getNome().equals(sistema.getUsuario().getNome())) {
				return true;
			}

		}
		return false;
	}

	public boolean habilitarDescricao() {
		if (reserva.getDescricao() == null) {
			return false;
		} else {
			return true;
		}
	}

	public void novoEvento() {
		FacesUtil.redirecionarPagina("cadastroEvento.xhtml");
	}

	public void redicionaCadastroUsuario() {
		FacesUtil.redirecionarPagina(
				"cadastroUsuario.xhtml?usuario=" + seguranca.getUsuarioLogado().getUsuario().getCodigo());
	}

	public StatusReserva[] getstatusReservas() {
		return StatusReserva.values();
	}

	public ScheduleModel getReservas() {
		return reservas;
	}

	public void setReservas(ScheduleModel reservas) {
		this.reservas = reservas;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	public List<Reserva> getListaReservas() {
		return listaReservas;
	}

	public void setListaReservas(List<Reserva> listaReservas) {
		this.listaReservas = listaReservas;
	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	public Date getHoje() {
		return hoje;
	}

	public void setHoje(Date hoje) {
		this.hoje = hoje;
	}

}
