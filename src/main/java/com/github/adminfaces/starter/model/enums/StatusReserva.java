package com.github.adminfaces.starter.model.enums;

public enum StatusReserva {
	
	RESERVADO("Reservado"),
	PEDENTE("Pedente"), 
	CONCLUIDO("Concluido"),
	INDEFERIDO("indeferido"),
	EXCUIDO("Excluido");
	
	private String descricao;

	StatusReserva(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
