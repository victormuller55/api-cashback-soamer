package com.api.soamer.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Formatters {
    public static Date formatDDMMYYYYHHMMToDate(String date) throws ParseException {

        SimpleDateFormat formato = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(formato.parse(date));

        return getBrazilianHour(calendar.getTime());
    }

    public static Date getBrazilianHour(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.getTime();

    }
}
