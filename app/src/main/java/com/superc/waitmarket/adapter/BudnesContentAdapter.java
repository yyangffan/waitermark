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
import com.superc.waitmarket.bean.ShopManageBean;
import com.superc.waitmarket.utils.BigDecimalUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BudnesContentAdapter extends RecyclerView.Adapter<BudnesContentAdapter.ViewHolder> {
    private Context mContext;
    private List<ShopManageBean.DataBean.ListBean> mLists;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public BudnesContentAdapter(Context context, List<ShopManageBean.DataBean.ListBean> stringList) {
        mContext = context;
        mLists = stringList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_busnes_content, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int position) {
        ShopManageBean.DataBean.ListBean bean = mLists.get(position);
        RoundedCorners roundedCorners = new RoundedCorners(30);
        RequestOptions override = RequestOptions.bitmapTransform(roundedCorners).error(R.drawable.icon_error).placeholder(R.drawable.icon_error).override(300, 300);
        RequestOptions requestOptions = new RequestOptions().error(R.drawable.icon_error).apply(override).placeholder(R.drawable.icon_error);
        String shoplogo = bean.getShopLogo();
        if (!TextUtils.isEmpty(shoplogo)) {
            if (shoplogo.startsWith("http") || shoplogo.startsWith("https")) {
                Glide.with(mContext).load(shoplogo).apply(requestOptions).into(vh.mItemContentImgv);
            } else {
                Glide.with(mContext).load(Constant.IMG_URL + shoplogo).apply(requestOptions).into(vh.mItemContentImgv);
            }
        } else {
            Glide.with(mContext).load(Constant.IMG_URL + shoplogo).apply(requestOptions).into(vh.mItemContentImgv);
        }
        vh.mItemContentName.setText(bean.getShopName());
        vh.mItemContentTime.setText("上线时间"+getTimeStr(bean.getChecksuccesstime(),"yyyy.MM.dd"));
        vh.mItemContentThemoth.setText("¥"+BigDecimalUtils.bigUtil(bean.getPayamount()+""));
        vh.mItemContentLiulinag.setText(BigDecimalUtils.bigUtil(bean.getCount()+""));
        vh.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClickListener(position);
            }
        });


    }
    /**
     * 将时间戳     转为   字符串
     * @param cc_time:1512132135带毫秒
     * @param simple_examp:yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getTimeStr(long cc_time, String simple_examp) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat(simple_examp);
        re_StrTime = sdf.format(new Date(cc_time));
        return re_StrTime;
    }

    @Override
    public int getItemCount() {
        return mLists == null ? 0 : mLists.size();
    }

    public interface OnItemClickListener {
        void onItemClickListener(int pos);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_content_imgv)
        ImageView mItemContentImgv;
        @BindView(R.id.item_content_name)
        TextView mItemContentName;
        @BindView(R.id.item_content_time)
        TextView mItemContentTime;
        @BindView(R.id.item_content_themoth)
        TextView mItemContentThemoth;
        @BindView(R.id.item_content_liulinag)
        TextView mItemContentLiulinag;
        View mView;

        ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }
}
