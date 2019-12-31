package com.superc.waitmarket.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.throwable.HttpThrowable;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.superc.waitmarket.R;
import com.superc.waitmarket.base.ApiService;
import com.superc.waitmarket.base.WaitApplication;
import com.superc.waitmarket.httputil.EncryPtionHttp;
import com.superc.waitmarket.httputil.EncryPtionUtil;
import com.superc.waitmarket.utils.BigDecimalUtils;
import com.superc.yyfflibrary.base.BaseFragment;
import com.superc.yyfflibrary.utils.ShareUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;

/**
 * A simple {@link Fragment} subclass.
 */
public class OldFragment extends BaseFragment {


    @BindView(R.id.item_lookold_content)
    TextView mItemLookoldContent;
    @BindView(R.id.item_lookold_leixing)
    TextView mItemLookoldLeixing;
    @BindView(R.id.item_lookold_time)
    TextView mItemLookoldTime;
    @BindView(R.id.item_lookold_timeduan)
    TextView mItemLookoldTimeduan;
    @BindView(R.id.item_lookold_jiejiashifo)
    TextView mItemLookoldJiejiashifo;
    @BindView(R.id.item_lookold_jiejiaduan)
    TextView mItemLookoldJiejiaduan;
    @BindView(R.id.item_lookold_guize)
    TextView mItemLookoldGuize;
    @BindView(R.id.item_lookold_shijianduan)
    TextView mItemLookoldShijianduan;
    @BindView(R.id.jichu_look_smart)
    SmartRefreshLayout mJichuLookSmart;
    Unbinder unbinder;
    private String mEdtdetail_id;
    private String channel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_old, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void init() {
        mJichuLookSmart.setEnableOverScrollDrag(true);
        mJichuLookSmart.setEnablePureScrollMode(true);

        getData();
    }

    public void getData() {
        mEdtdetail_id = (String) ShareUtil.getInstance(WaitApplication.getInstance()).get("edtdetail_id", "");
        channel = (String) ShareUtil.getInstance(WaitApplication.getInstance()).get("channel", "");
        Map<String, Object> map = new HashMap<>();
        map.put("shopId", mEdtdetail_id);
        map.put("type", 5);
        map.put("channel", channel);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).merchantDetails(EncryPtionUtil.getInstance(getActivity()).toEncryption(map));
        EncryPtionHttp.getInstance(getActivity()).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    if(result.getJSONObject("data").getJSONObject("merchantDetails")!=null) {
                        setData(result.getJSONObject("data").getJSONObject("merchantDetails"));
                    }else{
                        ToastShow("数据获取失败");
                    }
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

    private void setData(JSONObject merchant) {
        try {
            JSONObject temActMap = merchant.getJSONObject("temActMap");
            if (temActMap != null) {
                String coupontype = temActMap.getString("coupontype");
                switch (coupontype) {
                    case "1"://满减券
                        mItemLookoldLeixing.setText("满减券");
                        break;
                    case "2"://折扣券
                        mItemLookoldLeixing.setText("折扣券");
                        break;
                    case "3"://赠品券
                        mItemLookoldLeixing.setText("赠品券");
                        break;
                    case "5"://体验券
                        mItemLookoldLeixing.setText("体验券");
                        break;
                    case "6"://代金券
                        mItemLookoldLeixing.setText("代金券");
                        break;
                }
                mItemLookoldContent.setText(temActMap.getString("actName"));
                StringBuilder stb_timee = new StringBuilder("");
                String weekdays = temActMap.getString("weekdays");
                if (!TextUtils.isEmpty(weekdays)) {
                    String weekdays_new = weekdays.replaceAll(",", "");
                    if (weekdays_new.length() == 7) {
                        stb_timee.append("周一至周日,");
                    } else {
                        stb_timee.append(" ");
                        stb_timee.append(weekdays_new.contains("1") ? "周一，" : "");
                        stb_timee.append(weekdays_new.contains("2") ? "周二，" : "");
                        stb_timee.append(weekdays_new.contains("3") ? "周三，" : "");
                        stb_timee.append(weekdays_new.contains("4") ? "周四，" : "");
                        stb_timee.append(weekdays_new.contains("5") ? "周五，" : "");
                        stb_timee.append(weekdays_new.contains("6") ? "周六，" : "");
                        stb_timee.append(weekdays_new.contains("7") ? "周日，" : "");
                    }
                }
                String res = stb_timee.toString();
                mItemLookoldTime.setText(res.substring(0, res.length() - 1));

                JSONArray timeList = temActMap.getJSONArray("timeList");
                StringBuilder stb_time = new StringBuilder();
                for (int i = 0; i < timeList.size(); i++) {
                    JSONObject jsonObject = timeList.getJSONObject(i);
                    if (i == 0) {
                        stb_time.append(jsonObject.getString("starttime") + "-" + jsonObject.getString("endtime"));
                    } else {
                        stb_time.append("；");
                        stb_time.append(jsonObject.getString("starttime") + "-" + jsonObject.getString("endtime"));
                    }
                }
                mItemLookoldTimeduan.setText(stb_time.toString());

                String usagerules = temActMap.getString("usagerules");
                if (!TextUtils.isEmpty(usagerules)) {
                    mItemLookoldGuize.setText(usagerules.replace("#", "、"));
                }
                mItemLookoldShijianduan.setText("自生效日起" + BigDecimalUtils.bigUtil(temActMap.getString("limitvalidity")) + "天有效");

            }
            mItemLookoldJiejiashifo.setText("- -");
            mItemLookoldJiejiaduan.setText("- -");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
