package com.superc.waitmarket.bean;

import java.util.List;

public class PayflowBean {

    /**
     * code : True
     * data : {"count":6,"sum":0,"dataList":[{"date":"2019-12-11","count":1,"sum":0},{"date":"2019-12-05","count":3,"sum":0},{"date":"2019-12-02","count":1,"sum":0},{"date":"2019-12-01","count":1,"sum":0}]}
     * message : 获取成功
     * returnTime : 201912201519340542
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
         * count : 6
         * sum : 0.0
         * dataList : [{"date":"2019-12-11","count":1,"sum":0},{"date":"2019-12-05","count":3,"sum":0},{"date":"2019-12-02","count":1,"sum":0},{"date":"2019-12-01","count":1,"sum":0}]
         */

        private String count;
        private String sum;
        private List<DataListBean> dataList;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
        }

        public List<DataListBean> getDataList() {
            return dataList;
        }

        public void setDataList(List<DataListBean> dataList) {
            this.dataList = dataList;
        }

        public static class DataListBean {
            /**
             * date : 2019-12-11
             * count : 1
             * sum : 0.0
             */

            private String date;
            private String count;
            private String sum;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public String getSum() {
                return sum;
            }

            public void setSum(String sum) {
                this.sum = sum;
            }
        }
    }
}
