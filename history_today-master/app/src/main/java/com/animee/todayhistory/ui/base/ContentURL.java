package com.animee.todayhistory.ui.base;

public class ContentURL {

//    获取指定日期对应的历史上的今天的网址
    public static String getTodayHistoryURL(int month,int day){
        String date = month + "/" + day;
        String url = "http://v.juhe.cn/todayOnhistory/queryEvent?date=" + date + "&key=c53a9df1c3c9fe91a73bafea02234b18";
        return url;
    }

//    获取指定日期的老黄历的网址
    public static String getLaohuangliURL(String time){
        String url = "http://v.juhe.cn/laohuangli/d?date="+time+"&key=530ccafe6316f9854db36e58a3b81c2a";
        return url;
    }

//    获取指定日期的时辰信息的网址
    public static String getLaohuangliHoursURL(String time){
        String url = "http://v.juhe.cn/laohuangli/h?date="+time+"&key=530ccafe6316f9854db36e58a3b81c2a";
        return url;
    }

//    根据指定事件id获取指定事件详情信息
    public static String getHistoryDescURL(String version,String id){
        return "http://api.juheapi.com/japi/tohdet?key=c53a9df1c3c9fe91a73bafea02234b18&v="+version+"&id="+id;
    }
}
