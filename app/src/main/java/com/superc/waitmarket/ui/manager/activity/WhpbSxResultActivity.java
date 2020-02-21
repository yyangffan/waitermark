package com.superc.waitmarket.ui.manager.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.superc.waitmarket.R;
import com.superc.waitmarket.adapter.DkaitAdapter;
import com.superc.waitmarket.adapter.DshenheAdapter;
import com.superc.waitmarket.adapter.ReceiveAdapter;
import com.superc.waitmarket.adapter.YshangxAdapter;
import com.superc.waitmarket.ui.activity.MerchantDetailActivity;
import com.superc.waitmarket.utils.dialog.MerBohuiDialog;
import com.superc.waitmarket.utils.dialog.MiddleDialog;
import com.superc.waitmarket.utils.dialog.PhaseRemindDialog;
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

/********************************************************************
 @version: 1.0.0
 @description: type 3:领取 0：待开通 1：待审核 2：已上线
 @author: 杨帆
 @time: 2020/2/13 16:21
 @变更历史:
 ********************************************************************/
public class WhpbSxResultActivity extends BaseActivity {
    @BindView(R.id.screen_result_recy)
    RecyclerView mScreenResultRecy;
    @BindView(R.id.screen_result_smart)
    SmartRefreshLayout mScreenResultSmart;
    @BindView(R.id.textView112)
    TextView mTvNum;
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
    @BindView(R.id.screen_result_nodata)
    ImageView mImageNNoData;

    private String mName, mTuozh, mShenfNum, mShangq, mQuyu, mCity, mErji, mYiji, mXiaoer, mQuany, mYyzzb, mYihCode, mSt_time, mEd_time, mUser_id, receive_code,mLiq,mKait;
    private int page = 1;
    private String mType, mShifang_id;
    private List<String> mList_lingq;
    private ReceiveAdapter mReceiveAdapter;
    private List<Map<String, Object>> mList_kaitong;
    List<String> mList_shenhe;
    List<String> mList_yshangx;
    private DkaitAdapter mDkaitAdapter;
    private DshenheAdapter mDshenheAdapter;
    private YshangxAdapter mYshangxAdapter;
    private PhaseRemindDialog mPhaseRemindDialog;
    private PopShifhWindow mPopShifhWindow;
    private YfsRemindDialog mYfsRemindDialog;


    @Override
    public int getContentLayoutId() {
        return R.layout.activity_whpb_sx_result;
    }

