package com.superc.waitmarket.bean;

import java.util.List;

public class ShopManageBean {

    /**
     * code : true
     * data : {"list":[{"ShopLogo":"/Image/shoplog/621accf9-e3cb-44ca-970b-827fa6519c2c.200.jpg","ShopName":"九八","checksuccesstime":1572537600000,"num":1,"count":0,"shopid":5342,"payamount":0},{"ShopLogo":"/Image/shoplogo/2cdea093-3879-45c3-93e0-0b2f4272261a.200.jpg","ShopName":"安卓2","checksuccesstime":1577501555000,"num":2,"count":0,"shopid":5463,"payamount":0},{"ShopLogo":"/Image/shoplogo/f0cf38d6-9386-4b1f-87cb-97a1b7ba085e.200.jpg","ShopName":"安卓3","checksuccesstime":1577501551000,"num":3,"count":0,"shopid":5464,"payamount":0}],"countMap":{"pendingReview":1,"maintenance":2,"pendingActivation":0}}
     * message :
     * returnTime : 201912281122370480
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
         * list : [{"ShopLogo":"/Image/shoplog/621accf9-e3cb-44ca-970b-827fa6519c2c.200.jpg","ShopName":"九八","checksuccesstime":1572537600000,"num":1,"count":0,"shopid":5342,"payamount":0},{"ShopLogo":"/Image/shoplogo/2cdea093-3879-45c3-93e0-0b2f4272261a.200.jpg","ShopName":"安卓2","checksuccesstime":1577501555000,"num":2,"count":0,"shopid":5463,"payamount":0},{"ShopLogo":"/Image/shoplogo/f0cf38d6-9386-4b1f-87cb-97a1b7ba085e.200.jpg","ShopName":"安卓3","checksuccesstime":1577501551000,"num":3,"count":0,"shopid":5464,"payamount":0}]
         * countMap : {"pendingReview":1,"maintenance":2,"pendingActivation":0}
         */

        private CountMapBean countMap;
        private List<ListBean> list;

        public CountMapBean getCountMap() {
            return countMap;
        }

        public void setCountMap(CountMapBean countMap) {
            this.countMap = countMap;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class CountMapBean {
            /**
             * pendingReview : 1
             * maintenance : 2
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

        public static class ListBean {
            /**
             * ShopLogo : /Image/shoplog/621accf9-e3cb-44ca-970b-827fa6519c2c.200.jpg
             * ShopName : 九八
             * checksuccesstime : 1572537600000
             * num : 1
             * count : 0
             * shopid : 5342
             * payamount : 0.0
             */

            private String ShopLogo;
            private String ShopName;
            private long checksuccesstime;
            private int num;
            private int count;
            private String shopid;
            private double payamount;

            public String getShopLogo() {
                return ShopLogo;
            }

            public void setShopLogo(String ShopLogo) {
                this.ShopLogo = ShopLogo;
            }

            public String getShopName() {
                return ShopName;
            }

            public void setShopName(String ShopName) {
                this.ShopName = ShopName;
            }

            public long getChecksuccesstime() {
                return checksuccesstime;
            }

            public void setChecksuccesstime(long checksuccesstime) {
                this.checksuccesstime = checksuccesstime;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getShopid() {
                return shopid;
            }

            public void setShopid(String shopid) {
                this.shopid = shopid;
            }

            public double getPayamount() {
                return payamount;
            }

            public void setPayamount(double payamount) {
                this.payamount = payamount;
            }
        }
    }
}
