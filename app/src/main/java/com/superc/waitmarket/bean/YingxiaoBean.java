package com.superc.waitmarket.bean;

public class YingxiaoBean {
    private String one_content;
    private String two_content;
    private String three_content;
    private String four_content;
    private String five_content;
    private String img_url;
    private String type;
    private String id;
    private boolean is_wangd;

    public YingxiaoBean(Builder builder) {
        one_content = builder.one_content;
        two_content = builder.two_content;
        three_content = builder.three_content;
        four_content = builder.four_content;
        five_content = builder.five_content;
        img_url = builder.img_url;
        type = builder.type;
        id = builder.id;
        is_wangd=builder.is_wangd;
    }

    public boolean isIs_wangd() {
        return is_wangd;
    }

    public void setIs_wangd(boolean is_wangd) {
        this.is_wangd = is_wangd;
    }

    public String getFive_content() {
        return five_content;
    }

    public void setFive_content(String five_content) {
        this.five_content = five_content;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getOne_content() {
        return one_content;
    }

    public void setOne_content(String one_content) {
        this.one_content = one_content;
    }

    public String getTwo_content() {
        return two_content;
    }

    public void setTwo_content(String two_content) {
        this.two_content = two_content;
    }

    public String getThree_content() {
        return three_content;
    }

    public void setThree_content(String three_content) {
        this.three_content = three_content;
    }

    public String getFour_content() {
        return four_content;
    }

    public void setFour_content(String four_content) {
        this.four_content = four_content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static class Builder {
        private String one_content;
        private String two_content;
        private String three_content;
        private String four_content;
        private String five_content;
        private String img_url;
        private String type;
        private String id;
        private boolean is_wangd;

        public Builder one_content(String one_content) {
            this.one_content = one_content;
            return this;
        }

        public Builder two_content(String two_content) {
            this.two_content = two_content;
            return this;

        }

        public Builder three_content(String three_content) {
            this.three_content = three_content;
            return this;

        }

        public Builder four_content(String four_content) {
            this.four_content = four_content;
            return this;

        }

        public Builder is_wangd(boolean is_wangd) {
            this.is_wangd = is_wangd;
            return this;

        }

        public Builder type(String type) {
            this.type = type;
            return this;

        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder five_content(String five_content) {
            this.five_content = five_content;
            return this;
        }

        public Builder img_url(String img_url) {
            this.img_url = img_url;
            return this;
        }

        public YingxiaoBean build() {
            return new YingxiaoBean(this);
        }

    }
}
