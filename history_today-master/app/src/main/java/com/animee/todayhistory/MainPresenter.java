package com.animee.todayhistory;

import com.animee.todayhistory.bean.HistoryBean;
import com.animee.todayhistory.bean.LaoHuangliBean;
import com.animee.todayhistory.bean.LaoHuangliHoursBean;
import com.animee.todayhistory.network.ApiService;
import com.animee.todayhistory.network.RetrofitClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.MainView mainView;
    private MainModel mainModel;
    private ApiService apiService;
    private CompositeDisposable compositeDisposable;
    
    // API keys
    private static final String HISTORY_KEY = "c53a9df1c3c9fe91a73bafea02234b18";
    private static final String LAOHUANGLI_KEY = "530ccafe6316f9854db36e58a3b81c2a";
    
    public MainPresenter(MainContract.MainView MV) {
        mainModel = MainModel.getInstance();
        this.mainView = MV;
        mainModel.getTime();
        apiService = RetrofitClient.getApiService();
        compositeDisposable = new CompositeDisposable();
    }
    
    @Override
    public void getHuangLi() {
        String date = mainModel.getLaohuangliDate();
        Disposable disposable = apiService.getLaoHuangli(date, LAOHUANGLI_KEY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        laoHuangliBean -> {
                            if (mainView != null) {
                                mainView.getHuangLiSuccess(laoHuangliBean);
                            }
                        },
                        throwable -> {
                            if (mainView != null) {
                                mainView.getHuangLiError(throwable);
                            }
                        }
                );
        compositeDisposable.add(disposable);
    }

    @Override
    public void getHuangLiHours() {
        String date = mainModel.getLaohuangliDate();
        Disposable disposable = apiService.getLaoHuangliHours(date, LAOHUANGLI_KEY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        laoHuangliHoursBean -> {
                            if (mainView != null) {
                                mainView.getHuangLiHoursSuccess(laoHuangliHoursBean);
                            }
                        },
                        throwable -> {
                            if (mainView != null) {
                                mainView.getHuangLiHoursError(throwable);
                            }
                        }
                );
        compositeDisposable.add(disposable);
    }

    @Override
    public void getHistory() {
        String date = mainModel.getHistoryDate();
        Disposable disposable = apiService.getTodayHistory(date, HISTORY_KEY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        historyBean -> {
                            if (mainView != null) {
                                mainView.getHistorySuccess(historyBean);
                            }
                        },
                        throwable -> {
                            if (mainView != null) {
                                mainView.getHistoryError(throwable);
                            }
                        }
                );
        compositeDisposable.add(disposable);
    }
    
    @Override
    public void UpdateTime(int year, int month, int dayOfMonth) {
        mainModel.UpdateTime(year, month, dayOfMonth);
    }

    public void getMainPresenter() {
    }
    
    public void onDestroy() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        mainView = null;
    }

    @Override
    public void loadGet(String his) {
    }
}
