package com.superc.waitmarket.bean;

import java.util.List;

public class ProfitBean {

    /**
     * code : true
     * data : [{"allSum":"6.0","detailList":[{"amount":3,"balance":105,"month":"2019-12","type":1,"addtime":"2019-12-12 11:34:51"},{"amount":2,"balance":102,"month":"2019-12","type":2,"addtime":"2019-12-11 16:34:51"},{"amount":1,"balance":100,"month":"2019-12","type":1,"addtime":"2019-12-11 16:33:51"}],"marketingAmount":"2.0","expandAmount":"4.0","month":"2019-12"},{"allSum":"116.0","detailList":[{"amount":111,"balance":221,"month":"2019-11","type":2,"addtime":"2019-11-12 19:34:51"},{"amount":5,"balance":110,"month":"2019-11","type":1,"addtime":"2019-11-01 19:34:51"}],"marketingAmount":"111.0","expandAmount":"5.0","month":"2019-11"},{"allSum":"0.0","marketingAmount":"0.0","expandAmount":"0.0","month":"2019-10"},{"allSum":"0.0","marketingAmount":"0.0","expandAmount":"0.0","month":"2019-09"},{"allSum":"0.0","marketingAmount":"0.0","expandAmount":"0.0","month":"2019-08"},{"allSum":"0.0","marketingAmount":"0.0","expandAmount":"0.0","month":"2019-07"},{"allSum":"0.0","marketingAmount":"0.0","expandAmount":"0.0","month":"2019-06"},{"allSum":"0.0","marketingAmount":"0.0","expandAmount":"0.0","month":"2019-05"},{"allSum":"0.0","marketingAmount":"0.0","expandAmount":"0.0","month":"2019-04"},{"allSum":"0.0","marketingAmount":"0.0","expandAmount":"0.0","month":"2019-03"},{"allSum":"0.0","marketingAmount":"0.0","expandAmount":"0.0","month":"2019-02"}]
     * message :
     * returnTime : 201912211159020466
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
         * allSum : 6.0
         * detailList : [{"amount":3,"balance":105,"month":"2019-12","type":1,"addtime":"2019-12-12 11:34:51"},{"amount":2,"balance":102,"month":"2019-12","type":2,"addtime":"2019-12-11 16:34:51"},{"amount":1,"balance":100,"month":"2019-12","type":1,"addtime":"2019-12-11 16:33:51"}]
         * marketingAmount : 2.0
         * expandAmount : 4.0
         * month : 2019-12
         */

        private String allSum;
        private String marketingAmount;
        private String expandAmount;
        private String month;
        private List<DetailListBean> detailList;

        public String getAllSum() {
            return allSum;
        }

        public void setAllSum(String allSum) {
            this.allSum = allSum;
        }

        public String getMarketingAmount() {
            return marketingAmount;
        }

        public void setMarketingAmount(String marketingAmount) {
            this.marketingAmount = marketingAmount;
        }

        public String getExpandAmount() {
            return expandAmount;
        }

        public void setExpandAmount(String expandAmount) {
            this.expandAmount = expandAmount;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public List<DetailListBean> getDetailList() {
            return detailList;
        }

        public void setDetailList(List<DetailListBean> detailList) {
            this.detailList = detailList;
        }

        public static class DetailListBean {
            /**
             * amount : 3.0
             * balance : 105.0
             * month : 2019-12
             * type : 1
             * addtime : 2019-12-12 11:34:51
             */

            private String amount;
            private double balance;
            private String month;
            private int type;
            private String addtime;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public double getBalance() {
                return balance;
            }

            public void setBalance(double balance) {
                this.balance = balance;
            }

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
                this.month = month;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }
        }
    }
}
