package com.animee.todayhistory

import com.animee.todayhistory.bean.HistoryBean
import com.animee.todayhistory.bean.LaoHuangliBean
import com.animee.todayhistory.bean.LaoHuangliHoursBean

interface MainContract {
    interface MainView {
        fun getHuangLiSuccess(bean: LaoHuangliBean)
        fun getHuangLiError(throwable: Throwable)
        fun getHuangLiHoursSuccess(bean: LaoHuangliHoursBean)
        fun getHuangLiHoursError(throwable: Throwable)
        fun getHistorySuccess(bean: HistoryBean)
        fun getHistoryError(throwable: Throwable)
    }

    interface Presenter {
        fun loadGet(his: String)
        fun getHuangLi()
        fun getHuangLiHours()
        fun getHistory()
        fun UpdateTime(year: Int, month: Int, dayOfMonth: Int)
    }
}
