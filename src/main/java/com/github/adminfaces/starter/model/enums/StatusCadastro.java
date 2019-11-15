package com.github.adminfaces.starter.model.enums;

public  enum StatusCadastro {
	
	ATIVO("Ativo"),
	INATIVO("Inativo"),
	PEDENTE("Pendente");
	
	private String descricao;

	StatusCadastro(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
