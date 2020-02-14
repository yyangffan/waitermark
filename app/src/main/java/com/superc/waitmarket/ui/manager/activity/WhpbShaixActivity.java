package com.superc.waitmarket.ui.manager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.superc.waitmarket.R;
import com.superc.yyfflibrary.base.BaseActivity;
import com.superc.yyfflibrary.utils.titlebar.TitleUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WhpbShaixActivity extends BaseActivity {
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
    @BindView(R.id.textView111)
    TextView mRe;
    @BindView(R.id.smartlayout)
    SmartRefreshLayout mSmartlayout;
    @BindView(R.id.screen_shenfznum)
    EditText mScreenShenfznum;
    @BindView(R.id.screen_yyzzbinah)
    EditText mScreenYyzzbinah;
    @BindView(R.id.screen_yinhcode)
    EditText mScreenYinhcode;
    @BindView(R.id.screen_lingqstate)
    TextView mScreenLingqstate;
    @BindView(R.id.screen_kaitstate)
    TextView mScreenKaitstate;
    @BindView(R.id.screen_reset)
    TextView mScreenReset;
    @BindView(R.id.screen_shaix)
    TextView mScreenShaix;
    @BindView(R.id.screen_starttm)
    TextView mStartTm;
    @BindView(R.id.screen_endtm)
    TextView mEndTm;
    @BindView(R.id.screen_ll)
    LinearLayout mScreenLl;
    private String mType;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_whpb_shaix;
    }

    @Override
    public void init() {
        TitleUtils.setStatusTextColor(true, this);
        ButterKnife.bind(this);
        mType = getIntent().getStringExtra("type");
        mRe.requestFocus();
        mSmartlayout.setEnableOverScrollDrag(true);
        mSmartlayout.setEnablePureScrollMode(true);
    }

    @OnClick({R.id.imgv_back, R.id.screen_reset, R.id.screen_shaix, R.id.screen_chongm_tuozhan, R.id.screen_chongm_quany, R.id.screen_chongm_jingl,
            R.id.screen_lingqstate, R.id.screen_kaitstate, R.id.screen_starttm, R.id.screen_endtm})
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
            case R.id.screen_chongm_tuozhan:
                break;
            case R.id.screen_chongm_quany:
                break;
            case R.id.screen_chongm_jingl:
                break;
            case R.id.screen_lingqstate:
                break;
            case R.id.screen_kaitstate:
                break;
            case R.id.screen_starttm:
                ToastShow("开始时间");
                break;
            case R.id.screen_endtm:
                ToastShow("结束时间");
                break;
        }
    }

    private void toReset() {
        mScreenName.setText("");
        mScreenTuozhan.setText("");
        mScreenShanghuquany.setText("");
        mScreenXiaoek.setText("");
        mScreenShenfznum.setText("");
        mScreenYyzzbinah.setText("");
        mScreenYinhcode.setText("");
        mScreenYiji.setText("");
        mScreenErji.setText("");
        mScreenCity.setText("");
        mScreenQuyu.setText("");
        mScreenShangquan.setText("");
        mStartTm.setText("");
        mEndTm.setText("");
    }

    private void toJump() {
        String name = mScreenName.getText().toString();
        String tuozh = mScreenTuozhan.getText().toString();
        String quany = mScreenShanghuquany.getText().toString();
        String xiaoer = mScreenXiaoek.getText().toString();
        String shenfNum = mScreenShenfznum.getText().toString();
        String yyzzb = mScreenYyzzbinah.getText().toString();
        String yihCode = mScreenYinhcode.getText().toString();
        String yiji = mScreenYiji.getText().toString();
        String erji = mScreenErji.getText().toString();
        String city = mScreenCity.getText().toString();
        String quyu = mScreenQuyu.getText().toString();
        String shangq = mScreenShangquan.getText().toString();
        String st_time = mStartTm.getText().toString();
        String ed_time = mEndTm.getText().toString();
        if (TextUtils.isEmpty(name) && TextUtils.isEmpty(tuozh) && TextUtils.isEmpty(quany) && TextUtils.isEmpty(xiaoer) && TextUtils.isEmpty(shenfNum) && TextUtils.isEmpty(yyzzb) && TextUtils.isEmpty(yihCode) && TextUtils.isEmpty(yiji)
                && TextUtils.isEmpty(erji) && TextUtils.isEmpty(city) && TextUtils.isEmpty(quyu) && TextUtils.isEmpty(shangq) && TextUtils.isEmpty(st_time) && TextUtils.isEmpty(ed_time)) {
            ToastShow("请至少选择一个筛选条件进行筛选");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("tuozh", tuozh);
        bundle.putString("quany", quany);
        bundle.putString("xiaoer", xiaoer);
        bundle.putString("shenfNum", shenfNum);
        bundle.putString("yyzzb", yyzzb);
        bundle.putString("yihCode", yihCode);
        bundle.putString("yiji", yiji);
        bundle.putString("erji", erji);
        bundle.putString("city", city);
        bundle.putString("quyu", quyu);
        bundle.putString("shangq", shangq);
        bundle.putString("st_time", st_time);
        bundle.putString("ed_time", ed_time);
        bundle.putString("type",mType);
        Intent intent = new Intent(this, WhpbSxResultActivity.class);
        intent.putExtra("data", bundle);
        startActivity(intent);


    }
}
