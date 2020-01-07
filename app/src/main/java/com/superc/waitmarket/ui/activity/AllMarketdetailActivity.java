package com.superc.waitmarket.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.throwable.HttpThrowable;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.superc.waitmarket.R;
import com.superc.waitmarket.adapter.AllMarketDetailAdapter;
import com.superc.waitmarket.base.ApiService;
import com.superc.waitmarket.bean.YingxiaoBean;
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

public class AllMarketdetailActivity extends BaseActivity {

    @BindView(R.id.allmarket_top)
    TextView mAllmarketTop;
    @BindView(R.id.allmarket_hisnum)
    TextView mAllmarketHisnum;
    @BindView(R.id.allmarket_monthnum)
    TextView mAllmarketMonthnum;
    @BindView(R.id.allmarket_todaynum)
    TextView mAllmarketTodaynum;
    @BindView(R.id.allmarket_recy)
    RecyclerView mAllmarketRecy;
    @BindView(R.id.allmarket_smart)
    SmartRefreshLayout mAllmarketSmart;
    @BindView(R.id.allmarket_monthwhat)
    TextView mAllmarketMonthwhat;
    @BindView(R.id.allmarket_monthwhatxiajiang)
    TextView mAllmarketMonthwhatxiajiang;
    @BindView(R.id.allmarket_todaywhat)
    TextView mAllmarketTodaywhat;
    @BindView(R.id.allmarket_todaywhatshangsheng)
    TextView mAllmarketTodaywhatshangsheng;
    private int page = 1;
    private List<YingxiaoBean> mMapList;
    private AllMarketDetailAdapter mAllMarketDetailAdapter;
    private String mId;
    private String mUser_id;


    @Override
    public int getContentLayoutId() {
        return R.layout.activity_all_market;
    }

    @Override
    public void init() {
        TitleUtils.setStatusTextColor(true, this);
        ButterKnife.bind(this);
        mUser_id = (String) ShareUtil.getInstance(this).get("user_id", "");
        Intent intent = getIntent();
        if (intent != null) {
            mId = intent.getStringExtra("id");
        }
        mMapList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mAllmarketRecy.setLayoutManager(linearLayoutManager);
        mAllMarketDetailAdapter = new AllMarketDetailAdapter(this, mMapList);
        mAllmarketRecy.setAdapter(mAllMarketDetailAdapter);
        mAllmarketSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getData();
            }
        });
        mAllmarketSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ++page;
                getData();
            }
        });
        mAllmarketSmart.autoRefresh();
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
        if(mId.equals("空")){
            getNoData();
        }else{
            getHaveData();
        }

    }
    /*个人中心直接跳转*/
    private void getNoData(){
        Map<String, Object> map = new HashMap<>();
        map.put("userId", mUser_id);
        map.put("currentPage", page);
        map.put("pageSize", 10);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).branchBankData(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                mAllmarketSmart.finishRefresh();
                mAllmarketSmart.finishLoadMore();
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    JSONObject data = result.getJSONObject("data");
                    JSONArray detaillist = data.getJSONArray("detaillist");
                    setTopData(data);
                    if (page == 1) {
                        mMapList.clear();
                    }
                    for (int i = 0; i < detaillist.size(); i++) {
                        JSONObject jsonObject = detaillist.getJSONObject(i);
                        YingxiaoBean yxbean = new YingxiaoBean.Builder().img_url(jsonObject.getString("headimg")).one_content(jsonObject.getString("RealName")).two_content(jsonObject.getString("smallbankname")).
                                three_content(jsonObject.getString("acount")).four_content(jsonObject.getString("payamount")).five_content(jsonObject.getString("ccount")).build();
                        mMapList.add(yxbean);
                    }
                    mAllMarketDetailAdapter.notifyDataSetChanged();
                } else {
                    if (page == 1) {
                        mMapList.clear();
                        mAllMarketDetailAdapter.notifyDataSetChanged();
                    }
                }
                if (!TextUtils.isEmpty(msg)) {
                    ToastShow(msg);
                }
            }

            @Override
            public void onErrorResult(HttpThrowable httpThrowable) {
                mAllmarketSmart.finishRefresh();
                mAllmarketSmart.finishLoadMore();
                ToastShow("接口访问异常" + httpThrowable.message);
                Log.d("qqq", httpThrowable.message);
            }
        });
    }

    private void getHaveData(){
        Map<String, Object> map = new HashMap<>();
        map.put("branchId", mId);
        map.put("currentPage", page);
        map.put("pageSize", 10);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).branchBankDataByOne(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                mAllmarketSmart.finishRefresh();
                mAllmarketSmart.finishLoadMore();
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    JSONObject data = result.getJSONObject("data");
                    JSONArray detaillist = data.getJSONArray("detaillist");
                    setTopData(data);
                    if (page == 1) {
                        mMapList.clear();
                    }
                    for (int i = 0; i < detaillist.size(); i++) {
                        JSONObject jsonObject = detaillist.getJSONObject(i);
                        YingxiaoBean yxbean = new YingxiaoBean.Builder().one_content(jsonObject.getString("RealName")).two_content(jsonObject.getString("smallbankname")).
                                three_content(jsonObject.getString("acount")).four_content(jsonObject.getString("payamount")).five_content(jsonObject.getString("ccount")).build();
                        mMapList.add(yxbean);
                    }
                    mAllMarketDetailAdapter.notifyDataSetChanged();
                } else {
                    if (page == 1) {
                        mMapList.clear();
                        mAllMarketDetailAdapter.notifyDataSetChanged();
                    }
                }

                if (!TextUtils.isEmpty(msg)) {
                    ToastShow(msg);
                }
            }

            @Override
            public void onErrorResult(HttpThrowable httpThrowable) {
                mAllmarketSmart.finishRefresh();
                mAllmarketSmart.finishLoadMore();
                ToastShow("接口访问异常" + httpThrowable.message);
                Log.d("qqq", httpThrowable.message);
            }
        });
    }

    private void setTopData(JSONObject data){
        mAllmarketTop.setText("共有" + BigDecimalUtils.bigUtil(data.getString("expandpeoplenum")) + "名员工");
        mAllmarketHisnum.setText(BigDecimalUtils.bigUtil(data.getString("expandnum")));
        mAllmarketTodaynum.setText(BigDecimalUtils.bigUtil(data.getString("flow")));
        mAllmarketMonthnum.setText(BigDecimalUtils.bigUtil(data.getString("money")));

        String moneypercentagetype = data.getString("moneypercentagetype");//0减少 1增加
        mAllmarketMonthwhat.setText(data.getString("moneypercentage"));
        mAllmarketMonthwhatxiajiang.setText(data.getString("moneypercentage"));
        mAllmarketMonthwhat.setVisibility(moneypercentagetype.equals("1")?View.VISIBLE:View.GONE);
        mAllmarketMonthwhatxiajiang.setVisibility(moneypercentagetype.equals("1")?View.GONE:View.VISIBLE);


        String flowpercentagetype = data.getString("flowpercentagetype");//0减少 1增加
        mAllmarketTodaywhat.setText(data.getString("flowpercentage"));
        mAllmarketTodaywhatshangsheng.setText(data.getString("flowpercentage"));
        mAllmarketTodaywhat.setVisibility(flowpercentagetype.equals("1")?View.GONE:View.VISIBLE);
        mAllmarketTodaywhatshangsheng.setVisibility(flowpercentagetype.equals("1")?View.VISIBLE:View.GONE);
    }
}
