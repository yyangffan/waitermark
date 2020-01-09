package com.superc.waitmarket.utils;

import android.content.Context;

import com.superc.waitmarket.base.WaitApplication;
import com.superc.yyfflibrary.utils.ShareUtil;

public class MJavascriptInterface {
    private Context context;
    private OnPicClickListener mOnPicClickListener;

    public MJavascriptInterface(Context context) {
        this.context = context;
    }

    public void setOnPicClickListener(OnPicClickListener onPicClickListener) {
        mOnPicClickListener = onPicClickListener;
    }

    @android.webkit.JavascriptInterface
    public void openImage(String img) {
        ShareUtil.getInstance(WaitApplication.getInstance()).put("weburl",img.replaceAll("&#13;&#10;",""));
        if(mOnPicClickListener!=null)
            mOnPicClickListener.onPicClickListener(img);
    }

    public interface OnPicClickListener{
        void onPicClickListener(String url);
    }
}
