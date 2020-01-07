package com.superc.waitmarket.bean;

import java.util.List;

public class MainTainBean {

    /**
     * code : true
     * data : {"count":5,"list":[{"staus":0,"addTime":"2019-12-23 18:55:57","shopLogo":"/Image/shoplogo/22a8e4e5-aa6c-4ea3-a84f-f166337c4a43.200.jpg","auditmessage":"信息维护中","rejectreason":"","shopName":"我","shopId":"179","shopAddress":"天浦园"},{"staus":0,"addTime":"2019-12-23 18:53:35","shopLogo":"/Image/shoplogo/22a8e4e5-aa6c-4ea3-a84f-f166337c4a43.200.jpg","auditmessage":"信息维护中","rejectreason":"","shopName":"我","shopId":"178","shopAddress":"天浦园"},{"staus":0,"addTime":"2019-12-23 18:41:43","shopLogo":"/Image/shoplogo/92cede44-f634-4d83-9dfe-89950a563939.200.jpg","auditmessage":"信息维护中","rejectreason":"","shopName":"靠谱","shopId":"177","shopAddress":"滨海万达广场"},{"staus":0,"addTime":"2019-12-23 18:41:13","shopLogo":"/Image/shoplogo/92cede44-f634-4d83-9dfe-89950a563939.200.jpg","auditmessage":"信息维护中","rejectreason":"","shopName":"靠谱","shopId":"176","shopAddress":"滨海万达广场"},{"staus":0,"addTime":"2019-12-23 17:52:14","shopLogo":"/Image/shoplogo/f9544d72-51b7-4ec2-979d-cb659a3de371.200.png","auditmessage":"信息维护中","rejectreason":"","shopName":"GYM的店铺","shopId":"175","shopAddress":"万达广场(河东店)津滨大道53号万达广场L2层"}]}
     * message :
     * returnTime : 201912240941330261
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
         * count : 5
         * list : [{"staus":0,"addTime":"2019-12-23 18:55:57","shopLogo":"/Image/shoplogo/22a8e4e5-aa6c-4ea3-a84f-f166337c4a43.200.jpg","auditmessage":"信息维护中","rejectreason":"","shopName":"我","shopId":"179","shopAddress":"天浦园"},{"staus":0,"addTime":"2019-12-23 18:53:35","shopLogo":"/Image/shoplogo/22a8e4e5-aa6c-4ea3-a84f-f166337c4a43.200.jpg","auditmessage":"信息维护中","rejectreason":"","shopName":"我","shopId":"178","shopAddress":"天浦园"},{"staus":0,"addTime":"2019-12-23 18:41:43","shopLogo":"/Image/shoplogo/92cede44-f634-4d83-9dfe-89950a563939.200.jpg","auditmessage":"信息维护中","rejectreason":"","shopName":"靠谱","shopId":"177","shopAddress":"滨海万达广场"},{"staus":0,"addTime":"2019-12-23 18:41:13","shopLogo":"/Image/shoplogo/92cede44-f634-4d83-9dfe-89950a563939.200.jpg","auditmessage":"信息维护中","rejectreason":"","shopName":"靠谱","shopId":"176","shopAddress":"滨海万达广场"},{"staus":0,"addTime":"2019-12-23 17:52:14","shopLogo":"/Image/shoplogo/f9544d72-51b7-4ec2-979d-cb659a3de371.200.png","auditmessage":"信息维护中","rejectreason":"","shopName":"GYM的店铺","shopId":"175","shopAddress":"万达广场(河东店)津滨大道53号万达广场L2层"}]
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
             * staus : 0
             * addTime : 2019-12-23 18:55:57
             * shopLogo : /Image/shoplogo/22a8e4e5-aa6c-4ea3-a84f-f166337c4a43.200.jpg
             * auditmessage : 信息维护中
             * rejectreason :
             * shopName : 我
             * shopId : 179
             * shopAddress : 天浦园
             */

            private String staus;
            private String addTime;
            private String shopLogo;
            private String auditmessage;
            private String rejectreason;
            private String shopName;
            private String shopId;
            private String shopAddress;
            private boolean check;

            public boolean isCheck() {
                return check;
            }

            public void setCheck(boolean check) {
                this.check = check;
            }

            public String getStaus() {
                return staus;
            }

            public void setStaus(String staus) {
                this.staus = staus;
            }

            public String getAddTime() {
                return addTime;
            }

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public String getShopLogo() {
                return shopLogo;
            }

            public void setShopLogo(String shopLogo) {
                this.shopLogo = shopLogo;
            }

            public String getAuditmessage() {
                return auditmessage;
            }

            public void setAuditmessage(String auditmessage) {
                this.auditmessage = auditmessage;
            }

            public String getRejectreason() {
                return rejectreason;
            }

            public void setRejectreason(String rejectreason) {
                this.rejectreason = rejectreason;
            }

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

            public String getShopAddress() {
                return shopAddress;
            }

            public void setShopAddress(String shopAddress) {
                this.shopAddress = shopAddress;
            }
        }
    }
}
