package com.superc.waitmarket.bean;

import java.util.List;

public class JihuoBean {

    /**
     * code : true
     * data : {"count":1,"list":[{"auditstatus":5,"ShopLogo":"/Image/shoplogo/f0cf38d6-9386-4b1f-87cb-97a1b7ba085e.200.jpg","ShopName":"安卓3","ShopAddress":"天津","addtime":"2019-12-27 14:24:57","num":1,"shopid":5464,"mes":"等待成绩领取"}]}
     * message :
     * returnTime : 201912271832540720
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
         * count : 1
         * list : [{"auditstatus":5,"ShopLogo":"/Image/shoplogo/f0cf38d6-9386-4b1f-87cb-97a1b7ba085e.200.jpg","ShopName":"安卓3","ShopAddress":"天津","addtime":"2019-12-27 14:24:57","num":1,"shopid":5464,"mes":"等待成绩领取"}]
         */

        private String count;
        private List<ListBean> list;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * auditstatus : 5
             * ShopLogo : /Image/shoplogo/f0cf38d6-9386-4b1f-87cb-97a1b7ba085e.200.jpg
             * ShopName : 安卓3
             * ShopAddress : 天津
             * addtime : 2019-12-27 14:24:57
             * num : 1
             * shopid : 5464
             * mes : 等待成绩领取
             */

            private int auditstatus;
            private String ShopLogo;
            private String ShopName;
            private String ShopAddress;
            private String addtime;
            private int num;
            private String shopid;
            private String mes;
            private String AddPerson;

            public String getAddPerson() {
                return AddPerson;
            }

            public void setAddPerson(String addPerson) {
                AddPerson = addPerson;
            }

            public int getAuditstatus() {
                return auditstatus;
            }

            public void setAuditstatus(int auditstatus) {
                this.auditstatus = auditstatus;
            }

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

            public String getShopAddress() {
                return ShopAddress;
            }

            public void setShopAddress(String ShopAddress) {
                this.ShopAddress = ShopAddress;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String getShopid() {
                return shopid;
            }

            public void setShopid(String shopid) {
                this.shopid = shopid;
            }

            public String getMes() {
                return mes;
            }

            public void setMes(String mes) {
                this.mes = mes;
            }
        }
    }
}
