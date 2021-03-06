package com.superc.waitmarket.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationManagerCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.throwable.HttpThrowable;
import com.superc.waitmarket.R;
import com.superc.waitmarket.base.ApiService;
import com.superc.waitmarket.base.Constant;
import com.superc.waitmarket.bean.LoginBean;
import com.superc.waitmarket.httputil.EncryPtionHttp;
import com.superc.waitmarket.httputil.EncryPtionUtil;
import com.superc.waitmarket.utils.BigDecimalUtils;
import com.superc.waitmarket.utils.dialog.MiddleDialog;
import com.superc.waitmarket.utils.jpush.TagAliasOperatorHelper;
import com.superc.yyfflibrary.base.BaseActivity;
import com.superc.yyfflibrary.dialog.YfsRemindDialog;
import com.superc.yyfflibrary.utils.ShareUtil;
import com.superc.yyfflibrary.utils.titlebar.TitleUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_accounts)
    EditText mLoginAccounts;
    @BindView(R.id.login_number)
    EditText mLoginNumber;
    @BindView(R.id.login_bt)
    Button mLoginBt;
    private boolean thfir = false;
    private boolean thsec = false;
    private YfsRemindDialog mRemindDialog;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void init() {
        TitleUtils.setStatusTextColor(true, this);
        haveNotiPer();
        ButterKnife.bind(this);
        rxWritePermission();
        mLoginBt.setEnabled(false);
        mLoginAccounts.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString())) {
                    thfir = false;
                } else {
                    thfir = true;
                }
                if (thfir && thsec) {
                    mLoginBt.setBackgroundResource(R.drawable.bg_circle_main);
                    mLoginBt.setEnabled(true);
                } else {
                    mLoginBt.setBackgroundResource(R.drawable.bg_circle_gra);
                    mLoginBt.setEnabled(false);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mLoginNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString())) {
                    thsec = false;
                } else {
                    thsec = true;
                }
                if (thfir && thsec) {
                    mLoginBt.setBackgroundResource(R.drawable.bg_circle_main);
                    mLoginBt.setEnabled(true);
                } else {
                    mLoginBt.setBackgroundResource(R.drawable.bg_circle_gra);
                    mLoginBt.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @OnClick({R.id.login_bt, R.id.login_forget})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_bt:
                toLogin();

//                statActivity(MainActivity.class);
//                finish();
                break;
            case R.id.login_forget:
                statActivity(ForgetForActivity.class);
                break;
        }
    }

    private void rxWritePermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) throws Exception {
                if (!granted) {
                    ToastShow("可能会影响部分功能使用");
//                    rxWritePermission();
                }
            }
        });
    }
    /*判断是否打开了通知权限并进行跳转*/
    private void haveNotiPer() {
        NotificationManagerCompat notification = NotificationManagerCompat.from(this);
        boolean isEnabled = notification.areNotificationsEnabled();
        if (!isEnabled) {
            mRemindDialog = new YfsRemindDialog.Builder(this).title("提示").content("请在“通知”中打开通知权限").left("取消").left_color(R.color.txt_granine).right("设置").right_color(R.color.main_color).build();
            mRemindDialog.setOnTextClickListener(new YfsRemindDialog.OnTextClickListener() {
                @Override
                public void onRightClickListener() {
                    super.onRightClickListener();
                    Intent intent = new Intent();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                        intent.putExtra("android.provider.extra.APP_PACKAGE", LoginActivity.this.getPackageName());
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {  //5.0
                        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                        intent.putExtra("app_package", LoginActivity.this.getPackageName());
                        intent.putExtra("app_uid", LoginActivity.this.getApplicationInfo().uid);
                        startActivity(intent);
                    } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {  //4.4
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        intent.setData(Uri.parse("package:" + LoginActivity.this.getPackageName()));
                    } else if (Build.VERSION.SDK_INT >= 15) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                        intent.setData(Uri.fromParts("package", LoginActivity.this.getPackageName(), null));
                    }
                    startActivity(intent);
                }
            });
            mRemindDialog.show();
        }
    }


    private void toLogin() {
        String mobile = mLoginAccounts.getText().toString();
        String pwd = mLoginNumber.getText().toString();
        if (TextUtils.isEmpty(mobile) || TextUtils.isEmpty(pwd)) {
            ToastShow("请输入帐号或密码");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userName", mobile);
        map.put("passWord", pwd);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).login(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                Log.d("qqq", result.toJSONString());
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    LoginBean loginBean = new Gson().fromJson(result.toString(), LoginBean.class);
                    String shoplogo = loginBean.getData().getHeadimg();
                    if (!TextUtils.isEmpty(shoplogo)) {
                        if (shoplogo.startsWith("http") || shoplogo.startsWith("https")) {
                            ShareUtil.getInstance(LoginActivity.this).put("head_url", shoplogo);
                        } else {
                            ShareUtil.getInstance(LoginActivity.this).put("head_url", Constant.IMG_URL + shoplogo);
                        }
                    } else {
                        ShareUtil.getInstance(LoginActivity.this).put("head_url", Constant.IMG_URL + shoplogo);
                    }
                    ShareUtil.getInstance(LoginActivity.this).put("login", result.toString());
                    ShareUtil.getInstance(LoginActivity.this).put("user_id", BigDecimalUtils.bigUtil(loginBean.getData().getId()));
                    /*0-网点营销数据  1-中支行营销数据   2-全行营销数据   3-other*/
                    ShareUtil.getInstance(LoginActivity.this).put("type", BigDecimalUtils.bigUtil(loginBean.getData().getType()));
                    ShareUtil.getInstance(LoginActivity.this).put("phone", loginBean.getData().getPhone());
                    ShareUtil.getInstance(LoginActivity.this).put("realname", loginBean.getData().getRealname());
                    ShareUtil.getInstance(LoginActivity.this).put("is_login", true);
                    String bankname = loginBean.getData().getBankname();
                    if(!TextUtils.isEmpty(bankname)) {
                        ShareUtil.getInstance(LoginActivity.this).put("only", bankname.equals("趣街") ? "qujie" : "yinhang");
                        toSetTags(bankname.equals("趣街")?"qujie":"yinhang");
                    }
                    // TODO: 2020/2/11 放开是否是银行的判断
                    /*ShareUtil.getInstance(LoginActivity.this).put("isYh", bankname.equals("趣街")?false:true);*/
                    ShareUtil.getInstance(LoginActivity.this).put("isYh", true);
                    /*0- 小组营销数据  1-分区营销数据   2-全司营销数据   3-other*/
                    ShareUtil.getInstance(LoginActivity.this).put("jltype","2");
                    toSetAlias(BigDecimalUtils.bigUtil(loginBean.getData().getId()));
                    String msg_count = BigDecimalUtils.bigUtil(loginBean.getData().getMessagecount());
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("msg_count",msg_count);
                    startActivity(intent);
                    finish();
                    ToastShow("登录成功");
                }
                if(!TextUtils.isEmpty(msg)) {
                    new MiddleDialog.Builder(LoginActivity.this).content(msg).build().show();
                }
            }

            @Override
            public void onErrorResult(HttpThrowable httpThrowable) {
                ToastShow("接口访问异常" + httpThrowable.message);
                Log.d("qqq", httpThrowable.message);
            }
        });
    }
    /*设置极光推送别名*/
    private void toSetAlias(String shopid){
        // TODO: 2019/9/12  设置别名
        TagAliasOperatorHelper.getInstance().init(this);
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.setAliasAction(true);
        tagAliasBean.setAlias(shopid);
        tagAliasBean.setAction(TagAliasOperatorHelper.ACTION_SET);
        TagAliasOperatorHelper.getInstance().handleAction(this, 11, tagAliasBean);
    }

    private void toSetTags(String qufen){
        // TODO: 2019/9/12  添加标签
        Set<String> sets=new HashSet<>();
        sets.add(qufen);
        TagAliasOperatorHelper.getInstance().init(this);
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.setAliasAction(false);
        tagAliasBean.setTags(sets);
        tagAliasBean.setAction(TagAliasOperatorHelper.ACTION_SET);
        TagAliasOperatorHelper.getInstance().handleAction(this, 12, tagAliasBean);

    }


}
