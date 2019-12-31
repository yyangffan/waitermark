package com.superc.waitmarket.bean;

public class BotListBean {
    private String name;
    private boolean type;
    private String what;

    public BotListBean() {
    }

    public BotListBean(String name, boolean type) {
        this.name = name;
        this.type = type;
    }

    public BotListBean(String name, boolean type, String what) {
        this.name = name;
        this.type = type;
        this.what = what;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }
}
