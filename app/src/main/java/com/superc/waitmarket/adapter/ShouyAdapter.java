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

public class ShouyAdapter extends RecyclerView.Adapter<ShouyAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mLists;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public ShouyAdapter(Context context, List<String> stringList) {
        mContext = context;
        mLists = stringList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_shouydetail, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        String bean = mLists.get(position);
//        String shoplogo = bean.getShoplogo();
        String shoplogo = "";
        RequestOptions requestOptions = new RequestOptions().error(R.drawable.icon_error).placeholder(R.drawable.icon_error);
        Glide.with(mContext).load(Constant.IMG_URL + shoplogo).apply(requestOptions).into(vh.mItemPatdetailImgv);
        vh.mItemPatdetailTitle.setText("正新鸡排·小串·烧烤");
        vh.mItemPatdetailPosition.setText("天津市河东区万达广场A-" + position);
        vh.mItemPatdetailTime.setText("上线时间：2019.12.1" + position);
        vh.mItemPatdetailMoney.setText("+¥12" + position);


    }

    @Override
    public int getItemCount() {
        return mLists == null ? 0 : mLists.size();
    }

    public interface OnItemClickListener {
        void onItemClickListener(int pos);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_patdetail_title)
        TextView mItemPatdetailTitle;
        @BindView(R.id.item_patdetail_position)
        TextView mItemPatdetailPosition;
        @BindView(R.id.item_patdetail_time)
        TextView mItemPatdetailTime;
        @BindView(R.id.item_patdetail_imgv)
        CircleImageView mItemPatdetailImgv;
        @BindView(R.id.item_patdetail_money)
        TextView mItemPatdetailMoney;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
