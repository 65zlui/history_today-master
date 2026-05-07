package com.animee.todayhistory.ui

import com.animee.todayhistory.bean.HistoryDescBean

interface HistoryDescContact {
    interface View {
        fun showResult(bean: HistoryDescBean)
    }

    interface Presenter {
        fun loadGet(his: String)
    }
}
