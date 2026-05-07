package com.animee.todayhistory

import android.annotation.SuppressLint
import com.animee.todayhistory.ui.base.ContentURL
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

object MainModel {

    private var calendar: Calendar = Calendar.getInstance()
    private var date: Date = Date()
    var month: Int = 0
        private set
    var day: Int = 0
        private set
    var laohuangliURL: String = ""
        private set
    var laohuangliHoursURL: String = ""
        private set
    var todayHistoryURL: String = ""
        private set
    var currentLaohuangliDate: String = ""
        private set
    var currentHistoryDate: String = ""
        private set

    fun getTime() {
        calendar = Calendar.getInstance()
        date = Date()
        calendar.time = date
        month = calendar.get(Calendar.MONTH) + 1
        day = calendar.get(Calendar.DAY_OF_MONTH)

        currentHistoryDate = "$month/$day"
        todayHistoryURL = ContentURL.getTodayHistoryURL(month, day)

        @SuppressLint("SimpleDateFormat")
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        currentLaohuangliDate = sdf.format(date)
        laohuangliURL = ContentURL.getLaohuangliURL(currentLaohuangliDate)
        laohuangliHoursURL = ContentURL.getLaohuangliHoursURL(currentLaohuangliDate)
    }

    fun getLaohuangliDate(): String = currentLaohuangliDate

    fun getHistoryDate(): String = currentHistoryDate

    fun UpdateTime(year: Int, month: Int, dayOfMonth: Int) {
        currentLaohuangliDate = "$year-${month + 1}-$dayOfMonth"
        laohuangliURL = ContentURL.getLaohuangliURL(currentLaohuangliDate)
        laohuangliHoursURL = ContentURL.getLaohuangliHoursURL(currentLaohuangliDate)
        currentHistoryDate = "${month + 1}/$dayOfMonth"
        todayHistoryURL = ContentURL.getTodayHistoryURL(month + 1, dayOfMonth)
    }
}
