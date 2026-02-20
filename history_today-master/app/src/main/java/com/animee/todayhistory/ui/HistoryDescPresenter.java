package com.animee.todayhistory.ui;

import android.util.Log;

import com.animee.todayhistory.bean.HistoryDescBean;
import com.animee.todayhistory.network.ApiService;
import com.animee.todayhistory.network.RetrofitClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class HistoryDescPresenter implements HistoryDescContact.Presenter {
    HistoryDescModel model;
    HistoryDescContact.View view;
    ApiService apiService;
    CompositeDisposable compositeDisposable;
    
    // API key
    private static final String HISTORY_KEY = "c53a9df1c3c9fe91a73bafea02234b18";
    
    public HistoryDescPresenter(HistoryDescContact.View view) {
        model = HistoryDescModel.getInstance();
        this.view = view;
        apiService = RetrofitClient.getApiService();
        compositeDisposable = new CompositeDisposable();
    }
    
    @Override
    public void loadGet(String id) {
        Disposable disposable = apiService.getHistoryDesc(id, HISTORY_KEY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        bean -> {
                            if (view != null) {
                                view.showResult(bean);
                            }
                        },
                        throwable -> {
                            if (view != null) {
                                // Create error bean
                                HistoryDescBean errorBean = new HistoryDescBean();
                                errorBean.setResult(new java.util.ArrayList<>());
                                errorBean.setReason("数据加载失败: " + throwable.getMessage());
                                errorBean.setError_code(1);
                                view.showResult(errorBean);
                            }
                        }
                );
        compositeDisposable.add(disposable);
    }
    
    public void onDestroy() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        view = null;
    }
}