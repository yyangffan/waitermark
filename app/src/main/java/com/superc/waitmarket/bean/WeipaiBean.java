package com.superc.waitmarket.bean;

import java.util.List;

public class WeipaiBean {
    /**
     * code : true
     * data : {"count":0,"list":[{"auditstatus":5,"ShopLogo":"/Image/shoplogo/e34ae566-279d-4a41-9df8-44b018a684e7.200.jpeg","ShopName":"测试小二委派1","ShopAddress":"测试小二委派1","addtime":"2020-01-14 17:44:27","auditmessage":"等待成绩领取","num":1,"rejectreason":"","shopid":5506}]}
     * message :
     * returnTime : 202001150954580497
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
         * count : 0
         * list : [{"auditstatus":5,"ShopLogo":"/Image/shoplogo/e34ae566-279d-4a41-9df8-44b018a684e7.200.jpeg","ShopName":"测试小二委派1","ShopAddress":"测试小二委派1","addtime":"2020-01-14 17:44:27","auditmessage":"等待成绩领取","num":1,"rejectreason":"","shopid":5506}]
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
             * ShopLogo : /Image/shoplogo/e34ae566-279d-4a41-9df8-44b018a684e7.200.jpeg
             * ShopName : 测试小二委派1
             * ShopAddress : 测试小二委派1
             * addtime : 2020-01-14 17:44:27
             * auditmessage : 等待成绩领取
             * num : 1
             * rejectreason :
             * shopid : 5506
             */

            private int auditstatus;
            private String ShopLogo;
            private String ShopName;
            private String ShopAddress;
            private String addtime;
            private String auditmessage;
            private int num;
            private String rejectreason;
            private String shopid;
            private String channel;

            public String getChannel() {
                return channel;
            }

            public void setChannel(String channel) {
                this.channel = channel;
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

            public String getAuditmessage() {
                return auditmessage;
            }

            public void setAuditmessage(String auditmessage) {
                this.auditmessage = auditmessage;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String getRejectreason() {
                return rejectreason;
            }

            public void setRejectreason(String rejectreason) {
                this.rejectreason = rejectreason;
            }

            public String getShopid() {
                return shopid;
            }

            public void setShopid(String shopid) {
                this.shopid = shopid;
            }
        }
    }
}
