package com.superc.waitmarket.ui.activity;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.throwable.HttpThrowable;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.superc.waitmarket.R;
import com.superc.waitmarket.adapter.BudnesContentAdapter;
import com.superc.waitmarket.base.ApiService;
import com.superc.waitmarket.base.WaitApplication;
import com.superc.waitmarket.bean.BotListBean;
import com.superc.waitmarket.bean.SaryIndustryBean;
import com.superc.waitmarket.bean.SearchBean;
import com.superc.waitmarket.bean.ShopManageBean;
import com.superc.waitmarket.httputil.EncryPtionHttp;
import com.superc.waitmarket.httputil.EncryPtionUtil;
import com.superc.waitmarket.utils.BigDecimalUtils;
import com.superc.waitmarket.utils.dialog.JihuoDialog;
import com.superc.waitmarket.utils.pop.PopMerchWindow;
import com.superc.waitmarket.utils.vciv.SoftInputUtils;
import com.superc.yyfflibrary.base.BaseActivity;
import com.superc.yyfflibrary.utils.ShareUtil;
import com.superc.yyfflibrary.utils.titlebar.TitleUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;

public class SerachCouActivity extends BaseActivity {


    @BindView(R.id.merchpool_edt)
    EditText mMerchpoolEdt;
    @BindView(R.id.merchpool_hezuo)
    TextView mMerchpoolHezuo;
    @BindView(R.id.merchpool_posi)
    TextView mMerchpoolPosi;
    @BindView(R.id.merchpool_quyu)
    TextView mMerchpoolQuyu;
    @BindView(R.id.merchpool_cancel)
    TextView mMerchpoolCancel;
    @BindView(R.id.merchpool_recy)
    RecyclerView mMerchpoolRecy;
    @BindView(R.id.merchpool_smart)
    SmartRefreshLayout mMerchpoolSmart;
    @BindView(R.id.constraintLayout)
    ConstraintLayout mConstrainContent;
    @BindView(R.id.linearLayout2)
    LinearLayout mLinea;
    @BindView(R.id.merchpool_linear)
    LinearLayout merchpool_linear;
    @BindView(R.id.merchpool_searchresultnum)
    TextView mTvSearchNum;
    @BindView(R.id.merchpool_imgone)
    ImageView mImg_one;
    @BindView(R.id.merchpool_imgtwo)
    ImageView mImg_two;
    @BindView(R.id.merchpool_imgthree)
    ImageView mImg_three;

    private int page = 1;
    private List<SearchBean.DataBean> mMapList_search;
    private boolean is_search = false;
    private PopMerchWindow mPopMerchWindow_city;
    private PopMerchWindow mPopMerchWindow_quyu;
    private List<BotListBean> mMapList_pop;
    private List<BotListBean> mBotListBeans_city;
    private List<BotListBean> mBotListBeans_quyu;
    private String mUser_id, mHezuo_code, city_code, quyu_code;
    private String mRealname, bg_type = "0", sm_type = "";
    private JihuoDialog mJihuoDialog_once, mJihuoDialog_twice;
    private List<Map<String, Object>> mJhDig_mapOnce, mJhDig_mapTwice;
    private String hangye_str = "行业";
    private BudnesContentAdapter mBudnesContentAdapter;
    private List<ShopManageBean.DataBean.ListBean> mMapList_content;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_serach_cou;
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        TitleUtils.setStatusTextColor(true, this);
        mUser_id = (String) ShareUtil.getInstance(this).get("user_id", "");
        mRealname = (String) ShareUtil.getInstance(this).get("realname", "");
        mMerchpoolCancel.requestFocus();
        mBotListBeans_city = new ArrayList<>();
        mBotListBeans_quyu = new ArrayList<>();
        mJhDig_mapOnce = new ArrayList<>();
        mJhDig_mapTwice = new ArrayList<>();
        mMapList_content = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mMerchpoolRecy.setLayoutManager(linearLayoutManager);
        mMapList_search = new ArrayList<>();
        mMapList_pop = new ArrayList<>();


