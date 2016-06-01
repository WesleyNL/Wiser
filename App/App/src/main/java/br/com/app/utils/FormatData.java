package br.com.app.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jefferson on 31/05/2016.
 */
public class FormatData {

    public static final String DDMMYYYY_HHMMSS = "dd/MM/yyyy HH:mm:ss";
    public static final String HHMM = "HH:mm";

    public static String formatDate(Date data, String formatData) {
        return new SimpleDateFormat(formatData).format(data);
    }
}
