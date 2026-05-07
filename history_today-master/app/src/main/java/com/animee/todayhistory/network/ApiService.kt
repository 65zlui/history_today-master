package com.animee.todayhistory.network

import com.animee.todayhistory.bean.HistoryBean
import com.animee.todayhistory.bean.HistoryDescBean
import com.animee.todayhistory.bean.LaoHuangliBean
import com.animee.todayhistory.bean.LaoHuangliHoursBean
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/todayOnhistory/queryEvent")
    fun getTodayHistory(
        @Query("date") date: String,
        @Query("key") key: String
    ): Observable<HistoryBean>

    @GET("/todayOnhistory/queryDetail")
    fun getHistoryDesc(
        @Query("e_id") eventId: String,
        @Query("key") key: String
    ): Observable<HistoryDescBean>

    @GET("/laohuangli/d")
    fun getLaoHuangli(
        @Query("date") date: String,
        @Query("key") key: String
    ): Observable<LaoHuangliBean>

    @GET("/laohuangli/h")
    fun getLaoHuangliHours(
        @Query("date") date: String,
        @Query("key") key: String
    ): Observable<LaoHuangliHoursBean>
}
