package com.superc.waitmarket.utils.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.superc.waitmarket.R;
import com.superc.yyfflibrary.utils.ToastUtil;


public class MerBohuiDialog extends AlertDialog {
    private View mView;
    private Context mContext;
    private OnTextClickListener mOnTextClickListener;
    private TextView mtv_left;
    private TextView mtv_right;
    private EditText mEdt_content;

    private MerBohuiDialog mPhaseRemindDialog;
    private final Window mWindow;
    private Display mDisplay;


    public MerBohuiDialog(Context context, Builder builder) {
        super(context, R.style.AlertDialogTheme);
        setCanceledOnTouchOutside(false);
        mContext = context;
        mPhaseRemindDialog = this;
        mWindow = getWindow();
        mWindow.setBackgroundDrawable(new ColorDrawable(0x0000ffff));
        mWindow.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        mDisplay = mWindow.getWindowManager().getDefaultDisplay();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = LayoutInflater.from(mContext).inflate(R.layout.merchant_bohui, null);
        initView();
        setContentView(mView);
    }

    public void setOnTextClickListener(OnTextClickListener onTextClickListener) {
        mOnTextClickListener = onTextClickListener;
    }

    @Override
    public void show() {
        super.show();
        mWindow.setWindowAnimations(R.style.Pop_inOut_anim);
        Window window = mPhaseRemindDialog.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = (int) (mDisplay.getWidth() * 0.7);
        window.setAttributes(attributes);
    }

    private void initView() {
        mtv_left = mView.findViewById(R.id.dialog_remind_left);
        mtv_right = mView.findViewById(R.id.dialog_remind_right);
        mEdt_content = mView.findViewById(R.id.bohui_content);
        mtv_right.setEnabled(false);
        mEdt_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    mtv_right.setEnabled(false);
                    mtv_right.setTextColor(mContext.getResources().getColor(R.color.txt_week));
                }else{
                    mtv_right.setEnabled(true);
                    mtv_right.setTextColor(mContext.getResources().getColor(R.color.main_color));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mtv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnTextClickListener != null) {
                    mOnTextClickListener.onLeftClickListener();
                }
                mPhaseRemindDialog.dismiss();
            }
        });
        mtv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = mEdt_content.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.showToast(mContext, "请输入驳回原因");
                } else {
                    if (mOnTextClickListener != null) {
                        mOnTextClickListener.onRightClickListener(content);
                    }
                    mPhaseRemindDialog.dismiss();
                }
            }
        });

    }

    private void setCon(TextView tv, String con) {
        tv.setVisibility(TextUtils.isEmpty(con) ? View.GONE : View.VISIBLE);
        tv.setText(con);
    }

    public static abstract class OnTextClickListener {
        public void onLeftClickListener() {

        }

        public void onRightClickListener(String content) {

        }
    }

    public static class Builder {
        private Context mContext;

        public Builder(Context context) {
            mContext = context;
        }

        public MerBohuiDialog build() {
            return new MerBohuiDialog(mContext, this);
        }

    }


}

