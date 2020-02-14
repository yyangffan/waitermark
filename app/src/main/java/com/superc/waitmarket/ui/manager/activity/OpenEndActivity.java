package com.superc.waitmarket.ui.manager.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.superc.waitmarket.R;
import com.superc.waitmarket.adapter.ChangjLookAdapter;
import com.superc.waitmarket.base.Constant;
import com.superc.waitmarket.utils.picselector.FullyGridLayoutManager;
import com.superc.yyfflibrary.base.BaseActivity;
import com.superc.yyfflibrary.utils.titlebar.TitleUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OpenEndActivity extends BaseActivity {

    @BindView(R.id.screen_name)
    TextView mScreenName;
    @BindView(R.id.screen_mianji)
    TextView mScreenMianji;
    @BindView(R.id.screen_num)
    TextView mScreenNum;
    @BindView(R.id.screen_kedanjia)
    TextView mScreenKedanjia;
    @BindView(R.id.item_img_qianshou)
    ImageView mItemImgQianshou;
    @BindView(R.id.item_yinxiang_img)
    ImageView mItemYinxiangImg;
    @BindView(R.id.item_mentie_img)
    ImageView mItemMentieImg;
    @BindView(R.id.item_yinliang_img)
    ImageView mItemYinliangImg;
    @BindView(R.id.item_yunshanfu_img)
    ImageView mItemYunshanfuImg;
    @BindView(R.id.item_zfb_img)
    ImageView mItemZfbImg;
    @BindView(R.id.item_weixin_img)
    ImageView mItemWeixinImg;
    @BindView(R.id.item_shoukuanlq_img)
    ImageView mItemShoukuanlqImg;
    @BindView(R.id.item_hangjpiclook_recy)
    RecyclerView mItemHangjpiclookRecy;
    @BindView(R.id.smartlayout)
    SmartRefreshLayout mSmartlayout;
    private ChangjLookAdapter mChangjLookAdapter;
    private List<Map<String, Object>> mMapList;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_open_end;
    }

    @Override
    public void init() {
        TitleUtils.setStatusTextColor(true, this);
        ButterKnife.bind(this);
        mSmartlayout.setEnableOverScrollDrag(true);
        mSmartlayout.setEnablePureScrollMode(true);
        mMapList = new ArrayList<>();
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        mItemHangjpiclookRecy.setLayoutManager(manager);
        mChangjLookAdapter = new ChangjLookAdapter(this, mMapList);
        mItemHangjpiclookRecy.setAdapter(mChangjLookAdapter);
        getData();
    }


    @OnClick(R.id.imgv_back)
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.imgv_back:
                finish();
                break;
        }
    }

    private void getData() {
        mScreenName.setText("1241424");
        mScreenMianji.setText("124142");
        mScreenNum.setText("142142");
        mScreenKedanjia.setText("12");
        RoundedCorners roundedCorners = new RoundedCorners(10);
        RequestOptions override = RequestOptions.bitmapTransform(roundedCorners).error(R.drawable.icon_error).placeholder(R.drawable.icon_error).centerCrop().override(300, 300);
        Glide.with(this).load("").apply(override).into(mItemImgQianshou);
        Glide.with(this).load("").apply(override).into(mItemYinxiangImg);
        Glide.with(this).load("").apply(override).into(mItemMentieImg);
        Glide.with(this).load("").apply(override).into(mItemYinliangImg);
        Glide.with(this).load("").apply(override).into(mItemYunshanfuImg);
        Glide.with(this).load("").apply(override).into(mItemZfbImg);
        Glide.with(this).load("").apply(override).into(mItemWeixinImg);
        Glide.with(this).load("").apply(override).into(mItemShoukuanlqImg);
        mMapList.clear();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("img_url", Constant.IMG_URL + "");
            mMapList.add(map);
        }
        mChangjLookAdapter.notifyDataSetChanged();


    }

    private void setData(JSONObject merchant) {
       /* try {
            mMapList.clear();
            String mPicSmallPath = merchant.getString("mpicPath");
            RoundedCorners roundedCorners = new RoundedCorners(10);
            RequestOptions override = RequestOptions.bitmapTransform(roundedCorners).error(R.drawable.icon_error).placeholder(R.drawable.icon_error).override(300, 300);
            RequestOptions requestOptions = new RequestOptions().error(R.drawable.icon_error).apply(override).placeholder(R.drawable.icon_error);
            if (!TextUtils.isEmpty(mPicSmallPath)) {
                if (mPicSmallPath.startsWith("http") || mPicSmallPath.startsWith("https")) {
                    Glide.with(this).load(mPicSmallPath).apply(requestOptions).into(mItemHangjpiclookHead);
                } else {
                    Glide.with(this).load(Constant.IMG_URL + mPicSmallPath).apply(requestOptions).into(mItemHangjpiclookHead);
                }
            }
            JSONArray timeList = merchant.getJSONArray("timeList");
            for (int i = 0; i < timeList.size(); i++) {
                Map<String,Object>  map=new HashMap<>();
                String lpicPath = timeList.getJSONObject(i).getString("lpicPath");
                if (!TextUtils.isEmpty(lpicPath)) {
                    if (lpicPath.startsWith("http") || lpicPath.startsWith("https")) {
                        map.put("img_url",lpicPath);
                    } else {
                        map.put("img_url",Constant.IMG_URL + lpicPath);
                    }
                    mMapList.add(map);
                }
            }
            mChangjLookAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
*/

    }

}
