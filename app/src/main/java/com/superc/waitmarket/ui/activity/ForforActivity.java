package com.superc.waitmarket.ui.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.throwable.HttpThrowable;
import com.superc.waitmarket.R;
import com.superc.waitmarket.base.ApiService;
import com.superc.waitmarket.httputil.EncryPtionHttp;
import com.superc.waitmarket.httputil.EncryPtionUtil;
import com.superc.waitmarket.utils.dialog.MiddleDialog;
import com.superc.waitmarket.utils.vciv.VerificationCodeInputView;
import com.superc.yyfflibrary.base.BaseActivity;
import com.superc.yyfflibrary.utils.ShareUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;

public class ForforActivity extends BaseActivity {
    @BindView(R.id.textView22)
    TextView mTv_top;
    @BindView(R.id.forget_for_phone)
    TextView mForgetForPhone;
    @BindView(R.id.forget_for_get)
    TextView mForgetForGet;
    @BindView(R.id.vciv_code)
    VerificationCodeInputView mVcivCode;
    private String mMes;
    private String mUser_id;
    private String mAcd;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_forfor;
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        mUser_id = (String) ShareUtil.getInstance(this).get("user_id", "");
        Intent intent = this.getIntent();
        if (intent != null) {
            Bundle data = intent.getBundleExtra("data");
            mMes = data.getString("mes");
            mAcd = data.getString("acd");
            mTv_top.setText(mMes);
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        mVcivCode.setOnInputListener(new VerificationCodeInputView.OnInputListener() {
            @Override
            public void onComplete(String code) {
                toCheckCode(code);
            }

            @Override
            public void onInput() {

            }
        });
        togetAgain();

    }

    @OnClick({R.id.imgv_back, R.id.forget_for_get})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgv_back:
                finish();
                break;
            case R.id.forget_for_get:
                togetCode();
                break;
        }
    }

    /*验证 验证码*/
    private void toCheckCode(String mcodo) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", mUser_id);
        map.put("code", mcodo);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).verificationPhoneCode(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    statActivity(ForgetActivity.class);
                    ForforActivity.this.finish();
                    if (!TextUtils.isEmpty(msg)) {
                        ToastShow(msg);
                    }
                } else {
                    new MiddleDialog.Builder(ForforActivity.this).content(msg).build().show();
                }

            }

            @Override
            public void onErrorResult(HttpThrowable httpThrowable) {
                ToastShow("接口访问异常" + httpThrowable.message);
                Log.d("qqq", httpThrowable.message);
            }
        });
    }

    /*如用户未登录状态修改密码传输 account 用户名 status 状态传 0*/
    private void togetCode() {
        Map<String, Object> map = new HashMap<>();
        map.put("account", mAcd);
        map.put("status", "0");
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).getPhoneCode(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    togetAgain();
                }
                if (!TextUtils.isEmpty(msg)) {
                    ToastShow(msg);
                }
            }

            @Override
            public void onErrorResult(HttpThrowable httpThrowable) {
                ToastShow("接口访问异常" + httpThrowable.message);
                Log.d("qqq", httpThrowable.message);
            }
        });
    }

    private void togetAgain() {
        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mForgetForGet.setText((millisUntilFinished / 1000) + "s");
                mForgetForGet.setEnabled(false);
                mForgetForGet.getPaint().setFlags(Paint.SUBPIXEL_TEXT_FLAG); //下划线
            }

            @Override
            public void onFinish() {
                mForgetForGet.setText("重新获取");
                mForgetForGet.setEnabled(true);
                mForgetForGet.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
            }
        }.start();
    }

}
