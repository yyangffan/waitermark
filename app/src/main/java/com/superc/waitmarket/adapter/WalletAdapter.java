package com.superc.waitmarket.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.superc.waitmarket.R;
import com.superc.waitmarket.bean.ProfitBean;
import com.superc.waitmarket.utils.BigDecimalUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder> {
    private Context mContext;
    private List<ProfitBean.DataBean> mLists;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;
    private WalletChAdapter mWalletChAdapter;

    public WalletAdapter(Context context, List<ProfitBean.DataBean> stringList) {
        mContext = context;
        mLists = stringList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_expand_father, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        ProfitBean.DataBean bean = mLists.get(position);
        vh.mItemExpandfaTitle.setText(bean.getMonth());
        vh.mItemExpandfaWhat.setText("共收¥" + BigDecimalUtils.bigUtil(bean.getAllSum()) + "（交易流水收益¥" + BigDecimalUtils.bigUtil(bean.getMarketingAmount()) + "，商户扩展收益¥" + BigDecimalUtils.bigUtil(bean.getExpandAmount()) + "）");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        vh.mItemExpandfaRecy.setLayoutManager(linearLayoutManager);
//        List<Map<String,Object>> bean.get("data");
        mWalletChAdapter = new WalletChAdapter(mContext, bean.getDetailList());
        vh.mItemExpandfaRecy.setAdapter(mWalletChAdapter);

    }

    @Override
    public int getItemCount() {
        return mLists == null ? 0 : mLists.size();
    }

    public interface OnItemClickListener {
        void onItemClickListener(int pos);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_expandfa_title)
        TextView mItemExpandfaTitle;
        @BindView(R.id.item_expandfa_what)
        TextView mItemExpandfaWhat;
        @BindView(R.id.item_expandfa_recy)
        RecyclerView mItemExpandfaRecy;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
