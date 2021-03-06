package com.superc.waitmarket.ui.activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.throwable.HttpThrowable;
import com.superc.waitmarket.R;
import com.superc.waitmarket.adapter.ViewPaperAdapter;
import com.superc.waitmarket.base.ApiService;
import com.superc.waitmarket.httputil.EncryPtionHttp;
import com.superc.waitmarket.httputil.EncryPtionUtil;
import com.superc.waitmarket.ui.fragment.EdtChangjingFragment;
import com.superc.waitmarket.ui.fragment.EdtJichuFragment;
import com.superc.waitmarket.ui.fragment.EdtJieskFragment;
import com.superc.waitmarket.ui.fragment.EdtZizhiFragment;
import com.superc.waitmarket.utils.dialog.MiddleDialog;
import com.superc.waitmarket.views.CustomScrollViewPager;
import com.superc.yyfflibrary.base.BaseActivity;
import com.superc.yyfflibrary.dialog.YfsRemindDialog;
import com.superc.yyfflibrary.utils.ShareUtil;
import com.superc.yyfflibrary.utils.titlebar.TitleUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;

public class EdtDetailActivity extends BaseActivity {

    @BindView(R.id.edtdetail_one)
    TextView mEdtdetailOne;
    @BindView(R.id.edtdetail_two)
    TextView mEdtdetailTwo;
    @BindView(R.id.edtdetail_three)
    TextView mEdtdetailThree;
    @BindView(R.id.edtdetail_four)
    TextView mEdtdetailFour;
    @BindView(R.id.edtdetail_five)
    TextView mEdtdetailFive;
    @BindView(R.id.edtdetail_vp)
    CustomScrollViewPager mEdtdetailVp;
    @BindView(R.id.edtdetail_top)
    TextView mEdtdetailTop;
    @BindView(R.id.edtdetail_vline)
    View mEdtdetailVline;
    @BindView(R.id.edtdetail_bot)
    TextView mEdtdetailBot;
    @BindView(R.id.edtdetail_commit)
    TextView mSubmit;
    @BindView(R.id.merchant_detail_tabs)
    HorizontalScrollView mHorizontalScrollView;
    private List<TextView> mTextViews;
    private int what = 0;
    /*private String[] mStrings = new String[]{"基础信息", "场景照片", "资质信息", "结算信息", "支付营销老客"};*/
    private String[] mStrings = new String[]{"基础信息", "场景照片", "资质信息", "结算信息"};
    private ViewPaperAdapter mViewPaperAdapter;
    private List<Fragment> mFragmentList;
    private EdtJichuFragment mJiChuFragment;
    private EdtChangjingFragment mChangjPicFragment;
    private EdtZizhiFragment mZizhiFragment;
    private EdtJieskFragment mJieSFragment;
//    private EdtZhifFragment mEdtZhifFragment;
    private int mWidth;
    private int mScreenWidth;
    private boolean is_back = false;
    public boolean is_tijiao = false;
    private String mStatus;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_edt_detail;
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        TitleUtils.setStatusTextColor(true, this);
        getAndroiodScreenProperty();
        mTextViews = new ArrayList<>();
        mFragmentList = new ArrayList<>();
        mJiChuFragment = new EdtJichuFragment();
        mChangjPicFragment = new EdtChangjingFragment();
        mZizhiFragment = new EdtZizhiFragment();
        mJieSFragment = new EdtJieskFragment();
//        mEdtZhifFragment = new EdtZhifFragment();
        mFragmentList.add(mJiChuFragment);
        mFragmentList.add(mChangjPicFragment);
        mFragmentList.add(mZizhiFragment);
        mFragmentList.add(mJieSFragment);
//        mFragmentList.add(mEdtZhifFragment);
        mViewPaperAdapter = new ViewPaperAdapter(getSupportFragmentManager(), mFragmentList, mStrings);
        mEdtdetailVp.setAdapter(mViewPaperAdapter);
        mTextViews.add(mEdtdetailOne);
        mTextViews.add(mEdtdetailTwo);
        mTextViews.add(mEdtdetailThree);
        mTextViews.add(mEdtdetailFour);
        mTextViews.add(mEdtdetailFive);
       /* mStatus = (String) ShareUtil.getInstance(WaitApplication.getInstance()).get("status", "");
        if (mStatus.equals("1")) {//1 不可编辑  0可编辑
            new YfsRemindDialog.Builder(this).title("当前状态仅可编辑支付营销活动").left("确认").left_color(R.color.main_color).build().show();
            mSubmit.setVisibility(View.GONE);
        }else{
            mSubmit.setVisibility(View.VISIBLE);
        }*/
        initWhat();
    }


    @OnClick({R.id.imgv_back, R.id.edtdetail_commit, R.id.edtdetail_top, R.id.edtdetail_bot})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgv_back:
                finish();
                break;
            case R.id.edtdetail_commit:
                is_tijiao = true;
                YfsRemindDialog build = new YfsRemindDialog.Builder(this).title("确认提交该商户信息").left("取消").right("确定").build();
                build.setOnTextClickListener(new YfsRemindDialog.OnTextClickListener() {
                    @Override
                    public void onRightClickListener() {
                        super.onRightClickListener();
//                        if(mStatus.equals("1")){//1 不可编辑  0可编辑
//                            toSubmit();
//                            return;
//                        }
                        toCommit();
                    }
                });
                build.show();
                break;
            case R.id.edtdetail_top:
                is_back = true;
                is_tijiao = false;
