package com.superc.waitmarket.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.throwable.HttpThrowable;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.superc.waitmarket.R;
import com.superc.waitmarket.adapter.ExpandDetailAdapter;
import com.superc.waitmarket.base.ApiService;
import com.superc.waitmarket.bean.ExpandDetailBean;
import com.superc.waitmarket.httputil.EncryPtionHttp;
import com.superc.waitmarket.httputil.EncryPtionUtil;
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
import io.reactivex.Observable;

/********************************************************************
 @version: 1.0.0
 @description:与流水详情公用同一个布局，需修改部分文字
 @author: EDZ
 @time: 2019/12/4 10:04
 @变更历史:
 ********************************************************************/
public class ExpandDeatilActivity extends BaseActivity {

    @BindView(R.id.payflow_detail_money)
    TextView mExpandDetailOne;
    @BindView(R.id.payflow_detail_num)
    TextView mExpandDetailTwo;
    @BindView(R.id.payflow_detail_recy)
    RecyclerView mExpandDetailRecy;
    @BindView(R.id.payflow_smart)
    SmartRefreshLayout mExpandDetailSmart;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.one)
    TextView mOne;
    @BindView(R.id.two)
    TextView mTwo;
    @BindView(R.id.three)
    TextView mThree;
    private String mDate;
    private int page = 1;
    private List<ExpandDetailBean.DataBean.DataListBean> mMapList;
    private ExpandDetailAdapter mExpandDetailAdapter;
    private String mUser_id = "1";
    private int xiaoer;
    private int zituo;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_payflow_detail;
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        TitleUtils.setStatusTextColor(true, this);
        mUser_id = (String) ShareUtil.getInstance(this).get("user_id", "");
        mTwo.setText("家，小二委派");
        mThree.setText("家");
        Intent intent = getIntent();
        if (intent != null) {
            Bundle data = intent.getBundleExtra("data");
            mDate = data.getString("date");
            zituo = data.getInt("zituo");
            xiaoer = data.getInt("xiaoer");
            mExpandDetailOne.setText(TextUtils.isEmpty(zituo + "") ? "- -" : zituo + "");
            mExpandDetailTwo.setText(TextUtils.isEmpty(xiaoer + "") ? "- -" : xiaoer + "");
            mTitle.setText(mDate);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mExpandDetailRecy.setLayoutManager(linearLayoutManager);
        mMapList = new ArrayList<>();
        mExpandDetailAdapter = new ExpandDetailAdapter(this, mMapList);
        mExpandDetailRecy.setAdapter(mExpandDetailAdapter);

        mExpandDetailSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getData();
            }
        });
        mExpandDetailSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ++page;
                getData();
            }
        });
        mExpandDetailSmart.autoRefresh();

    }

    @OnClick(R.id.imgv_back)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgv_back:
                finish();
                break;
        }

    }

    private void getData() {
        if (page == 1) {
            mMapList.clear();
        }

        Map<String, Object> map = new HashMap<>();
        map.put("userId", mUser_id);
        map.put("date", mDate);
        map.put("currentPage", page);
        map.put("pageSize", 10);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).selectBusinessCountByDay(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                mExpandDetailSmart.finishRefresh();
                mExpandDetailSmart.finishLoadMore();
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    ExpandDetailBean detailBean = new Gson().fromJson(result.toString(), ExpandDetailBean.class);
                    mOne.setText("共拓展" + detailBean.getData().getCount() + "家商户：自拓展");
                    if (page == 1) {
                        mMapList.clear();
                    }
                    mMapList.addAll(detailBean.getData().getDataList());
                    mExpandDetailAdapter.notifyDataSetChanged();
                } else {
                    if (page == 1) {
                        mMapList.clear();
                        mExpandDetailAdapter.notifyDataSetChanged();
                    }
                }
                if (!TextUtils.isEmpty(msg)) {
                    ToastShow(msg);
                }
            }

            @Override
            public void onErrorResult(HttpThrowable httpThrowable) {
                mExpandDetailSmart.finishRefresh();
                mExpandDetailSmart.finishLoadMore();
                ToastShow("接口访问异常" + httpThrowable.message);
                Log.d("qqq", httpThrowable.message);
            }
        });


    }

}
