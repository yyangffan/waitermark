package com.superc.waitmarket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.superc.waitmarket.R;
import com.superc.waitmarket.bean.ProfitBean;
import com.superc.waitmarket.utils.BigDecimalUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalletChAdapter extends RecyclerView.Adapter<WalletChAdapter.ViewHolder> {
    private Context mContext;
    private List<ProfitBean.DataBean.DetailListBean> mLists;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;
    private boolean mYinhang;

    public WalletChAdapter(Context context, List<ProfitBean.DataBean.DetailListBean> stringList,boolean yihang) {
        mContext = context;
        mLists = stringList;
        mYinhang=yihang;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_expand_child, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int position) {
        ProfitBean.DataBean.DetailListBean bean = mLists.get(position);
        if (mYinhang) {
            int type = bean.getType();
            vh.mItemChildTitle.setText(type == 1 ? "商户拓展收益" : "交易流水收益");
            vh.mItemChildYue.setText("账户余额：" + BigDecimalUtils.bigUtil(bean.getBalance() + ""));
            vh.mItemChildMoney.setText("+" + BigDecimalUtils.bigUtil(bean.getAmount()));
            vh.mItemChildTime.setText(bean.getAddtime());
            vh.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mOnItemClickListener!=null)
                        mOnItemClickListener.onItemClickListener(position);
                }
            });
        } else {
            int type = bean.getType();
            vh.mItemChildTitle.setText(type == 1 ? "商户拓展收益" : "交易流水收益");
//            vh.mItemChildYue.setText("账户余额：" + BigDecimalUtils.bigUtil(bean.getBalance() + ""));
            vh.mItemChildMoney.setText("+" + BigDecimalUtils.bigUtil(bean.getAmount()));
            vh.mItemChildTime.setText(bean.getAddtime());
            vh.mItemChildYue.setVisibility(View.INVISIBLE);
            vh.mImage_more.setVisibility(View.VISIBLE);
            vh.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mOnItemClickListener!=null)
                        mOnItemClickListener.onItemClickListener(position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mLists == null ? 0 : mLists.size();
    }

    public interface OnItemClickListener {
        void onItemClickListener(int pos);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_child_title)
        TextView mItemChildTitle;
        @BindView(R.id.item_child_yue)
        TextView mItemChildYue;
        @BindView(R.id.item_child_money)
        TextView mItemChildMoney;
        @BindView(R.id.item_child_time)
        TextView mItemChildTime;
        @BindView(R.id.imageView10)
        ImageView mImage_more;
        View mView;

        ViewHolder(View view) {
            super(view);
            mView=view;
            ButterKnife.bind(this, view);
        }
    }
}
