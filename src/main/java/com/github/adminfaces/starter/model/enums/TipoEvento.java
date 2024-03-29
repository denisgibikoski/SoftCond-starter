package com.github.adminfaces.starter.model.enums;

public enum TipoEvento {
		
	HAPPYHOUR("Happy hour"),
	ALMOCO("almoço"),
	JANTAR("jantar"),
	CAFEDAMANHA("café da manhã"),
	ANIVERSARIO("festa de aniversário"),
	REUNIAO("Reunião de Condominio"),
	OUTROS("outros");
	
	private String descricao;
	
	TipoEvento(String descricao) {
		this.descricao =  descricao;
	}
	
	
	public String getDescricao() {
		return descricao;
	}

}
