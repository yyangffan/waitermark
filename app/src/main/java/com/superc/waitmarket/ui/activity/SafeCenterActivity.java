package com.superc.waitmarket.ui.activity;

import android.view.View;

import com.superc.waitmarket.R;
import com.superc.waitmarket.bean.AppMessage;
import com.superc.waitmarket.utils.jpush.TagAliasOperatorHelper;
import com.superc.yyfflibrary.base.BaseActivity;
import com.superc.yyfflibrary.dialog.YfsRemindDialog;
import com.superc.yyfflibrary.utils.ShareUtil;
import com.superc.yyfflibrary.utils.titlebar.TitleUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashSet;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SafeCenterActivity extends BaseActivity {


    private YfsRemindDialog mBuild;
    private String mOnly;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_safe_center;
    }

    @Override
    public void init() {
        TitleUtils.setStatusTextColor(true, this);
        ButterKnife.bind(this);
        mOnly = (String) ShareUtil.getInstance(SafeCenterActivity.this).get("only", "yinhang");
    }


    @OnClick({R.id.imgv_back, R.id.safe_center_changepwd, R.id.safe_center_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgv_back:
                finish();
                break;
            case R.id.safe_center_changepwd:
                statActivity(ChangePwdActivity.class);
                break;
            case R.id.safe_center_logout:
                mBuild = new YfsRemindDialog.Builder(this).title("是否确认退出？").left("取消").right("确认").build();
                mBuild.setOnTextClickListener(new YfsRemindDialog.OnTextClickListener() {
                    @Override
                    public void onRightClickListener() {
                        super.onRightClickListener();
                        logout();
                    }
                });
                mBuild.show();
                break;
        }
    }

    private void logout() {
        toRemoveAliasJpush();
        statActivity(LoginActivity.class);
        EventBus.getDefault().post(new AppMessage(0, "finish"));
        ShareUtil.getInstance(this).clear();
        finish();

    }

    private void toRemoveAliasJpush() {
        // TODO: 2019/9/16 取消别名
        TagAliasOperatorHelper.getInstance().init(SafeCenterActivity.this);
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.setAliasAction(true);
        tagAliasBean.setAlias("sss");
        tagAliasBean.setAction(TagAliasOperatorHelper.ACTION_DELETE);
        TagAliasOperatorHelper.getInstance().handleAction(SafeCenterActivity.this, 11, tagAliasBean);
        toRemoveJpush();
    }

    private void toRemoveJpush() {
        // TODO: 2019/9/16 删除标签
        Set<String> sets=new HashSet<>();
        sets.add(mOnly);
        TagAliasOperatorHelper.getInstance().init(SafeCenterActivity.this);
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.setAliasAction(false);
        tagAliasBean.setTags(sets);
        tagAliasBean.setAction(TagAliasOperatorHelper.ACTION_DELETE);
        TagAliasOperatorHelper.getInstance().handleAction(SafeCenterActivity.this, 12, tagAliasBean);

    }

}
