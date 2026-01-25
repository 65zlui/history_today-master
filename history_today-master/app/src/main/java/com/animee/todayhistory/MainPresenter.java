package com.animee.todayhistory;

import com.animee.todayhistory.bean.HistoryBean;
import com.animee.todayhistory.bean.LaoHuangliBean;
import com.animee.todayhistory.bean.LaoHuangliHoursBean;
import com.animee.todayhistory.network.OkHttpCallBack;
import com.google.gson.Gson;
import okhttp3.Response;

import java.io.IOException;

public class MainPresenter implements  MainContract.Presenter{
    private MainContract.MainView mainView;
    private MainModel mainModel;
    private Gson gson;
    
    public MainPresenter(MainContract.MainView MV){
        mainModel = MainModel.getInstance();
        this.mainView = MV;
        mainModel.getTime();
        gson = new Gson();
    }
    
    @Override
    public void getHuangLi(){
        mainModel.HuangLi(new OkHttpCallBack() {
            @Override
            public void onSuccess(Response response) {
                try {
                    String res = response.body().string();
                    LaoHuangliBean laoHuangliBean = gson.fromJson(res, LaoHuangliBean.class);
                    if (mainView != null) {
                        mainView.getHuangLiSuccess(laoHuangliBean);
                    }
                } catch (IOException e) {
                    if (mainView != null) {
                        mainView.getHuangLiError(e);
                    }
                }
            }
            
            @Override
            public void onFailure(IOException e) {
                if (mainView != null) {
                    mainView.getHuangLiError(e);
                }
            }
        });
    }

    @Override
    public void getHuangLiHours(){
        mainModel.HuangLiHours(new OkHttpCallBack() {
            @Override
            public void onSuccess(Response response) {
                try {
                    String res = response.body().string();
                    LaoHuangliHoursBean laoHuangliHoursBean = gson.fromJson(res, LaoHuangliHoursBean.class);
                    if (mainView != null) {
                        mainView.getHuangLiHoursSuccess(laoHuangliHoursBean);
                    }
                } catch (IOException e) {
                    if (mainView != null) {
                        mainView.getHuangLiHoursError(e);
                    }
                }
            }
            
            @Override
            public void onFailure(IOException e) {
                if (mainView != null) {
                    mainView.getHuangLiHoursError(e);
                }
            }
        });
    }

    @Override
    public void getHistory() {
        mainModel.HisTory(new OkHttpCallBack() {
            @Override
            public void onSuccess(Response response) {
                try {
                    String res = response.body().string();
                    HistoryBean historyBean = gson.fromJson(res, HistoryBean.class);
                    if (mainView != null) {
                        mainView.getHistorySuccess(historyBean);
                    }
                } catch (IOException e) {
                    if (mainView != null) {
                        mainView.getHistoryError(e);
                    }
                }
            }
            
            @Override
            public void onFailure(IOException e) {
                if (mainView != null) {
                    mainView.getHistoryError(e);
                }
            }
        });
    }
    
    @Override
    public void UpdateTime(int year, int month, int dayOfMonth){
        mainModel.UpdateTime(year, month, dayOfMonth);
    }

    public void getMainPresenter(){
    }
    
    public void onDestroy() {
        mainView = null;
    }

    @Override
    public void loadGet(String his) {
    }
}
