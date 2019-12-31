package com.superc.waitmarket.utils.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.superc.waitmarket.R;


public class MiddleDialog extends AlertDialog {
    private View mView;
    private Context mContext;
    private String content, title;
    private int img_id;
    private ImageView mImageView;
    private TextView mtv_note, mtv_title;

    private MiddleDialog mPhaseRemindDialog;
    private final Window mWindow;
    private Display mDisplay;


    public MiddleDialog(Context context, Builder builder) {
        super(context, R.style.MiddleDialogTheme);
        setCanceledOnTouchOutside(false);
        mContext = context;
        mPhaseRemindDialog = this;
        mWindow = getWindow();
        mWindow.setDimAmount(0f);/*去掉背景*/
//        mWindow.setBackgroundDrawable(new ColorDrawable(0x0000ffff));
//        mWindow.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        mDisplay = mWindow.getWindowManager().getDefaultDisplay();
        content = builder.content;
        title = builder.title;
        img_id = builder.img_id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_middle, null);
        initView();
        setContentView(mView);
    }

    @Override
    public void show() {
        super.show();
        mWindow.setWindowAnimations(R.style.Pop_inOut_anim);
        Window window = mPhaseRemindDialog.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = (int) (mDisplay.getWidth() * 0.5);
        window.setAttributes(attributes);
    }

    private void initView() {
        mtv_title = mView.findViewById(R.id.third_dig_title);
        mtv_note = mView.findViewById(R.id.third_dig_note);
        mImageView = mView.findViewById(R.id.third_dig_imgv);
        if (img_id != 0)
            mImageView.setImageResource(img_id);
        setCon(mtv_note, content);
        setCon(mtv_title, title);
        new CountDownTimer(1400, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                mPhaseRemindDialog.dismiss();
            }
        }.start();
    }

    private void setCon(TextView tv, String con) {
        tv.setVisibility(TextUtils.isEmpty(con) ? View.GONE : View.VISIBLE);
        tv.setText(con);
    }

    public static class Builder {
        private String content, title;
        private int img_id = 0;
        private Context mContext;

        public Builder(Context context) {
            mContext = context;
        }


        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder img_id(int img_id) {
            this.img_id = img_id;
            return this;
        }

        public MiddleDialog build() {
            return new MiddleDialog(mContext, this);
        }

    }


}

