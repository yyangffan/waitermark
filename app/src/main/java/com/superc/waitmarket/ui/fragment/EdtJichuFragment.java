package com.superc.waitmarket.ui.fragment;


import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Poi;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.observer.CommonObserver;
import com.ljy.devring.http.support.throwable.HttpThrowable;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.superc.waitmarket.R;
import com.superc.waitmarket.base.ApiService;
import com.superc.waitmarket.base.Constant;
import com.superc.waitmarket.base.WaitApplication;
import com.superc.waitmarket.bean.BotListBean;
import com.superc.waitmarket.httputil.EncryPtionHttp;
import com.superc.waitmarket.httputil.EncryPtionUtil;
import com.superc.waitmarket.ui.activity.EdtDetailActivity;
import com.superc.waitmarket.utils.BigDecimalUtils;
import com.superc.waitmarket.utils.CheckDateRule;
import com.superc.waitmarket.utils.PoiOverlay;
import com.superc.waitmarket.utils.dialog.DialogBotList;
import com.superc.waitmarket.utils.dialog.MiddleDialog;
import com.superc.waitmarket.views.InConstranLayout;
import com.superc.yyfflibrary.base.BaseFragment;
import com.superc.yyfflibrary.dialog.YfsRemindDialog;
import com.superc.yyfflibrary.utils.ShareUtil;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.yogee.customdatepicker.datepicker.CustomDatePicker;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
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
import butterknife.Unbinder;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;

