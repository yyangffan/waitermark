package com.superc.waitmarket.utils.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.github.chrisbanes.photoview.PhotoView;
import com.superc.waitmarket.R;
import com.superc.waitmarket.base.WaitApplication;
import com.superc.yyfflibrary.utils.ShareUtil;

public class DialogPicbig extends AlertDialog {
    private static final String TAG = "DialogPicbig";
    private DialogPicbig mDialogPicbig;
    private PhotoView mPhotoView;
    private Context mContext;
    private String img_url;

    public DialogPicbig(Context context,String img_url) {
        super(context, R.style.WorkDialogTheme);
        this.img_url = img_url;
        mContext = context;
        mDialogPicbig=this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pic);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.getDecorView().setMinimumWidth(mContext.getResources().getDisplayMetrics().widthPixels);
        WindowManager.LayoutParams lps = window.getAttributes();
        lps.horizontalMargin = 0;
//        lps.verticalMargin = 0.02f;
        window.setAttributes(lps);
        window.setWindowAnimations(R.style.Dialog_ent_out);
        setCanceledOnTouchOutside(false);
        init();
    }

    private void init() {
        String weburl = (String) ShareUtil.getInstance(WaitApplication.getInstance()).get("weburl", "");
        if(weburl.contains("%0D%0A")){
            weburl=weburl.replaceAll("%0D%0A","");
        }
        mPhotoView = findViewById(R.id.dialog_photo);
        mPhotoView.setImageBitmap(stringToBitmap(weburl));
        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogPicbig.dismiss();
            }
        });
    }

    public static Bitmap stringToBitmap(String string) {
        Bitmap bitmap = null;
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = false;//inJustDecodeBounds 需要设置为false，如果设置为true，那么将返回null
            byte[] bitmapArray = Base64.decode(string.split(",")[1], Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length, opts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
