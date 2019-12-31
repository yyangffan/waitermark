package com.superc.waitmarket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.superc.waitmarket.R;
import com.superc.waitmarket.bean.MerchantBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MerChantAdapter extends RecyclerView.Adapter<MerChantAdapter.ViewHolder> {
    private Context mContext;
    private List<MerchantBean.DataBean.DataListBean> mLists;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public MerChantAdapter(Context context, List<MerchantBean.DataBean.DataListBean> stringList) {
        mContext = context;
        mLists = stringList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_merchant, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int position) {
        MerchantBean.DataBean.DataListBean bean = mLists.get(position);
        vh.mItemMerchantDate.setText(bean.getDate());
        vh.mItemMerchantNum.setText(bean.getCount() + "次");
        vh.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClickListener(position);
                }
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
        @BindView(R.id.item_merchant_date)
        TextView mItemMerchantDate;
        @BindView(R.id.item_merchant_num)
        TextView mItemMerchantNum;
        View mView;

        ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }
}