public class EdtJichuFragment extends BaseFragment implements PoiSearch.OnPoiSearchListener, AMap.OnPOIClickListener, GeocodeSearch.OnGeocodeSearchListener {
    private static final String TAG = "EdtJichuFragment";
    @BindView(R.id.edtjichu_mendname)
    EditText mEdtjichuMendname;
    @BindView(R.id.edtjichu_shanghuname)
    EditText mEdtjichuShanghuname;
    @BindView(R.id.edtjichu_onehangy)
    TextView mEdtjichuOnehangy;
    @BindView(R.id.edtjichu_twohangy)
    TextView mEdtjichuTwohangy;
    @BindView(R.id.edtjichu_city)
    TextView mEdtjichuCity;
    @BindView(R.id.edtjichu_quyu)
    TextView mEdtjichuQuyu;
    @BindView(R.id.edtjichu_shangquan)
    TextView mEdtjichuShangquan;
    @BindView(R.id.edtjichu_dizhi)
    EditText mEdtjichuDizhi;
    @BindView(R.id.edtjichu_yingyephone)
    EditText mEdtjichuYingyephone;
    @BindView(R.id.edtjichu_stone)
    TextView mEdtjichuStone;
    @BindView(R.id.edtjichu_edone)
    TextView mEdtjichuEdone;
    @BindView(R.id.edtjichu_sttwo)
    TextView mEdtjichuSttwo;
    @BindView(R.id.edtjichu_edtwo)
    TextView mEdtjichuEdtwo;
    @BindView(R.id.phase_marketset_lltimetwo)
    RelativeLayout mPhaseMarketsetLltimetwo;
    @BindView(R.id.edtjichu_twode)
    TextView mEdtjichuTwode;
    @BindView(R.id.textView53)
    TextView mtvRe;
    @BindView(R.id.edtjichu_stthree)
    TextView mEdtjichuStthree;
    @BindView(R.id.edtjichu_edthree)
    TextView mEdtjichuEdthree;
    @BindView(R.id.phase_marketset_llthreetime)
    RelativeLayout mPhaseMarketsetLlthreetime;
    @BindView(R.id.edtjichu_threede)
    TextView mEdtjichuThreede;
    @BindView(R.id.edtjichu_addtm)
    TextView mEdtjichuAddtm;
    @BindView(R.id.edtjichu_map)
    TextureMapView mMapView;
    @BindView(R.id.edtjichu_lianxiren)
    EditText mEdtjichuLianxiren;
    @BindView(R.id.edtjichu_lianxiphone)
    EditText mEdtjichuLianxiphone;
    @BindView(R.id.edtjichu_head)
    ImageView mEdtjichuHead;
    @BindView(R.id.yinhang_delete)
    ImageView mImageDelete;
    @BindView(R.id.edtjichu_tianjia)
    TextView mEdtjichuTianjia;
    @BindView(R.id.edtjichu_linear)
    LinearLayout mEdtjichuLinear;
    @BindView(R.id.jichu_look_smart)
    SmartRefreshLayout mJichuLookSmart;
    @BindView(R.id.incon_con)
    InConstranLayout mIncon;
    Unbinder unbinder;
    private CustomDatePicker customTimePickerSt;
    private boolean ll_twiceTimeShow, ll_thirdTimeShow = false;
    private List<Map<String, String>> mList_time;
    private YfsRemindDialog mYfsRemindDialog;
    private int delet_what = 0;
    private AMap mMap;
    private PoiSearch poiSearch;
    private PoiSearch.Query mQuery;
    /*private boolean is_click = false;*/
    private String mUser_id;
    private String head_url;
    private DialogBotList mDialogBotList_yiji;
    private DialogBotList mDialogBotList_erji;
    private DialogBotList mDialogBotList_city;
    private DialogBotList mDialogBotList_quyu;
    private DialogBotList mDialogBotList_shangquan;
    private List<BotListBean> mBotListBeans_yiji;
    private List<BotListBean> mBotListBeans_erji;
    private List<BotListBean> mBotListBeans_city;
    private List<BotListBean> mBotListBeans_quyu;
    private List<BotListBean> mBotListBeans_shagnquan;
    private String city_code, quyu_code, shangquan_code, one_code, two_code;
    private double click_lat, click_lon;
    private String mPicSmallPath;
    private String mEdtdetail_id;
    private String mIs_creat;//0新建1修改
    private boolean is_succ = false;
    private EdtDetailActivity mEdtDetailActivity;
    private String channel;
    private GeocodeSearch geocoderSearch;
    private boolean is_xuanze = false;
    private String mRealname;
    private String mStatus;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edt_jichu, container, false);
        unbinder = ButterKnife.bind(this, view);
        mMapView.onCreate(savedInstanceState);
        init();
        return view;
    }

    @Override
    public void init() {
        mEdtDetailActivity = (EdtDetailActivity) getActivity();
        mJichuLookSmart.setEnableOverScrollDrag(true);
        mJichuLookSmart.setEnablePureScrollMode(true);
        mtvRe.requestFocus();
        mList_time = new ArrayList<>();
        mBotListBeans_yiji = new ArrayList<>();
        mBotListBeans_erji = new ArrayList<>();
        mBotListBeans_city = new ArrayList<>();
        mBotListBeans_quyu = new ArrayList<>();
        mBotListBeans_shagnquan = new ArrayList<>();
        mStatus = (String) ShareUtil.getInstance(WaitApplication.getInstance()).get("status", "");
        initMapView();
        initDialog();
        toJudge();
    }

    /* mIs_creat  0新建1修改*/
    public void toJudge() {
        mEdtdetail_id = (String) ShareUtil.getInstance(WaitApplication.getInstance()).get("edtdetail_id", "");
        mIs_creat = (String) ShareUtil.getInstance(WaitApplication.getInstance()).get("is_creat", "0");
        mUser_id = (String) ShareUtil.getInstance(WaitApplication.getInstance()).get("user_id", "");
        channel = (String) ShareUtil.getInstance(WaitApplication.getInstance()).get("channel", "");
        mRealname = (String) ShareUtil.getInstance(WaitApplication.getInstance()).get("realname", "");
        mStatus = (String) ShareUtil.getInstance(WaitApplication.getInstance()).get("status", "");
        if (mStatus.equals("1") && mIncon != null) {//1 不可编辑  0可编辑
            mIncon.setmIsIntercept(true);
        }
        if (mIs_creat.equals("1")) {
            toGetEdtData();
        }
    }

    /**
     * type//1基础信息2场景照片3资质信息4结算信息5老客6新客
     * channel 0待激活入口,已激活入口1待审核入口 3维护列表
     */
    private void toGetEdtData() {
        Map<String, Object> map = new HashMap<>();
        map.put("shopId", mEdtdetail_id);
        map.put("type", 1);
        map.put("channel", channel);
        map.put("staus", mStatus);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).merchantDetails(EncryPtionUtil.getInstance(getActivity()).toEncryption(map));
        EncryPtionHttp.getInstance(getActivity()).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    setData(result.getJSONObject("data").getJSONObject("merchantDetails"));
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
        /* is_click=true;*/
        mtvRe.requestFocus();
        mEdtjichuMendname.setText(merchant.getString("ShopName"));
        try {
            mEdtjichuShanghuname.setText(merchant.getString("ShopBrand"));
        } catch (Exception e) {
            mEdtjichuShanghuname.setText("");
            Log.e(TAG, "setData:未设置品牌名称 ");
        }
        mEdtjichuOnehangy.setText(merchant.getString("TypeName"));
        mEdtjichuTwohangy.setText(merchant.getString("SMTName"));
        mEdtjichuCity.setText(merchant.getString("CityName"));
        mEdtjichuQuyu.setText(merchant.getString("DistrictName"));
        mEdtjichuShangquan.setText(merchant.getString("BusinessCircleName"));
        mEdtjichuDizhi.setText(merchant.getString("ShopAddress"));
        mEdtjichuYingyephone.setText(merchant.getString("ShopTel"));
        mEdtjichuLianxiren.setText(merchant.getString("ShopHead"));
        mEdtjichuLianxiphone.setText(merchant.getString("HeadTel"));
        mPicSmallPath = merchant.getString("ShopLogo");
        one_code = BigDecimalUtils.bigUtil(merchant.getString("shopType"));
        getSecondaryIndustry(one_code);
        two_code = BigDecimalUtils.bigUtil(merchant.getString("smallType"));
        city_code = merchant.getString("CityCode");
        getDistrict(city_code);
        quyu_code = merchant.getString("DistrictCode");
        getBusinessCircle(city_code, quyu_code);
        shangquan_code = merchant.getString("BusinessCircleID");

        try {
            click_lat = Double.parseDouble(merchant.getString("AndroidLatitude"));
            click_lon = Double.parseDouble(merchant.getString("AndroidLongitude"));
        } catch (NumberFormatException e) {
            Log.e(TAG, "setData:未设置经纬度 ");
        }
        ll_twiceTimeShow = false;
        ll_thirdTimeShow = false;
        mPhaseMarketsetLltimetwo.setVisibility(View.GONE);
        mPhaseMarketsetLlthreetime.setVisibility(View.GONE);
        mEdtjichuTwode.setVisibility(View.GONE);
        mEdtjichuThreede.setVisibility(View.GONE);
        mEdtjichuAddtm.setVisibility(View.VISIBLE);
        JSONArray temActList = merchant.getJSONArray("temActList");
        mEdtjichuStone.setText(temActList.getJSONObject(0).getString("starttime"));
        mEdtjichuEdone.setText(temActList.getJSONObject(0).getString("endtime"));
        if (temActList.size() >= 2) {
            ll_twiceTimeShow = true;
            mPhaseMarketsetLltimetwo.setVisibility(View.VISIBLE);
            mEdtjichuTwode.setVisibility(View.VISIBLE);
            mEdtjichuSttwo.setText(temActList.getJSONObject(1).getString("starttime"));
            mEdtjichuEdtwo.setText(temActList.getJSONObject(1).getString("endtime"));
        }
        if (temActList.size() == 3) {
            ll_thirdTimeShow = true;
            mPhaseMarketsetLlthreetime.setVisibility(View.VISIBLE);
            mEdtjichuThreede.setVisibility(View.VISIBLE);
            mEdtjichuAddtm.setVisibility(View.GONE);
            mEdtjichuStthree.setText(temActList.getJSONObject(2).getString("starttime"));
            mEdtjichuEdthree.setText(temActList.getJSONObject(2).getString("endtime"));
        }
        mMap.clear();
        RoundedCorners roundedCorners = new RoundedCorners(10);
        RequestOptions override = RequestOptions.bitmapTransform(roundedCorners).error(R.drawable.icon_error).placeholder(R.drawable.icon_error).override(300, 300);
        RequestOptions requestOptions = new RequestOptions().error(R.drawable.icon_error).apply(override).placeholder(R.drawable.icon_error);
        if (!TextUtils.isEmpty(mPicSmallPath)) {
            mEdtjichuHead.setVisibility(View.VISIBLE);
            mImageDelete.setVisibility(View.VISIBLE);
            if (mPicSmallPath.startsWith("http") || mPicSmallPath.startsWith("https")) {
                Glide.with(this).load(mPicSmallPath).apply(requestOptions).into(mEdtjichuHead);
            } else {
                Glide.with(this).load(Constant.IMG_URL + mPicSmallPath).apply(requestOptions).into(mEdtjichuHead);
            }
        } else {
            mEdtjichuHead.setVisibility(View.GONE);
            mImageDelete.setVisibility(View.GONE);
        }

