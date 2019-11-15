package com.github.adminfaces.starter.bean.moradia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import com.github.adminfaces.starter.model.UnidadeMoradia;
import com.github.adminfaces.starter.model.enums.StatusCadastro;
import com.github.adminfaces.starter.service.UnidadeMoradiaService;
import com.github.adminfaces.starter.util.FacesUtil;
import com.github.adminfaces.starter.util.ScheduleUtil;


@Named
@ViewScoped
public class UnidadeMoradiaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UnidadeMoradiaService service;

	@Autowired
	private ApplicationEventPublisher publisher;

	private UnidadeMoradia moradia;

	private List<UnidadeMoradia> listUnidadeMoradia = new ArrayList<UnidadeMoradia>();
	private boolean ativarUnidade = false;
	
	public String getStyleClass(UnidadeMoradia moradia) {
		return ScheduleUtil.getClass(moradia.getStatusUnidadeMoradia());	
	}

	@PostConstruct
	public void inicializar() {

	}

	public void salvarMoradia(UnidadeMoradia moradia) {
		try {
			moradia.getUsuario().setStatusUsuario(StatusCadastro.ATIVO);
			moradia = service.salvar(moradia);
			publisher.publishEvent(moradia);
			FacesUtil.addInfoMessage("Unidade de Moradisa Atualizada com sucesso!!!");
		} catch (Exception e) {
			FacesUtil.addFatalMessage(e.getMessage());
		}
	}

	public StatusCadastro[] getStatusCadastros() {
		return StatusCadastro.values();
	}

	public boolean isEditando() {
		return moradia != null;
	}

	public List<UnidadeMoradia> getListUnidadeMoradia() {
		return listUnidadeMoradia;
	}

	public void setListUnidadeMoradia(List<UnidadeMoradia> listUnidadeMoradia) {
		this.listUnidadeMoradia = listUnidadeMoradia;
	}

	public UnidadeMoradia getMoradia() {
		return moradia;
	}

	public boolean isAtivarUnidade() {
		return ativarUnidade;
	}

	public void setAtivarUnidade(boolean ativarUnidade) {
		this.ativarUnidade = ativarUnidade;
	}

}