//                initWhat();
                ShareUtil.getInstance(this).put("is_creat", "1");
//                if(mStatus.equals("1")){//1 不可编辑  0可编辑
//                    toScroll();
//                    return;
//                }
                if (what == 0) {
                    mJiChuFragment.getJichu();
                }
                toCommit();
                break;
            case R.id.edtdetail_bot:
                is_tijiao = false;
                is_back = false;
//                if(mStatus.equals("1")){//1 不可编辑  0可编辑
//                    toScroll();
//                    return;
//                }
                toCommit();
                break;
        }
    }

    private void toSubmit() {
        String edt_id = (String) ShareUtil.getInstance(this).get("edtdetail_id", "");
        Map<String, Object> map = new HashMap<>();
        map.put("shopId", edt_id);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).submit(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                Log.d("qqq", result.toJSONString());
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    if (!TextUtils.isEmpty(msg)) {
                        MiddleDialog dig_middle = new MiddleDialog.Builder(EdtDetailActivity.this).img_id(R.drawable.icon_chenggong).title(msg).build();
                        dig_middle.setOnMiddleDigFinishListener(new MiddleDialog.OnMiddleDigFinishListener() {
                            @Override
                            public void onMiddleDigfinishListener() {
                                EdtDetailActivity.this.finish();
                            }
                        });
                        dig_middle.show();
                    }

                } else {
                    String data = result.getString("data");
                    if (!TextUtils.isEmpty(data)) {
                        new MiddleDialog.Builder(EdtDetailActivity.this).img_id(R.drawable.con_shibai).title("提交失败").content(data).build().show();
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

    private void toCommit() {
        switch (what) {
            case 0:
                if (mJiChuFragment != null)
                    mJiChuFragment.toCommit();
                break;
            case 1:
                if (mChangjPicFragment != null)
                    mChangjPicFragment.toCommit();
                break;
            case 2:
                if (mZizhiFragment != null)
                    mZizhiFragment.toCommit();
                break;
            case 3:
                /*if (mJieSFragment != null)
                    mJieSFragment.toCommit();*/
                if (mJieSFragment != null){
                    if (is_back) {
                        mJieSFragment.toCommit();
                    }else{
                        toSubmit();
                    }
                }

                break;
            case 4:
//                if (mEdtZhifFragment != null) {
//                    if (is_back) {
//                        mEdtZhifFragment.toCommit();
//                    }else{
//                        toSubmit();
//                    }
//                }
                break;
        }
    }

    public void toScroll() {
        if (is_tijiao) {
            toSubmit();
            is_tijiao = false;
            return;
        }
        if (is_back) {
            --what;
        } else {
            ++what;
        }
        if (what >= 3)
            what = 3;
        initWhat();
        if (what >= 2) {
            mHorizontalScrollView.smoothScrollTo(mWidth / 2, 0);
        } else {
            mHorizontalScrollView.smoothScrollTo(0, 0);
        }
        if (what == 3 && mJieSFragment != null) {
            mJieSFragment.toJudge();
        }
    }

    private void initWhat() {
        if (what == 0) {
            mEdtdetailTop.setVisibility(View.GONE);
            mEdtdetailVline.setVisibility(View.GONE);
            mEdtdetailBot.setVisibility(View.VISIBLE);
        } else if (what == 3) {
            mEdtdetailTop.setVisibility(View.VISIBLE);
            mEdtdetailBot.setVisibility(View.GONE);
            mEdtdetailVline.setVisibility(View.GONE);
        } else {
            mEdtdetailBot.setVisibility(View.VISIBLE);
            mEdtdetailTop.setVisibility(View.VISIBLE);
            mEdtdetailVline.setVisibility(View.VISIBLE);
        }
        switch (what) {
            case 0:
                if (mJiChuFragment != null)
                    mJiChuFragment.toJudge();
                break;
            case 1:
                if (mChangjPicFragment != null)
                    mChangjPicFragment.toJudge();
                break;
            case 2:
                if (mZizhiFragment != null)
                    mZizhiFragment.toJudge();
                break;
            case 3:
                if (mJieSFragment != null)
                    mJieSFragment.toJudge();
                break;
            case 4:
                break;
        }
        mEdtdetailVp.setCurrentItem(what);
        for (int i = 0; i < mTextViews.size(); i++) {
            TextView textView = mTextViews.get(i);
            TextPaint paint = textView.getPaint();
            if (i == what) {
                textView.setTextColor(this.getResources().getColor(R.color.red));
                paint.setFakeBoldText(true);
            } else {
                textView.setTextColor(this.getResources().getColor(R.color.txt_granine));
                paint.setFakeBoldText(false);
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
       /* if (what == 4 && mEdtZhifFragment != null) {
            mEdtZhifFragment.toJudge();
        }*/
    }

    /*获取屏幕参数*/
    public void getAndroiodScreenProperty() {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        // 屏幕宽度（像素）
        mWidth = dm.widthPixels;
        int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        // 屏幕宽度(dp)
        mScreenWidth = (int) (mWidth / density);
        int screenHeight = (int) (height / density);// 屏幕高度(dp)
    }

}
