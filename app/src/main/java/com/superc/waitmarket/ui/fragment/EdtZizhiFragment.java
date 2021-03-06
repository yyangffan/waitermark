package com.superc.waitmarket.ui.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.observer.CommonObserver;
import com.ljy.devring.http.support.throwable.HttpThrowable;
import com.luck.picture.lib.PictureSelectionModel;
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
import com.superc.waitmarket.utils.dialog.LoadingDialog;
import com.superc.waitmarket.utils.dialog.MiddleDialog;
import com.superc.waitmarket.views.InConstranLayout;
import com.superc.yyfflibrary.base.BaseFragment;
import com.superc.yyfflibrary.utils.ShareUtil;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.yogee.customdatepicker.datepicker.CustomDatePicker;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class EdtZizhiFragment extends BaseFragment {


    @BindView(R.id.item_edtzizhi_bianhao)
    EditText mItemEdtzizhiBianhao;
    @BindView(R.id.item_edtzizhi_danwei)
    EditText mItemEdtzizhiDanwei;
    @BindView(R.id.item_edtzizhi_faren)
    EditText mItemEdtzizhiFaren;
    @BindView(R.id.item_edtzizhi_shenfenzh)
    EditText mItemEdtzizhiShenfenzh;
    @BindView(R.id.item_edtzizhi_imgone)
    ImageView mItemEdtzizhiImgone;
    @BindView(R.id.item_edtzizhi_imgonexiangji)
    ImageView mItemEdtzizhiImgonexiangji;
    @BindView(R.id.item_edtzizhi_imgtwo)
    ImageView mItemEdtzizhiImgtwo;
    @BindView(R.id.item_edtzizhi_imgtwoxiangji)
    ImageView mItemEdtzizhiImgtwoxiangji;
    @BindView(R.id.imageView7)
    ImageView mImageView7;
    @BindView(R.id.imageView8)
    ImageView mImageView8;
    @BindView(R.id.jichu_look_smart)
    SmartRefreshLayout mJichuLookSmart;
    @BindView(R.id.item_edtzizhi_leixing)
    TextView mtvLeixing;
    @BindView(R.id.textView599)
    TextView mtvFocus;
    @BindView(R.id.incon_con)
    InConstranLayout mIncon;
    @BindView(R.id.item_lookjies_imgvyyzz)
    ImageView mItemLookjiesImgvyyzz;
    @BindView(R.id.item_lookjies_yyzzadd)
    TextView mItemLookjiesYyzzadd;
    @BindView(R.id.item_lookjies_lineyyzz)
    LinearLayout mItemLookjiesLineyyzz;
    @BindView(R.id.yingyedelete)
    ImageView mYingyedelete;
    @BindView(R.id.item_edtzizhi_fanwei)
    EditText mItemEdtzizhiFanwei;
    @BindView(R.id.item_edtzizhi_changsuo)
    EditText mItemEdtzizhiChangsuo;
    @BindView(R.id.item_edtzizhi_zijin)
    EditText mItemEdtzizhiZijin;
    @BindView(R.id.item_edtzizhi_yyzzst)
    TextView mItemEdtzizhiYyzzst;
    @BindView(R.id.item_edtzizhi_yyzzed)
    TextView mItemEdtzizhiYyzzed;
    @BindView(R.id.item_edtzizhi_sfztimest)
    TextView mItemEdtzizhiSfztimest;
    @BindView(R.id.item_edtzizhi_sfztimeed)
    TextView mItemEdtzizhiSfztimeed;
    @BindView(R.id.textView119)
    TextView mtvYyzzTmWenz;
    @BindView(R.id.textView162)
    TextView mtvZczjWenz;
    @BindView(R.id.linearLayout4)
    LinearLayout mLinearYyzzTm;
    @BindView(R.id.phase_broad_rb)
    RadioButton mBtYyzzz;
    @BindView(R.id.line_yyzzchangq)
    TextView mYyzzChangqi;
    @BindView(R.id.phase_broad_shenfen)
    RadioButton mBtShenfenz;
    @BindView(R.id.line_shenfenzchangq)
    TextView mShenfenzChanqi;
    @BindView(R.id.textView110)
    TextView mSfzTv;

    Unbinder unbinder;
    private String url_fore, url_back, url_yingye, mZhewngPath, mFanPath, mYingyePath;
    private String mEdtdetail_id, mIs_creat;
    private String leixing_id = "0";
    private EdtDetailActivity mEdtDetailActivity;
    private DialogBotList mDialogBotList_leixing;
    private List<BotListBean> mBotListBeans_leixing;
    private String[][] mStrings_zizhi = new String[][]{{"企业", "0"}, {"个体户", "1"}, {"个人", "2"}};
    private String channel;
    private String mStatus;
    private CustomDatePicker customDatePickerSt;
    private LoadingDialog mLoadingDialog;
    private boolean yyzz_ischeck = false, shenfenz_ischeck = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edt_zizhi, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void init() {
        mJichuLookSmart.setEnableOverScrollDrag(true);
        mJichuLookSmart.setEnablePureScrollMode(true);
        mLoadingDialog = LoadingDialog.getInstance(getActivity());
        mEdtDetailActivity = (EdtDetailActivity) getActivity();
        mBotListBeans_leixing = new ArrayList<>();
        mtvFocus.requestFocus();
        mStatus = (String) ShareUtil.getInstance(WaitApplication.getInstance()).get("status", "");
        if (mStatus.equals("1")) {//1 不可编辑  0可编辑
            mIncon.setmIsIntercept(true);
        }
    }

    @OnClick({R.id.item_edtzizhi_imgone, R.id.item_edtzizhi_imgonexiangji, R.id.item_edtzizhi_imgtwo, R.id.item_edtzizhi_imgtwoxiangji, R.id.imageView7, R.id.imageView8, R.id.item_edtzizhi_leixing,
            R.id.item_lookjies_lineyyzz, R.id.item_lookjies_yyzzadd, R.id.item_lookjies_imgvyyzz, R.id.yingyedelete, R.id.item_edtzizhi_yyzzst, R.id.item_edtzizhi_yyzzed, R.id.item_edtzizhi_sfztimest,
            R.id.item_edtzizhi_sfztimeed, R.id.phase_broad_rb, R.id.phase_broad_shenfen})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_lookjies_lineyyzz:
            case R.id.item_lookjies_yyzzadd:
            case R.id.item_lookjies_imgvyyzz:
                selectPic(111);
                break;
            case R.id.imageView7:
            case R.id.item_edtzizhi_imgone:
            case R.id.item_edtzizhi_imgonexiangji:
                selectPic(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.imageView8:
            case R.id.item_edtzizhi_imgtwo:
            case R.id.item_edtzizhi_imgtwoxiangji:
                selectPic(110);
                break;
            case R.id.item_edtzizhi_leixing:
                mDialogBotList_leixing.show();
                break;
            case R.id.yingyedelete:
                mYingyePath = "";
                url_yingye = "";
                mItemLookjiesImgvyyzz.setVisibility(View.GONE);
                mYingyedelete.setVisibility(View.GONE);
                break;
            case R.id.item_edtzizhi_yyzzst:
                showDateDialog(mItemEdtzizhiYyzzst, "2000-01-01 00:00", "2035-12-12 00:00");
                break;
            case R.id.item_edtzizhi_yyzzed:
                String zizhi_st = mItemEdtzizhiYyzzst.getText().toString();
                if (TextUtils.isEmpty(zizhi_st)) {
                    ToastShow("请先选择开始时间");
                    return;
                }
                showDateDialog(mItemEdtzizhiYyzzed, zizhi_st.replace(".", "-") + " 00:00", "2035-12-12 00:00");
                break;
            case R.id.item_edtzizhi_sfztimest:
                showDateDialog(mItemEdtzizhiSfztimest, "2000-01-01 00:00", "2035-12-12 00:00");
                break;
            case R.id.item_edtzizhi_sfztimeed:
                String sfz_st = mItemEdtzizhiSfztimest.getText().toString();
                if (TextUtils.isEmpty(sfz_st)) {
                    ToastShow("请先选择开始时间");
                    return;
                }
                showDateDialog(mItemEdtzizhiSfztimeed, sfz_st.replace(".", "-") + " 00:00", "2035-12-12 00:00");
                break;
            case R.id.phase_broad_rb:
                yyzz_ischeck = !yyzz_ischeck;
                mBtYyzzz.setChecked(yyzz_ischeck);
                if (yyzz_ischeck) {
                    mYyzzChangqi.setVisibility(View.GONE);
                    mItemEdtzizhiYyzzed.setVisibility(View.GONE);
                    mtvYyzzTmWenz.setText("营业执照开始时间");
                } else {
                    mYyzzChangqi.setVisibility(View.VISIBLE);
                    mItemEdtzizhiYyzzed.setVisibility(View.VISIBLE);
                    mtvYyzzTmWenz.setText("营业执照有效期");
                }
                break;
            case R.id.phase_broad_shenfen:
                shenfenz_ischeck = !shenfenz_ischeck;
                mBtShenfenz.setChecked(shenfenz_ischeck);
                if (shenfenz_ischeck) {
                    mShenfenzChanqi.setVisibility(View.GONE);
                    mItemEdtzizhiSfztimeed.setVisibility(View.GONE);
                    mSfzTv.setText("身份证开始时间");
                } else {
                    mShenfenzChanqi.setVisibility(View.VISIBLE);
                    mItemEdtzizhiSfztimeed.setVisibility(View.VISIBLE);
                    mSfzTv.setText("身份证有效期");
                }

                break;

        }
    }

    /* mIs_creat  0新建1修改*/
    public void toJudge() {
        mEdtdetail_id = (String) ShareUtil.getInstance(WaitApplication.getInstance()).get("edtdetail_id", "");
        channel = (String) ShareUtil.getInstance(WaitApplication.getInstance()).get("channel", "");
        mStatus = (String) ShareUtil.getInstance(WaitApplication.getInstance()).get("status", "");
        if (mStatus.equals("1") && mIncon != null) {//1 不可编辑  0可编辑
            mIncon.setmIsIntercept(true);
        }
        toGetEdtData();
    }

    /**
     * type//1基础信息2场景照片3资质信息4结算信息5老客6新客
     * channel 0待激活入口,已激活入口1待审核入口 3维护列表
     */
    private void toGetEdtData() {
        Map<String, Object> map = new HashMap<>();
        map.put("shopId", mEdtdetail_id);
        map.put("type", 3);
        map.put("channel", channel);
        map.put("staus", mStatus);
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
                        toCHushihuaDig();
                    } else {
                        mIs_creat = "1";
                        setData(result.getJSONObject("data").getJSONObject("merchantDetails"));
                    }
                } else {
                    toCHushihuaDig();
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

    private void toCHushihuaDig() {
        mBotListBeans_leixing.clear();
        String leixing = mtvLeixing.getText().toString();
        for (int i = 0; i < mStrings_zizhi.length; i++) {
            BotListBean botListBean = new BotListBean(mStrings_zizhi[i][0], TextUtils.isEmpty(leixing) ? false : (mStrings_zizhi[i][0].equals(leixing) ? true : false), mStrings_zizhi[i][1]);
            mBotListBeans_leixing.add(botListBean);
        }
        mDialogBotList_leixing = new DialogBotList.Builder().title("请选择").botListBeanMap(mBotListBeans_leixing).builder(EdtZizhiFragment.this.getActivity());
        mDialogBotList_leixing.setOnTextClickListener(new DialogBotList.OnTextClickListener() {
            @Override
            public void onTextClickListenerHis(String name, String what) {
                super.onTextClickListenerHis(name, what);
                leixing_id = what;
                mtvLeixing.setText(name);
                if (what.equals("2")) {
                    mtvYyzzTmWenz.setVisibility(View.GONE);
                    mtvZczjWenz.setVisibility(View.GONE);
                    mLinearYyzzTm.setVisibility(View.GONE);
                    mItemEdtzizhiZijin.setVisibility(View.GONE);
                } else {
                    mtvYyzzTmWenz.setVisibility(View.VISIBLE);
                    mtvZczjWenz.setVisibility(View.VISIBLE);
                    mLinearYyzzTm.setVisibility(View.VISIBLE);
                    mItemEdtzizhiZijin.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    private void setData(JSONObject merchant) {
        String managetype = BigDecimalUtils.bigUtil(merchant.getString("managetype"));
        mtvYyzzTmWenz.setVisibility(View.VISIBLE);
        mtvZczjWenz.setVisibility(View.VISIBLE);
        mLinearYyzzTm.setVisibility(View.VISIBLE);
        mItemEdtzizhiZijin.setVisibility(View.VISIBLE);
        if (managetype.equals("0")) {
            mtvLeixing.setText("企业");
        } else if (managetype.equals("1")) {
            mtvLeixing.setText("个体户");
        } else {
            mtvLeixing.setText("个人");
            mtvYyzzTmWenz.setVisibility(View.GONE);
            mtvZczjWenz.setVisibility(View.GONE);
            mLinearYyzzTm.setVisibility(View.GONE);
            mItemEdtzizhiZijin.setVisibility(View.GONE);
        }
        mItemEdtzizhiBianhao.setText(merchant.getString("creditcode"));
        mItemEdtzizhiDanwei.setText(merchant.getString("registercompany"));
        mItemEdtzizhiFaren.setText(merchant.getString("name"));
        mItemEdtzizhiShenfenzh.setText(merchant.getString("cardid"));
        mItemEdtzizhiFanwei.setText(merchant.getString("businessscope"));
        mItemEdtzizhiChangsuo.setText(merchant.getString("placeofbusiness"));
        mItemEdtzizhiZijin.setText(merchant.getString("registeredcapital"));
        mItemEdtzizhiYyzzst.setText(merchant.getString("startdate"));
        String yyenddate = merchant.getString("enddate");
        if (TextUtils.isEmpty(yyenddate)) {
            yyzz_ischeck = true;
            mBtYyzzz.setChecked(true);
            mYyzzChangqi.setVisibility(View.GONE);
            mItemEdtzizhiYyzzed.setVisibility(View.GONE);
            mtvYyzzTmWenz.setText("营业执照开始时间");
        } else {
            yyzz_ischeck = false;
            mBtYyzzz.setChecked(false);
            mYyzzChangqi.setVisibility(View.VISIBLE);
            mItemEdtzizhiYyzzed.setVisibility(View.VISIBLE);
            mItemEdtzizhiYyzzed.setText(yyenddate);
            mtvYyzzTmWenz.setText("营业执照有效期");
        }
        mItemEdtzizhiSfztimest.setText(merchant.getString("starttime"));
        String endtime = merchant.getString("endtime");
        if (TextUtils.isEmpty(endtime)) {
            shenfenz_ischeck = true;
            mBtShenfenz.setChecked(true);
            mShenfenzChanqi.setVisibility(View.GONE);
            mItemEdtzizhiSfztimeed.setVisibility(View.GONE);
            mSfzTv.setText("身份证开始时间");
        } else {
            shenfenz_ischeck = false;
            mBtShenfenz.setChecked(false);
            mShenfenzChanqi.setVisibility(View.VISIBLE);
            mItemEdtzizhiSfztimeed.setVisibility(View.VISIBLE);
            mItemEdtzizhiSfztimeed.setText(endtime);
            mSfzTv.setText("身份证有效期");
        }
        toCHushihuaDig();

        mZhewngPath = merchant.getString("cardidfrntphoto");//正面
        mFanPath = merchant.getString("cardidbackphoto");//反面
        mYingyePath = merchant.getString("licenseimg");//营业执照

        RoundedCorners roundedCorners = new RoundedCorners(10);
        RequestOptions override = RequestOptions.bitmapTransform(roundedCorners).error(R.drawable.icon_error).placeholder(R.drawable.icon_error).override(300, 300);
        RequestOptions requestOptions = new RequestOptions().error(R.drawable.icon_error).apply(override).placeholder(R.drawable.icon_error);
        /*——————————————————————————————————————————————身份证——————————————————————————————————————————————*/
        if (!TextUtils.isEmpty(mZhewngPath)) {
            mItemEdtzizhiImgone.setVisibility(View.VISIBLE);
            mItemEdtzizhiImgonexiangji.setVisibility(View.GONE);
            mImageView7.setVisibility(View.VISIBLE);
            if (mZhewngPath.startsWith("http") || mZhewngPath.startsWith("https")) {
                Glide.with(this).load(mZhewngPath).apply(requestOptions).into(mItemEdtzizhiImgone);
            } else {
                Glide.with(this).load(Constant.IMG_URL + mZhewngPath).apply(requestOptions).into(mItemEdtzizhiImgone);
            }
        } else {
            Glide.with(this).load(R.drawable.t_shenfenzhengzhengmian).into(mItemEdtzizhiImgone);
            mItemEdtzizhiImgone.setVisibility(View.VISIBLE);
            mItemEdtzizhiImgonexiangji.setVisibility(View.VISIBLE);
            mImageView7.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(mFanPath)) {
            mItemEdtzizhiImgtwo.setVisibility(View.VISIBLE);
            mItemEdtzizhiImgtwoxiangji.setVisibility(View.GONE);
            mImageView8.setVisibility(View.VISIBLE);
            if (mFanPath.startsWith("http") || mFanPath.startsWith("https")) {
                Glide.with(this).load(mFanPath).apply(requestOptions).into(mItemEdtzizhiImgtwo);
            } else {
                Glide.with(this).load(Constant.IMG_URL + mFanPath).apply(requestOptions).into(mItemEdtzizhiImgtwo);
            }
        } else {
            Glide.with(this).load(R.drawable.t_shenfenzhengfanmian).into(mItemEdtzizhiImgtwo);
            mItemEdtzizhiImgtwo.setVisibility(View.VISIBLE);
            mItemEdtzizhiImgtwoxiangji.setVisibility(View.VISIBLE);
            mImageView8.setVisibility(View.GONE);
        }
        /*——————————————————————————————————————————————营业执照——————————————————————————————————————————————*/
        if (!TextUtils.isEmpty(mYingyePath)) {
            mItemLookjiesImgvyyzz.setVisibility(View.VISIBLE);
            mYingyedelete.setVisibility(View.VISIBLE);
            if (mYingyePath.startsWith("http") || mYingyePath.startsWith("https")) {
                Glide.with(this).load(mYingyePath).apply(requestOptions).into(mItemLookjiesImgvyyzz);
            } else {
                Glide.with(this).load(Constant.IMG_URL + mYingyePath).apply(requestOptions).into(mItemLookjiesImgvyyzz);
            }
        } else {
            mItemLookjiesImgvyyzz.setVisibility(View.GONE);
            mYingyedelete.setVisibility(View.GONE);
        }

    }

    public void toCommit() {
        String bianhao = mItemEdtzizhiBianhao.getText().toString();
        String danwei = mItemEdtzizhiDanwei.getText().toString();
        String fanren = mItemEdtzizhiFaren.getText().toString();
        String sfznum = mItemEdtzizhiShenfenzh.getText().toString();
        String yyzz_st = mItemEdtzizhiYyzzst.getText().toString();
        String yyzz_ed = mItemEdtzizhiYyzzed.getText().toString();
        String sfz_st = mItemEdtzizhiSfztimest.getText().toString();
        String sfz_ed = mItemEdtzizhiSfztimeed.getText().toString();
        if (compare_dateDate(yyzz_st, yyzz_ed) == -1 && !yyzz_ischeck) {
            ToastShow("营业执照开始时间不能大于结束时间");
            return;
        }
        if (compare_dateDate(sfz_st, sfz_ed) == -1 && !shenfenz_ischeck) {
            ToastShow("身份证有效期开始时间不能大于结束时间");
            return;
        }
       /* if (TextUtils.isEmpty(leixing_id)) {
            ToastShow("请选择经营类型");
            return;
        }
        if (TextUtils.isEmpty(bianhao)) {
            ToastShow("请输入营业执照编号");
            return;
        }
        if (TextUtils.isEmpty(danwei)) {
            ToastShow("请输入注册单位");
            return;
        }
        if (TextUtils.isEmpty(fanren)) {
            ToastShow("请输入法定代表人");
            return;
        }
        if (TextUtils.isEmpty(sfznum)) {
            ToastShow("请输入法人身份证号");
            return;
        }
        if (TextUtils.isEmpty(mZhewngPath)) {
            ToastShow("请上传身份证正面");
            return;
        }
        if (TextUtils.isEmpty(mFanPath)) {
            ToastShow("请上传身份证反面");
            return;
        }*/
        Map<String, Object> map = new HashMap<>();
        map.put("shopId", mEdtdetail_id);
        map.put("manageType", leixing_id);
        map.put("creditCode", bianhao);
        map.put("registerCompany", danwei);
        map.put("legalPerson", fanren);
        map.put("cardId", sfznum);
        map.put("cardIdFrntPhoto", mZhewngPath);
        map.put("cardIdBackPhoto", mFanPath);
        map.put("licenseimg", mYingyePath);
        map.put("businessscope", mItemEdtzizhiFanwei.getText().toString());
        map.put("placeofbusiness", mItemEdtzizhiChangsuo.getText().toString());
        map.put("registeredcapital", mItemEdtzizhiZijin.getText().toString());
        map.put("startdate", yyzz_st);
        if (!yyzz_ischeck) {
            map.put("enddate", yyzz_ed);
        } else {
            map.put("enddate", "");
        }
        map.put("starttime", sfz_st);
        if (!shenfenz_ischeck) {
            map.put("endtime", sfz_ed);
        } else {
            map.put("endtime", "");
        }
        map.put("type", mIs_creat);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).newQualificationInformation(EncryPtionUtil.getInstance(getActivity()).toEncryption(map));
        EncryPtionHttp.getInstance(getActivity()).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                Log.d("qqq", result.toJSONString());
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    if (mEdtDetailActivity.is_tijiao) {
                        toJudge();
                    }
                    mEdtDetailActivity.toScroll();
                } else {
                    if (!TextUtils.isEmpty(msg)) {
                        new MiddleDialog.Builder(getActivity()).img_id(R.drawable.con_shibai).title("提交失败").content(msg).build().show();
                    }
                }
            }

            @Override
            public void onErrorResult(HttpThrowable httpThrowable) {
                ToastShow("接口访问异常" + httpThrowable.message);
                Log.d("qqq", httpThrowable.message);
            }
        });


    }

    private void selectPic(int reque_code) {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelectionModel pictureSelectionModel = PictureSelector.create(this).openGallery(PictureMimeType.ofImage());
        if (reque_code == 111) {
            pictureSelectionModel.enableCrop(false);
        } else {
            pictureSelectionModel.enableCrop(true);

        }
        pictureSelectionModel
                .maxSelectNum(10)// 最大图片选择数量 int
//                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .previewImage(false)// 是否可预览图片 true or false
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .isCamera(true)// 是否显示拍照按钮 true or false
                .compress(true)// 是否压缩 true or false
                .compressSavePath(getPath())//压缩图片保存地址
                .withAspectRatio((reque_code == 111 ? 9 : 16), 9)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
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
            switch (requestCode) {
                case 111:
                    List<LocalMedia> selectListt = PictureSelector.obtainMultipleResult(data);
                    RoundedCorners roundedCornerss = new RoundedCorners(10);
                    RequestOptions overridee = RequestOptions.bitmapTransform(roundedCornerss).error(R.drawable.icon_error).placeholder(R.drawable.icon_error).override(300, 300);
                    url_yingye = selectListt.get(0).getCompressPath();
                    Glide.with(EdtZizhiFragment.this).load(url_yingye).apply(overridee).into(mItemLookjiesImgvyyzz);
                    mItemLookjiesImgvyyzz.setVisibility(View.VISIBLE);
                    mYingyedelete.setVisibility(View.VISIBLE);
                    toCommitYingye();
                    break;

                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    RoundedCorners roundedCorners = new RoundedCorners(10);
                    RequestOptions override = RequestOptions.bitmapTransform(roundedCorners).error(R.drawable.icon_error).placeholder(R.drawable.icon_error).override(300, 300);
                    url_fore = selectList.get(0).getCompressPath();
                    Glide.with(EdtZizhiFragment.this).load(url_fore).apply(override).into(mItemEdtzizhiImgone);
                    mItemEdtzizhiImgone.setVisibility(View.VISIBLE);
                    mItemEdtzizhiImgonexiangji.setVisibility(View.GONE);
                    mImageView7.setVisibility(View.VISIBLE);
                    toCommitPic(true);
                    break;
                case 110:
                    List<LocalMedia> selectListtt = PictureSelector.obtainMultipleResult(data);
                    RoundedCorners roundedCornersss = new RoundedCorners(10);
                    RequestOptions overrideee = RequestOptions.bitmapTransform(roundedCornersss).error(R.drawable.icon_error).placeholder(R.drawable.icon_error).override(300, 300);
                    url_back = selectListtt.get(0).getCompressPath();
                    Glide.with(EdtZizhiFragment.this).load(url_back).apply(overrideee).into(mItemEdtzizhiImgtwo);
                    mItemEdtzizhiImgtwo.setVisibility(View.VISIBLE);
                    mItemEdtzizhiImgtwoxiangji.setVisibility(View.GONE);
                    mImageView8.setVisibility(View.VISIBLE);
                    toCommitPic(false);
                    break;
            }
        }
    }

    private void toCommitYingye() {
        File img = new File(url_yingye);
        String names = img.getName();
        mLoadingDialog.show();
        RequestBody requestFile = RequestBody.create(MediaType.parse(guessMimeType(img.getPath())), img);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", names, requestFile);
        Observable<JSONObject> observable = DevRing.httpManager().getService(ApiService.class).uploadLicenseLogo(body);
        DevRing.httpManager().commonRequest(observable, new CommonObserver<JSONObject>() {
            @Override
            public void onResult(JSONObject result) {
                mLoadingDialog.dismiss();
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    mYingyePath = result.getJSONObject("data").getString("picSmallPath");
                } else {
                    if (!TextUtils.isEmpty(msg)) {
                        ToastShow(msg);
                    }
                }
            }

            @Override
            public void onError(HttpThrowable httpThrowable) {
                mLoadingDialog.dismiss();
            }
        }, (LifecycleTransformer) null);
    }

    private void toCommitPic(final boolean is_fore) {
        File img = new File(is_fore ? url_fore : url_back);
        String names = img.getName();
        RequestBody requestFile = RequestBody.create(MediaType.parse(guessMimeType(img.getPath())), img);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", names, requestFile);
        Observable<JSONObject> observable = DevRing.httpManager().getService(ApiService.class).uploadLegalPersonCard(body);
        DevRing.httpManager().commonRequest(observable, new CommonObserver<JSONObject>() {
            @Override
            public void onResult(JSONObject result) {
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    if (is_fore) {
                        mZhewngPath = result.getJSONObject("data").getString("picSmallPath");
                    } else {
                        mFanPath = result.getJSONObject("data").getString("picSmallPath");
                    }
                } else {
                    if (!TextUtils.isEmpty(msg)) {
                        ToastShow(msg);
                    }
                }
            }

            @Override
            public void onError(HttpThrowable httpThrowable) {
            }
        }, (LifecycleTransformer) null);

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
        customDatePickerSt = new CustomDatePicker(getActivity(), new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                mtv.setText(new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA).format(new Date(timestamp)));
//                mtv.setText(DateFormatUtils.long2Str(timestamp, false));
            }
        }, begin_tm + " 00:00", ed_tm);
        customDatePickerSt.setCanShowPreciseTime(false); // 不显示时和分
        customDatePickerSt.setScrollLoop(true); // 允许循环滚动
        customDatePickerSt.setCanShowAnim(true);//开启滚动动画
        customDatePickerSt.show(!TextUtils.isEmpty(time) ? time.replace(".", "-") + " 00:00" : new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA).format(new Date()));
    }

    /**
     * @param DATE1
     * @param DATE2
     * @return -1:DATE1>DATE2
     * 1:DATE1< DATE2
     * 0:DATE1==DATE2
     */
    public static int compare_dateDate(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (DATE1.contains(".")) {
                DATE1 = DATE1.replace(".", "-");
            }
            if (DATE2.contains(".")) {
                DATE2 = DATE2.replace(".", "-");
            }
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 > dt2");
                return -1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1 < dt2");
                return 1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
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
