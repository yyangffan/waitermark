package com.superc.waitmarket.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.superc.waitmarket.R;
import com.superc.waitmarket.bean.ShanghuDetailBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpandAdapter extends RecyclerView.Adapter<ExpandAdapter.ViewHolder> {
    private Context mContext;
    private List<ShanghuDetailBean.DataBean.DataListBean> mLists;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;
    private boolean isYh;

    public ExpandAdapter(Context context, List<ShanghuDetailBean.DataBean.DataListBean> stringList) {
        mContext = context;
        mLists = stringList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public boolean isYh() {
        return isYh;
    }

    public void setYh(boolean yh) {
        isYh = yh;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_expand, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int position) {
        ShanghuDetailBean.DataBean.DataListBean bean = mLists.get(position);
        if (!isYh()) {
            vh.mCon.setVisibility(View.GONE);
            vh.mItemExpandJingLNum.setVisibility(View.VISIBLE);
            vh.mItemExpandDate.setText(bean.getDate());
            vh.mItemExpandJingLNum.setText((bean.getCount1()) + "家商户");
        }else {
            vh.mItemExpandDate.setText(bean.getDate());
            vh.mItemExpandNum.setText(bean.getCount1() + bean.getCount2() + "家");
            vh.mItemExpandEnd.setText("自拓展" + (bean.getCount1()) + "家，小二委派" + bean.getCount2() + "家");
        }
        vh.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClickListener(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mLists == null ? 0 : mLists.size();
    }

    public interface OnItemClickListener {
        void onItemClickListener(int pos);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_expand_date)
        TextView mItemExpandDate;
        @BindView(R.id.item_expand_num)
        TextView mItemExpandNum;
        @BindView(R.id.item_expand_end)
        TextView mItemExpandEnd;
        @BindView(R.id.item_expand_num_jingl)
        TextView mItemExpandJingLNum;
        @BindView(R.id.con)
        ConstraintLayout mCon;
        View mView;

        ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }
}
