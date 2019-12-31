package com.superc.waitmarket.utils.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.superc.waitmarket.R;
import com.superc.waitmarket.bean.MarketDeatilBean;
import com.superc.waitmarket.utils.BigDecimalUtils;

import java.util.List;

/********************************************************************
 @version: 1.0.0
 @description: 反显弹窗
 @author: EDZ
 @time: 2019/11/14 10:33
 @变更历史: 2019/11/14 暂时隐藏掉了节假日功能   放开的话将TODO全部放开   且要修改布局文件
 ********************************************************************/
public class DialogDetailList extends AlertDialog {
    private Context mContext;
    private OnTextClickListener mOnTextClickListener;
    private DialogDetailList mDialogDetailList;
    private View mView;
    private Display mDisplay;
    private Window mWindow;
    private TextView mtv_week, mtv_tm, mtv_canorcant, mtv_date_wenzi, mtv_date,
            mtv_guize_wenzi, mtv_guize, mtv_zidingguize_wenzi, mtv_zidingguize, mtv_day, mtv_sure,
            mtv_heghMonywenzi, mtv_heghMony, mtv_djqStMony, mtv_djqStMonywenzi, mtv_djqNum, mtv_djqNumwenzi;
    private MarketDeatilBean.DataBean data;


    public DialogDetailList(Context context, MarketDeatilBean.DataBean dataBean, Display mDis) {
        super(context);
        mContext = context;
        mDisplay = mDis;
        data = dataBean;
        mDialogDetailList = this;
    }

    public void setOnTextClickListener(OnTextClickListener onTextClickListener) {
        mOnTextClickListener = onTextClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_quandetail, null);
        setContentView(mView);
        mWindow = getWindow();
        mWindow.setGravity(Gravity.CENTER);
        mWindow.setBackgroundDrawable(new ColorDrawable(0x0000ffff));
        mWindow.setWindowAnimations(R.style.Dialog_ent_out);
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        mtv_week = mView.findViewById(R.id.dialog_detail_week);
        mtv_tm = mView.findViewById(R.id.dialog_detail_tm);
        mtv_canorcant = mView.findViewById(R.id.dialog_detail_canorcant);
        mtv_date_wenzi = mView.findViewById(R.id.dialog_detail_date_wenzi);
        mtv_date = mView.findViewById(R.id.dialog_detail_date);
        mtv_guize_wenzi = mView.findViewById(R.id.dialog_detail_guize_wenzi);
        mtv_guize = mView.findViewById(R.id.dialog_detail_guize);
        mtv_zidingguize_wenzi = mView.findViewById(R.id.dialog_detail_free_wenzi);
        mtv_zidingguize = mView.findViewById(R.id.dialog_detail_free);
        mtv_day = mView.findViewById(R.id.dialog_detail_day);
        mtv_sure = mView.findViewById(R.id.dialog_detail_sure);
        mtv_heghMonywenzi = mView.findViewById(R.id.dialog_detail_heghmonywenzi);
        mtv_heghMony = mView.findViewById(R.id.dialog_detail_heghmony);
        mtv_djqStMony = mView.findViewById(R.id.dialog_detail_djqstmony);
        mtv_djqStMonywenzi = mView.findViewById(R.id.dialog_detail_djqstmony_wenzi);
        mtv_djqNum = mView.findViewById(R.id.dialog_detail_djqnum);
        mtv_djqNumwenzi = mView.findViewById(R.id.dialog_detail_djqnum_wenzi);


