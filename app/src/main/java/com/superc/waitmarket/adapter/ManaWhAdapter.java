package com.superc.waitmarket.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
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
import com.superc.waitmarket.bean.MainTainBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManaWhAdapter extends RecyclerView.Adapter<ManaWhAdapter.ViewHolder> {
    private Context mContext;
    private List<MainTainBean.DataBean.ListBean> mLists;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;
    private OnEdtClickListener mOnEdtClickListener;
    private boolean is_shifang = false;

    public ManaWhAdapter(Context context, List<MainTainBean.DataBean.ListBean> stringList) {
        mContext = context;
        mLists = stringList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public boolean isshifang() {
        return is_shifang;
    }

    public void setIs_shifang(boolean is_shifang) {
        this.is_shifang = is_shifang;
    }

    public void setOnEdtClickListener(OnEdtClickListener onEdtClickListener) {
        mOnEdtClickListener = onEdtClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_magapb, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder vh, final int position) {
        final MainTainBean.DataBean.ListBean bean = mLists.get(position);
        RoundedCorners roundedCorners = new RoundedCorners(8);
        RequestOptions requestOptions = RequestOptions.bitmapTransform(roundedCorners).override(300, 300).error(R.drawable.icon_error).placeholder(R.drawable.icon_error);
        String shoplogo = bean.getShopLogo();
        if (!TextUtils.isEmpty(shoplogo)) {
            if (shoplogo.startsWith("http") || shoplogo.startsWith("https")) {
                Glide.with(mContext).load(shoplogo).apply(requestOptions).into(vh.mItemMagapbHead);
            } else {
                Glide.with(mContext).load(Constant.IMG_URL + shoplogo).apply(requestOptions).into(vh.mItemMagapbHead);
            }
        } else {
            Glide.with(mContext).load(Constant.IMG_URL + shoplogo).apply(requestOptions).into(vh.mItemMagapbHead);
        }
        vh.mItemMagapbTitle.setText(bean.getShopName());
        vh.mItemMagapbPosi.setText(bean.getShopAddress());
        String addTime = bean.getAddTime();
        if (!TextUtils.isEmpty(addTime)) {
            vh.mItemMagapbTime.setText(bean.getAddTime().replaceAll("-", "."));
        } else {
            vh.mItemMagapbTime.setText("- -");
        }

        vh.mTextVieitemMagapbReason.setText(bean.getRejectreason());
        vh.mItemMagapbState.setText(bean.getAuditmessage());
        String rejectreason = bean.getRejectreason();
        vh.mTextVieitemMagapbReason.setVisibility(TextUtils.isEmpty(rejectreason) ? View.INVISIBLE : View.VISIBLE);
        vh.mItemMagapbReasonwenzi.setVisibility(TextUtils.isEmpty(rejectreason) ? View.INVISIBLE : View.VISIBLE);

        vh.mItemMagapbCheck.setVisibility(isshifang() ? View.VISIBLE : View.GONE);

        final ObjectAnimator animator1_con = ObjectAnimator.ofFloat(vh.mConstraintLayout, "translationX", 0f, 120f);
        final ObjectAnimator animator1 = ObjectAnimator.ofFloat(vh.mItemMagapbEdt, "translationX", 0f, 60f);

        final ObjectAnimator animator2_con = ObjectAnimator.ofFloat(vh.mConstraintLayout, "translationX", 0f, 0f);
        final ObjectAnimator animator2 = ObjectAnimator.ofFloat(vh.mItemMagapbEdt, "translationX", 0f, 0f);
        animator1_con.start();
        animator1.start();
        if (isshifang()) {
            animator1_con.start();
            animator1.start();
        } else {
            animator2_con.start();
            animator2.start();
        }
        final boolean check = bean.isCheck();
        vh.mItemMagapbCheck.setImageResource(check ? R.drawable.icon_gouxuan : R.drawable.icon_weigouxuan);
        vh.mItemMagapbCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bean.setCheck(!bean.isCheck());
                vh.mItemMagapbCheck.setImageResource(bean.isCheck() ? R.drawable.icon_gouxuan : R.drawable.icon_weigouxuan);
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClickListener(position);
            }
        });

        vh.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bean.setCheck(!bean.isCheck());
                vh.mItemMagapbCheck.setImageResource(bean.isCheck() ? R.drawable.icon_gouxuan : R.drawable.icon_weigouxuan);
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClickListener(position);
            }
        });
        vh.mItemMagapbEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnEdtClickListener != null)
                    mOnEdtClickListener.onEdtClickListener(position);
            }
        });

    }

    public List<MainTainBean.DataBean.ListBean> getLists() {
        return mLists;
    }

    @Override
    public int getItemCount() {
        return mLists == null ? 0 : mLists.size();
    }

    public interface OnItemClickListener {
        void onItemClickListener(int pos);
    }

    public interface OnEdtClickListener {
        void onEdtClickListener(int pos);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_magapb_check)
        ImageView mItemMagapbCheck;
        @BindView(R.id.item_magapb_edt)
        TextView mItemMagapbEdt;
        @BindView(R.id.item_magapb_head)
        ImageView mItemMagapbHead;
        @BindView(R.id.item_magapb_title)
        TextView mItemMagapbTitle;
        @BindView(R.id.item_magapb_posi)
        TextView mItemMagapbPosi;
        @BindView(R.id.item_magapb_time)
        TextView mItemMagapbTime;
        @BindView(R.id.item_magapb_reasonwenzi)
        TextView mItemMagapbReasonwenzi;
        @BindView(R.id.textVieitem_magapb_reason)
        TextView mTextVieitemMagapbReason;
        @BindView(R.id.item_magapb_state)
        TextView mItemMagapbState;
        @BindView(R.id.constraintLayout6)
        ConstraintLayout mConstraintLayout;

        View mView;

        ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }
}
