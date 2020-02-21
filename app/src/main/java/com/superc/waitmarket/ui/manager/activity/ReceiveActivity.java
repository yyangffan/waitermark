package com.superc.waitmarket.ui.manager.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
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

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.throwable.HttpThrowable;
import com.ljy.devring.util.KeyboardUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.superc.waitmarket.R;
import com.superc.waitmarket.adapter.ReceiveAdapter;
import com.superc.waitmarket.base.ApiService;
import com.superc.waitmarket.base.WaitApplication;
import com.superc.waitmarket.bean.BotListBean;
import com.superc.waitmarket.bean.SaryIndustryBean;
import com.superc.waitmarket.httputil.EncryPtionHttp;
import com.superc.waitmarket.httputil.EncryPtionUtil;
import com.superc.waitmarket.utils.BigDecimalUtils;
import com.superc.waitmarket.utils.dialog.JihuoDialog;
import com.superc.waitmarket.utils.dialog.MiddleDialog;
import com.superc.waitmarket.utils.dialog.PhaseRemindDialog;
import com.superc.waitmarket.utils.pop.PopMerchWindow;
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

public class ReceiveActivity extends BaseActivity {


    @BindView(R.id.higheney_edt)
    EditText mHigheneyEdt;
    @BindView(R.id.merchpool_hezuo)
    TextView mMerchpoolHezuo;
    @BindView(R.id.merchpool_imgone)
    ImageView mMerchpoolImgone;
    @BindView(R.id.merchpool_one)
    LinearLayout mMerchpoolOne;
    @BindView(R.id.merchpool_tuozhan)
    TextView mMerchpoolTuozhan;
    @BindView(R.id.merchpool_imgtwo)
    ImageView mMerchpoolImgtwo;
    @BindView(R.id.merchpool_tuozhanwangd)
    TextView mMerchpoolTuozhanwangd;
    @BindView(R.id.merchpool_imgthree)
    ImageView mMerchpoolImgthree;
    @BindView(R.id.merchpool_recy)
    RecyclerView mMerchpoolRecy;
    @BindView(R.id.user_check_smart)
    SmartRefreshLayout mUserCheckSmart;
    @BindView(R.id.higheney_cancel)
    TextView mTvCancel;
    @BindView(R.id.linearLayout2)
    LinearLayout mLinea;
    @BindView(R.id.merchpool_linear)
    LinearLayout merchpool_linear;
    private int page = 1;
    private String mUser_id;
    private List<String> mList;
    private ReceiveAdapter mReceiveAdapter;
    private JihuoDialog mJihuoDialog_once, mJihuoDialog_twice;
    private List<Map<String, Object>> mJhDig_mapOnce, mJhDig_mapTwice;
    private String bg_type = "0", sm_type = "";
    private String hangye_str = "行业";
    private PopMerchWindow mPopMerchWindow_tuozhan, mPopMerchWindow_wangdian;
    private List<BotListBean> mBotListBeans_tuozhan, mBotListBeans_wangdian;
    private String tuozhan_code, wangdian_code, receive_code;
    private PhaseRemindDialog mPhaseRemindDialog;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_receive;
    }

    @Override
    public void init() {
        TitleUtils.setStatusTextColor(true, this);
        ButterKnife.bind(this);
        mTvCancel.requestFocus();
        mList = new ArrayList<>();
        mJhDig_mapOnce = new ArrayList<>();
        mJhDig_mapTwice = new ArrayList<>();
        mBotListBeans_tuozhan = new ArrayList<>();
        mBotListBeans_wangdian = new ArrayList<>();
        mReceiveAdapter = new ReceiveAdapter(this, mList);
        mPhaseRemindDialog = new PhaseRemindDialog.Builder(ReceiveActivity.this).title("确定领取此商户").left("取消").right("确认").build(ReceiveActivity.this.getWindowManager().getDefaultDisplay());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mMerchpoolRecy.setLayoutManager(linearLayoutManager);
        mMerchpoolRecy.setAdapter(mReceiveAdapter);
        mUserCheckSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getData();
            }
        });
        mUserCheckSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ++page;
                getData();
            }
        });
        mUserCheckSmart.autoRefresh();
        mReceiveAdapter.setOnReceiveClickListener(new ReceiveAdapter.OnReceiveClickListener() {
            @Override
            public void onReceiveClickListener(int position) {
                receive_code = position + "";
                mPhaseRemindDialog.show();
            }
        });
        mPhaseRemindDialog.setOnTextClickListener(new PhaseRemindDialog.OnTextClickListener() {
            @Override
            public void onRightClickListener() {
                super.onRightClickListener();
                if (receive_code.equals("3")) {
                    new MiddleDialog.Builder(ReceiveActivity.this).title("领取成功").img_id(R.drawable.icon_chenggong).build().show();
                } else {
                    new MiddleDialog.Builder(ReceiveActivity.this).title("领取失败").img_id(R.drawable.icon_weidabiao).build().show();
                }
            }
        });
        getOnceTab();
        initPopWindow();
    }


    @OnClick({R.id.imgv_back, R.id.higheney_search, R.id.higheney_cancel, R.id.merchpool_one, R.id.merchpool_two, R.id.merchpool_three, R.id.merchpool_four})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgv_back:
                finish();
                break;
            case R.id.higheney_search:
                String content = mHigheneyEdt.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    ToastShow("请输入搜索内容");
                } else {
                    KeyboardUtil.hideKeyboard(mLinea);
                    mUserCheckSmart.autoRefresh();
                    mTvCancel.requestFocus();
                }
                break;
            case R.id.higheney_cancel:
                String sear_con = mHigheneyEdt.getText().toString();
                if (!TextUtils.isEmpty(sear_con)) {
                    mHigheneyEdt.setText("");
                    KeyboardUtil.hideKeyboard(mLinea);
                    mUserCheckSmart.autoRefresh();
                }
                break;
            case R.id.merchpool_one:
                if (mJihuoDialog_once != null) {
                    mMerchpoolImgone.setImageResource(R.drawable.icon_shangla);
                    mMerchpoolHezuo.setTextColor(getResources().getColor(R.color.red));
                    mJihuoDialog_once.show();
                }
                break;
            case R.id.merchpool_two:
                if (mPopMerchWindow_tuozhan != null) {
                    mPopMerchWindow_tuozhan.showAsDropDown(mLinea);
                    merchpool_linear.setVisibility(View.VISIBLE);
                    mMerchpoolImgtwo.setImageResource(R.drawable.icon_shangla);
                    mMerchpoolTuozhan.setTextColor(getResources().getColor(R.color.red));
                }
                break;
            case R.id.merchpool_three:
                if (mPopMerchWindow_wangdian != null) {
                    mPopMerchWindow_wangdian.showAsDropDown(mLinea);
                    merchpool_linear.setVisibility(View.VISIBLE);
                    mMerchpoolImgthree.setImageResource(R.drawable.icon_shangla);
                    mMerchpoolTuozhanwangd.setTextColor(getResources().getColor(R.color.red));
                }
                break;
            case R.id.merchpool_four:
                Intent intent = new Intent(this, WhpbShaixActivity.class);
                intent.putExtra("type", "3");
                startActivity(intent);
                break;
        }
    }

    private void getData() {
        if (page == 1) {
            mList.clear();
        }
        for (int i = 0; i < 10; i++) {
            mList.add("");
        }
        mReceiveAdapter.notifyDataSetChanged();
        mUserCheckSmart.finishRefresh();
        mUserCheckSmart.finishLoadMore();

    }

    //    获取横向列表
    private void getOnceTab() {
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
                    mJihuoDialog_once = new JihuoDialog(ReceiveActivity.this, mJhDig_mapOnce, "请选择一级行业");
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
                                mMerchpoolImgone.setImageResource(R.drawable.icon_xiala);
                                mMerchpoolHezuo.setTextColor(getResources().getColor(R.color.merchool_txt));
                                mUserCheckSmart.autoRefresh();
                            } else {
                                initSmallPop((String) map.get("data"));
                            }
                        }
                    });
                    mJihuoDialog_once.setOnChaClickListener(new JihuoDialog.OnChaClickListener() {
                        @Override
                        public void onChaClickListener(int pos, String what) {
                            mMerchpoolImgone.setImageResource(R.drawable.icon_xiala);
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
        mJihuoDialog_twice = new JihuoDialog(this, mJhDig_mapTwice, "请选择二级行业");
        mJihuoDialog_twice.show();
        mJihuoDialog_twice.setOnFinishListener(new JihuoDialog.OnFinishListener() {
            @Override
            public void onFinishListener(int pos, String what) {
                toPanD(pos, what);
            }
        });
        mJihuoDialog_twice.setOnChaClickListener(new JihuoDialog.OnChaClickListener() {
            @Override
            public void onChaClickListener(int pos, String what) {
                toPanD(pos, what);
            }
        });

    }

    private void toPanD(int pos, String what) {
        mMerchpoolImgone.setImageResource(R.drawable.icon_xiala);
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
                mUserCheckSmart.autoRefresh();
            }
        } else {
            sm_type = small_id;
            mUserCheckSmart.autoRefresh();
        }
    }

    private void initPopWindow() {
        /*拓展中支*/
        for (int i = 0; i < 10; i++) {
            BotListBean botListBean = new BotListBean("第" + (i + 1) + "中支", false, i + "");
            mBotListBeans_tuozhan.add(botListBean);
        }
        mPopMerchWindow_tuozhan = new PopMerchWindow(ReceiveActivity.this, mBotListBeans_tuozhan);
        mPopMerchWindow_tuozhan.setOnPopClickListener(new PopMerchWindow.OnPopClickListener() {
            @Override
            public void onPopClickListener(String con, String what) {
                String old = mMerchpoolTuozhan.getText().toString();
                if (!con.equals(old)) {
                    mMerchpoolTuozhan.setText(con);
                    tuozhan_code = what;
                    page = 1;
                    getData();
//                                mMerchpoolSmart.autoRefresh();
                }

            }
        });

        mPopMerchWindow_tuozhan.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                merchpool_linear.setVisibility(View.GONE);
                mMerchpoolImgtwo.setImageResource(R.drawable.icon_xiala);
                mMerchpoolTuozhan.setTextColor(getResources().getColor(R.color.merchool_txt));
            }
        });
        /*网点*/
        for (int i = 0; i < 10; i++) {
            BotListBean botListBean = new BotListBean("第" + (i + 1) + "1网点", false, i + "");
            mBotListBeans_wangdian.add(botListBean);
        }
        mPopMerchWindow_wangdian = new PopMerchWindow(ReceiveActivity.this, mBotListBeans_wangdian);
        mPopMerchWindow_wangdian.setOnPopClickListener(new PopMerchWindow.OnPopClickListener() {
            @Override
            public void onPopClickListener(String con, String what) {
                String old = mMerchpoolTuozhanwangd.getText().toString();
                if (!con.equals(old)) {
                    mMerchpoolTuozhanwangd.setText(con);
                    wangdian_code = what;
                    page = 1;
                    getData();
//                                mMerchpoolSmart.autoRefresh();
                }

            }
        });

        mPopMerchWindow_wangdian.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                merchpool_linear.setVisibility(View.GONE);
                mMerchpoolImgthree.setImageResource(R.drawable.icon_xiala);
                mMerchpoolTuozhanwangd.setTextColor(getResources().getColor(R.color.merchool_txt));
            }
        });


    }

}
