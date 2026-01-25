package com.animee.todayhistory.ui;

import android.util.Log;

import com.animee.todayhistory.bean.HistoryDescBean;
import com.animee.todayhistory.network.OkHttpCallBack;
import com.animee.todayhistory.ui.base.ContentURL;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HistoryDescPresenter implements HistoryDescContact.Presenter{
    HistoryDescModel model;
    HistoryDescContact.View view;
    String s;
    public HistoryDescPresenter(HistoryDescContact.View view){
        model = HistoryDescModel.getInstance();
        this.view = view;
    }
    @Override
    public void loadGet(String id){
        s=model.getHistoryDesc(ContentURL.getHistoryDescURL("1.0", id));
        HistoryDescBean bean = new Gson().fromJson(s,HistoryDescBean.class);
        if(bean != null) view.showResult(bean);
    }
}