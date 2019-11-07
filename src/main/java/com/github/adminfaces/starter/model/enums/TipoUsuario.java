package com.github.adminfaces.starter.model.enums;

public enum TipoUsuario {
	
	ADMINISTRADOR("Administrador"),
	SINDICO("Sindico"),
	MORADOR( "Morador");
		
	private String descricao;
	
	TipoUsuario(String descricao) {
		this.descricao =  descricao;
	}
	
	
	public String getDescricao() {
		return descricao;
	}
}
