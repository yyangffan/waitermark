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
import com.superc.waitmarket.adapter.FenquAdapter;
import com.superc.waitmarket.base.ApiService;
import com.superc.waitmarket.bean.BotListBean;
import com.superc.waitmarket.httputil.EncryPtionHttp;
import com.superc.waitmarket.httputil.EncryPtionUtil;
import com.superc.waitmarket.utils.BigDecimalUtils;
import com.superc.waitmarket.utils.dialog.DialogBotList;
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

public class PartGroupActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.yingxiao_titlll)
    TextView mMingx;
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
    @BindView(R.id.allmarket_group)
    TextView mAllMarketGroup;
    private int page = 1;
    private String mUser_id;
    private List<String> mStrings;
    private FenquAdapter mFenquAdapter;
    private String mId;
    private DialogBotList mDialogBotList_group;
    private List<BotListBean> mBotListBeans_group;
    private String[] mStringps=new String[]{"全部小组","一组","二组","三组","四组","五组","六组","七组"};

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_part_group;
    }

    @Override
    public void init() {
        TitleUtils.setStatusTextColor(true, this);
        ButterKnife.bind(this);
        mBotListBeans_group = new ArrayList<>();
        mUser_id = (String) ShareUtil.getInstance(this).get("user_id", "");
        Intent intent = getIntent();
        if (intent != null) {
            mId = intent.getStringExtra("id");
            if (mId.equals("空")) {
                mTitle.setText("小组营销数据");
                mMingx.setText("小组营销明细");
            } else {
                mTitle.setText("分区营销数据");
                mMingx.setText("分区营销明细");
                mAllMarketGroup.setVisibility(View.VISIBLE);
            }
        }
        mStrings = new ArrayList<>();
        mFenquAdapter = new FenquAdapter(this, mStrings);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mAllmarketRecy.setLayoutManager(linearLayoutManager);
        mAllmarketRecy.setAdapter(mFenquAdapter);
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
        initGroup();
    }

    @OnClick({R.id.imgv_back, R.id.allmarket_group})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgv_back:
                finish();
                break;
            case R.id.allmarket_group:
                mDialogBotList_group.show();
                break;
        }
    }

    private void getData() {
        if (mId.equals("空")) {
            getXiaozu();
        } else {
            getFenqu();
        }
    }

    /*获取分区数据*/
    private void getFenqu() {
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
                    setTopData(data);
                    mAllmarketTop.setText("共3455个小组，24322234名员工");
                    if (page == 1) {
                        mStrings.clear();
                    }
                    for (int i = 0; i < 10; i++) {
                        mStrings.add("");
                    }
                    mFenquAdapter.notifyDataSetChanged();
                } else {
                    if (page == 1) {
                        mStrings.clear();
                        mFenquAdapter.notifyDataSetChanged();
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

    /*获取小组数据*/
    private void getXiaozu() {
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
                    setTopData(data);
                    mAllmarketTop.setText("共有" + BigDecimalUtils.bigUtil(data.getString("expandpeoplenum")) + "名员工");
                    if (page == 1) {
                        mStrings.clear();
                    }
                    for (int i = 0; i < 10; i++) {
                        mStrings.add("");
                    }
                    mFenquAdapter.notifyDataSetChanged();
                } else {
                    if (page == 1) {
                        mStrings.clear();
                        mFenquAdapter.notifyDataSetChanged();
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
    
    private void initGroup(){
        String group = mAllMarketGroup.getText().toString();
        mBotListBeans_group.clear();
        for (int i = 0; i <mStringps.length; i++) {
            BotListBean botListBean = new BotListBean(mStringps[i], mStringps[i].equals(group) ? true : false, i+"");
            mBotListBeans_group.add(botListBean);
        }
        mDialogBotList_group = new DialogBotList.Builder().title("请选择").botListBeanMap(mBotListBeans_group).builder(this);
        mDialogBotList_group.setOnTextClickListener(new DialogBotList.OnTextClickListener() {
            @Override
            public void onTextClickListenerHis(String name, String what) {
                super.onTextClickListenerHis(name, what);
                String groupp = mAllMarketGroup.getText().toString();
                if (!groupp.equals(name)) {
                    mAllMarketGroup.setText(name);
                }
            }
        });
    }

}
