package com.github.adminfaces.starter.util;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.github.adminfaces.starter.model.Reserva;

public class RestricaoHorario implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static Long DIA = 86400000L;

	private static Long RESTRICAO = 259200000L;

	public static Long getRESTRICAO() {
		return RESTRICAO;
	}

	public static Long getDia() {
		return DIA;
	}
	
	public static boolean permite(Date dataInicial) {

		Long hoje = new Date().getTime() + getRESTRICAO();

		Long restricao = dataInicial.getTime();

		if (hoje <= restricao) {
			return true;
		} else {
			throw new NegocioException("Não esta de acordo com o termo assinado!! \n Esta fora do prazo de 72horas !!");
		}

	}

	public static void verificaHorarioReserva(Reserva reserva, List<Reserva> list) {
		Long iniRESERVA = reserva.getDataInicial().getTime();
		Long fimRESERVA = reserva.getDataFinal().getTime();

		List<Reserva> reservas = list;
		for (Reserva reserva2 : reservas) {

			Long iniRESERVA2 = reserva2.getDataInicial().getTime();
			Long fimRESERVA2 = reserva2.getDataFinal().getTime();

			if (iniRESERVA >= iniRESERVA2 && iniRESERVA <= fimRESERVA2) {
				throw new NegocioException("Ja Existe Reserva neste peiodo");
			}
			if (fimRESERVA >= iniRESERVA2 && fimRESERVA <= fimRESERVA2) {
				throw new NegocioException("Ja Existe Reserva neste peiodo");
			}
		}
		
	}



}
