package com.animee.todayhistory.bean;

import java.io.Serializable;
import java.util.List;

public class LaoHuangliHoursBean implements Serializable{

    /**
     * reason : successed
     * result : [
         {
             "yangli": "2014-09-11",
             "hours": "1-3",
             "des": " 修造 安葬 求财 见贵 嫁娶 进人口 移徙",
             "yi": "赴任 出行",
             "ji": "冲猴 煞北 时冲甲申 地兵 三合 长生 司命"
         }
     ]
     * error_code : 0
     */

    private String reason;
    private int error_code;
    private List<ResultBean> result;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable{
        /**
         * yangli : 2014-09-11
         * hours : 1-3
         * des :  修造 安葬 求财 见贵 嫁娶 进人口 移徙
         * yi : 赴任 出行
         * ji : 冲猴 煞北 时冲甲申 地兵 三合 长生 司命
         */

        private String yangli;
        private String hours;
        private String des;
        private String yi;
        private String ji;

        public String getYangli() {
            return yangli;
        }

        public void setYangli(String yangli) {
            this.yangli = yangli;
        }

        public String getHours() {
            return hours;
        }

        public void setHours(String hours) {
            this.hours = hours;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getYi() {
            return yi;
        }

        public void setYi(String yi) {
            this.yi = yi;
        }

        public String getJi() {
            return ji;
        }

        public void setJi(String ji) {
            this.ji = ji;
        }
    }
}