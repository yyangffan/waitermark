package com.superc.waitmarket.bean;

public class UserBean {

    /**
     * code : true
     * data : {"smallbankname":"杭州道支行","headimg":"/Image/headImg/07008490-5b4c-4e84-ac43-acea87ab2ea1.200.png","bankname":"滨海营业部","account":"10671","email":"wangjinhong@bankoftianjin.com","MobilePhone":"18210260512","realname":"王金红"}
     * message :
     * returnTime : 201912201744270023
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
         * smallbankname : 杭州道支行
         * headimg : /Image/headImg/07008490-5b4c-4e84-ac43-acea87ab2ea1.200.png
         * bankname : 滨海营业部
         * account : 10671
         * email : wangjinhong@bankoftianjin.com
         * MobilePhone : 18210260512
         * realname : 王金红
         */

        private String smallbankname;
        private String headimg;
        private String bankname;
        private String account;
        private String email;
        private String MobilePhone;
        private String realname;

        public String getSmallbankname() {
            return smallbankname;
        }

        public void setSmallbankname(String smallbankname) {
            this.smallbankname = smallbankname;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public String getBankname() {
            return bankname;
        }

        public void setBankname(String bankname) {
            this.bankname = bankname;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobilePhone() {
            return MobilePhone;
        }

        public void setMobilePhone(String MobilePhone) {
            this.MobilePhone = MobilePhone;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }
    }
}
