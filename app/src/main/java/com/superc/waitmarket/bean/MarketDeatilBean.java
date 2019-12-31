package com.superc.waitmarket.bean;

import java.util.List;

public class MarketDeatilBean {

    /**
     * code : True
     * data : {"limitvalidity":90,"fullamount":"69.00","coupontype":1,"weekdays":"1,2,3,4,7","isremovedthenextday":0,"discount":"","actTime":[{"startTime":"00:02","endTime":"02:02"},{"startTime":"04:02","endTime":"06:02"},{"startTime":"08:02","endTime":"10:02"}],"holiday":0,"reduceamount":"9.00","usagerules":"需要预约，消费高峰期可能需要等位#商家提供免费WIFI#发票内容请咨询商家#不可自带酒水","addresource":2,"activitystatus":4,"experienceticketname":"","addtime":1572426990323,"customusagerules":"虐屠夫的","productname":"","shopid":2,"id":"DC70E0EA-115B-4FF7-B73C-AC92F30244D8","delstatus":0,"effecttype":2,"isweekdays":2,"holidayDate":[{"endDate":"2019-06-01","startDate":"2019-03-01"},{"endDate":"2019-06-05","startDate":"2019-06-01"},{"endDate":"2019-08-08","startDate":"2019-08-05"}]}
     * message :
     * returnTime : 201910301731460555
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
         * limitvalidity : 90
         * fullamount : 69.00
         * coupontype : 1
         * weekdays : 1,2,3,4,7
         * isremovedthenextday : 0
         * discount :
         * actTime : [{"startTime":"00:02","endTime":"02:02"},{"startTime":"04:02","endTime":"06:02"},{"startTime":"08:02","endTime":"10:02"}]
         * holiday : 0
         * reduceamount : 9.00
         * usagerules : 需要预约，消费高峰期可能需要等位#商家提供免费WIFI#发票内容请咨询商家#不可自带酒水
         * addresource : 2
         * activitystatus : 4
         * experienceticketname :
         * addtime : 1572426990323
         * customusagerules : 虐屠夫的
         * productname :
         * shopid : 2
         * id : DC70E0EA-115B-4FF7-B73C-AC92F30244D8
         * delstatus : 0
         * effecttype : 2
         * isweekdays : 2
         * holidayDate : [{"endDate":"2019-06-01","startDate":"2019-03-01"},{"endDate":"2019-06-05","startDate":"2019-06-01"},{"endDate":"2019-08-08","startDate":"2019-08-05"}]
         * isnewcustomer : 2
         * maxdiscountamount : 2
         * voucheramount : 2
         * startingpoint : 2
         * maxnumber : 2
         *
         *
         */

        private int limitvalidity;
        private String fullamount;
        private int coupontype;
        private String weekdays;
        private int isremovedthenextday;
        private String discount;
        private int holiday;
        private String reduceamount;
        private String usagerules;
        private String rejectreson;
        private int addresource;
        private int activitystatus;
        private String experienceticketname;
        private long addtime;
        private String customusagerules;
        private String productname;
        private int shopid;
        private String id;
        private int delstatus;
        private int effecttype;
        private int isweekdays;
        private List<ActTimeBean> actTime;
        private List<HolidayDateBean> holidayDate;
        private String isnewcustomer;
        private String maxdiscountamount;
        private String voucheramount;
        private String startingpoint;
        private String maxnumber;

        public String getIsnewcustomer() {
            return isnewcustomer;
        }

        public void setIsnewcustomer(String isnewcustomer) {
            this.isnewcustomer = isnewcustomer;
        }

        public String getMaxdiscountamount() {
            return maxdiscountamount;
        }

        public void setMaxdiscountamount(String maxdiscountamount) {
            this.maxdiscountamount = maxdiscountamount;
        }

        public String getVoucheramount() {
            return voucheramount;
        }

        public void setVoucheramount(String voucheramount) {
            this.voucheramount = voucheramount;
        }

        public String getStartingpoint() {
            return startingpoint;
        }

