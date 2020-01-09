package com.superc.waitmarket.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.throwable.HttpThrowable;
import com.superc.waitmarket.R;
import com.superc.waitmarket.base.ApiService;
import com.superc.waitmarket.bean.AppMessage;
import com.superc.waitmarket.httputil.EncryPtionHttp;
import com.superc.waitmarket.httputil.EncryPtionUtil;
import com.superc.waitmarket.utils.BigDecimalUtils;
import com.superc.waitmarket.utils.dialog.MiddleDialog;
import com.superc.yyfflibrary.base.BaseActivity;
import com.superc.yyfflibrary.utils.ShareUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;

public class ForgetForActivity extends BaseActivity {

    @BindView(R.id.forgetfor_num)
    EditText mForgetforNum;
    @BindView(R.id.forgetfor_bt)
    Button mButton;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_forget_for;
    }

    @Override
    public void init() {
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        mButton.setEnabled(false);
        mForgetforNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString())) {
                    mButton.setBackgroundResource(R.drawable.bg_circle_gra);
                    mButton.setEnabled(false);

                } else {
                    mButton.setBackgroundResource(R.drawable.bg_circle_main);
                    mButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.imgv_back, R.id.forgetfor_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgv_back:
                finish();
                break;
            case R.id.forgetfor_bt:
                toCom();
                break;
        }
    }

    /*如用户未登录状态修改密码传输 account 用户名 status 状态传 0*/
    private void toCom() {
        final String acd = mForgetforNum.getText().toString();
        Map<String, Object> map = new HashMap<>();
        map.put("account", acd);
        map.put("status", "0");
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).getPhoneCode(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    final Bundle bundle = new Bundle();
                    JSONObject data = result.getJSONObject("data");
                    String userid = BigDecimalUtils.bigUtil(data.getString("userId"));
                    ShareUtil.getInstance(ForgetForActivity.this).put("user_id", userid);
                    final String mes = data.getString("phone");
                    final Intent intent = new Intent(ForgetForActivity.this, ForforActivity.class);
                    bundle.putString("mes", mes);
                    bundle.putString("acd", acd);
                    intent.putExtra("data", bundle);

//                    finish();
                    if(!TextUtils.isEmpty(msg)) {
                        MiddleDialog build = new MiddleDialog.Builder(ForgetForActivity.this).content(msg).build();
                        build.setOnMiddleDigFinishListener(new MiddleDialog.OnMiddleDigFinishListener() {
                            @Override
                            public void onMiddleDigfinishListener() {
                                startActivity(intent);
                            }
                        });
                        build.show();
                    }
                } else {
                    if (!TextUtils.isEmpty(msg)) {
                        ToastShow(msg);
                    }
                }
            }

            @Override
            public void onErrorResult(HttpThrowable httpThrowable) {
                ToastShow("接口访问异常" + httpThrowable.message);
                Log.d("qqq", httpThrowable.message);
            }
        });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiverMessage(AppMessage message) {
        String what = message.getWhat();
        if (!TextUtils.isEmpty(what)) {
            if (what.equals("finish_forget")) {
                this.finish();
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
