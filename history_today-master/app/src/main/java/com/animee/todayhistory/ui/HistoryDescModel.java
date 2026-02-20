package com.animee.todayhistory.ui;

public class HistoryDescModel {
    private static volatile HistoryDescModel instance ;
    private HistoryDescModel(){
    }
    public static HistoryDescModel getInstance(){
        if(instance == null) {
            synchronized (HistoryDescModel.class) {
                if (instance == null) {
                    instance = new HistoryDescModel();
                }
            }
        }
        return instance;
    }
}
