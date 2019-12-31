package com.superc.waitmarket.bean;

import java.util.List;

public class MerPoolBean {

    /**
     * code : true
     * data : [{"auditstatus":"3","shopid":5464,"ShopLogo":"/Image/shoplogo/f0cf38d6-9386-4b1f-87cb-97a1b7ba085e.200.jpg","ShopName":"安卓3","ShopAddress":"天津","num":1},{"auditstatus":"3","shopid":5463,"ShopLogo":"/Image/shoplogo/2cdea093-3879-45c3-93e0-0b2f4272261a.200.jpg","ShopName":"安卓2","ShopAddress":"天宣公寓","num":2},{"auditstatus":"2","shopid":5451,"ShopLogo":"/Image/shoplogo/b2dddf1f-7498-4a4f-92bf-032aa876b985.200.jpg","ShopName":"坚果墙智慧水管家","ShopAddress":"泰兴南路100号","num":3},{"auditstatus":"2","shopid":5450,"ShopLogo":"/Image/shoplogo/dfc16a8f-f515-4698-be1f-5dd0bd3b3a0c.200.jpeg","ShopName":"诺兰德中医馆","ShopAddress":"泰兴南路100号","num":4},{"auditstatus":"2","shopid":5449,"ShopLogo":"/Image/shoplogo/4769e187-0f44-4ffa-9f26-77e25c2c87aa.200.jpg","ShopName":"海牛烤生蚝","ShopAddress":"泰兴南路100号","num":5},{"auditstatus":"2","shopid":5448,"ShopLogo":"/Image/shoplogo/d6518ae7-c72f-4561-9937-e51ae99895ca.200.jpg","ShopName":"健身工作室","ShopAddress":"东丽区津滨大道","num":6},{"auditstatus":"2","shopid":5445,"ShopLogo":"/Image/shoplogo/abf005af-7b41-437d-9d01-e8a491828ff1.200.jpeg","ShopName":"渔港海鲜（河东分店）","ShopAddress":"泰兴南路天创企业孵化基地","num":7},{"auditstatus":"2","shopid":5444,"ShopLogo":"/Image/shoplogo/725fec55-6d81-47e3-9245-43a3433d1a02.200.jpg","ShopName":"今天是周日","ShopAddress":"111","num":8},{"auditstatus":"2","shopid":5442,"ShopLogo":"/Image/shoplogo/bcab0b37-1e94-4161-a6c7-f3938435f751.200.jpg","ShopName":"张亮麻辣啦啦啦烫","ShopAddress":"泰兴南路","num":9}]
     * message :
     * returnTime : 201912271556490329
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
         * auditstatus : 3
         * shopid : 5464
         * ShopLogo : /Image/shoplogo/f0cf38d6-9386-4b1f-87cb-97a1b7ba085e.200.jpg
         * ShopName : 安卓3
         * ShopAddress : 天津
         * num : 1
         */

        private String auditstatus;
        private String shopid;
        private String ShopLogo;
        private String ShopName;
        private String ShopAddress;
        private String num;

        public String getAuditstatus() {
            return auditstatus;
        }

        public void setAuditstatus(String auditstatus) {
            this.auditstatus = auditstatus;
        }

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
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

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }
    }
}
