package com.animee.todayhistory.ui;

import android.graphics.ColorSpace;

import com.animee.todayhistory.network.OkHttpCallBack;
import com.animee.todayhistory.network.OkpClient;
import com.animee.todayhistory.ui.base.ContentURL;

import java.util.Map;

import okhttp3.Callback;
import okhttp3.OkHttpClient;

public class HistoryDescModel {
    private static volatile HistoryDescModel instance ;
    private HistoryDescModel(){
    }
    public static HistoryDescModel getInstance(){
        if(instance == null) {
            synchronized (HistoryDescModel.class) {
                if (instance == null) {
                    instance = new HistoryDescModel();
                }
            }
        }
        return instance;
    }
    public String getHistoryDesc(String address){
       return OkpClient.getSync(address);
    }

}
