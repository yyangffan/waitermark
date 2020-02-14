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

public class ShoukmAdapter extends RecyclerView.Adapter<ShoukmAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mLists;
    private LayoutInflater mInflater;

    public ShoukmAdapter(Context context, List<String> stringList) {
        mContext = context;
        mLists = stringList;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_shoukcode, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        String bean = mLists.get(position);
        vh.mItemShoukcodeName.setText("收款码" + (position + 1));
        vh.mItemShoukcodeNum.setText("12345" + position);
    }

    @Override
    public int getItemCount() {
        return mLists == null ? 0 : mLists.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_shoukcode_name)
        TextView mItemShoukcodeName;
        @BindView(R.id.item_shoukcode_num)
        TextView mItemShoukcodeNum;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
