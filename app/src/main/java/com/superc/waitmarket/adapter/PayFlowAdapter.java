package com.superc.waitmarket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.superc.waitmarket.R;
import com.superc.waitmarket.bean.PayflowBean;
import com.superc.waitmarket.utils.BigDecimalUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayFlowAdapter extends RecyclerView.Adapter<PayFlowAdapter.ViewHolder> {
    private Context mContext;
    private List<PayflowBean.DataBean.DataListBean> mLists;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public PayFlowAdapter(Context context, List<PayflowBean.DataBean.DataListBean> stringList) {
        mContext = context;
        mLists = stringList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_payflow, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int position) {
        PayflowBean.DataBean.DataListBean bean = mLists.get(position);

        vh.mItemPayflowDate.setText(bean.getDate());
        vh.mItemPayflowMoney.setText("¥" +BigDecimalUtils.bigUtil(bean.getSum()));
        vh.mItemPayflowEnd.setText("来自"+BigDecimalUtils.bigUtil(bean.getCount())+"家商户");
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
        @BindView(R.id.item_payflow_date)
        TextView mItemPayflowDate;
        @BindView(R.id.item_payflow_money)
        TextView mItemPayflowMoney;
        @BindView(R.id.item_payflow_end)
        TextView mItemPayflowEnd;
        @BindView(R.id.item_payflow_imgv)
        ImageView mItemPayflowImgv;
        View mView;

        ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }
}
