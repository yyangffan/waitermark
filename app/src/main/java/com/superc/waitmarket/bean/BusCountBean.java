package com.superc.waitmarket.bean;

public class BusCountBean {

    /**
     * code : true
     * data : {"countMap":{"pendingReview":2,"maintenance":4,"pendingActivation":0}}
     * message :
     * returnTime : 201912281553580530
     */

    private boolean code;
    private DataBean data;
    private String message;
    private String returnTime;

    public boolean isCode() {
        return code;
    }

    public void setCode(boolean code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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

    public static class DataBean {
        /**
         * countMap : {"pendingReview":2,"maintenance":4,"pendingActivation":0}
         */

        private CountMapBean countMap;

        public CountMapBean getCountMap() {
            return countMap;
        }

        public void setCountMap(CountMapBean countMap) {
            this.countMap = countMap;
        }

        public static class CountMapBean {
            /**
             * pendingReview : 2
             * maintenance : 4
             * pendingActivation : 0
             */

            private int pendingReview;
            private int maintenance;
            private int pendingActivation;

            public int getPendingReview() {
                return pendingReview;
            }

            public void setPendingReview(int pendingReview) {
                this.pendingReview = pendingReview;
            }

            public int getMaintenance() {
                return maintenance;
            }

            public void setMaintenance(int maintenance) {
                this.maintenance = maintenance;
            }

            public int getPendingActivation() {
                return pendingActivation;
            }

            public void setPendingActivation(int pendingActivation) {
                this.pendingActivation = pendingActivation;
            }
        }
    }
}
