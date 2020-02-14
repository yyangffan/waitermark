package com.superc.waitmarket.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
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
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DkaitAdapter extends RecyclerView.Adapter<DkaitAdapter.ViewHolder> {

    private Context mContext;
    private List<Map<String, Object>> mLists;
    private LayoutInflater mInflater;
    private OnButtonClickListener mOnButtonClickListener;
    private boolean is_shifang = false;

    public DkaitAdapter(Context context, List<Map<String, Object>> stringList) {
        mContext = context;
        mLists = stringList;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_weihpb, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    public boolean isshifang() {
        return is_shifang;
    }

    public List<Map<String, Object>> getLists() {
        return mLists;
    }

    public void setLists(List<Map<String, Object>> lists) {
        mLists = lists;
    }

    public void setIs_shifang(boolean is_shifang) {
        this.is_shifang = is_shifang;
    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        mOnButtonClickListener = onButtonClickListener;
    }

    @Override
    public void onBindViewHolder(final ViewHolder vh, final int position) {
        final Map<String, Object> bean = mLists.get(position);
        RoundedCorners roundedCorners = new RoundedCorners(30);
        RequestOptions override = RequestOptions.bitmapTransform(roundedCorners).error(R.drawable.icon_error).placeholder(R.drawable.icon_error).override(300, 300);
        RequestOptions requestOptions = new RequestOptions().error(R.drawable.icon_error).apply(override).placeholder(R.drawable.icon_error);
//        String shoplogo = bean.getShopLogo();
        String shoplogo = "";
        Glide.with(mContext).load(Constant.IMG_URL + shoplogo).apply(requestOptions).into(vh.mItemMagapbHead);
        vh.mItemLeftState.setText("待商户激活");
        vh.mItemMagapbTitle.setText("鱼酷(万达店)");
        vh.mItemMagapbPosi.setText("地址地址地址地址地址" + position);
        vh.mItemMagapbTime.setText("2019.12.2" + position);
        vh.mItemWeihpbState.setText(position % 2 == 0 ? "已激活" : "未激活");
        vh.mItemWeihpbState.setTextColor(mContext.getResources().getColor(position % 2 == 0 ? R.color.main_color : R.color.gray));
        vh.mItemWeihpbImgkaitong.setVisibility(position % 2 == 0 ? View.GONE : View.VISIBLE);
        vh.mKaitong.setTextColor(mContext.getResources().getColor(position % 2 == 0 ? R.color.main_color : R.color.gray));
        vh.mItemWeihuBoh.setTextColor(mContext.getResources().getColor(position % 2 == 0 ? R.color.main_color : R.color.gray));
        vh.mLinearOne.setVisibility(position % 1 == 0 ? View.VISIBLE : View.GONE);
        vh.mItemReasonOne.setText("小二驳回原因.....");
        vh.mLinearTwo.setVisibility(position % 2 == 0 ? View.VISIBLE : View.GONE);
        vh.mItemReasonTwo.setText("重新提交原因...");
        vh.mLinearThree.setVisibility(position % 3 == 0 ? View.VISIBLE : View.GONE);
        vh.mItemReasonThree.setText("开通审核驳回原因...");

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

        vh.mItemMagapbCheck.setVisibility(isshifang() ? View.VISIBLE : View.GONE);

        final ObjectAnimator animator1_con = ObjectAnimator.ofFloat(vh.mConstraintLayout, "translationX", 0f, 120f);
        final ObjectAnimator animator1 = ObjectAnimator.ofFloat(vh.mItemWeihpbState, "translationX", 0f, 60f);

        final ObjectAnimator animator2_con = ObjectAnimator.ofFloat(vh.mConstraintLayout, "translationX", 0f, 0f);
        final ObjectAnimator animator2 = ObjectAnimator.ofFloat(vh.mItemWeihpbState, "translationX", 0f, 0f);
        animator1_con.start();
        animator1.start();
        if (isshifang()) {
            animator1_con.start();
            animator1.start();
        } else {
            animator2_con.start();
            animator2.start();
        }

        final boolean check = (boolean) bean.get("isCheck");
        vh.mItemMagapbCheck.setImageResource(check ? R.drawable.icon_gouxuan : R.drawable.icon_weigouxuan);
        vh.mItemMagapbCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isshifang()) {
                    bean.put("isCheck", !(boolean) bean.get("isCheck"));
                    vh.mItemMagapbCheck.setImageResource((boolean) bean.get("isCheck") ? R.drawable.icon_gouxuan : R.drawable.icon_weigouxuan);
                    if (mOnButtonClickListener != null)
                        mOnButtonClickListener.onItemClickListener(position);
                }
            }
        });

        vh.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isshifang()) {
                    bean.put("isCheck", !(boolean) bean.get("isCheck"));
                    vh.mItemMagapbCheck.setImageResource((boolean) bean.get("isCheck") ? R.drawable.icon_gouxuan : R.drawable.icon_weigouxuan);
                    if (mOnButtonClickListener != null)
                        mOnButtonClickListener.onItemClickListener(position);
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

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_weihpb_state)
        TextView mItemWeihpbState;
        @BindView(R.id.item_magapb_head)
        ImageView mItemMagapbHead;
        @BindView(R.id.item_magapb_title)
        TextView mItemMagapbTitle;
        @BindView(R.id.item_magapb_posi)
        TextView mItemMagapbPosi;
        @BindView(R.id.item_magapb_time)
        TextView mItemMagapbTime;
        @BindView(R.id.item_weih_look)
        TextView mItemWeihLook;
        @BindView(R.id.item_weihpb_imgkaitong)
        ImageView mItemWeihpbImgkaitong;
        @BindView(R.id.item_weihu_boh)
        TextView mItemWeihuBoh;
        @BindView(R.id.item_magapb_state)
        TextView mItemLeftState;
        @BindView(R.id.kaitong)
        TextView mKaitong;
        @BindView(R.id.item_weihu_kaitong)
        LinearLayout mItemWeihuKaitong;
        @BindView(R.id.item_magapb_check)
        ImageView mItemMagapbCheck;
        @BindView(R.id.constraintLayout6)
        ConstraintLayout mConstraintLayout;
        @BindView(R.id.item_reason_one)
        TextView mItemReasonOne;
        @BindView(R.id.linear_one)
        LinearLayout mLinearOne;
        @BindView(R.id.item_reason_two)
        TextView mItemReasonTwo;
        @BindView(R.id.linear_two)
        LinearLayout mLinearTwo;
        @BindView(R.id.item_reason_three)
        TextView mItemReasonThree;
        @BindView(R.id.linear_three)
        LinearLayout mLinearThree;

        View mView;

        ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }
}
