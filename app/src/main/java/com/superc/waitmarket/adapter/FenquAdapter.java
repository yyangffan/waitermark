package com.superc.waitmarket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.superc.waitmarket.R;
import com.superc.waitmarket.base.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class FenquAdapter extends RecyclerView.Adapter<FenquAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mLists;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public FenquAdapter(Context context, List<String> stringList) {
        mContext = context;
        mLists = stringList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_fenqu, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        String bea = mLists.get(position);
        String img_url = "";
        RequestOptions requestOptions = new RequestOptions().error(R.drawable.icon_error).placeholder(R.drawable.icon_error);
        Glide.with(mContext).load(Constant.IMG_URL + img_url).apply(requestOptions).into(vh.mImageView);
        vh.mItemAllmarketTitle.setText("名称名称  员工工号123"+position);
        vh.mItemAllmarketName.setText("三组。");
        vh.mItemAllmarketOne.setText("451");
        vh.mItemAllmarketTwo.setText("44451");
        vh.mItemAllmarketThree.setText("¥5541");

    }

    @Override
    public int getItemCount() {
        return mLists == null ? 0 : mLists.size();
    }

    public interface OnItemClickListener {
        void onItemClickListener(int pos);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView)
        CircleImageView mImageView;
        @BindView(R.id.item_allmarket_title)
        TextView mItemAllmarketTitle;
        @BindView(R.id.item_allmarket_name)
        TextView mItemAllmarketName;
        @BindView(R.id.item_allmarket_one)
        TextView mItemAllmarketOne;
        @BindView(R.id.item_allmarket_two)
        TextView mItemAllmarketTwo;
        @BindView(R.id.item_allmarket_three)
        TextView mItemAllmarketThree;
        View mView;

        ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }
}
