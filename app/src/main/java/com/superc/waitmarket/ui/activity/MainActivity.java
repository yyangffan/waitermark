package com.superc.waitmarket.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.throwable.HttpThrowable;
import com.superc.waitmarket.R;
import com.superc.waitmarket.base.ApiService;
import com.superc.waitmarket.base.Constant;
import com.superc.waitmarket.base.WaitApplication;
import com.superc.waitmarket.bean.AppMessage;
import com.superc.waitmarket.httputil.EncryPtionHttp;
import com.superc.waitmarket.httputil.EncryPtionUtil;
import com.superc.waitmarket.ui.fragment.BusnesFragment;
import com.superc.waitmarket.ui.fragment.HomeFragment;
import com.superc.waitmarket.ui.fragment.ManageFragment;
import com.superc.waitmarket.ui.fragment.UserFragment;
import com.superc.waitmarket.ui.manager.fragment.ManaBusnesFragment;
import com.superc.waitmarket.utils.BigDecimalUtils;
import com.superc.yyfflibrary.base.BaseActivity;
import com.superc.yyfflibrary.utils.ShareUtil;
import com.superc.yyfflibrary.utils.titlebar.TitleUtils;
import com.superc.yyfflibrary.views.lowhurdles.TabContainerView;
import com.superc.yyfflibrary.views.lowhurdles.TabFragmentAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    /*tab图标集合*/
    private final int ICONS_RES[][] = {{R.drawable.icon_chengji_weixuanzhong, R.drawable.icon_chengji_xuanzhong}, {R.drawable.icon_shanghuguanli_weixuanzhong, R.drawable.icon_shanghuguanli_xuanzhong},
            {R.drawable.icon_yonghuguanli_weixuanzhong, R.drawable.icon_yonghuguanli_xuanzhong}, {R.drawable.icon_gerenzhongxin_weixuanze, R.drawable.icon_gerenzhongxin_xuanzhong}};

    /*tab 颜色值*/
    private final int[] TAB_COLORS = new int[]{R.color.txt_granine, R.color.main_color};
    private String[] titles = new String[]{"成绩", "商户管理", "用户管理", "个人中心"};
    private Fragment[] fragments = null;
    private HomeFragment mHomeFragment;
    private BusnesFragment mBusnesFragment;
    private ManaBusnesFragment mManaBusnesFragment;
    private ManageFragment mManageFragment;
    private UserFragment mUserFragment;
    private ViewPager mPager;
    private TabContainerView mTabLayout;
    private TextView muser_red;
    private String mMsg_count = "- -";
    private String mUser_id;
    private boolean mYihang;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        TitleUtils.setStatusTextColor(true, this);
        Intent intent = this.getIntent();
        if (intent != null) {
            mMsg_count = intent.getStringExtra("msg_count");
        }
        EventBus.getDefault().register(this);
        mHomeFragment = new HomeFragment();
        mBusnesFragment = new BusnesFragment();
        mManaBusnesFragment = new ManaBusnesFragment();
        mManageFragment = new ManageFragment();
        mUserFragment = new UserFragment();
        mYihang = Constant.isYihang();
        if (mYihang) {
            fragments = new Fragment[]{mHomeFragment, mBusnesFragment, mManageFragment, mUserFragment};
        } else {
            fragments = new Fragment[]{mHomeFragment, mManaBusnesFragment, mManageFragment, mUserFragment};
        }
        TabFragmentAdapter mAdapter = new TabFragmentAdapter(getSupportFragmentManager(), fragments);
        mPager = findViewById(R.id.tab_pager);
//        mPager.setScrollble(false);
        //设置当前可见Item左右可见page数，次范围内不会被销毁
        mPager.setOffscreenPageLimit(3);
        mPager.setAdapter(mAdapter);
        mTabLayout = (TabContainerView) findViewById(R.id.ll_tab_container);
        mTabLayout.setOnPageChangeListener(this);
        mTabLayout.initContainer(titles, ICONS_RES, TAB_COLORS, false);
        int width = getResources().getDimensionPixelSize(R.dimen.tab_icon_width);
        int height = getResources().getDimensionPixelSize(R.dimen.tab_icon_height);
        mTabLayout.setContainerLayout(R.layout.tab_container_view, R.id.iv_tab_icon, R.id.tv_tab_text, width, height);
        mTabLayout.setViewPager(mPager);
        mPager.setCurrentItem(getIntent().getIntExtra("tab", 0));
        View[] tabView = mTabLayout.getTabView();
        if (tabView.length >= 3) {
            muser_red = tabView[3].findViewById(R.id.iv_tab_red);
            if (!TextUtils.isEmpty(mMsg_count)) {
                muser_red.setVisibility(mMsg_count.equals("- -") ? View.GONE : View.VISIBLE);
                muser_red.setText(mMsg_count);
            }
        }
        getMsg();
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int index = 0, len = fragments.length; index < len; index++) {
            fragments[index].onHiddenChanged(index != position);
        }
        switch (position) {
            case 0:
                if (mHomeFragment != null) {
                    mHomeFragment.getData();
                }
                break;
            case 1:
                if (mYihang) {
                    if (mBusnesFragment != null) {
                        mBusnesFragment.getCount();
                        mBusnesFragment.getOnceTab();
                        mBusnesFragment.getData();
                    }
                } else {
                    if (mManaBusnesFragment != null && mManaBusnesFragment.isVisible()) {
                        mManaBusnesFragment.getData();
                    }
                }
                break;
            case 2:
                break;
            case 3:
                if (mUserFragment != null) {
                    mUserFragment.getMsg();
                    mUserFragment.getData();
                }
                break;

        }

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private void getMsg() {
        if (TextUtils.isEmpty(mUser_id)) {
            mUser_id = (String) ShareUtil.getInstance(WaitApplication.getInstance()).get("user_id", "");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", mUser_id);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).userInfoNew(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    JSONObject data = result.getJSONObject("data");
                    String messagecount = BigDecimalUtils.bigUtil(data.getString("messagecount"));
                    if (!TextUtils.isEmpty(messagecount)) {
                        muser_red.setVisibility(messagecount.equals("- -") ? View.GONE : (messagecount.equals("0") ? View.GONE : View.VISIBLE));
                        muser_red.setText(messagecount);
                    }
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

    @Override
    protected void onRestart() {
        super.onRestart();
        getMsg();
        if (mUserFragment != null && mUserFragment.isVisible()) {
            mUserFragment.getMsg();
            mUserFragment.getData();
        }
        if (mYihang) {
            if (mBusnesFragment != null && mBusnesFragment.isVisible()) {
                mBusnesFragment.getCount();
                mBusnesFragment.getOnceTab();
                mBusnesFragment.getData();
            }
        } else {
            if (mManaBusnesFragment != null && mManaBusnesFragment.isVisible()) {
                mManaBusnesFragment.getData();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiverMessage(AppMessage message) {
        String what = message.getWhat();
        if (!TextUtils.isEmpty(what)) {
            if (what.equals("finish")) {
                this.finish();
            }

        }
    }

    long stT = 0;
    long endT = 0;

    @Override
    public void onBackPressed() {
        stT = System.currentTimeMillis();
        if (stT - endT >= 2000) {
            ToastShow("双击退出");
            endT = stT;
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
