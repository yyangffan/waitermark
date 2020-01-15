package com.superc.waitmarket.ui.fragment;


import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.throwable.HttpThrowable;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.superc.waitmarket.R;
import com.superc.waitmarket.base.ApiService;
import com.superc.waitmarket.base.Constant;
import com.superc.waitmarket.base.WaitApplication;
import com.superc.waitmarket.httputil.EncryPtionHttp;
import com.superc.waitmarket.httputil.EncryPtionUtil;
import com.superc.yyfflibrary.base.BaseFragment;
import com.superc.yyfflibrary.utils.ShareUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;

/********************************************************************
 @version: 1.0.0
 @description: 查看-基础信息
 @author: EDZ
 @time: 2019/12/11 17:22
 @变更历史:
 ********************************************************************/
public class JiChuFragment extends BaseFragment {
    private static final String TAG = "JiChuFragment";
    @BindView(R.id.jichu_look_smart)
    SmartRefreshLayout mJichuLookSmart;
    Unbinder unbinder;
    @BindView(R.id.item_lookjichu_head)
    ImageView mItemLookjichuHead;
    @BindView(R.id.item_lookjichu_name)
    TextView mItemLookjichuName;
    @BindView(R.id.item_lookjichu_mingcheng)
    TextView mItemLookjichuMingcheng;
    @BindView(R.id.item_lookjichu_city)
    TextView mItemLookjichuCity;
    @BindView(R.id.item_lookjichu_quyu)
    TextView mItemLookjichuQuyu;
    @BindView(R.id.item_lookjichu_shangquan)
    TextView mItemLookjichuShangquan;
    @BindView(R.id.item_lookjichu_posi)
    TextView mItemLookjichuPosi;
    @BindView(R.id.item_lookjichu_yingyphone)
    TextView mItemLookjichuYingyphone;
    @BindView(R.id.item_lookjichu_lianxiren)
    TextView mItemLookjichuLianxiren;
    @BindView(R.id.item_lookjichu_lianxiphone)
    TextView mItemLookjichuLianxiphone;
    @BindView(R.id.item_lookjichu_onehang)
    TextView mItemLookjichuOnehang;
    @BindView(R.id.item_lookjichu_twohang)
    TextView mItemLookjichuTwohang;
    @BindView(R.id.item_lookjichu_time)
    TextView mItemLookjichuTime;
    @BindView(R.id.item_lookjichu_state)
    TextView mItemLookjichuState;
    @BindView(R.id.edtjichu_map)
    TextureMapView mMapView;
    @BindView(R.id.item_lookjichu_tuozhanren)
    TextView mTvTuozhanren;
    @BindView(R.id.item_lookjichu_quanyi)
    TextView mTvQuanyi;
    @BindView(R.id.item_lookjichu_xiaoekh)
    TextView mTvXiaoerKeh;


