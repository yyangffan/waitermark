package com.superc.waitmarket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.superc.waitmarket.R;
import com.superc.waitmarket.bean.SaryIndustryBean;
import com.superc.waitmarket.utils.BigDecimalUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BudnesTabAdapter extends RecyclerView.Adapter<BudnesTabAdapter.ViewHolder> {
    private Context mContext;
    private List<SaryIndustryBean.DataBean.SecondaryIndustryBean> mLists;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;
    private float mX = 0;

    public BudnesTabAdapter(Context context, List<SaryIndustryBean.DataBean.SecondaryIndustryBean> stringList) {
        mContext = context;
        mLists = stringList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_busnes_tab, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int position) {
        SaryIndustryBean.DataBean.SecondaryIndustryBean bean = mLists.get(position);
        vh.mItemTabTitle.setText(bean.getTypeName());
        vh.mItemTabNum.setText("(" + BigDecimalUtils.bigUtil(bean.getSum()) + "家)");
        boolean is_check = (boolean) bean.isIs_check();
        vh.mItemTabLine.setVisibility(is_check ? View.VISIBLE : View.GONE);
        vh.mItemTabTitle.setTextSize(is_check ? 16 : 15);
        TextPaint paint = vh.mItemTabTitle.getPaint();
        paint.setFakeBoldText(is_check?true:false);
        vh.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClickListener(position, mX);
            }
        });

        vh.mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mX = motionEvent.getRawX();
                return false;
            }
        });



    }

    @Override
    public int getItemCount() {
        return mLists == null ? 0 : mLists.size();
    }

    public interface OnItemClickListener {
        void onItemClickListener(int pos, float x);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_tab_title)
        TextView mItemTabTitle;
        @BindView(R.id.item_tab_num)
        TextView mItemTabNum;
        @BindView(R.id.item_tab_line)
        View mItemTabLine;
        View mView;

        ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }
}
