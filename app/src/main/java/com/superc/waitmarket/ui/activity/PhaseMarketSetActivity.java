package com.superc.waitmarket.ui.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.throwable.HttpThrowable;
import com.superc.waitmarket.R;
import com.superc.waitmarket.adapter.PhaseYuYueAdapter;
import com.superc.waitmarket.base.ApiService;
import com.superc.waitmarket.bean.BotListBean;
import com.superc.waitmarket.bean.MarketDeatilBean;
import com.superc.waitmarket.httputil.EncryPtionHttp;
import com.superc.waitmarket.httputil.EncryPtionUtil;
import com.superc.waitmarket.utils.CheckDateRule;
import com.superc.waitmarket.utils.dialog.DialogBotList;
import com.superc.waitmarket.utils.dialog.DialogWeekList;
import com.superc.waitmarket.utils.dialog.PhaseRemindDialog;
import com.superc.yyfflibrary.base.BaseActivity;
import com.superc.yyfflibrary.utils.ShareUtil;
import com.yogee.customdatepicker.datepicker.CustomDatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;

/********************************************************************
 @version: 1.0.0
 @description: 营销券设置
 @author: EDZ
 @time: 2019/11/14 10:16
 @变更历史: 2019/11/14 暂时隐藏掉了节假日功能   放开的话将TODO全部放开
 ********************************************************************/
public class PhaseMarketSetActivity extends BaseActivity {
    private static final String TAG = "PhaseMarketSetActivity";
    @BindView(R.id.user_title)
    TextView mPhaseTitle;
    @BindView(R.id.phase_marketset_what)
    TextView mPhaseMarketsetWhat;
    @BindView(R.id.phase_marketset_manmoney)
    EditText mPhaseMarketsetManmoney;
    @BindView(R.id.phase_marketset_jianmoney)
    EditText mPhaseMarketsetJianmoney;
    @BindView(R.id.phase_marketset_llmanjian)
    LinearLayout mPhaseMarketsetLlmanjian;
    @BindView(R.id.phase_marketset_rbtopone)
    RadioButton mPhaseMarketsetRbtopone;
    @BindView(R.id.phase_marketset_rbtoptwo)
    RadioButton mPhaseMarketsetRbtoptwo;
    @BindView(R.id.phase_marketset_rgtop)
    RadioGroup mPhaseMarketsetRgtop;
    @BindView(R.id.phase_marketset_timeonest)
    TextView mPhaseMarketsetTimeonest;
    @BindView(R.id.phase_marketset_timeoneed)
    TextView mPhaseMarketsetTimeoneed;
    @BindView(R.id.phase_marketset_timetwost)
    TextView mPhaseMarketsetTimetwost;
    @BindView(R.id.phase_marketset_timetwoed)
    TextView mPhaseMarketsetTimetwoed;
    @BindView(R.id.phase_marketset_lltimetwo)
    LinearLayout mPhaseMarketsetLltimetwo;
    @BindView(R.id.phase_marketset_lltimetwode)
    TextView mPhaseMarketsetLltimetwode;//时间2删除
    @BindView(R.id.phase_marketset_timethreest)
    TextView mPhaseMarketsetTimethreest;
    @BindView(R.id.phase_marketset_timethreeed)
    TextView mPhaseMarketsetTimethreeed;
    @BindView(R.id.phase_marketset_llthreetime)
    LinearLayout mPhaseMarketsetLlthreetime;
    @BindView(R.id.phase_marketset_llthreede)
    TextView mPhaseMarketsetLlthreede;//时间3删除
    @BindView(R.id.phase_marketset_canuse)
    RadioButton mPhaseMarketsetCanuse;
    @BindView(R.id.phase_marketset_cntuse)
    RadioButton mPhaseMarketsetCntuse;
    @BindView(R.id.phase_marketset_rgt)
    RadioGroup mPhaseMarketsetRgt;
    @BindView(R.id.phase_marketset_dateonest)
    TextView mPhaseMarketsetDateonest;
    @BindView(R.id.phase_marketset_dateoneed)
    TextView mPhaseMarketsetDateoneed;
    @BindView(R.id.phase_marketset_datetwost)
    TextView mPhaseMarketsetDatetwost;
    @BindView(R.id.phase_marketset_datetwoed)
    TextView mPhaseMarketsetDatetwoed;
    @BindView(R.id.phase_marketset_lldatetwo)
    LinearLayout mPhaseMarketsetLldatetwo;
    @BindView(R.id.phase_marketset_datetwodelete)
    TextView mPhaseMarketsetDatetwodelete;
    @BindView(R.id.phase_marketset_datethreedelete)
    TextView mPhaseMarketsetDateThreedelete;
    @BindView(R.id.phase_marketset_datethreest)
    TextView mPhaseMarketsetDatethreest;
    @BindView(R.id.phase_marketset_datethreeed)
    TextView mPhaseMarketsetDatethreeed;
    @BindView(R.id.phase_marketset_lldatethree)
    LinearLayout mPhaseMarketsetLldatethree;
    @BindView(R.id.phase_marketset_adddate)
    TextView mPhaseMarketsetAdddate;
    @BindView(R.id.phase_marketset_guize)
    EditText mPhaseMarketsetGuize;
    @BindView(R.id.phase_marketset_lookimgv)
    ImageView mPhaseMarketsetLookimgv;
    @BindView(R.id.recyclerphase_marketset_recy)
    RecyclerView mRecyclerphaseMarketsetRecy;
    @BindView(R.id.phase_marketset_edtday)
    EditText mPhaseMarketsetEdtday;
    @BindView(R.id.phase_marketset_week)
    TextView mPhaseMarketsetTvWeek;
    @BindView(R.id.phase_marketset_help)
    TextView mPhaseMarketsetTvHelp;
    @BindView(R.id.phase_marketset_zhekounum)
    EditText mPhaseMarketsetZhekounum;
    @BindView(R.id.phase_marketset_llzhekou)
    LinearLayout mPhaseMarketsetLlzhekou;
    @BindView(R.id.phase_marketset_zengyanall)
    EditText mPhaseMarketsetZengyanall;
    @BindView(R.id.phase_marketset_lltizengpin)
    LinearLayout mPhaseMarketsetLltizengpin;
    @BindView(R.id.textView92)
    TextView mtv;
    @BindView(R.id.market_set_addtm)
    TextView mtv_timeAdd;
    @BindView(R.id.phase_market_set_nouse)
    FrameLayout mjiejiaUse;
    @BindView(R.id.phase_marketset_lldateone)
    ConstraintLayout mllDateOne;
    @BindView(R.id.phase_marketset_dateonedelete)
    TextView mtv_dateoneDelete;
    @BindView(R.id.phase_marketset_heghadd)
    TextView mPhaseMarketsetHeghadd;
    @BindView(R.id.phase_marketset_heghdelete)
    TextView mPhaseMarketsetHeghdelete;
    @BindView(R.id.phase_marketset_edthegh)
    EditText mPhaseMarketsetEdthegh;
    @BindView(R.id.phase_marketset_llhegh)
    LinearLayout mPhaseMarketsetLlhegh;
    @BindView(R.id.phase_marketset_djqnum)
    EditText mPhaseMarketsetDjqnum;
    @BindView(R.id.phase_marketset_djqadd)
    TextView mPhaseMarketsetDjqadd;
    @BindView(R.id.phase_marketset_djqstclear)
    TextView mPhaseMarketsetDjqstclear;
    @BindView(R.id.phase_marketset_djqedtst)
    EditText mPhaseMarketsetDjqedtst;
    @BindView(R.id.phase_marketset_djqnumclear)
    TextView mPhaseMarketsetDjqnumclear;
    @BindView(R.id.phase_marketset_djqedtnum)
    EditText mPhaseMarketsetDjqedtnum;
    @BindView(R.id.phase_marketset_djqcancel)
    TextView mPhaseMarketsetDjqcancel;
    @BindView(R.id.phase_marketset_lldaijin)
    LinearLayout mPhaseMarketsetLldaijin;
    @BindView(R.id.phase_marketset_djqllset)
    LinearLayout mPhaseMarketsetDjqllSet;
    @BindView(R.id.checkBox)
    CheckBox mCheckBox;
    @BindView(R.id.phase_marketset_save)
    TextView mtv_commit;


