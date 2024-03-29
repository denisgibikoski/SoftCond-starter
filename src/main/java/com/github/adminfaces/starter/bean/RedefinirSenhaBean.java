package com.github.adminfaces.starter.bean;

import java.io.Serializable;
import java.util.UUID;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.adminfaces.starter.model.Usuario;
import com.github.adminfaces.starter.service.EmailService;
import com.github.adminfaces.starter.service.UsuarioService;
import com.github.adminfaces.starter.util.FacesUtil;
import com.github.adminfaces.starter.util.NegocioException;


@Named
@RequestScoped
public class RedefinirSenhaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	private String email;
	
	public void redifinirSenha() {
		try {
			Usuario usuario = usuarioService.porEmail(email);

			if (usuario == null) {
				FacesUtil.addWarnMessage("Nenhum usuário encontrado com o email informado.");
			} else {
				
				String senha = gerarSenhatemporaria();
				usuario.setPassword(senha);
				usuarioService.redefinirSenha(usuario);
				emailService.enviarSenhaNova(usuario, senha);
				
				FacesUtil.addInfoMessage("Sucesso! Verifique sua caixa de entrada.");
			}
		} catch (Exception e) {
			throw new NegocioException("Falha ao enviar o email." + e.getMessage() + e.getCause());
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	private String gerarSenhatemporaria () {
		UUID uuid = UUID.randomUUID();
		String myRandom = uuid.toString();
		return myRandom.substring(0,8);
	}
	

}
