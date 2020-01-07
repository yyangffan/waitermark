package com.superc.waitmarket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.superc.waitmarket.R;
import com.superc.waitmarket.bean.MsgBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {
    private Context mContext;
    private List<MsgBean.DataBean> mLists;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public MsgAdapter(Context context, List<MsgBean.DataBean> stringList) {
        mContext = context;
        mLists = stringList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_msg, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int position) {
        MsgBean.DataBean bean = mLists.get(position);
        int status = bean.getStatus();
        if (status == 0) {//0未读 其他数字为已读
            vh.mItemMsgTitle.setTextColor(mContext.getResources().getColor(R.color.black));
            vh.mTexitemMsgContent.setTextColor(mContext.getResources().getColor(R.color.black));
            vh.mItemMsgDetail.setTextColor(mContext.getResources().getColor(R.color.main_color));
        } else {
            vh.mItemMsgTitle.setTextColor(mContext.getResources().getColor(R.color.txt_granine));
            vh.mTexitemMsgContent.setTextColor(mContext.getResources().getColor(R.color.txt_granine));
            vh.mItemMsgDetail.setTextColor(mContext.getResources().getColor(R.color.txt_granine));
        }
        vh.mItemMsgTitle.setText(bean.getTitle());
        vh.mItemMsgTime.setText(bean.getAddtime());
        vh.mTexitemMsgContent.setText(bean.getContent());
        vh.mItemMsgDetail.setOnClickListener(new View.OnClickListener() {
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
        @BindView(R.id.item_msg_title)
        TextView mItemMsgTitle;
        @BindView(R.id.item_msg_time)
        TextView mItemMsgTime;
        @BindView(R.id.texitem_msg_content)
        TextView mTexitemMsgContent;
        @BindView(R.id.item_msg_detail)
        TextView mItemMsgDetail;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
