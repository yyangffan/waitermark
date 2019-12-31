package com.superc.waitmarket.bean;

import java.util.List;

public class ShenheBean {

    /**
     * code : true
     * data : {"count":1,"list":[{"shopName":"安卓3","shopId":"238","addTime":"2019-12-26 19:47:44","shopAddress":"天津","message":"等待入网审核","shopLogo":"/Image/shoplogo/f0cf38d6-9386-4b1f-87cb-97a1b7ba085e.200.jpg"}]}
     * message :
     * returnTime : 201912270915330358
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
         * list : [{"shopName":"安卓3","shopId":"238","addTime":"2019-12-26 19:47:44","shopAddress":"天津","message":"等待入网审核","shopLogo":"/Image/shoplogo/f0cf38d6-9386-4b1f-87cb-97a1b7ba085e.200.jpg"}]
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
             * shopName : 安卓3
             * shopId : 238
             * addTime : 2019-12-26 19:47:44
             * shopAddress : 天津
             * message : 等待入网审核
             * shopLogo : /Image/shoplogo/f0cf38d6-9386-4b1f-87cb-97a1b7ba085e.200.jpg
             */

            private String shopName;
            private String shopId;
            private String addTime;
            private String shopAddress;
            private String message;
            private String shopLogo;

            public String getShopName() {
                return shopName;
            }

            public void setShopName(String shopName) {
                this.shopName = shopName;
            }

            public String getShopId() {
                return shopId;
            }

            public void setShopId(String shopId) {
                this.shopId = shopId;
            }

            public String getAddTime() {
                return addTime;
            }

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public String getShopAddress() {
                return shopAddress;
            }

            public void setShopAddress(String shopAddress) {
                this.shopAddress = shopAddress;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getShopLogo() {
                return shopLogo;
            }

            public void setShopLogo(String shopLogo) {
                this.shopLogo = shopLogo;
            }
        }
    }
}
