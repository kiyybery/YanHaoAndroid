
package com.yanhao.main.yanhaoandroid.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.text.format.Time;


public class TimeUtil {

    public static String getTimeString(long t) {
        Time time = new Time();
        time.set(t);
        return time.format("%T");
    }

    public static String getDateString(long t) {
    	
        Time time = new Time();
        time.set(t);
        return time.format("%y-%m-%d");
    }

    public static boolean isToday(long t) {
        final Calendar c = Calendar.getInstance();
        c.setTimeInMillis(t);
        int y = c.get(Calendar.YEAR);
        int dy = c.get(Calendar.DAY_OF_YEAR);
        long time = System.currentTimeMillis();
        c.setTimeInMillis(time);
        int cy = c.get(Calendar.YEAR);
        int cdy = c.get(Calendar.DAY_OF_YEAR);
        if (y == cy && dy == cdy) {
            return true;
        }
        return false;
    }
   
    public static String getSimpleFormat(long time){
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    	String hourAndMin = sdf.format(new Date(time));
    	final Calendar cal = Calendar.getInstance();
    	cal.setTimeInMillis(time);
    	int year = cal.get(Calendar.YEAR);
    	int week = cal.get(Calendar.WEEK_OF_YEAR);
    	int day = cal.get(Calendar.DAY_OF_YEAR);
    	long currentTime = System.currentTimeMillis();
    	cal.setTimeInMillis(currentTime);
    	int currentYear = cal.get(Calendar.YEAR);
    	int currentWeek = cal.get(Calendar.WEEK_OF_YEAR);
    	int currentDay = cal.get(Calendar.DAY_OF_YEAR);
    	if(year == currentYear && currentDay - day == 0){
    		//today
    		return hourAndMin;
    	}else if(year == currentYear && currentDay - day == 1){
    		return "昨天" + hourAndMin;
    	}else if(year == currentYear && currentDay - day <7){
    		return (currentDay - day) + "天前";
    	}else if(year == currentYear){
    		sdf = new SimpleDateFormat("MM-dd HH:mm");
        	String date = sdf.format(new Date(time));
        	return date;
    	}else{
    		sdf = new SimpleDateFormat("yy-MM-dd HH:mm");
        	String date = sdf.format(new Date(time));
        	return date;
    	}
    }
}
