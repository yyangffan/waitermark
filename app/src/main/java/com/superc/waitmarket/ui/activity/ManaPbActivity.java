package com.superc.waitmarket.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.superc.waitmarket.adapter.ManaJhAdapter;
import com.superc.waitmarket.adapter.ManaShAdapter;
import com.superc.waitmarket.adapter.ManaWhAdapter;
import com.superc.waitmarket.base.ApiService;
import com.superc.waitmarket.base.WaitApplication;
import com.superc.waitmarket.bean.JihuoBean;
import com.superc.waitmarket.bean.MainTainBean;
import com.superc.waitmarket.bean.ShenheBean;
import com.superc.waitmarket.httputil.EncryPtionHttp;
import com.superc.waitmarket.httputil.EncryPtionUtil;
import com.superc.waitmarket.utils.BigDecimalUtils;
import com.superc.waitmarket.utils.dialog.MiddleDialog;
import com.superc.waitmarket.utils.pop.PopShifhWindow;
import com.superc.yyfflibrary.base.BaseActivity;
import com.superc.yyfflibrary.dialog.YfsRemindDialog;
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

public class ManaPbActivity extends BaseActivity {


    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.manapb_shifang)
    TextView mManapbShifang;
    @BindView(R.id.manapb_imgv)
    ImageView mManapbImgv;
    @BindView(R.id.manapb_num)
    TextView mManapbNum;
    @BindView(R.id.manapb_recy)
    RecyclerView mManapbRecy;
    @BindView(R.id.manapb_smart)
    SmartRefreshLayout mManapbSmart;
    @BindView(R.id.manapb_cancel)
    TextView mManapbCancel;
    @BindView(R.id.manapb_shifangbot)
    TextView mManapbShifangbot;
    @BindView(R.id.manapb_linear)
    LinearLayout mManapbLinear;

    private PopShifhWindow mPopShifhWindow;
    private int page = 1;
    private ManaWhAdapter mManaWhAdapter;
    private ManaShAdapter mManaShAdapter;
    private ManaJhAdapter mManaJhAdapter;
    private List<MainTainBean.DataBean.ListBean> mMapWhList;
    private List<ShenheBean.DataBean.ListBean> mMapShList;
    private List<JihuoBean.DataBean.ListBean> mMapJhList;
    private String mWhat, mUser_id, mShifang_id;
    private YfsRemindDialog mYfsRemindDialog;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_mana_pb;
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        TitleUtils.setStatusTextColor(true, this);
        mManapbShifangbot.setEnabled(false);
        mUser_id = (String) ShareUtil.getInstance(this).get("user_id", "");
        mMapWhList = new ArrayList<>();
        mMapShList = new ArrayList<>();
        mMapJhList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mManapbRecy.setLayoutManager(manager);
        mManaWhAdapter = new ManaWhAdapter(this, mMapWhList);
        mManaShAdapter = new ManaShAdapter(this, mMapShList);
        mManaJhAdapter = new ManaJhAdapter(this, mMapJhList);

        Intent intent = getIntent();
        if (intent != null) {
            mWhat = intent.getStringExtra("what");
        }
        switch (mWhat) {
            case "0":/*维护中*/
                initWeihu();
                mManapbImgv.setVisibility(View.VISIBLE);
                break;
            case "1":/*待审核*/
                initShData();
                mTitle.setText("待审核");
                break;
            case "2":/*待激活*/
                initJhData();
                mTitle.setText("待激活");
                break;
        }
        mYfsRemindDialog = new YfsRemindDialog.Builder(this).title("是否确认释放该商户").left("取消").right("确认").build();
        mYfsRemindDialog.setOnTextClickListener(new YfsRemindDialog.OnTextClickListener() {
            @Override
            public void onRightClickListener() {
                super.onRightClickListener();
                toShifang();
            }
        });

    }

    @OnClick({R.id.imgv_back, R.id.manapb_imgv, R.id.manapb_cancel, R.id.manapb_shifangbot})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgv_back:
                finish();
                break;
            case R.id.manapb_imgv:
                initPop();
                break;
            case R.id.manapb_cancel:
                mManapbImgv.setVisibility(View.VISIBLE);
                mManapbShifang.setVisibility(View.GONE);
                mManapbLinear.setVisibility(View.GONE);
                mManaWhAdapter.setIs_shifang(false);
                mManaWhAdapter.notifyDataSetChanged();
                break;
            case R.id.manapb_shifangbot:
                mYfsRemindDialog.show();
                break;
        }
    }

    /*维护中*/
    private void initPop() {
        mPopShifhWindow = new PopShifhWindow(this);
        mPopShifhWindow.setOnPopClickListener(new PopShifhWindow.OnPopClickListener() {
            @Override
            public void onPopClickListener() {
                mManapbShifang.setVisibility(View.VISIBLE);
                mManapbImgv.setVisibility(View.GONE);
                mManapbLinear.setVisibility(View.VISIBLE);
                mManaWhAdapter.setIs_shifang(true);
                mManaWhAdapter.notifyDataSetChanged();

            }
        });
        mPopShifhWindow.showAsDropDown(mManapbImgv);
        mPopShifhWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(ManaPbActivity.this, 1.0f);
            }
        });
    }

    private void initWeihu() {
        mManapbRecy.setAdapter(mManaWhAdapter);
        mManapbSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getWhData();
            }
        });
        mManapbSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ++page;
                getWhData();
            }
        });
        mManapbSmart.autoRefresh();
        mManaWhAdapter.setOnItemClickListener(new ManaWhAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int pos) {
                boolean isshifang = mManaWhAdapter.isshifang();
                if (isshifang) {
                    MainTainBean.DataBean.ListBean map = mMapWhList.get(pos);
                    boolean check = map.isCheck();
                    check = !check;
                    map.setCheck(check);
                    mManaWhAdapter.notifyDataSetChanged();
                }
                isCanShifang();
            }
        });
        mManaWhAdapter.setOnEdtClickListener(new ManaWhAdapter.OnEdtClickListener() {
            @Override
            public void onEdtClickListener(int pos) {
                MainTainBean.DataBean.ListBean map = mMapWhList.get(pos);
                Intent intent = new Intent(ManaPbActivity.this, EdtDetailActivity.class);
                ShareUtil.getInstance(ManaPbActivity.this).put("edtdetail_id", BigDecimalUtils.bigUtil(map.getShopId()));
                ShareUtil.getInstance(ManaPbActivity.this).put("is_creat", "1");
                ShareUtil.getInstance(ManaPbActivity.this).put("channel", "3");

                startActivity(intent);
            }
        });
    }

    private void isCanShifang() {
        boolean canshifang = false;
        StringBuilder stringBuilder = new StringBuilder();
        for (MainTainBean.DataBean.ListBean bean : mMapWhList) {
            if (bean.isCheck()) {
                canshifang = true;
                stringBuilder.append(bean.getShopId());
                stringBuilder.append(",");
            }
        }
        if (canshifang) {
            mManapbShifangbot.setBackgroundColor(this.getResources().getColor(R.color.red));
            mManapbShifangbot.setEnabled(true);
            mShifang_id = stringBuilder.toString();
        } else {
            mManapbShifangbot.setBackgroundColor(this.getResources().getColor(R.color.line_color));
            mManapbShifangbot.setEnabled(false);
        }
    }

    private void toShifang() {
        Map<String, Object> map = new HashMap<>();
        map.put("shopId", mShifang_id);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).merchantPoolSearchfreed(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    new MiddleDialog.Builder(ManaPbActivity.this).content("释放成功").img_id(R.drawable.icon_chenggong).build().show();
                    mManapbSmart.autoRefresh();
                    mManapbImgv.setVisibility(View.VISIBLE);
                    mManapbShifang.setVisibility(View.GONE);
                    mManapbLinear.setVisibility(View.GONE);
                    mManaWhAdapter.setIs_shifang(false);
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

    private void getWhData() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", mUser_id);
        map.put("currentPage", page);
        map.put("pageSize", 10);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).maintenanceList(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                mManapbSmart.finishRefresh();
                mManapbSmart.finishLoadMore();
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    MainTainBean mainTainBean = new Gson().fromJson(result.toString(), MainTainBean.class);
                    mManapbNum.setText("共有" + BigDecimalUtils.bigUtil(mainTainBean.getData().getCount()) + "家维护中");
                    if (page == 1) {
                        mMapWhList.clear();
                    }
                    for (int i = 0; i < mainTainBean.getData().getList().size(); i++) {
                        MainTainBean.DataBean.ListBean listBean = mainTainBean.getData().getList().get(i);
                        listBean.setCheck(false);
                    }
                    mMapWhList.addAll(mainTainBean.getData().getList());
                    mManaWhAdapter.notifyDataSetChanged();
                } else {
                    if (page == 1) {
                        mMapWhList.clear();
                        mManaWhAdapter.notifyDataSetChanged();
                    }
                }
                if (!TextUtils.isEmpty(msg)) {
                    ToastShow(msg);
                }
            }

            @Override
            public void onErrorResult(HttpThrowable httpThrowable) {
                mManapbSmart.finishRefresh();
                mManapbSmart.finishLoadMore();
                ToastShow("接口访问异常" + httpThrowable.message);
                Log.d("qqq", httpThrowable.message);
            }
        });

    }

    /*待审核*/
    private void initShData() {
        mManapbRecy.setAdapter(mManaShAdapter);
        mManapbSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getShData();
            }
        });
        mManapbSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ++page;
                getShData();
            }
        });
        mManapbSmart.autoRefresh();
        mManaShAdapter.setOnItemClickListener(new ManaShAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int pos) {
                ShenheBean.DataBean.ListBean map = mMapShList.get(pos);
                ShareUtil.getInstance(WaitApplication.getInstance()).put("edtdetail_id", map.getShopId());
                ShareUtil.getInstance(WaitApplication.getInstance()).put("channel", "1");
                statActivity(MerchantDetailActivity.class);
            }
        });

    }

    private void getShData() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", mUser_id);
        map.put("currentPage", page);
        map.put("pageSize", 10);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).pendingList(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                mManapbSmart.finishRefresh();
                mManapbSmart.finishLoadMore();
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    ShenheBean shenheBean = new Gson().fromJson(result.toString(), ShenheBean.class);
                    mManapbNum.setText("共有" + BigDecimalUtils.bigUtil(shenheBean.getData().getCount()) + "家待审核");
                    if (page == 1) {
                        mMapShList.clear();
                    }
                    mMapShList.addAll(shenheBean.getData().getList());
                    mManaShAdapter.notifyDataSetChanged();
                } else {
                    if (page == 1) {
                        mMapShList.clear();
                        mManaShAdapter.notifyDataSetChanged();
                    }
                }
                if (!TextUtils.isEmpty(msg)) {
                    ToastShow(msg);
                }
            }

            @Override
            public void onErrorResult(HttpThrowable httpThrowable) {
                mManapbSmart.finishRefresh();
                mManapbSmart.finishLoadMore();
                ToastShow("接口访问异常" + httpThrowable.message);
                Log.d("qqq", httpThrowable.message);
            }
        });


    }

    /*待激活*/
    private void initJhData() {
        mManapbRecy.setAdapter(mManaJhAdapter);
        mManapbSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getJhData();
            }
        });
        mManapbSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ++page;
                getJhData();
            }
        });
        mManapbSmart.autoRefresh();
        mManaJhAdapter.setOnItemClickListener(new ManaJhAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int pos) {
                JihuoBean.DataBean.ListBean map = mMapJhList.get(pos);
                ShareUtil.getInstance(WaitApplication.getInstance()).put("edtdetail_id", BigDecimalUtils.bigUtil(map.getShopid()));
                ShareUtil.getInstance(WaitApplication.getInstance()).put("channel", "0");
                statActivity(MerchantDetailActivity.class);
            }
        });
    }

    private void getJhData() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", mUser_id);
        map.put("currentPage", page);
        map.put("pageSize", 10);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).activatedList(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                mManapbSmart.finishRefresh();
                mManapbSmart.finishLoadMore();
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    JihuoBean bean = new Gson().fromJson(result.toString(), JihuoBean.class);
                    mManapbNum.setText("共有" + BigDecimalUtils.bigUtil(bean.getData().getCount()) + "家待激活");
                    if (page == 1) {
                        mMapJhList.clear();
                    }
                    mMapJhList.addAll(bean.getData().getList());
                    mManaJhAdapter.notifyDataSetChanged();
                } else {
                    if (page == 1) {
                        mMapJhList.clear();
                        mManaJhAdapter.notifyDataSetChanged();
                    }
                }
                if (!TextUtils.isEmpty(msg)) {
                    ToastShow(msg);
                }
            }

            @Override
            public void onErrorResult(HttpThrowable httpThrowable) {
                mManapbSmart.finishRefresh();
                mManapbSmart.finishLoadMore();
                ToastShow("接口访问异常" + httpThrowable.message);
                Log.d("qqq", httpThrowable.message);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mManapbSmart.autoRefresh();
    }

    /**
     * 设置添加屏幕的背景透明度(值越大,透明度越高)
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }
}
