package com.animee.todayhistory;

import com.animee.todayhistory.bean.HistoryBean;
import com.animee.todayhistory.bean.LaoHuangliBean;
import com.animee.todayhistory.ui.base.ContentURL;

public class MainPresenter implements  MainContract.Presenter{
    private MainContract.MainView mainView;
    private MainModel mainModel;
    public MainPresenter(MainContract.MainView MV){

        mainModel = MainModel.getInstance();
        this.mainView = MV;
        mainModel.getTime();
    }
    @Override
    public void getHuangLi(){

        LaoHuangliBean laoHuangliBean = mainModel.HuangLi();
        mainView.getHuangLiSuccess(laoHuangliBean);
    }

    @Override
    public void getHistory() {

        HistoryBean historyBean = mainModel.HisTory();

        mainView.getHistorySuccess(historyBean);
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
