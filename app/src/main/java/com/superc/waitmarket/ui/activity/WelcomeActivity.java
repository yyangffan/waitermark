package com.superc.waitmarket.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.superc.waitmarket.base.WaitApplication;
import com.superc.yyfflibrary.utils.ShareUtil;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        toGo();
       /* new CountDownTimer(1500, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                toGo();
            }
        }.start();*/
    }

    private void toGo() {
        boolean is_login = (boolean) ShareUtil.getInstance(WaitApplication.getInstance()).get("is_login", false);
        if(is_login){
            startActivity(new Intent(this, MainActivity.class));
        }else{
            startActivity(new Intent(this, LoginActivity.class));
        }
        finish();

    }


}
