package com.superc.waitmarket.bean;

import java.util.List;

public class ShenheBean {

    /**
     * code : true
     * data : {"count":1,"list":[{"shopid":258,"mes":"等待商家审核","ShopLogo":"/Image/shoplogo/19d2a1e8-cfcb-4076-8c51-e7a87a7002e0.200.jpg","ShopName":"老干妈海鲜a","ShopAddress":"浙江路17号(浙江路与曲阜道交口汉庭全季酒店旁边)土大力(小白楼店)","addtime":"2019-12-31 10:59:34"}]}
     * message :
     * returnTime : 202001041430000917
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
         * list : [{"shopid":258,"mes":"等待商家审核","ShopLogo":"/Image/shoplogo/19d2a1e8-cfcb-4076-8c51-e7a87a7002e0.200.jpg","ShopName":"老干妈海鲜a","ShopAddress":"浙江路17号(浙江路与曲阜道交口汉庭全季酒店旁边)土大力(小白楼店)","addtime":"2019-12-31 10:59:34"}]
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
             * shopid : 258
             * mes : 等待商家审核
             * ShopLogo : /Image/shoplogo/19d2a1e8-cfcb-4076-8c51-e7a87a7002e0.200.jpg
             * ShopName : 老干妈海鲜a
             * ShopAddress : 浙江路17号(浙江路与曲阜道交口汉庭全季酒店旁边)土大力(小白楼店)
             * addtime : 2019-12-31 10:59:34
             */

            private String shopid;
            private String mes;
            private String ShopLogo;
            private String ShopName;
            private String ShopAddress;
            private String addtime;

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
        }
    }
}
