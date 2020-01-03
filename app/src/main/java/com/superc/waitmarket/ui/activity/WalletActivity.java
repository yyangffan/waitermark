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
import com.superc.waitmarket.adapter.WalletAdapter;
import com.superc.waitmarket.base.ApiService;
import com.superc.waitmarket.bean.ProfitBean;
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

public class WalletActivity extends BaseActivity {

    @BindView(R.id.wallet_money)
    TextView mWalletMoney;
    @BindView(R.id.wallet_expandrecy)
    RecyclerView mWalletExpandrecy;
    @BindView(R.id.wallet_smart)
    SmartRefreshLayout mWalletSmart;
    private int page = 1;
    private WalletAdapter mWalletAdapter;
    private List<ProfitBean.DataBean> mMapList;
    private String mUser_id;


    @Override
    public int getContentLayoutId() {
        return R.layout.activity_wallet;
    }

    @Override
    public void init() {
        TitleUtils.setStatusTextColor(true, this);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent != null) {
            String num = intent.getStringExtra("num");
            mWalletMoney.setText(TextUtils.isEmpty(num) ? "- -" : num);
        }
        mUser_id = (String) ShareUtil.getInstance(this).get("user_id", "");
        mMapList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mWalletExpandrecy.setLayoutManager(linearLayoutManager);
        mWalletAdapter = new WalletAdapter(this, mMapList);
        mWalletExpandrecy.setAdapter(mWalletAdapter);
        mWalletSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getData();
            }
        });
        mWalletSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ++page;
                getData();
            }
        });
        mWalletSmart.autoRefresh();

    }


    @OnClick(R.id.imgv_back)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgv_back:
                finish();
                break;
        }
    }

    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", mUser_id);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).profitInfo(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                mWalletSmart.finishRefresh();
                mWalletSmart.finishLoadMore();
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    ProfitBean profitBean = new Gson().fromJson(result.toString(), ProfitBean.class);
                    if (page == 1) {
                        mMapList.clear();
                    }
                    mMapList.addAll(profitBean.getData());
                    mWalletAdapter.notifyDataSetChanged();
                } else {
                    if (page == 1) {
                        mMapList.clear();
                        mWalletAdapter.notifyDataSetChanged();
                    }
                }
                if (!TextUtils.isEmpty(msg)) {
                    ToastShow(msg);
                }
            }

            @Override
            public void onErrorResult(HttpThrowable httpThrowable) {
                mWalletSmart.finishRefresh();
                mWalletSmart.finishLoadMore();
                ToastShow("接口访问异常" + httpThrowable.message);
                Log.d("qqq", httpThrowable.message);
            }
        });

    }

}
