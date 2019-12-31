package com.superc.waitmarket.utils.pop;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.superc.waitmarket.R;

public class PopShifhWindow extends PopupWindow {
    private View mView;
    private PopShifhWindow mOverallPop;
    private Activity mContext;
    private OnPopClickListener mOnPopClickListener;
    private ImageView mimgv_shif;

    public PopShifhWindow(Activity context) {
        super(context);
        mContext = context;
        mOverallPop = this;
        mView = LayoutInflater.from(context).inflate(R.layout.pop_shif, null);
        init();
        initPopWindow();

    }

    public void setOnPopClickListener(OnPopClickListener onPopClickListener) {
        mOnPopClickListener = onPopClickListener;
    }

    private void initPopWindow() {
        this.setContentView(mView);
        setAnimationStyle(R.style.Pop_inOut_anim);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setTouchable(true);
        setOutsideTouchable(true);
       // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00FFFFFF);
        //设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
//        backgroundAlpha(context, 0.5f);//0.0-1.0
        backgroundAlpha(mContext, 0.5f);
    }

    private void init() {
        mimgv_shif = mView.findViewById(R.id.pop_shif_imgv);

        mimgv_shif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnPopClickListener != null)
                    mOnPopClickListener.onPopClickListener();
                mOverallPop.dismiss();
                backgroundAlpha(mContext, 1.0f);
            }
        });

    }

    public interface OnPopClickListener {
        void onPopClickListener();
    }

    /**
     * 设置添加屏幕的背景透明度(值越大,透明度越高)
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }
}
