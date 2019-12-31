package com.superc.waitmarket.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.superc.waitmarket.R;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JiHuoAdapter extends RecyclerView.Adapter<JiHuoAdapter.ViewHolder> {
    private Context mContext;
    private List<Map<String, Object>> mLists;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemclickListener;

    public JiHuoAdapter(Context context, List<Map<String, Object>> stringList) {
        mContext = context;
        mLists = stringList;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_jihuo, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    public void setOnItemclickListener(OnItemClickListener onItemclickListener) {
        mOnItemclickListener = onItemclickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int position) {
        Map<String, Object> bean = mLists.get(position);
        boolean is_check = (boolean) bean.get("is_check");
        vh.mItemJihuoContent.setText((String) bean.get("content"));
        vh.mItemJihuoContent.setTextColor(mContext.getResources().getColor(is_check ? R.color.main_color : R.color.txt_granine));
        vh.mConstraintLayout.setBackground(mContext.getResources().getDrawable(is_check ? R.drawable.bg_corner_main : R.drawable.bg_corner_kuang));
        vh.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemclickListener != null)
                    mOnItemclickListener.onItemclickListener(position);
            }
        });


    }

    public interface OnItemClickListener {
        void onItemclickListener(int pos);
    }

    @Override
    public int getItemCount() {
        return mLists == null ? 0 : mLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_jihuo_content)
        TextView mItemJihuoContent;
        @BindView(R.id.item_jihuo_cons)
        ConstraintLayout mConstraintLayout;
        View mView;

        ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }
}
