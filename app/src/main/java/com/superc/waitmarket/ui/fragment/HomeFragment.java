package com.superc.waitmarket.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.throwable.HttpThrowable;
import com.superc.waitmarket.R;
import com.superc.waitmarket.base.ApiService;
import com.superc.waitmarket.base.WaitApplication;
import com.superc.waitmarket.httputil.EncryPtionHttp;
import com.superc.waitmarket.httputil.EncryPtionUtil;
import com.superc.waitmarket.ui.activity.ExpandActivity;
import com.superc.waitmarket.ui.activity.MerchantActivity;
import com.superc.waitmarket.ui.activity.PayflowActivity;
import com.superc.waitmarket.utils.BigDecimalUtils;
import com.superc.yyfflibrary.base.BaseFragment;
import com.superc.yyfflibrary.utils.ShareUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {


    @BindView(R.id.home_once_hisnum)
    TextView mHomeOnceHisnum;
    @BindView(R.id.home_once_monthnum)
    TextView mHomeOnceMonthnum;
    @BindView(R.id.home_once_todaynum)
    TextView mHomeOnceTodaynum;
    @BindView(R.id.home_once_monthwhat)
    TextView mHomeOnceMonthwhat;
    @BindView(R.id.home_once_todaywhat)
    TextView mHomeOnceTodaywhat;
    @BindView(R.id.home_twice_hisnum)
    TextView mHomeTwiceHisnum;
    @BindView(R.id.home_twice_monthnum)
    TextView mHomeTwiceMonthnum;
    @BindView(R.id.home_twice_todaynum)
    TextView mHomeTwiceTodaynum;
    @BindView(R.id.home_twice_monthwhat)
    TextView mHomeTwiceMonthwhat;
    @BindView(R.id.home_twice_todaywhat)
    TextView mHomeTwiceTodaywhat;
    @BindView(R.id.home_third_hisnum)
    TextView mHomeThirdHisnum;
    @BindView(R.id.home_third_monthnum)
    TextView mHomeThirdMonthnum;
    @BindView(R.id.home_third_todaynum)
    TextView mHomeThirdTodaynum;
    @BindView(R.id.home_third_monthwhat)
    TextView mHomeThirdMonthwhat;
    @BindView(R.id.home_third_todaywhat)
    TextView mHomeThirdTodaywhat;
    @BindView(R.id.home_once_monthwhatxiajiang)
    TextView mHomeOnceMonthwhatxiajiang;
    @BindView(R.id.home_once_todaywhatshangsheng)
    TextView mHomeOnceTodaywhatshangsheng;
    @BindView(R.id.home_twice_monthwhatxiajiang)
    TextView mHomeTwiceMonthwhatxiajiang;
    @BindView(R.id.home_twice_todaywhatshangsheng)
    TextView mHomeTwiceTodaywhatshangsheng;
    @BindView(R.id.home_third_monthwhatxiajiang)
    TextView mHomeThirdMonthwhatxiajiang;
    @BindView(R.id.home_third_todaywhatshangsheng)
    TextView mHomeThirdTodaywhatshangsheng;
    Unbinder unbinder;
    private String mUser_id = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    @OnClick({R.id.home_once_detail, R.id.home_twice_detail, R.id.home_third_detail})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_once_detail:
                startActivity(new Intent(this.getActivity(), ExpandActivity.class));
                break;
            case R.id.home_twice_detail:
                startActivity(new Intent(this.getActivity(), PayflowActivity.class));
                break;
            case R.id.home_third_detail:
                startActivity(new Intent(this.getActivity(), MerchantActivity.class));
                break;
        }
    }

    @Override
    public void init() {
//        mUser_id = (String) ShareUtil.getInstance(this.getActivity()).get("user_id", "");
        getData();


    }

    public void getData() {
        if (TextUtils.isEmpty(mUser_id)) {
            mUser_id = (String) ShareUtil.getInstance(WaitApplication.getInstance()).get("user_id", "");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", mUser_id);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).resultManagerIndex(EncryPtionUtil.getInstance(getActivity()).toEncryption(map));
        EncryPtionHttp.getInstance(this.getActivity()).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    setData(result.getJSONObject("data"));
                }
                if (!TextUtils.isEmpty(msg)) {
                    ToastShow(msg);
                }
            }

            @Override
            public void onErrorResult(HttpThrowable httpThrowable) {
                ToastShow("接口访问异常" + httpThrowable.message);
                Log.d("qqq", httpThrowable.message);
            }
        });
    }

    private void setData(JSONObject jsonObject) {
        /*--------------------------------商户拓展数-----------------------------------*/
        mHomeOnceHisnum.setText(BigDecimalUtils.bigUtil(jsonObject.getString("historyCount")));
        mHomeOnceMonthnum.setText(BigDecimalUtils.bigUtil(jsonObject.getString("monthCount")));
        mHomeOnceTodaynum.setText(BigDecimalUtils.bigUtil(jsonObject.getString("todayCount")));

        boolean countMonthFlag = jsonObject.getBoolean("countMonthFlag");//本月 true 增加  false 减少
        mHomeOnceMonthwhat.setVisibility(countMonthFlag ? View.VISIBLE : View.GONE);
        mHomeOnceMonthwhatxiajiang.setVisibility(countMonthFlag ? View.GONE : View.VISIBLE);
        mHomeOnceMonthwhat.setText(jsonObject.getString("countMonthPercentage"));
        mHomeOnceMonthwhatxiajiang.setText(jsonObject.getString("countMonthPercentage"));

        boolean countDayFlag = jsonObject.getBoolean("countDayFlag");//今日 true 增加  false 减少
        mHomeOnceTodaywhat.setVisibility(countDayFlag ? View.GONE : View.VISIBLE);
        mHomeOnceTodaywhatshangsheng.setVisibility(countDayFlag ? View.VISIBLE : View.GONE);
        mHomeOnceTodaywhat.setText(jsonObject.getString("countDayPercentage"));
        mHomeOnceTodaywhatshangsheng.setText(jsonObject.getString("countDayPercentage"));
        /*-------------------------------交易流水------------------------------*/
        mHomeTwiceHisnum.setText(BigDecimalUtils.bigUtil(jsonObject.getString("historySum")));
        mHomeTwiceMonthnum.setText(BigDecimalUtils.bigUtil(jsonObject.getString("monthSum")));
        mHomeTwiceTodaynum.setText(BigDecimalUtils.bigUtil(jsonObject.getString("todaySum")));

        boolean sumMonthFlag = jsonObject.getBoolean("sumMonthFlag");
        mHomeTwiceMonthwhat.setVisibility(sumMonthFlag ? View.VISIBLE : View.GONE);
        mHomeTwiceMonthwhatxiajiang.setVisibility(sumMonthFlag ? View.GONE : View.VISIBLE);
        mHomeTwiceMonthwhat.setText(jsonObject.getString("sumMonthPercentage"));
        mHomeTwiceMonthwhatxiajiang.setText(jsonObject.getString("sumMonthPercentage"));

        boolean sumDayFlag = jsonObject.getBoolean("sumDayFlag");
        mHomeTwiceTodaywhat.setVisibility(sumDayFlag ? View.GONE : View.VISIBLE);
        mHomeTwiceTodaywhatshangsheng.setVisibility(sumDayFlag ? View.VISIBLE : View.GONE);
        mHomeTwiceTodaywhat.setText(jsonObject.getString("sumDayPercentage"));
        mHomeTwiceTodaywhatshangsheng.setText(jsonObject.getString("sumDayPercentage"));
        /*----------------------------------商户产生流量数据----------------------------*/
        mHomeThirdHisnum.setText(BigDecimalUtils.bigUtil(jsonObject.getString("historyFlow")));
        mHomeThirdMonthnum.setText(BigDecimalUtils.bigUtil(jsonObject.getString("monthFlow")));
        mHomeThirdTodaynum.setText(BigDecimalUtils.bigUtil(jsonObject.getString("todayFlow")));

        boolean flowMonthFlag = jsonObject.getBoolean("flowMonthFlag");
        mHomeThirdMonthwhat.setVisibility(flowMonthFlag ? View.VISIBLE : View.GONE);
        mHomeThirdMonthwhatxiajiang.setVisibility(flowMonthFlag ? View.GONE : View.VISIBLE);
        mHomeThirdMonthwhat.setText(jsonObject.getString("flowMonthPercentage"));
        mHomeThirdMonthwhatxiajiang.setText(jsonObject.getString("flowMonthPercentage"));

        boolean flowDayFlag = jsonObject.getBoolean("flowDayFlag");
        mHomeThirdTodaywhat.setVisibility(flowDayFlag ? View.GONE : View.VISIBLE);
        mHomeThirdTodaywhatshangsheng.setVisibility(flowDayFlag ? View.VISIBLE : View.GONE);
        mHomeThirdTodaywhat.setText(jsonObject.getString("flowDayPercentage"));
        mHomeThirdTodaywhatshangsheng.setText(jsonObject.getString("flowDayPercentage"));

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