//        LatLng coordinate = new LatLng(click_lat,click_lon);
//        mMap.addMarker(new MarkerOptions().position(coordinate).title(merchant.getString("ShopAddress")).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
//                .decodeResource(getResources(), R.drawable.location))));


    }


    private void initMapView() {
        mMap = mMapView.getMap();
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(false);
        CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(39.091592, 117.195332), 8, 0, 0));
        mMap.moveCamera(mCameraUpdate);
        /*mMap.setOnPOIClickListener(this);*/
        mMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.e(TAG, "onMarkerClick: " + marker.getSnippet() + "   \n" + marker.toString() + "    \n" + marker.getOptions().getTitle());
                /*marker.showInfoWindow();*/
                /*  is_click = true;*/
                is_xuanze = true;
                marker.getOptions().getTitle();
                mEdtjichuDizhi.setText(marker.getSnippet() + marker.getTitle());
                mEdtjichuDizhi.setSelection(marker.getTitle().toString().length());
                click_lat = marker.getPosition().latitude;
                click_lon = marker.getPosition().longitude;
                LatLonPoint latLonPoint = new LatLonPoint(click_lat, click_lon);
                RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);
                geocoderSearch.getFromLocationAsyn(query);
                return true;
            }
        });
        mEdtjichuDizhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_xuanze = false;
            }
        });
        mEdtjichuDizhi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!is_xuanze)
                    mMap.clear();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (TextUtils.isEmpty(s)) {
                    mMap.clear();
                } else if (!is_xuanze) {
                    mQuery = new PoiSearch.Query(s.toString(), "", "天津");
                    mQuery.setPageSize(100);// 设置每页最多返回多少条poiitem
                    poiSearch = new PoiSearch(EdtJichuFragment.this.getActivity(), mQuery);
                    poiSearch.setOnPoiSearchListener(EdtJichuFragment.this);
                    poiSearch.searchPOIAsyn();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        geocoderSearch = new GeocodeSearch(getActivity());
        geocoderSearch.setOnGeocodeSearchListener(this);
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
//        mEdtjichuDizhi.setText(regeocodeResult.getRegeocodeAddress().getFormatAddress());
//        mEdtjichuDizhi.setSelection(regeocodeResult.getRegeocodeAddress().getFormatAddress().length());
//        Log.e(TAG, "onRegeocodeSearched: "+regeocodeResult.toString()+regeocodeResult.getRegeocodeAddress().getFormatAddress());
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @OnClick({R.id.edtjichu_onehangy, R.id.edtjichu_twohangy, R.id.edtjichu_city, R.id.edtjichu_quyu, R.id.edtjichu_shangquan, R.id.edtjichu_stone,
            R.id.edtjichu_edone, R.id.edtjichu_sttwo, R.id.edtjichu_edtwo, R.id.edtjichu_twode, R.id.edtjichu_stthree, R.id.edtjichu_edthree, R.id.edtjichu_threede,
            R.id.edtjichu_addtm, R.id.edtjichu_head, R.id.edtjichu_tianjia, R.id.edtjichu_linear, R.id.yinhang_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edtjichu_onehangy:
                getFirsttierIndustry();
                break;
            case R.id.edtjichu_twohangy:
                String h = mEdtjichuOnehangy.getText().toString();
                if (TextUtils.isEmpty(h)) {
                    ToastShow("请先选择一级行业");
                } else {
                    mDialogBotList_erji.show();
                }
                break;
            case R.id.edtjichu_city:
                getCity();
                break;
            case R.id.edtjichu_quyu:
                String city = mEdtjichuCity.getText().toString();
                if (TextUtils.isEmpty(city)) {
                    ToastShow("请先选择城市");
                } else {
                    mDialogBotList_quyu.show();
                }
                break;
            case R.id.edtjichu_shangquan:
                String cityy = mEdtjichuCity.getText().toString();
                String quyu = mEdtjichuQuyu.getText().toString();
                if (TextUtils.isEmpty(cityy)) {
                    ToastShow("请先选择城市");
                } else if (TextUtils.isEmpty(quyu)) {
                    ToastShow("请先选择区域");
                } else {
                    mDialogBotList_shangquan.show();
                }

                break;
            case R.id.edtjichu_stone:
                showTimeDialog(mEdtjichuStone, "2035-12-12 00:00", "2035-12-12 23:59");
                break;
            case R.id.edtjichu_edone:
                showTimeDialog(mEdtjichuEdone, "2019-03-03 " + mEdtjichuStone.getText().toString(), "2035-12-12 23:59");
                break;
            case R.id.edtjichu_sttwo:
                showTimeDialog(mEdtjichuSttwo, "2019-03-03 " + mEdtjichuEdone.getText().toString(), "2035-12-12 23:59");
                break;
            case R.id.edtjichu_edtwo:
                showTimeDialog(mEdtjichuEdtwo, "2019-03-03 " + mEdtjichuSttwo.getText().toString(), "2035-12-12 23:59");
                break;
            case R.id.edtjichu_twode:
                delet_what = 0;
                mYfsRemindDialog.show();
                break;
            case R.id.edtjichu_stthree:
                if (ll_twiceTimeShow) {
                    showTimeDialog(mEdtjichuStthree, "2019-03-03 " + mEdtjichuEdtwo.getText().toString(), "2035-12-12 23:59");
                } else {
                    showTimeDialog(mEdtjichuStthree, "2019-03-03 " + mEdtjichuOnehangy.getText().toString(), "2035-12-12 23:59");
                }
                break;
            case R.id.edtjichu_edthree:
                showTimeDialog(mEdtjichuEdthree, "2019-03-03 " + mEdtjichuStthree.getText().toString(), "2035-12-12 23:59");
                break;
            case R.id.edtjichu_threede:
                delet_what = 1;
                mYfsRemindDialog.show();
                break;
            case R.id.edtjichu_addtm:
                if (!ll_twiceTimeShow) {
                    ll_twiceTimeShow = true;
                    mPhaseMarketsetLltimetwo.setVisibility(View.VISIBLE);
                    mEdtjichuTwode.setVisibility(View.VISIBLE);
                } else {
                    ll_thirdTimeShow = true;
                    mPhaseMarketsetLlthreetime.setVisibility(View.VISIBLE);
                    mEdtjichuThreede.setVisibility(View.VISIBLE);
                }
                if (ll_twiceTimeShow && ll_thirdTimeShow) {
                    mEdtjichuAddtm.setVisibility(View.GONE);
                }
                break;
            case R.id.edtjichu_head:
            case R.id.edtjichu_tianjia:
            case R.id.edtjichu_linear:
                selectPic();
                break;
            case R.id.yinhang_delete:
                head_url = "";
                mPicSmallPath = "";
                mEdtjichuHead.setVisibility(View.GONE);
                mImageDelete.setVisibility(View.GONE);
                break;
        }
    }

    /*提交数据*/
    public boolean toCommit() {
        if (!toCheck()) {
            return false;
        }
        return true;
    }

    //提交前检测
    private boolean toCheck() {
        if (!checkTime()) {
            return false;
        }
        String name = mEdtjichuMendname.getText().toString();
        String shagnhuname = mEdtjichuShanghuname.getText().toString();
        String onehang = mEdtjichuOnehangy.getText().toString();
        String twohang = mEdtjichuTwohangy.getText().toString();
        String city = mEdtjichuCity.getText().toString();
        String quyu = mEdtjichuQuyu.getText().toString();
        String shangquan = mEdtjichuShangquan.getText().toString();
        String dizhi = mEdtjichuDizhi.getText().toString();
        String yingyephone = mEdtjichuYingyephone.getText().toString();
        String lianxiren = mEdtjichuLianxiren.getText().toString();
        String lianxiphone = mEdtjichuLianxiphone.getText().toString();
        if (TextUtils.isEmpty(name)) {
            new MiddleDialog.Builder(getActivity()).img_id(R.drawable.con_shibai).title(mEdtDetailActivity.is_tijiao ? "提交失败" : "").content("请输入[基础信息-门店名称]").build().show();
            return false;
        }
        if (TextUtils.isEmpty(mPicSmallPath)) {
            new MiddleDialog.Builder(getActivity()).img_id(R.drawable.con_shibai).title(mEdtDetailActivity.is_tijiao ? "提交失败" : "").content("请选择[基础信息-店铺头像]").build().show();
            return false;
        }
        if (TextUtils.isEmpty(onehang)) {
            new MiddleDialog.Builder(getActivity()).img_id(R.drawable.con_shibai).title(mEdtDetailActivity.is_tijiao ? "提交失败" : "").content("请选择[基础信息-一级行业]").build().show();
            return false;
        }
        if (TextUtils.isEmpty(twohang)) {
            new MiddleDialog.Builder(getActivity()).img_id(R.drawable.con_shibai).title(mEdtDetailActivity.is_tijiao ? "提交失败" : "").content("请选择[基础信息-二级行业]").build().show();
            return false;
        }
        if (TextUtils.isEmpty(city)) {
            new MiddleDialog.Builder(getActivity()).img_id(R.drawable.con_shibai).title(mEdtDetailActivity.is_tijiao ? "提交失败" : "").content("请选择[基础信息-城市]").build().show();
            return false;
        }
        if (TextUtils.isEmpty(quyu)) {
            new MiddleDialog.Builder(getActivity()).img_id(R.drawable.con_shibai).title(mEdtDetailActivity.is_tijiao ? "提交失败" : "").content("请选择[基础信息-区域]").build().show();
            return false;
        }
        if (TextUtils.isEmpty(shangquan)) {
            new MiddleDialog.Builder(getActivity()).img_id(R.drawable.con_shibai).title(mEdtDetailActivity.is_tijiao ? "提交失败" : "").content("请选择[基础信息-商圈]").build().show();
            return false;
        }
        if (click_lat == 0 || click_lon == 0) {
            new MiddleDialog.Builder(getActivity()).img_id(R.drawable.con_shibai).title(mEdtDetailActivity.is_tijiao ? "提交失败" : "").content("请选择[基础信息-门店地址]").build().show();
            return false;
        }
        if (TextUtils.isEmpty(dizhi)) {
            new MiddleDialog.Builder(getActivity()).img_id(R.drawable.con_shibai).title(mEdtDetailActivity.is_tijiao ? "提交失败" : "").content("请输入[基础信息-门店地址]").build().show();
            return false;
        }
        if (TextUtils.isEmpty(yingyephone)) {
            new MiddleDialog.Builder(getActivity()).img_id(R.drawable.con_shibai).title(mEdtDetailActivity.is_tijiao ? "提交失败" : "").content("请输入[基础信息-营业电话]").build().show();
            return false;
        }
        if (TextUtils.isEmpty(lianxiren)) {
            new MiddleDialog.Builder(getActivity()).img_id(R.drawable.con_shibai).title(mEdtDetailActivity.is_tijiao ? "提交失败" : "").content("请输入[基础信息-店铺联系人]").build().show();
            return false;
        }
        if (TextUtils.isEmpty(lianxiphone)) {
            new MiddleDialog.Builder(getActivity()).img_id(R.drawable.con_shibai).title(mEdtDetailActivity.is_tijiao ? "提交失败" : "").content("请输入[基础信息-联系人手机号]").build().show();
            return false;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("shopName", name);
        map.put("merchantBrandName", shagnhuname);
        map.put("shopType", one_code);
        map.put("smallType", two_code);
        map.put("businessHours", mList_time);
        map.put("cityCode", city_code);
        map.put("districtCode", quyu_code);
        map.put("businessCircleId", shangquan_code);
        map.put("shopAddress", dizhi);
        map.put("IOSLongitude", click_lon);
        map.put("IOSLatitude", click_lat);
        map.put("AndroidLongitude", click_lon);
        map.put("AndroidLatitude", click_lat);
        map.put("shopTel", yingyephone);
        map.put("shopHead", lianxiren);
        map.put("headTel", lianxiphone);
        map.put("picSmallPath", mPicSmallPath);
        map.put("type", mIs_creat);//0新建1修改
        map.put("shopId", mEdtdetail_id);//修改传参
        map.put("userId", mUser_id);
        map.put("userName", mRealname);

        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).newMerchantResources(EncryPtionUtil.getInstance(getActivity()).toEncryption(map));
        EncryPtionHttp.getInstance(getActivity()).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                Log.d("qqq", result.toJSONString());
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    if (mEdtDetailActivity.is_tijiao) {
                        ShareUtil.getInstance(EdtJichuFragment.this.getActivity()).put("is_creat", "1");
                        mIs_creat = "1";
                        mEdtdetail_id = result.getJSONObject("data").getString("shopId");
//                        toJudge();
                    }
                    ShareUtil.getInstance(getActivity()).put("edtdetail_id", result.getJSONObject("data").getString("shopId"));
                    is_succ = true;
                    mEdtDetailActivity.toScroll();
                } else {
                    is_succ = false;
                    if (!TextUtils.isEmpty(msg)) {
                        ToastShow(msg);
                    }
                }

            }

            @Override
            public void onErrorResult(HttpThrowable httpThrowable) {
                ToastShow("接口访问异常" + httpThrowable.message);
                Log.d("qqq", httpThrowable.message);
                is_succ = false;
            }
        });
        return is_succ;
    }

    /*检测可用时间段是否有重叠*/
    private boolean checkTime() {
        mList_time.clear();
        Map<String, String> map_one = new HashMap<>();
        map_one.put("startTime", mEdtjichuStone.getText().toString());
        map_one.put("endTime", mEdtjichuEdone.getText().toString());
        mList_time.add(map_one);
        if (ll_twiceTimeShow) {
            Map<String, String> map_two = new HashMap<>();
            map_two.put("startTime", mEdtjichuSttwo.getText().toString());
            map_two.put("endTime", mEdtjichuEdtwo.getText().toString());
            mList_time.add(map_two);
        }
        if (ll_thirdTimeShow) {
            Map<String, String> map_three = new HashMap<>();
            map_three.put("startTime", mEdtjichuStthree.getText().toString());
            map_three.put("endTime", mEdtjichuEdthree.getText().toString());
            mList_time.add(map_three);
        }
        if (mList_time.size() == 1 && CheckDateRule.compare_date(map_one.get("startTime"), map_one.get("endTime")) == -1) {
            ToastShow("开始时间不能大于结束时间");
            return false;
        } else if (mList_time.size() != 1 && !CheckDateRule.toCompare(mList_time)) {
            ToastShow("请检查可用时间段,不允许重叠");
            return false;
        } else if (mList_time.size() == 1) {
            int one_compare = CheckDateRule.compare_date(map_one.get("startTime"), map_one.get("endTime"));
            if (one_compare == -1 || one_compare == 0) {
                ToastShow("请检查可用时间段,不允许重叠");
                return false;
            }
        }
        return true;
    }

    public void getJichu() {
        mEdtdetail_id = (String) ShareUtil.getInstance(getActivity()).get("edtdetail_id", "");
        mIs_creat = (String) ShareUtil.getInstance(getActivity()).get("is_creat", "0");
        toGetEdtData();
//         if(!TextUtils.isEmpty(mEdtdetail_id)){
//             mIs_creat="1";
//         }
    }

    /*--------------------------------各种数据进行获取----------------------------*/
    //全部一级行业
    private void getFirsttierIndustry() {
        final String s_yiji = mEdtjichuOnehangy.getText().toString();
        Map<String, Object> map = new HashMap<>();
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).getFirsttierIndustry(EncryPtionUtil.getInstance(getActivity()).toEncryption(map));
        EncryPtionHttp.getInstance(getActivity()).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                Log.d("qqq", result.toJSONString());
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    mBotListBeans_yiji.clear();
                    JSONArray data = result.getJSONArray("data");
                    for (int i = 0; i < data.size(); i++) {
                        JSONObject jsonObject = data.getJSONObject(i);
                        String typeName = jsonObject.getString("TypeName");
                        BotListBean botListBean = new BotListBean(typeName, typeName.equals(s_yiji) ? true : false, BigDecimalUtils.bigUtil(jsonObject.getString("ID")));
                        mBotListBeans_yiji.add(botListBean);
                    }
                    mDialogBotList_yiji = new DialogBotList.Builder().title("请选择").botListBeanMap(mBotListBeans_yiji).builder(EdtJichuFragment.this.getActivity());
                    mDialogBotList_yiji.setOnTextClickListener(new DialogBotList.OnTextClickListener() {
                        @Override
                        public void onTextClickListenerHis(String name, String what) {
                            super.onTextClickListenerHis(name, what);
                            String one_hang = mEdtjichuOnehangy.getText().toString();
                            if (!one_hang.equals(name)) {
                                mEdtjichuOnehangy.setText(name);
                                getSecondaryIndustry(what);
                                mEdtjichuTwohangy.setText("");
                                one_code = what;
                            }
                        }
                    });
                    mDialogBotList_yiji.show();

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

    /*全部二级行业*/
    private void getSecondaryIndustry(String id) {
        final String s_hangye = mEdtjichuTwohangy.getText().toString();
        Map<String, Object> map = new HashMap<>();
        map.put("BigShopTypeID", id);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).getSecondaryIndustry(EncryPtionUtil.getInstance(getActivity()).toEncryption(map));
        EncryPtionHttp.getInstance(getActivity()).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                Log.d("qqq", result.toJSONString());
                final boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    mBotListBeans_erji.clear();
                    JSONArray data = result.getJSONArray("data");
                    for (int i = 0; i < data.size(); i++) {
                        JSONObject jsonObject = data.getJSONObject(i);
                        String typeName = jsonObject.getString("TypeName");
                        BotListBean botListBean = new BotListBean(typeName, typeName.equals(s_hangye) ? true : false, BigDecimalUtils.bigUtil(jsonObject.getString("ID")));
                        mBotListBeans_erji.add(botListBean);
                    }
                    mDialogBotList_erji = new DialogBotList.Builder().title("请选择").botListBeanMap(mBotListBeans_erji).builder(EdtJichuFragment.this.getActivity());
                    mDialogBotList_erji.setOnTextClickListener(new DialogBotList.OnTextClickListener() {
                        @Override
                        public void onTextClickListenerHis(String name, String what) {
                            super.onTextClickListenerHis(name, what);
                            mEdtjichuTwohangy.setText(name);
                            two_code = what;
                        }
                    });
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

    /*获取城市*/
    private void getCity() {
        final String city = mEdtjichuCity.getText().toString();
        Map<String, Object> map = new HashMap<>();
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).getCity(EncryPtionUtil.getInstance(getActivity()).toEncryption(map));
        EncryPtionHttp.getInstance(getActivity()).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                Log.d("qqq", result.toJSONString());
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    mBotListBeans_city.clear();
                    JSONArray data = result.getJSONArray("data");
                    for (int i = 0; i < data.size(); i++) {
                        JSONObject jsonObject = data.getJSONObject(i);
                        String cityName = jsonObject.getString("CityName");
                        BotListBean botListBean = new BotListBean(cityName, cityName.equals(city) ? true : false, jsonObject.getString("CityCode"));
                        mBotListBeans_city.add(botListBean);
                    }
                    mDialogBotList_city = new DialogBotList.Builder().title("请选择").botListBeanMap(mBotListBeans_city).builder(EdtJichuFragment.this.getActivity());
                    mDialogBotList_city.setOnTextClickListener(new DialogBotList.OnTextClickListener() {
                        @Override
                        public void onTextClickListenerHis(String name, String what) {
                            super.onTextClickListenerHis(name, what);
                            String city = mEdtjichuCity.getText().toString();
                            if (!city.equals(name)) {
                                mEdtjichuCity.setText(name);
                                getDistrict(what);
                                mEdtjichuQuyu.setText("");
                                mEdtjichuShangquan.setText("");
                                city_code = what;
                            }
                        }
                    });
                    mDialogBotList_city.show();
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

    /*获取区域*/
    private void getDistrict(final String city_code) {
        final String quyu = mEdtjichuQuyu.getText().toString();
        Map<String, Object> map = new HashMap<>();
        map.put("CityCode", city_code);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).getDistrict(EncryPtionUtil.getInstance(getActivity()).toEncryption(map));
        EncryPtionHttp.getInstance(getActivity()).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                Log.d("qqq", result.toJSONString());
                final boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    mBotListBeans_quyu.clear();
                    JSONArray data = result.getJSONArray("data");
                    for (int i = 0; i < data.size(); i++) {
                        JSONObject jsonObject = data.getJSONObject(i);
                        String districtName = jsonObject.getString("DistrictName");
                        BotListBean botListBean = new BotListBean(districtName, districtName.equals(quyu) ? true : false, jsonObject.getString("DistrictCode"));
                        mBotListBeans_quyu.add(botListBean);
                    }
                    mDialogBotList_quyu = new DialogBotList.Builder().title("请选择").botListBeanMap(mBotListBeans_quyu).builder(EdtJichuFragment.this.getActivity());
                    mDialogBotList_quyu.setOnTextClickListener(new DialogBotList.OnTextClickListener() {
                        @Override
                        public void onTextClickListenerHis(String name, String what) {
                            super.onTextClickListenerHis(name, what);
                            String quyu = mEdtjichuQuyu.getText().toString();
                            if (!quyu.equals(name)) {
                                mEdtjichuQuyu.setText(name);
                                getBusinessCircle(city_code, what);
                                mEdtjichuShangquan.setText("");
                                quyu_code = what;
                            }
                        }
                    });
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

    /*获取商圈*/
    private void getBusinessCircle(String city_code, String di_code) {
        final String shangquan = mEdtjichuShangquan.getText().toString();
        Map<String, Object> map = new HashMap<>();
        map.put("CityCode", city_code);
        map.put("DistrictCode", di_code);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).getBusinessCircle(EncryPtionUtil.getInstance(getActivity()).toEncryption(map));
        EncryPtionHttp.getInstance(getActivity()).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                Log.d("qqq", result.toJSONString());
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    mBotListBeans_shagnquan.clear();
                    JSONArray data = result.getJSONArray("data");
                    for (int i = 0; i < data.size(); i++) {
                        JSONObject jsonObject = data.getJSONObject(i);
                        String businessCircleName = jsonObject.getString("BusinessCircleName");
                        BotListBean botListBean = new BotListBean(businessCircleName, businessCircleName.equals(shangquan) ? true : false, jsonObject.getString("ID"));
                        mBotListBeans_shagnquan.add(botListBean);
                    }
                    mDialogBotList_shangquan = new DialogBotList.Builder().title("请选择").botListBeanMap(mBotListBeans_shagnquan).builder(EdtJichuFragment.this.getActivity());
                    mDialogBotList_shangquan.setOnTextClickListener(new DialogBotList.OnTextClickListener() {
                        @Override
                        public void onTextClickListenerHis(String name, String what) {
                            super.onTextClickListenerHis(name, what);
                            mEdtjichuShangquan.setText(name);
                            shangquan_code = what;
                        }
                    });
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


    /*----------------------------如下各种功能初始化及实现------------------------*/

    private void initDialog() {
        mYfsRemindDialog = new YfsRemindDialog.Builder(getActivity()).title("是否确认删除该时间段？").left("取消").right("确认").build();
        mYfsRemindDialog.setOnTextClickListener(new YfsRemindDialog.OnTextClickListener() {
            @Override
            public void onRightClickListener() {
                super.onRightClickListener();
                switch (delet_what) {
                    case 0:
                        mPhaseMarketsetLltimetwo.setVisibility(View.GONE);
                        mEdtjichuTwode.setVisibility(View.GONE);
                        mEdtjichuAddtm.setVisibility(View.VISIBLE);
                        ll_twiceTimeShow = false;
                        break;
                    case 1:
                        mPhaseMarketsetLlthreetime.setVisibility(View.GONE);
                        mEdtjichuThreede.setVisibility(View.GONE);
                        mEdtjichuAddtm.setVisibility(View.VISIBLE);
                        ll_thirdTimeShow = false;
                        break;
                }

            }
        });
    }


    /**
     * 时间选择
     *
     * @param mtv      需要进行时间设置的TextView
     * @param begin_tm 时间选择的开始时间
     * @param ed_tm    时间选择的结束时间
     */
    private void showTimeDialog(final TextView mtv, String begin_tm, String ed_tm) {
        customTimePickerSt = new CustomDatePicker(getActivity(), new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                mtv.setText(new SimpleDateFormat("HH:mm", Locale.CHINA).format(new Date(timestamp)));
            }
        }, begin_tm + " 00:00", ed_tm);
        customTimePickerSt.setCanShowPreciseTime(true); // 显示时和分
        customTimePickerSt.setMissPreciseDate(true); // 不显示日期
        customTimePickerSt.setScrollLoop(true); // 允许循环滚动
        customTimePickerSt.setCanShowAnim(true);//开启滚动动画
        customTimePickerSt.show(mtv.getText().toString());
    }

    /*头像修改--上传图片*/
    private void toUpHead() {
        File img = new File(head_url);
        String names = img.getName();
        RequestBody requestFile = RequestBody.create(MediaType.parse(guessMimeType(img.getPath())), img);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", names, requestFile);
        Observable<JSONObject> observable = DevRing.httpManager().getService(ApiService.class).uploadShopLogo(body);
        DevRing.httpManager().commonRequest(observable, new CommonObserver<JSONObject>() {
            @Override
            public void onResult(JSONObject result) {
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    mPicSmallPath = result.getJSONObject("data").getString("picSmallPath");
                }
                if (!TextUtils.isEmpty(msg)) {
                    ToastShow(msg);
                }
            }

            @Override
            public void onError(HttpThrowable httpThrowable) {
            }
        }, (LifecycleTransformer) null);

    }


    private void selectPic() {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(this).openGallery(PictureMimeType.ofImage())
                .maxSelectNum(1)// 最大图片选择数量 int
//                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .previewImage(false)// 是否可预览图片 true or false
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .isCamera(true)// 是否显示拍照按钮 true or false
                .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .compressSavePath(getPath())//压缩图片保存地址
                .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .circleDimmedLayer(false)//是否圆形裁剪
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .isDragFrame(false)// 是否可拖动裁剪框(固定)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    head_url = selectList.get(0).getCompressPath();
                    Glide.with(EdtJichuFragment.this).load(head_url).into(mEdtjichuHead);
                    mEdtjichuHead.setVisibility(View.VISIBLE);
                    mImageDelete.setVisibility(View.VISIBLE);
                    toUpHead();
                    break;
            }
        }
    }

    /**
     * 自定义压缩存储地址
     *
     * @return
     */
    private String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/Luban/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
       /* Log.e(TAG, "onPoiSearched: " + poiResult.getPois().size());
        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();//存放所有点的经纬度
        for (int j = 0; j < poiResult.getPois().size(); j++) {
            PoiItem poiItem = poiResult.getPois().get(j);
            LatLonPoint latLonPoint = poiItem.getLatLonPoint();
            LatLng latLng = new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
//            Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
//                    .decodeResource(getResources(), R.drawable.icon_head))));
            Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).title(poiItem.getTitle()).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(getResources(), R.drawable.location))));

            boundsBuilder.include(marker.getPosition());//把所有点都include进去（LatLng类型）
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 15));*/


        List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
        List<SuggestionCity> suggestionCities = poiResult
                .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

        if (poiItems != null && poiItems.size() > 0) {
            mMap.clear();// 清理之前的图标
            PoiOverlay poiOverlay = new PoiOverlay(mMap, poiItems);
            poiOverlay.removeFromMap();
            poiOverlay.addToMap();
            poiOverlay.zoomToSpan();
        }

    }

    @Override
    public void onPOIClick(Poi poi) {
        /*   is_click = true;*/
        mMap.clear();
        LatLng coordinate = poi.getCoordinate();
        mMap.addMarker(new MarkerOptions().position(coordinate).title(poi.getName()).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.drawable.location))));
        mEdtjichuDizhi.setText(poi.getName());
        mEdtjichuDizhi.setSelection(poi.getName().length());
        click_lat = coordinate.latitude;
        click_lon = coordinate.longitude;

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

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

    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = null;
        try {
            contentTypeFor = fileNameMap.getContentTypeFor(URLEncoder.encode(path, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
