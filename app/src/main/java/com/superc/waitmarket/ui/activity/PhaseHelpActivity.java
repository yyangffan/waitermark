package com.superc.waitmarket.ui.activity;

import android.view.View;

import com.luck.picture.lib.widget.longimage.ImageSource;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;
import com.superc.waitmarket.R;
import com.superc.yyfflibrary.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhaseHelpActivity extends BaseActivity {

    @BindView(R.id.phase_help_imgv)
    SubsamplingScaleImageView mPhaseHelpImgv;


    @Override
    public int getContentLayoutId() {
        return R.layout.activity_phase_help;
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        mPhaseHelpImgv.setImage(ImageSource.resource(R.drawable.help));

    }


    @OnClick({R.id.left_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
        }
    }
}
