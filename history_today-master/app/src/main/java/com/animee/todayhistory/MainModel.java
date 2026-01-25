package com.animee.todayhistory;

import android.annotation.SuppressLint;

import com.animee.todayhistory.bean.HistoryBean;
import com.animee.todayhistory.bean.HistoryDescBean;
import com.animee.todayhistory.bean.LaoHuangliBean;
import com.animee.todayhistory.bean.LaoHuangliHoursBean;
import com.animee.todayhistory.network.OkHttpCallBack;
import com.animee.todayhistory.network.OkpClient;
import com.animee.todayhistory.ui.base.ContentURL;
import com.google.gson.Gson;

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

        todayHistoryURL = ContentURL.getTodayHistoryURL(month, day);

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(date);
        laohuangliURL = ContentURL.getLaohuangliURL(time);
        laohuangliHoursURL = ContentURL.getLaohuangliHoursURL(time);
    }
    public void UpdateTime(int year, int month, int dayOfMonth){
        String time = year+"-"+(month+1)+"-"+dayOfMonth;
        laohuangliURL = ContentURL.getLaohuangliURL(time);
        laohuangliHoursURL = ContentURL.getLaohuangliHoursURL(time);
        todayHistoryURL = ContentURL.getTodayHistoryURL((month + 1), dayOfMonth);

    }
    public void UpdateHisTory(int year, int month, int dayOfMonth, final OkHttpCallBack callBack){
        String todayHistoryURL = ContentURL.getTodayHistoryURL(month, day);
        OkpClient.doAsyncGet(todayHistoryURL, callBack);
    }
    public void HisTory(final OkHttpCallBack callBack){
        OkpClient.doAsyncGet(todayHistoryURL, callBack);
    }

    public void HuangLi(final OkHttpCallBack callBack) {
        OkpClient.doAsyncGet(laohuangliURL, callBack);
    }

    public void HuangLiHours(final OkHttpCallBack callBack) {
        OkpClient.doAsyncGet(laohuangliHoursURL, callBack);
    }

}
