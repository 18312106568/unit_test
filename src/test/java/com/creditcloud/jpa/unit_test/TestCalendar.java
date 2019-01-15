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
}
