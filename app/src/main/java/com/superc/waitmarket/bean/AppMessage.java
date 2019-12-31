package com.superc.waitmarket.bean;

public class AppMessage {
    private int state;
    private String what;

    public AppMessage(int state, String what) {
        this.state = state;
        this.what = what;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }
}
