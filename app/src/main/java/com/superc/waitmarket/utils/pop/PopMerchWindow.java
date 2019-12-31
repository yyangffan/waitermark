package com.superc.waitmarket.utils.pop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.superc.waitmarket.R;
import com.superc.waitmarket.adapter.PopMerchAdapter;
import com.superc.waitmarket.bean.BotListBean;

import java.util.List;

public class PopMerchWindow extends PopupWindow {
    private View mView;
    private PopMerchWindow mOverallPop;
    private List<BotListBean> mMapList;
    private PopMerchAdapter mPopMerchAdapter;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private OnPopClickListener mOnPopClickListener;

    public PopMerchWindow(Context context,  List<BotListBean> mMapList) {
        super(context);
        mContext = context;
        mOverallPop = this;
        mView = LayoutInflater.from(context).inflate(R.layout.pop_merch, null);
        this.mMapList = mMapList;
        init();
        initPopWindow();

    }

    public void setOnPopClickListener(OnPopClickListener onPopClickListener) {
        mOnPopClickListener = onPopClickListener;
    }

    private void initPopWindow() {
        this.setContentView(mView);
        setAnimationStyle(R.style.Pop_inOut_anim);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        if (mMapList.size()>7) {
            setHeight(1000);
        } else {
            setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        setTouchable(true);
        setOutsideTouchable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00FFFFFF);
        //设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
//        backgroundAlpha(context, 0.5f);//0.0-1.0
    }

    private void init() {
        mRecyclerView = mView.findViewById(R.id.pop_merch_recy);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mPopMerchAdapter = new PopMerchAdapter(mContext, mMapList);
        mRecyclerView.setAdapter(mPopMerchAdapter);
        mPopMerchAdapter.setOnItemClickListener(new PopMerchAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int pos) {
                for (int i = 0; i < mMapList.size(); i++) {
                    BotListBean map = mMapList.get(i);
                    if (i == pos) {
                        map.setType(true);
                        if(mOnPopClickListener!=null)
                            mOnPopClickListener.onPopClickListener(map.getName(),map.getWhat());
                    } else {
                        map.setType(false);
                    }
                }
                mPopMerchAdapter.notifyDataSetChanged();
                mOverallPop.dismiss();

            }
        });
    }

    public void setMapList(List<BotListBean> mapList) {
        mMapList = mapList;
        mPopMerchAdapter.notifyDataSetChanged();
    }

    public interface OnPopClickListener{
        void onPopClickListener(String con,String what);
    }


}
