package com.github.adminfaces.starter.bean.moradia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.adminfaces.starter.model.UnidadeMoradia;
import com.github.adminfaces.starter.model.enums.StatusCadastro;
import com.github.adminfaces.starter.service.UnidadeMoradiaService;
import com.github.adminfaces.starter.util.FacesUtil;



@Named
@ViewScoped
public class ListaMoradiaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private UnidadeMoradiaService service;

	List<UnidadeMoradia> listUnidadeMoradia = new ArrayList<UnidadeMoradia>();
	
	@PostConstruct
	public void inicializar() {
		consultar();
	}
		
	public void excluir(UnidadeMoradia moradia) {
		moradia.setStatusUnidadeMoradia(StatusCadastro.EXCUIDO);
		moradia = service.salvar(moradia);
		FacesUtil.addInfoMessage("Cliente excluído com sucesso!");
		consultar();
	}
	
	public void consultar() {
		listUnidadeMoradia = service.todos();
	}

	public List<UnidadeMoradia> getListUnidadeMoradia() {
		return listUnidadeMoradia;
	}

	public void setListUnidadeMoradia(List<UnidadeMoradia> listUnidadeMoradia) {
		this.listUnidadeMoradia = listUnidadeMoradia;
	}

}
