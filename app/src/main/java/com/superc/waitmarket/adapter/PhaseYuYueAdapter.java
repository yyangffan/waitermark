package com.superc.waitmarket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.superc.waitmarket.R;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhaseYuYueAdapter extends RecyclerView.Adapter<PhaseYuYueAdapter.ViewHolder> {
    private Context mContext;
    private List<Map<String, Object>> mLists;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public PhaseYuYueAdapter(Context context, List<Map<String, Object>> stringList) {
        mContext = context;
        mLists = stringList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.phase_item_yuyue, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int position) {
        Map<String, Object> bean = mLists.get(position);
        String title= (String) bean.get("title");
        boolean type= (boolean) bean.get("type");
        vh.mPhaseItemyuyueTitle.setText(title);
        vh.mPhaseItemyuyueImg.setImageDrawable(mContext.getResources().getDrawable(type?R.drawable.icon_xuanzhong:R.drawable.icon_weixuanzhong));
        vh.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnItemClickListener!=null){
                    mOnItemClickListener.onItemClickListener(position);
                }
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

    static class ViewHolder   extends RecyclerView.ViewHolder{
        @BindView(R.id.phase_itemyuyue_title)
        TextView mPhaseItemyuyueTitle;
        @BindView(R.id.phase_itemyuyue_imgv)
        ImageView mPhaseItemyuyueImg;
        View mView;
        ViewHolder(View view) {
            super(view);
            mView=view;
            ButterKnife.bind(this, view);
        }
    }
}
