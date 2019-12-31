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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.throwable.HttpThrowable;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.superc.waitmarket.R;
import com.superc.waitmarket.base.ApiService;
import com.superc.waitmarket.base.WaitApplication;
import com.superc.waitmarket.bean.MarketDeatilBean;
import com.superc.waitmarket.httputil.EncryPtionHttp;
import com.superc.waitmarket.httputil.EncryPtionUtil;
import com.superc.waitmarket.ui.activity.EdtDetailActivity;
import com.superc.waitmarket.ui.activity.PhaseMarketSetActivity;
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
public class EdtZhifFragment extends BaseFragment {


    @BindView(R.id.item_lookzhif_new)
    TextView mItemLookzhifNew;
    @BindView(R.id.item_lookzhif_old)
    TextView mItemLookzhifOld;
    @BindView(R.id.jichu_look_smart)
    SmartRefreshLayout mJichuLookSmart;
    Unbinder unbinder;

    private String mEdtdetail_id;
    private String mIs_creat;
    private String mUser_id;
    private String channel;
    private EdtDetailActivity mEdtDetailActivity;
    private MarketDeatilBean mMarketDeatilBeanOld;
    private MarketDeatilBean mMarketDeatilBeanNew;
    private boolean is_creatNew=true, is_creatOld = true;
    private String new_id,old_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edt_zhif, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void init() {
        mEdtDetailActivity = (EdtDetailActivity) getActivity();
        mJichuLookSmart.setEnableOverScrollDrag(true);
        mJichuLookSmart.setEnablePureScrollMode(true);

    }

    @OnClick({R.id.item_lookzhif_new, R.id.item_lookzhif_old})
    public void onViewClicked(View view) {
        Intent intent = new Intent(this.getActivity(), PhaseMarketSetActivity.class);
        switch (view.getId()) {
            case R.id.item_lookzhif_old:
                if (is_creatOld) {
                    intent.putExtra("isnewcustomer", "0");
                    intent.putExtra("is_creat", is_creatOld);
                } else {
                    intent.putExtra("is_creat", is_creatOld);
                    intent.putExtra("isnewcustomer", "0");
                    intent.putExtra("id", old_id);
                    intent.putExtra("data", new Gson().toJson(mMarketDeatilBeanOld));
                }
                startActivity(intent);

                break;
            case R.id.item_lookzhif_new:
                if (is_creatNew) {
                    intent.putExtra("is_creat", is_creatNew);
                    intent.putExtra("isnewcustomer", "1");
                } else {
                    intent.putExtra("is_creat", is_creatNew);
                    intent.putExtra("isnewcustomer", "1");
                    intent.putExtra("id", new_id);
                    intent.putExtra("data", new Gson().toJson(mMarketDeatilBeanNew));
                }
                startActivity(intent);
                break;

        }
    }

    /* mIs_creat  0新建1修改*/
    public void toJudge() {
        mEdtdetail_id = (String) ShareUtil.getInstance(WaitApplication.getInstance()).get("edtdetail_id", "");
        channel = (String) ShareUtil.getInstance(WaitApplication.getInstance()).get("channel", "");
        toGetEdtData();
    }

    private void toGetEdtData() {
        Map<String, Object> map = new HashMap<>();
        map.put("shopId", mEdtdetail_id);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).eventsList(EncryPtionUtil.getInstance(getActivity()).toEncryption(map));
        EncryPtionHttp.getInstance(getActivity()).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                Log.d("qqq", result.toJSONString());
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    JSONArray data = result.getJSONArray("data");
                    setData(data);
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

    private void setData(JSONArray data) {
        if (data != null) {
            if (data.size() == 0) {
                is_creatNew = true;
                is_creatOld = true;
                mItemLookzhifOld.setText("去设置");
                mItemLookzhifOld.setTextColor(getResources().getColor(R.color.main_color));
                mItemLookzhifNew.setText("去设置");
                mItemLookzhifNew.setTextColor(getResources().getColor(R.color.main_color));
            } else {
                if (data.size() == 1) {
                    JSONObject jsonObject = data.getJSONObject(0);
                    toPanDuan(jsonObject,1);
                } else {
                    JSONObject jsonObject = data.getJSONObject(0);
                    JSONObject jsonObjectw = data.getJSONObject(1);
                    toPanDuan(jsonObject,2);
                    toPanDuan(jsonObjectw,2);
                }
            }
        } else {
            is_creatNew = true;
            is_creatOld = true;
            mItemLookzhifNew.setText("去设置");
            mItemLookzhifOld.setText("去设置");
            mItemLookzhifNew.setTextColor(getResources().getColor(R.color.main_color));
            mItemLookzhifOld.setTextColor(getResources().getColor(R.color.main_color));
        }
    }

    private void toPanDuan(JSONObject jsonObject,int what){
        String isnewcu=BigDecimalUtils.bigUtil(jsonObject.getString("isnewcustomer"));
        if(isnewcu.equals("0")){//老客
            is_creatOld = false;
            old_id=jsonObject.getString("id");
            mItemLookzhifOld.setText(jsonObject.getString("actName"));
            mItemLookzhifOld.setTextColor(getResources().getColor(R.color.black));
            if(what==1) {
                mItemLookzhifNew.setText("去设置");
                mItemLookzhifNew.setTextColor(getResources().getColor(R.color.main_color));
            }
            getDetailMsg(old_id,true);
        }else{//新客
            is_creatNew = false;
            new_id=jsonObject.getString("id");
            mItemLookzhifNew.setText(jsonObject.getString("actName"));
            mItemLookzhifNew.setTextColor(getResources().getColor(R.color.black));
            if(what==1) {
                mItemLookzhifOld.setText("去设置");
                mItemLookzhifOld.setTextColor(getResources().getColor(R.color.main_color));
            }
            getDetailMsg(new_id,false);
        }

    }

    public void toCommit() {
        mEdtDetailActivity.toScroll();
    }


    /*获取驳回以及详细信息查看*/
    private void getDetailMsg(String id, final boolean is_old) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("shopId", mEdtdetail_id);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).currentActivity(EncryPtionUtil.getInstance(getActivity()).toEncryption(map));
        EncryPtionHttp.getInstance(getActivity()).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                boolean code = result.getBoolean("code");
                if (code) {
                    if(is_old) {
                        mMarketDeatilBeanOld = new Gson().fromJson(result.toString(), MarketDeatilBean.class);
                    }else{
                        mMarketDeatilBeanNew = new Gson().fromJson(result.toString(), MarketDeatilBean.class);
                    }
                }
            }

            @Override
            public void onErrorResult(HttpThrowable httpThrowable) {
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