    private ArrayList<BotListBean> mMbot_leftList;
    private DialogBotList mDialogBotList_left;
    private DialogWeekList mDialogWeekList;
    private String marketset_week = "";
    private CustomDatePicker customTimePickerSt;
    private boolean ll_twiceTimeShow, ll_thirdTimeShow, ll_oneDateShow, ll_twiceDateShow, ll_thirdDateShow, ll_hegh, ll_djq = false;
    private boolean look = true;
    private CustomDatePicker customDatePickerSt;
    private List<Map<String, Object>> mMapList_yuyue;
    private PhaseYuYueAdapter mPhaseYuYueAdapter;
    private String[] mStrings_yuyue = new String[]{"需要预约，消费高峰期可能需要等位", "商家提供免费WIFI", "发票内容请咨询商家", "不可自带酒水"
            , "不提供包间", "免费打包", "商家提供免费车位", "仅限堂食",};
    private PhaseRemindDialog mPhaseRemindDialog;
    private int delet_what = 0;
    private List<Map<String, String>> mList_time;
    private List<Map<String, String>> mList_date;
    private String mMShopid;
    private PhaseRemindDialog mPhaseRemindDialogTishi;
    private PhaseRemindDialog mPhaseRemindDialog_commit;
    private boolean mIs_creat;
    private String mId;
    private MarketDeatilBean mMarketDeatilBean;
    private String mIsnewcustomer = "0";//0老客福利1新客专享2异业营销券
    private String mEdtdetail_id;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_phase_market_set;
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        mtv.requestFocus();
        setPricePointT(mPhaseMarketsetManmoney);
        setPricePointT(mPhaseMarketsetJianmoney);
        setPricePoint(mPhaseMarketsetZhekounum);
        setPricePointT(mPhaseMarketsetDjqnum);
        setPricePointT(mPhaseMarketsetEdthegh);
        setPricePointT(mPhaseMarketsetDjqedtst);
        mMShopid = (String) ShareUtil.getInstance(this).get("edtdetail_id", "");
        mPhaseMarketsetTvWeek.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mPhaseMarketsetTvHelp.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mRecyclerphaseMarketsetRecy.setNestedScrollingEnabled(false);
        mMapList_yuyue = new ArrayList<>();
        mList_time = new ArrayList<>();
        mList_date = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerphaseMarketsetRecy.setLayoutManager(linearLayoutManager);
        mPhaseYuYueAdapter = new PhaseYuYueAdapter(this, mMapList_yuyue);
        mRecyclerphaseMarketsetRecy.setAdapter(mPhaseYuYueAdapter);
        mPhaseYuYueAdapter.setOnItemClickListener(new PhaseYuYueAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int pos) {
                for (int i = 0; i < mMapList_yuyue.size(); i++) {
                    Map<String, Object> map = mMapList_yuyue.get(i);
                    boolean type = (boolean) map.get("type");
                    if (i == pos) {
                        map.put("type", !type);
                    }
                }
                mPhaseYuYueAdapter.notifyDataSetChanged();
            }
        });
        Intent intent = getIntent();
        if (intent != null) {
            mIs_creat = intent.getBooleanExtra("is_creat", true);
            mIsnewcustomer = intent.getStringExtra("isnewcustomer");
            mCheckBox.setText(mIsnewcustomer.equals("0") ? "次日生效" : "当日生效");
            if (!mIs_creat) {
                mPhaseTitle.setText("修改支付营销券");
                mId = intent.getStringExtra("id");
                String data = intent.getStringExtra("data");
                mMarketDeatilBean = new Gson().fromJson(data, MarketDeatilBean.class);
                setData();
            } else {
                mPhaseTitle.setText("创建支付营销券");
                initCreat();
            }
        }
        initViewClick();
        initBotDialog();
        initDig();

        mPhaseMarketsetGuize.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});

    }

    private void initViewClick() {
        mPhaseMarketsetRgtop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if (id == R.id.phase_marketset_rbtopone) {
                    mPhaseMarketsetTvWeek.setVisibility(View.GONE);
                }
            }
        });

    }

    @OnClick({R.id.left_back, R.id.phase_marketset_what, R.id.phase_marketset_help, R.id.phase_marketset_timeonest, R.id.phase_marketset_timeoneed,
            R.id.phase_marketset_timetwost, R.id.phase_marketset_timetwoed, R.id.phase_marketset_timethreest, R.id.phase_marketset_timethreeed, R.id.market_set_addtm,
            R.id.phase_marketset_dateonest, R.id.phase_marketset_dateoneed, R.id.phase_marketset_datetwost, R.id.phase_marketset_datetwoed, R.id.phase_marketset_datetwodelete,
            R.id.phase_marketset_datethreest, R.id.phase_marketset_datethreeed, R.id.phase_marketset_adddate, R.id.phase_marketset_looktv, R.id.phase_marketset_lookimgv,
            R.id.phase_marketset_save, R.id.phase_marketset_rbtoptwo, R.id.phase_marketset_week, R.id.phase_marketset_lltimetwode, R.id.phase_marketset_llthreede,
            R.id.phase_marketset_datethreedelete, R.id.phase_marketset_cntuse, R.id.phase_marketset_canuse, R.id.phase_market_set_nouse, R.id.phase_marketset_dateonedelete,
            R.id.phase_marketset_heghadd, R.id.phase_marketset_heghdelete, R.id.phase_marketset_djqadd, R.id.phase_marketset_djqcancel, R.id.phase_marketset_djqnumclear, R.id.phase_marketset_djqstclear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.phase_marketset_what:
                mDialogBotList_left.show();
                break;
            case R.id.phase_marketset_week:
            case R.id.phase_marketset_rbtoptwo:
                toShowWeekDialog();
                break;
            case R.id.phase_marketset_help:
                statActivity(PhaseHelpActivity.class);
                break;
            case R.id.phase_marketset_timeonest:
                showTimeDialog(mPhaseMarketsetTimeonest, "2035-12-12 00:00", "2035-12-12 23:59");
                break;
            case R.id.phase_marketset_timeoneed:
                showTimeDialog(mPhaseMarketsetTimeoneed, "2019-03-03 " + mPhaseMarketsetTimeonest.getText().toString(), "2035-12-12 23:59");
                break;
            case R.id.phase_marketset_timetwost:
                showTimeDialog(mPhaseMarketsetTimetwost, "2019-03-03 " + mPhaseMarketsetTimeoneed.getText().toString(), "2035-12-12 23:59");
                break;
            case R.id.phase_marketset_timetwoed:
                showTimeDialog(mPhaseMarketsetTimetwoed, "2019-03-03 " + mPhaseMarketsetTimetwost.getText().toString(), "2035-12-12 23:59");
                break;
            case R.id.phase_marketset_lltimetwode:
                delet_what = 0;
                mPhaseRemindDialog.show();
                break;
            case R.id.phase_marketset_timethreest:
                if (ll_twiceTimeShow) {
                    showTimeDialog(mPhaseMarketsetTimethreest, "2019-03-03 " + mPhaseMarketsetTimetwoed.getText().toString(), "2035-12-12 23:59");
                } else {
                    showTimeDialog(mPhaseMarketsetTimethreest, "2019-03-03 " + mPhaseMarketsetTimeoneed.getText().toString(), "2035-12-12 23:59");
                }
                break;
            case R.id.phase_marketset_timethreeed:
                showTimeDialog(mPhaseMarketsetTimethreeed, "2019-03-03 " + mPhaseMarketsetTimethreest.getText().toString(), "2035-12-12 23:59");
                break;
            case R.id.phase_marketset_llthreede:
                delet_what = 1;
                mPhaseRemindDialog.show();
                break;
            case R.id.phase_marketset_dateonedelete:
                delet_what = 5;
                mPhaseRemindDialog.show();
                break;
            case R.id.market_set_addtm:
                if (!ll_twiceTimeShow) {
                    ll_twiceTimeShow = true;
                    mPhaseMarketsetLltimetwo.setVisibility(View.VISIBLE);
                    mPhaseMarketsetLltimetwode.setVisibility(View.VISIBLE);
                } else {
                    ll_thirdTimeShow = true;
                    mPhaseMarketsetLlthreetime.setVisibility(View.VISIBLE);
                    mPhaseMarketsetLlthreede.setVisibility(View.VISIBLE);
                }
                if (ll_twiceTimeShow && ll_thirdTimeShow) {
                    mtv_timeAdd.setVisibility(View.GONE);
                }
                break;
            case R.id.phase_marketset_dateonest:
                showDateDialog(mPhaseMarketsetDateonest, "2019-01-01 00:00", "2035-12-12 00:00");
                break;
            case R.id.phase_marketset_dateoneed:
                showDateDialog(mPhaseMarketsetDateoneed, "2019-" + mPhaseMarketsetDateonest.getText().toString() + " 00:00", "2035-12-12 00:00");
                break;
            case R.id.phase_marketset_datetwost:
                showDateDialog(mPhaseMarketsetDatetwost, "2019-" + mPhaseMarketsetDateoneed.getText().toString() + " 00:00", "2035-12-12 00:00");
                break;
            case R.id.phase_marketset_datetwoed:
                showDateDialog(mPhaseMarketsetDatetwoed, "2019-" + mPhaseMarketsetDatetwost.getText().toString() + " 00:00", "2035-12-12 00:00");
                break;
            case R.id.phase_marketset_datetwodelete:
                delet_what = 2;
                mPhaseRemindDialog.show();
                break;
            case R.id.phase_marketset_datethreest:
                if (ll_twiceDateShow) {
                    showDateDialog(mPhaseMarketsetDatethreest, "2019-" + mPhaseMarketsetDatetwoed.getText().toString() + " 00:00", "2035-12-12 00:00");
                } else {
                    showDateDialog(mPhaseMarketsetDatethreest, "2019-" + mPhaseMarketsetDateoneed.getText().toString() + " 00:00", "2035-12-12 00:00");
                }
                break;
            case R.id.phase_marketset_datethreeed:
                showDateDialog(mPhaseMarketsetDatethreeed, "2019-" + mPhaseMarketsetDatethreest.getText().toString() + " 00:00", "2035-12-12 00:00");
                break;
            case R.id.phase_marketset_datethreedelete:
                delet_what = 3;
                mPhaseRemindDialog.show();
                break;
            case R.id.phase_marketset_adddate:
                if (!ll_oneDateShow) {
                    ll_oneDateShow = true;
                    mllDateOne.setVisibility(View.VISIBLE);
                    mtv_dateoneDelete.setVisibility(View.VISIBLE);
                } else if (!ll_twiceDateShow) {
                    ll_twiceDateShow = true;
                    mPhaseMarketsetLldatetwo.setVisibility(View.VISIBLE);
                    mPhaseMarketsetDatetwodelete.setVisibility(View.VISIBLE);
                } else {
                    ll_thirdDateShow = true;
                    mPhaseMarketsetLldatethree.setVisibility(View.VISIBLE);
                    mPhaseMarketsetDateThreedelete.setVisibility(View.VISIBLE);
                }
                if (ll_oneDateShow && ll_twiceDateShow && ll_thirdDateShow) {
                    mPhaseMarketsetAdddate.setVisibility(View.GONE);
                }
                break;
            case R.id.phase_marketset_looktv:
            case R.id.phase_marketset_lookimgv:
                look = !look;
                if (look) {
                    mPhaseMarketsetLookimgv.setImageDrawable(this.getResources().getDrawable(R.drawable.icon_shanglajiantou));
                    mRecyclerphaseMarketsetRecy.setVisibility(View.VISIBLE);
                } else {
                    mPhaseMarketsetLookimgv.setImageDrawable(this.getResources().getDrawable(R.drawable.icon_xialajiantou));
                    mRecyclerphaseMarketsetRecy.setVisibility(View.GONE);
                }
                break;
            case R.id.phase_market_set_nouse://FrameLayout进行时间拦截  不进行任何操作
                break;
            case R.id.phase_marketset_cntuse:
                mjiejiaUse.setVisibility(View.VISIBLE);
                break;
            case R.id.phase_marketset_canuse:
                mjiejiaUse.setVisibility(View.GONE);
                break;
            case R.id.phase_marketset_save:
                toCheck();
                break;
            case R.id.phase_marketset_heghadd:
                ll_hegh = true;
                mPhaseMarketsetHeghadd.setVisibility(View.GONE);
                mPhaseMarketsetLlhegh.setVisibility(View.VISIBLE);
                break;
            case R.id.phase_marketset_heghdelete:
                ll_hegh = false;
                mPhaseMarketsetHeghadd.setVisibility(View.VISIBLE);
                mPhaseMarketsetLlhegh.setVisibility(View.GONE);
                mPhaseMarketsetEdthegh.setText("");
                break;
            case R.id.phase_marketset_djqadd:
                ll_djq = true;
                mPhaseMarketsetDjqllSet.setVisibility(View.VISIBLE);
                mPhaseMarketsetDjqadd.setVisibility(View.GONE);
                mPhaseMarketsetDjqcancel.setVisibility(View.VISIBLE);
                break;
            case R.id.phase_marketset_djqcancel:
                ll_djq = false;
                mPhaseMarketsetDjqllSet.setVisibility(View.GONE);
                mPhaseMarketsetDjqadd.setVisibility(View.VISIBLE);
                mPhaseMarketsetDjqcancel.setVisibility(View.GONE);
                mPhaseMarketsetDjqedtst.setText("");
                mPhaseMarketsetDjqedtnum.setText("");
                break;
            case R.id.phase_marketset_djqnumclear:
                mPhaseMarketsetDjqedtnum.setText("");
                break;
            case R.id.phase_marketset_djqstclear:
                mPhaseMarketsetDjqedtst.setText("");
                break;
        }
    }

    /*编辑、查看的参数设置*/
    private void setData() {
//        mMarketDeatilBean;
        MarketDeatilBean.DataBean data = mMarketDeatilBean.getData();
        if (data != null) {
            int coupontype = data.getCoupontype();
            String what_type = "";
            switch (coupontype) {
                case 1:
                    what_type = "满减券";
                    break;
                case 2:
                    what_type = "折扣券";
                    break;
                case 3:
                    what_type = "赠品券";
                    break;
                case 5:
                    what_type = "体验券";
                    break;
                case 6:
                    what_type = "代金券";
                    break;
            }
            whatJudge(what_type);
            mPhaseMarketsetWhat.setText(what_type);
            mPhaseMarketsetZhekounum.setText(data.getDiscount());
            mPhaseMarketsetManmoney.setText(data.getFullamount());
            mPhaseMarketsetJianmoney.setText(data.getReduceamount());
            mPhaseMarketsetZengyanall.setText(coupontype == 3 ? data.getProductname() : data.getExperienceticketname());

            int isweekdays = data.getIsweekdays();
            mPhaseMarketsetRbtopone.setChecked(isweekdays == 1 ? true : false);
            mPhaseMarketsetRbtoptwo.setChecked(isweekdays == 1 ? false : true);
            mPhaseMarketsetTvWeek.setVisibility(mPhaseMarketsetRbtopone.isChecked() ? View.GONE : View.VISIBLE);
            if (mPhaseMarketsetRbtoptwo.isChecked()) {
                StringBuilder stb_time = new StringBuilder();
                String weekdays = data.getWeekdays();
                marketset_week = weekdays;
                stb_time.append(weekdays.contains("1") ? "周一," : "");
                stb_time.append(weekdays.contains("2") ? "周二," : "");
                stb_time.append(weekdays.contains("3") ? "周三," : "");
                stb_time.append(weekdays.contains("4") ? "周四," : "");
                stb_time.append(weekdays.contains("5") ? "周五," : "");
                stb_time.append(weekdays.contains("6") ? "周六," : "");
                stb_time.append(weekdays.contains("7") ? "周日," : "");
                String resu_time = stb_time.toString();
                mPhaseMarketsetTvWeek.setText(resu_time.substring(0, resu_time.length() - 1));
            }
            List<MarketDeatilBean.DataBean.ActTimeBean> actTime = data.getActTime();
            mPhaseMarketsetTimeonest.setText(actTime.get(0).getStartTime());
            mPhaseMarketsetTimeoneed.setText(actTime.get(0).getEndTime());
            if (actTime.size() >= 2) {
                ll_twiceTimeShow = true;
                mPhaseMarketsetLltimetwo.setVisibility(View.VISIBLE);
                mPhaseMarketsetLltimetwode.setVisibility(View.VISIBLE);
                mPhaseMarketsetTimetwost.setText(actTime.get(1).getStartTime());
                mPhaseMarketsetTimetwoed.setText(actTime.get(1).getEndTime());
            }
            if (actTime.size() == 3) {
                ll_thirdTimeShow = true;
                mPhaseMarketsetLlthreetime.setVisibility(View.VISIBLE);
                mPhaseMarketsetLlthreede.setVisibility(View.VISIBLE);
                mPhaseMarketsetTimethreest.setText(actTime.get(2).getStartTime());
                mPhaseMarketsetTimethreeed.setText(actTime.get(2).getEndTime());
            }
            // TODO: 2019/11/14 暂时隐藏掉节假日是否可用条件
            /*
            mPhaseMarketsetCanuse.setChecked(data.getHoliday() == 1 ? true : false);
            mPhaseMarketsetCntuse.setChecked(data.getHoliday() == 1 ? false : true);
            if (!mPhaseMarketsetCntuse.isChecked()) {
                mjiejiaUse.setVisibility(View.GONE);
                List<MarketDeatilBean.DataBean.HolidayDateBean> holidayDate = data.getHolidayDate();
                if (holidayDate != null && holidayDate.size() > 0) {
                    ll_oneDateShow = true;
                    mllDateOne.setVisibility(View.VISIBLE);
                    mtv_dateoneDelete.setVisibility(View.VISIBLE);
                    mPhaseMarketsetDateonest.setText(PhaseDateUtil.getStrTimeSet(holidayDate.get(0).getStartDate()));
                    mPhaseMarketsetDateoneed.setText(PhaseDateUtil.getStrTimeSet(holidayDate.get(0).getEndDate()));
                    if (holidayDate.size() >= 2) {
                        ll_twiceDateShow = true;
                        mPhaseMarketsetLldatetwo.setVisibility(View.VISIBLE);
                        mPhaseMarketsetDatetwodelete.setVisibility(View.VISIBLE);
                        mPhaseMarketsetDatetwost.setText(PhaseDateUtil.getStrTimeSet(holidayDate.get(1).getStartDate()));
                        mPhaseMarketsetDatetwoed.setText(PhaseDateUtil.getStrTimeSet(holidayDate.get(1).getEndDate()));
                    }
                    if (holidayDate.size() == 3) {
                        ll_thirdDateShow = true;
                        mPhaseMarketsetLldatethree.setVisibility(View.VISIBLE);
                        mPhaseMarketsetDateThreedelete.setVisibility(View.VISIBLE);
                        mPhaseMarketsetDatethreest.setText(PhaseDateUtil.getStrTimeSet(holidayDate.get(2).getStartDate()));
                        mPhaseMarketsetDatethreeed.setText(PhaseDateUtil.getStrTimeSet(holidayDate.get(2).getEndDate()));
                    }
                }
                if (ll_twiceTimeShow && ll_thirdTimeShow) {
                    mtv_timeAdd.setVisibility(View.GONE);
                }
                if (ll_oneDateShow && ll_twiceDateShow && ll_thirdDateShow) {
                    mPhaseMarketsetAdddate.setVisibility(View.GONE);
                }
            } else {
                mjiejiaUse.setVisibility(View.VISIBLE);
            }
            */
            mPhaseMarketsetGuize.setText(data.getCustomusagerules());
            // 预设规则
            String customusagerules = data.getUsagerules();
            for (int i = 0; i < mStrings_yuyue.length; i++) {
                Map<String, Object> map = new HashMap<>();
                String title = mStrings_yuyue[i];
                map.put("title", mStrings_yuyue[i]);
                map.put("type", customusagerules.contains(title) ? true : false);
                mMapList_yuyue.add(map);
            }
            mPhaseYuYueAdapter.notifyDataSetChanged();
            mPhaseMarketsetEdtday.setText(data.getLimitvalidity() + "");
            /*三期修改*/
//            data.getIsnewcustomer();
            String maxdiscountamount = data.getMaxdiscountamount();
            String voucheramount = data.getVoucheramount();
            String startingpoint = data.getStartingpoint();
            String maxnumber = data.getMaxnumber();
            if (TextUtils.isEmpty(maxdiscountamount)) {//最高优惠
                ll_hegh = false;
                mPhaseMarketsetHeghadd.setVisibility(View.VISIBLE);
                mPhaseMarketsetLlhegh.setVisibility(View.GONE);
            } else {
                ll_hegh = true;
                mPhaseMarketsetHeghadd.setVisibility(View.GONE);
                mPhaseMarketsetLlhegh.setVisibility(View.VISIBLE);
                mPhaseMarketsetEdthegh.setText(maxdiscountamount);
            }
            mPhaseMarketsetDjqnum.setText(voucheramount);
            if (TextUtils.isEmpty(startingpoint) && TextUtils.isEmpty(maxnumber)) {
                ll_djq = false;
                mPhaseMarketsetDjqadd.setVisibility(View.VISIBLE);
                mPhaseMarketsetDjqllSet.setVisibility(View.GONE);
                mPhaseMarketsetDjqcancel.setVisibility(View.GONE);
            } else {
                ll_djq = true;
                mPhaseMarketsetDjqadd.setVisibility(View.GONE);
                mPhaseMarketsetDjqllSet.setVisibility(View.VISIBLE);
                mPhaseMarketsetDjqcancel.setVisibility(View.VISIBLE);
                mPhaseMarketsetDjqedtst.setText(startingpoint);
                mPhaseMarketsetDjqedtnum.setText(maxnumber);
            }


        }
    }

    private void initCreat() {
        for (int i = 0; i < mStrings_yuyue.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("title", mStrings_yuyue[i]);
            map.put("type", false);
            mMapList_yuyue.add(map);
        }
        mPhaseYuYueAdapter.notifyDataSetChanged();
    }

    /*提交前的条件检测*/
    private void toCheck() {
        /*券内容*/
        String quan_leixing = mPhaseMarketsetWhat.getText().toString();
        String manMony = mPhaseMarketsetManmoney.getText().toString();
        String jianMony = mPhaseMarketsetJianmoney.getText().toString();
        String zhekou_s = mPhaseMarketsetZhekounum.getText().toString();
        String zp_ty = mPhaseMarketsetZengyanall.getText().toString();
        switch (quan_leixing) {
            case "满减券":
                if (TextUtils.isEmpty(manMony) || TextUtils.isEmpty(jianMony)) {
                    ToastShow("请输入券内容");
                    return;
                }
                double man = 0;
                double jian = 0;
                try {
                    man = Double.parseDouble(manMony);
                    jian = Double.parseDouble(jianMony);
                } catch (NumberFormatException e) {
                    Log.e(TAG, e.toString());
                    ToastShow("请输入正常数字(券内容)");
                    return;
                }
                if (man == 0 || jian == 0) {
                    ToastShow("起点金额、优惠金额均须>0");
                    return;
                } else if (man <= jian) {
                    ToastShow("优惠金额不可大于或等于起点金额");
                    return;
                }
                break;
            case "折扣券":
                if (TextUtils.isEmpty(zhekou_s)) {
                    ToastShow("请输入券内容");
                    return;
                }
                float zhekou = Float.parseFloat(zhekou_s);
                if (zhekou >= 10) {
                    ToastShow("折扣需小于10");
                    return;
                } else if (zhekou <= 0) {
                    ToastShow("折扣需大于0");
                    return;
                }
                break;
            case "赠品券":
            case "体验券":
                if (TextUtils.isEmpty(zp_ty)) {
                    ToastShow("请输入券内容");
                    return;
                }
                break;
            case "代金券":
                if (TextUtils.isEmpty(mPhaseMarketsetDjqnum.getText().toString())) {
                    ToastShow("请输入券内容");
                    return;
                }
                break;
        }
        if (ll_hegh && TextUtils.isEmpty(mPhaseMarketsetEdthegh.getText().toString())) {
            ToastShow("请设置最高优惠");
            return;
        }
        if (ll_hegh) {
            String mPhse_heMony = mPhaseMarketsetEdthegh.getText().toString();
            String mPhase_JianMony = mPhaseMarketsetJianmoney.getText().toString();
            double heMony = Double.parseDouble(mPhse_heMony) * 100;
            double jianMon = Double.parseDouble(mPhase_JianMony) * 100;
            Log.e(TAG, "最高跟立减的倍数关系：" + heMony % jianMon);
            if (heMony % jianMon != 0 || heMony % jianMon != 0.0 || heMony == 0.0 || heMony == 0) {
                ToastShow("输入金额需为立减金额倍数");
                return;
            }
        }
        if (ll_djq && TextUtils.isEmpty(mPhaseMarketsetDjqedtst.getText().toString()) && TextUtils.isEmpty(mPhaseMarketsetDjqedtnum.getText().toString())) {
            ToastShow("请输入设置优惠内容");
            return;
        }
        String djq_input = mPhaseMarketsetDjqnum.getText().toString();
        String djq_start = mPhaseMarketsetDjqedtst.getText().toString();
        String djq_num = mPhaseMarketsetDjqedtnum.getText().toString();
        if (!TextUtils.isEmpty(djq_start)) {
            double dou_start = Double.parseDouble(djq_start);
            double dou_input = Double.parseDouble(djq_input);
            if (dou_start < dou_input) {
                ToastShow("代金券使用起点金额需≥代金券金额");
                return;
            }
        }
        if (!TextUtils.isEmpty(djq_num)) {
            int int_num = Integer.parseInt(djq_num);
            if (int_num == 0) {
                ToastShow("输入张数需≥1");
                return;
            }
        }

        //可用时间
        if (checkTime().equals("请检查可用时间段,不允许重叠")) {
            return;
        }
        // TODO: 2019/11/14 暂时隐藏掉节假日是否可用条件
        /*
        //节假日
        if (mPhaseMarketsetCanuse.isChecked()) {
            if (ll_oneDateShow || ll_twiceDateShow || ll_thirdDateShow) {
                if (checkDate().equals("请检查节假日,不允许重叠"))
                    return;
            }
        }
        */
        if (mPhaseMarketsetRbtoptwo.isChecked()) {
            if (TextUtils.isEmpty(marketset_week)) {
                ToastShow("至少选择周一-周日中的一天");
                return;
            }
        }
        String guize = mPhaseMarketsetGuize.getText().toString();
        if (!TextUtils.isEmpty(guize)) {
            Pattern p = Pattern.compile("[a-zA-Z|\u4e00-\u9fa5|,.，。：: 1234567890]+");
            Matcher m = p.matcher(guize);
            if (!m.matches()) {
                if (!guize.contains("\n")) {
                    ToastShow("自定义规则中，符号只能输入逗号、句号和冒号");
                    return;
                }
            }
        }
        String day = mPhaseMarketsetEdtday.getText().toString();
        if (TextUtils.isEmpty(day)) {
            ToastShow("请填写营销券有效期");
            return;
        } else {
            if (Integer.parseInt(day) < 1) {
                ToastShow("营销券有效期不能小于1天");
                return;
            }
        }

        mPhaseRemindDialog_commit.show();
    }

    /*检测可用时间段是否有重叠*/
    private String checkTime() {
        StringBuilder ky_timeT = new StringBuilder();
        ky_timeT.append(mPhaseMarketsetTimeonest.getText().toString() + "━" + mPhaseMarketsetTimeoneed.getText().toString());
        ky_timeT.append(ll_twiceTimeShow ? "；" + mPhaseMarketsetTimetwost.getText().toString() + "━" + mPhaseMarketsetTimetwoed.getText().toString() + (ll_thirdTimeShow ? "；" : "") : (ll_thirdTimeShow ? "；" : ""));
        String timeThree = ll_thirdTimeShow ? mPhaseMarketsetTimethreest.getText().toString() + "━" + mPhaseMarketsetTimethreeed.getText().toString() : "";
        String timet = ky_timeT.toString();
        mList_time.clear();
        Map<String, String> map_one = new HashMap<>();
        map_one.put("startTime", mPhaseMarketsetTimeonest.getText().toString());
        map_one.put("endTime", mPhaseMarketsetTimeoneed.getText().toString());
        mList_time.add(map_one);
        if (ll_twiceTimeShow) {
            Map<String, String> map_two = new HashMap<>();
            map_two.put("startTime", mPhaseMarketsetTimetwost.getText().toString());
            map_two.put("endTime", mPhaseMarketsetTimetwoed.getText().toString());
            mList_time.add(map_two);
        }
        if (ll_thirdTimeShow) {
            Map<String, String> map_three = new HashMap<>();
            map_three.put("startTime", mPhaseMarketsetTimethreest.getText().toString());
            map_three.put("endTime", mPhaseMarketsetTimethreeed.getText().toString());
            mList_time.add(map_three);
        }
        if (mList_time.size() != 1 && !CheckDateRule.toCompare(mList_time)) {
            ToastShow("请检查可用时间段,不允许重叠");
            return "请检查可用时间段,不允许重叠";
        } else if (mList_time.size() == 1) {
            int one_compare = CheckDateRule.compare_date(map_one.get("startTime"), map_one.get("endTime"));
            if (one_compare == -1 || one_compare == 0) {
                ToastShow("请检查可用时间段,不允许重叠");
                return "请检查可用时间段,不允许重叠";
            }
        }
        return timet + timeThree;
    }

    /*检测节假日时间段是否有重叠*/
    private String checkDate() {
        StringBuilder ky_timeT = new StringBuilder();
        ky_timeT.append(mPhaseMarketsetDateonest.getText().toString() + "━" + mPhaseMarketsetDateoneed.getText().toString());
        ky_timeT.append(ll_thirdDateShow ? "；" + mPhaseMarketsetDatetwost.getText().toString() + "━" + mPhaseMarketsetDatetwoed.getText().toString() + (ll_thirdDateShow ? "；" : "") : (ll_thirdDateShow ? "；" : ""));
        String timeThree = ll_thirdDateShow ? mPhaseMarketsetDatethreest.getText().toString() + "━" + mPhaseMarketsetDatethreeed.getText().toString() : "";
        String timet = ky_timeT.toString();
        mList_date.clear();
        Map<String, String> map_one = new HashMap<>();
        if (ll_oneDateShow) {
            map_one.put("startDate", mPhaseMarketsetDateonest.getText().toString());
            map_one.put("endDate", mPhaseMarketsetDateoneed.getText().toString());
            mList_date.add(map_one);
        }
        if (ll_twiceDateShow) {
            Map<String, String> map_two = new HashMap<>();
            map_two.put("startDate", mPhaseMarketsetDatetwost.getText().toString());
            map_two.put("endDate", mPhaseMarketsetDatetwoed.getText().toString());
            mList_date.add(map_two);
        }
        if (ll_thirdDateShow) {
            Map<String, String> map_three = new HashMap<>();
            map_three.put("startDate", mPhaseMarketsetDatethreest.getText().toString());
            map_three.put("endDate", mPhaseMarketsetDatethreeed.getText().toString());
            mList_date.add(map_three);
        }
        if (mList_date.size() > 1 && !CheckDateRule.toCompareDate(mList_date)) {
            ToastShow("请检查节假日,不允许重叠");
            return "请检查节假日,不允许重叠";
        } else if (mList_date.size() == 1) {
            int one_compare = CheckDateRule.compare_dateDate(mList_date.get(0).get("startDate"), mList_date.get(0).get("endDate"));
            if (one_compare == -1 || one_compare == 0) {
                ToastShow("请检查节假日,不允许重叠");
                return "请检查节假日,不允许重叠";
            }
        }
        return timet + timeThree;
    }


    /*创建以及修改的提交*/
    private void toCommit() {
        String coupontype = null, yueshe = "";
        StringBuilder yusheBuild = new StringBuilder();
        String what = mPhaseMarketsetWhat.getText().toString();
        switch (what) {
            case "满减券":
                coupontype = "1";
                break;
            case "折扣券":
                coupontype = "2";
                break;
            case "赠品券":
                coupontype = "3";
                break;
            case "体验券":
                coupontype = "5";
                break;
            case "代金券":
                coupontype = "6";
                break;
        }

        for (int i = 0; i < mMapList_yuyue.size(); i++) {
            Map<String, Object> map = mMapList_yuyue.get(i);
            boolean type = (boolean) map.get("type");
            if (type) {
                String title = (String) map.get("title");
                yusheBuild.append(title);
                yusheBuild.append("#");
            }
        }
        yueshe = yusheBuild.toString();
        Map<String, Object> map = new HashMap<>();
        map.put("shopId", mMShopid);
        map.put("couponType", coupontype);
        map.put("fullAmount", mPhaseMarketsetManmoney.getText().toString());
        map.put("reduceAmount", mPhaseMarketsetJianmoney.getText().toString());
        map.put("discount", mPhaseMarketsetZhekounum.getText().toString());
        map.put("productName", mPhaseMarketsetZengyanall.getText().toString());
        map.put("experienceTicketName", mPhaseMarketsetZengyanall.getText().toString());
        map.put("isweekdays", mPhaseMarketsetRbtopone.isChecked() ? "1" : "2");
        map.put("weekDays", mPhaseMarketsetRbtoptwo.isChecked() ? marketset_week : "1,2,3,4,5,6,7");
        map.put("availableTime", mList_time);
        // TODO: 2019/11/14 暂时隐藏掉节假日是否可用条件
        /*
        map.put("holiday", mPhaseMarketsetCanuse.isChecked() ? "1" : "0");
        map.put("unavailableDate", mList_date);
        */
        map.put("effectType", mIsnewcustomer.equals("0") ? "2" : "1");
        map.put("isnewcustomer", mIsnewcustomer);//0老客福利1新客专享2异业营销券
        map.put("maxdiscountamount", mPhaseMarketsetEdthegh.getText().toString());//最高优惠金额
        map.put("voucheramount", mPhaseMarketsetDjqnum.getText().toString());
        map.put("startingpoint", mPhaseMarketsetDjqedtst.getText().toString());
        map.put("maxnumber", mPhaseMarketsetDjqedtnum.getText().toString());
        map.put("limitValiDity", mPhaseMarketsetEdtday.getText().toString());
        map.put("usagerules", TextUtils.isEmpty(yueshe) ? "" : yueshe.substring(0, yueshe.length() - 1));
        map.put("customusagerules", mPhaseMarketsetGuize.getText().toString());
        map.put("type", mIs_creat ? "1" : "2");//1创建2修改
        map.put("id", mIs_creat ? "" : mId);//创建传""
        mtv_commit.setEnabled(false);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).campaignSettings(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                mtv_commit.setEnabled(true);
                boolean code = result.getBoolean("code");
                if (code) {
                    mPhaseRemindDialogTishi = new PhaseRemindDialog.Builder(PhaseMarketSetActivity.this).title((String) result.get("message"))
                            .left("确认").left_color(R.color.main_color).build(PhaseMarketSetActivity.this.getWindowManager().getDefaultDisplay());
                    mPhaseRemindDialogTishi.setOnTextClickListener(new PhaseRemindDialog.OnTextClickListener() {
                        @Override
                        public void onLeftClickListener() {
                            super.onLeftClickListener();
                            PhaseMarketSetActivity.this.finish();
                        }
                    });
                    mPhaseRemindDialogTishi.show();
                }
            }

            @Override
            public void onErrorResult(HttpThrowable httpThrowable) {
                mtv_commit.setEnabled(true);
            }
        });


    }

    private void initDig() {
        mPhaseRemindDialog_commit = new PhaseRemindDialog.Builder(this).title("提示").content("是否确认提交该营销券？")
                .left("取消").left_color(0).right_color(0).right("确认").build(getWindowManager().getDefaultDisplay());
        mPhaseRemindDialog_commit.setOnTextClickListener(new PhaseRemindDialog.OnTextClickListener() {
            @Override
            public void onRightClickListener() {
                super.onRightClickListener();
                toCommit();
            }
        });
    }

    private void initBotDialog() {
        mMbot_leftList = new ArrayList<>();
        String setwhat = mPhaseMarketsetWhat.getText().toString();
        mMbot_leftList.add(new BotListBean("满减券", setwhat.equals("满减券"), "1"));
        mMbot_leftList.add(new BotListBean("折扣券", setwhat.equals("折扣券"), "2"));
        mMbot_leftList.add(new BotListBean("赠品券", setwhat.equals("赠品券"), "3"));
        mMbot_leftList.add(new BotListBean("体验券", setwhat.equals("体验券"), "5"));
        mMbot_leftList.add(new BotListBean("代金券", setwhat.equals("代金券"), "6"));
        mDialogBotList_left = new DialogBotList.Builder().title("请选择券类型").botListBeanMap(mMbot_leftList).builder(this);
        mDialogBotList_left.setOnTextClickListener(new DialogBotList.OnTextClickListener() {
            @Override
            public void onTextClickListener(String name) {
                String old_name = mPhaseMarketsetWhat.getText().toString();
                if (!old_name.equals(name)) {
                    mPhaseMarketsetWhat.setText(name);
                    whatJudge(name);
                }
            }
        });
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        mPhaseRemindDialog = new PhaseRemindDialog.Builder(this).title("是否确认删除该时间段？").
                left_color(R.color.txt_week).left("取消").right_color(R.color.main_color).right("确认").build(defaultDisplay);
        mPhaseRemindDialog.setOnTextClickListener(new PhaseRemindDialog.OnTextClickListener() {
            @Override
            public void onRightClickListener() {
                super.onRightClickListener();
                switch (delet_what) {
                    case 0:
                        mPhaseMarketsetLltimetwo.setVisibility(View.GONE);
                        mPhaseMarketsetLltimetwode.setVisibility(View.GONE);
                        mtv_timeAdd.setVisibility(View.VISIBLE);
                        ll_twiceTimeShow = false;
                        break;
                    case 1:
                        mPhaseMarketsetLlthreetime.setVisibility(View.GONE);
                        mPhaseMarketsetLlthreede.setVisibility(View.GONE);
                        mtv_timeAdd.setVisibility(View.VISIBLE);
                        ll_thirdTimeShow = false;
                        break;
                    case 2:
                        mPhaseMarketsetLldatetwo.setVisibility(View.GONE);
                        mPhaseMarketsetDatetwodelete.setVisibility(View.GONE);
                        mPhaseMarketsetAdddate.setVisibility(View.VISIBLE);
                        ll_twiceDateShow = false;
                        break;
                    case 3:
                        mPhaseMarketsetLldatethree.setVisibility(View.GONE);
                        mPhaseMarketsetDateThreedelete.setVisibility(View.GONE);
                        mPhaseMarketsetAdddate.setVisibility(View.VISIBLE);
                        ll_thirdDateShow = false;
                        break;
                    case 5:
                        mllDateOne.setVisibility(View.GONE);
                        mtv_dateoneDelete.setVisibility(View.GONE);
                        mPhaseMarketsetAdddate.setVisibility(View.VISIBLE);
                        ll_oneDateShow = false;
                        break;
                }

            }
        });

    }

    private void toShowWeekDialog() {
        mDialogWeekList = new DialogWeekList(PhaseMarketSetActivity.this, marketset_week);
        mDialogWeekList.setOnSureClickListener(new DialogWeekList.OnSureClickListener() {
            @Override
            public void onSureClickListener(Map<String, Object> map) {
                String week = (String) map.get("week");
                marketset_week = (String) map.get("num");
                mPhaseMarketsetTvWeek.setVisibility(!TextUtils.isEmpty(week) ? View.VISIBLE : View.GONE);
                mPhaseMarketsetTvWeek.setText(!TextUtils.isEmpty(week) ? week : "");
            }
        });


        mDialogWeekList.show();
    }

    private void whatJudge(String name) {
        toResetState();
        mPhaseMarketsetLlmanjian.setVisibility(View.GONE);
        mPhaseMarketsetLlzhekou.setVisibility(View.GONE);
        mPhaseMarketsetLltizengpin.setVisibility(View.GONE);
        mPhaseMarketsetLldaijin.setVisibility(View.GONE);
        switch (name) {
            case "满减券":
                mPhaseMarketsetLlmanjian.setVisibility(View.VISIBLE);
                break;
            case "折扣券":
                mPhaseMarketsetLlzhekou.setVisibility(View.VISIBLE);
                break;
            case "赠品券":
            case "体验券":
                mPhaseMarketsetLltizengpin.setVisibility(View.VISIBLE);
                break;
            case "代金券":
                mPhaseMarketsetLldaijin.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void toResetState() {
        mPhaseMarketsetManmoney.setText("");
        mPhaseMarketsetJianmoney.setText("");
        mPhaseMarketsetZhekounum.setText("");
        mPhaseMarketsetZengyanall.setText("");
        mPhaseMarketsetDjqnum.setText("");
        mPhaseMarketsetDjqedtst.setText("");
        mPhaseMarketsetDjqedtnum.setText("");
        mPhaseMarketsetEdthegh.setText("");
        ll_hegh = false;
        mPhaseMarketsetHeghadd.setVisibility(View.VISIBLE);
        mPhaseMarketsetLlhegh.setVisibility(View.GONE);
        ll_djq = false;
        mPhaseMarketsetDjqadd.setVisibility(View.VISIBLE);
        mPhaseMarketsetDjqllSet.setVisibility(View.GONE);
        mPhaseMarketsetDjqcancel.setVisibility(View.GONE);
    }

    /**
     * 时间选择
     *
     * @param mtv      需要进行时间设置的TextView
     * @param begin_tm 时间选择的开始时间
     * @param ed_tm    时间选择的结束时间
     */
    private void showTimeDialog(final TextView mtv, String begin_tm, String ed_tm) {
        customTimePickerSt = new CustomDatePicker(this, new CustomDatePicker.Callback() {
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

    /**
     * 日期选择
     *
     * @param mtv      需要进行日期设置的TextView
     * @param begin_tm 日期选择的开始日期
     * @param ed_tm    日期选择的结束日期
     */
    private void showDateDialog(final TextView mtv, String begin_tm, String ed_tm) {
        String time = mtv.getText().toString();
        customDatePickerSt = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                mtv.setText(new SimpleDateFormat("MM-dd", Locale.CHINA).format(new Date(timestamp)));
//                mtv.setText(DateFormatUtils.long2Str(timestamp, false));
            }
        }, begin_tm + " 00:00", ed_tm);
        customDatePickerSt.setCanShowPreciseTime(false); // 不显示时和分
        customDatePickerSt.setScrollLoop(true); // 允许循环滚动
        customDatePickerSt.setCanShowAnim(true);//开启滚动动画
        customDatePickerSt.show(!TextUtils.isEmpty(time) ? "2019-" + time + " 00:00" : "2019-01-01 00:00");
    }

    public static void setPricePoint(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 1) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 2);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(1);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {


            }

        });

    }

    public static void setPricePointT(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {


            }

        });

    }

}
