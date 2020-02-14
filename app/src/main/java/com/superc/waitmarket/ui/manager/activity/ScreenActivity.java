package com.superc.waitmarket.ui.manager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.superc.waitmarket.R;
import com.superc.yyfflibrary.base.BaseActivity;
import com.superc.yyfflibrary.utils.titlebar.TitleUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScreenActivity extends BaseActivity {
    @BindView(R.id.screen_name)
    EditText mScreenName;
    @BindView(R.id.screen_tuozhan)
    EditText mScreenTuozhan;
    @BindView(R.id.screen_shanghuquany)
    EditText mScreenShanghuquany;
    @BindView(R.id.screen_xiaoek)
    EditText mScreenXiaoek;
    @BindView(R.id.screen_yiji)
    TextView mScreenYiji;
    @BindView(R.id.screen_erji)
    TextView mScreenErji;
    @BindView(R.id.screen_city)
    TextView mScreenCity;
    @BindView(R.id.screen_quyu)
    TextView mScreenQuyu;
    @BindView(R.id.screen_shangquan)
    TextView mScreenShangquan;
    @BindView(R.id.screen_time)
    TextView mScreenTime;
    @BindView(R.id.textView111)
    TextView mRe;
    @BindView(R.id.smartlayout)
    SmartRefreshLayout mSmartlayout;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_screen;
    }

    @Override
    public void init() {
        TitleUtils.setStatusTextColor(true, this);
        ButterKnife.bind(this);
        mRe.requestFocus();
        mSmartlayout.setEnableOverScrollDrag(true);
        mSmartlayout.setEnablePureScrollMode(true);

    }


    @OnClick({R.id.imgv_back, R.id.screen_reset, R.id.screen_shaix})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgv_back:
                finish();
                break;
            case R.id.screen_reset:
                toReset();
                break;
            case R.id.screen_shaix:
                toJump();
                break;
        }
    }

    private void toReset() {
        mScreenName.setText("");
        mScreenTuozhan.setText("");
        mScreenShanghuquany.setText("");
        mScreenXiaoek.setText("");
        mScreenYiji.setText("");
        mScreenErji.setText("");
        mScreenCity.setText("");
        mScreenQuyu.setText("");
        mScreenShangquan.setText("");
        mScreenTime.setText("");
    }

    private void toJump() {
        String name = mScreenName.getText().toString();
        String tuozh = mScreenTuozhan.getText().toString();
        String quany = mScreenShanghuquany.getText().toString();
        String xiaoer = mScreenXiaoek.getText().toString();
        String yiji = mScreenYiji.getText().toString();
        String erji = mScreenErji.getText().toString();
        String city = mScreenCity.getText().toString();
        String quyu = mScreenQuyu.getText().toString();
        String shangq = mScreenShangquan.getText().toString();
        String time = mScreenTime.getText().toString();
        if (TextUtils.isEmpty(name) && TextUtils.isEmpty(tuozh) && TextUtils.isEmpty(quany) && TextUtils.isEmpty(xiaoer) && TextUtils.isEmpty(yiji)
                && TextUtils.isEmpty(erji) && TextUtils.isEmpty(city) && TextUtils.isEmpty(quyu) && TextUtils.isEmpty(shangq) && TextUtils.isEmpty(time)) {
            ToastShow("请至少选择一个筛选条件进行筛选");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("tuozh", tuozh);
        bundle.putString("quany", quany);
        bundle.putString("xiaoer", xiaoer);
        bundle.putString("yiji", yiji);
        bundle.putString("erji", erji);
        bundle.putString("city", city);
        bundle.putString("quyu", quyu);
        bundle.putString("shangq", shangq);
        bundle.putString("time", time);
        Intent intent = new Intent(this, ScreenResultActivity.class);
        intent.putExtra("data",bundle);
        startActivity(intent);


    }

}
