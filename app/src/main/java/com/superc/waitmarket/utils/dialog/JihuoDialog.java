package com.superc.waitmarket.utils.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.superc.waitmarket.R;
import com.superc.waitmarket.adapter.JiHuoAdapter;

import java.util.List;
import java.util.Map;

public class JihuoDialog extends AlertDialog {
    private final Window mWindow;
    private Context mContext;
    private JihuoDialog mDigRebate;
    private Display mDisplay;
    private ImageView mimgv_close;
    private TextView mtv_title;
    private RecyclerView mRecyclerView;
    private List<Map<String, Object>> mMapList;
    private JiHuoAdapter mJiHuoAdapter;
    private OnFinishListener mOnFinishListener;
    private OnChaClickListener mOnChaClickListener;
    private String title;


    public JihuoDialog(Context context, List<Map<String, Object>> mMapList) {
        super(context, R.style.WorkDialogTheme);
        mContext = context;
        mWindow = getWindow();
        mDisplay = mWindow.getWindowManager().getDefaultDisplay();
        mDigRebate = this;
        this.mMapList = mMapList;
    }

    public JihuoDialog(Context context, List<Map<String, Object>> mMapList, String titlee) {
        super(context, R.style.WorkDialogTheme);
        mContext = context;
        mWindow = getWindow();
        mDisplay = mWindow.getWindowManager().getDefaultDisplay();
        mDigRebate = this;
        this.mMapList = mMapList;
        title=titlee;
    }

    public void setMapList(List<Map<String, Object>> mapList) {
        mMapList = mapList;
        mJiHuoAdapter.notifyDataSetChanged();
    }

    public void setOnFinishListener(OnFinishListener onFinishListener) {
        mOnFinishListener = onFinishListener;
    }

    public void setOnChaClickListener(OnChaClickListener onChaClickListener) {
        mOnChaClickListener = onChaClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_jihuo);
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
        mimgv_close = findViewById(R.id.dialog_jihuo_close);
        mRecyclerView = findViewById(R.id.dialog_jihuo_recy);
        mtv_title = findViewById(R.id.dialog_jihuo_title);
        mJiHuoAdapter = new JiHuoAdapter(mContext, mMapList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mJiHuoAdapter);
         mtv_title.setText(title);
        mJiHuoAdapter.setOnItemclickListener(new JiHuoAdapter.OnItemClickListener() {
            @Override
            public void onItemclickListener(int pos) {
                mDigRebate.dismiss();
                if (mOnFinishListener != null)
                    mOnFinishListener.onFinishListener(pos, (String) mMapList.get(pos).get("content"));
            }
        });


        mimgv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDigRebate.dismiss();
                if (mOnChaClickListener != null) {
                    mOnChaClickListener.onChaClickListener(0, (String) mMapList.get(0).get("content"));
                }
            }
        });
    }

    public interface OnFinishListener {
        void onFinishListener(int pos, String what);
    }

    public interface OnChaClickListener {
        void onChaClickListener(int pos, String what);
    }

    @Override
    public void show() {
        super.show();
        mWindow.setWindowAnimations(R.style.Pop_inOut_anim);
//        Window window = mDigRebate.getWindow();
//        WindowManager.LayoutParams attributes = window.getAttributes();
//        attributes.width = (int) (mDisplay.getWidth() * 0.9);
//        window.setAttributes(attributes);
    }
}
