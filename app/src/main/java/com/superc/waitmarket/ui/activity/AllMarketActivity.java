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
import com.superc.waitmarket.adapter.AllMarketAdapter;
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

public class AllMarketActivity extends BaseActivity {

    @BindView(R.id.allmarket_top)
    TextView mAllmarketTop;
    @BindView(R.id.title)
    TextView mTiTle;
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
    @BindView(R.id.yingxiao_titlll)
    TextView mAllmarketTodaywhattitlll;
    private int page = 1;
    private List<YingxiaoBean> mMapList;
    private AllMarketAdapter mAllMarketAdapter;
    private String mType;
    private String mUser_id;


    @Override
    public int getContentLayoutId() {
        return R.layout.activity_all_market;
    }

    @Override
    public void init() {
        TitleUtils.setStatusTextColor(true, this);
        ButterKnife.bind(this);
        mType = (String) ShareUtil.getInstance(this).get("type", "");
        mUser_id = (String) ShareUtil.getInstance(this).get("user_id", "");
        switch (mType) {
            case "0":
                mTiTle.setText("网点营销数据");
                mAllmarketTodaywhattitlll.setText("网点营销明细");
                break;
            case "1":
                mTiTle.setText("中支行营销数据");
                mAllmarketTodaywhattitlll.setText("中支行营销明细");
                break;
            case "2":
                mTiTle.setText("全行营销数据");
                mAllmarketTodaywhattitlll.setText("全行营销明细");
                break;
        }
        mMapList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mAllmarketRecy.setLayoutManager(linearLayoutManager);
        mAllMarketAdapter = new AllMarketAdapter(this, mMapList);
        mAllmarketRecy.setAdapter(mAllMarketAdapter);
        mAllMarketAdapter.setOnItemClickListener(new AllMarketAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int pos) {
                YingxiaoBean map = mMapList.get(pos);
                Intent intent = new Intent(AllMarketActivity.this, AllMarketdetailActivity.class);
                intent.putExtra("id", map.getId());
                startActivity(intent);

            }
        });
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
        switch (mType) {
            case "0":
                getWangDian();
                break;
            case "1":
                getZhongzhi();
                break;
            case "2":
                getQuanhang();
                break;
        }
    }

    /*网点*/
    private void getWangDian() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", mUser_id);
        map.put("currentPage", page);
        map.put("pageSize", 10);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).smallBranchBankData(EncryPtionUtil.getInstance(this).toEncryption(map));
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
                        YingxiaoBean yxbean = new YingxiaoBean.Builder().is_wangd(true).img_url(jsonObject.getString("headimg")).one_content(jsonObject.getString("RealName")+"  柜员号"+jsonObject.getString("account")).
                                two_content(jsonObject.getString("acount")).three_content(jsonObject.getString("payamount")).four_content(jsonObject.getString("ccount")).build();
                        mMapList.add(yxbean);
                    }
                    mAllMarketAdapter.notifyDataSetChanged();
                } else {
                    if (page == 1) {
                        mMapList.clear();
                        mAllMarketAdapter.notifyDataSetChanged();
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

    /*中支*/
    private void getZhongzhi() {
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
                        YingxiaoBean yxbean = new YingxiaoBean.Builder().is_wangd(false).one_content(jsonObject.getString("RealName")+"  柜员号"+jsonObject.getString("account")).
                                two_content(jsonObject.getString("acount")).three_content(jsonObject.getString("payamount")).four_content(jsonObject.getString("ccount")).build();
                        mMapList.add(yxbean);
                    }
                    mAllMarketAdapter.notifyDataSetChanged();
                } else {
                    if (page == 1) {
                        mMapList.clear();
                        mAllMarketAdapter.notifyDataSetChanged();
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

    /*全行*/
    private void getQuanhang() {
        Map<String, Object> map = new HashMap<>();
        map.put("currentPage", page);
        map.put("pageSize", 10);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).bankData(EncryPtionUtil.getInstance(this).toEncryption(map));
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
                        YingxiaoBean yxbean = new YingxiaoBean.Builder().is_wangd(false).one_content(jsonObject.getString("NAME")).
                                two_content(jsonObject.getString("acount")).three_content(jsonObject.getString("payamount")).four_content(jsonObject.getString("ccount")).id(BigDecimalUtils.bigUtil(jsonObject.getString("branchid"))).build();
                        mMapList.add(yxbean);
                    }
                    mAllMarketAdapter.notifyDataSetChanged();
                } else {
                    if (page == 1) {
                        mMapList.clear();
                        mAllMarketAdapter.notifyDataSetChanged();
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

    private void setTopData(JSONObject data) {
        switch (mType) {
            case "0":
            case "1":
                mAllmarketTop.setText("共有" + BigDecimalUtils.bigUtil(data.getString("expandpeoplenum")) + "名员工");
                break;
            case "2":
                mAllmarketTop.setText("共有" + BigDecimalUtils.bigUtil(data.getString("expandpeoplenum")) + "家支行");
                break;
        }
        mAllmarketHisnum.setText(BigDecimalUtils.bigUtil(data.getString("expandnum")));
        mAllmarketTodaynum.setText(BigDecimalUtils.bigUtil(data.getString("flow")));
        mAllmarketMonthnum.setText(BigDecimalUtils.bigUtil(data.getString("money")));

        String moneypercentagetype = data.getString("moneypercentagetype");//0减少 1增加
        mAllmarketMonthwhat.setText(data.getString("moneypercentage"));
        mAllmarketMonthwhatxiajiang.setText(data.getString("moneypercentage"));
        mAllmarketMonthwhat.setVisibility(moneypercentagetype.equals("1") ? View.VISIBLE : View.GONE);
        mAllmarketMonthwhatxiajiang.setVisibility(moneypercentagetype.equals("1") ? View.GONE : View.VISIBLE);


        String flowpercentagetype = data.getString("flowpercentagetype");//0减少 1增加
        mAllmarketTodaywhat.setText(data.getString("flowpercentage"));
        mAllmarketTodaywhatshangsheng.setText(data.getString("flowpercentage"));
        mAllmarketTodaywhat.setVisibility(flowpercentagetype.equals("1") ? View.GONE : View.VISIBLE);
        mAllmarketTodaywhatshangsheng.setVisibility(flowpercentagetype.equals("1") ? View.VISIBLE : View.GONE);
    }

}
