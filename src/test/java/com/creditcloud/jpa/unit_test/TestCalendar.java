package com.creditcloud.jpa.unit_test;

import org.junit.Test;

import java.util.Calendar;

public class TestCalendar {

    @Test
    public void todayCalendar(){
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
    }

    private static Integer getTodayCalendar(){
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }


    @Test
    public void testMonthEndDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE,calendar.getActualMaximum(calendar.DATE));
        calendar.set(Calendar.HOUR_OF_DAY,calendar.getActualMaximum(calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE,calendar.getActualMaximum(calendar.MINUTE));
        calendar.set(Calendar.SECOND,calendar.getActualMaximum(calendar.SECOND));
        System.out.println(calendar.getTime());
    }
}
