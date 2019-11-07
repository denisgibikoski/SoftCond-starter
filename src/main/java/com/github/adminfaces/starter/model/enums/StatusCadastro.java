package com.github.adminfaces.starter.model.enums;

public  enum StatusCadastro {
	
	ATIVO("Ativo"),
	INATIVO("Inativo"),
	PEDENTE("Pedente"),
	EXCUIDO("Excluido");
	
	private String descricao;

	StatusCadastro(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