        mtv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnTextClickListener != null) {
                    mOnTextClickListener.onTextClickListener("");
                }
                mDialogDetailList.dismiss();

            }
        });
        setData();
    }

    private void setData() {
        if (data != null) {
            StringBuilder stb_time = new StringBuilder();
            int coupontype = data.getCoupontype();
            String weekdays = data.getWeekdays();
            stb_time.append(weekdays.contains("1") ? "周一、" : "");
            stb_time.append(weekdays.contains("2") ? "周二、" : "");
            stb_time.append(weekdays.contains("3") ? "周三、" : "");
            stb_time.append(weekdays.contains("4") ? "周四、" : "");
            stb_time.append(weekdays.contains("5") ? "周五、" : "");
            stb_time.append(weekdays.contains("6") ? "周六、" : "");
            stb_time.append(weekdays.contains("7") ? "周日、" : "");
            String resu_time = stb_time.toString();
            mtv_week.setText(resu_time.substring(0, resu_time.length() - 1));
            StringBuilder tb_tm = new StringBuilder();
            List<MarketDeatilBean.DataBean.ActTimeBean> timeList = data.getActTime();
            for (int i = 0; i < timeList.size(); i++) {
                if (i == 0) {
                    tb_tm.append(timeList.get(i).getStartTime() + "-" + timeList.get(i).getEndTime());
                } else {
                    tb_tm.append(" ");
                    tb_tm.append(timeList.get(i).getStartTime() + "-" + timeList.get(i).getEndTime());
                }
            }
            mtv_tm.setText(tb_tm.toString());
            // TODO: 2019/11/14 暂时隐藏掉节假日是否可用条件
            /*
            mtv_canorcant.setText(data.getHoliday() == 1 ? "可用" : "不可用");
            List<MarketDeatilBean.DataBean.HolidayDateBean> holidayD = data.getHolidayDate();
            if (holidayD != null && holidayD.size() > 0) {
                StringBuilder sb_data = new StringBuilder();
                for (int i = 0; i < holidayD.size(); i++) {
                    if (i == 0) {
                        sb_data.append(PhaseDateUtil.getStrTimeSetDian(holidayD.get(i).getStartDate()) + "-" + PhaseDateUtil.getStrTimeSetDian(holidayD.get(i).getEndDate()));
                    } else {
                        sb_data.append(" ");
                        sb_data.append(PhaseDateUtil.getStrTimeSetDian(holidayD.get(i).getStartDate()) + "-" + PhaseDateUtil.getStrTimeSetDian(holidayD.get(i).getEndDate()));
                    }
                }
                mtv_date.setText(sb_data.toString());
            } else {
                mtv_date_wenzi.setVisibility(View.GONE);
                mtv_date.setVisibility(View.GONE);
            }
            */
            mtv_zidingguize_wenzi.setVisibility(TextUtils.isEmpty(data.getCustomusagerules()) ? View.GONE : View.VISIBLE);
            mtv_zidingguize.setVisibility(TextUtils.isEmpty(data.getCustomusagerules()) ? View.GONE : View.VISIBLE);
            mtv_zidingguize.setText(data.getCustomusagerules());

            String usagerules = data.getUsagerules();
            if (!TextUtils.isEmpty(usagerules)) {
                mtv_guize.setText(usagerules.replace("#", "、"));
            } else {
                mtv_guize_wenzi.setVisibility(View.GONE);
                mtv_guize.setVisibility(View.GONE);
            }
            mtv_day.setText(data.getLimitvalidity() + "天");
            /*三期修改*/
//            data.getIsnewcustomer();
            String maxdiscountamount = data.getMaxdiscountamount();
            String startingpoint = data.getStartingpoint();
            String maxnumber = data.getMaxnumber();
            if (TextUtils.isEmpty(maxdiscountamount)) {//最高优惠
                mtv_heghMony.setVisibility(View.GONE);
                mtv_heghMonywenzi.setVisibility(View.GONE);
            } else {
                mtv_heghMony.setVisibility(View.VISIBLE);
                mtv_heghMonywenzi.setVisibility(View.VISIBLE);
                mtv_heghMony.setText(BigDecimalUtils.formatDouble(Double.parseDouble(maxdiscountamount))+"元");
            }
            if (TextUtils.isEmpty(startingpoint)) {
                mtv_djqStMonywenzi.setVisibility(View.GONE);
                mtv_djqStMony.setVisibility(View.GONE);
            } else {
                mtv_djqStMonywenzi.setVisibility(View.VISIBLE);
                mtv_djqStMony.setVisibility(View.VISIBLE);
                mtv_djqStMony.setText(BigDecimalUtils.formatDouble(Double.parseDouble(startingpoint))+"元");
            }
            if (TextUtils.isEmpty(maxnumber)) {
                mtv_djqNumwenzi.setVisibility(View.GONE);
                mtv_djqNum.setVisibility(View.GONE);
            } else {
                mtv_djqNumwenzi.setVisibility(View.VISIBLE);
                mtv_djqNum.setVisibility(View.VISIBLE);
                mtv_djqNum.setText(maxnumber+"张");
            }
            switch (coupontype) {
                case 1://满减券
                    mtv_djqStMonywenzi.setVisibility(View.GONE);
                    mtv_djqStMony.setVisibility(View.GONE);
                    mtv_djqNumwenzi.setVisibility(View.GONE);
                    mtv_djqNum.setVisibility(View.GONE);
                    break;
                case 2://折扣券
                case 3://赠品券
                case 5://体验券
                    mtv_heghMony.setVisibility(View.GONE);
                    mtv_heghMonywenzi.setVisibility(View.GONE);
                    mtv_djqStMonywenzi.setVisibility(View.GONE);
                    mtv_djqStMony.setVisibility(View.GONE);
                    mtv_djqNumwenzi.setVisibility(View.GONE);
                    mtv_djqNum.setVisibility(View.GONE);
                    break;
                case 6://代金券
                    mtv_heghMony.setVisibility(View.GONE);
                    mtv_heghMonywenzi.setVisibility(View.GONE);
                    break;
            }

        }
    }

    @Override
    public void show() {
        super.show();
        mWindow.setWindowAnimations(R.style.Pop_inOut_anim);
        Window window = mDialogDetailList.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = (int) (mDisplay.getWidth() * 0.7);
        window.setAttributes(attributes);
    }

    public interface OnTextClickListener {
        void onTextClickListener(String name);
    }


}
