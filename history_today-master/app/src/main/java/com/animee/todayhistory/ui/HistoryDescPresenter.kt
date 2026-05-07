package com.animee.todayhistory.ui

import com.animee.todayhistory.bean.HistoryDescBean
import com.animee.todayhistory.network.RetrofitClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

class HistoryDescPresenter(private var view: HistoryDescContact.View?) : HistoryDescContact.Presenter {

    private val apiService = RetrofitClient.getApiService()
    private val compositeDisposable = CompositeDisposable()

    override fun loadGet(id: String) {
        val disposable: Disposable = apiService.getHistoryDesc(id, HISTORY_KEY)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { bean -> view?.showResult(bean) },
                { throwable ->
                    val errorBean = HistoryDescBean(
                        reason = "数据加载失败: ${throwable.message}",
                        error_code = 1,
                        result = emptyList()
                    )
                    view?.showResult(errorBean)
                }
            )
        compositeDisposable.add(disposable)
    }

    fun onDestroy() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
        view = null
    }

    companion object {
        private const val HISTORY_KEY = "c53a9df1c3c9fe91a73bafea02234b18"
    }
}
