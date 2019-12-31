package com.superc.waitmarket.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.throwable.HttpThrowable;
import com.superc.waitmarket.R;
import com.superc.waitmarket.adapter.ChangjLookAdapter;
import com.superc.waitmarket.base.ApiService;
import com.superc.waitmarket.base.Constant;
import com.superc.waitmarket.base.WaitApplication;
import com.superc.waitmarket.httputil.EncryPtionHttp;
import com.superc.waitmarket.httputil.EncryPtionUtil;
import com.superc.waitmarket.utils.picselector.FullyGridLayoutManager;
import com.superc.yyfflibrary.base.BaseFragment;
import com.superc.yyfflibrary.utils.ShareUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;

/********************************************************************
 @version: 1.0.0
 @description:查看-场景图片
 @author: EDZ
 @time: 2019/12/11 18:40
 @变更历史:
 ********************************************************************/
public class ChangjPicFragment extends BaseFragment {


    @BindView(R.id.item_hangjpiclook_head)
    ImageView mItemHangjpiclookHead;
    @BindView(R.id.item_hangjpiclook_recy)
    RecyclerView mItemHangjpiclookRecy;
    Unbinder unbinder;
    private ChangjLookAdapter mChangjLookAdapter;
    private List<Map<String, Object>> mMapList;
    private String mEdtdetail_id;
    private String channel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_changj_pic, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void init() {
        mMapList = new ArrayList<>();
        FullyGridLayoutManager manager = new FullyGridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        mItemHangjpiclookRecy.setLayoutManager(manager);
        mChangjLookAdapter = new ChangjLookAdapter(this.getActivity(), mMapList);
        mItemHangjpiclookRecy.setAdapter(mChangjLookAdapter);

        getData();
    }

    public void getData() {
        mEdtdetail_id = (String) ShareUtil.getInstance(WaitApplication.getInstance()).get("edtdetail_id", "");
        channel = (String) ShareUtil.getInstance(WaitApplication.getInstance()).get("channel", "");
        Map<String, Object> map = new HashMap<>();
        map.put("shopId", mEdtdetail_id);
        map.put("type", 2);
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

    private  void setData(JSONObject merchant){
        try {
            String mPicSmallPath = merchant.getString("mpicPath");
            RoundedCorners roundedCorners = new RoundedCorners(10);
            RequestOptions override = RequestOptions.bitmapTransform(roundedCorners).error(R.drawable.icon_error).placeholder(R.drawable.icon_error).override(300, 300);
            RequestOptions requestOptions = new RequestOptions().error(R.drawable.icon_error).apply(override).placeholder(R.drawable.icon_error);
            if (!TextUtils.isEmpty(mPicSmallPath)) {
                if (mPicSmallPath.startsWith("http") || mPicSmallPath.startsWith("https")) {
                    Glide.with(this).load(mPicSmallPath).apply(requestOptions).into(mItemHangjpiclookHead);
                } else {
                    Glide.with(this).load(Constant.IMG_URL + mPicSmallPath).apply(requestOptions).into(mItemHangjpiclookHead);
                }
            }
            JSONArray timeList = merchant.getJSONArray("timeList");
            for (int i = 0; i < timeList.size(); i++) {
                Map<String,Object>  map=new HashMap<>();
                String lpicPath = timeList.getJSONObject(i).getString("lpicPath");
                if (!TextUtils.isEmpty(lpicPath)) {
                    if (lpicPath.startsWith("http") || lpicPath.startsWith("https")) {
                        map.put("img_url",lpicPath);
                    } else {
                        map.put("img_url",Constant.IMG_URL + lpicPath);
                    }
                    mMapList.add(map);
                }
            }
            mChangjLookAdapter.notifyDataSetChanged();
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
