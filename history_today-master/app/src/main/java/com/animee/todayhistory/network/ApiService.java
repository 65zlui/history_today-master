package com.animee.todayhistory.network;

import com.animee.todayhistory.bean.HistoryBean;
import com.animee.todayhistory.bean.HistoryDescBean;
import com.animee.todayhistory.bean.LaoHuangliBean;
import com.animee.todayhistory.bean.LaoHuangliHoursBean;

import io.reactivex.rxjava3.core.Observable;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    
    // 获取历史上的今天
    @GET("/todayOnhistory/queryEvent")
    Observable<HistoryBean> getTodayHistory(
            @Query("date") String date,
            @Query("key") String key
    );
    
    // 获取历史事件详情
    @GET("/todayOnhistory/queryDetail")
    Observable<HistoryDescBean> getHistoryDesc(
            @Query("e_id") String eventId,
            @Query("key") String key
    );
    
    // 获取老黄历
    @GET("/laohuangli/d")
    Observable<LaoHuangliBean> getLaoHuangli(
            @Query("date") String date,
            @Query("key") String key
    );
    
    // 获取老黄历时辰信息
    @GET("/laohuangli/h")
    Observable<LaoHuangliHoursBean> getLaoHuangliHours(
            @Query("date") String date,
            @Query("key") String key
    );
}