        public void setStartingpoint(String startingpoint) {
            this.startingpoint = startingpoint;
        }

        public String getMaxnumber() {
            return maxnumber;
        }

        public void setMaxnumber(String maxnumber) {
            this.maxnumber = maxnumber;
        }

        public String getRejectreson() {
            return rejectreson;
        }

        public void setRejectreson(String rejectreson) {
            this.rejectreson = rejectreson;
        }

        public int getLimitvalidity() {
            return limitvalidity;
        }

        public void setLimitvalidity(int limitvalidity) {
            this.limitvalidity = limitvalidity;
        }

        public String getFullamount() {
            return fullamount;
        }

        public void setFullamount(String fullamount) {
            this.fullamount = fullamount;
        }

        public int getCoupontype() {
            return coupontype;
        }

        public void setCoupontype(int coupontype) {
            this.coupontype = coupontype;
        }

        public String getWeekdays() {
            return weekdays;
        }

        public void setWeekdays(String weekdays) {
            this.weekdays = weekdays;
        }

        public int getIsremovedthenextday() {
            return isremovedthenextday;
        }

        public void setIsremovedthenextday(int isremovedthenextday) {
            this.isremovedthenextday = isremovedthenextday;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public int getHoliday() {
            return holiday;
        }

        public void setHoliday(int holiday) {
            this.holiday = holiday;
        }

        public String getReduceamount() {
            return reduceamount;
        }

        public void setReduceamount(String reduceamount) {
            this.reduceamount = reduceamount;
        }

        public String getUsagerules() {
            return usagerules;
        }

        public void setUsagerules(String usagerules) {
            this.usagerules = usagerules;
        }

        public int getAddresource() {
            return addresource;
        }

        public void setAddresource(int addresource) {
            this.addresource = addresource;
        }

        public int getActivitystatus() {
            return activitystatus;
        }

        public void setActivitystatus(int activitystatus) {
            this.activitystatus = activitystatus;
        }

        public String getExperienceticketname() {
            return experienceticketname;
        }

        public void setExperienceticketname(String experienceticketname) {
            this.experienceticketname = experienceticketname;
        }

        public long getAddtime() {
            return addtime;
        }

        public void setAddtime(long addtime) {
            this.addtime = addtime;
        }

        public String getCustomusagerules() {
            return customusagerules;
        }

        public void setCustomusagerules(String customusagerules) {
            this.customusagerules = customusagerules;
        }

        public String getProductname() {
            return productname;
        }

        public void setProductname(String productname) {
            this.productname = productname;
        }

        public int getShopid() {
            return shopid;
        }

        public void setShopid(int shopid) {
            this.shopid = shopid;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getDelstatus() {
            return delstatus;
        }

        public void setDelstatus(int delstatus) {
            this.delstatus = delstatus;
        }

        public int getEffecttype() {
            return effecttype;
        }

        public void setEffecttype(int effecttype) {
            this.effecttype = effecttype;
        }

        public int getIsweekdays() {
            return isweekdays;
        }

        public void setIsweekdays(int isweekdays) {
            this.isweekdays = isweekdays;
        }

        public List<ActTimeBean> getActTime() {
            return actTime;
        }

        public void setActTime(List<ActTimeBean> actTime) {
            this.actTime = actTime;
        }

        public List<HolidayDateBean> getHolidayDate() {
            return holidayDate;
        }

        public void setHolidayDate(List<HolidayDateBean> holidayDate) {
            this.holidayDate = holidayDate;
        }

        public static class ActTimeBean {
            /**
             * startTime : 00:02
             * endTime : 02:02
             */

            private String startTime;
            private String endTime;

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }
        }

        public static class HolidayDateBean {
            /**
             * endDate : 2019-06-01
             * startDate : 2019-03-01
             */

            private String endDate;
            private String startDate;

            public String getEndDate() {
                return endDate;
            }

            public void setEndDate(String endDate) {
                this.endDate = endDate;
            }

            public String getStartDate() {
                return startDate;
            }

            public void setStartDate(String startDate) {
                this.startDate = startDate;
            }
        }
    }
}
