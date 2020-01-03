package com.superc.waitmarket.ui.activity;

import android.graphics.Paint;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.throwable.HttpThrowable;
import com.superc.waitmarket.R;
import com.superc.waitmarket.base.ApiService;
import com.superc.waitmarket.bean.AppMessage;
import com.superc.waitmarket.httputil.EncryPtionHttp;
import com.superc.waitmarket.httputil.EncryPtionUtil;
import com.superc.waitmarket.utils.dialog.MiddleDialog;
import com.superc.waitmarket.utils.vciv.VerificationCodeInputView;
import com.superc.yyfflibrary.base.BaseActivity;
import com.superc.yyfflibrary.utils.ShareUtil;
import com.superc.yyfflibrary.utils.titlebar.TitleUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;

public class ChangePwdendActivity extends BaseActivity {


    @BindView(R.id.changepwded_phone)
    TextView mChangepwdedPhone;
    @BindView(R.id.changepwded_get)
    TextView mChangepwdedGet;
    @BindView(R.id.changepwded_code)
    VerificationCodeInputView mChangepwdedCode;
    @BindView(R.id.change_pwdcons_one)
    ConstraintLayout mChangePwdconsOne;
    @BindView(R.id.changepwwded_edtonece)
    EditText mChangepwwdedEdtonece;
    @BindView(R.id.changepwwded_edtonetwice)
    EditText mChangepwwdedEdtonetwice;
    @BindView(R.id.changepwwded_bt)
    Button mChangepwwdedBt;
    @BindView(R.id.change_pwdcons_two)
    ConstraintLayout mChangePwdconsTwo;
    private String mUser_id;
    private String mPhone;
    private boolean theFirst, theTwice = false;
    private int theFirstLength, theTwiceLength = 0;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_change_pwdend;
    }

    @Override
    public void init() {
        TitleUtils.setStatusTextColor(true, this);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        mUser_id = (String) ShareUtil.getInstance(this).get("user_id", "");
        mPhone = (String) ShareUtil.getInstance(this).get("phone", "");
        mChangepwdedPhone.setText(mPhone);
        mChangepwwdedBt.setEnabled(false);
        mChangepwdedCode.setOnInputListener(new VerificationCodeInputView.OnInputListener() {
            @Override
            public void onComplete(String code) {
                toCheckCode(code);
            }

            @Override
            public void onInput() {

            }
        });
        mChangepwwdedEdtonece.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                theFirst = charSequence.length() >= 6 ? true : false;
                theFirstLength = charSequence.length();
                if (theTwice && theFirst) {
                    if (theTwiceLength != 0 && charSequence.length() == theTwiceLength) {
                        mChangepwwdedBt.setEnabled(true);
                        mChangepwwdedBt.setBackground(ChangePwdendActivity.this.getResources().getDrawable(R.drawable.bg_circle_red));
                    } else {
                        mChangepwwdedBt.setEnabled(false);
                        mChangepwwdedBt.setBackground(ChangePwdendActivity.this.getResources().getDrawable(R.drawable.bg_circle_gra));
                    }
                } else {
                    mChangepwwdedBt.setEnabled(false);
                    mChangepwwdedBt.setBackground(ChangePwdendActivity.this.getResources().getDrawable(R.drawable.bg_circle_gra));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mChangepwwdedEdtonetwice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                theTwice = charSequence.length() >= 6 ? true : false;
                theTwiceLength = charSequence.length();
                if (theTwice && theFirst) {
                    if (theFirstLength != 0 && charSequence.length() == theFirstLength) {
                        mChangepwwdedBt.setEnabled(true);
                        mChangepwwdedBt.setBackground(ChangePwdendActivity.this.getResources().getDrawable(R.drawable.bg_circle_red));
                    } else {
                        mChangepwwdedBt.setEnabled(false);
                        mChangepwwdedBt.setBackground(ChangePwdendActivity.this.getResources().getDrawable(R.drawable.bg_circle_gra));
                    }
                } else {
                    mChangepwwdedBt.setEnabled(false);
                    mChangepwwdedBt.setBackground(ChangePwdendActivity.this.getResources().getDrawable(R.drawable.bg_circle_gra));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        togetAgain();
    }

    @OnClick({R.id.imgv_back, R.id.changepwded_get, R.id.changepwwded_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgv_back:
                finish();
                break;
            case R.id.changepwded_get:
                toSendCode();
                break;
            case R.id.changepwwded_bt:
                toChange() ;
                break;
        }
    }

    /*如用户登录状态修改密码传输 userId 用户id  status 状态传 1*/
    private void toSendCode() {
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
                    mChangePwdconsOne.setVisibility(View.GONE);
                    mChangepwwdedEdtonece.requestFocus();
                    mChangePwdconsTwo.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(msg)) {
                        ToastShow(msg);
                    }
                } else {
                    new MiddleDialog.Builder(ChangePwdendActivity.this).content("验证码输入错误\n请重新输入").build().show();
                }

            }

            @Override
            public void onErrorResult(HttpThrowable httpThrowable) {
                ToastShow("接口访问异常" + httpThrowable.message);
                Log.d("qqq", httpThrowable.message);
            }
        });
    }
    private void toChange() {
        String pwd = mChangepwwdedEdtonece.getText().toString();
        String pwd_agin = mChangepwwdedEdtonetwice.getText().toString();

        if (!pwd.equals(pwd_agin)) {
            new MiddleDialog.Builder(ChangePwdendActivity.this).content("两次输入密码不一致，请重新输入").build().show();
        }
//        else if (!PwdCheckUtil.isContainAll(pwd)) {
//            new YfsRemindDialog.Builder(this).title("提示").content("⼤⼩写数字混合（每种⾄少1位）").left("确认").left_color(R.color.main_color).build().show();
//        }
        else {
            toCommitPwd(pwd);
        }

    }

    private void toCommitPwd(String passWord){
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
                    statActivity(LoginActivity.class);
                    EventBus.getDefault().post(new AppMessage(0, "finish"));
                    ShareUtil.getInstance(ChangePwdendActivity.this).clear();
                    finish();
                }
                if (!TextUtils.isEmpty(msg)) {
                    new MiddleDialog.Builder(ChangePwdendActivity.this).content(msg).build().show();
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
                mChangepwdedGet.setText((millisUntilFinished / 1000) + "s");
                mChangepwdedGet.setEnabled(false);
                mChangepwdedGet.getPaint().setFlags(Paint.SUBPIXEL_TEXT_FLAG); //下划线
            }

            @Override
            public void onFinish() {
                mChangepwdedGet.setText("重新获取");
                mChangepwdedGet.setEnabled(true);
                mChangepwdedGet.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
            }
        }.start();
    }


}