    private String mEdtdetail_id;
    private String channel;
    private AMap mMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ji_chu, container, false);
        unbinder = ButterKnife.bind(this, view);
        mMapView.onCreate(savedInstanceState);
        init();
        initMapView();
        return view;
    }

    @Override
    public void init() {
        mJichuLookSmart.setEnableOverScrollDrag(true);
        mJichuLookSmart.setEnablePureScrollMode(true);
        getData();
    }

    private void initMapView() {
        mMap = mMapView.getMap();
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(false);
    }

    /**
     * type//1基础信息2场景照片3资质信息4结算信息5老客6新客
     * channel 0待激活入口,已激活入口1待审核入口 3维护列表
     */
    public void getData() {
        mEdtdetail_id = (String) ShareUtil.getInstance(WaitApplication.getInstance()).get("edtdetail_id", "");
        channel = (String) ShareUtil.getInstance(WaitApplication.getInstance()).get("channel", "");
        Map<String, Object> map = new HashMap<>();
        map.put("shopId", mEdtdetail_id);
        map.put("type", 1);
        map.put("channel", channel);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).merchantDetails(EncryPtionUtil.getInstance(getActivity()).toEncryption(map));
        EncryPtionHttp.getInstance(getActivity()).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                Log.e(TAG, "onSuccessResult: " + result.toString());
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    if (result.getJSONObject("data").getJSONObject("merchantDetails") != null) {
                        setData(result.getJSONObject("data").getJSONObject("merchantDetails"));
                    } else {
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
            RoundedCorners roundedCorners = new RoundedCorners(10);
            RequestOptions override = RequestOptions.bitmapTransform(roundedCorners).error(R.drawable.icon_error).placeholder(R.drawable.icon_error).override(300, 300);
            RequestOptions requestOptions = new RequestOptions().error(R.drawable.icon_error).apply(override).placeholder(R.drawable.icon_error);
            String shoplogo = merchant.getString("ShopLogo");
            if (!TextUtils.isEmpty(shoplogo)) {
                if (shoplogo.startsWith("http") || shoplogo.startsWith("https")) {
                    Glide.with(this).load(shoplogo).apply(requestOptions).into(mItemLookjichuHead);
                } else {
                    Glide.with(this).load(Constant.IMG_URL + shoplogo).apply(requestOptions).into(mItemLookjichuHead);
                }
            } else {
                Glide.with(this).load(Constant.IMG_URL + shoplogo).apply(requestOptions).into(mItemLookjichuHead);
            }

            LatLng coordinate = new LatLng(Double.parseDouble(merchant.getString("AndroidLatitude")), Double.parseDouble(merchant.getString("AndroidLongitude")));
            mMap.addMarker(new MarkerOptions().position(coordinate).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(getResources(), R.drawable.location))));
            CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(coordinate, 8, 0, 0));
            mMap.moveCamera(mCameraUpdate);


            mItemLookjichuState.setVisibility(View.GONE);
            mItemLookjichuName.setText(merchant.getString("ShopName"));
            try {
                mItemLookjichuMingcheng.setText(merchant.getString("ShopBrand"));
            } catch (Exception e) {
                mItemLookjichuMingcheng.setText("");
                Log.e(TAG, "setData:未设置品牌名称 ");
            }
            mItemLookjichuOnehang.setText(merchant.getString("TypeName"));
            mItemLookjichuTwohang.setText(merchant.getString("SMTName"));
            mItemLookjichuCity.setText(merchant.getString("CityName"));
            mItemLookjichuQuyu.setText(merchant.getString("DistrictName"));
            mItemLookjichuShangquan.setText(merchant.getString("BusinessCircleName"));
            mItemLookjichuPosi.setText(merchant.getString("ShopAddress"));
            mItemLookjichuYingyphone.setText(merchant.getString("ShopTel"));
            mItemLookjichuLianxiren.setText(merchant.getString("ShopHead"));
            mItemLookjichuLianxiphone.setText(merchant.getString("HeadTel"));
            String devFirstLevelAttribution = merchant.getString("devFirstLevelAttribution");
            String devSecondLevelAttribution = merchant.getString("devSecondLevelAttribution");
            String developname = merchant.getString("developname");
            StringBuilder sb_tuoz=new StringBuilder();
            sb_tuoz.append(TextUtils.isEmpty(devFirstLevelAttribution)?"":devFirstLevelAttribution+",");
            sb_tuoz.append(TextUtils.isEmpty(devSecondLevelAttribution)?"":devSecondLevelAttribution+",");
            sb_tuoz.append(developname);
            String interestsFirstLevelAttribution = merchant.getString("interestsFirstLevelAttribution");
            String interestsSecondLevelAttribution = merchant.getString("interestsSecondLevelAttribution");
            String equityOwner = merchant.getString("equityOwner");
            StringBuilder sb_quany=new StringBuilder();
            sb_quany.append(TextUtils.isEmpty(interestsFirstLevelAttribution)?"":interestsFirstLevelAttribution+",");
            sb_quany.append(TextUtils.isEmpty(interestsSecondLevelAttribution)?"":interestsSecondLevelAttribution+",");
            sb_quany.append(equityOwner);

            mTvTuozhanren.setText(sb_tuoz.toString());
            mTvQuanyi.setText(sb_quany.toString());
            mTvXiaoerKeh.setText(merchant.getString("AddPerson"));

            StringBuilder stb_tm = new StringBuilder();
            JSONArray temActList = merchant.getJSONArray("temActList");
            stb_tm.append(temActList.getJSONObject(0).getString("starttime"));
            stb_tm.append("-");
            stb_tm.append(temActList.getJSONObject(0).getString("endtime"));
            if (temActList.size() >= 2) {
                stb_tm.append("\n");
                stb_tm.append(temActList.getJSONObject(1).getString("starttime"));
                stb_tm.append("-");
                stb_tm.append(temActList.getJSONObject(1).getString("endtime"));
            }
            if (temActList.size() == 3) {
                stb_tm.append("\n");
                stb_tm.append(temActList.getJSONObject(2).getString("starttime"));
                stb_tm.append("-");
                stb_tm.append(temActList.getJSONObject(2).getString("endtime"));
            }
            mItemLookjichuTime.setText(stb_tm.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        if (mMapView != null)
            mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        if (mMapView != null)
            mMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        if (mMapView != null)
            mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
