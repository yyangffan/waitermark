package com.superc.waitmarket.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.github.chrisbanes.photoview.PhotoView;
import com.superc.waitmarket.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends AppCompatActivity {
    private static final String TAG = "TestActivity";

    @BindView(R.id.editText)
    EditText mEditText;
    @BindView(R.id.imageView9)
    PhotoView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

    }

    @OnClick({R.id.textView107, R.id.textView108})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.textView107:
                break;
            case R.id.textView108:
                break;
        }
    }


}
