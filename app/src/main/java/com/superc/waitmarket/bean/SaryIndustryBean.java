package com.superc.waitmarket.bean;

import java.util.List;

public class SaryIndustryBean {

    /**
     * code : true
     * data : {"secondaryIndustry":[{"list":[{"count":2,"TypeName":"火锅","SMTypeID":1},{"count":1,"TypeName":"海鲜","SMTypeID":2}],"TypeName":"美食","sum":3},{"list":[],"TypeName":"蛋糕","sum":0}],"activatedCount":3}
     * message :
     * returnTime : 201912281658080456
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
         * secondaryIndustry : [{"list":[{"count":2,"TypeName":"火锅","SMTypeID":1},{"count":1,"TypeName":"海鲜","SMTypeID":2}],"TypeName":"美食","sum":3},{"list":[],"TypeName":"蛋糕","sum":0}]
         * activatedCount : 3
         */

        private String activatedCount;
        private List<SecondaryIndustryBean> secondaryIndustry;

        public String getActivatedCount() {
            return activatedCount;
        }

        public void setActivatedCount(String activatedCount) {
            this.activatedCount = activatedCount;
        }

        public List<SecondaryIndustryBean> getSecondaryIndustry() {
            return secondaryIndustry;
        }

        public void setSecondaryIndustry(List<SecondaryIndustryBean> secondaryIndustry) {
            this.secondaryIndustry = secondaryIndustry;
        }

        public static class SecondaryIndustryBean {
            /**
             * list : [{"count":2,"TypeName":"火锅","SMTypeID":1},{"count":1,"TypeName":"海鲜","SMTypeID":2}]
             * TypeName : 美食
             * sum : 3
             */

            private String TypeName;
            private String sum;
            private String  type;
            private boolean is_check;
            private List<ListBean> list;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public boolean isIs_check() {
                return is_check;
            }

            public void setIs_check(boolean is_check) {
                this.is_check = is_check;
            }

            public String getTypeName() {
                return TypeName;
            }

            public void setTypeName(String TypeName) {
                this.TypeName = TypeName;
            }

            public String getSum() {
                return sum;
            }

            public void setSum(String sum) {
                this.sum = sum;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * count : 2
                 * TypeName : 火锅
                 * SMTypeID : 1
                 */

                private String count;
                private String TypeName;
                private String SMTypeID;

                public String getCount() {
                    return count;
                }

                public void setCount(String count) {
                    this.count = count;
                }

                public String getTypeName() {
                    return TypeName;
                }

                public void setTypeName(String TypeName) {
                    this.TypeName = TypeName;
                }

                public String getSMTypeID() {
                    return SMTypeID;
                }

                public void setSMTypeID(String SMTypeID) {
                    this.SMTypeID = SMTypeID;
                }
            }
        }
    }
}
