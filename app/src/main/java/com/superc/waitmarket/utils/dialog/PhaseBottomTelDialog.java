package com.superc.waitmarket.utils.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.superc.waitmarket.R;
import com.superc.waitmarket.base.WaitApplication;
import com.superc.yyfflibrary.utils.ShareUtil;

public class PhaseBottomTelDialog {
    private Context mContext;
    private String mTel;
    private AlertDialog mBottomDialog;

    public PhaseBottomTelDialog(Context context) {
        mTel = (String) ShareUtil.getInstance(WaitApplication.getInstance()).get("m_tel", "022-59661128");
        mContext=context;
    }
    /*
    * phone.equals=='kefu' 显示客服电话  否则显示获取到的电话（如果为获取到也会显示客服电话）
    * */
    public AlertDialog initDig(String phone){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        mBottomDialog = builder.create();
        View mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_bottom, null);
        mBottomDialog.setView(mView);
        Window window = mBottomDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setBackgroundDrawable(new ColorDrawable(0x0000ffff));
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams lps = window.getAttributes();
        lps.verticalMargin = 0.02f;
        window.setAttributes(lps);
        window.setWindowAnimations(R.style.Dialog_ent_out);

        final TextView mtv_mobile = mView.findViewById(R.id.dialog_bottom_mobile);
        if(TextUtils.isEmpty(phone)){
            phone="kefu";
        }
        mtv_mobile.setText(phone.equals("kefu")?"022-59661128":phone);
        TextView mtv_cancel = mView.findViewById(R.id.dialog_bottom_cancel);
        mtv_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);//跳转到拨号界面
                //                intent.setAction(Intent.ACTION_CALL);//直接拨打电话
                intent.setData(Uri.parse("tel:" + mtv_mobile.getText().toString()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //开启系统拨号器
                mContext.startActivity(intent);
                mBottomDialog.dismiss();
            }
        });
        mtv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomDialog.dismiss();
            }
        });
        return mBottomDialog;
    }

}
