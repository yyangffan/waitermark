package com.superc.waitmarket.bean;

import java.util.List;

public class PayflowDetailBean {

    /**
     * code : True
     * data : {"count":1,"sum":0,"dataList":[{"payamount":0,"shopname":"测试111","shopaddress":"天津1111","shoplogo":"/Image/shoplogo/b8db8a3e-9eda-4f59-8f8e-c3bd266476fa.200.png"}]}
     * message : 获取成功
     * returnTime : 201912201529040358
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
         * sum : 0.0
         * dataList : [{"payamount":0,"shopname":"测试111","shopaddress":"天津1111","shoplogo":"/Image/shoplogo/b8db8a3e-9eda-4f59-8f8e-c3bd266476fa.200.png"}]
         */

        private String count;
        private String sum;
        private List<DataListBean> dataList;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
        }

        public List<DataListBean> getDataList() {
            return dataList;
        }

        public void setDataList(List<DataListBean> dataList) {
            this.dataList = dataList;
        }

        public static class DataListBean {
            /**
             * payamount : 0.0
             * shopname : 测试111
             * shopaddress : 天津1111
             * shoplogo : /Image/shoplogo/b8db8a3e-9eda-4f59-8f8e-c3bd266476fa.200.png
             */

            private double payamount;
            private String shopname;
            private String shopaddress;
            private String shoplogo;

            public double getPayamount() {
                return payamount;
            }

            public void setPayamount(double payamount) {
                this.payamount = payamount;
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
