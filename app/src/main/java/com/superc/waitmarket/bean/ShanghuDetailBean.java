package com.superc.waitmarket.bean;

import java.util.List;

public class ShanghuDetailBean {

    /**
     * code : True
     * data : {"count":11,"dataList":[{"count1":2,"date":"2019-12-15","count2":0},{"count1":1,"date":"2019-12-14","count2":0},{"count1":2,"date":"2019-12-12","count2":0},{"count1":1,"date":"2019-12-07","count2":0},{"count1":1,"date":"2019-12-05","count2":0},{"count1":3,"date":"2019-12-04","count2":0},{"count1":1,"date":"2019-12-01","count2":0}]}
     * message : 获取成功
     * returnTime : 201912201426410862
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
         * count : 11
         * dataList : [{"count1":2,"date":"2019-12-15","count2":0},{"count1":1,"date":"2019-12-14","count2":0},{"count1":2,"date":"2019-12-12","count2":0},{"count1":1,"date":"2019-12-07","count2":0},{"count1":1,"date":"2019-12-05","count2":0},{"count1":3,"date":"2019-12-04","count2":0},{"count1":1,"date":"2019-12-01","count2":0}]
         */

        private String count;
        private List<DataListBean> dataList;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public List<DataListBean> getDataList() {
            return dataList;
        }

        public void setDataList(List<DataListBean> dataList) {
            this.dataList = dataList;
        }

        public static class DataListBean {
            /**
             * count1 : 2
             * date : 2019-12-15
             * count2 : 0
             */

            private int count1;
            private String date;
            private int count2;

            public int getCount1() {
                return count1;
            }

            public void setCount1(int count1) {
                this.count1 = count1;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public int getCount2() {
                return count2;
            }

            public void setCount2(int count2) {
                this.count2 = count2;
            }
        }
    }
}