        mBudnesContentAdapter = new BudnesContentAdapter(this, mMapList_content);
        mMerchpoolRecy.setAdapter(mBudnesContentAdapter);
        mBudnesContentAdapter.setOnItemClickListener(new BudnesContentAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int pos) {
                ShopManageBean.DataBean.ListBean bean = mMapList_content.get(pos);
                ShareUtil.getInstance(WaitApplication.getInstance()).put("edtdetail_id", BigDecimalUtils.bigUtil(bean.getShopid()));
                ShareUtil.getInstance(WaitApplication.getInstance()).put("channel", "2");
                statActivity(MerchantDetailActivity.class);
            }
        });

        mMerchpoolSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                togetNoSearch();
                ;

            }
        });
        mMerchpoolSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ++page;
                togetNoSearch();
                ;
            }
        });
        mMerchpoolSmart.autoRefresh();
        /*getCity();*/
        getQuyu();
        initOnePop();
        getOnceTab();
    }

    private void initOnePop() {

        mBotListBeans_city.clear();
        BotListBean botListBean = new BotListBean("天津市", false, "022");
        mBotListBeans_city.add(botListBean);
        mPopMerchWindow_city = new PopMerchWindow(SerachCouActivity.this, mBotListBeans_city);
        mPopMerchWindow_city.setOnPopClickListener(new PopMerchWindow.OnPopClickListener() {
            @Override
            public void onPopClickListener(String con, String what) {
                String old_posi = mMerchpoolPosi.getText().toString();
                if (!con.equals(old_posi)) {
                    mMerchpoolPosi.setText(con);
                    city_code = what;
                    getDistrict(what);
                    mMerchpoolQuyu.setText("区域");
                    page = 1;
                    togetNoSearch();
//                    mMerchpoolSmart.autoRefresh();
                }
            }
        });

        mPopMerchWindow_city.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                merchpool_linear.setVisibility(View.GONE);
                mImg_two.setImageResource(R.drawable.icon_xiala);
                mMerchpoolPosi.setTextColor(getResources().getColor(R.color.merchool_txt));
            }
        });

    }

    @OnClick({R.id.merchpool_search, R.id.merchpool_cancel, R.id.merchpool_one, R.id.merchpool_two, R.id.merchpool_three, R.id.imgv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgv_back:
                finish();
                break;
            case R.id.merchpool_search:
                mMerchpoolSmart.autoRefresh();
                SoftInputUtils.hideSoftInput(this);
                mMerchpoolCancel.requestFocus();
                break;
            case R.id.merchpool_cancel:
                mMerchpoolEdt.setText("");
                mMerchpoolSmart.autoRefresh();
                break;
            case R.id.merchpool_one:
                if (mJihuoDialog_once != null) {
                    mImg_one.setImageResource(R.drawable.icon_shangla);
                    mMerchpoolHezuo.setTextColor(getResources().getColor(R.color.red));
                    mJihuoDialog_once.show();
                }
                break;
            case R.id.merchpool_two:
                if (mPopMerchWindow_city != null) {
                    mPopMerchWindow_city.showAsDropDown(mLinea);
                    merchpool_linear.setVisibility(View.VISIBLE);
                    mImg_two.setImageResource(R.drawable.icon_shangla);
                    mMerchpoolPosi.setTextColor(getResources().getColor(R.color.red));
                }
                break;
            case R.id.merchpool_three:
                String city = mMerchpoolPosi.getText().toString();
                if (TextUtils.isEmpty(city) || city.equals("城市")) {
                    ToastShow("请先选择城市");
                } else if (mPopMerchWindow_quyu != null) {
                    mPopMerchWindow_quyu.showAsDropDown(mLinea);
                    merchpool_linear.setVisibility(View.VISIBLE);
                    mImg_three.setImageResource(R.drawable.icon_shangla);
                    mMerchpoolQuyu.setTextColor(getResources().getColor(R.color.red));
                }
                break;
        }
    }

    private void togetNoSearch() {
        Map<String, Object> map = new HashMap<>();
        String search_content = mMerchpoolEdt.getText().toString();
        if (TextUtils.isEmpty(search_content) && TextUtils.isEmpty(city_code) && TextUtils.isEmpty(quyu_code) &&
                TextUtils.isEmpty(sm_type) && bg_type.equals("0")) {
            mTvSearchNum.setVisibility(View.GONE);
        } else {
            mTvSearchNum.setVisibility(View.VISIBLE);
        }
        map.put("userId", mUser_id);
        if(!TextUtils.isEmpty(search_content)){
            map.put("shopName",search_content);
        }
        if(!TextUtils.isEmpty(city_code)) {
            map.put("city", city_code);
        }
        if (!TextUtils.isEmpty(quyu_code)) {
            map.put("region", quyu_code);
        }
        if(!TextUtils.isEmpty(bg_type)) {
            map.put("type", bg_type);//行业type  0已激活商家
        }
        if (!TextUtils.isEmpty(sm_type)) {
            map.put("BigShopTypeID", sm_type);
        }
        map.put("currentPage", page);
        map.put("pageSize", 10);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).shopManagerIndexsar(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                mMerchpoolSmart.finishRefresh();
                mMerchpoolSmart.finishLoadMore();
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    JSONObject data = result.getJSONObject("data");
                    mTvSearchNum.setText("共" + BigDecimalUtils.bigUtil(data.getString("count")) + "家符合要求商家");
                    ShopManageBean bean = new Gson().fromJson(result.toString(), ShopManageBean.class);
                    if (page == 1) {
                        mMapList_content.clear();
                    }
                    mMapList_content.addAll(bean.getData().getList());
                    mBudnesContentAdapter.notifyDataSetChanged();
                } else {
                    if (page == 1) {
                        mMapList_content.clear();
                        mBudnesContentAdapter.notifyDataSetChanged();
                    }
                }
                if (!TextUtils.isEmpty(msg)) {
                    ToastShow(msg);
                }
            }

            @Override
            public void onErrorResult(HttpThrowable httpThrowable) {
                mMerchpoolSmart.finishRefresh();
                mMerchpoolSmart.finishLoadMore();
                ToastShow("接口访问异常" + httpThrowable.message);
                Log.d("qqq", httpThrowable.message);
            }
        });

    }

    /*获取城市*/
    private void getCity() {
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
                        BotListBean botListBean = new BotListBean(cityName, false, jsonObject.getString("CityCode"));
                        mBotListBeans_city.add(botListBean);
                    }
                    mPopMerchWindow_city = new PopMerchWindow(SerachCouActivity.this, mBotListBeans_city);
                    mPopMerchWindow_city.setOnPopClickListener(new PopMerchWindow.OnPopClickListener() {
                        @Override
                        public void onPopClickListener(String con, String what) {
                            String old_posi = mMerchpoolPosi.getText().toString();
                            if (!con.equals(old_posi)) {
                                mMerchpoolPosi.setText(con);
                                city_code = what;
                                getDistrict(what);
                                mMerchpoolQuyu.setText("");
                                page = 1;
                                togetNoSearch();
                                ;
//                                mMerchpoolSmart.autoRefresh();
                            }
                        }
                    });

                    mPopMerchWindow_city.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            merchpool_linear.setVisibility(View.GONE);
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

    private void getQuyu() {
        Map<String, Object> map = new HashMap<>();
        map.put("cityCode", "022");
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).merchantPoolStatusBar(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                Log.d("qqq", result.toJSONString());
                final boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    mBotListBeans_quyu.clear();
                    BotListBean botListB = new BotListBean("全部区域", true, "");
                    mBotListBeans_quyu.add(botListB);
                    JSONArray data = result.getJSONArray("data");
                    for (int i = 0; i < data.size(); i++) {
                        JSONObject jsonObject = data.getJSONObject(i);
                        String districtName = jsonObject.getString("DistrictName");
                        BotListBean botListBean = new BotListBean(districtName, false, jsonObject.getString("DistrictCode"));
                        mBotListBeans_quyu.add(botListBean);
                    }

                    mPopMerchWindow_quyu = new PopMerchWindow(SerachCouActivity.this, mBotListBeans_quyu);
                    mPopMerchWindow_quyu.setOnPopClickListener(new PopMerchWindow.OnPopClickListener() {
                        @Override
                        public void onPopClickListener(String con, String what) {
                            String old = mMerchpoolQuyu.getText().toString();
                            if (!con.equals(old)) {
                                mMerchpoolQuyu.setText(con);
                                quyu_code = what;
                                page = 1;
                                togetNoSearch();
                                ;
//                                mMerchpoolSmart.autoRefresh();
                            }

                        }
                    });

                    mPopMerchWindow_quyu.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            merchpool_linear.setVisibility(View.GONE);
                            mImg_three.setImageResource(R.drawable.icon_xiala);
                            mMerchpoolQuyu.setTextColor(getResources().getColor(R.color.merchool_txt));
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

    /*获取区域*/
    private void getDistrict(final String city_code) {
        /*final String quyu = mMerchpoolQuyu.getText().toString();
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

                    mPopMerchWindow_quyu = new PopMerchWindow(MerchPoolActivity.this, mBotListBeans_quyu);
                    mPopMerchWindow_quyu.setOnPopClickListener(new PopMerchWindow.OnPopClickListener() {
                        @Override
                        public void onPopClickListener(String con, String what) {
                            String old = mMerchpoolQuyu.getText().toString();
                            if (!con.equals(old)) {
                                mMerchpoolQuyu.setText(con);
                                quyu_code = what;
                                mMerchpoolSmart.autoRefresh();
                            }

                        }
                    });

                    mPopMerchWindow_quyu.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            merchpool_linear.setVisibility(View.GONE);
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
        });*/
    }

    //    获取横向列表
    public void getOnceTab() {
        mMerchpoolHezuo.setText("行业");
        mUser_id = (String) ShareUtil.getInstance(WaitApplication.getInstance()).get("user_id", "");
        bg_type = "0";
        sm_type = "";
        Map<String, Object> map = new HashMap<>();
        map.put("userId", mUser_id);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).secondaryIndustry(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                Log.d("qqq", result.toJSONString());
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    SaryIndustryBean bean = new Gson().fromJson(result.toString(), SaryIndustryBean.class);
                    if (bean.getData() != null) {
                        mJhDig_mapOnce.clear();
                        Map<String, Object> map_oo = new HashMap<>();
                        map_oo.put("content", "已激活商家" + "(" + BigDecimalUtils.bigUtil(bean.getData().getActivatedCount()) + ")");
                        map_oo.put("type", "0");
                        map_oo.put("is_check", false);
                        mJhDig_mapOnce.add(map_oo);
                        if (bean.getData().getSecondaryIndustry() != null) {
                            for (int i = 0; i < bean.getData().getSecondaryIndustry().size(); i++) {
                                SaryIndustryBean.DataBean.SecondaryIndustryBean secondaryIndustryBean = bean.getData().getSecondaryIndustry().get(i);
                                Map<String, Object> map = new HashMap<>();
                                map.put("content", secondaryIndustryBean.getTypeName() + "(" + BigDecimalUtils.bigUtil(secondaryIndustryBean.getSum()) + ")");
                                map.put("type", secondaryIndustryBean.getType());
                                map.put("is_check", false);
                                map.put("data", new Gson().toJson(secondaryIndustryBean));
                                mJhDig_mapOnce.add(map);
                            }
                        }
                    }
                    mJihuoDialog_once = new JihuoDialog(SerachCouActivity.this, mJhDig_mapOnce,"请选择一级行业");
                    mJihuoDialog_once.setOnFinishListener(new JihuoDialog.OnFinishListener() {
                        @Override
                        public void onFinishListener(int pos, String what) {
                            Map<String, Object> map = mJhDig_mapOnce.get(pos);
                            String type = (String) map.get("type");
                            if (what.contains("(")) {
                                hangye_str = what.substring(0, what.indexOf("("));
                            } else {
                                hangye_str = what;
                            }
                            mMerchpoolHezuo.setText(hangye_str);
                            for (int i = 0; i < mJhDig_mapOnce.size(); i++) {
                                if (i == pos) {
                                    mJhDig_mapOnce.get(i).put("is_check", true);
                                } else {
                                    mJhDig_mapOnce.get(i).put("is_check", false);
                                }
                            }
                            mJihuoDialog_once.setMapList(mJhDig_mapOnce);
                            if (!TextUtils.isEmpty(type)) {
                                if (!type.equals(bg_type)) {
                                    bg_type = type;
                                }
                            } else {
                                bg_type = type;
                            }
                            if (pos == 0) {
                                sm_type = "";
                                mImg_one.setImageResource(R.drawable.icon_xiala);
                                mMerchpoolHezuo.setTextColor(getResources().getColor(R.color.merchool_txt));
                                mMerchpoolSmart.autoRefresh();
                            } else {
                                initSmallPop((String) map.get("data"));
                            }
                        }
                    });
                    mJihuoDialog_once.setOnChaClickListener(new JihuoDialog.OnChaClickListener() {
                        @Override
                        public void onChaClickListener(int pos, String what) {
                            mImg_one.setImageResource(R.drawable.icon_xiala);
                            mMerchpoolHezuo.setTextColor(getResources().getColor(R.color.merchool_txt));
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

    private void initSmallPop(String stts) {
        SaryIndustryBean.DataBean.SecondaryIndustryBean secondaryIndustryBean = new Gson().fromJson(stts, SaryIndustryBean.DataBean.SecondaryIndustryBean.class);
        List<SaryIndustryBean.DataBean.SecondaryIndustryBean.ListBean> listBeans = secondaryIndustryBean.getList();
        mJhDig_mapTwice.clear();
        Map<String, Object> map_first = new HashMap<>();
        map_first.put("content", "全部");
        map_first.put("small_id", "");
        map_first.put("is_check", true);
        mJhDig_mapTwice.add(map_first);
        for (int i = 0; i < listBeans.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("content", listBeans.get(i).getTypeName() + "(" + BigDecimalUtils.bigUtil(listBeans.get(i).getCount()) + ")");
            map.put("small_id", BigDecimalUtils.bigUtil(listBeans.get(i).getSMTypeID()));
            map.put("is_check", false);
            mJhDig_mapTwice.add(map);
        }
        mJihuoDialog_twice = new JihuoDialog(this, mJhDig_mapTwice,"请选择二级行业");
        mJihuoDialog_twice.show();
        mJihuoDialog_twice.setOnFinishListener(new JihuoDialog.OnFinishListener() {
            @Override
            public void onFinishListener(int pos, String what) {
                toPanD(pos,what);
            }
        });
        mJihuoDialog_twice.setOnChaClickListener(new JihuoDialog.OnChaClickListener() {
            @Override
            public void onChaClickListener(int pos, String what) {
                toPanD(pos,what);
            }
        });

    }
    private void toPanD(int pos, String what){
        mImg_one.setImageResource(R.drawable.icon_xiala);
        mMerchpoolHezuo.setTextColor(getResources().getColor(R.color.merchool_txt));
        Map<String, Object> map = mJhDig_mapTwice.get(pos);
        String small_id = (String) map.get("small_id");
        if (what.contains("(")) {
            hangye_str += "•" + what.substring(0, what.indexOf("("));
        } else {
            hangye_str += "•" + what;
        }
        mMerchpoolHezuo.setText(hangye_str);
        for (int i = 0; i < mJhDig_mapTwice.size(); i++) {
            if (i == pos) {
                mJhDig_mapTwice.get(i).put("is_check", true);
            } else {
                mJhDig_mapTwice.get(i).put("is_check", false);
            }
        }
        mJihuoDialog_twice.setMapList(mJhDig_mapTwice);
        if (!TextUtils.isEmpty(small_id)) {
            if (!small_id.equals(sm_type)) {
                sm_type = small_id;
                mMerchpoolSmart.autoRefresh();
            }
        } else {
            sm_type = small_id;
            mMerchpoolSmart.autoRefresh();
        }
    }
}
