package com.animee.todayhistory;

import android.annotation.SuppressLint;

import com.animee.todayhistory.ui.base.ContentURL;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainModel {
    private static MainModel instance;
    private Calendar calendar;
    private Date date;
    int month;
    int day;
    String laohuangliURL;
    String laohuangliHoursURL;
    String todayHistoryURL;
    String currentLaohuangliDate;
    String currentHistoryDate;

    public static MainModel getInstance() {
        if (instance == null)
            synchronized (MainModel.class) {
                if (instance == null)
                    instance = new MainModel();
            }
        return instance;
    }

    private MainModel() {

    }

    public void getTime() {
        calendar = Calendar.getInstance();
        date = new Date();
        calendar.setTime(date);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);

        currentHistoryDate = month + "/" + day;
        todayHistoryURL = ContentURL.getTodayHistoryURL(month, day);

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        currentLaohuangliDate = sdf.format(date);
        laohuangliURL = ContentURL.getLaohuangliURL(currentLaohuangliDate);
        laohuangliHoursURL = ContentURL.getLaohuangliHoursURL(currentLaohuangliDate);
    }
    
    public String getLaohuangliDate() {
        return currentLaohuangliDate;
    }
    
    public String getHistoryDate() {
        return currentHistoryDate;
    }
    
    public void UpdateTime(int year, int month, int dayOfMonth){
        currentLaohuangliDate = year+"-"+(month+1)+"-"+dayOfMonth;
        laohuangliURL = ContentURL.getLaohuangliURL(currentLaohuangliDate);
        laohuangliHoursURL = ContentURL.getLaohuangliHoursURL(currentLaohuangliDate);
        
        currentHistoryDate = (month + 1) + "/" + dayOfMonth;
        todayHistoryURL = ContentURL.getTodayHistoryURL((month + 1), dayOfMonth);

    }
}
