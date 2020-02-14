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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HighEneyAdapter extends RecyclerView.Adapter<HighEneyAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mLists;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public HighEneyAdapter(Context context, List<String> stringList) {
        mContext = context;
        mLists = stringList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_higheney, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int position) {
        String bean = mLists.get(position);
        RoundedCorners roundedCorners = new RoundedCorners(10);
        RequestOptions override = RequestOptions.bitmapTransform(roundedCorners).error(R.drawable.icon_error).placeholder(R.drawable.icon_error).override(300, 300);
        RequestOptions requestOptions = new RequestOptions().error(R.drawable.icon_error).apply(override).placeholder(R.drawable.icon_error);
//        String shoplogo = bean.getShopLogo();
        String shoplogo = "";
        if (!TextUtils.isEmpty(shoplogo)) {
            if (shoplogo.startsWith("http") || shoplogo.startsWith("https")) {
                Glide.with(mContext).load(shoplogo).apply(requestOptions).into(vh.mItemHigheneyImgv);
            } else {
                Glide.with(mContext).load(Constant.IMG_URL + shoplogo).apply(requestOptions).into(vh.mItemHigheneyImgv);
            }
        } else {
            Glide.with(mContext).load(Constant.IMG_URL + shoplogo).apply(requestOptions).into(vh.mItemHigheneyImgv);
        }
        vh.mItemHigheneyName.setText("鱼酷(万达店)" + position);
        vh.mItemHigheneyContent.setText("近5日：共交易5笔，合计123456" + position + "元");
//        vh.mItemHigheneyWhat.setTextColor(mContext.getResources().getColor(R.color.main_color));
        vh.mItemHigheneyWhat.setTextColor(mContext.getResources().getColor(position % 2 == 0 ? R.color.main_color : R.color.high_green));
        vh.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClickListener(position);
                }
            }
        });

    }

    public interface OnItemClickListener {
        void onItemClickListener(int position);
    }

    @Override
    public int getItemCount() {
        return mLists == null ? 0 : mLists.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_higheney_imgv)
        ImageView mItemHigheneyImgv;
        @BindView(R.id.item_higheney_name)
        TextView mItemHigheneyName;
        @BindView(R.id.item_higheney_content)
        TextView mItemHigheneyContent;
        @BindView(R.id.item_higheney_what)
        TextView mItemHigheneyWhat;
        View mView;

        ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }
}
