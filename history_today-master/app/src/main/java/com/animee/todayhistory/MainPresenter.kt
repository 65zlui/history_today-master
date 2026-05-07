package com.animee.todayhistory

import com.animee.todayhistory.bean.HistoryBean
import com.animee.todayhistory.bean.LaoHuangliBean
import com.animee.todayhistory.bean.LaoHuangliHoursBean
import com.animee.todayhistory.network.RetrofitClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

class MainPresenter(private var mainView: MainContract.MainView?) : MainContract.Presenter {

    private val apiService = RetrofitClient.getApiService()
    private val compositeDisposable = CompositeDisposable()

    init {
        MainModel.getTime()
    }

    override fun getHuangLi() {
        val date = MainModel.getLaohuangliDate()
        val disposable = apiService.getLaoHuangli(date, LAOHUANGLI_KEY)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { bean -> mainView?.getHuangLiSuccess(bean) },
                { throwable -> mainView?.getHuangLiError(throwable) }
            )
        compositeDisposable.add(disposable)
    }

    override fun getHuangLiHours() {
        val date = MainModel.getLaohuangliDate()
        val disposable = apiService.getLaoHuangliHours(date, LAOHUANGLI_KEY)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { bean -> mainView?.getHuangLiHoursSuccess(bean) },
                { throwable -> mainView?.getHuangLiHoursError(throwable) }
            )
        compositeDisposable.add(disposable)
    }

    override fun getHistory() {
        val date = MainModel.getHistoryDate()
        val disposable = apiService.getTodayHistory(date, HISTORY_KEY)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { bean -> mainView?.getHistorySuccess(bean) },
                { throwable -> mainView?.getHistoryError(throwable) }
            )
        compositeDisposable.add(disposable)
    }

    override fun UpdateTime(year: Int, month: Int, dayOfMonth: Int) {
        MainModel.UpdateTime(year, month, dayOfMonth)
    }

    override fun loadGet(his: String) {
        // no-op: kept for contract compatibility
    }

    fun onDestroy() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
        mainView = null
    }

    companion object {
        private const val HISTORY_KEY = "c53a9df1c3c9fe91a73bafea02234b18"
        private const val LAOHUANGLI_KEY = "530ccafe6316f9854db36e58a3b81c2a"
    }
}
