package com.superc.waitmarket.ui.manager.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.throwable.HttpThrowable;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.superc.waitmarket.R;
import com.superc.waitmarket.adapter.QuansiAdapter;
import com.superc.waitmarket.base.ApiService;
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

public class WholeDepartActivity extends BaseActivity {
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
    private String mUser_id;
    private List<String> mStringList;
    private QuansiAdapter mQuansiAdapter;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_whole_depart;
    }

    @Override
    public void init() {
        TitleUtils.setStatusTextColor(true, this);
        ButterKnife.bind(this);
        mUser_id = (String) ShareUtil.getInstance(this).get("user_id", "");
        mStringList = new ArrayList<>();
        mQuansiAdapter = new QuansiAdapter(this, mStringList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mAllmarketRecy.setLayoutManager(manager);
        mAllmarketRecy.setAdapter(mQuansiAdapter);
        mQuansiAdapter.setOnItemClickListener(new QuansiAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int pos) {
                Intent intent=new Intent(WholeDepartActivity.this,PartGroupActivity.class);
                intent.putExtra("id","id");
                startActivity(intent);
            }
        });

        mAllmarketSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getQuansiData();
            }
        });
        mAllmarketSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ++page;
                getQuansiData();
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

    private void getQuansiData() {
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
                    setTopData(data);
                    if(page==1){
                        mStringList.clear();
                    }
                    for (int i = 0; i < 10; i++) {
                        mStringList.add("");
                    }
                    mQuansiAdapter.notifyDataSetChanged();
                } else {
                    if (page == 1) {
                        mStringList.clear();
                        mQuansiAdapter.notifyDataSetChanged();
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
        mAllmarketTop.setText("共33个分区，1212个小组，21343214名员工");
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
