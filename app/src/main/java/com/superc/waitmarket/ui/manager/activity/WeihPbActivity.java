package com.superc.waitmarket.ui.manager.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
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
import com.superc.waitmarket.adapter.DkaitAdapter;
import com.superc.waitmarket.adapter.DshenheAdapter;
import com.superc.waitmarket.adapter.YshangxAdapter;
import com.superc.waitmarket.base.ApiService;
import com.superc.waitmarket.base.WaitApplication;
import com.superc.waitmarket.bean.BotListBean;
import com.superc.waitmarket.bean.SaryIndustryBean;
import com.superc.waitmarket.httputil.EncryPtionHttp;
import com.superc.waitmarket.httputil.EncryPtionUtil;
import com.superc.waitmarket.ui.activity.MerchantDetailActivity;
import com.superc.waitmarket.ui.activity.TransDetailActivity;
import com.superc.waitmarket.utils.BigDecimalUtils;
import com.superc.waitmarket.utils.dialog.JihuoDialog;
import com.superc.waitmarket.utils.dialog.MerBohuiDialog;
import com.superc.waitmarket.utils.dialog.MiddleDialog;
import com.superc.waitmarket.utils.pop.PopMerchWindow;
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

/********************************************************************
 @version: 1.0.0
 @description: 待开通、待审核、已上线
 @author: 杨帆
 @time: 2020/2/12 14:38
 @变更历史:
 ********************************************************************/
