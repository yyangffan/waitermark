package com.superc.waitmarket.bean;

public class LoginBean {

    /**
     * code : True
     * data : {"amount":122,"smallbankname":"杭州道支行","headimg":"/Image/headImg/07008490-5b4c-4e84-ac43-acea87ab2ea1.200.png","phone":"18210260512","rolename":"全行管理员","messagecount":8,"bankname":"滨海营业部","id":7160,"type":"2","account":"10671","realname":"王金红"}
     * message :
     * returnTime : 201912201144470298
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
         * amount : 122.0
         * smallbankname : 杭州道支行
         * headimg : /Image/headImg/07008490-5b4c-4e84-ac43-acea87ab2ea1.200.png
         * phone : 18210260512
         * rolename : 全行管理员
         * messagecount : 8
         * bankname : 滨海营业部
         * id : 7160
         * type : 2
         * account : 10671
         * realname : 王金红
         */

        private double amount;
        private String smallbankname;
        private String headimg;
        private String phone;
        private String rolename;
        private String messagecount;
        private String bankname;
        private String id;
        private String type;
        private String account;
        private String realname;

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRolename() {
            return rolename;
        }

        public void setRolename(String rolename) {
            this.rolename = rolename;
        }

        public String getMessagecount() {
            return messagecount;
        }

        public void setMessagecount(String messagecount) {
            this.messagecount = messagecount;
        }

        public String getBankname() {
            return bankname;
        }

        public void setBankname(String bankname) {
            this.bankname = bankname;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }
    }
}
