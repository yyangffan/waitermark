package com.superc.waitmarket.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.superc.waitmarket.R;
import com.superc.waitmarket.utils.dialog.LoadingDialog;
import com.superc.yyfflibrary.utils.ToastUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends AppCompatActivity {
    private static final String TAG = "TestActivity";

    @BindView(R.id.editText)
    EditText mEditText;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
    }
    @OnClick({R.id.textView107,R.id.textView108})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.textView107:
                boolean phoneNumberValid = isPhoneNumberValid(mEditText.getText().toString());
                ToastUtil.showToast(this,phoneNumberValid?"验证通过":"验证失败");
                Log.e(TAG, "onViewClicked: "+phoneNumberValid);
                LoadingDialog.getInstance(this).show();
                break;
            case R.id.textView108:
                LoadingDialog.getInstance(this).dismiss();
                break;
        }
    }


    /*
     * 验证号码 手机号 固话均可
     *
     */
    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;

        String expression = "(^1[3|4|5|6|7|8|9]\\d{9}$)";
        CharSequence inputStr = phoneNumber;

        Pattern pattern = Pattern.compile(expression);

        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches()) {
            isValid = true;
        }

        return isValid;

    }

}
