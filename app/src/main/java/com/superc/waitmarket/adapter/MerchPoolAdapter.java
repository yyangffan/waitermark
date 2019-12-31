package com.superc.waitmarket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.superc.waitmarket.R;
import com.superc.waitmarket.base.Constant;
import com.superc.waitmarket.bean.MerPoolBean;
import com.superc.waitmarket.utils.BigDecimalUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MerchPoolAdapter extends RecyclerView.Adapter<MerchPoolAdapter.ViewHolder> {
    private Context mContext;
    private List<MerPoolBean.DataBean> mLists;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public MerchPoolAdapter(Context context, List<MerPoolBean.DataBean> stringList) {
        mContext = context;
        mLists = stringList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_merchpool, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int position) {
        MerPoolBean.DataBean bean = mLists.get(position);
        RoundedCorners roundedCorners = new RoundedCorners(10);
        RequestOptions override = RequestOptions.bitmapTransform(roundedCorners).error(R.drawable.icon_error).placeholder(R.drawable.icon_error).override(300, 300);
        RequestOptions requestOptions = new RequestOptions().error(R.drawable.icon_error).apply(override).placeholder(R.drawable.icon_error);
        String shoplogo = bean.getShopLogo();
        if (!TextUtils.isEmpty(shoplogo)) {
            if (shoplogo.startsWith("http") || shoplogo.startsWith("https")) {
                Glide.with(mContext).load(shoplogo).apply(requestOptions).into(vh.mItemMerchpoolImgv);
            } else {
                Glide.with(mContext).load(Constant.IMG_URL + shoplogo).apply(requestOptions).into(vh.mItemMerchpoolImgv);
            }
        } else {
            Glide.with(mContext).load(Constant.IMG_URL + shoplogo).apply(requestOptions).into(vh.mItemMerchpoolImgv);
        }
        vh.mItemMerchpoolName.setText(bean.getShopName());
        vh.mItemMerchpoolPos.setText(bean.getShopAddress());
        String auditstatus = BigDecimalUtils.bigUtil(bean.getAuditstatus());
        vh.mItemMerchpoolReceive.setVisibility(View.GONE);
        switch (auditstatus) {
            case "1":
                vh.mItemMerchpoolState.setText("可拓展");
                vh.mItemMerchpoolState.setTextColor(mContext.getResources().getColor( R.color.txt_yell));
                break;
            case "2":
                vh.mItemMerchpoolState.setText("不可拓");
                vh.mItemMerchpoolState.setTextColor(mContext.getResources().getColor( R.color.txt_blue));
                break;
            case "3":
                vh.mItemMerchpoolState.setText("可领取");
                vh.mItemMerchpoolReceive.setVisibility(View.VISIBLE);
                vh.mItemMerchpoolState.setTextColor(mContext.getResources().getColor( R.color.txt_yell));
                break;
        }
        vh.mItemMerchpoolReceive.setOnClickListener(new View.OnClickListener() {
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
        @BindView(R.id.item_merchpool_imgv)
        ImageView mItemMerchpoolImgv;
        @BindView(R.id.item_merchpool_name)
        TextView mItemMerchpoolName;
        @BindView(R.id.item_merchpool_pos)
        TextView mItemMerchpoolPos;
        @BindView(R.id.item_merchpool_state)
        TextView mItemMerchpoolState;
        @BindView(R.id.item_merchpool_receive)
        TextView mItemMerchpoolReceive;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
