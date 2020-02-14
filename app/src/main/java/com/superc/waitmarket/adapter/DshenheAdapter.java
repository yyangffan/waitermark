package com.superc.waitmarket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.superc.waitmarket.R;
import com.superc.waitmarket.base.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DshenheAdapter extends RecyclerView.Adapter<DshenheAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mLists;
    private LayoutInflater mInflater;
    private OnButtonClickListener mOnButtonClickListener;

    public DshenheAdapter(Context context, List<String> stringList) {
        mContext = context;
        mLists = stringList;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_dshenhe, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        mOnButtonClickListener = onButtonClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int position) {
        String bean = mLists.get(position);
        RoundedCorners roundedCorners = new RoundedCorners(30);
        RequestOptions override = RequestOptions.bitmapTransform(roundedCorners).error(R.drawable.icon_error).placeholder(R.drawable.icon_error).override(300, 300);
        RequestOptions requestOptions = new RequestOptions().error(R.drawable.icon_error).apply(override).placeholder(R.drawable.icon_error);
//        String shoplogo = bean.getShopLogo();
        String shoplogo = "";
        Glide.with(mContext).load(Constant.IMG_URL + shoplogo).apply(requestOptions).into(vh.mItemMagapbHead);
        vh.mItemLeftState.setText("待审核");
        vh.mItemMagapbTitle.setText("鱼酷(万达店)");
        vh.mItemMagapbPosi.setText("地址地址地址地址地址" + position);
        vh.mItemMagapbTime.setText("2019.12.2" + position);
        vh.mItemWeihLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnButtonClickListener != null) {
                    mOnButtonClickListener.onLookClickListener(position);
                }
            }
        });
        vh.mItemWeihuKaitong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnButtonClickListener != null) {
                    mOnButtonClickListener.onKaiTongClickListener(position);
                }
            }
        });
        vh.mItemWeihuBoh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnButtonClickListener != null) {
                    mOnButtonClickListener.onBohuiClickListener(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLists == null ? 0 : mLists.size();
    }
    public static abstract class OnButtonClickListener {
        public void onLookClickListener(int position) {
        }

        public void onKaiTongClickListener(int position) {
        }

        public void onBohuiClickListener(int position) {
        }

        public void onItemClickListener(int position) {

        }
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_magapb_state)
        TextView mItemLeftState;
        @BindView(R.id.item_magapb_head)
        ImageView mItemMagapbHead;
        @BindView(R.id.item_magapb_title)
        TextView mItemMagapbTitle;
        @BindView(R.id.item_magapb_posi)
        TextView mItemMagapbTime;
        @BindView(R.id.item_magapb_time)
        TextView mItemMagapbPosi;
        @BindView(R.id.item_weih_look)
        TextView mItemWeihLook;
        @BindView(R.id.kaitong)
        TextView mKaitong;
        @BindView(R.id.item_weihu_boh)
        TextView mItemWeihuBoh;
        @BindView(R.id.item_weihu_kaitong)
        LinearLayout mItemWeihuKaitong;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

