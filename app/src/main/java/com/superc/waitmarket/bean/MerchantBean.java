package com.superc.waitmarket.bean;

import java.util.List;

public class MerchantBean {

    /**
     * code : True
     * data : {"sum":30,"dataList":[{"date":"2019-12-16","count":2},{"date":"2019-12-15","count":2},{"date":"2019-12-14","count":2},{"date":"2019-12-13","count":1},{"date":"2019-12-12","count":2},{"date":"2019-12-11","count":3},{"date":"2019-12-10","count":1},{"date":"2019-12-09","count":1},{"date":"2019-12-07","count":1},{"date":"2019-12-06","count":2}]}
     * message : 获取成功
     * returnTime : 201912201551120607
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
         * sum : 30
         * dataList : [{"date":"2019-12-16","count":2},{"date":"2019-12-15","count":2},{"date":"2019-12-14","count":2},{"date":"2019-12-13","count":1},{"date":"2019-12-12","count":2},{"date":"2019-12-11","count":3},{"date":"2019-12-10","count":1},{"date":"2019-12-09","count":1},{"date":"2019-12-07","count":1},{"date":"2019-12-06","count":2}]
         */

        private String sum;
        private List<DataListBean> dataList;

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
             * date : 2019-12-16
             * count : 2
             */

            private String date;
            private int count;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }
        }
    }
}
