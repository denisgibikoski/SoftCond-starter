package com.github.adminfaces.starter.model.enums;

public enum StatusReserva {
	
	RESERVADO("Reservado"),
	PEDENTE("Pendente"), 
	CONCLUIDO("Concluido"),
	INDEFERIDO("Indeferido"),
	EXCLUIDO("Excluido");
	
	private String descricao;

	StatusReserva(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
