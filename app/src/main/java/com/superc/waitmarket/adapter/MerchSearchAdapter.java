package com.superc.waitmarket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.superc.waitmarket.R;
import com.superc.waitmarket.bean.SearchBean;
import com.superc.waitmarket.utils.BigDecimalUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MerchSearchAdapter extends RecyclerView.Adapter<MerchSearchAdapter.ViewHolder> {
    private Context mContext;
    private List<SearchBean.DataBean> mLists;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public MerchSearchAdapter(Context context, List<SearchBean.DataBean> stringList) {
        mContext = context;
        mLists = stringList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_search, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int position) {
        SearchBean.DataBean bean = mLists.get(position);
        vh.mItemSearchName.setText(bean.getShopName());
        vh.mItemSearchPos.setText(bean.getShopAddress());
        String auditstatus = BigDecimalUtils.bigUtil(bean.getAuditstatus());
        switch (auditstatus) {
            case "1":
                vh.mItemSearchState.setText("可拓展");
                vh.mItemSearchState.setTextColor(mContext.getResources().getColor( R.color.txt_yell));
                break;
            case "2":
                vh.mItemSearchState.setText("不可拓");
                vh.mItemSearchState.setTextColor(mContext.getResources().getColor( R.color.txt_blue));
                break;
            case "3":
                vh.mItemSearchState.setText("可领取");
                vh.mItemSearchState.setTextColor(mContext.getResources().getColor( R.color.txt_yell));
                break;
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
        @BindView(R.id.item_search_name)
        TextView mItemSearchName;
        @BindView(R.id.item_search_state)
        TextView mItemSearchState;
        @BindView(R.id.item_search_pos)
        TextView mItemSearchPos;
        View mView;

        ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }
}
