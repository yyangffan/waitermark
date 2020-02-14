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
import com.superc.waitmarket.adapter.MerChantAdapter;
import com.superc.waitmarket.base.ApiService;
import com.superc.waitmarket.bean.MerchantBean;
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

public class MerchantActivity extends BaseActivity {

    @BindView(R.id.merchant_date)
    TextView mMerchantDate;
    @BindView(R.id.merchant_note)
    TextView mMerchantNote;
    @BindView(R.id.merchant_recy)
    RecyclerView mMerchantRecy;
    @BindView(R.id.merchant_smart)
    SmartRefreshLayout mMerchantSmart;
    private List<MerchantBean.DataBean.DataListBean> mMapList;
    private MerChantAdapter mMerChantAdapter;
    private int page = 1;
    private CustomDatePicker customDatePickerSt;
    private String mUser_id = "1";

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_merchant;
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        initTvDate();
        TitleUtils.setStatusTextColor(true, this);
        mUser_id = (String) ShareUtil.getInstance(this).get("user_id", "");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mMerchantRecy.setLayoutManager(linearLayoutManager);
        mMapList = new ArrayList<>();
        mMerChantAdapter = new MerChantAdapter(this, mMapList);
        mMerchantRecy.setAdapter(mMerChantAdapter);
        mMerchantSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getData();
            }
        });
        mMerchantSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ++page;
                getData();
            }
        });
        mMerchantSmart.autoRefresh();
        mMerchantSmart.setEnableLoadMore(false);
    }

    @OnClick({R.id.imgv_back, R.id.merchant_date})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgv_back:
                finish();
                break;
            case R.id.merchant_date:
                showDateDialog(mMerchantDate, "2010-01-01 00:00", "2035-12-12 00:00");
                break;
        }
    }

    private void getData() {
        final String date = mMerchantDate.getText().toString();
        String d = date.replaceAll("年", "-");
        Map<String, Object> map = new HashMap<>();
        map.put("userId", mUser_id);
        map.put("date", d.replace("月", ""));
        map.put("currentPage", page);
        map.put("pageSize", 10);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).businessFlowDetail(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                mMerchantSmart.finishRefresh();
                mMerchantSmart.finishLoadMore();
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    MerchantBean merchantBean = new Gson().fromJson(result.toString(), MerchantBean.class);
                    mMerchantNote.setText("共产生" + BigDecimalUtils.bigUtil(merchantBean.getData().getSum()) + "次");
                    if (page == 1) {
                        mMapList.clear();
                    }
                    mMapList.addAll(merchantBean.getData().getDataList());
                    mMerChantAdapter.notifyDataSetChanged();

                } else {
                    if (page == 1) {
                        mMapList.clear();
                        mMerChantAdapter.notifyDataSetChanged();
                    }
                }
                if (!TextUtils.isEmpty(msg)) {
                    ToastShow(msg);
                }
            }

            @Override
            public void onErrorResult(HttpThrowable httpThrowable) {
                mMerchantSmart.finishRefresh();
                mMerchantSmart.finishLoadMore();
                ToastShow("接口访问异常" + httpThrowable.message);
                Log.d("qqq", httpThrowable.message);
            }
        });

    }


    private void initTvDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月");
        mMerchantDate.setText(simpleDateFormat.format(new Date()));
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
                    mMerchantSmart.autoRefresh();
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
