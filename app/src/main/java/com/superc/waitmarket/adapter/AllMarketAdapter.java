package com.superc.waitmarket.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.superc.waitmarket.R;
import com.superc.waitmarket.base.Constant;
import com.superc.waitmarket.bean.YingxiaoBean;
import com.superc.waitmarket.utils.BigDecimalUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class AllMarketAdapter extends RecyclerView.Adapter<AllMarketAdapter.ViewHolder> {
    private Context mContext;
    private List<YingxiaoBean> mLists;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public AllMarketAdapter(Context context, List<YingxiaoBean> stringList) {
        mContext = context;
        mLists = stringList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_allmarket, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int position) {
        YingxiaoBean bean = mLists.get(position);
        boolean is_wangd = bean.isIs_wangd();
        if (is_wangd) {
            vh.mItemAllmarketTitle.setTextSize(13);
            vh.mItemAllmarketTitle.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            vh.mImageView.setVisibility(View.VISIBLE);
            String img_url = bean.getImg_url();
            RequestOptions requestOptions = new RequestOptions().error(R.drawable.icon_error).placeholder(R.drawable.icon_error);
            if (!TextUtils.isEmpty(img_url)) {
                if (img_url.startsWith("http") || img_url.startsWith("https")) {
                    Glide.with(mContext).load(img_url).apply(requestOptions).into(vh.mImageView);
                } else {
                    Glide.with(mContext).load(Constant.IMG_URL + img_url).apply(requestOptions).into(vh.mImageView);
                }
            } else {
                Glide.with(mContext).load(Constant.IMG_URL + img_url).apply(requestOptions).into(vh.mImageView);
            }
        }
        vh.mItemAllmarketTitle.setText(bean.getOne_content());
        vh.mItemAllmarketOne.setText(BigDecimalUtils.bigUtil(bean.getTwo_content()));
        vh.mItemAllmarketTwo.setText("¥" + BigDecimalUtils.bigUtil(bean.getThree_content()));
        vh.mItemAllmarketThree.setText(BigDecimalUtils.bigUtil(bean.getFour_content()));
        final String id = bean.getId();
        vh.mItemAllmarketMore.setVisibility(TextUtils.isEmpty(id) ? View.GONE : View.VISIBLE);
        vh.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null && !TextUtils.isEmpty(id))
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
        @BindView(R.id.item_allmarket_title)
        TextView mItemAllmarketTitle;
        @BindView(R.id.item_allmarket_one)
        TextView mItemAllmarketOne;
        @BindView(R.id.item_allmarket_two)
        TextView mItemAllmarketTwo;
        @BindView(R.id.item_allmarket_three)
        TextView mItemAllmarketThree;
        @BindView(R.id.item_allmarket_name)
        TextView mItemAllmarketMore;
        @BindView(R.id.imageView)
        CircleImageView mImageView;

        View mView;

        ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }
}
