package com.animee.todayhistory;

import com.animee.todayhistory.bean.HistoryBean;
import com.animee.todayhistory.bean.HistoryDescBean;
import com.animee.todayhistory.bean.LaoHuangliBean;
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
    String todayHistoryURL;
    public static MainModel getInstance() {
        if(instance == null)
        synchronized (MainModel.class){
            if(instance == null)
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

        todayHistoryURL = ContentURL.getTodayHistoryURL("1.0", month, day);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(date);
        laohuangliURL = ContentURL.getLaohuangliURL(time);
    }
    public void UpdateTime(int year, int month, int dayOfMonth){
        String time = year+"-"+(month+1)+"-"+dayOfMonth;
        laohuangliURL = ContentURL.getLaohuangliURL(time);
        todayHistoryURL = ContentURL.getTodayHistoryURL("1.0", (month + 1), dayOfMonth);

    }
    public HistoryBean UpdateHisTory(int year, int month, int dayOfMonth){

        String todayHistoryURL = ContentURL.getTodayHistoryURL("1.0", month, day);
//        loadData(todayHistoryURL);
        String response = OkpClient.getSync(todayHistoryURL);
        final HistoryBean historyBean = new Gson().fromJson(response,HistoryBean.class);
        return historyBean;
    }
    public HistoryBean HisTory(){

//        loadData(todayHistoryURL);
        String response = OkpClient.getSync(todayHistoryURL);
        final HistoryBean historyBean = new Gson().fromJson(response,HistoryBean.class);
        return historyBean;
    }

    public LaoHuangliBean HuangLi() {


        String response= OkpClient.getSync(laohuangliURL);
        final LaoHuangliBean huangliBean = new Gson().fromJson(response, LaoHuangliBean.class);
        return huangliBean;
    }

}
