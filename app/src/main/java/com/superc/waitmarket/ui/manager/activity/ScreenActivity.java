package com.superc.waitmarket.ui.manager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.throwable.HttpThrowable;
import com.ljy.devring.util.KeyboardUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.superc.waitmarket.R;
import com.superc.waitmarket.base.ApiService;
import com.superc.waitmarket.bean.BotListBean;
import com.superc.waitmarket.httputil.EncryPtionHttp;
import com.superc.waitmarket.httputil.EncryPtionUtil;
import com.superc.waitmarket.utils.BigDecimalUtils;
import com.superc.waitmarket.utils.dialog.DialogBotList;
import com.superc.yyfflibrary.base.BaseActivity;
import com.superc.yyfflibrary.utils.titlebar.TitleUtils;
import com.yogee.customdatepicker.datepicker.CustomDatePicker;

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
import io.reactivex.Observable;

public class ScreenActivity extends BaseActivity {
    @BindView(R.id.screen_name)
    EditText mScreenName;
    @BindView(R.id.screen_tuozhan)
    EditText mScreenTuozhan;
    @BindView(R.id.screen_shanghuquany)
    EditText mScreenShanghuquany;
    @BindView(R.id.screen_xiaoek)
    EditText mScreenXiaoek;
    @BindView(R.id.screen_yiji)
    TextView mScreenYiji;
    @BindView(R.id.screen_erji)
    TextView mScreenErji;
    @BindView(R.id.screen_city)
    TextView mScreenCity;
    @BindView(R.id.screen_quyu)
    TextView mScreenQuyu;
    @BindView(R.id.screen_shangquan)
    TextView mScreenShangquan;
    @BindView(R.id.textView111)
    TextView mRe;
    @BindView(R.id.screen_starttm)
    TextView mStartTm;
    @BindView(R.id.screen_endtm)
    TextView mEndTm;
    @BindView(R.id.smartlayout)
    SmartRefreshLayout mSmartlayout;
    private DialogBotList mDialogBotList_repeat;
    private DialogBotList mDialogBotList_yiji;
    private DialogBotList mDialogBotList_erji;
    private DialogBotList mDialogBotList_city;
    private DialogBotList mDialogBotList_quyu;
    private DialogBotList mDialogBotList_shangquan;
    private List<BotListBean> mBotListBeans_repeat;
    private List<BotListBean> mBotListBeans_yiji;
    private List<BotListBean> mBotListBeans_erji;
    private List<BotListBean> mBotListBeans_city;
    private List<BotListBean> mBotListBeans_quyu;
    private List<BotListBean> mBotListBeans_shagnquan;
    private String city_code, quyu_code, shangquan_code, one_code, two_code, shangquan_type;
    private CustomDatePicker customDatePickerSt;
    private String[][] mRepeat = new String[][]{{"张三", "1"}, {"李四", "2"}, {"王五", "2"}, {"张三三", "2"}, {"李四四", "2"}};

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_screen;
    }

    @Override
    public void init() {
        TitleUtils.setStatusTextColor(true, this);
        ButterKnife.bind(this);
        mRe.requestFocus();
        mSmartlayout.setEnableOverScrollDrag(true);
        mSmartlayout.setEnablePureScrollMode(true);
        mBotListBeans_repeat = new ArrayList<>();
        mBotListBeans_yiji = new ArrayList<>();
        mBotListBeans_erji = new ArrayList<>();
        mBotListBeans_city = new ArrayList<>();
        mBotListBeans_quyu = new ArrayList<>();
        mBotListBeans_shagnquan = new ArrayList<>();

    }


    @OnClick({R.id.imgv_back, R.id.screen_reset, R.id.screen_shaix, R.id.screen_chongm_tuozhan, R.id.screen_chongm_quany, R.id.screen_chongm_jingl, R.id.screen_yiji, R.id.screen_erji, R.id.screen_city, R.id.screen_quyu, R.id.screen_shangquan,
    R.id.screen_starttm,R.id.screen_endtm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgv_back:
                finish();
                break;
            case R.id.screen_reset:
                toReset();
                break;
            case R.id.screen_shaix:
                toJump();
                break;
            case R.id.screen_chongm_tuozhan:
                showRepeatDig(mScreenTuozhan);
                break;
            case R.id.screen_chongm_quany:
                showRepeatDig(mScreenShanghuquany);
                break;
            case R.id.screen_chongm_jingl:
                showRepeatDig(mScreenXiaoek);
                break;
            case R.id.screen_yiji:
                getFirsttierIndustry();
                break;
            case R.id.screen_erji:
                String h = mScreenYiji.getText().toString();
                if (TextUtils.isEmpty(h)) {
                    ToastShow("请先选择一级行业");
                } else {
                    mDialogBotList_erji.show();
                }
                break;
            case R.id.screen_city:
                getCity();
                break;
            case R.id.screen_quyu:
                String city = mScreenCity.getText().toString();
                if (TextUtils.isEmpty(city)) {
                    ToastShow("请先选择城市");
                } else {
                    mDialogBotList_quyu.show();
                }
                break;
            case R.id.screen_shangquan:
                String cityy = mScreenCity.getText().toString();
                String quyu = mScreenQuyu.getText().toString();
                if (TextUtils.isEmpty(cityy)) {
                    ToastShow("请先选择城市");
                } else if (TextUtils.isEmpty(quyu)) {
                    ToastShow("请先选择区域");
                } else {
                    mDialogBotList_shangquan.show();
                }
                break;
            case R.id.screen_starttm:
                showDateDialog(mStartTm, "2000-01-01 00:00", "2035-12-12 00:00");
                break;
            case R.id.screen_endtm:
                String zizhi_st = mStartTm.getText().toString();
                if (TextUtils.isEmpty(zizhi_st)) {
                    ToastShow("请先选择开始时间");
                    return;
                }
                showDateDialog(mEndTm, zizhi_st.replace(".", "-") + " 00:00", "2035-12-12 00:00");
                break;
        }
    }

    private void toReset() {
        mScreenName.setText("");
        mScreenTuozhan.setText("");
        mScreenShanghuquany.setText("");
        mScreenXiaoek.setText("");
        mScreenYiji.setText("");
        mScreenErji.setText("");
        mScreenCity.setText("");
        mScreenQuyu.setText("");
        mScreenShangquan.setText("");
        mStartTm.setText("");
        mEndTm.setText("");
    }

    private void toJump() {
        String name = mScreenName.getText().toString();
        String tuozh = mScreenTuozhan.getText().toString();
        String quany = mScreenShanghuquany.getText().toString();
        String xiaoer = mScreenXiaoek.getText().toString();
        String yiji = mScreenYiji.getText().toString();
        String erji = mScreenErji.getText().toString();
        String city = mScreenCity.getText().toString();
        String quyu = mScreenQuyu.getText().toString();
        String shangq = mScreenShangquan.getText().toString();
        String st_time = mStartTm.getText().toString();
        String ed_time = mEndTm.getText().toString();
        if (TextUtils.isEmpty(name) && TextUtils.isEmpty(tuozh) && TextUtils.isEmpty(quany) && TextUtils.isEmpty(xiaoer) && TextUtils.isEmpty(yiji)
                && TextUtils.isEmpty(erji) && TextUtils.isEmpty(city) && TextUtils.isEmpty(quyu) && TextUtils.isEmpty(shangq) && TextUtils.isEmpty(st_time) && TextUtils.isEmpty(ed_time)) {
            ToastShow("请至少选择一个筛选条件进行筛选");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("tuozh", tuozh);
        bundle.putString("quany", quany);
        bundle.putString("xiaoer", xiaoer);
        bundle.putString("yiji", yiji);
        bundle.putString("erji", erji);
        bundle.putString("city", city);
        bundle.putString("quyu", quyu);
        bundle.putString("shangq", shangq);
        bundle.putString("st_time", st_time);
        bundle.putString("ed_time", ed_time);
        Intent intent = new Intent(this, ScreenResultActivity.class);
        intent.putExtra("data", bundle);
        startActivity(intent);


    }

    /*--------------------------------各种数据进行获取----------------------------*/
    //全部一级行业
    private void getFirsttierIndustry() {
        final String s_yiji = mScreenYiji.getText().toString();
        Map<String, Object> map = new HashMap<>();
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).getFirsttierIndustry(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
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
                    mDialogBotList_yiji = new DialogBotList.Builder().title("请选择").botListBeanMap(mBotListBeans_yiji).builder(ScreenActivity.this);
                    mDialogBotList_yiji.setOnTextClickListener(new DialogBotList.OnTextClickListener() {
                        @Override
                        public void onTextClickListenerHis(String name, String what) {
                            super.onTextClickListenerHis(name, what);
                            String one_hang = mScreenYiji.getText().toString();
                            if (!one_hang.equals(name)) {
                                mScreenYiji.setText(name);
                                getSecondaryIndustry(what);
                                mScreenErji.setText("");
                                one_code = what;
//                                if(name.equals("菜市场")){
                                mScreenShangquan.setText("");
//                                }
                            }
                            if (name.equals("菜市场")) {
                                shangquan_type = "1";
                            } else {
                                shangquan_type = "0";
                            }
                            if (!TextUtils.isEmpty(quyu_code))
                                getBusinessCircle(city_code, quyu_code);
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
        final String s_hangye = mScreenErji.getText().toString();
        Map<String, Object> map = new HashMap<>();
        map.put("BigShopTypeID", id);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).getSecondaryIndustry(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
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
                    mDialogBotList_erji = new DialogBotList.Builder().title("请选择").botListBeanMap(mBotListBeans_erji).builder(ScreenActivity.this);
                    mDialogBotList_erji.setOnTextClickListener(new DialogBotList.OnTextClickListener() {
                        @Override
                        public void onTextClickListenerHis(String name, String what) {
                            super.onTextClickListenerHis(name, what);
                            mScreenErji.setText(name);
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
        final String city = mScreenCity.getText().toString();
        Map<String, Object> map = new HashMap<>();
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).getCity(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
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
                    mDialogBotList_city = new DialogBotList.Builder().title("请选择").botListBeanMap(mBotListBeans_city).builder(ScreenActivity.this);
                    mDialogBotList_city.setOnTextClickListener(new DialogBotList.OnTextClickListener() {
                        @Override
                        public void onTextClickListenerHis(String name, String what) {
                            super.onTextClickListenerHis(name, what);
                            String city = mScreenCity.getText().toString();
                            if (!city.equals(name)) {
                                mScreenCity.setText(name);
                                getDistrict(what);
                                mScreenQuyu.setText("");
                                mScreenShangquan.setText("");
                                quyu_code = "";
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
        final String quyu = mScreenQuyu.getText().toString();
        Map<String, Object> map = new HashMap<>();
        map.put("CityCode", city_code);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).getDistrict(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
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
                    mDialogBotList_quyu = new DialogBotList.Builder().title("请选择").botListBeanMap(mBotListBeans_quyu).builder(ScreenActivity.this);
                    mDialogBotList_quyu.setOnTextClickListener(new DialogBotList.OnTextClickListener() {
                        @Override
                        public void onTextClickListenerHis(String name, String what) {
                            super.onTextClickListenerHis(name, what);
                            String quyu = mScreenQuyu.getText().toString();
                            if (!quyu.equals(name)) {
                                mScreenQuyu.setText(name);
                                getBusinessCircle(city_code, what);
                                mScreenShangquan.setText("");
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
        final String shangquan = mScreenShangquan.getText().toString();
        Map<String, Object> map = new HashMap<>();
        map.put("CityCode", city_code);
        map.put("DistrictCode", di_code);
        map.put("type", shangquan_type);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).getBusinessCircle(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
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
                    mDialogBotList_shangquan = new DialogBotList.Builder().title("请选择").botListBeanMap(mBotListBeans_shagnquan).builder(ScreenActivity.this);
                    mDialogBotList_shangquan.setOnTextClickListener(new DialogBotList.OnTextClickListener() {
                        @Override
                        public void onTextClickListenerHis(String name, String what) {
                            super.onTextClickListenerHis(name, what);
                            mScreenShangquan.setText(name);
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

    private void showRepeatDig(final EditText mtv) {
        String str = mtv.getText().toString();
        KeyboardUtil.hideKeyboard(mScreenYiji);
        mRe.requestFocus();
        if (TextUtils.isEmpty(str)) {
            ToastShow("请输入查询内容");
            return;
        } else if (str.contains("张三") || str.contains("李四")) {
            mBotListBeans_repeat.clear();
            for (int i = 0; i < mRepeat.length; i++) {
                BotListBean botListBean = new BotListBean(mRepeat[i][0], str.equals(mRepeat[i][0]) ? true : false, mRepeat[i][1]);
                mBotListBeans_repeat.add(botListBean);
            }
            mDialogBotList_repeat = new DialogBotList.Builder().title("请选择").botListBeanMap(mBotListBeans_repeat).builder(ScreenActivity.this);
            mDialogBotList_repeat.setOnTextClickListener(new DialogBotList.OnTextClickListener() {
                @Override
                public void onTextClickListenerHis(String name, String what) {
                    super.onTextClickListenerHis(name, what);
                    mtv.setText(name);
                    mtv.setSelection(name.length());
                }
            });
            mDialogBotList_repeat.show();
        }else{
            ToastShow("未查询到重复名称");
        }
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
                mtv.setText(new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA).format(new Date(timestamp)));
//                mtv.setText(DateFormatUtils.long2Str(timestamp, false));
            }
        }, begin_tm + " 00:00", ed_tm);
        customDatePickerSt.setCanShowPreciseTime(false); // 不显示时和分
        customDatePickerSt.setScrollLoop(true); // 允许循环滚动
        customDatePickerSt.setCanShowAnim(true);//开启滚动动画
        customDatePickerSt.show(!TextUtils.isEmpty(time) ? time.replace(".", "-") + " 00:00" : new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA).format(new Date()));
    }


    /*----------------------------如下各种功能初始化及实现------------------------*/


}
