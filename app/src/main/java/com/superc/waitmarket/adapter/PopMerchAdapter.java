package com.superc.waitmarket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.superc.waitmarket.R;
import com.superc.waitmarket.bean.BotListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PopMerchAdapter extends RecyclerView.Adapter<PopMerchAdapter.ViewHolder> {
    private Context mContext;
    private List<BotListBean> mLists;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public PopMerchAdapter(Context context, List<BotListBean> stringList) {
        mContext = context;
        mLists = stringList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_merch_pop, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int position) {
        BotListBean bean = mLists.get(position);
        boolean is_check = (boolean) bean.isType();
        vh.mItemMerchpopTitle.setText(bean.getName());
        vh.mItemMerchpopTitle.setTextColor(mContext.getResources().getColor(is_check ? R.color.main_color : R.color.black));
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
        @BindView(R.id.item_merchpop_title)
        TextView mItemMerchpopTitle;
        View mView;

        ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }
}
