package com.superc.waitmarket.ui.manager.activity;

import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.superc.waitmarket.R;
import com.superc.yyfflibrary.base.BaseActivity;
import com.superc.yyfflibrary.utils.titlebar.TitleUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShMerdetailActivity extends BaseActivity {


    @BindView(R.id.screen_detail_threename)
    TextView mScreenDetailThreename;
    @BindView(R.id.screen_detail_threebianma)
    TextView mScreenDetailThreebianma;
    @BindView(R.id.screen_detail_threetuozhan)
    TextView mScreenDetailThreetuozhan;
    @BindView(R.id.screen_detail_threeshopjl)
    TextView mScreenDetailThreeshopjl;
    @BindView(R.id.textViescreen_detail_threeshangxtime)
    TextView mTextViescreenDetailThreeshangxtime;
    @BindView(R.id.screen_detail_twohisnum)
    TextView mScreenDetailTwohisnum;
    @BindView(R.id.screen_detail_twohismony)
    TextView mScreenDetailTwohismony;
    @BindView(R.id.textVscreen_detail_twomonthnum)
    TextView mTextVscreenDetailTwomonthnum;
    @BindView(R.id.screen_detail_twomonthmony)
    TextView mScreenDetailTwomonthmony;
    @BindView(R.id.screen_detail_twosevennum)
    TextView mScreenDetailTwosevennum;
    @BindView(R.id.screen_detail_twosevenmony)
    TextView mScreenDetailTwosevenmony;
    @BindView(R.id.screen_detail_twothreenum)
    TextView mScreenDetailTwothreenum;
    @BindView(R.id.screen_detail_twothreemony)
    TextView mScreenDetailTwothreemony;
    @BindView(R.id.smartlayout)
    SmartRefreshLayout mSmartlayout;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_sh_merdetail;
    }

    @Override
    public void init() {
        TitleUtils.setStatusTextColor(true, this);
        ButterKnife.bind(this);
        mSmartlayout.setEnableOverScrollDrag(true);
        mSmartlayout.setEnablePureScrollMode(true);

        getData();
    }

    @OnClick({R.id.imgv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgv_back:
                finish();
                break;
        }
    }

    private void getData() {

        mScreenDetailThreename.setText("老干妈海鲜");
        mScreenDetailThreebianma.setText("123456124");
        mScreenDetailThreetuozhan.setText("张三");
        mScreenDetailThreeshopjl.setText("张三");
        mTextViescreenDetailThreeshangxtime.setText("2019.02.10");

        mScreenDetailTwohisnum.setText("1比");
        mScreenDetailTwohismony.setText("124");
        mTextVscreenDetailTwomonthnum.setText("24sldj");
        mScreenDetailTwomonthmony.setText("112");
        mScreenDetailTwosevennum.setText("12比");
        mScreenDetailTwosevenmony.setText("124元");
        mScreenDetailTwothreenum.setText("343bi");
        mScreenDetailTwothreemony.setText("421元");


    }
}
