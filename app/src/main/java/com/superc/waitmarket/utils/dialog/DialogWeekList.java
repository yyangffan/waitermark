package com.superc.waitmarket.utils.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.superc.waitmarket.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DialogWeekList extends AlertDialog {
    private Context mContext;
    private ImageView mImgv_back;
    private TextView mtv_one, mtv_two, mtv_three, mtv_four, mtv_five, mtv_six, mtv_seven, mtv_sure;
    private DialogWeekList mDialogBotList;
    private String content;
    private View mView;
    private List<Map<String, Object>> mWeeks;
    private OnSureClickListener mOnSureClickListener;
    private String[] mStrings = new String[]{"周一", "周二", "周三", "周四", "周五", "周六", "周日",};


    public DialogWeekList(Context context, String content) {
        super(context);
        mWeeks = new ArrayList<>();
        mContext = context;
        this.content = content;
        mDialogBotList = this;
    }

    public void setOnSureClickListener(OnSureClickListener onSureClickListener) {
        mOnSureClickListener = onSureClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_week, null);
        setContentView(mView);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setBackgroundDrawable(new ColorDrawable(0x0000ffff));
        window.setWindowAnimations(R.style.Dialog_ent_out);
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        mImgv_back = findViewById(R.id.dialog_week_imgv);
        mtv_one = findViewById(R.id.dialog_week_monday);
        mtv_two = findViewById(R.id.dialog_week_tuesday);
        mtv_three = findViewById(R.id.dialog_week_wednesday);
        mtv_four = findViewById(R.id.dialog_week_thurday);
        mtv_five = findViewById(R.id.dialog_week_firday);
        mtv_six = findViewById(R.id.dialog_week_staday);
        mtv_seven = findViewById(R.id.dialog_week_sunday);
        mtv_sure = findViewById(R.id.dialog_bt_weeksure);
        for (int i = 0; i < mStrings.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("num", (i + 1) + "");
            map.put("type", false);
            map.put("week", mStrings[i]);
            mWeeks.add(map);
        }
        toJudge("1", mtv_one);
        toJudge("2", mtv_two);
        toJudge("3", mtv_three);
        toJudge("4", mtv_four);
        toJudge("5", mtv_five);
        toJudge("6", mtv_six);
        toJudge("7", mtv_seven);
        mImgv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialogBotList.dismiss();
            }
        });
        mtv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnSureClickListener != null) {
                    mOnSureClickListener.onSureClickListener(toChangeProblem());
                }

                mDialogBotList.dismiss();
            }
        });

    }

    private Map<String, Object> toChangeProblem() {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder string_num = new StringBuilder();
        for (int i = 0; i < mWeeks.size(); i++) {
            boolean type = (boolean) mWeeks.get(i).get("type");
            if (type) {
                stringBuilder.append((String) mWeeks.get(i).get("week"));
                stringBuilder.append(",");
                string_num.append((String) mWeeks.get(i).get("num"));
                string_num.append(",");
            }
        }
        Map<String, Object> map=new HashMap<>();
        map.put("week",!TextUtils.isEmpty(stringBuilder.toString())?stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1):"");
        map.put("num",!TextUtils.isEmpty(string_num.toString())?string_num.toString().substring(0, string_num.toString().length() - 1):"");

        return map;
    }


    private void toJudge(final String what, final TextView tv) {
        tv.setTextColor(mContext.getResources().getColor(content.contains(what) ? R.color.main_color : R.color.txt_week));
        if (content.contains(what)) {
            for (Map<String, Object> map : mWeeks) {
                String num = (String) map.get("num");
                if (what.equals(num)) {
                    map.put("type", true);
                }
            }
        }
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toJudgeWhat(what,tv);
            }
        });
    }

    private void toJudgeWhat(String what,TextView mtv) {
        for (int i=0;i<mWeeks.size();i++ ) {
            Map<String,Object> map=mWeeks.get(i);
            String num = (String) map.get("num");
            boolean type = (boolean) map.get("type");
            if (what.equals(num)) {
                map.put("type", !type);
                boolean type_new = (boolean) map.get("type");
                mtv.setTextColor(mContext.getResources().getColor(type_new? R.color.main_color:R.color.txt_week ));
            }
        }
    }

    public interface OnSureClickListener {
        void onSureClickListener(Map<String, Object> map);
    }


}

