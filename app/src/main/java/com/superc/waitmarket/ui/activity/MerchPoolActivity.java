package com.superc.waitmarket.ui.activity;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
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
import com.superc.waitmarket.adapter.MerchPoolAdapter;
import com.superc.waitmarket.adapter.MerchSearchAdapter;
import com.superc.waitmarket.base.ApiService;
import com.superc.waitmarket.bean.BotListBean;
import com.superc.waitmarket.bean.MerPoolBean;
import com.superc.waitmarket.bean.SearchBean;
import com.superc.waitmarket.httputil.EncryPtionHttp;
import com.superc.waitmarket.httputil.EncryPtionUtil;
import com.superc.waitmarket.utils.BigDecimalUtils;
import com.superc.waitmarket.utils.dialog.MiddleDialog;
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

public class MerchPoolActivity extends BaseActivity {


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
    @BindView(R.id.merchpool_search_recy)
    RecyclerView mMerchpoolSerchRecy;
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
    private List<MerPoolBean.DataBean> mMapList;
    private MerchPoolAdapter mMerchPoolAdapter;
    private List<SearchBean.DataBean> mMapList_search;
    private MerchSearchAdapter mMerchSearchAdapter;
    private boolean is_search = false;
    private PopMerchWindow mPopMerchWindow;
    private PopMerchWindow mPopMerchWindow_city;
    private PopMerchWindow mPopMerchWindow_quyu;
    private List<BotListBean> mMapList_pop;
    private String[][] mStrings_state = new String[][]{{"全部状态", ""}, {"可领取", "3"}, {"可拓展", "1"}, {"不可拓", "2"}};
    private List<BotListBean> mBotListBeans_city;
    private List<BotListBean> mBotListBeans_quyu;
    private String mUser_id, mHezuo_code, city_code, quyu_code;
    private boolean is_toSearch = false;
    private String mRealname;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_merch_pool;
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        TitleUtils.setStatusTextColor(true, this);
        mUser_id = (String) ShareUtil.getInstance(this).get("user_id", "");
        mRealname = (String) ShareUtil.getInstance(this).get("realname", "");
        mMerchpoolCancel.requestFocus();
        mMapList = new ArrayList<>();
        mBotListBeans_city = new ArrayList<>();
        mBotListBeans_quyu = new ArrayList<>();
        mMerchPoolAdapter = new MerchPoolAdapter(this, mMapList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mMerchpoolRecy.setLayoutManager(linearLayoutManager);
        mMerchpoolRecy.setAdapter(mMerchPoolAdapter);
        mMapList_search = new ArrayList<>();
        mMerchSearchAdapter = new MerchSearchAdapter(this, mMapList_search);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mMerchpoolSerchRecy.setLayoutManager(manager);
        mMerchpoolSerchRecy.setAdapter(mMerchSearchAdapter);
        mMapList_pop = new ArrayList<>();


        mMerchpoolSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getData(true);

            }
        });
        mMerchpoolSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ++page;
                getData(true);
            }
        });
        mMerchPoolAdapter.setOnItemClickListener(new MerchPoolAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int pos) {
                MerPoolBean.DataBean map = mMapList.get(pos);
                String id = BigDecimalUtils.bigUtil(map.getShopid());
                toReceive(id);
            }
        });
        mMerchpoolEdt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mConstrainContent.setVisibility(View.GONE);
                mMerchpoolSerchRecy.setVisibility(View.VISIBLE);
                String search = mMerchpoolEdt.getText().toString();
                if (!TextUtils.isEmpty(search)) {
                    toSearch(search);
                }
                is_search = true;
                return false;
            }
        });
        mMerchpoolEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                toSearch(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mMerchSearchAdapter.setOnItemClickListener(new MerchSearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int pos) {
             /*   Map<String, Object> map = mMapList_search.get(pos);
                String name = (String) map.get("name");
                mMerchpoolEdt.setText(name);
                mMerchpoolEdt.setSelection(name.length());
                mConstrainContent.setVisibility(View.VISIBLE);
                mMerchpoolSerchRecy.setVisibility(View.GONE);
                mMerchpoolSmart.autoRefresh();
                mMerchpoolCancel.requestFocus();
                SoftInputUtils.hideSoftInput(MerchPoolActivity.this);
                is_search = false;*/
            }
        });
        mMerchpoolSmart.autoRefresh();
        /*getCity();*/
        getQuyu();
        initOnePop();
    }

    private void initOnePop() {
        for (int i = 0; i < mStrings_state.length; i++) {
            BotListBean botListBean = new BotListBean(mStrings_state[i][0], i == 0 ? true : false, mStrings_state[i][1]);
            mMapList_pop.add(botListBean);
        }
        mPopMerchWindow = new PopMerchWindow(this, mMapList_pop);
        mPopMerchWindow.setOnPopClickListener(new PopMerchWindow.OnPopClickListener() {
            @Override
            public void onPopClickListener(String con, String what) {
                String old = mMerchpoolHezuo.getText().toString();
                if (!con.equals(old)) {
                    mMerchpoolHezuo.setText(con);
                    mHezuo_code = what;
                    page = 1;
                    getData(true);
//                    mMerchpoolSmart.autoRefresh();
                }
            }
        });

        mPopMerchWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                merchpool_linear.setVisibility(View.GONE);
                mImg_one.setImageResource(R.drawable.icon_xiala);
                mMerchpoolHezuo.setTextColor(getResources().getColor(R.color.merchool_txt));
            }
        });

        mBotListBeans_city.clear();
        BotListBean botListBean = new BotListBean("天津市", false, "022");
        mBotListBeans_city.add(botListBean);
        mPopMerchWindow_city = new PopMerchWindow(MerchPoolActivity.this, mBotListBeans_city);
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
                    getData(true);
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
                String search_content = mMerchpoolEdt.getText().toString();
                if (TextUtils.isEmpty(search_content)) {
                    is_toSearch = false;
                } else {
                    is_toSearch = true;
                }
                is_search = false;
                getData(true);
                mConstrainContent.setVisibility(View.VISIBLE);
                mMerchpoolSerchRecy.setVisibility(View.GONE);
                SoftInputUtils.hideSoftInput(this);
                mMerchpoolCancel.requestFocus();
                break;
            case R.id.merchpool_cancel:
                mConstrainContent.setVisibility(View.VISIBLE);
                mMerchpoolSerchRecy.setVisibility(View.GONE);
                is_search = false;
                is_toSearch = false;
                mMerchpoolEdt.setText("");
                mMerchpoolSmart.autoRefresh();
                break;
            case R.id.merchpool_one:
                if (mPopMerchWindow != null) {
                    mPopMerchWindow.showAsDropDown(mLinea);
                    merchpool_linear.setVisibility(View.VISIBLE);
                    mImg_one.setImageResource(R.drawable.icon_shangla);
                    mMerchpoolHezuo.setTextColor(getResources().getColor(R.color.red));
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

    private void getData(boolean sear) {
        if (is_toSearch) {
            mTvSearchNum.setVisibility(View.VISIBLE);
            toSearch();
        } else {
            mTvSearchNum.setVisibility(View.GONE);
            togetNoSearch();
        }
    }

    private void togetNoSearch() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", mUser_id);
        if (!TextUtils.isEmpty(mHezuo_code)) {
            map.put("cooperationStatus", mHezuo_code);
        }
        map.put("city", city_code);
        if (!TextUtils.isEmpty(quyu_code)) {
            map.put("region", quyu_code);
        }
        map.put("currentPage", page);
        map.put("pageSize", 10);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).merchantPool(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                mMerchpoolSmart.finishRefresh();
                mMerchpoolSmart.finishLoadMore();
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    MerPoolBean merPoolBean = new Gson().fromJson(result.toString(), MerPoolBean.class);
                    if (page == 1) {
                        mMapList.clear();
                    }
                    mMapList.addAll(merPoolBean.getData());
                    mMerchPoolAdapter.notifyDataSetChanged();
                } else {
                    if (page == 1) {
                        mMapList.clear();
                        mMerchPoolAdapter.notifyDataSetChanged();
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

    /*搜索过来的检索*/
    private void toSearch() {
        String search_content = mMerchpoolEdt.getText().toString();
        Map<String, Object> map = new HashMap<>();
        map.put("userId", mUser_id);
        if (!TextUtils.isEmpty(mHezuo_code)) {
            map.put("cooperationStatus", mHezuo_code);
        }
        map.put("shopName", search_content);
        map.put("city", city_code);
        if (!TextUtils.isEmpty(quyu_code)) {
            map.put("region", quyu_code);
        }
        map.put("currentPage", page);
        map.put("pageSize", 10);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).merchantPoolSearchList(EncryPtionUtil.getInstance(this).toEncryption(map));
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
                    if (page == 1) {
                        mMapList.clear();
                    }
                    JSONArray list = data.getJSONArray("list");
                    if (list != null && list.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {
                            JSONObject jsonObject = list.getJSONObject(i);
                            MerPoolBean.DataBean bean = new MerPoolBean.DataBean();
                            bean.setAuditstatus(jsonObject.getString("auditstatus"));
                            bean.setShopid(jsonObject.getString("shopid"));
                            bean.setShopLogo(jsonObject.getString("ShopLogo"));
                            bean.setShopName(jsonObject.getString("ShopName"));
                            bean.setShopAddress(jsonObject.getString("ShopAddress"));
                            bean.setNum(jsonObject.getString("num"));
                            mMapList.add(bean);
                        }
                    }
                    mMerchPoolAdapter.notifyDataSetChanged();
                } else {
                    if (page == 1) {
                        mMapList.clear();
                        mMerchPoolAdapter.notifyDataSetChanged();
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

    private void toReceive(String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", mUser_id);
        map.put("shopId", id);
        map.put("developname", mRealname);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).merchantPoolSearchreceive(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                Log.d("qqq", result.toJSONString());
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    mMerchpoolSmart.autoRefresh();
                    new MiddleDialog.Builder(MerchPoolActivity.this).img_id(R.drawable.icon_chenggong).content(msg).build().show();
                } else {
                    if (!TextUtils.isEmpty(msg)) {
                        ToastShow(msg);
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

    private void toSearch(String content) {
        if (TextUtils.isEmpty(content)) {
            mMapList_search.clear();
            mMerchSearchAdapter.notifyDataSetChanged();
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("shopName", content);
            Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).merchantPoolSearch(EncryPtionUtil.getInstance(this).toEncryption(map));
            EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
                @Override
                public void onSuccessResult(JSONObject result) {
                    Log.d("qqq", result.toJSONString());
                    boolean code = result.getBoolean("code");
                    String msg = result.getString("message");
                    if (code) {
                        mMapList_search.clear();
                        SearchBean searchBean = new Gson().fromJson(result.toString(), SearchBean.class);
                        mMapList_search.addAll(searchBean.getData());
                        mMerchSearchAdapter.notifyDataSetChanged();
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
                    mPopMerchWindow_city = new PopMerchWindow(MerchPoolActivity.this, mBotListBeans_city);
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
                                getData(true);
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

                    mPopMerchWindow_quyu = new PopMerchWindow(MerchPoolActivity.this, mBotListBeans_quyu);
                    mPopMerchWindow_quyu.setOnPopClickListener(new PopMerchWindow.OnPopClickListener() {
                        @Override
                        public void onPopClickListener(String con, String what) {
                            String old = mMerchpoolQuyu.getText().toString();
                            if (!con.equals(old)) {
                                mMerchpoolQuyu.setText(con);
                                quyu_code = what;
                                page = 1;
                                getData(true);
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


    @Override
    public void onBackPressed() {
        if (is_search) {
            mConstrainContent.setVisibility(View.VISIBLE);
            mMerchpoolSerchRecy.setVisibility(View.GONE);
            is_search = false;
        } else {
            super.onBackPressed();
        }


    }
}
