package com.superc.waitmarket.adapter;

import android.content.Context;
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
import com.superc.waitmarket.bean.ExpandDetailBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ExpandDetailAdapter extends RecyclerView.Adapter<ExpandDetailAdapter.ViewHolder> {
    private Context mContext;
    private List<ExpandDetailBean.DataBean.DataListBean> mLists;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public ExpandDetailAdapter(Context context, List<ExpandDetailBean.DataBean.DataListBean> stringList) {
        mContext = context;
        mLists = stringList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_paydetail, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        ExpandDetailBean.DataBean.DataListBean bean = mLists.get(position);
        vh.mItemPatdetailTitle.setText(bean.getShopname());
        vh.mItemPatdetailPosition.setText(bean.getShopaddress());
        int type=bean.getType();
        if (type==1) {//1，自拓展 ；2，小二委派
            vh.mItemPatdetailWhat.setText("商户拓展");
            vh.mItemPatdetailWhat.setTextColor(mContext.getResources().getColor(R.color.txt_blue));
        } else {
            vh.mItemPatdetailWhat.setText("小二委派");
            vh.mItemPatdetailWhat.setTextColor(mContext.getResources().getColor(R.color.txt_yell));
        }

        String shoplogo = bean.getShoplogo();
        RequestOptions requestOptions=new RequestOptions().error(R.drawable.icon_error).placeholder(R.drawable.icon_error);
        if (!TextUtils.isEmpty(shoplogo)) {
            if (shoplogo.startsWith("http") || shoplogo.startsWith("https")) {
                Glide.with(mContext).load(shoplogo).apply(requestOptions).into(vh.mItemPatdetailImgv);
            } else {
                Glide.with(mContext).load(Constant.IMG_URL + shoplogo).apply(requestOptions).into(vh.mItemPatdetailImgv);
            }
        } else {
            Glide.with(mContext).load(Constant.IMG_URL + shoplogo).apply(requestOptions).into(vh.mItemPatdetailImgv);
        }

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
        @BindView(R.id.item_patdetail_imgv)
        CircleImageView mItemPatdetailImgv;
        @BindView(R.id.item_patdetail_money)
        TextView mItemPatdetailWhat;
        View mView;

        ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }
}
