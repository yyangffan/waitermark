package com.superc.waitmarket.ui.activity;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.superc.waitmarket.R;
import com.superc.waitmarket.adapter.ViewPaperAdapter;
import com.superc.waitmarket.base.Constant;
import com.superc.waitmarket.ui.fragment.ChangjPicFragment;
import com.superc.waitmarket.ui.fragment.JiChuFragment;
import com.superc.waitmarket.ui.fragment.JieSFragment;
import com.superc.waitmarket.ui.fragment.ZizhiFragment;
import com.superc.waitmarket.ui.manager.fragment.ShoukCodeFragment;
import com.superc.waitmarket.utils.dialog.PhaseBottomTelDialog;
import com.superc.yyfflibrary.base.BaseActivity;
import com.superc.yyfflibrary.utils.titlebar.TitleUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MerchantDetailActivity extends BaseActivity implements OnTabSelectListener {

    @BindView(R.id.merchant_detail_tabs)
    SlidingTabLayout mMerchantDetailTabs;
    @BindView(R.id.merchant_detail_talk)
    TextView mTVPhone;
    @BindView(R.id.merchant_detail_viewpage)
    ViewPager mMerchantDetailViewpage;
    /*private String[] mStrings = new String[]{"基础信息", "场景照片", "资质信息", "结算信息", "支付营销老客", "支付营销新客"};
    private String[] mStrings_jili = new String[]{"基础信息", "场景照片", "资质信息", "结算信息", "支付营销老客", "支付营销新客","收款码"};*/
    private String[] mStrings = new String[]{"基础信息", "场景照片", "资质信息", "结算信息"};
    private String[] mStrings_jili = new String[]{"基础信息", "场景照片", "资质信息", "结算信息","收款码"};
    private ViewPaperAdapter mViewPaperAdapter;
    private List<Fragment> mFragmentList;
    private JiChuFragment mJiChuFragment;
    private ChangjPicFragment mChangjPicFragment;
    private ZizhiFragment mZizhiFragment;
    private JieSFragment mJieSFragment;
//    private OldFragment mOldFragment;
//    private NewFragment mNewFragment;
    private ShoukCodeFragment mShoukCodeFragment;
    private boolean mYihang;
    private PhaseBottomTelDialog mPhaseBottomTelDialog;
    private AlertDialog mMarketingManagerTel;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_merchant_detail;
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        TitleUtils.setStatusTextColor(true, this);
        mPhaseBottomTelDialog = new PhaseBottomTelDialog(this);
        mMarketingManagerTel = mPhaseBottomTelDialog.initDig("kefu");
        mYihang = Constant.isYihang();
        if (!mYihang) {
            mTVPhone.setVisibility(View.VISIBLE);
        }
        mFragmentList = new ArrayList<>();
        mJiChuFragment = new JiChuFragment();
        mChangjPicFragment = new ChangjPicFragment();
        mZizhiFragment = new ZizhiFragment();
        mJieSFragment = new JieSFragment();
       /* mOldFragment = new OldFragment();
        mNewFragment = new NewFragment();*/
        mFragmentList.add(mJiChuFragment);
        mFragmentList.add(mChangjPicFragment);
        mFragmentList.add(mZizhiFragment);
        mFragmentList.add(mJieSFragment);
       /* mFragmentList.add(mOldFragment);
        mFragmentList.add(mNewFragment);*/
        if (!mYihang) {
            mShoukCodeFragment = new ShoukCodeFragment();
            mFragmentList.add(mShoukCodeFragment);
        }
        mViewPaperAdapter = new ViewPaperAdapter(getSupportFragmentManager(), mFragmentList, mYihang?mStrings:mStrings_jili);
        mMerchantDetailViewpage.setAdapter(mViewPaperAdapter);
        mMerchantDetailTabs.setViewPager(mMerchantDetailViewpage, mYihang?mStrings:mStrings_jili);
        mMerchantDetailTabs.setOnTabSelectListener(this);

    }

    @OnClick({R.id.imgv_back, R.id.merchant_detail_talk})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgv_back:
                finish();
                break;
            case R.id.merchant_detail_talk:
                mMarketingManagerTel.show();
                break;
        }
    }

    @Override
    public void onTabSelect(int position) {
        switch (position) {
            case 0:
                if (mJiChuFragment != null)
                    mJiChuFragment.getData();
                break;
            case 1:
                if (mChangjPicFragment != null)
                    mChangjPicFragment.getData();
                break;
            case 2:
                if (mZizhiFragment != null)
                    mZizhiFragment.getData();
                break;
            case 3:
                if (mJieSFragment != null)
                    mJieSFragment.getData();
                break;
           /* case 4:
                if (mNewFragment != null)
                    mNewFragment.getData();
                break;
            case 5:
                if (mOldFragment != null)
                    mOldFragment.getData();
                break;*/
        }
    }

    @Override
    public void onTabReselect(int position) {

    }
}
