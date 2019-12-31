package com.superc.waitmarket.ui.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.throwable.HttpThrowable;
import com.superc.waitmarket.R;
import com.superc.waitmarket.base.ApiService;
import com.superc.waitmarket.httputil.EncryPtionHttp;
import com.superc.waitmarket.httputil.EncryPtionUtil;
import com.superc.yyfflibrary.base.BaseActivity;
import com.superc.yyfflibrary.utils.ShareUtil;
import com.superc.yyfflibrary.utils.titlebar.TitleUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;

public class ChangePwdActivity extends BaseActivity {


    @BindView(R.id.imgv_back)
    ImageView mImgvBack;
    @BindView(R.id.changepwd_num)
    TextView mChangepwdNum;
    private String mPhone;
    private String mUser_id;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_change_pwd;
    }

    @Override
    public void init() {
        TitleUtils.setStatusTextColor(true, this);
        ButterKnife.bind(this);
        mUser_id = (String) ShareUtil.getInstance(this).get("user_id", "");
        mPhone = (String) ShareUtil.getInstance(this).get("phone", "");
        mChangepwdNum.setText(TextUtils.isEmpty(mPhone) ? "- -" : mPhone);
    }


    @OnClick({R.id.imgv_back, R.id.changepwd_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgv_back:
                finish();
                break;
            case R.id.changepwd_next:
                toSendCode();
                break;
        }
    }
    /*如用户登录状态修改密码传输 userId 用户id  status 状态传 1*/
    private void toSendCode(){
        if(TextUtils.isEmpty(mPhone)){
            ToastShow("用户手机号获取失败请重新登陆后重试");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", mUser_id);
        map.put("status", "1");
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).getPhoneCode(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    statActivity(ChangePwdendActivity.class);
                    finish();
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


}
