package com.superc.waitmarket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReceiveAdapter extends RecyclerView.Adapter<ReceiveAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mLists;
    private LayoutInflater mInflater;
    private OnReceiveClickListener mOnReceiveClickListener;

    public ReceiveAdapter(Context context, List<String> stringList) {
        mContext = context;
        mLists = stringList;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_receive, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    public void setOnReceiveClickListener(OnReceiveClickListener onReceiveClickListener) {
        mOnReceiveClickListener = onReceiveClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int position) {
        String bean = mLists.get(position);
        RoundedCorners roundedCorners = new RoundedCorners(30);
        RequestOptions override = RequestOptions.bitmapTransform(roundedCorners).error(R.drawable.icon_error).placeholder(R.drawable.icon_error).override(300, 300);
        RequestOptions requestOptions = new RequestOptions().error(R.drawable.icon_error).apply(override).placeholder(R.drawable.icon_error);
//        String shoplogo = bean.getShopLogo();
        String shoplogo ="";
        Glide.with(mContext).load(Constant.IMG_URL + shoplogo).apply(requestOptions).into(vh.mItemMagapbHead);
      /*  if (!TextUtils.isEmpty(shoplogo)) {
            if (shoplogo.startsWith("http") || shoplogo.startsWith("https")) {
                Glide.with(mContext).load(shoplogo).apply(requestOptions).into(vh.mItemMagapbHead);
            } else {
                Glide.with(mContext).load(Constant.IMG_URL + shoplogo).apply(requestOptions).into(vh.mItemContentImgv);
            }
        } else {
            Glide.with(mContext).load(Constant.IMG_URL + shoplogo).apply(requestOptions).into(vh.mItemContentImgv);
        }*/

        vh.mItemMagapbTitle.setText("鱼酷(万达店)" + position);
        vh.mItemMagapbPosi.setText("王串场***" + position);
        vh.mItemMagapbTime.setText("一级归属·二级归属" + position);
        vh.mTextVieitemMagapbReason.setText("工号：1234，姓名：张" + position);
        if(position%2==0){
            vh.mItemImgvGo.setVisibility(View.GONE);
            vh.mItemImgvContent.setVisibility(View.VISIBLE);
            vh.mItemImgvContent.setText("已被领取\n领取人：张三");
        }else{
            vh.mItemImgvGo.setVisibility(View.VISIBLE);
            vh.mItemImgvContent.setVisibility(View.GONE);
        }

        vh.mItemImgvGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnReceiveClickListener != null) {
                    mOnReceiveClickListener.onReceiveClickListener(position);
                }
            }
        });

    }

    public interface OnReceiveClickListener {
        void onReceiveClickListener(int position);
    }

    @Override
    public int getItemCount() {
        return mLists == null ? 0 : mLists.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_magapb_head)
        ImageView mItemMagapbHead;
        @BindView(R.id.item_magapb_title)
        TextView mItemMagapbTitle;
        @BindView(R.id.item_magapb_posi)
        TextView mItemMagapbPosi;
        @BindView(R.id.item_magapb_time)
        TextView mItemMagapbTime;
        @BindView(R.id.textVieitem_magapb_reason)
        TextView mTextVieitemMagapbReason;
        @BindView(R.id.item_magapb_edt)
        TextView mItemImgvGo;
        @BindView(R.id.item_magapb_content)
        TextView mItemImgvContent;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
