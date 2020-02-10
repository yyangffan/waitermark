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
import com.superc.waitmarket.adapter.ExpandAdapter;
import com.superc.waitmarket.base.ApiService;
import com.superc.waitmarket.bean.ShanghuDetailBean;
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

public class ExpandActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.payflow_date)
    TextView mExpandDate;
    @BindView(R.id.payflow_note)
    TextView mExpandNote;
    @BindView(R.id.payflow_smart)
    SmartRefreshLayout mExpandSmart;
    @BindView(R.id.payflow_recy)
    RecyclerView mExpandRecy;
    private List<ShanghuDetailBean.DataBean.DataListBean> mMapList;
    private ExpandAdapter mExpandAdapter;
    private int page = 1;
    private CustomDatePicker customDatePickerSt;
    private String mUser_id = "1";

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_payflow;
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        initTvDate();
        mTitle.setText("商户数据详情");
        TitleUtils.setStatusTextColor(true, this);
        mUser_id = (String) ShareUtil.getInstance(this).get("user_id", "");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mExpandRecy.setLayoutManager(linearLayoutManager);
        mMapList = new ArrayList<>();
        mExpandAdapter = new ExpandAdapter(this, mMapList);
        mExpandRecy.setAdapter(mExpandAdapter);
        mExpandAdapter.setOnItemClickListener(new ExpandAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int pos) {
                Bundle bundle=new Bundle();
                ShanghuDetailBean.DataBean.DataListBean map = mMapList.get(pos);
                String date = map.getDate();
                Intent intent = new Intent(ExpandActivity.this, ExpandDeatilActivity.class);
                bundle.putString("date", date);
                bundle.putInt("zituo", map.getCount1());
                bundle.putInt("xiaoer", map.getCount2());
                intent.putExtra("data",bundle);
                startActivity(intent);
            }
        });
        mExpandSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getData();
            }
        });
        mExpandSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ++page;
                getData();
            }
        });
        mExpandSmart.autoRefresh();
        mExpandSmart.setEnableLoadMore(false);
    }

    @OnClick({R.id.imgv_back, R.id.payflow_date})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgv_back:
                finish();
                break;
            case R.id.payflow_date:
                showDateDialog(mExpandDate, "2010-01-01 00:00", "2035-12-12 00:00");
                break;
        }
    }

    private void getData() {
        final String date = mExpandDate.getText().toString();
        String d = date.replaceAll("年", "-");
        Map<String, Object> map = new HashMap<>();
        map.put("userId", mUser_id);
        map.put("date", d.replace("月", ""));
        map.put("currentPage", page);
        map.put("pageSize", 50);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).bussinessInfo(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                mExpandSmart.finishRefresh();
                mExpandSmart.finishLoadMore();
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    ShanghuDetailBean detailBean = new Gson().fromJson(result.toString(), ShanghuDetailBean.class);
                    mExpandNote.setText("共拓展" + BigDecimalUtils.bigUtil(detailBean.getData().getCount()) + "家商户");
                    if (page == 1) {
                        mMapList.clear();
                    }
                    mMapList.addAll(detailBean.getData().getDataList());
                    mExpandAdapter.notifyDataSetChanged();
                } else {
                    if (page == 1) {
                        mMapList.clear();
                        mExpandAdapter.notifyDataSetChanged();
                    }
                }
                if (!TextUtils.isEmpty(msg)) {
                    ToastShow(msg);
                }
            }

            @Override
            public void onErrorResult(HttpThrowable httpThrowable) {
                mExpandSmart.finishRefresh();
                mExpandSmart.finishLoadMore();
                ToastShow("接口访问异常" + httpThrowable.message);
                Log.d("qqq", httpThrowable.message);
            }
        });


    }


    private void initTvDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月");
        mExpandDate.setText(simpleDateFormat.format(new Date()));
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
                    mExpandSmart.autoRefresh();
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
