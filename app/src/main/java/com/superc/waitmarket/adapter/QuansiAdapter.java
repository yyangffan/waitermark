package com.superc.waitmarket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.superc.waitmarket.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuansiAdapter extends RecyclerView.Adapter<QuansiAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mLists;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public QuansiAdapter(Context context, List<String> stringList) {
        mContext = context;
        mLists = stringList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_quans, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int position) {
        String bean = mLists.get(position);
        vh.mItemAllmarketTitle.setText("一分区");
        vh.mItemAllmarketDetail.setText(" · 共3个小组，6位员工");
        vh.mItemAllmarketOne.setText("35"+position);
        vh.mItemAllmarketTwo.setText("456");
        vh.mItemAllmarketThree.setText("¥512");
        vh.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null )
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
        @BindView(R.id.item_allmarket_detail)
        TextView mItemAllmarketDetail;
        View mView;

        ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }
}