    @Override
    public void init() {
        TitleUtils.setStatusTextColor(true, this);
        ButterKnife.bind(this);
        mYfsRemindDialog = new YfsRemindDialog.Builder(this).title("是否确认释放该商户").left("取消").right("确认").build();
        mYfsRemindDialog.setOnTextClickListener(new YfsRemindDialog.OnTextClickListener() {
            @Override
            public void onRightClickListener() {
                super.onRightClickListener();
                toShifang();
            }
        });
        mList_kaitong = new ArrayList<>();
        mList_shenhe = new ArrayList<>();
        mList_yshangx = new ArrayList<>();
        mList_lingq = new ArrayList<>();
        mReceiveAdapter = new ReceiveAdapter(this, mList_lingq);
        mDkaitAdapter = new DkaitAdapter(this, mList_kaitong);
        mDshenheAdapter = new DshenheAdapter(this, mList_shenhe);
        mYshangxAdapter = new YshangxAdapter(this, mList_yshangx);
        mUser_id = (String) ShareUtil.getInstance(this).get("user_id", "");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mScreenResultRecy.setLayoutManager(linearLayoutManager);
        mPhaseRemindDialog = new PhaseRemindDialog.Builder(WhpbSxResultActivity.this).title("确定领取此商户").left("取消").right("确认").build(WhpbSxResultActivity.this.getWindowManager().getDefaultDisplay());
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getBundleExtra("data");
            if (extras != null) {
                mName = extras.getString("name", "");
                mTuozh = extras.getString("tuozh", "");
                mQuany = extras.getString("quany", "");
                mXiaoer = extras.getString("xiaoer", "");
                mShenfNum = extras.getString("shenfNum", "");
                mYyzzb = extras.getString("yyzzb", "");
                mYihCode = extras.getString("yihCode", "");
                mYiji = extras.getString("yiji", "");
                mErji = extras.getString("erji", "");
                mCity = extras.getString("city", "");
                mQuyu = extras.getString("quyu", "");
                mShangq = extras.getString("shangq", "");
                mSt_time = extras.getString("st_time", "");
                mEd_time = extras.getString("ed_time", "");
                mLiq = extras.getString("liq", "");
                mKait = extras.getString("kait", "");
                mType = extras.getString("type");
            }
        }
        mScreenResultSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getData();
            }
        });
        mScreenResultSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ++page;
                getData();
            }
        });
        mScreenResultSmart.autoRefresh();
        initWhat();

    }

    @OnClick({R.id.imgv_back, R.id.manapb_imgv, R.id.manapb_cancel, R.id.manapb_shifangbot})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgv_back:
                finish();
                break;
            case R.id.manapb_imgv:
                initPop();
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

    private void initWhat() {
        switch (mType) {
            case "3":
                mScreenResultRecy.setAdapter(mReceiveAdapter);
                break;
            case "0":
                mManapbImgv.setVisibility(View.VISIBLE);
                mScreenResultRecy.setAdapter(mDkaitAdapter);
                break;
            case "1":
                mScreenResultRecy.setAdapter(mDshenheAdapter);
                break;
            case "2":
                mScreenResultRecy.setAdapter(mYshangxAdapter);
                break;
        }
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
                    new MiddleDialog.Builder(WhpbSxResultActivity.this).title("领取成功").img_id(R.drawable.icon_chenggong).build().show();
                } else {
                    new MiddleDialog.Builder(WhpbSxResultActivity.this).title("领取失败").img_id(R.drawable.icon_weidabiao).build().show();
                }
            }
        });

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
                Intent intent = new Intent(WhpbSxResultActivity.this, OpeningActivity.class);
                startActivity(intent);
            }

            @Override
            public void onBohuiClickListener(final int position) {
                MerBohuiDialog build = new MerBohuiDialog.Builder(WhpbSxResultActivity.this).build();
                build.show();
                build.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                build.setOnTextClickListener(new MerBohuiDialog.OnTextClickListener() {
                    @Override
                    public void onRightClickListener(String content) {
                        super.onRightClickListener(content);
                        if (position == 3) {
                            new MiddleDialog.Builder(WhpbSxResultActivity.this).img_id(R.drawable.icon_chenggong).title("商户驳回成功" + content).build().show();
                            mScreenResultSmart.autoRefresh();
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
                Intent intent = new Intent(WhpbSxResultActivity.this, OpenEndActivity.class);
//                intent.putExtra("")
                startActivity(intent);
            }

            @Override
            public void onBohuiClickListener(int position) {
                ToastShow("审核-经营信息第" + position + "条");
                if (position == 3) {
                    new MiddleDialog.Builder(WhpbSxResultActivity.this).img_id(R.drawable.icon_chenggong).title("商户驳回成功").build().show();
                    mScreenResultSmart.autoRefresh();
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
                Intent intent = new Intent(WhpbSxResultActivity.this, OpenEndActivity.class);
//                intent.putExtra("")
                startActivity(intent);
            }

            @Override
            public void onBohuiClickListener(int position) {
                Intent intent = new Intent(WhpbSxResultActivity.this, ShMerdetailActivity.class);
//                intent.putExtra("")
                startActivity(intent);
            }
        });
    }

    private void getData() {
       /* Map<String, Object> map = new HashMap<>();
        map.put("name", mName);
        map.put("tuozh", mTuozh);
        map.put("quany", mQuany);
        map.put("xiaoer", mXiaoer);
        map.put("shenfNum", mShenfNum);
        map.put("yyzzb", mYyzzb);
        map.put("yihCode", mYihCode);
        map.put("yiji", mYiji);
        map.put("erji", mErji);
        map.put("city", mCity);
        map.put("quyu", mQuyu);
        map.put("shangq", mShangq);
        map.put("st_time", mSt_time);
        map.put("ed_time", mEd_time);
        map.put("mLiq", mLiq);
        map.put("mKait", mKait);
        mTvNum.setText("共223家符合要求商户");*/

        switch (mType) {
            case "3":
                getLingq();
                break;
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

    /*领取数据*/
    private void getLingq() {
        if(page==3){
            mImageNNoData.setVisibility(View.VISIBLE);
            mScreenResultSmart.setVisibility(View.GONE);
            mTvNum.setVisibility(View.GONE);
        }
        if (page == 1) {
            mList_lingq.clear();
        }
        for (int i = 0; i < 10; i++) {
            mList_lingq.add("");
        }
        mTvNum.setText("共223家符合要求商户");
        mReceiveAdapter.notifyDataSetChanged();
        mScreenResultSmart.finishRefresh();
        mScreenResultSmart.finishLoadMore();

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
                backgroundAlpha(WhpbSxResultActivity.this, 1.0f);
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
        mTvNum.setText("共224家符合要求商户");
        mDkaitAdapter.notifyDataSetChanged();
        mScreenResultSmart.finishRefresh();
        mScreenResultSmart.finishLoadMore();


    }

    /*获取待审核数据*/
    private void getDaishenhe() {
        if (page == 1) {
            mList_shenhe.clear();
        }
        for (int i = 0; i < 10; i++) {
            mList_shenhe.add("");
        }
        mTvNum.setText("共225家符合要求商户");
        mDshenheAdapter.notifyDataSetChanged();
        mScreenResultSmart.finishRefresh();
        mScreenResultSmart.finishLoadMore();


    }/*获取已上线数据*/

    private void getYshangxian() {
        if (page == 1) {
            mList_yshangx.clear();
        }
        for (int i = 0; i < 10; i++) {
            mList_yshangx.add("");
        }
        mTvNum.setText("共226家符合要求商户");
        mYshangxAdapter.notifyDataSetChanged();
        mScreenResultSmart.finishRefresh();
        mScreenResultSmart.finishLoadMore();

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
