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
public class JieSFragment extends BaseFragment {


    @BindView(R.id.item_lookjiesu_leixing)
    TextView mItemLookjiesuLeixing;
    @BindView(R.id.item_lookjiesu_jiesuanstate)
    TextView mItemLookjiesuJiesuanstate;
    @BindView(R.id.item_lookjiesu_shifou)
    TextView mItemLookjiesuShifou;
    @BindView(R.id.item_lookjiesu_jiesuanzhangh)
    TextView mItemLookjiesuJiesuanzhangh;
    @BindView(R.id.item_lookjiesu_kaihuiname)
    TextView mItemLookjiesuKaihuiname;
    @BindView(R.id.item_lookjiesu_phone)
    TextView mItemLookjiesuPhone;
    @BindView(R.id.item_lookjiesu_kaihuihang)
    TextView mItemLookjiesuKaihuihang;
    @BindView(R.id.item_lookjiesu_zhihang)
    TextView mItemLookjiesuZhihang;
    @BindView(R.id.item_lookjiesu_hanghao)
    TextView mItemLookjiesuHanghao;
    @BindView(R.id.item_lookjiesu_imgpos)
    ImageView mItemLookjiesuImgpos;
    @BindView(R.id.item_lookjiesu_kaihuhang)
    ImageView mItemLookjiesuKaihuhang;
    @BindView(R.id.item_lookjiesu_imgchengnuo)
    ImageView mItemLookjiesuImgchengnuo;
    @BindView(R.id.item_lookjiesu_farenshouchiimgv)
    ImageView mItemLookjiesuFarenshouchiimgv;
    @BindView(R.id.item_lookjiesu_lastimgv)
    ImageView mItemLookjiesuLastimgv;
    @BindView(R.id.item_lookjiesu_shenfzone)
    ImageView mItemLookjiesuShenfzone;
    @BindView(R.id.item_lookjiesu_shenfztwo)
    ImageView mItemLookjiesuShenfztwo;
    @BindView(R.id.jichu_look_smart)
    SmartRefreshLayout mJichuLookSmart;

    @BindView(R.id.linear_banka)
    ConstraintLayout mLinearBanka;
    @BindView(R.id.linear_gps)
    ConstraintLayout mLinearGps;
    @BindView(R.id.linear_chengnuo)
    ConstraintLayout mLinearChengnuo;
    @BindView(R.id.linear_chouchi)
    ConstraintLayout mLinearShouchi;
    @BindView(R.id.linear_shenfenzheng)
    ConstraintLayout mLinearShenfenzheng;
    @BindView(R.id.linear_zijin)
    ConstraintLayout mLinearZijin;
    @BindView(R.id.textView68)
    TextView mTvXiugai;
    @BindView(R.id.textView66)
    TextView mTvShoukuanGai;
    @BindView(R.id.item_lookjies_banka)
    TextView mItemLookjiesBanka;
    @BindView(R.id.textView74)
    TextView mtvkaiHuren_danwei;
    @BindView(R.id.textView88)
    View mLineOne;
    @BindView(R.id.linear_shenfenzhengn)
    ConstraintLayout mLinearShenfenzhengn;
    @BindView(R.id.item_lookjies_shenfenzheng)
    TextView mTvSfzNum;

