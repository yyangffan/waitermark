package com.superc.waitmarket.bean;

import java.util.List;

public class ExpandDetailBean {

    /**
     * code : True
     * data : {"count":1,"dataList":[{"type":1,"shopname":"八爷手擀炸酱面（辛庄店）","shopaddress":"新庄镇新濠广场底商","shoplogo":"http://api.qujie365.com/upload/qzx_20180327141741.png"}]}
     * message : 获取成功
     * returnTime : 201912201444020655
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
         * dataList : [{"type":1,"shopname":"八爷手擀炸酱面（辛庄店）","shopaddress":"新庄镇新濠广场底商","shoplogo":"http://api.qujie365.com/upload/qzx_20180327141741.png"}]
         */

        private int count;
        private List<DataListBean> dataList;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
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
             * type : 1
             * shopname : 八爷手擀炸酱面（辛庄店）
             * shopaddress : 新庄镇新濠广场底商
             * shoplogo : http://api.qujie365.com/upload/qzx_20180327141741.png
             */

            private int type;
            private String shopname;
            private String shopaddress;
            private String shoplogo;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getShopname() {
                return shopname;
            }

            public void setShopname(String shopname) {
                this.shopname = shopname;
            }

            public String getShopaddress() {
                return shopaddress;
            }

            public void setShopaddress(String shopaddress) {
                this.shopaddress = shopaddress;
            }

            public String getShoplogo() {
                return shoplogo;
            }

            public void setShoplogo(String shoplogo) {
                this.shoplogo = shoplogo;
            }
        }
    }
}
