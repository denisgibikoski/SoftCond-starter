package com.github.adminfaces.starter.util;

import com.github.adminfaces.starter.model.enums.StatusCadastro;
import com.github.adminfaces.starter.model.enums.StatusReserva;

public class ScheduleUtil {

	public static String getClass(StatusReserva statusReserva) {
		switch (statusReserva) {
		case RESERVADO:
			return "event-RESERVADO";
		case PEDENTE:
			return "event-PEDENTE";
		case CONCLUIDO:
			return "event-CONCLUIDO";
		case EXCLUIDO:
			return "event-EXCUIDO";
		default:
			return "event-default";
		}
		
	}

	public static String getClass(StatusCadastro statusUnidadeMoradia) {
		switch (statusUnidadeMoradia) {
		case ATIVO:
			return "event-RESERVADO";
		case INATIVO:
			return "event-PEDENTE";
		case PEDENTE:
			return "event-CONCLUIDO";
		default:
			return "event-default";
		}
	}

}
