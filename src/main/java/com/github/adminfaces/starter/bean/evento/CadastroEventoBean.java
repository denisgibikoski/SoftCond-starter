package com.github.adminfaces.starter.bean.evento;


import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;

import com.github.adminfaces.starter.model.Reserva;
import com.github.adminfaces.starter.model.enums.StatusReserva;
import com.github.adminfaces.starter.model.enums.TipoEvento;
import com.github.adminfaces.starter.security.model.UsuarioSistema;
import com.github.adminfaces.starter.service.ReservaService;
import com.github.adminfaces.starter.util.FacesUtil;
import com.github.adminfaces.starter.util.NegocioException;
import com.github.adminfaces.starter.util.RestricaoHorario;

@Named
@ViewScoped
public class CadastroEventoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ReservaService service;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	private Date hoje;
	private UsuarioSistema sistema;
	private Reserva reserva;

	@PostConstruct
	public void inicializar() {
		if (SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser") {
			setSistema((UsuarioSistema) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		}
		long aux = new Date().getTime() + RestricaoHorario.getRESTRICAO();
		Date tesDate = new Date();
		tesDate.setTime(aux);
		setHoje(tesDate);
		
		limpar();
		FacesContext fContext = FacesContext.getCurrentInstance();
		if (fContext != null) {
			HttpServletRequest request = (HttpServletRequest) fContext.getExternalContext().getRequest();
			if (request.getParameter("reserva") != null) {
				String url = request.getParameter("reserva");
				Long id = Long.valueOf(url);
				reserva = service.porId(id);
				request.removeAttribute("usuario");
			}
		}
	}
		
	public void removerEvento() {
		try {
			RestricaoHorario.permite(reserva.getDataFinal());
			service.remover(reserva);
			reserva.setStatusReserva(StatusReserva.EXCLUIDO);
			publisher.publishEvent(reserva);
			inicializar();
			FacesUtil.addInfoMessage("Reserva Excluida com sucesso!!!");
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.addFatalMessage(e.getMessage());
		}
	}
	
	public void clear() {
		inicializar();
	}
	
	
	public void novoEvento() {
		try {
			if (isTermoResposabilidade(reserva.getTermoDeUso())) {
				service.salvar(reserva);
				publisher.publishEvent(reserva);
				FacesUtil.addInfoMessage("Evento " + reserva.getCodigo() + "  salvo !!");
				FacesUtil.addInfoMessage("Unidade N°:  " + reserva.getUsuario().getMoradia().getUnidade() + "  salvo !!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage("ERRO :" + e.getMessage());
		}
	}

	private boolean isTermoResposabilidade(Boolean termoDeUso) {
		if (termoDeUso.booleanValue()) {
			return true;
		} else {
			throw new NegocioException("O Termo de Uso não foi Assinado");
		}
	}
	
	 public Boolean isNew() {
	        return reserva.getCodigo() == null;
	    }
	
	public void ativaTermo() {
		try {
			if (!reserva.getAssinatura().isEmpty()) {
				reserva.setTermoDeUso(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			new NegocioException("Sem Assinatura");
		}		
	}

	public StreamedContent termoDeUso() {
		InputStream stream = FacesContext.getCurrentInstance().getExternalContext()
				.getResourceAsStream("/resources/termo_de_responsabilidade.pdf");
		DefaultStreamedContent file = new DefaultStreamedContent(stream, "application/pdf",
				"termo_de_responsabilidade.pdf");
		return file;
	}

	private void limpar() {		
		reserva = new Reserva();
		if (FacesContext.getCurrentInstance() != null) {
			reserva.setUsuario(sistema.getUsuario());
		}
	}

	public TipoEvento[] getTipoEvento() {
		return TipoEvento.values();
	}
	
	public StatusReserva[] getStatusReserva() {
		return StatusReserva.values();
	}

	public boolean isEditando() {
		return reserva != null;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	public Date getHoje() {
		return hoje;
	}

	public void setHoje(Date hoje) {
		this.hoje = hoje;
	}

	public UsuarioSistema getSistema() {
		return sistema;
	}

	public void setSistema(UsuarioSistema sistema) {
		this.sistema = sistema;
	}

}
