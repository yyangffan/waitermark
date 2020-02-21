package com.superc.waitmarket.ui.manager.activity;

import android.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.superc.waitmarket.R;
import com.superc.waitmarket.utils.dialog.PhaseBottomTelDialog;
import com.superc.yyfflibrary.base.BaseActivity;
import com.superc.yyfflibrary.utils.titlebar.TitleUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScreenDetailActivity extends BaseActivity {


    @BindView(R.id.screen_detail_state)
    TextView mTvState;
    @BindView(R.id.screen_detail_onename)
    TextView mScreenDetailOnename;
    @BindView(R.id.lineone)
    TextView mLineone;
    @BindView(R.id.screen_detail_onetime)
    TextView mScreenDetailOnetime;
    @BindView(R.id.screen_detail_oneyiji)
    TextView mScreenDetailOneyiji;
    @BindView(R.id.screen_detail_oneerji)
    TextView mScreenDetailOneerji;
    @BindView(R.id.screen_detail_onecityqu)
    TextView mScreenDetailOnecityqu;
    @BindView(R.id.screen_detail_oneposi)
    TextView mScreenDetailOneposi;
    @BindView(R.id.screen_detail_onefuzename)
    TextView mScreenDetailOnefuzename;
    @BindView(R.id.screen_detail_onefuzephone)
    TextView mScreenDetailOnefuzephone;
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
    @BindView(R.id.screen_detail_threename)
    TextView mScreenDetailThreename;
    @BindView(R.id.screen_detail_threeyiji)
    TextView mScreenDetailThreeyiji;
    @BindView(R.id.screen_detail_threeerji)
    TextView mScreenDetailThreeerji;
    @BindView(R.id.screen_detail_threequanyi)
    TextView mScreenDetailThreequanyi;
    @BindView(R.id.textViescreen_detail_threequanyyiji)
    TextView mTextViescreenDetailThreequanyyiji;
    @BindView(R.id.screen_detail_threequanyierji)
    TextView mScreenDetailThreequanyierji;
    @BindView(R.id.screen_detail_threexiaoerk)
    TextView mScreenDetailThreexiaoerk;
    @BindView(R.id.smartlayout)
    SmartRefreshLayout mSmartRefreshLayout;
    private PhaseBottomTelDialog mPhaseBottomTelDialog;
    private AlertDialog mMarketingManagerTel;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_screen_detail;
    }

    @Override
    public void init() {
        TitleUtils.setStatusTextColor(true, this);
        ButterKnife.bind(this);
        mSmartRefreshLayout.setEnableOverScrollDrag(true);
        mSmartRefreshLayout.setEnablePureScrollMode(true);
        mPhaseBottomTelDialog = new PhaseBottomTelDialog(this);
        mMarketingManagerTel = mPhaseBottomTelDialog.initDig("kefu");

        getData();
    }


    @OnClick({R.id.imgv_back, R.id.screen_detail_talk})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgv_back:
                finish();
                break;
            case R.id.screen_detail_talk:
                mMarketingManagerTel.show();
                break;
        }
    }

    private void getData() {
        setData();

    }

    private void setData() {
        mTvState.setText("运行正常.");
        mScreenDetailOnename.setText("老干妈");
        mScreenDetailOnetime.setText("2019120015");
        mScreenDetailOneyiji.setText("没事");
        mScreenDetailOneerji.setText("二级");
        mScreenDetailOnecityqu.setText("天津。。。");
        mScreenDetailOneposi.setText("就类似的经历可根据\n902");
        mScreenDetailOnefuzename.setText("张思");
        mScreenDetailOnefuzephone.setText("2819192929");

        mScreenDetailTwohisnum.setText("1比");
        mScreenDetailTwohismony.setText("124");
        mTextVscreenDetailTwomonthnum.setText("24sldj");
        mScreenDetailTwomonthmony.setText("112");
        mScreenDetailTwosevennum.setText("12比");
        mScreenDetailTwosevenmony.setText("124元");
        mScreenDetailTwothreenum.setText("343bi");
        mScreenDetailTwothreemony.setText("421元");

        mScreenDetailThreename.setText("杨梦");
        mScreenDetailThreeyiji.setText("以种植");
        mScreenDetailThreeerji.setText("二级");
        mScreenDetailThreequanyi.setText("张三");
        mTextViescreenDetailThreequanyyiji.setText("权益以及");
        mScreenDetailThreequanyierji.setText("权益二级");
        mScreenDetailThreexiaoerk.setText("张三");

    }

}