public class WeihPbActivity extends BaseActivity {


    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.higheney_edt)
    EditText mHigheneyEdt;
    @BindView(R.id.higheney_cancel)
    TextView mHigheneyCancel;
    @BindView(R.id.merchpool_hezuo)
    TextView mMerchpoolHezuo;
    @BindView(R.id.merchpool_imgone)
    ImageView mMerchpoolImgone;
    @BindView(R.id.merchpool_tuozhan)
    TextView mMerchpoolTuozhan;
    @BindView(R.id.merchpool_imgtwo)
    ImageView mMerchpoolImgtwo;
    @BindView(R.id.merchpool_tuozhanwangd)
    TextView mMerchpoolTuozhanwangd;
    @BindView(R.id.merchpool_imgthree)
    ImageView mMerchpoolImgthree;
    @BindView(R.id.merchpool_shaixuan)
    TextView mMerchpoolShaixuan;
    @BindView(R.id.merchpool_imgfour)
    ImageView mMerchpoolImgfour;
    @BindView(R.id.linearLayout2)
    LinearLayout mLinea;
    @BindView(R.id.merchpool_recy)
    RecyclerView mMerchpoolRecy;
    @BindView(R.id.merchpool_linear)
    LinearLayout merchpool_linear;
    @BindView(R.id.user_check_smart)
    SmartRefreshLayout mUserCheckSmart;
    @BindView(R.id.manapb_shifang)
    TextView mManapbShifang;
    @BindView(R.id.manapb_imgv)
    ImageView mManapbImgv;
    @BindView(R.id.manapb_cancel)
    TextView mManapbCancel;
    @BindView(R.id.manapb_shifangbot)
    TextView mManapbShifangbot;
    @BindView(R.id.manapb_linear)
    LinearLayout mManapbLinear;

    private String type = "";
    private Intent mIntent;
    private String mType, mShifang_id;
    private int page = 1;
    private List<Map<String, Object>> mList_kaitong;
    List<String> mList_shenhe;
    List<String> mList_yshangx;
    private DkaitAdapter mDkaitAdapter;
    private DshenheAdapter mDshenheAdapter;
    private YshangxAdapter mYshangxAdapter;
    private PopShifhWindow mPopShifhWindow;
    private YfsRemindDialog mYfsRemindDialog;
    private String mUser_id;
    private JihuoDialog mJihuoDialog_once, mJihuoDialog_twice;
    private List<Map<String, Object>> mJhDig_mapOnce, mJhDig_mapTwice;
    private String bg_type = "0", sm_type = "";
    private String hangye_str = "行业";
    private PopMerchWindow mPopMerchWindow_quyu, mPopMerchWindow_shangquan;
    private List<BotListBean> mBotListBeans_tuozhan, mBotListBeans_wangdian;
    private String quyu_code, shangquan_code;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_weih_pb;
    }

    @Override
    public void init() {
        TitleUtils.setStatusTextColor(true, this);
        ButterKnife.bind(this);
        mHigheneyCancel.requestFocus();
        mIntent = getIntent();
        mType = mIntent.getStringExtra("type");
        mList_kaitong = new ArrayList<>();
        mList_shenhe = new ArrayList<>();
        mList_yshangx = new ArrayList<>();
        mJhDig_mapOnce = new ArrayList<>();
        mJhDig_mapTwice = new ArrayList<>();
        mBotListBeans_tuozhan = new ArrayList<>();
        mBotListBeans_wangdian = new ArrayList<>();
        mDkaitAdapter = new DkaitAdapter(this, mList_kaitong);
        mDshenheAdapter = new DshenheAdapter(this, mList_shenhe);
        mYshangxAdapter = new YshangxAdapter(this, mList_yshangx);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mMerchpoolRecy.setLayoutManager(linearLayoutManager);
        initTitle();
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
        mYfsRemindDialog = new YfsRemindDialog.Builder(this).title("是否确认释放该商户").left("取消").right("确认").build();
        mYfsRemindDialog.setOnTextClickListener(new YfsRemindDialog.OnTextClickListener() {
            @Override
            public void onRightClickListener() {
                super.onRightClickListener();
                toShifang();
            }
        });
        getOnceTab();
        initPopWindow();
    }

    private void initTitle() {
        switch (mType) {
            case "0":
                mTitle.setText("待开通");
                mManapbImgv.setVisibility(View.VISIBLE);
                mMerchpoolRecy.setAdapter(mDkaitAdapter);
                break;
            case "1":
                mTitle.setText("待审核");
                mMerchpoolRecy.setAdapter(mDshenheAdapter);
                break;
            case "2":
                mTitle.setText("已上线");
                mMerchpoolRecy.setAdapter(mYshangxAdapter);
                break;
        }
        mDkaitAdapter.setOnButtonClickListener(new DkaitAdapter.OnButtonClickListener() {
            @Override
            public void onLookClickListener(int position) {
//                ShopManageBean.DataBean.ListBean bean = mMapList_content.get(pos);
//                ShareUtil.getInstance(WaitApplication.getInstance()).put("edtdetail_id", BigDecimalUtils.bigUtil(bean.getShopid()));
//                ShareUtil.getInstance(WaitApplication.getInstance()).put("channel", "2");
                statActivity(MerchantDetailActivity.class);
            }

            @Override
            public void onKaiTongClickListener(int position) {
                Intent intent = new Intent(WeihPbActivity.this, OpeningActivity.class);
                startActivity(intent);
            }

            @Override
            public void onBohuiClickListener(final int position) {
                MerBohuiDialog build = new MerBohuiDialog.Builder(WeihPbActivity.this).build();
                build.show();
                build.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                build.setOnTextClickListener(new MerBohuiDialog.OnTextClickListener() {
                    @Override
                    public void onRightClickListener(String content) {
                        super.onRightClickListener(content);
                        if (position == 3) {
                            new MiddleDialog.Builder(WeihPbActivity.this).img_id(R.drawable.icon_chenggong).title("商户驳回成功" + content).build().show();
                            mUserCheckSmart.autoRefresh();
                        } else {
                            ToastShow("驳回失败");
                        }
                    }
                });


            }

            @Override
            public void onItemClickListener(int pos) {
                boolean isshifang = mDkaitAdapter.isshifang();
                if (isshifang) {
                    mList_kaitong = mDkaitAdapter.getLists();
                }
                isCanShifang();
            }
        });
        mDshenheAdapter.setOnButtonClickListener(new DshenheAdapter.OnButtonClickListener() {
            @Override
            public void onLookClickListener(int position) {
//                ShopManageBean.DataBean.ListBean bean = mMapList_content.get(pos);
//                ShareUtil.getInstance(WaitApplication.getInstance()).put("edtdetail_id", BigDecimalUtils.bigUtil(bean.getShopid()));
//                ShareUtil.getInstance(WaitApplication.getInstance()).put("channel", "2");
                statActivity(MerchantDetailActivity.class);
            }

            @Override
            public void onKaiTongClickListener(int position) {
                Intent intent = new Intent(WeihPbActivity.this, OpenEndActivity.class);
//                intent.putExtra("")
                startActivity(intent);
            }

            @Override
            public void onBohuiClickListener(int position) {
                ToastShow("审核-经营信息第" + position + "条");
                if (position == 3) {
                    new MiddleDialog.Builder(WeihPbActivity.this).img_id(R.drawable.icon_chenggong).title("商户驳回成功").build().show();
                    mUserCheckSmart.autoRefresh();
                } else {
                    ToastShow("驳回失败");
                }
            }
        });
        mYshangxAdapter.setOnButtonClickListener(new YshangxAdapter.OnButtonClickListener() {
            @Override
            public void onLookClickListener(int position) {
                //                ShopManageBean.DataBean.ListBean bean = mMapList_content.get(pos);
//                ShareUtil.getInstance(WaitApplication.getInstance()).put("edtdetail_id", BigDecimalUtils.bigUtil(bean.getShopid()));
//                ShareUtil.getInstance(WaitApplication.getInstance()).put("channel", "2");
                statActivity(MerchantDetailActivity.class);
            }

            @Override
            public void onKaiTongClickListener(int position) {
                Intent intent = new Intent(WeihPbActivity.this, OpenEndActivity.class);
//                intent.putExtra("")
                startActivity(intent);
            }

            @Override
            public void onBohuiClickListener(int position) {
                Intent intent = new Intent(WeihPbActivity.this, TransDetailActivity.class);
//                intent.putExtra("")
                startActivity(intent);
            }
        });
    }


    @OnClick({R.id.manapb_imgv, R.id.imgv_back, R.id.higheney_search, R.id.higheney_cancel, R.id.merchpool_one, R.id.merchpool_two, R.id.merchpool_three, R.id.merchpool_four,
            R.id.manapb_cancel, R.id.manapb_shifangbot})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgv_back:
                finish();
                break;
            case R.id.manapb_imgv:
                initPop();
                break;
            case R.id.higheney_search:
                String se_content = mHigheneyEdt.getText().toString();
                if (!TextUtils.isEmpty(se_content)) {
                    mUserCheckSmart.autoRefresh();
                } else {
                    ToastShow("请输入搜索内容");
                }
                break;
            case R.id.higheney_cancel:
                String e_content = mHigheneyEdt.getText().toString();
                if (!TextUtils.isEmpty(e_content)) {
                    mHigheneyEdt.setText("");
                    mUserCheckSmart.autoRefresh();
                }
                break;
            case R.id.merchpool_one:
                if (mPopMerchWindow_quyu != null) {
                    mPopMerchWindow_quyu.showAsDropDown(mLinea);
                    merchpool_linear.setVisibility(View.VISIBLE);
                    mMerchpoolImgone.setImageResource(R.drawable.icon_shangla);
                    mMerchpoolHezuo.setTextColor(getResources().getColor(R.color.red));
                }
                break;
            case R.id.merchpool_two:
                if (mPopMerchWindow_shangquan != null) {
                    mPopMerchWindow_shangquan.showAsDropDown(mLinea);
                    merchpool_linear.setVisibility(View.VISIBLE);
                    mMerchpoolImgtwo.setImageResource(R.drawable.icon_shangla);
                    mMerchpoolTuozhan.setTextColor(getResources().getColor(R.color.red));
                }
                break;
            case R.id.merchpool_three:
                if (mJihuoDialog_once != null) {
                    mMerchpoolImgthree.setImageResource(R.drawable.icon_shangla);
                    mMerchpoolTuozhanwangd.setTextColor(getResources().getColor(R.color.red));
                    mJihuoDialog_once.show();
                }
                break;
            case R.id.merchpool_four:
                Intent intent = new Intent(this, WhpbShaixActivity.class);
                intent.putExtra("type", mType);
                startActivity(intent);
                break;
            case R.id.manapb_cancel:
                mManapbShifangbot.setBackgroundColor(this.getResources().getColor(R.color.line_color));
                mManapbShifangbot.setEnabled(false);
                mManapbImgv.setVisibility(View.VISIBLE);
                mManapbShifang.setVisibility(View.GONE);
                mManapbLinear.setVisibility(View.GONE);
                for (int i = 0; i < mList_kaitong.size(); i++) {
                    Map<String, Object> listBean = mList_kaitong.get(i);
                    listBean.put("isCheck", false);
                }
                mDkaitAdapter.setIs_shifang(false);
                mDkaitAdapter.notifyDataSetChanged();
              /*  for (int i = 0; i < mMapWhList.size(); i++) {
                    MainTainBean.DataBean.ListBean listBean = mMapWhList.get(i);
                    listBean.setCheck(false);
                }
                mManaWhAdapter.setIs_shifang(false);
                mManaWhAdapter.notifyDataSetChanged();*/
                break;
            case R.id.manapb_shifangbot:
                mYfsRemindDialog.show();
                break;
        }
    }

    private void getData() {
        switch (mType) {
            case "0":
                getDaikaiTong();
                break;
            case "1":
                getDaishenhe();
                break;
            case "2":
                getYshangxian();
                break;
        }


    }

    private void initPop() {
        mPopShifhWindow = new PopShifhWindow(this);
        mPopShifhWindow.setOnPopClickListener(new PopShifhWindow.OnPopClickListener() {
            @Override
            public void onPopClickListener() {
                mManapbShifang.setVisibility(View.VISIBLE);
                mManapbImgv.setVisibility(View.GONE);
                mManapbLinear.setVisibility(View.VISIBLE);
                mDkaitAdapter.setIs_shifang(true);
                mDkaitAdapter.notifyDataSetChanged();

            }
        });
        mPopShifhWindow.showAsDropDown(mManapbImgv);
        mPopShifhWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(WeihPbActivity.this, 1.0f);
            }
        });
    }

    private void isCanShifang() {
        boolean canshifang = false;
        StringBuilder stringBuilder = new StringBuilder();
        for (Map<String, Object> bean : mList_kaitong) {
            boolean isCheck = (boolean) bean.get("isCheck");
            if (isCheck) {
                canshifang = true;
                stringBuilder.append(bean.get("id"));
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

       /* boolean canshifang = false;
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
        }*/
    }

    /*获取待开通数据*/
    private void getDaikaiTong() {
        if (page == 1) {
            mList_kaitong.clear();
        }
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("isCheck", false);
            map.put("id", "10" + i);
            mList_kaitong.add(map);
        }
        mDkaitAdapter.notifyDataSetChanged();
        mUserCheckSmart.finishRefresh();
        mUserCheckSmart.finishLoadMore();


    }

    /*获取待审核数据*/
    private void getDaishenhe() {
        if (page == 1) {
            mList_shenhe.clear();
        }
        for (int i = 0; i < 10; i++) {
            mList_shenhe.add("");
        }
        mDshenheAdapter.notifyDataSetChanged();
        mUserCheckSmart.finishRefresh();
        mUserCheckSmart.finishLoadMore();


    }/*获取已上线数据*/

    private void getYshangxian() {
        if (page == 1) {
            mList_yshangx.clear();
        }
        for (int i = 0; i < 10; i++) {
            mList_yshangx.add("");
        }
        mYshangxAdapter.notifyDataSetChanged();
        mUserCheckSmart.finishRefresh();
        mUserCheckSmart.finishLoadMore();

    }

    /*-----------------------------------------*/
    private void toShifang() {
        ToastShow("进行释放");
       /* Map<String, Object> map = new HashMap<>();
        map.put("shopId", mShifang_id);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).merchantPoolSearchfreed(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
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
        });*/


    }

    //    获取横向列表
    private void getOnceTab() {
        mMerchpoolTuozhanwangd.setText("行业");
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
                    mJihuoDialog_once = new JihuoDialog(WeihPbActivity.this, mJhDig_mapOnce, "请选择一级行业");
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
                            mMerchpoolTuozhanwangd.setText(hangye_str);
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
                                mMerchpoolImgthree.setImageResource(R.drawable.icon_xiala);
                                mMerchpoolTuozhanwangd.setTextColor(getResources().getColor(R.color.merchool_txt));
                                mUserCheckSmart.autoRefresh();
                            } else {
                                initSmallPop((String) map.get("data"));
                            }
                        }
                    });
                    mJihuoDialog_once.setOnChaClickListener(new JihuoDialog.OnChaClickListener() {
                        @Override
                        public void onChaClickListener(int pos, String what) {
                            mMerchpoolImgthree.setImageResource(R.drawable.icon_xiala);
                            mMerchpoolTuozhanwangd.setTextColor(getResources().getColor(R.color.merchool_txt));
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
        mMerchpoolImgthree.setImageResource(R.drawable.icon_xiala);
        mMerchpoolTuozhanwangd.setTextColor(getResources().getColor(R.color.merchool_txt));
        Map<String, Object> map = mJhDig_mapTwice.get(pos);
        String small_id = (String) map.get("small_id");
        if (what.contains("(")) {
            hangye_str += "•" + what.substring(0, what.indexOf("("));
        } else {
            hangye_str += "•" + what;
        }
        mMerchpoolTuozhanwangd.setText(hangye_str);
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
        /*区域*/
        for (int i = 0; i < 10; i++) {
            BotListBean botListBean = new BotListBean("第" + (i + 1) + "区域", false, i + "");
            mBotListBeans_tuozhan.add(botListBean);
        }
        mPopMerchWindow_quyu = new PopMerchWindow(WeihPbActivity.this, mBotListBeans_tuozhan);
        mPopMerchWindow_quyu.setOnPopClickListener(new PopMerchWindow.OnPopClickListener() {
            @Override
            public void onPopClickListener(String con, String what) {
                String old = mMerchpoolHezuo.getText().toString();
                if (!con.equals(old)) {
                    mMerchpoolHezuo.setText(con);
                    quyu_code = what;
                    page = 1;
                    getData();
//                                mMerchpoolSmart.autoRefresh();
                }

            }
        });

        mPopMerchWindow_quyu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                merchpool_linear.setVisibility(View.GONE);
                mMerchpoolImgone.setImageResource(R.drawable.icon_xiala);
                mMerchpoolHezuo.setTextColor(getResources().getColor(R.color.merchool_txt));
            }
        });
        /*商圈*/
        for (int i = 0; i < 10; i++) {
            BotListBean botListBean = new BotListBean("第" + (i + 1) + "1商圈", false, i + "");
            mBotListBeans_wangdian.add(botListBean);
        }
        mPopMerchWindow_shangquan = new PopMerchWindow(WeihPbActivity.this, mBotListBeans_wangdian);
        mPopMerchWindow_shangquan.setOnPopClickListener(new PopMerchWindow.OnPopClickListener() {
            @Override
            public void onPopClickListener(String con, String what) {
                String old = mMerchpoolTuozhan.getText().toString();
                if (!con.equals(old)) {
                    mMerchpoolTuozhan.setText(con);
                    shangquan_code = what;
                    page = 1;
                    getData();
//                                mMerchpoolSmart.autoRefresh();
                }

            }
        });

        mPopMerchWindow_shangquan.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                merchpool_linear.setVisibility(View.GONE);
                mMerchpoolImgtwo.setImageResource(R.drawable.icon_xiala);
                mMerchpoolTuozhan.setTextColor(getResources().getColor(R.color.merchool_txt));
            }
        });


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
