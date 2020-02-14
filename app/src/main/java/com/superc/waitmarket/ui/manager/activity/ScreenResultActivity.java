package com.superc.waitmarket.ui.manager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.superc.waitmarket.R;
import com.superc.waitmarket.adapter.HighEneyAdapter;
import com.superc.yyfflibrary.base.BaseActivity;
import com.superc.yyfflibrary.utils.ShareUtil;
import com.superc.yyfflibrary.utils.titlebar.TitleUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScreenResultActivity extends BaseActivity {


    @BindView(R.id.screen_result_recy)
    RecyclerView mScreenResultRecy;
    @BindView(R.id.screen_result_smart)
    SmartRefreshLayout mScreenResultSmart;
    @BindView(R.id.textView112)
    TextView mTvNum;
    private String mName;
    private String mTuozh;
    private String mTime;
    private String mShangq;
    private String mQuyu;
    private String mCity;
    private String mErji;
    private String mYiji;
    private String mXiaoer;
    private String mQuany;
    private int page = 1;
    private List<String> mStrings;
    private HighEneyAdapter mHighEneyAdapter;
    private String mUser_id;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_screen_result;
    }

    @Override
    public void init() {
        TitleUtils.setStatusTextColor(true, this);
        ButterKnife.bind(this);
        mStrings = new ArrayList<>();
        mHighEneyAdapter = new HighEneyAdapter(this, mStrings);
        mUser_id = (String) ShareUtil.getInstance(this).get("user_id", "");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mScreenResultRecy.setLayoutManager(linearLayoutManager);
        mScreenResultRecy.setAdapter(mHighEneyAdapter);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                mName = extras.getString("name", "");
                mTuozh = extras.getString("tuozh", "");
                mQuany = extras.getString("quany", "");
                mXiaoer = extras.getString("xiaoer", "");
                mYiji = extras.getString("yiji", "");
                mErji = extras.getString("erji", "");
                mCity = extras.getString("city", "");
                mQuyu = extras.getString("quyu", "");
                mShangq = extras.getString("shangq", "");
                mTime = extras.getString("time", "");
            }
        }
        mScreenResultSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getData();
            }
        });
        mScreenResultSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ++page;
                getData();
            }
        });
        mScreenResultSmart.autoRefresh();
        mHighEneyAdapter.setOnItemClickListener(new HighEneyAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                statActivity(ScreenDetailActivity.class);
            }
        });

    }


    @OnClick({R.id.imgv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgv_back:
                finish();
                break;
        }
    }

    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", mName);
        map.put("tuozh", mTuozh);
        map.put("quany", mQuany);
        map.put("xiaoer", mXiaoer);
        map.put("yiji", mYiji);
        map.put("erji", mErji);
        map.put("city", mCity);
        map.put("quyu", mQuyu);
        map.put("shangq", mShangq);
        map.put("time", mTime);
        mTvNum.setText("共223家符合要求商户");
        if (page == 1) {
            mStrings.clear();
        }
        mStrings.add("");
        mStrings.add("");
        mStrings.add("");
        mStrings.add("");
        mStrings.add("");
        mStrings.add("");
        mStrings.add("");
        mStrings.add("");
        mStrings.add("");
        mHighEneyAdapter.notifyDataSetChanged();
        mScreenResultSmart.finishRefresh();
        mScreenResultSmart.finishLoadMore();
    }

}
