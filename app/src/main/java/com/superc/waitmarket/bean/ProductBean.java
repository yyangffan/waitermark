package com.superc.waitmarket.bean;

import java.util.List;

public class ProductBean {

    /**
     * code : True
     * data : {"productList":[{"addresource":"2","timeList":[{"endtime":"03:00","starttime":"02:00"},{"endtime":"09:00","starttime":"06:00"},{"endtime":"13:00","starttime":"11:00"}],"limitvalidity":"90","actStatus":"7","coupontype":"3","weekdays":"1,2,3,4,5,6,7","receiveCount":0,"id":"AFF5BCB4-2893-4519-B641-273B07255F2B","useCount":0,"actName":"哦哟是我人做手语舞呜呜呜呜XP下午用肉在人做"},{"addresource":"1","timeList":[{"endtime":"23:59","starttime":"00:01"}],"limitvalidity":"30","actStatus":"7","coupontype":"2","weekdays":"1,2,3,4,5,6,7","receiveCount":0,"id":"EF6BBB9F-330D-4528-A6CA-A75DDDFA79CA","useCount":0,"actName":"8.9折"},{"addresource":"2","timeList":[{"endtime":"04:00","starttime":"01:00"},{"endtime":"11:00","starttime":"07:00"},{"endtime":"20:00","starttime":"16:00"}],"limitvalidity":"66","actStatus":"7","coupontype":"2","weekdays":"2,3,4,5,7","receiveCount":0,"id":"BE80B000-A593-47B5-BE3E-5547FB5BFFF1","useCount":0,"actName":"1.0折"},{"addresource":"2","timeList":[{"endtime":"23:59","starttime":"00:00"}],"limitvalidity":"90","actStatus":"7","coupontype":"1","weekdays":"1,2,3,4,5,6,7","receiveCount":0,"id":"B02113BC-CA86-4F8A-9F31-456CA38A1071","useCount":0,"actName":"每满￥28.0减￥6.0"},{"addresource":"2","timeList":[{"endtime":"23:59","starttime":"00:00"}],"limitvalidity":"90","actStatus":"7","coupontype":"3","weekdays":"1,2,3,4,5,6,7","receiveCount":0,"id":"B580376C-B4DE-4F71-BFBF-3FF0CF771036","useCount":0,"actName":"是谁"}]}
     * message :
     * returnTime : 201910291609320806
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
        private List<ProductListBean> productList;

        public List<ProductListBean> getProductList() {
            return productList;
        }

        public void setProductList(List<ProductListBean> productList) {
            this.productList = productList;
        }

        public static class ProductListBean {
            /**
             * addresource : 2
             * timeList : [{"endtime":"03:00","starttime":"02:00"},{"endtime":"09:00","starttime":"06:00"},{"endtime":"13:00","starttime":"11:00"}]
             * limitvalidity : 90
             * actStatus : 7
             * coupontype : 3
             * weekdays : 1,2,3,4,5,6,7
             * receiveCount : 0
             * id : AFF5BCB4-2893-4519-B641-273B07255F2B
             * useCount : 0
             * actName : 哦哟是我人做手语舞呜呜呜呜XP下午用肉在人做
             */

            private String addresource;
            private String limitvalidity;
            private String actStatus;
            private String coupontype;
            private String weekdays;
            private int receiveCount;
            private String id;
            private int useCount;
            private String actName;
            private String isremovedthenextday;
            private List<TimeListBean> timeList;

            public String getIsremovedthenextday() {
                return isremovedthenextday;
            }

            public void setIsremovedthenextday(String isremovedthenextday) {
                this.isremovedthenextday = isremovedthenextday;
            }

            public String getAddresource() {
                return addresource;
            }

            public void setAddresource(String addresource) {
                this.addresource = addresource;
            }

            public String getLimitvalidity() {
                return limitvalidity;
            }

            public void setLimitvalidity(String limitvalidity) {
                this.limitvalidity = limitvalidity;
            }

            public String getActStatus() {
                return actStatus;
            }

            public void setActStatus(String actStatus) {
                this.actStatus = actStatus;
            }

            public String getCoupontype() {
                return coupontype;
            }

            public void setCoupontype(String coupontype) {
                this.coupontype = coupontype;
            }

            public String getWeekdays() {
                return weekdays;
            }

            public void setWeekdays(String weekdays) {
                this.weekdays = weekdays;
            }

            public int getReceiveCount() {
                return receiveCount;
            }

            public void setReceiveCount(int receiveCount) {
                this.receiveCount = receiveCount;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getUseCount() {
                return useCount;
            }

            public void setUseCount(int useCount) {
                this.useCount = useCount;
            }

            public String getActName() {
                return actName;
            }

            public void setActName(String actName) {
                this.actName = actName;
            }

            public List<TimeListBean> getTimeList() {
                return timeList;
            }

            public void setTimeList(List<TimeListBean> timeList) {
                this.timeList = timeList;
            }

            public static class TimeListBean {
                /**
                 * endtime : 03:00
                 * starttime : 02:00
                 */

                private String endtime;
                private String starttime;

                public String getEndtime() {
                    return endtime;
                }

                public void setEndtime(String endtime) {
                    this.endtime = endtime;
                }

                public String getStarttime() {
                    return starttime;
                }

                public void setStarttime(String starttime) {
                    this.starttime = starttime;
                }
            }
        }
    }
}
