package com.superc.waitmarket.utils.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.superc.waitmarket.R;
import com.superc.waitmarket.adapter.DialogBtAdapter;
import com.superc.waitmarket.bean.BotListBean;

import java.util.List;

public class DialogBotList extends AlertDialog {
    private Context mContext;
    private ImageView mImgv_back;
    private TextView mtv_title;
    private RecyclerView mRecyclerView;
    private String title;
    private List<BotListBean> mBotListBeanMap;
    private DialogBtAdapter mPhaseDialogBtAdapter;
    private OnTextClickListener mOnTextClickListener;
    private DialogBotList mDialogBotList;
    private View mView;


    public DialogBotList(Context context, Builder builder) {
        super(context);
        mContext = context;
        title = builder.title;
        mBotListBeanMap = builder.mBotListBeanMap;
        mDialogBotList=this;
    }

    public void setBotListBeanMap(List<BotListBean> botListBeanMap) {
        mBotListBeanMap = botListBeanMap;
        mPhaseDialogBtAdapter.notifyDataSetChanged();
    }

    public void setOnTextClickListener(OnTextClickListener onTextClickListener) {
        mOnTextClickListener = onTextClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_btlist,null);
        setContentView(mView);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setBackgroundDrawable(new ColorDrawable(0x0000ffff));
        window.setWindowAnimations(R.style.Dialog_ent_out);
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        if (mBotListBeanMap.size()>7) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, 1000);
        } else {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }

        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        mImgv_back = findViewById(R.id.dialog_btlist_imgv);
        mtv_title = findViewById(R.id.dialog_btlist_title);
        mRecyclerView = findViewById(R.id.dialog_btlist_recy);
        mtv_title.setText(title);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mPhaseDialogBtAdapter = new DialogBtAdapter(mContext, mBotListBeanMap);
        mRecyclerView.setAdapter(mPhaseDialogBtAdapter);
        mPhaseDialogBtAdapter.setOnItemClickListener(new DialogBtAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int pos) {
                mDialogBotList.dismiss();
                String name=mBotListBeanMap.get(pos).getName();
                String what = mBotListBeanMap.get(pos).getWhat();
                if(mOnTextClickListener!=null){
                    mOnTextClickListener.onTextClickListener(name);
                    mOnTextClickListener.onTextClickListenerHis(name,what);
                }
                for (int i = 0; i < mBotListBeanMap.size(); i++) {
                    if(i==pos){
                        mBotListBeanMap.get(i).setType(true);
                    }else {
                        mBotListBeanMap.get(i).setType(false);
                    }
                }
                mPhaseDialogBtAdapter.notifyDataSetChanged();
            }
        });
        mImgv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialogBotList.dismiss();
            }
        });
    }

    public static abstract class OnTextClickListener{
       public  void onTextClickListener(String name){};
       public  void onTextClickListenerHis(String name,String what){};
    }

    public static class Builder {
        private String title;
        private List<BotListBean> mBotListBeanMap;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder botListBeanMap(List<BotListBean> botListBeanMap) {
            mBotListBeanMap = botListBeanMap;
            return this;
        }

        public DialogBotList builder(Context context) {
            return new DialogBotList(context, this);
        }

    }


}
