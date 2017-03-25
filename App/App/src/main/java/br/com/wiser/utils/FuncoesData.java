package br.com.wiser.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FuncoesData {

    public static final String DDMMYYYY = "dd/MM/yyyy";
    public static final String DDMMYYYY_HHMM = "dd/MM/yyyy HH:mm";
    public static final String DDMMYYYY_HHMMSS = "dd/MM/yyyy HH:mm:ss";
    public static final String HHMM = "HH:mm";

    public static String formatDate(Date data, String formatData) {
        return new SimpleDateFormat(formatData).format(data);
    }

    public static int getDiferencaDataDias(Date primeiroDia, Date segundoDia){
        return (int)(((primeiroDia.getTime() - segundoDia.getTime()) / 86400000));
    }

    public static int getDiferencaDiasNoMes(Date primeiroDia, Date segundoDia){
        return primeiroDia.getDay() - segundoDia.getDay();
    }

    public static boolean getDiferencaDataDiferenteHoje(Date dia){
        Date hoje = new Date();
        return getDiferencaDataDias(hoje, dia) == 0 && getDiferencaDiasNoMes(hoje, dia) == 0;
    }
}
