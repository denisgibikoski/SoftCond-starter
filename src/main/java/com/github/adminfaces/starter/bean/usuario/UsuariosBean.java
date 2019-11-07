package com.github.adminfaces.starter.bean.usuario;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import com.github.adminfaces.starter.model.UnidadeMoradia;
import com.github.adminfaces.starter.model.Usuario;
import com.github.adminfaces.starter.model.enums.StatusCadastro;
import com.github.adminfaces.starter.model.enums.TipoUsuario;
import com.github.adminfaces.starter.service.UsuarioService;
import com.github.adminfaces.starter.util.FacesUtil;
import com.github.adminfaces.starter.util.NegocioException;

@Named
@ViewScoped
public class UsuariosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired(required = false)
	private UsuarioService usuarioService;

	@Autowired
	private ApplicationEventPublisher publisher;

	private Usuario usuario;

	private boolean sindico = false;
	private boolean ativar = false;
	private boolean morador = false;

	@PostConstruct
	public void inicializar() {

		limpar();
		FacesContext fContext = FacesContext.getCurrentInstance();
		if (fContext != null) {
			HttpServletRequest request = (HttpServletRequest) fContext.getExternalContext().getRequest();
			if (request.getParameter("usuario") != null) {
				String url = request.getParameter("usuario");
				Long id = Long.valueOf(url);

				usuario = usuarioService.porId(id);
				if (usuario.getMoradia() != null) {
					morador = true;
					mostarCampos();
				}
				request.removeAttribute("usuario");
			}
		}
	}

	public void clear() {
		inicializar();
	}

	public Boolean isNew() {
		return usuario.getCodigo() == null;
	}

	public void limpar() {
		sindico = false;
		ativar = false;
		morador = false;
		usuario = new Usuario();
		usuario.setMoradia(new UnidadeMoradia());
	}

	public boolean isEditando() {
		return usuario != null;
	}

	public void remover() {
		usuarioService.delete(usuario);
		FacesUtil.addInfoMessage("Cliente excluído com sucesso!");
	}

	public void novoUsuario() {
		try {
			if (morador == true) {
				usuario.setTipoUsuario(TipoUsuario.MORADOR);
			} else {
				usuario.getMoradia().setUnidade(000);
				usuario.setTipoUsuario(TipoUsuario.SINDICO);
			}
			if (sindico == true) {
				if (existeSindico(usuario) == true) {
					throw new NegocioException("Ja existe Sindico cadastrado!!!\n Consulte o Administrador do Sistema");
				} else {
					usuario.setSindico(sindico);
				}
			}
			usuario.getMoradia().setUsuario(usuario);
			usuario = usuarioService.salvar(usuario);
			publisher.publishEvent(usuario);
			FacesUtil.addInfoMessage("Usuario " + usuario.getNome() + "  salvo !!");
			FacesUtil.addInfoMessage("Unidade N°:  " + usuario.getMoradia().getUnidade() + "  salvo !!");
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage("ERRO :" + e.getMessage() + " - " + e.getCause());
			FacesUtil.addFatalMessage(e.getMessage());
		}
	}

	private boolean existeSindico(Usuario usuario2) {
		Usuario usuario = usuarioService.getSindico();
		if (usuario.isSindico() && usuario.getStatusUsuario() == StatusCadastro.ATIVO
				&& usuario.getCodigo() != usuario2.getCodigo()) {
			return true;
		} else {
			return false;
		}
	}

	public void mostarCampos() {
		if (usuario.isSindico()) {
			sindico = true;
		}
		if (morador == true) {
			ativar = true;
		} else if (morador == false) {
			ativar = false;
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isSindico() {
		return sindico;
	}

	public void setSindico(boolean sindico) {
		this.sindico = sindico;
	}

	public boolean isMorador() {
		return morador;
	}

	public void setMorador(boolean morador) {
		this.morador = morador;
	}

	public boolean isAtivar() {
		return ativar;
	}

	public void setAtivar(boolean ativar) {
		this.ativar = ativar;
	}

}
