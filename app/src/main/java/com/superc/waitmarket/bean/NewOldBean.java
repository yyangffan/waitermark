package com.superc.waitmarket.bean;

import java.util.List;

public class NewOldBean {

    /**
     * code : true
     * data : [{"id":"B10D0F94-5395-493C-A181-C2C708151243","activitystatus":"1","isnewcustomer":"0","actName":"体验佛佛虎扑调速器额维沃"},{"id":"B10D0F94-5395-493C-A181-C2C708151243","activitystatus":"1","isnewcustomer":"0","actName":"体验佛佛虎扑调速器额维沃"}]
     * message :
     * returnTime : 202001031720510977
     */

    private boolean code;
    private String message;
    private String returnTime;
    private List<DataBean> data;

    public boolean isCode() {
        return code;
    }

    public void setCode(boolean code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : B10D0F94-5395-493C-A181-C2C708151243
         * activitystatus : 1
         * isnewcustomer : 0
         * actName : 体验佛佛虎扑调速器额维沃
         */

        private String id;
        private String activitystatus;
        private String isnewcustomer;
        private String actName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getActivitystatus() {
            return activitystatus;
        }

        public void setActivitystatus(String activitystatus) {
            this.activitystatus = activitystatus;
        }

        public String getIsnewcustomer() {
            return isnewcustomer;
        }

        public void setIsnewcustomer(String isnewcustomer) {
            this.isnewcustomer = isnewcustomer;
        }

        public String getActName() {
            return actName;
        }

        public void setActName(String actName) {
            this.actName = actName;
        }
    }
}
