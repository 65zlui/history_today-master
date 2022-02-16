package com.animee.todayhistory.ui;

import com.animee.todayhistory.bean.HistoryDescBean;

public interface HistoryDescContact {
    interface View {
        void showResult(HistoryDescBean bean);
    }
    interface Presenter{
        void loadGet(String his);
    }
}
