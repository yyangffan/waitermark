package com.superc.waitmarket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.superc.waitmarket.R;
import com.superc.waitmarket.bean.ProductBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhaseMarketListAdapter extends RecyclerView.Adapter<PhaseMarketListAdapter.ViewHolder> {
    private Context mContext;
    private List<ProductBean.DataBean.ProductListBean> mLists;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public PhaseMarketListAdapter(Context context, List<ProductBean.DataBean.ProductListBean> stringList) {
        mContext = context;
        mLists = stringList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.phase_item_market, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        ProductBean.DataBean.ProductListBean bean = mLists.get(position);
        String what = bean.getCoupontype();
        String content = "折扣券";
        switch (what) {
            case "1": content = "满减券";break;
            case "2": content = "折扣券";break;
            case "3": content = "赠品券";break;
            case "5": content = "体验券";break;
            case "6": content = "代金券";break;
        }

        vh.mPhaseItemmarketContent.setText(bean.getActName());
        vh.mPhaseItemmarketWhat.setText(content);
        List<ProductBean.DataBean.ProductListBean.TimeListBean> timeList = bean.getTimeList();
        StringBuilder stb_time = new StringBuilder("可用时间段：");
        for (int i = 0; i < timeList.size(); i++) {
            if(i==0) {
                stb_time.append(timeList.get(i).getStarttime() + "-" + timeList.get(i).getEndtime());
            }else{
                stb_time.append("；");
                stb_time.append(timeList.get(i).getStarttime() + "-" + timeList.get(i).getEndtime());
            }
        }
        String weekdays = bean.getWeekdays();
        if(!TextUtils.isEmpty(weekdays)){
            String weekdays_new = weekdays.replaceAll(",", "");
            if(weekdays_new.length()==7){
                stb_time.append(" ");
                stb_time.append("周一至周日,");
            }else{
                stb_time.append(" ");
                stb_time.append(weekdays_new.contains("1")?"周一，":"");
                stb_time.append(weekdays_new.contains("2")?"周二，":"");
                stb_time.append(weekdays_new.contains("3")?"周三，":"");
                stb_time.append(weekdays_new.contains("4")?"周四，":"");
                stb_time.append(weekdays_new.contains("5")?"周五，":"");
                stb_time.append(weekdays_new.contains("6")?"周六，":"");
                stb_time.append(weekdays_new.contains("7")?"周日，":"");
            }
        }
        String res=stb_time.toString();
        vh.mPhaseItemmarketTime.setText(res.substring(0,res.length()-1));
        vh.mPhaseItemmarketLqnum.setText("领取数：" + bean.getReceiveCount() + "张");
        vh.mPhaseItemmarketSynum.setText("使用数：" + bean.getUseCount() + "张");



    }

    @Override
    public int getItemCount() {
        return mLists == null ? 0 : mLists.size();
    }

    public interface OnItemClickListener {
        void onItemClickListener(int pos);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.phase_itemmarket_content)
        TextView mPhaseItemmarketContent;
        @BindView(R.id.phase_itemmarket_what)
        TextView mPhaseItemmarketWhat;
        @BindView(R.id.phase_itemmarket_state)
        TextView mPhaseItemmarketState;
        @BindView(R.id.phase_itemmarket_time)
        TextView mPhaseItemmarketTime;
        @BindView(R.id.phase_itemmarket_lqnum)
        TextView mPhaseItemmarketLqnum;
        @BindView(R.id.phase_itemmarket_synum)
        TextView mPhaseItemmarketSynum;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
