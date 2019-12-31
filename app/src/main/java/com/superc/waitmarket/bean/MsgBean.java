package com.superc.waitmarket.bean;

import java.util.List;

public class MsgBean {

    /**
     * code : true
     * data : [{"id":3,"title":"测试2","addtime":"2019-12-17 13:47:30","content":"测试消息2","num":1,"status":3},{"id":3,"title":"测试2","addtime":"2019-12-17 13:47:30","content":"测试消息2","num":2,"status":3},{"id":3,"title":"测试2","addtime":"2019-12-17 13:47:30","content":"测试消息2","num":3,"status":3},{"id":3,"title":"测试2","addtime":"2019-12-17 13:47:30","content":"测试消息2","num":4,"status":3},{"id":3,"title":"测试2","addtime":"2019-12-17 13:47:30","content":"测试消息2","num":5,"status":3},{"id":3,"title":"测试2","addtime":"2019-12-17 13:47:30","content":"测试消息2","num":6,"status":3},{"id":3,"title":"测试2","addtime":"2019-12-17 13:47:30","content":"测试消息2","num":7,"status":3},{"id":3,"title":"测试2","addtime":"2019-12-17 13:47:30","content":"测试消息2","num":8,"status":3},{"id":3,"title":"测试2","addtime":"2019-12-17 13:47:30","content":"测试消息2","num":9,"status":3},{"id":3,"title":"测试2","addtime":"2019-12-17 13:47:30","content":"测试消息2","num":10,"status":3}]
     * message :
     * returnTime : 201912211453460560
     */

    private boolean code;
    private String message;
    private String returnTime;
    private List<DataBean> data;

    public boolean isCode() {
        return code;
    }

    public void setCode(boolean code) {
        this.code = code;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 3
         * title : 测试2
         * addtime : 2019-12-17 13:47:30
         * content : 测试消息2
         * num : 1
         * status : 3
         */

        private String id;
        private String title;
        private String addtime;
        private String content;
        private int num;
        private int status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
