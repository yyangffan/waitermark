package com.superc.waitmarket.ui.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import com.superc.waitmarket.utils.dialog.DialogBotList;
import com.superc.waitmarket.utils.dialog.InputDialog;
import com.superc.yyfflibrary.base.BaseFragment;
import com.superc.yyfflibrary.utils.ShareUtil;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class EdtJieskFragment extends BaseFragment {

    @BindView(R.id.item_lookjies_leixing)
    TextView mItemLookjiesLeixing;
    @BindView(R.id.item_lookjies_banka)
    TextView mItemLookjiesBanka;
    @BindView(R.id.item_lookjies_jiesuanstate)
    TextView mItemLookjiesJiesuanstate;
    @BindView(R.id.item_lookjies_ynfaren)
    TextView mItemLookjiesYnfaren;
    @BindView(R.id.item_lookjies_imgvgps)
    ImageView mItemLookjiesImgvgps;
    @BindView(R.id.item_lookjies_gsptianjia)
    TextView mItemLookjiesGsptianjia;
    @BindView(R.id.item_lookjies_linegps)
    LinearLayout mItemLookjiesLinegps;
    @BindView(R.id.item_lookjies_imgvyinh)
    ImageView mItemLookjiesImgvyinh;
    @BindView(R.id.item_lookjies_yinhangtianjia)
    TextView mItemLookjiesYinhangtianjia;
    @BindView(R.id.item_lookjies_lineayinhang)
    LinearLayout mItemLookjiesLineayinhang;
    @BindView(R.id.item_lookjies_jiesuannum)
    EditText mItemLookjiesJiesuannum;
    @BindView(R.id.item_lookjies_kaihuren)
    EditText mItemLookjiesKaihuren;
    @BindView(R.id.item_lookjies_yuliuphone)
    EditText mItemLookjiesYuliuphone;
    @BindView(R.id.item_lookjies_kaihuhang)
    TextView mItemLookjiesKaihuhang;
    @BindView(R.id.item_lookjies_zhihang)
    EditText mItemLookjiesZhihang;
    @BindView(R.id.item_lookjies_hanghao)
    EditText mItemLookjiesHanghao;
    @BindView(R.id.item_lookjies_imgvchengnuo)
    ImageView mItemLookjiesImgvchengnuo;
    @BindView(R.id.item_lookjies_tianjiachengnuo)
    TextView mItemLookjiesTianjiachengnuo;
    @BindView(R.id.item_lookjies_linarchengnuo)
    LinearLayout mItemLookjiesLinarchengnuo;
    @BindView(R.id.item_edtzizhi_imgone)
    ImageView mItemEdtzizhiImgone;
    @BindView(R.id.item_edtzizhi_imgonexiangji)
    ImageView mItemEdtzizhiImgonexiangji;
    @BindView(R.id.item_edtzizhi_imgtwo)
    ImageView mItemEdtzizhiImgtwo;
    @BindView(R.id.item_edtzizhi_imgtwoxiangji)
    ImageView mItemEdtzizhiImgtwoxiangji;
    @BindView(R.id.item_lookjies_imgvfarenshou)
    ImageView mItemLookjiesImgvfarenshou;
    @BindView(R.id.item_lookjies_tianjiafaren)
    TextView mItemLookjiesTianjiafaren;
    @BindView(R.id.item_lookjies_linearfarenpic)
    LinearLayout mItemLookjiesLinearfarenpic;
    @BindView(R.id.item_lookjies_imgvshanghu)
    ImageView mItemLookjiesImgvshanghu;
    @BindView(R.id.item_lookjies_tianjiashanghu)
    TextView mItemLookjiesTianjiashanghu;
    @BindView(R.id.item_lookjies_tianjin)
    TextView mItemLookTianjin;
    @BindView(R.id.item_lookjies_linearshanghu)
    LinearLayout mItemLookjiesLinearshanghu;
    @BindView(R.id.imageView7)
    ImageView mImageViewSeven;
    @BindView(R.id.imageView8)
    ImageView mImageViewEight;
    @BindView(R.id.jichu_look_smart)
    SmartRefreshLayout mJichuLookSmart;

    @BindView(R.id.textView68)
    TextView mTvXiugai;
    @BindView(R.id.textView98)
    TextView mTvShoukuanGai;
    @BindView(R.id.linear_banka)
    ConstraintLayout mLinearBanka;
    @BindView(R.id.linear_gps)
    ConstraintLayout mLinearGps;
    @BindView(R.id.linear_chengnuo)
    ConstraintLayout mLinearChengnuo;
    @BindView(R.id.linear_shenfenzheng)
    ConstraintLayout mLinearShenfenzheng;
    @BindView(R.id.linear_chouchi)
    ConstraintLayout mLinearShouchi;
    @BindView(R.id.linear_zijin)
    ConstraintLayout mLinearZijin;
    @BindView(R.id.textView76)
    View mLineOne;
    @BindView(R.id.textView102)
    View mLineTwo;

    Unbinder unbinder;
    @BindView(R.id.gpsdelete)
    ImageView mGpsdelete;
    @BindView(R.id.yinhang_delete)
    ImageView mYinhangDelete;
    @BindView(R.id.chengnuodelete)
    ImageView mChengnuodelete;
    @BindView(R.id.shouchidelete)
    ImageView mShouchidelete;
    @BindView(R.id.shanghudelete)
    ImageView mShanghudelete;
    private String mGPS_path, mKaihuiPath, mYinhang_path, mChengnuo_path, mZhewngPath, mFanPath, mShouChi_path, mShanghu_path;
    private String url_gps, url_kaihu, url_yinhang, url_chengnuo, url_fore, url_back, url_shouchi, url_shanghu;
    private EdtDetailActivity mEdtDetailActivity;
    private String[] mStrings_zhanghu = new String[]{"对公", "对私"};
    private String[][] mStrings_banka = new String[][]{{"商家自办", "1"}, {"时时开卡", "3"}};
    private String[] mStrings_jiesuan = new String[]{"统一结算账户", "非统一结算方式"};
    private String[][] mStrings_shiffaren = new String[][]{{"是", "1"}, {"否", "0"}};
    private String[] mStrings_shiftianjin = new String[]{"天津银行", "非天津银行"};
    private InputDialog mInputDialog;
    private DialogBotList mDialogBotList_zhanghu;
    private DialogBotList mDialogBotList_banka;
    private DialogBotList mDialogBotList_jiesuan;
    private DialogBotList mDialogBotList_shiffaren;
    private DialogBotList mDialogBotList_shiftianjin;
    private DialogBotList mDialogBotList_zhihang;
    private List<BotListBean> mBotListBeans_zhanghu;
    private List<BotListBean> mBotListBeans_banka;
    private List<BotListBean> mBotListBeans_jiesuan;
    private List<BotListBean> mBotListBeans_shiffaren;
    private List<BotListBean> mBotListBeans_shiftianjin;
    private List<BotListBean> mBotListBeans_zhihang;
    private String banka, shifoufaren;
    private String mEdtdetail_id;
    private String mIs_creat;
    private String channel;
    private boolean is_Tianjin = false;
    private boolean is_kaihuPic = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edt_jiesk, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void init() {
        mBotListBeans_zhihang = new ArrayList<>();
        mInputDialog = new InputDialog.Builder(EdtJieskFragment.this.getActivity()).title("请输入开户行").left("取消").right("确定").build();
        mJichuLookSmart.setEnableOverScrollDrag(true);
        mJichuLookSmart.setEnablePureScrollMode(true);
        mEdtDetailActivity = (EdtDetailActivity) getActivity();
        mInputDialog.setOnTextClickListener(new InputDialog.OnTextClickListener() {
            @Override
            public void onRightClickListenerContent(String resu) {
                super.onRightClickListenerContent(resu);
                mItemLookjiesKaihuhang.setText(resu);
            }
        });
    }

    @OnClick({R.id.item_lookjies_leixing, R.id.item_lookjies_banka, R.id.item_lookjies_jiesuanstate, R.id.item_lookjies_ynfaren, R.id.item_lookjies_imgvgps, R.id.item_lookjies_gsptianjia,
            R.id.item_lookjies_linegps, R.id.item_lookjies_imgvyinh, R.id.item_lookjies_yinhangtianjia, R.id.item_lookjies_lineayinhang, R.id.item_lookjies_imgvchengnuo, R.id.item_lookjies_tianjiachengnuo,
            R.id.item_lookjies_linarchengnuo, R.id.item_edtzizhi_imgone, R.id.item_edtzizhi_imgonexiangji, R.id.item_edtzizhi_imgtwo, R.id.item_edtzizhi_imgtwoxiangji, R.id.item_lookjies_imgvfarenshou,
            R.id.item_lookjies_tianjiafaren, R.id.item_lookjies_linearfarenpic, R.id.item_lookjies_imgvshanghu, R.id.item_lookjies_tianjiashanghu, R.id.item_lookjies_linearshanghu, R.id.item_lookjies_tianjin,
            R.id.item_lookjies_kaihuhang, R.id.gpsdelete, R.id.yinhang_delete, R.id.chengnuodelete, R.id.shouchidelete, R.id.shanghudelete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_lookjies_leixing:
                mDialogBotList_zhanghu.show();
                break;
            case R.id.item_lookjies_banka:
                mDialogBotList_banka.show();
                break;
            case R.id.item_lookjies_jiesuanstate:
                mDialogBotList_jiesuan.show();
                break;
            case R.id.item_lookjies_ynfaren:
                mDialogBotList_shiffaren.show();
                break;
            case R.id.item_lookjies_imgvgps:
            case R.id.item_lookjies_gsptianjia:
            case R.id.item_lookjies_linegps:
                selectPic(1, 9);
                break;
            case R.id.item_lookjies_imgvyinh:
            case R.id.item_lookjies_yinhangtianjia:
            case R.id.item_lookjies_lineayinhang:
                selectPic(2, 9);
                break;
            case R.id.item_lookjies_imgvchengnuo:
            case R.id.item_lookjies_tianjiachengnuo:
            case R.id.item_lookjies_linarchengnuo:
                selectPic(3, 9);
                break;
            case R.id.item_edtzizhi_imgone:
            case R.id.item_edtzizhi_imgonexiangji:
                selectPic(4, 16);
                break;
            case R.id.item_edtzizhi_imgtwo:
            case R.id.item_edtzizhi_imgtwoxiangji:
                selectPic(5, 16);
                break;
            case R.id.item_lookjies_imgvfarenshou:
            case R.id.item_lookjies_tianjiafaren:
            case R.id.item_lookjies_linearfarenpic:
                selectPic(6, 9);
                break;
            case R.id.item_lookjies_imgvshanghu:
            case R.id.item_lookjies_tianjiashanghu:
            case R.id.item_lookjies_linearshanghu:
                selectPic(7, 9);
                break;
            case R.id.item_lookjies_kaihuhang:
                mDialogBotList_shiftianjin.show();
                break;
            case R.id.item_lookjies_tianjin:
                mDialogBotList_zhihang.show();
                break;
            case R.id.gpsdelete:
                url_gps = "";
                mGPS_path = "";
                mItemLookjiesImgvgps.setVisibility(View.GONE);
                mGpsdelete.setVisibility(View.GONE);
                break;
            case R.id.yinhang_delete:
                url_yinhang = "";
                mYinhang_path = "";
                mItemLookjiesImgvyinh.setVisibility(View.GONE);
                mYinhangDelete.setVisibility(View.GONE);
                break;
            case R.id.chengnuodelete:
                url_chengnuo = "";
                mChengnuo_path = "";
                mItemLookjiesImgvchengnuo.setVisibility(View.GONE);
                mChengnuodelete.setVisibility(View.GONE);
                break;
            case R.id.shouchidelete:
                url_shouchi = "";
                mShouChi_path = "";
                mItemLookjiesImgvfarenshou.setVisibility(View.GONE);
                mShouchidelete.setVisibility(View.GONE);
                break;
            case R.id.shanghudelete:
                url_shanghu = "";
                mShanghu_path = "";
                mItemLookjiesImgvshanghu.setVisibility(View.GONE);
                mShanghudelete.setVisibility(View.GONE);
                break;
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
        mLineOne.setVisibility(View.VISIBLE);
        mLineTwo.setVisibility(View.VISIBLE);
        is_kaihuPic = false;

        String leixing = mItemLookjiesLeixing.getText().toString();
        String baanka = mItemLookjiesBanka.getText().toString();
        String jisuanstate = mItemLookjiesJiesuanstate.getText().toString();
        String shifoufren = mItemLookjiesYnfaren.getText().toString();
        if (leixing.equals("对公")) {
            mLinearBanka.setVisibility(View.GONE);
            if (jisuanstate.equals("统一结算账户") && shifoufren.equals("否")) {
                mTvXiugai.setText("开户行许可证照片");
                is_kaihuPic = true;
                mTvShoukuanGai.setText("(许可证上面的账户)");
            } else if (jisuanstate.equals("统一结算账户") && shifoufren.equals("是")) {
                mLinearShenfenzheng.setVisibility(View.GONE);
                mLinearShouchi.setVisibility(View.GONE);
                mLinearZijin.setVisibility(View.GONE);
                mLineTwo.setVisibility(View.INVISIBLE);
                mTvXiugai.setText("开户行许可证照片");
                is_kaihuPic = true;
                mTvShoukuanGai.setText("(许可证上面的账户)");
            } else if (jisuanstate.equals("非统一结算方式") && shifoufren.equals("否")) {
                mLinearGps.setVisibility(View.GONE);
                mLinearChengnuo.setVisibility(View.GONE);
                mTvXiugai.setText("开户行许可证照片");
                is_kaihuPic = true;
                mTvShoukuanGai.setText("(许可证上面的账户)");
            } else if (jisuanstate.equals("非统一结算方式") && shifoufren.equals("是")) {
                mLineOne.setVisibility(View.GONE);
                mLinearGps.setVisibility(View.GONE);
                mLinearChengnuo.setVisibility(View.GONE);
                mLinearShenfenzheng.setVisibility(View.GONE);
                mLinearShouchi.setVisibility(View.GONE);
                mLinearZijin.setVisibility(View.GONE);
                mTvXiugai.setText("开户行许可证照片");
                is_kaihuPic = true;
                mTvShoukuanGai.setText("(许可证上面的账户)");
            }
        } else {//对私
            if (jisuanstate.equals("统一结算账户") && shifoufren.equals("否")) {

            } else if (jisuanstate.equals("统一结算账户") && shifoufren.equals("是")) {
                mLineTwo.setVisibility(View.INVISIBLE);
                mLinearShenfenzheng.setVisibility(View.GONE);
                mLinearShouchi.setVisibility(View.GONE);
                mLinearZijin.setVisibility(View.GONE);
            } else if (jisuanstate.equals("非统一结算方式") && shifoufren.equals("否")) {
                mLinearChengnuo.setVisibility(View.GONE);
                mLinearGps.setVisibility(View.GONE);
            } else if (jisuanstate.equals("非统一结算方式") && shifoufren.equals("是")) {
                mLinearChengnuo.setVisibility(View.GONE);
                mLinearGps.setVisibility(View.GONE);
                mLineOne.setVisibility(View.GONE);
                mLinearShenfenzheng.setVisibility(View.GONE);
                mLinearShouchi.setVisibility(View.GONE);
                mLinearZijin.setVisibility(View.GONE);
            }
        }
    }

    /* mIs_creat  0新建1修改*/
    public void toJudge() {
        mEdtdetail_id = (String) ShareUtil.getInstance(WaitApplication.getInstance()).get("edtdetail_id", "");
        channel = (String) ShareUtil.getInstance(WaitApplication.getInstance()).get("channel", "");
        toGetEdtData();
        getZhi();
    }

    /**
     * type//1基础信息2场景照片3资质信息4结算信息5老客6新客
     * channel 0待激活入口,已激活入口1待审核入口 3维护列表
     */
    private void toGetEdtData() {
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
                    JSONObject j = result.getJSONObject("data").getJSONObject("merchantDetails");
                    if (j == null) {
                        mIs_creat = "0";
                        mItemLookjiesLeixing.setText("对私");
                        mItemLookjiesBanka.setText("商家自办");
                        mItemLookjiesJiesuanstate.setText("非统一结算账户");
                        mItemLookjiesYnfaren.setText("是");
                        banka = "1";
                        shifoufaren = "1";
                        initDialog();
                    } else {
                        mIs_creat = "1";
                        setData(result.getJSONObject("data").getJSONObject("merchantDetails"));
                    }
                } else {
                    initDialog();
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
        String accounttype = merchant.getString("accounttype");
        mItemLookjiesLeixing.setText(accounttype);
        banka = BigDecimalUtils.bigUtil(merchant.getString("opencardtype"));
        mItemLookjiesBanka.setText(banka.equals("1") ? "商家自办" : "时时开卡");
        mItemLookjiesJiesuanstate.setText(merchant.getString("settlementtype"));
        shifoufaren = BigDecimalUtils.bigUtil(merchant.getString("islegalperson"));
        mItemLookjiesYnfaren.setText(shifoufaren.equals("1") ? "是" : "否");
        mItemLookjiesJiesuannum.setText(merchant.getString("cardnum"));

        mItemLookjiesKaihuren.setText(merchant.getString("bankacctname"));
        String bankname = merchant.getString("bankname");
        if (bankname.equals("天津银行")) {
            is_Tianjin=true;
            mItemLookTianjin.setVisibility(View.VISIBLE);
            mItemLookjiesZhihang.setVisibility(View.INVISIBLE);
        } else {
            is_Tianjin=false;
            mItemLookTianjin.setVisibility(View.INVISIBLE);
            mItemLookjiesZhihang.setVisibility(View.VISIBLE);
        }
        mItemLookjiesYuliuphone.setText(merchant.getString("bankcardphone"));
        mItemLookjiesKaihuhang.setText(merchant.getString("bankname"));
        mItemLookjiesZhihang.setText(merchant.getString("branchname"));
        mItemLookTianjin.setText(merchant.getString("branchname"));
        mItemLookjiesHanghao.setText(merchant.getString("bankCode"));


        RoundedCorners roundedCorners = new RoundedCorners(10);
        RequestOptions override = RequestOptions.bitmapTransform(roundedCorners).error(R.drawable.icon_error).placeholder(R.drawable.icon_error).override(300, 300);
        RequestOptions requestOptions = new RequestOptions().error(R.drawable.icon_error).apply(override).placeholder(R.drawable.icon_error);
        /*————————————————————————————————————GPS——————————————————————————————————————————*/
        mGPS_path = merchant.getString("gpspicPath");
        if (!TextUtils.isEmpty(mGPS_path)) {
            mItemLookjiesImgvgps.setVisibility(View.VISIBLE);
            mGpsdelete.setVisibility(View.VISIBLE);
            if (mGPS_path.startsWith("http") || mGPS_path.startsWith("https")) {
                Glide.with(this).load(mGPS_path).apply(requestOptions).into(mItemLookjiesImgvgps);
            } else {
                Glide.with(this).load(Constant.IMG_URL + mGPS_path).apply(requestOptions).into(mItemLookjiesImgvgps);
            }
        } else {
            mItemLookjiesImgvgps.setVisibility(View.GONE);
            mGpsdelete.setVisibility(View.GONE);
        }
        /*————————————————————————————————————银行卡——————————————————————————————————————————*/
        if (accounttype.equals("对公")) {
            is_kaihuPic = true;
            mKaihuiPath = merchant.getString("bankpermission");
            if (!TextUtils.isEmpty(mKaihuiPath)) {
                mItemLookjiesImgvyinh.setVisibility(View.VISIBLE);
                mYinhangDelete.setVisibility(View.VISIBLE);
                if (mKaihuiPath.startsWith("http") || mKaihuiPath.startsWith("https")) {
                    Glide.with(this).load(mKaihuiPath).apply(requestOptions).into(mItemLookjiesImgvyinh);
                } else {
                    Glide.with(this).load(Constant.IMG_URL + mKaihuiPath).apply(requestOptions).into(mItemLookjiesImgvyinh);
                }
            } else {
                mItemLookjiesImgvyinh.setVisibility(View.GONE);
                mYinhangDelete.setVisibility(View.GONE);
            }
        } else {
            is_kaihuPic = false;
            mYinhang_path = merchant.getString("bankcardimg");
            if (!TextUtils.isEmpty(mYinhang_path)) {
                mItemLookjiesImgvyinh.setVisibility(View.VISIBLE);
                mYinhangDelete.setVisibility(View.VISIBLE);
                if (mYinhang_path.startsWith("http") || mYinhang_path.startsWith("https")) {
                    Glide.with(this).load(mYinhang_path).apply(requestOptions).into(mItemLookjiesImgvyinh);
                } else {
                    Glide.with(this).load(Constant.IMG_URL + mYinhang_path).apply(requestOptions).into(mItemLookjiesImgvyinh);
                }
            } else {
                mItemLookjiesImgvyinh.setVisibility(View.GONE);
                mYinhangDelete.setVisibility(View.GONE);
            }
        }

        /*————————————————————————————————————正反面身份证——————————————————————————————————————————*/
        mZhewngPath = merchant.getString("collcardidfront");
        mFanPath = merchant.getString("collcardidback");
        if (!TextUtils.isEmpty(mZhewngPath)) {
            mItemEdtzizhiImgone.setVisibility(View.VISIBLE);
            mItemEdtzizhiImgonexiangji.setVisibility(View.GONE);
            mImageViewSeven.setVisibility(View.VISIBLE);
            if (mZhewngPath.startsWith("http") || mZhewngPath.startsWith("https")) {
                Glide.with(this).load(mZhewngPath).apply(requestOptions).into(mItemEdtzizhiImgone);
            } else {
                Glide.with(this).load(Constant.IMG_URL + mZhewngPath).apply(requestOptions).into(mItemEdtzizhiImgone);
            }
        } else {
            Glide.with(this).load(R.drawable.t_shenfenzhengzhengmian).into(mItemEdtzizhiImgone);
            mItemEdtzizhiImgone.setVisibility(View.VISIBLE);
            mItemEdtzizhiImgonexiangji.setVisibility(View.VISIBLE);
            mImageViewSeven.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(mFanPath)) {
            mItemEdtzizhiImgtwo.setVisibility(View.VISIBLE);
            mItemEdtzizhiImgtwoxiangji.setVisibility(View.GONE);
            mImageViewEight.setVisibility(View.VISIBLE);
            if (mFanPath.startsWith("http") || mFanPath.startsWith("https")) {
                Glide.with(this).load(mFanPath).apply(requestOptions).into(mItemEdtzizhiImgtwo);
            } else {
                Glide.with(this).load(Constant.IMG_URL + mFanPath).apply(requestOptions).into(mItemEdtzizhiImgtwo);
            }
        } else {
            Glide.with(this).load(R.drawable.t_shenfenzhengfanmian).into(mItemEdtzizhiImgtwo);
            mItemEdtzizhiImgtwo.setVisibility(View.VISIBLE);
            mItemEdtzizhiImgtwoxiangji.setVisibility(View.VISIBLE);
            mImageViewEight.setVisibility(View.GONE);
        }

        /*————————————————————————————————————承诺书——————————————————————————————————————————*/
        mChengnuo_path = merchant.getString("letterforpromise");
        if (!TextUtils.isEmpty(mChengnuo_path)) {
            mItemLookjiesImgvchengnuo.setVisibility(View.VISIBLE);
            mChengnuodelete.setVisibility(View.VISIBLE);
            if (mChengnuo_path.startsWith("http") || mChengnuo_path.startsWith("https")) {
                Glide.with(this).load(mChengnuo_path).apply(requestOptions).into(mItemLookjiesImgvchengnuo);
            } else {
                Glide.with(this).load(Constant.IMG_URL + mChengnuo_path).apply(requestOptions).into(mItemLookjiesImgvchengnuo);
            }
        } else {
            mItemLookjiesImgvchengnuo.setVisibility(View.GONE);
            mChengnuodelete.setVisibility(View.GONE);
        }
        /*————————————————————————————————————法人手持——————————————————————————————————————————*/
        mShouChi_path = merchant.getString("authletterandcardfront");
        if (!TextUtils.isEmpty(mShouChi_path)) {
            mItemLookjiesImgvfarenshou.setVisibility(View.VISIBLE);
            mShouchidelete.setVisibility(View.VISIBLE);
            if (mShouChi_path.startsWith("http") || mShouChi_path.startsWith("https")) {
                Glide.with(this).load(mShouChi_path).apply(requestOptions).into(mItemLookjiesImgvfarenshou);
            } else {
                Glide.with(this).load(Constant.IMG_URL + mShouChi_path).apply(requestOptions).into(mItemLookjiesImgvfarenshou);
            }
        } else {
            mItemLookjiesImgvfarenshou.setVisibility(View.GONE);
            mShouchidelete.setVisibility(View.GONE);
        }
        /*————————————————————————————————————商户资金——————————————————————————————————————————*/
        mShanghu_path = merchant.getString("authletter");
        if (!TextUtils.isEmpty(mShanghu_path)) {
            mItemLookjiesImgvshanghu.setVisibility(View.VISIBLE);
            mShanghudelete.setVisibility(View.VISIBLE);
            if (mShanghu_path.startsWith("http") || mShanghu_path.startsWith("https")) {
                Glide.with(this).load(mShanghu_path).apply(requestOptions).into(mItemLookjiesImgvshanghu);
            } else {
                Glide.with(this).load(Constant.IMG_URL + mShanghu_path).apply(requestOptions).into(mItemLookjiesImgvshanghu);
            }
        } else {
            mItemLookjiesImgvshanghu.setVisibility(View.GONE);
            mShanghudelete.setVisibility(View.GONE);
        }
        toChangeUi();

        initDialog();

    }

    public void toCommit() {
        String zhanghu = mItemLookjiesLeixing.getText().toString();
        String jiesuanState = mItemLookjiesJiesuanstate.getText().toString();
        String jiesuan = mItemLookjiesJiesuannum.getText().toString();
        String kaihuName = mItemLookjiesKaihuren.getText().toString();
        String phone = mItemLookjiesYuliuphone.getText().toString();
        String kaihuhang = mItemLookjiesKaihuhang.getText().toString();
        String zhihang = null;
        if (is_Tianjin) {
            zhihang = mItemLookTianjin.getText().toString();
        } else {
            zhihang = mItemLookjiesZhihang.getText().toString();
        }
        String hanghao = mItemLookjiesHanghao.getText().toString();
       /* if (TextUtils.isEmpty(zhanghu)) {
            ToastShow("请选择账户类型");
            return;
        }
        if (TextUtils.isEmpty(banka)) {
            ToastShow("请选择办卡方式");
            return;
        }
        if (TextUtils.isEmpty(jiesuanState)) {
            ToastShow("请选择结算方式");
            return;
        }
        if (TextUtils.isEmpty(shifoufaren)) {
            ToastShow("请选择是否是法人收款");
            return;
        }
        if (TextUtils.isEmpty(mGPS_path)) {
            ToastShow("请选择GPS定位截图");
            return;
        }
        if (TextUtils.isEmpty(mYinhang_path)) {
            ToastShow("请选择银行卡正面照");
            return;
        }
        if (TextUtils.isEmpty(jiesuan)) {
            ToastShow("请输入营业执照编号");
            return;
        }
        if (TextUtils.isEmpty(kaihuName)) {
            ToastShow("请输入开户人名称");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            ToastShow("请输入预留手机号");
            return;
        }
        if (TextUtils.isEmpty(kaihuhang)) {
            ToastShow("请输入开户行");
            return;
        }
        if (TextUtils.isEmpty(zhihang)) {
            ToastShow("请输入支行");
            return;
        }
        if (TextUtils.isEmpty(hanghao)) {
            ToastShow("请输入行号");
            return;
        }
        if (TextUtils.isEmpty(mChengnuo_path)) {
            ToastShow("请选择承诺书");
            return;
        }
        if (TextUtils.isEmpty(mZhewngPath)) {
            ToastShow("请选择身份证正面");
            return;
        }
        if (TextUtils.isEmpty(mFanPath)) {
            ToastShow("请选择身份证反面");
            return;
        }
        if (TextUtils.isEmpty(mShouChi_path)) {
            ToastShow("请选择法人手持商户资金清算授权书与法人身份证正面照片");
            return;
        }
        if (TextUtils.isEmpty(mShanghu_path)) {
            ToastShow("请选择商户资金清算授权书");
            return;
        }*/
        Map<String, Object> map = new HashMap<>();
        map.put("shopId", mEdtdetail_id);
        map.put("accountType", zhanghu);
        map.put("openCardType", banka);
        map.put("settleMentType", jiesuanState);
        map.put("isLegalPerson", shifoufaren);
        map.put("GPSpicSmallPath", mGPS_path);
        map.put("cardFrontPicSmallPath", mYinhang_path);
        map.put("bankpermission", mKaihuiPath);
        map.put("cardnum", jiesuan);
        map.put("bankAcctName", kaihuName);
        map.put("bankCardPhone", phone);
        map.put("bankName", kaihuhang);
        map.put("branchName", zhihang);
        map.put("bankCode", hanghao);
        map.put("promisePicSmallPath", mChengnuo_path);
        map.put("collCardIdFront", mZhewngPath);
        map.put("collCardIdBack", mFanPath);
        map.put("authLetterAndCardFront", mShouChi_path);
        map.put("authLetter", mShanghu_path);
        map.put("type", mIs_creat);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).newSettlementInformation(EncryPtionUtil.getInstance(getActivity()).toEncryption(map));
        EncryPtionHttp.getInstance(getActivity()).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                Log.d("qqq", result.toJSONString());
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    mEdtDetailActivity.toScroll();
                    ShareUtil.getInstance(getActivity()).put("can_commit", true);
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

    /**
     * @param reque_code
     * @param shenfen    身份证传16 其它传9
     */
    private void selectPic(int reque_code, int shenfen) {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(this).openGallery(PictureMimeType.ofImage())
                .maxSelectNum(10)// 最大图片选择数量 int
//                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .previewImage(false)// 是否可预览图片 true or false
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .isCamera(true)// 是否显示拍照按钮 true or false
                .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .compressSavePath(getPath())//压缩图片保存地址
                .withAspectRatio(shenfen, 9)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .circleDimmedLayer(false)//是否圆形裁剪
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .isDragFrame(false)// 是否可拖动裁剪框(固定)
//                .selectionMedia(selectList)
                .forResult(reque_code);//结果回调onActivityResult code
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            RoundedCorners roundedCorners = new RoundedCorners(10);
            RequestOptions override = RequestOptions.bitmapTransform(roundedCorners).error(R.drawable.icon_error).placeholder(R.drawable.icon_error).override(300, 300);
            switch (requestCode) {
                case 1:
                    url_gps = selectList.get(0).getCompressPath();
                    Glide.with(EdtJieskFragment.this).load(url_gps).apply(override).into(mItemLookjiesImgvgps);
                    mItemLookjiesImgvgps.setVisibility(View.VISIBLE);
                    mGpsdelete.setVisibility(View.VISIBLE);
                    toCommitGPs();
                    break;
                case 2:
                    if (is_kaihuPic) {
                        url_yinhang = "";
                        mYinhang_path = "";
                        url_kaihu = selectList.get(0).getCompressPath();
                        Glide.with(EdtJieskFragment.this).load(url_kaihu).apply(override).into(mItemLookjiesImgvyinh);
                        mItemLookjiesImgvyinh.setVisibility(View.VISIBLE);
                        mYinhangDelete.setVisibility(View.VISIBLE);
                        toCommitKaihuPic();
                    } else {
                        url_kaihu = "";
                        mKaihuiPath = "";
                        url_yinhang = selectList.get(0).getCompressPath();
                        Glide.with(EdtJieskFragment.this).load(url_yinhang).apply(override).into(mItemLookjiesImgvyinh);
                        mItemLookjiesImgvyinh.setVisibility(View.VISIBLE);
                        mYinhangDelete.setVisibility(View.VISIBLE);
                        toCommitYinhang();
                    }
                    break;
                case 3:
                    url_chengnuo = selectList.get(0).getCompressPath();
                    Glide.with(EdtJieskFragment.this).load(url_chengnuo).apply(override).into(mItemLookjiesImgvchengnuo);
                    mItemLookjiesImgvchengnuo.setVisibility(View.VISIBLE);
                    mChengnuodelete.setVisibility(View.VISIBLE);
                    toCommitChengnuop();
                    break;
                case 4:
                    url_fore = selectList.get(0).getCompressPath();
                    Glide.with(EdtJieskFragment.this).load(url_fore).apply(override).into(mItemEdtzizhiImgone);
                    mItemEdtzizhiImgone.setVisibility(View.VISIBLE);
                    mItemEdtzizhiImgonexiangji.setVisibility(View.GONE);
                    mImageViewSeven.setVisibility(View.VISIBLE);
                    toCommitPic(1);
                    break;
                case 5:
                    url_back = selectList.get(0).getCompressPath();
                    Glide.with(EdtJieskFragment.this).load(url_back).apply(override).into(mItemEdtzizhiImgtwo);
                    mItemEdtzizhiImgtwo.setVisibility(View.VISIBLE);
                    mItemEdtzizhiImgtwoxiangji.setVisibility(View.GONE);
                    mImageViewEight.setVisibility(View.VISIBLE);
                    toCommitPic(2);
                    break;
                case 6:
                    url_shouchi = selectList.get(0).getCompressPath();
                    Glide.with(EdtJieskFragment.this).load(url_shouchi).apply(override).into(mItemLookjiesImgvfarenshou);
                    mItemLookjiesImgvfarenshou.setVisibility(View.VISIBLE);
                    mShouchidelete.setVisibility(View.VISIBLE);
                    toCommitPic(3);
                    break;
                case 7:
                    url_shanghu = selectList.get(0).getCompressPath();
                    Glide.with(EdtJieskFragment.this).load(url_shanghu).apply(override).into(mItemLookjiesImgvshanghu);
                    mItemLookjiesImgvshanghu.setVisibility(View.VISIBLE);
                    mShanghudelete.setVisibility(View.VISIBLE);
                    toCommitPic(4);
                    break;
            }
        }
    }

    /*GPS图片上传*/
    private void toCommitGPs() {
        File img = new File(url_gps);
        String names = img.getName();
        RequestBody requestFile = RequestBody.create(MediaType.parse(guessMimeType(img.getPath())), img);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", names, requestFile);
        Observable<JSONObject> observable = DevRing.httpManager().getService(ApiService.class).uploadGPS(body);
        DevRing.httpManager().commonRequest(observable, new CommonObserver<JSONObject>() {
            @Override
            public void onResult(JSONObject result) {
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    mGPS_path = result.getJSONObject("data").getString("GPSpicSmallPath");
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

    /*银行卡正面图片*/
    private void toCommitYinhang() {
        File img = new File(url_yinhang);
        String names = img.getName();
        RequestBody requestFile = RequestBody.create(MediaType.parse(guessMimeType(img.getPath())), img);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", names, requestFile);
        Observable<JSONObject> observable = DevRing.httpManager().getService(ApiService.class).uploadBankCardFront(body);
        DevRing.httpManager().commonRequest(observable, new CommonObserver<JSONObject>() {
            @Override
            public void onResult(JSONObject result) {
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    mYinhang_path = result.getJSONObject("data").getString("cardFrontPicSmallPath");
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

    private void toCommitKaihuPic() {
        File img = new File(url_kaihu);
        String names = img.getName();
        RequestBody requestFile = RequestBody.create(MediaType.parse(guessMimeType(img.getPath())), img);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", names, requestFile);
        Observable<JSONObject> observable = DevRing.httpManager().getService(ApiService.class).uploadBankPermission(body);
        DevRing.httpManager().commonRequest(observable, new CommonObserver<JSONObject>() {
            @Override
            public void onResult(JSONObject result) {
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    mKaihuiPath = result.getJSONObject("data").getString("picSmallPath");
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

    /*承诺书图片*/
    private void toCommitChengnuop() {
        File img = new File(url_chengnuo);
        String names = img.getName();
        RequestBody requestFile = RequestBody.create(MediaType.parse(guessMimeType(img.getPath())), img);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", names, requestFile);
        Observable<JSONObject> observable = DevRing.httpManager().getService(ApiService.class).uploadLetterForPromise(body);
        DevRing.httpManager().commonRequest(observable, new CommonObserver<JSONObject>() {
            @Override
            public void onResult(JSONObject result) {
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    mChengnuo_path = result.getJSONObject("data").getString("promisePicSmallPath");
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

    private void toCommitPic(final int what) {
        File img = null;
        switch (what) {
            case 1:
                img = new File(url_fore);
                break;
            case 2:
                img = new File(url_back);
                break;
            case 3:
                img = new File(url_shouchi);
                break;
            case 4:
                img = new File(url_shanghu);
                break;
        }
        String names = img.getName();
        RequestBody requestFile = RequestBody.create(MediaType.parse(guessMimeType(img.getPath())), img);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", names, requestFile);
        Observable<JSONObject> observable = DevRing.httpManager().getService(ApiService.class).uploadSettlementLegalPersonCard(body);
        DevRing.httpManager().commonRequest(observable, new CommonObserver<JSONObject>() {
            @Override
            public void onResult(JSONObject result) {
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    switch (what) {
                        case 1:
                            mZhewngPath = result.getJSONObject("data").getString("picSmallPath");
                            break;
                        case 2:
                            mFanPath = result.getJSONObject("data").getString("picSmallPath");
                            break;
                        case 3:
                            mShouChi_path = result.getJSONObject("data").getString("picSmallPath");
                            break;
                        case 4:
                            mShanghu_path = result.getJSONObject("data").getString("picSmallPath");
                            break;
                    }
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

    private void initDialog() {
        String leixing = mItemLookjiesLeixing.getText().toString();
        String baanka = mItemLookjiesBanka.getText().toString();
        String jisuanstate = mItemLookjiesJiesuanstate.getText().toString();
        String shifoufren = mItemLookjiesYnfaren.getText().toString();
        String kaihuhang = mItemLookjiesKaihuhang.getText().toString();

        mBotListBeans_zhanghu = new ArrayList<>();
        mBotListBeans_banka = new ArrayList<>();
        mBotListBeans_jiesuan = new ArrayList<>();
        mBotListBeans_shiffaren = new ArrayList<>();
        mBotListBeans_shiftianjin = new ArrayList<>();
        for (int i = 0; i < mStrings_zhanghu.length; i++) {
            BotListBean botListBean = new BotListBean(mStrings_zhanghu[i], TextUtils.isEmpty(leixing) ? false : (mStrings_zhanghu[i].equals(leixing) ? true : false));
            mBotListBeans_zhanghu.add(botListBean);
        }
        for (int i = 0; i < mStrings_banka.length; i++) {
            BotListBean botListBean = new BotListBean(mStrings_banka[i][0], TextUtils.isEmpty(baanka) ? false : (mStrings_banka[i][0].equals(baanka) ? true : false), mStrings_banka[i][1]);
            mBotListBeans_banka.add(botListBean);
        }
        for (int i = 0; i < mStrings_jiesuan.length; i++) {
            BotListBean botListBean = new BotListBean(mStrings_jiesuan[i], TextUtils.isEmpty(jisuanstate) ? false : (mStrings_jiesuan[i].equals(jisuanstate) ? true : false));
            mBotListBeans_jiesuan.add(botListBean);
        }
        for (int i = 0; i < mStrings_shiffaren.length; i++) {
            BotListBean botListBean = new BotListBean(mStrings_shiffaren[i][0], TextUtils.isEmpty(shifoufren) ? false : (mStrings_shiffaren[i][0].equals(shifoufren) ? true : false), mStrings_shiffaren[i][1]);
            mBotListBeans_shiffaren.add(botListBean);
        }
        for (int i = 0; i < mStrings_shiftianjin.length; i++) {
            BotListBean botListBean = new BotListBean(mStrings_shiftianjin[i], TextUtils.isEmpty(kaihuhang) ? false : (mStrings_jiesuan[i].equals(kaihuhang) ? true : false));
            mBotListBeans_shiftianjin.add(botListBean);
        }

        mDialogBotList_zhanghu = new DialogBotList.Builder().title("请选择").botListBeanMap(mBotListBeans_zhanghu).builder(EdtJieskFragment.this.getActivity());
        mDialogBotList_banka = new DialogBotList.Builder().title("请选择").botListBeanMap(mBotListBeans_banka).builder(EdtJieskFragment.this.getActivity());
        mDialogBotList_jiesuan = new DialogBotList.Builder().title("请选择").botListBeanMap(mBotListBeans_jiesuan).builder(EdtJieskFragment.this.getActivity());
        mDialogBotList_shiffaren = new DialogBotList.Builder().title("请选择").botListBeanMap(mBotListBeans_shiffaren).builder(EdtJieskFragment.this.getActivity());
        mDialogBotList_shiftianjin = new DialogBotList.Builder().title("请选择").botListBeanMap(mBotListBeans_shiftianjin).builder(EdtJieskFragment.this.getActivity());
        mDialogBotList_zhanghu.setOnTextClickListener(new DialogBotList.OnTextClickListener() {
            @Override
            public void onTextClickListenerHis(String name, String what) {
                super.onTextClickListenerHis(name, what);
                mItemLookjiesLeixing.setText(name);
                toChangeUi();
            }
        });
        mDialogBotList_banka.setOnTextClickListener(new DialogBotList.OnTextClickListener() {
            @Override
            public void onTextClickListenerHis(String name, String what) {
                super.onTextClickListenerHis(name, what);
                banka = what;
                mItemLookjiesBanka.setText(name);
                toChangeUi();
            }
        });
        mDialogBotList_jiesuan.setOnTextClickListener(new DialogBotList.OnTextClickListener() {
            @Override
            public void onTextClickListenerHis(String name, String what) {
                super.onTextClickListenerHis(name, what);
                mItemLookjiesJiesuanstate.setText(name);
                toChangeUi();
            }
        });
        mDialogBotList_shiffaren.setOnTextClickListener(new DialogBotList.OnTextClickListener() {
            @Override
            public void onTextClickListenerHis(String name, String what) {
                super.onTextClickListenerHis(name, what);
                shifoufaren = what;
                mItemLookjiesYnfaren.setText(name);
                toChangeUi();
            }
        });
        mDialogBotList_shiftianjin.setOnTextClickListener(new DialogBotList.OnTextClickListener() {
            @Override
            public void onTextClickListenerHis(String name, String what) {
                super.onTextClickListenerHis(name, what);
                if (name.equals("天津银行")) {
                    mItemLookjiesKaihuhang.setText(name);
                    is_Tianjin = true;
                    mItemLookTianjin.setVisibility(View.VISIBLE);
                    mItemLookjiesZhihang.setVisibility(View.INVISIBLE);
                } else {
                    is_Tianjin = false;
                    mItemLookTianjin.setVisibility(View.INVISIBLE);
                    mItemLookjiesZhihang.setVisibility(View.VISIBLE);
                    mInputDialog.show();
                    mInputDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                }
            }
        });
    }

    /*获取支行数据*/
    private void getZhi() {
        Map<String, Object> map = new HashMap<>();
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).tjBranch(EncryPtionUtil.getInstance(getActivity()).toEncryption(map));
        EncryPtionHttp.getInstance(getActivity()).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                Log.d("qqq", result.toJSONString());
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    JSONArray data = result.getJSONArray("data");
                    for (int i = 0; i < data.size(); i++) {
                        JSONObject jsonObject = data.getJSONObject(i);
                        BotListBean botListBean = new BotListBean(jsonObject.getString("bankname"), false);
                        mBotListBeans_zhihang.add(botListBean);
                    }
                    mDialogBotList_zhihang = new DialogBotList.Builder().title("请选择").botListBeanMap(mBotListBeans_zhihang).builder(EdtJieskFragment.this.getActivity());
                    mDialogBotList_zhihang.setOnTextClickListener(new DialogBotList.OnTextClickListener() {
                        @Override
                        public void onTextClickListenerHis(String name, String what) {
                            super.onTextClickListenerHis(name, what);
                            mItemLookTianjin.setText(name);
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
