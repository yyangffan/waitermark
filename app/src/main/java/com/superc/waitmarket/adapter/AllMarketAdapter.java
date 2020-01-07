package com.superc.waitmarket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.superc.waitmarket.R;
import com.superc.waitmarket.bean.YingxiaoBean;
import com.superc.waitmarket.utils.BigDecimalUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllMarketAdapter extends RecyclerView.Adapter<AllMarketAdapter.ViewHolder> {
    private Context mContext;
    private List<YingxiaoBean> mLists;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public AllMarketAdapter(Context context, List<YingxiaoBean> stringList) {
        mContext = context;
        mLists = stringList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_allmarket, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int position) {
        YingxiaoBean bean = mLists.get(position);
        vh.mItemAllmarketTitle.setText(bean.getOne_content());
        vh.mItemAllmarketOne.setText(BigDecimalUtils.bigUtil(bean.getTwo_content()));
        vh.mItemAllmarketTwo.setText("¥" + bean.getThree_content());
        vh.mItemAllmarketThree.setText(BigDecimalUtils.bigUtil(bean.getFour_content()));
        final String id = bean.getId();
        vh.mItemAllmarketMore.setVisibility(TextUtils.isEmpty(id) ? View.GONE : View.VISIBLE);
        vh.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null && !TextUtils.isEmpty(id))
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
        @BindView(R.id.item_allmarket_title)
        TextView mItemAllmarketTitle;
        @BindView(R.id.item_allmarket_one)
        TextView mItemAllmarketOne;
        @BindView(R.id.item_allmarket_two)
        TextView mItemAllmarketTwo;
        @BindView(R.id.item_allmarket_three)
        TextView mItemAllmarketThree;
        @BindView(R.id.item_allmarket_name)
        TextView mItemAllmarketMore;

        View mView;

        ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }
}
