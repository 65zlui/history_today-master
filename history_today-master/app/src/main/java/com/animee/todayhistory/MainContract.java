package com.animee.todayhistory;

import com.animee.todayhistory.bean.HistoryBean;
import com.animee.todayhistory.bean.HistoryDescBean;
import com.animee.todayhistory.bean.LaoHuangliBean;
import com.animee.todayhistory.bean.LaoHuangliHoursBean;

public interface MainContract {
    interface MainView {
        void getHuangLiSuccess(LaoHuangliBean bean);

        void getHuangLiError(Throwable throwable);
        
        void getHuangLiHoursSuccess(LaoHuangliHoursBean bean);
        
        void getHuangLiHoursError(Throwable throwable);

        void getHistorySuccess(HistoryBean bean);

        void getHistoryError(Throwable throwable);

    }
    interface Presenter{
        void loadGet(String his);
        void getHuangLi();
        void getHuangLiHours();
        void getHistory();
        void UpdateTime(int year, int month, int dayOfMonth);
    }
}
