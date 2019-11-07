package com.github.adminfaces.starter.bean.usuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.adminfaces.starter.model.Usuario;
import com.github.adminfaces.starter.service.UsuarioService;
import com.github.adminfaces.starter.util.FacesUtil;

@Named
@ViewScoped
public class ListaUsuariosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UsuarioService service;

	private List<Usuario> todosusuarios = new ArrayList<Usuario>();

	@PostConstruct
	public void inicializar() {
		consultar();
	}

	public void consultar() {
		todosusuarios = service.todos();
	}

	public void excluir(Usuario usuario) {
		service.delete(usuario);
		FacesUtil.addInfoMessage("Cliente excluído com sucesso!");
		consultar();
	}

	public List<Usuario> getTodosusuarios() {
		return todosusuarios;
	}

	public void setTodosusuarios(List<Usuario> todosusuarios) {
		this.todosusuarios = todosusuarios;
	}

}
