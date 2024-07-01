package com.api.soamer.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static int extractNumbers(String str) {

        StringBuilder numbers = new StringBuilder();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()) {
            numbers.append(matcher.group());
        }

        if (numbers.length() == 0) {
            return 0;
        }

        return Integer.parseInt(numbers.toString());
    }
}
