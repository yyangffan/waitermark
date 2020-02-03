package com.superc.waitmarket.ui.fragment;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
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
public class ZizhiFragment extends BaseFragment {

    @BindView(R.id.item_lookzizhi_bianhao)
    TextView mItemLookzizhiBianhao;
    @BindView(R.id.item_lookzizhi_danwei)
    TextView mItemLookzizhiDanwei;
    @BindView(R.id.item_lookzizhi_faren)
    TextView mItemLookzizhiFaren;
    @BindView(R.id.item_lookzizhi_shenfenzh)
    TextView mItemLookzizhiShenfenzh;
    @BindView(R.id.item_lookzizhi_leixing)
    TextView mItemLookLeixing;
    @BindView(R.id.item_lookzizhi_imgone)
    ImageView mItemLookzizhiImgone;
    @BindView(R.id.item_lookzizhi_imgtwo)
    ImageView mItemLookzizhiImgtwo;
    @BindView(R.id.jichu_look_smart)
    SmartRefreshLayout mJichuLookSmart;
    @BindView(R.id.item_lookjies_imgvyyzz)
    ImageView mImageYingyezhiZ;
    Unbinder unbinder;
    @BindView(R.id.item_lookzizhi_fanwei)
    TextView mItemLookzizhiFanwei;
    @BindView(R.id.item_lookzizhi_changsuo)
    TextView mItemLookzizhiChangsuo;
    @BindView(R.id.item_lookzizhi_zijin)
    TextView mItemLookzizhiZijin;
    @BindView(R.id.item_lookzizhi_yyzztime)
    TextView mItemLookzizhiYyzztime;
    @BindView(R.id.item_lookzizhi_sfztime)
    TextView mItemLookzizhiSfzTm;
    @BindView(R.id.con_xinzeng)
    ConstraintLayout mConXinzeng;


    private String mEdtdetail_id;
    private String channel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zizhi, container, false);
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
        map.put("type", 3);
        map.put("channel", channel);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).merchantDetails(EncryPtionUtil.getInstance(getActivity()).toEncryption(map));
        EncryPtionHttp.getInstance(getActivity()).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
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
            String managetype = BigDecimalUtils.bigUtil(merchant.getString("managetype"));
            if (managetype.equals("0")) {
                mItemLookLeixing.setText("企业");
            } else if (managetype.equals("1")) {
                mItemLookLeixing.setText("个体户");
            } else {
                mItemLookLeixing.setText("个人");
                mConXinzeng.setVisibility(View.GONE);
            }
            mItemLookzizhiBianhao.setText(merchant.getString("creditcode"));
            mItemLookzizhiDanwei.setText(merchant.getString("registercompany"));
            mItemLookzizhiFaren.setText(merchant.getString("name"));
            mItemLookzizhiShenfenzh.setText(merchant.getString("cardid"));
            mItemLookzizhiFanwei.setText(merchant.getString("businessscope"));
            mItemLookzizhiChangsuo.setText(merchant.getString("placeofbusiness"));
            mItemLookzizhiZijin.setText(merchant.getString("registeredcapital"));
            mItemLookzizhiYyzztime.setText(merchant.getString("startdate")+"-"+merchant.getString("enddate"));
            mItemLookzizhiSfzTm.setText(merchant.getString("starttime")+"-"+merchant.getString("endtime"));

            String mZhewngPath = merchant.getString("cardidfrntphoto");//正面
            String mFanPath = merchant.getString("cardidbackphoto");//反面
            String licenseimg = merchant.getString("licenseimg");//营业执照
            RoundedCorners roundedCorners = new RoundedCorners(10);
            RequestOptions override = RequestOptions.bitmapTransform(roundedCorners).error(R.drawable.icon_error).placeholder(R.drawable.icon_error).override(300, 300);
            RequestOptions requestOptions = new RequestOptions().error(R.drawable.icon_error).apply(override).placeholder(R.drawable.icon_error);
            /*——————————————————————————————————————————————身份证——————————————————————————————————————————————*/
            if (!TextUtils.isEmpty(mZhewngPath)) {
                if (mZhewngPath.startsWith("http") || mZhewngPath.startsWith("https")) {
                    Glide.with(this).load(mZhewngPath).apply(requestOptions).into(mItemLookzizhiImgone);
                } else {
                    Glide.with(this).load(Constant.IMG_URL + mZhewngPath).apply(requestOptions).into(mItemLookzizhiImgone);
                }
            }
            if (!TextUtils.isEmpty(mFanPath)) {
                if (mFanPath.startsWith("http") || mFanPath.startsWith("https")) {
                    Glide.with(this).load(mFanPath).apply(requestOptions).into(mItemLookzizhiImgtwo);
                } else {
                    Glide.with(this).load(Constant.IMG_URL + mFanPath).apply(requestOptions).into(mItemLookzizhiImgtwo);
                }
            }
            /*——————————————————————————————————————————————营业执照——————————————————————————————————————————————*/
            if (!TextUtils.isEmpty(licenseimg)) {
                if (licenseimg.startsWith("http") || licenseimg.startsWith("https")) {
                    Glide.with(this).load(licenseimg).apply(requestOptions).into(mImageYingyezhiZ);
                } else {
                    Glide.with(this).load(Constant.IMG_URL + licenseimg).apply(requestOptions).into(mImageYingyezhiZ);
                }
            }
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
