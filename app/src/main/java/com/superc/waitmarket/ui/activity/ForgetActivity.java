package com.superc.waitmarket.ui.activity;

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
import com.superc.waitmarket.utils.dialog.MiddleDialog;
import com.superc.yyfflibrary.base.BaseActivity;
import com.superc.yyfflibrary.utils.ShareUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;

public class ForgetActivity extends BaseActivity {

    @BindView(R.id.forget_onece)
    EditText mForgetOnece;
    @BindView(R.id.forget_twice)
    EditText mForgetTwice;
    @BindView(R.id.forget_bt)
    Button mCommitBt;
    private String mUser_id;
    private boolean theFirst, theTwice = false;
    private int theFirstLength, theTwiceLength = 0;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_forget;
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        mUser_id = (String) ShareUtil.getInstance(this).get("user_id", "");
        mCommitBt.setEnabled(false);
        mForgetOnece.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                theFirst = charSequence.length() >= 6 ? true : false;
                theFirstLength = charSequence.length();
                if (theTwice && theFirst) {
                    if (theTwiceLength != 0 && charSequence.length() == theTwiceLength) {
                        mCommitBt.setEnabled(true);
                        mCommitBt.setBackground(ForgetActivity.this.getResources().getDrawable(R.drawable.bg_circle_red));
                    } else {
                        mCommitBt.setEnabled(false);
                        mCommitBt.setBackground(ForgetActivity.this.getResources().getDrawable(R.drawable.bg_circle_gra));
                    }
                } else {
                    mCommitBt.setEnabled(false);
                    mCommitBt.setBackground(ForgetActivity.this.getResources().getDrawable(R.drawable.bg_circle_gra));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mForgetTwice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                theTwice = charSequence.length() >= 6 ? true : false;
                theTwiceLength = charSequence.length();
                if (theTwice && theFirst) {
                    if (theFirstLength != 0 && charSequence.length() == theFirstLength) {
                        mCommitBt.setEnabled(true);
                        mCommitBt.setBackground(ForgetActivity.this.getResources().getDrawable(R.drawable.bg_circle_red));
                    } else {
                        mCommitBt.setEnabled(false);
                        mCommitBt.setBackground(ForgetActivity.this.getResources().getDrawable(R.drawable.bg_circle_gra));
                    }
                } else {
                    mCommitBt.setEnabled(false);
                    mCommitBt.setBackground(ForgetActivity.this.getResources().getDrawable(R.drawable.bg_circle_gra));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }


    @OnClick({R.id.forget_bt, R.id.imgv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.forget_bt:
                toChange();
                break;
            case R.id.imgv_back:
                finish();
                break;
        }
    }


    private void toChange() {
        String pwd = mForgetOnece.getText().toString();
        String pwd_agin = mForgetTwice.getText().toString();

        if (!pwd.equals(pwd_agin)) {
            new MiddleDialog.Builder(ForgetActivity.this).content("两次输入密码不一致，请重新输入").build().show();
        }
//        else if (!PwdCheckUtil.isContainAll(pwd)) {
//            new YfsRemindDialog.Builder(this).title("提示").content("⼤⼩写数字混合（每种⾄少1位）").left("确认").left_color(R.color.main_color).build().show();
//        }
        else {
            toCommitPwd(pwd);
        }

    }

    private void toCommitPwd(String passWord) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", mUser_id);
        map.put("passWord", passWord);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).updatePassword(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    EventBus.getDefault().post(new AppMessage(0, "finish_forget"));
                    new MiddleDialog.Builder(ForgetActivity.this).content("设置成功\n请重新登录").build().show();
                    finish();
                } else {
                    if (!TextUtils.isEmpty(msg)) {
                        new MiddleDialog.Builder(ForgetActivity.this).content(msg).build().show();
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

}