    Unbinder unbinder;
    private String mEdtdetail_id;
    private String channel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jie, container, false);
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
        map.put("type", 4);
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
            String accounttype = merchant.getString("accounttype");
            mItemLookjiesuLeixing.setText(accounttype);
            String banka = BigDecimalUtils.bigUtil(merchant.getString("opencardtype"));
            mItemLookjiesBanka.setText(banka.equals("1") ? "商家自办" : "实时开卡");
            mItemLookjiesuJiesuanstate.setText(merchant.getString("settlementtype"));
            String shifoufaren = BigDecimalUtils.bigUtil(merchant.getString("islegalperson"));
            mItemLookjiesuShifou.setText(shifoufaren.equals("1") ? "是" : "否");
            mItemLookjiesuJiesuanzhangh.setText(merchant.getString("cardnum"));
            mItemLookjiesuKaihuiname.setText(merchant.getString("bankacctname"));
            String bankname = merchant.getString("bankname");
            mItemLookjiesuKaihuihang.setText(bankname);
            mItemLookjiesuPhone.setText(merchant.getString("bankcardphone"));
            mItemLookjiesuZhihang.setText(merchant.getString("branchname"));
            mItemLookjiesuHanghao.setText(merchant.getString("bankCode"));
            mTvSfzNum.setText(merchant.getString("collcardid"));


            RoundedCorners roundedCorners = new RoundedCorners(10);
            RequestOptions override = RequestOptions.bitmapTransform(roundedCorners).error(R.drawable.icon_error).placeholder(R.drawable.icon_error).override(300, 300);
            RequestOptions requestOptions = new RequestOptions().error(R.drawable.icon_error).apply(override).placeholder(R.drawable.icon_error);
            /*————————————————————————————————————GPS——————————————————————————————————————————*/
            String mGPS_path = merchant.getString("gpspicPath");
            if (!TextUtils.isEmpty(mGPS_path)) {
                if (mGPS_path.startsWith("http") || mGPS_path.startsWith("https")) {
                    Glide.with(this).load(mGPS_path).apply(requestOptions).into(mItemLookjiesuImgpos);
                } else {
                    Glide.with(this).load(Constant.IMG_URL + mGPS_path).apply(requestOptions).into(mItemLookjiesuImgpos);
                }
            }
            /*————————————————————————————————————银行卡——————————————————————————————————————————*/
            if (accounttype.equals("对公")) {
                //            is_kaihuPic = true;
                String mKaihuiPath = merchant.getString("bankpermission");
                if (!TextUtils.isEmpty(mKaihuiPath)) {
                    if (mKaihuiPath.startsWith("http") || mKaihuiPath.startsWith("https")) {
                        Glide.with(this).load(mKaihuiPath).apply(requestOptions).into(mItemLookjiesuKaihuhang);
                    } else {
                        Glide.with(this).load(Constant.IMG_URL + mKaihuiPath).apply(requestOptions).into(mItemLookjiesuKaihuhang);
                    }
                }
            } else {
                //            is_kaihuPic = false;
                String mYinhang_path = merchant.getString("bankcardimg");
                if (!TextUtils.isEmpty(mYinhang_path)) {
                    if (mYinhang_path.startsWith("http") || mYinhang_path.startsWith("https")) {
                        Glide.with(this).load(mYinhang_path).apply(requestOptions).into(mItemLookjiesuKaihuhang);
                    } else {
                        Glide.with(this).load(Constant.IMG_URL + mYinhang_path).apply(requestOptions).into(mItemLookjiesuKaihuhang);
                    }
                }
            }

            /*————————————————————————————————————正反面身份证——————————————————————————————————————————*/
            String mZhewngPath = merchant.getString("collcardidfront");
            String mFanPath = merchant.getString("collcardidback");
            if (!TextUtils.isEmpty(mZhewngPath)) {
                if (mZhewngPath.startsWith("http") || mZhewngPath.startsWith("https")) {
                    Glide.with(this).load(mZhewngPath).apply(requestOptions).into(mItemLookjiesuShenfzone);
                } else {
                    Glide.with(this).load(Constant.IMG_URL + mZhewngPath).apply(requestOptions).into(mItemLookjiesuShenfzone);
                }
            }
            if (!TextUtils.isEmpty(mFanPath)) {
                if (mFanPath.startsWith("http") || mFanPath.startsWith("https")) {
                    Glide.with(this).load(mFanPath).apply(requestOptions).into(mItemLookjiesuShenfztwo);
                } else {
                    Glide.with(this).load(Constant.IMG_URL + mFanPath).apply(requestOptions).into(mItemLookjiesuShenfztwo);
                }
            }
            /*————————————————————————————————————承诺书——————————————————————————————————————————*/
            String mChengnuo_path = merchant.getString("letterforpromise");
            if (!TextUtils.isEmpty(mChengnuo_path)) {
                if (mChengnuo_path.startsWith("http") || mChengnuo_path.startsWith("https")) {
                    Glide.with(this).load(mChengnuo_path).apply(requestOptions).into(mItemLookjiesuImgchengnuo);
                } else {
                    Glide.with(this).load(Constant.IMG_URL + mChengnuo_path).apply(requestOptions).into(mItemLookjiesuImgchengnuo);
                }
            }
            /*————————————————————————————————————法人手持——————————————————————————————————————————*/
            String mShouChi_path = merchant.getString("authletterandcardfront");
            if (!TextUtils.isEmpty(mShouChi_path)) {
                if (mShouChi_path.startsWith("http") || mShouChi_path.startsWith("https")) {
                    Glide.with(this).load(mShouChi_path).apply(requestOptions).into(mItemLookjiesuFarenshouchiimgv);
                } else {
                    Glide.with(this).load(Constant.IMG_URL + mShouChi_path).apply(requestOptions).into(mItemLookjiesuFarenshouchiimgv);
                }
            }
            /*————————————————————————————————————商户资金——————————————————————————————————————————*/
            String mShanghu_path = merchant.getString("authletter");
            if (!TextUtils.isEmpty(mShanghu_path)) {
                if (mShanghu_path.startsWith("http") || mShanghu_path.startsWith("https")) {
                    Glide.with(this).load(mShanghu_path).apply(requestOptions).into(mItemLookjiesuLastimgv);
                } else {
                    Glide.with(this).load(Constant.IMG_URL + mShanghu_path).apply(requestOptions).into(mItemLookjiesuLastimgv);
                }
            }
            toChangeUi();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void toChangeUi() {
        mTvXiugai.setText("银行卡正面照");
        mTvShoukuanGai.setText("(银行卡号)");
        mLinearBanka.setVisibility(View.VISIBLE);
        mLinearGps.setVisibility(View.VISIBLE);
        mLinearChengnuo.setVisibility(View.VISIBLE);
        mLinearShenfenzheng.setVisibility(View.VISIBLE);
        mLinearShouchi.setVisibility(View.VISIBLE);
        mLinearZijin.setVisibility(View.VISIBLE);
//        is_kaihuPic = false;

        String leixing = mItemLookjiesuLeixing.getText().toString();
        String baanka = mItemLookjiesBanka.getText().toString();
        String jisuanstate = mItemLookjiesuJiesuanstate.getText().toString();
        String shifoufren = mItemLookjiesuShifou.getText().toString();
        if (leixing.equals("对公")) {
            mLinearBanka.setVisibility(View.GONE);
            mtvkaiHuren_danwei.setText("开户单位名称");
            if (jisuanstate.equals("统一结算账户") && shifoufren.equals("否")) {
                mTvXiugai.setText("开户行许可证照片");
//                is_kaihuPic = true;
                mTvShoukuanGai.setText("(许可证上面的账户)");
            } else if (jisuanstate.equals("统一结算账户") && shifoufren.equals("是")) {
                mLinearShenfenzheng.setVisibility(View.GONE);
                mLinearShouchi.setVisibility(View.GONE);
                mLinearZijin.setVisibility(View.GONE);
                mTvXiugai.setText("开户行许可证照片");
//                is_kaihuPic = true;
                mTvShoukuanGai.setText("(许可证上面的账户)");
            } else if (jisuanstate.equals("非统一结算账户") && shifoufren.equals("否")) {
                mLinearGps.setVisibility(View.GONE);
                mLinearChengnuo.setVisibility(View.GONE);
                mTvXiugai.setText("开户行许可证照片");
//                is_kaihuPic = true;
                mTvShoukuanGai.setText("(许可证上面的账户)");
            } else if (jisuanstate.equals("非统一结算账户") && shifoufren.equals("是")) {
                mLineOne.setVisibility(View.GONE);
                mLinearGps.setVisibility(View.GONE);
                mLinearChengnuo.setVisibility(View.GONE);
                mLinearShenfenzheng.setVisibility(View.GONE);
                mLinearShouchi.setVisibility(View.GONE);
                mLinearZijin.setVisibility(View.GONE);
                mTvXiugai.setText("开户行许可证照片");
//                is_kaihuPic = true;
                mTvShoukuanGai.setText("(许可证上面的账户)");
            }
        } else {//对私
            mtvkaiHuren_danwei.setText("开户人名称");
            if (jisuanstate.equals("统一结算账户") && shifoufren.equals("否")) {

            } else if (jisuanstate.equals("统一结算账户") && shifoufren.equals("是")) {
                mLinearShenfenzheng.setVisibility(View.GONE);
                mLinearShouchi.setVisibility(View.GONE);
                mLinearZijin.setVisibility(View.GONE);
            } else if (jisuanstate.equals("非统一结算账户") && shifoufren.equals("否")) {
                mLinearChengnuo.setVisibility(View.GONE);
                mLinearGps.setVisibility(View.GONE);
            } else if (jisuanstate.equals("非统一结算账户") && shifoufren.equals("是")) {
                mLinearChengnuo.setVisibility(View.GONE);
                mLinearGps.setVisibility(View.GONE);
                mLineOne.setVisibility(View.GONE);
                mLinearShenfenzheng.setVisibility(View.GONE);
                mLinearShouchi.setVisibility(View.GONE);
                mLinearZijin.setVisibility(View.GONE);
            }
        }
        if(shifoufren.equals("否")){
            mLinearShenfenzhengn.setVisibility(View.VISIBLE);
        }else{
            mLinearShenfenzhengn.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
