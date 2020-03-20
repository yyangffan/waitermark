package com.superc.waitmarket.ui.activity;

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
import com.superc.waitmarket.adapter.PayFlowAdapter;
import com.superc.waitmarket.base.ApiService;
import com.superc.waitmarket.bean.PayflowBean;
import com.superc.waitmarket.httputil.EncryPtionHttp;
import com.superc.waitmarket.httputil.EncryPtionUtil;
import com.superc.waitmarket.utils.BigDecimalUtils;
import com.superc.yyfflibrary.base.BaseActivity;
import com.superc.yyfflibrary.utils.ShareUtil;
import com.superc.yyfflibrary.utils.titlebar.TitleUtils;
import com.yogee.customdatepicker.datepicker.CustomDatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;

/********************************************************************
 @version: 1.0.0
 @description: 交易流水数据详情
 @author: EDZ
 @time: 2019/12/3 15:07
 @变更历史:
 ********************************************************************/
public class PayflowActivity extends BaseActivity {

    @BindView(R.id.payflow_date)
    TextView mTvDate;
    @BindView(R.id.payflow_note)
    TextView mTvNote;
    @BindView(R.id.payflow_recy)
    RecyclerView mRecyclerView;
    @BindView(R.id.payflow_smart)
    SmartRefreshLayout mSmartRefreshLayout;
    private List<PayflowBean.DataBean.DataListBean> mMapList;
    private int page = 1;
    private CustomDatePicker customDatePickerSt;
    private PayFlowAdapter mPayFlowAdapter;
    private String mUser_id = "1";


    @Override
    public int getContentLayoutId() {
        return R.layout.activity_payflow;
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        initTvDate();
        TitleUtils.setStatusTextColor(true, this);
        mUser_id = (String) ShareUtil.getInstance(this).get("user_id", "");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mMapList = new ArrayList<>();
        mPayFlowAdapter = new PayFlowAdapter(this, mMapList);
        mRecyclerView.setAdapter(mPayFlowAdapter);
        mPayFlowAdapter.setOnItemClickListener(new PayFlowAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int pos) {
               /* PayflowBean.DataBean.DataListBean map = mMapList.get(pos);
                String date = map.getDate();
                Intent intent = new Intent(PayflowActivity.this, PayflowDetailActivity.class);
                intent.putExtra("date", date);
                startActivity(intent);*/
            }
        });
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getData();
            }
        });
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ++page;
                getData();
            }
        });
        mSmartRefreshLayout.autoRefresh();
        mSmartRefreshLayout.setEnableLoadMore(false);
    }

    @OnClick({R.id.payflow_date, R.id.imgv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgv_back:
                finish();
                break;
            case R.id.payflow_date:
                showDateDialog(mTvDate, "2010-01-01 00:00", "2035-12-12 00:00");
                break;
        }
    }

    private void getData() {
        final String date = mTvDate.getText().toString();
        String d = date.replaceAll("年", "-");
        Map<String, Object> map = new HashMap<>();
        map.put("userId", mUser_id);
        map.put("date", d.replace("月", ""));
        map.put("currentPage", page);
        map.put("pageSize", 50);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).businessRecordInfo(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                mSmartRefreshLayout.finishRefresh();
                mSmartRefreshLayout.finishLoadMore();
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    PayflowBean payflowBean = new Gson().fromJson(result.toString(), PayflowBean.class);
                    mTvNote.setText("交易流水¥" + BigDecimalUtils.bigUtil(payflowBean.getData().getSum()) + "，来自" + BigDecimalUtils.bigUtil(payflowBean.getData().getCount()) + "家商户");
                    if (page == 1) {
                        mMapList.clear();
                    }
                    mMapList.addAll(payflowBean.getData().getDataList());
                    mPayFlowAdapter.notifyDataSetChanged();
                } else {
                    if (page == 1) {
                        mMapList.clear();
                        mPayFlowAdapter.notifyDataSetChanged();
                    }
                }
                if (!TextUtils.isEmpty(msg)) {
                    ToastShow(msg);
                }
            }

            @Override
            public void onErrorResult(HttpThrowable httpThrowable) {
                mSmartRefreshLayout.finishRefresh();
                mSmartRefreshLayout.finishLoadMore();
                Log.d("qqq", httpThrowable.message);
                ToastShow("接口访问异常" + httpThrowable.message);
            }
        });


    }

    private void initTvDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月");
        mTvDate.setText(simpleDateFormat.format(new Date()));
    }

    /**
     * 日期选择
     *
     * @param mtv      需要进行日期设置的TextView
     * @param begin_tm 日期选择的开始日期
     * @param ed_tm    日期选择的结束日期
     */
    private void showDateDialog(final TextView mtv, String begin_tm, String ed_tm) {
        String time = mtv.getText().toString();
        customDatePickerSt = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                String tv_old = mtv.getText().toString();
                if (!new SimpleDateFormat("yyyy年MM月", Locale.CHINA).format(new Date(timestamp)).equals(tv_old)) {
                    mMapList.clear();
                    mSmartRefreshLayout.autoRefresh();
                }
                mtv.setText(new SimpleDateFormat("yyyy年MM月", Locale.CHINA).format(new Date(timestamp)));
//                mtv.setText(DateFormatUtils.long2Str(timestamp, false));
            }
        }, begin_tm + " 00:00", ed_tm);
        customDatePickerSt.setCanShowDay(false); // 不显示时和分
        customDatePickerSt.setScrollLoop(true); // 允许循环滚动
        customDatePickerSt.setCanShowAnim(true);//开启滚动动画
        customDatePickerSt.show(!TextUtils.isEmpty(time) ? time.replaceAll("年", "-").replaceAll("月", "-") + "01" + " 00:00" : "2019-01-01 00:00");
    }

}
