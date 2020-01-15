package com.superc.waitmarket.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

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
import com.superc.waitmarket.adapter.BudnesTabAdapter;
import com.superc.waitmarket.base.ApiService;
import com.superc.waitmarket.base.WaitApplication;
import com.superc.waitmarket.bean.BusCountBean;
import com.superc.waitmarket.bean.SaryIndustryBean;
import com.superc.waitmarket.bean.ShopManageBean;
import com.superc.waitmarket.httputil.EncryPtionHttp;
import com.superc.waitmarket.httputil.EncryPtionUtil;
import com.superc.waitmarket.ui.activity.EdtDetailActivity;
import com.superc.waitmarket.ui.activity.ManaPbActivity;
import com.superc.waitmarket.ui.activity.MerchPoolActivity;
import com.superc.waitmarket.ui.activity.MerchantDetailActivity;
import com.superc.waitmarket.utils.BigDecimalUtils;
import com.superc.waitmarket.utils.ScrollSpeedLinearLayoutManger;
import com.superc.waitmarket.utils.dialog.JihuoDialog;
import com.superc.yyfflibrary.base.BaseFragment;
import com.superc.yyfflibrary.utils.ShareUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusnesFragment extends BaseFragment {


    @BindView(R.id.busnes_txt_weihunum)
    TextView mBusnesTxtWeihunum;
    @BindView(R.id.busnes_shenhenum)
    TextView mBusnesShenhenum;
    @BindView(R.id.busnes_jihuonum)
    TextView mBusnesJihuonum;
    @BindView(R.id.busnes_recy_tab)
    RecyclerView mBusnesRecyTab;
    @BindView(R.id.busnes_recy_content)
    RecyclerView mBusnesRecyContent;
    @BindView(R.id.busnes_what)
    TextView mTv_what;
    @BindView(R.id.user_check_smart)
    SmartRefreshLayout mSmartRefreshLayout;

    Unbinder unbinder;
    private BudnesTabAdapter mBudnesTabAdapter;
    private BudnesContentAdapter mBudnesContentAdapter;
    private List<SaryIndustryBean.DataBean.SecondaryIndustryBean> mMapList_tab;
    private List<ShopManageBean.DataBean.ListBean> mMapList_content;
    private List<Map<String, Object>> mMapList_jihuo;
    private JihuoDialog mJihuoDialog;
    private int mWidth = 0;
    private int mScreenWidth;
    private int page = 1;
    private String type = "0", sm_type = "";
    private String mUser_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_busnes, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void init() {
        getAndroiodScreenProperty();
        ScrollSpeedLinearLayoutManger linearLayoutManager = new ScrollSpeedLinearLayoutManger(this.getActivity());
        linearLayoutManager.setOrientation(ScrollSpeedLinearLayoutManger.HORIZONTAL);
        mBusnesRecyTab.setLayoutManager(linearLayoutManager);
        mMapList_tab = new ArrayList<>();
        mBudnesTabAdapter = new BudnesTabAdapter(this.getActivity(), mMapList_tab);
        mBusnesRecyTab.setAdapter(mBudnesTabAdapter);
        mMapList_content = new ArrayList<>();
        LinearLayoutManager linearLayoutMan = new LinearLayoutManager(this.getActivity());
        mBusnesRecyContent.setLayoutManager(linearLayoutMan);
        mBudnesContentAdapter = new BudnesContentAdapter(this.getActivity(), mMapList_content);
        mBusnesRecyContent.setAdapter(mBudnesContentAdapter);
        mBudnesContentAdapter.setOnItemClickListener(new BudnesContentAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int pos) {
                ShopManageBean.DataBean.ListBean bean = mMapList_content.get(pos);
                ShareUtil.getInstance(WaitApplication.getInstance()).put("edtdetail_id", BigDecimalUtils.bigUtil(bean.getShopid()));
                ShareUtil.getInstance(WaitApplication.getInstance()).put("channel", "2");
                statActivity(MerchantDetailActivity.class);
            }
        });
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getCount();
                page = 1;
                getData();
                getOnceTab();

            }
        });
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ++page;
                getData();
            }
        });
        mBudnesTabAdapter.setOnItemClickListener(new BudnesTabAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int pos, float x) {
                if (x > (mWidth / 2)) {
                    if (pos != (mMapList_tab.size() - 1)) {
                        mBusnesRecyTab.smoothScrollToPosition(pos + 1);
                    }
                } else {
                    if (pos != 0) {
                        mBusnesRecyTab.smoothScrollToPosition(pos - 1);
                    }
                }

                mTv_what.setVisibility(pos == 0 ? View.GONE : View.VISIBLE);
                for (int i = 0; i < mMapList_tab.size(); i++) {
                    if (pos == i) {
                        mMapList_tab.get(i).setIs_check(true);
                    } else {
                        mMapList_tab.get(i).setIs_check(false);
                    }
                }
                if (pos != 0) {
                    initSmallPop(mMapList_tab.get(pos).getList());
                }
                type = mMapList_tab.get(pos).getType();
                sm_type = "";
                mBudnesTabAdapter.notifyDataSetChanged();
                togetDataA();
//                mSmartRefreshLayout.autoRefresh();
            }
        });

        initJihDig();
        getOnceTab();
        getCount();
    }

    private void togetDataA() {
        getCount();
        page = 1;
        getData();
    }

    private void initJihDig() {

    }

    @OnClick({R.id.busnes_shanghuchi, R.id.busnes_rela_xinjian, R.id.busnes_rela_weihu, R.id.busnes_rela_shenhe, R.id.busnes_rela_jihuo, R.id.busnes_what,
            R.id.busnes_weipai})
    public void onClick(View view) {
        Intent intent = new Intent(this.getActivity(), ManaPbActivity.class);
        switch (view.getId()) {
            case R.id.busnes_shanghuchi:
                statActivity(MerchPoolActivity.class);
                break;
            case R.id.busnes_rela_xinjian:
                ShareUtil.getInstance(getActivity()).put("edtdetail_id", "");
                ShareUtil.getInstance(getActivity()).put("is_creat", "0");
                ShareUtil.getInstance(getActivity()).put("can_commit", false);
                ShareUtil.getInstance(getActivity()).put("status", "0");
                statActivity(EdtDetailActivity.class);
                break;
            case R.id.busnes_rela_weihu:
                intent.putExtra("what", "0");
                startActivity(intent);
                break;
            case R.id.busnes_rela_shenhe:
                intent.putExtra("what", "1");
                startActivity(intent);
                break;
            case R.id.busnes_rela_jihuo:
                intent.putExtra("what", "2");
                startActivity(intent);
                break;
            case R.id.busnes_what:
                mJihuoDialog.show();
                break;
            case R.id.busnes_weipai:
                intent.putExtra("what", "3");
                startActivity(intent);
                break;
        }
    }

    public void getData() {
        mUser_id = (String) ShareUtil.getInstance(this.getActivity()).get("user_id", "");
        Map<String, Object> map = new HashMap<>();
        map.put("userId", mUser_id);
        map.put("type", type);//行业type  0已激活商家
        if (!TextUtils.isEmpty(sm_type)) {
            map.put("BigShopTypeID", sm_type);
        }
        map.put("currentPage", page);
        map.put("pageSize", 10);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).shopManagerIndex(EncryPtionUtil.getInstance(getActivity()).toEncryption(map));
        EncryPtionHttp.getInstance(getActivity()).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                mSmartRefreshLayout.finishRefresh();
                mSmartRefreshLayout.finishLoadMore();
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
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
                mSmartRefreshLayout.finishRefresh();
                mSmartRefreshLayout.finishLoadMore();
                ToastShow("接口访问异常" + httpThrowable.message);
                Log.d("qqq", httpThrowable.message);
            }
        });

    }

    //    获取横向列表
    public void getOnceTab() {
        mUser_id = (String) ShareUtil.getInstance(WaitApplication.getInstance()).get("user_id", "");
        type = "0";
        sm_type = "";
        mTv_what.setVisibility(type.equals("0") ? View.GONE : View.VISIBLE);
        Map<String, Object> map = new HashMap<>();
        map.put("userId", mUser_id);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).secondaryIndustry(EncryPtionUtil.getInstance(getActivity()).toEncryption(map));
        EncryPtionHttp.getInstance(getActivity()).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                Log.d("qqq", result.toJSONString());
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    SaryIndustryBean bean = new Gson().fromJson(result.toString(), SaryIndustryBean.class);
                    if (bean.getData() != null) {
                        mMapList_tab.clear();
                        SaryIndustryBean.DataBean.SecondaryIndustryBean secondOne = new SaryIndustryBean.DataBean.SecondaryIndustryBean();
                        secondOne.setIs_check(true);
                        secondOne.setType("0");
                        secondOne.setSum(BigDecimalUtils.bigUtil(bean.getData().getActivatedCount()));
                        secondOne.setTypeName("已激活商家");
                        mMapList_tab.add(secondOne);
                        if (bean.getData().getSecondaryIndustry() != null) {
                            for (int i = 0; i < bean.getData().getSecondaryIndustry().size(); i++) {
                                SaryIndustryBean.DataBean.SecondaryIndustryBean secondaryIndustryBean = bean.getData().getSecondaryIndustry().get(i);
                                secondaryIndustryBean.setIs_check(false);
                                mMapList_tab.add(secondaryIndustryBean);
                            }
                        }
                    }
                    mBudnesTabAdapter.notifyDataSetChanged();
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

    //    获取上方数量
    public void getCount() {
        mUser_id = (String) ShareUtil.getInstance(WaitApplication.getInstance()).get("user_id", "");
        Map<String, Object> map = new HashMap<>();
        map.put("userId", mUser_id);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).getCount(EncryPtionUtil.getInstance(getActivity()).toEncryption(map));
        EncryPtionHttp.getInstance(getActivity()).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    BusCountBean busCountBean = new Gson().fromJson(result.toString(), BusCountBean.class);
                    if (busCountBean != null) {
                        mBusnesTxtWeihunum.setText("（" + busCountBean.getData().getCountMap().getMaintenance() + "家）");
                        mBusnesShenhenum.setText("（" + busCountBean.getData().getCountMap().getPendingReview() + "家）");
                        mBusnesJihuonum.setText("（" + busCountBean.getData().getCountMap().getPendingActivation() + "家）");
                    }
                } else {
                    mBusnesTxtWeihunum.setText("（- -家）");
                    mBusnesShenhenum.setText("（- -家）");
                    mBusnesJihuonum.setText("（- -家）");
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

    private void initSmallPop(List<SaryIndustryBean.DataBean.SecondaryIndustryBean.ListBean> listBeans) {
        mTv_what.setText("全部");
        mMapList_jihuo = new ArrayList<>();
        Map<String, Object> map_first = new HashMap<>();
        map_first.put("content", "全部");
        map_first.put("small_id", "");
        map_first.put("is_check", true);
        mMapList_jihuo.add(map_first);
        for (int i = 0; i < listBeans.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("content", listBeans.get(i).getTypeName() + "(" + BigDecimalUtils.bigUtil(listBeans.get(i).getCount()) + ")");
            map.put("small_id", BigDecimalUtils.bigUtil(listBeans.get(i).getSMTypeID()));
            map.put("is_check", false);
            mMapList_jihuo.add(map);
        }
        mJihuoDialog = new JihuoDialog(this.getActivity(), mMapList_jihuo);
        mJihuoDialog.setOnFinishListener(new JihuoDialog.OnFinishListener() {
            @Override
            public void onFinishListener(int pos, String what) {
                Map<String, Object> map = mMapList_jihuo.get(pos);
                String small_id = (String) map.get("small_id");
                if (what.contains("(")) {
                    mTv_what.setText(what.substring(0, what.indexOf("(")));
                } else {
                    mTv_what.setText(what);
                }
                for (int i = 0; i < mMapList_jihuo.size(); i++) {
                    if (i == pos) {
                        mMapList_jihuo.get(i).put("is_check", true);
                    } else {
                        mMapList_jihuo.get(i).put("is_check", false);
                    }
                }
                mJihuoDialog.setMapList(mMapList_jihuo);
                if (!TextUtils.isEmpty(small_id)) {
                    if (!small_id.equals(sm_type)) {
                        sm_type = small_id;
                        togetDataA();
//                        mSmartRefreshLayout.autoRefresh();
                    }
                } else {
                    sm_type = small_id;
                    togetDataA();
//                    mSmartRefreshLayout.autoRefresh();
                }
            }
        });


    }


    /*获取屏幕参数*/
    public void getAndroiodScreenProperty() {
        WindowManager wm = (WindowManager) this.getActivity().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        // 屏幕宽度（像素）
        mWidth = dm.widthPixels;
        int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        // 屏幕宽度(dp)
        mScreenWidth = (int) (mWidth / density);
        int screenHeight = (int) (height / density);// 屏幕高度(dp)
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
