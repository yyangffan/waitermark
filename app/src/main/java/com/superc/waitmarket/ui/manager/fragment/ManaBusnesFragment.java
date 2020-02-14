package com.superc.waitmarket.ui.manager.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.superc.waitmarket.R;
import com.superc.waitmarket.ui.activity.EdtDetailActivity;
import com.superc.waitmarket.ui.activity.ManaPbActivity;
import com.superc.waitmarket.ui.activity.MerchPoolActivity;
import com.superc.waitmarket.ui.activity.SerachCouActivity;
import com.superc.waitmarket.ui.manager.activity.ReceiveActivity;
import com.superc.waitmarket.ui.manager.activity.WeihPbActivity;
import com.superc.yyfflibrary.base.BaseFragment;
import com.superc.yyfflibrary.utils.ShareUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManaBusnesFragment extends BaseFragment {


    @BindView(R.id.manabus_weihu_num)
    TextView mManabusWeihuNum;
    @BindView(R.id.manabus_shenhe_num)
    TextView mManabusShenheNum;
    @BindView(R.id.manabus_kaitong_num)
    TextView mManabusKaitongNum;
    @BindView(R.id.manabus_shangxian_num)
    TextView mManabusShangxianNum;
    @BindView(R.id.manabus_kaitong_botnum)
    TextView mManabusKaitongBotnum;
    @BindView(R.id.manabus_shenhe_num_bot)
    TextView mManabusShenheNumBot;
    @BindView(R.id.manabus_shangxian_num_bot)
    TextView mManabusShangxianNumBot;
    @BindView(R.id.user_check_smart)
    SmartRefreshLayout mUserCheckSmart;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mana_busnes, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void init() {
        mUserCheckSmart.setEnableOverScrollDrag(true);
        mUserCheckSmart.setEnablePureScrollMode(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.busnes_shanghuchi, R.id.textView118, R.id.textView120, R.id.textView122, R.id.textView126, R.id.textView128, R.id.textView132, R.id.textView134, R.id.textView136,R.id.textView130})
    public void onViewClicked(View view) {
        Intent intent = new Intent(this.getActivity(), ManaPbActivity.class);
        Intent inte=new Intent(getActivity(),WeihPbActivity.class);
        switch (view.getId()) {
            case R.id.busnes_shanghuchi:
                statActivity(MerchPoolActivity.class);
                break;
            case R.id.textView118://one 新建
                ShareUtil.getInstance(getActivity()).put("edtdetail_id", "");
                ShareUtil.getInstance(getActivity()).put("is_creat", "0");
                ShareUtil.getInstance(getActivity()).put("can_commit", false);
                ShareUtil.getInstance(getActivity()).put("status", "0");
                statActivity(EdtDetailActivity.class);
                break;
            case R.id.textView120://one 维护中
                intent.putExtra("what", "0");
                startActivity(intent);
                break;
            case R.id.textView122://one 待审核
                intent.putExtra("what", "1");
                startActivity(intent);
                break;
            case R.id.textView126://one 待开通
                intent.putExtra("what", "2");
                startActivity(intent);
                break;
            case R.id.textView128://one 已上线
                statActivity(SerachCouActivity.class);
                break;
            case R.id.textView130://two 领取
                statActivity(ReceiveActivity.class);
                break;
            case R.id.textView132://two 待开通
                inte.putExtra("type","0");
                startActivity(inte);
                break;
            case R.id.textView134://two 待审核
                inte.putExtra("type","1");
                startActivity(inte);
                break;
            case R.id.textView136://two 已上线
                inte.putExtra("type","2");
                startActivity(inte);
                break;
        }
    }

    public void getData(){

    }

}
