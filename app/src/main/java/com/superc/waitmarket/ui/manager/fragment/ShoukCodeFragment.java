package com.superc.waitmarket.ui.manager.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.superc.waitmarket.R;
import com.superc.waitmarket.adapter.ShoukmAdapter;
import com.superc.yyfflibrary.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoukCodeFragment extends BaseFragment {


    @BindView(R.id.shoukuanm_recy)
    RecyclerView mShoukuanmRecy;
    @BindView(R.id.jichu_look_smart)
    SmartRefreshLayout mJichuLookSmart;
    Unbinder unbinder;
    private List<String> mStrings;
    private ShoukmAdapter mShoukmAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shouk_code, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void init() {
        mJichuLookSmart.setEnableOverScrollDrag(true);
        mJichuLookSmart.setEnablePureScrollMode(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mShoukuanmRecy.setLayoutManager(linearLayoutManager);
        mStrings = new ArrayList<>();
        mShoukmAdapter = new ShoukmAdapter(getActivity(), mStrings);
        mShoukuanmRecy.setAdapter(mShoukmAdapter);
        getData();
    }

    private void getData() {
        for (int i = 0; i < 5; i++) {
            mStrings.add("");
        }
        mShoukmAdapter.notifyDataSetChanged();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
