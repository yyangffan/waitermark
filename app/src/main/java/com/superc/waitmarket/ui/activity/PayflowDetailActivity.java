package com.superc.waitmarket.ui.activity;

import android.content.Intent;
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
import com.superc.waitmarket.adapter.PayDetailAdapter;
import com.superc.waitmarket.base.ApiService;
import com.superc.waitmarket.bean.PayflowDetailBean;
import com.superc.waitmarket.httputil.EncryPtionHttp;
import com.superc.waitmarket.httputil.EncryPtionUtil;
import com.superc.waitmarket.utils.BigDecimalUtils;
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

public class PayflowDetailActivity extends BaseActivity {

    @BindView(R.id.payflow_detail_money)
    TextView mPayflowDetailMoney;
    @BindView(R.id.payflow_detail_num)
    TextView mPayflowDetailNum;
    @BindView(R.id.payflow_detail_recy)
    RecyclerView mPayflowDetailRecy;
    @BindView(R.id.payflow_smart)
    SmartRefreshLayout mPayflowSmart;
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
    private List<PayflowDetailBean.DataBean.DataListBean> mMapList;
    private PayDetailAdapter mPayDetailAdapter;
    private String mUser_id = "1";

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_payflow_detail;
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        TitleUtils.setStatusTextColor(true, this);
        mUser_id = (String) ShareUtil.getInstance(this).get("user_id", "");
        mOne.setText("交易流水");
        mTwo.setText("，来自");
        mThree.setText("家商户");
        Intent intent = getIntent();
        if (intent != null) {
            mDate = intent.getStringExtra("date");
            mTitle.setText(mDate);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mPayflowDetailRecy.setLayoutManager(linearLayoutManager);
        mMapList = new ArrayList<>();
        mPayDetailAdapter = new PayDetailAdapter(this, mMapList);
        mPayflowDetailRecy.setAdapter(mPayDetailAdapter);

        mPayflowSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getData();
            }
        });
        mPayflowSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ++page;
                getData();
            }
        });
        mPayflowSmart.autoRefresh();
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
        Map<String, Object> map = new HashMap<>();
        map.put("userId", mUser_id);
        map.put("date", mDate);
        map.put("currentPage", page);
        map.put("pageSize", 10);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).businessRecorDetail(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                mPayflowSmart.finishRefresh();
                mPayflowSmart.finishLoadMore();
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    PayflowDetailBean payflowDetailBean = new Gson().fromJson(result.toString(), PayflowDetailBean.class);
                    mPayflowDetailMoney.setText("¥" + BigDecimalUtils.bigUtil(payflowDetailBean.getData().getSum()));
                    mPayflowDetailNum.setText(BigDecimalUtils.bigUtil(payflowDetailBean.getData().getCount()));
                    if (page == 1) {
                        mMapList.clear();
                    }
                    mMapList.addAll(payflowDetailBean.getData().getDataList());
                    mPayDetailAdapter.notifyDataSetChanged();
                }
                if (!TextUtils.isEmpty(msg)) {
                    ToastShow(msg);
                }
            }

            @Override
            public void onErrorResult(HttpThrowable httpThrowable) {
                mPayflowSmart.finishRefresh();
                mPayflowSmart.finishLoadMore();

                ToastShow("接口访问异常" + httpThrowable.message);
                Log.d("qqq", httpThrowable.message);
            }
        });
    }
}
