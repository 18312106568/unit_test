package com.creditcloud.jpa.unit_test.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss SSS";

    public static final Long DAY_SECOND = 24*60*60*1000L;

    private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>();

    public static DateFormat getDateFormat(){
        DateFormat df = threadLocal.get();
        if(df==null){
            df = new SimpleDateFormat(DATE_FORMAT);
            threadLocal.set(df);
        }
        return df;
    }


    public static String format(Date date){
       return  getDateFormat().format(date);
    }

    public static Date parse(String dateStr) throws ParseException {
        return  getDateFormat().parse(dateStr);
    }





}
