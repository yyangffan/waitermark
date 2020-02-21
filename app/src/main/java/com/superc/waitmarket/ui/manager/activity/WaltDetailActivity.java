package com.superc.waitmarket.ui.manager.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.superc.waitmarket.R;
import com.superc.waitmarket.adapter.ShouyAdapter;
import com.superc.yyfflibrary.base.BaseActivity;
import com.superc.yyfflibrary.utils.ShareUtil;
import com.superc.yyfflibrary.utils.titlebar.TitleUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WaltDetailActivity extends BaseActivity {
    @BindView(R.id.payflow_detail_money)
    TextView mExpandDetailOne;
    @BindView(R.id.payflow_detail_num)
    TextView mExpandDetailTwo;
    @BindView(R.id.payflow_detail_recy)
    RecyclerView mExpandDetailRecy;
    @BindView(R.id.payflow_smart)
    SmartRefreshLayout mExpandDetailSmart;
    private String mUser_id;
    private int page=1;
    private List<String> mStringList;
    private ShouyAdapter mShouyAdapter;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_walt_detail;
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        TitleUtils.setStatusTextColor(true, this);
        mUser_id = (String) ShareUtil.getInstance(this).get("user_id", "");
        mStringList=new ArrayList<>();
        mShouyAdapter=new ShouyAdapter(this,mStringList);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        mExpandDetailRecy.setLayoutManager(manager);
        mExpandDetailRecy.setAdapter(mShouyAdapter);
        mExpandDetailSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
                getData();
            }
        });
        mExpandDetailSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ++page;
                getData();
            }
        });
        mExpandDetailSmart.autoRefresh();

    }

    private void getData(){
        mExpandDetailOne.setText("¥23678");
        mExpandDetailTwo.setText("43");
        if(page==1){
            mStringList.clear();
        }
        for (int i = 0; i < 10; i++) {
            mStringList.add("");
        }
        mShouyAdapter.notifyDataSetChanged();
        mExpandDetailSmart.finishRefresh();
        mExpandDetailSmart.finishLoadMore();
    }
}
