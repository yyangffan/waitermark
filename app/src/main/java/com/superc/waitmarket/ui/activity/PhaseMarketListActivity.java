package com.superc.waitmarket.ui.activity;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.superc.waitmarket.R;
import com.superc.waitmarket.adapter.PhaseMarketListAdapter;
import com.superc.waitmarket.bean.MarketDeatilBean;
import com.superc.waitmarket.bean.ProductBean;
import com.superc.waitmarket.utils.dialog.DialogDetailList;
import com.superc.waitmarket.utils.dialog.PhaseRemindDialog;
import com.superc.yyfflibrary.base.BaseActivity;
import com.superc.yyfflibrary.utils.ShareUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhaseMarketListActivity extends BaseActivity {
    private static final String TAG = "PhaseMarketListActivity";
    @BindView(R.id.phase_marketli_linear)
    LinearLayout mPhaseMarketliLinear;
    @BindView(R.id.phase_marketlist_content)
    TextView mPhaseMarketlistContent;
    @BindView(R.id.phase_marketlist_what)
    TextView mPhaseMarketlistWhat;
    @BindView(R.id.phase_marketlist_state)
    TextView mPhaseMarketlistState;
    @BindView(R.id.phase_marketlist_toptvStop)
    TextView mPhaseMarketlistTopStop;
    @BindView(R.id.phase_marketlist_statenote)
    TextView mPhaseMarketlistTopNote;
    @BindView(R.id.phase_marketlist_contop)
    ConstraintLayout mPhaseMarketlistContop;
    @BindView(R.id.phase_marketlist_conbw)
    TextView mPhaseMarketlistTvbw;
    @BindView(R.id.phase_marketlist_time)
    TextView mPhaseMarketlistTime;
    @BindView(R.id.phase_marketlist_lqnum)
    TextView mPhaseMarketlistLqnum;
    @BindView(R.id.phase_marketlist_synum)
    TextView mPhaseMarketlistSynum;
    @BindView(R.id.phase_marketlist_recy)
    RecyclerView mPhaseMarketlistRecy;
    @BindView(R.id.phase_marketlist_conbot)
    ConstraintLayout mPhaseMarketlistConbot;
    @BindView(R.id.phase_marketlist_topcon)
    ConstraintLayout mPhaseMarketlistTopCon;
    @BindView(R.id.phase_marketlist_smart)
    SmartRefreshLayout mPhaseMarketlistSmart;
    @BindView(R.id.phase_marketlist_bohui)
    TextView mPhaseMarketlistBohui;
    @BindView(R.id.phase_marketlist_tongyi)
    TextView mPhaseMarketlistTongyi;
    @BindView(R.id.phase_marketlist_statemiaoshu)
    TextView mPhaseMarketlistStateMiaoshu;
    @BindView(R.id.phase_marketlist_llshangh)
    LinearLayout mPhaseMarketlistLlshangh;
    private ArrayList<ProductBean.DataBean.ProductListBean> mMapList;
    private int page = 1;
    private PhaseMarketListAdapter mPhaseMarketListAdapter;
    private int[][] mInt_bg = new int[][]{{R.drawable.b_manjianquan_up, R.drawable.b_manjianquan_between, R.drawable.b_manjianquan_down},//满减
            {R.drawable.b_zhekouquan_up, R.drawable.b_zhekouquan_between, R.drawable.b_zhekouquan_down},//折扣
            {R.drawable.b_zengpinquan_up, R.drawable.b_zengpinquan_between, R.drawable.b_zengpinquan_down},//赠品
            {R.drawable.b_tiyanquan_up, R.drawable.b_tiyanquan_between, R.drawable.b_tiyanquan_down},//体验
            {R.drawable.b_daijinquan_up, R.drawable.b_daijinquan_between, R.drawable.b_daijinquan_down}};//代金券
    private String[] mstr_what = new String[]{"满减券", "折扣券", "赠品券", "体验券", "代金券"};
    private int[] mint_what = new int[]{R.drawable.beijing_manjianquan, R.drawable.beijing_zhekouquan, R.drawable.beijing_zengpinquan, R.drawable.beijing_tiyanquan, R.drawable.icon_daijinquan};
    private int[] mInt_color = new int[]{R.color.txt_manjian, R.color.txt_zhekou, R.color.txt_zengpin, R.color.txt_tiyan, R.color.txt_daijin};
    private String mMShopid;
    private int mStatus;
    private ProductBean.DataBean.ProductListBean mtop_proBean;
    private PhaseRemindDialog mPhaseRemindDialog;
    private DialogDetailList mDialogDetailList;
    private MarketDeatilBean mMarketDeatilBean;//获取驳回以及详细信息
    private String isnewcustomer = "0";

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_phase_market_list;
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        mMapList = new ArrayList<>();
        mMShopid = (String) ShareUtil.getInstance(this).get("user_id", "");
     /*   Intent intent = getIntent();
        if (intent != null) {
            isnewcustomer = intent.getStringExtra("isnewcustomer");
        }
//        cancelRed();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mPhaseMarketlistRecy.setLayoutManager(linearLayoutManager);
        mPhaseMarketListAdapter = new PhaseMarketListAdapter(this, mMapList);
        mPhaseMarketlistRecy.setAdapter(mPhaseMarketListAdapter);
        mPhaseMarketlistSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                isPossible();
                getData();

            }
        });
        mPhaseMarketlistSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ++page;
                isPossible();
                getData();
            }
        });
        mPhaseMarketlistSmart.autoRefresh();*/
    }
  /*
    @OnClick({R.id.left_back, R.id.phase_marketli_linear, R.id.phase_marketlist_topcon, R.id.phase_marketlist_toptvStop, R.id.phase_marketlist_bohui, R.id.phase_marketlist_tongyi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.phase_marketli_linear:
                Intent intent = new Intent(this, PhaseMarketSetActivity.class);
                intent.putExtra("is_creat", true);
                intent.putExtra("isnewcustomer", isnewcustomer);
                startActivity(intent);
                break;
            case R.id.phase_marketlist_topcon://查看驳回  以及  详情
                String actStatus = mtop_proBean.getActStatus();
                if (actStatus.equals("4")) {//后台驳回
                    String rejectreson = mMarketDeatilBean.getData().getRejectreson();
                    mPhaseRemindDialog = new PhaseRemindDialog.Builder(this).title("驳回原因").content(rejectreson).
                            left_color(R.color.main_color).left("我知道了").build(getWindowManager().getDefaultDisplay());
                    mPhaseRemindDialog.setOnTextClickListener(new PhaseRemindDialog.OnTextClickListener() {
                    });
                    mPhaseRemindDialog.show();
                } else {
                    mDialogDetailList = new DialogDetailList(this, mMarketDeatilBean.getData(), getWindowManager().getDefaultDisplay());
                    mDialogDetailList.setOnTextClickListener(new DialogDetailList.OnTextClickListener() {
                        @Override
                        public void onTextClickListener(String name) {
                            mDialogDetailList.dismiss();
                        }
                    });
                    mDialogDetailList.show();
                }
                break;
            case R.id.phase_marketlist_toptvStop:
                String stop = mPhaseMarketlistTopStop.getText().toString();
                if (stop.equals("停止发放")) {
                    mPhaseRemindDialog = new PhaseRemindDialog.Builder(this).title("提示").content("您正在停止当前营销券发放").left("取消").left_color(0).
                            right_color(0).right("确认").build(getWindowManager().getDefaultDisplay());
                    mPhaseRemindDialog.setOnTextClickListener(new PhaseRemindDialog.OnTextClickListener() {
                        @Override
                        public void onRightClickListener() {
                            super.onRightClickListener();
                            toStopFf(mtop_proBean.getId());
                        }
                    });
                    mPhaseRemindDialog.show();
                } else {
                    Intent inte = new Intent(this, PhaseMarketSetActivity.class);
                    inte.putExtra("is_creat", false);
                    inte.putExtra("isnewcustomer", isnewcustomer);
                    inte.putExtra("id", mtop_proBean.getId());
                    inte.putExtra("data", new Gson().toJson(mMarketDeatilBean));
                    startActivity(inte);
                }
                break;
            case R.id.phase_marketlist_bohui:
                mPhaseRemindDialog = new PhaseRemindDialog.Builder(this).title("提示").content("确认驳回？").left("取消").left_color(0).
                        right_color(0).right("确认").build(getWindowManager().getDefaultDisplay());
                mPhaseRemindDialog.setOnTextClickListener(new PhaseRemindDialog.OnTextClickListener() {
                    @Override
                    public void onRightClickListener() {
                        super.onRightClickListener();
                        shopQuer(mtop_proBean.getId(), false);
                    }
                });
                mPhaseRemindDialog.show();
                break;
            case R.id.phase_marketlist_tongyi:
                mPhaseRemindDialog = new PhaseRemindDialog.Builder(this).title("提示").content("确认通过？").left("取消").left_color(0).
                        right_color(0).right("确认").build(getWindowManager().getDefaultDisplay());
                mPhaseRemindDialog.setOnTextClickListener(new PhaseRemindDialog.OnTextClickListener() {
                    @Override
                    public void onRightClickListener() {
                        super.onRightClickListener();
                        shopQuer(mtop_proBean.getId(), true);
                    }
                });
                mPhaseRemindDialog.show();
                break;
        }
    }

 private void cancelRed() {
        Map<String, Object> map = new HashMap<>();
        map.put("shopId", mMShopid);
        map.put("type", isnewcustomer);//0老客1新客
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(PhaseApiService.class).cancelRed(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
            }

            @Override
            public void onErrorResult(HttpThrowable httpThrowable) {
            }
        });


    }

    *//*是否可以进行设置*//*
    private void isPossible() {
        Map<String, Object> map = new HashMap<>();
        map.put("shopId", mMShopid);
        map.put("type", isnewcustomer);//0老客1新客
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(PhaseApiService.class).isPossibleSettings(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                boolean code = result.getBoolean("code");
                if (code) {
                    JSONObject data = result.getJSONObject("data");
                    mStatus = data.getInteger("status");
                    if (mStatus == 0) {//未设置
                        mPhaseMarketliLinear.setVisibility(View.VISIBLE);
                        mPhaseMarketlistTopCon.setVisibility(View.GONE);
                    } else {//已设置
                        mPhaseMarketliLinear.setVisibility(View.GONE);
                        mPhaseMarketlistTopCon.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onErrorResult(HttpThrowable httpThrowable) {
            }
        });
    }

    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("shopId", mMShopid);
        map.put("currentPage", page);
        map.put("pageSize", 10);
        map.put("type", isnewcustomer);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(PhaseApiService.class).marketingDetails(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                mPhaseMarketlistSmart.finishRefresh();
                mPhaseMarketlistSmart.finishLoadMore();
                ProductBean productBean = new Gson().fromJson(result.toString(), ProductBean.class);
                if (productBean.isCode()) {
                    setData(productBean.getData(), productBean.getMessage());
                }
            }

            @Override
            public void onErrorResult(HttpThrowable httpThrowable) {
                mPhaseMarketlistSmart.finishRefresh();
                mPhaseMarketlistSmart.finishLoadMore();
            }
        });
    }

    *//*停止发放*//*
    private void toStopFf(String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(PhaseApiService.class).underwayActivity(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                boolean code = result.getBoolean("code");
                if (code) {
                    mPhaseMarketlistSmart.autoRefresh();
                }
                String message = result.getString("message");
                if (!TextUtils.isEmpty(message)) {
                    ToastShow(message);
                }
            }

            @Override
            public void onErrorResult(HttpThrowable httpThrowable) {
            }
        });
    }

    *//**
     * @paramid
     * @paramis_queren true 确认 false 驳回
     *//*
    private void shopQuer(String id, boolean is_queren) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        Observable<JSONObject> jsonObjectObservable = null;
        if (is_queren) {
            jsonObjectObservable = DevRing.httpManager().getService(PhaseApiService.class).merchantConfirmation(EncryPtionUtil.getInstance(this).toEncryption(map));
        } else {
            jsonObjectObservable = DevRing.httpManager().getService(PhaseApiService.class).merchantRejected(EncryPtionUtil.getInstance(this).toEncryption(map));
        }
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                boolean code = result.getBoolean("code");
                if (code) {
                    mPhaseMarketlistSmart.autoRefresh();
                }
                String message = result.getString("message");
                if (!TextUtils.isEmpty(message)) {
                    ToastShow(message);
                }
            }

            @Override
            public void onErrorResult(HttpThrowable httpThrowable) {
            }
        });
    }

    private void setData(ProductBean.DataBean data, String msg) {
        List<ProductBean.DataBean.ProductListBean> productList = data.getProductList();
        if (productList != null && productList.size() != 0) {
            if (page == 1) {
                ProductBean.DataBean.ProductListBean productListBean = productList.get(0);
                String actStatus = productListBean.getActStatus();
                if (!actStatus.equals("7")) {
                    productList.remove(productListBean);
                    mtop_proBean = productListBean;
                    toChangeColor();
                }
                mMapList.clear();
                mMapList.addAll(productList);
            } else {
                mMapList.addAll(productList);
            }
            mPhaseMarketListAdapter.notifyDataSetChanged();
        } else {
            if (!TextUtils.isEmpty(msg)) {
                ToastShow(msg);
            }
        }

    }

    private void toChangeColor() {
        if (mtop_proBean != null) {
            String coupontype = mtop_proBean.getCoupontype();
            String actStatus = mtop_proBean.getActStatus();
            String addresource = mtop_proBean.getAddresource();
            String isremovedthenextday = mtop_proBean.getIsremovedthenextday();
            switch (coupontype) {
                case "1":
                    topToChange(0);
                    break;
                case "2":
                    topToChange(1);
                    break;
                case "3":
                    topToChange(2);
                    break;
                case "5":
                    topToChange(3);
                    break;
                case "6":
                    topToChange(4);
                    break;
            }
            mPhaseMarketlistTopStop.setVisibility(View.GONE);
            mPhaseMarketlistTopNote.setVisibility(View.GONE);
            mPhaseMarketlistLlshangh.setVisibility(View.GONE);
            mPhaseMarketlistStateMiaoshu.setTextColor(this.getResources().getColor(R.color.black));
            switch (actStatus) {
                case "1":
                    mPhaseMarketlistLlshangh.setVisibility(View.VISIBLE);
                    mPhaseMarketlistStateMiaoshu.setText("等待商家审核");
                    break;
                case "2":
                    mPhaseMarketlistStateMiaoshu.setText("商家驳回");
                    mPhaseMarketlistStateMiaoshu.setTextColor(this.getResources().getColor(R.color.main_color));
                    break;
                case "3":
                    mPhaseMarketlistStateMiaoshu.setText("等待小二审核");
                    mPhaseMarketlistStateMiaoshu.setTextColor(this.getResources().getColor(R.color.main_color));
                    break;
                case "4":
                    if (!TextUtils.isEmpty(addresource)) {// --活动新增来源  1后台添加 2商家添加
                        if (addresource.equals("2") || addresource.equals("2.0")) {
                            mPhaseMarketlistTopStop.setVisibility(View.VISIBLE);
                            mPhaseMarketlistTopStop.setText("待修改");
                            mPhaseMarketlistStateMiaoshu.setText("小二审核驳回");
                        } else {
                            mPhaseMarketlistStateMiaoshu.setText("待修改 请联系您的客户经理修改");
                        }
                    }
                    break;
                case "6":
                    mPhaseMarketlistStateMiaoshu.setText("发放中");
                    if (isremovedthenextday.equals("1")) {
                        mPhaseMarketlistTopNote.setVisibility(View.VISIBLE);
                        mPhaseMarketlistTopNote.setText("次日下架");
                    } else {
                        mPhaseMarketlistTopStop.setVisibility(View.VISIBLE);
                        mPhaseMarketlistTopStop.setText("停止发放");
                    }
                    break;
            }
            getDetailMsg(mtop_proBean.getId());
        }

    }

    private void topToChange(int what) {
        Log.e(TAG, "setData: " + (what));
        mPhaseMarketlistContent.setText(mtop_proBean.getActName());
        mPhaseMarketlistWhat.setText(mstr_what[what]);
        List<ProductBean.DataBean.ProductListBean.TimeListBean> timeList = mtop_proBean.getTimeList();
        StringBuilder stb_time = new StringBuilder("可用时间段：");
        for (int i = 0; i < timeList.size(); i++) {
            if (i == 0) {
                stb_time.append(timeList.get(i).getStarttime() + "-" + timeList.get(i).getEndtime());
            } else {
                stb_time.append("；");
                stb_time.append(timeList.get(i).getStarttime() + "-" + timeList.get(i).getEndtime());
            }
        }
        String weekdays = mtop_proBean.getWeekdays();
        if (!TextUtils.isEmpty(weekdays)) {
            String weekdays_new = weekdays.replaceAll(",", "");
            if (weekdays_new.length() == 7) {
                stb_time.append(" ");
                stb_time.append("周一至周日,");
            } else {
                stb_time.append(" ");
                stb_time.append(weekdays_new.contains("1") ? "周一，" : "");
                stb_time.append(weekdays_new.contains("2") ? "周二，" : "");
                stb_time.append(weekdays_new.contains("3") ? "周三，" : "");
                stb_time.append(weekdays_new.contains("4") ? "周四，" : "");
                stb_time.append(weekdays_new.contains("5") ? "周五，" : "");
                stb_time.append(weekdays_new.contains("6") ? "周六，" : "");
                stb_time.append(weekdays_new.contains("7") ? "周日，" : "");
            }
        }
        String res = stb_time.toString();
        mPhaseMarketlistTime.setText(res.substring(0, res.length() - 1));
        mPhaseMarketlistLqnum.setText("领取数：" + mtop_proBean.getReceiveCount() + "张");
        mPhaseMarketlistSynum.setText("使用数：" + mtop_proBean.getUseCount() + "张");
        mPhaseMarketlistContop.setBackgroundResource(mInt_bg[what][0]);
        mPhaseMarketlistTvbw.setBackgroundResource(mInt_bg[what][1]);
        mPhaseMarketlistConbot.setBackgroundResource(mInt_bg[what][2]);
        mPhaseMarketlistWhat.setBackgroundResource(mint_what[what]);
        mPhaseMarketlistContent.setTextColor(this.getResources().getColor(mInt_color[what]));
    }

    *//*获取驳回以及详细信息查看*//*
    private void getDetailMsg(String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).currentActivity(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                boolean code = result.getBoolean("code");
                if (code) {
                    mMarketDeatilBean = new Gson().fromJson(result.toString(), MarketDeatilBean.class);
                }
            }

            @Override
            public void onErrorResult(HttpThrowable httpThrowable) {
            }
        });
    }*/

    @Override
    protected void onRestart() {
        super.onRestart();
        mPhaseMarketlistSmart.autoRefresh();
    }
}
