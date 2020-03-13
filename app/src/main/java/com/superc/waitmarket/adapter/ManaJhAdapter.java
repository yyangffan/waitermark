package com.superc.waitmarket.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.superc.waitmarket.bean.JihuoBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManaJhAdapter extends RecyclerView.Adapter<ManaJhAdapter.ViewHolder> {
    private Context mContext;
    private List<JihuoBean.DataBean.ListBean> mLists;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;
    private OnCommitClickListener mOnCommitClickListener;

    public ManaJhAdapter(Context context, List<JihuoBean.DataBean.ListBean> stringList) {
        mContext = context;
        mLists = stringList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnCommitClickListener(OnCommitClickListener onCommitClickListener) {
        mOnCommitClickListener = onCommitClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_magapb, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder vh, final int position) {
        vh.mPos_wenzi.setVisibility(View.GONE);
        vh.mTime_wenzi.setVisibility(View.GONE);
        final JihuoBean.DataBean.ListBean bean = mLists.get(position);
        vh.mimgv.setVisibility(View.VISIBLE);
        vh.mItemMagapbEdt.setVisibility(View.GONE);
        RoundedCorners roundedCorners = new RoundedCorners(10);
        RequestOptions override = RequestOptions.bitmapTransform(roundedCorners).error(R.drawable.icon_error).placeholder(R.drawable.icon_error).override(300, 300);
        RequestOptions requestOptions = new RequestOptions().error(R.drawable.icon_error).apply(override).placeholder(R.drawable.icon_error);
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
        vh.mItemMagapbPosi.setText("地址：" + bean.getShopAddress());
        String addTime = bean.getAddtime();
        if (!TextUtils.isEmpty(addTime)) {
            vh.mItemMagapbTime.setText("创建时间：" + addTime.replaceAll("-", "."));
        } else {
            vh.mItemMagapbTime.setText("创建时间：- -");
        }
        vh.mTextVieitemMagapbReason.setText("基础信息有误");
        vh.mItemMagapbState.setText(bean.getMes());
        vh.mItemMagapbReasonwenzi.setText("领取状态：");
        vh.mItemMagapbReasonwenzi.setTextColor(mContext.getResources().getColor(R.color.red));
        vh.mTextVieitemMagapbReason.setTextColor(mContext.getResources().getColor(R.color.red));
        if (TextUtils.isEmpty(bean.getAddPerson())) {
            vh.mTextVieitemMagapbReason.setText("待领取");
        } else {
            vh.mTextVieitemMagapbReason.setText("已领取（" + bean.getAddPerson() + "）");
        }

        vh.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClickListener(position);
            }
        });
        if (false){
            vh.mLinearOne.setVisibility(View.VISIBLE);
            vh.mTvCommit.setVisibility(View.VISIBLE);
            vh.mTvOne.setText("重新提交原因原因原因原因原因");
            vh.mTvCommit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mOnCommitClickListener!=null)
                        mOnCommitClickListener.onCommitClickListener(position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mLists == null ? 0 : mLists.size();
    }

    public interface OnItemClickListener {
        void onItemClickListener(int pos);
    }

    public interface OnCommitClickListener{
        void onCommitClickListener(int pos);
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
        @BindView(R.id.item_imgv_go)
        ImageView mimgv;
        @BindView(R.id.textView48)
        TextView mPos_wenzi;
        @BindView(R.id.textView57)
        TextView mTime_wenzi;
        @BindView(R.id.linear_one)
        LinearLayout mLinearOne;
        @BindView(R.id.item_reason_one)
        TextView mTvOne;
        @BindView(R.id.item_magapb_commit)
        TextView mTvCommit;
        View mView;

        ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }
}
