package com.superc.waitmarket.utils.dialog;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.superc.waitmarket.R;
import com.superc.waitmarket.base.Constant;
import com.superc.waitmarket.bean.UserBean;
import com.superc.waitmarket.utils.SavePhoto;
import com.tbruyelle.rxpermissions2.RxPermissions;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.functions.Consumer;
import qrcode.ZXingCodeUtils;

public class WorkCardDialog extends AlertDialog {
    private final Window mWindow;
    private Context mContext;
    private Activity activity;
    private WorkCardDialog mDigRebate;
    private Display mDisplay;
    private ImageView mimgv_close, mImgv_erweim;
    private CircleImageView mCircleImageView;
    private TextView mtv_save, mtv_title, mtv_guiyuan, mtv_zhongzhi, mtv_wangdian, mtv_lianxi, mtv_youxiang;
    private LinearLayout mLinearLayout;
    private FrameLayout mFrameLayout;
    private int isWhat = 1;//1 服务  2云闪付
    private JSONObject mJSONObject;
    private Bitmap mImage;
    private SaveMiddleDialog mTishiDialog;
    private SavePhoto mInstance;


    protected WorkCardDialog(Context context, Builder builder) {
        super(context, R.style.WorkDialogTheme);
        mContext = context;
        mWindow = getWindow();
        mDisplay = mWindow.getWindowManager().getDefaultDisplay();
        mDigRebate = this;
        isWhat = builder.isWhat;
        mJSONObject = builder.mJSONObject;
        activity = builder.activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_wordcard);
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
        mimgv_close = findViewById(R.id.dig_rebate_close);
        mCircleImageView = findViewById(R.id.dialog_wordcard_head);
        mImgv_erweim = findViewById(R.id.dialog_wordcard_erweima);
        mtv_save = findViewById(R.id.dialog_wordcard_save);
        mtv_title = findViewById(R.id.dialog_wordcard_title);
        mtv_guiyuan = findViewById(R.id.dialog_wordcard_guiyuannum);
        mtv_zhongzhi = findViewById(R.id.dialog_wordcard_zhongzhi);
        mtv_wangdian = findViewById(R.id.dialog_wordcard_wangdian);
        mtv_lianxi = findViewById(R.id.dialog_wordcard_phone);
        mtv_youxiang = findViewById(R.id.dialog_wordcard_mailbox);
        mFrameLayout = findViewById(R.id.dialog_card_big);
        mLinearLayout = findViewById(R.id.dialog_card_lin);
        mTishiDialog = new SaveMiddleDialog.Builder(mContext).img_id(R.drawable.icon_chenggong).content("保存成功").sm_content("已保存至您的【手机相册】").build();
        mInstance = SavePhoto.getInstance(mContext);
        mInstance.setOnSaveFinishListener(new SavePhoto.OnSaveFinishListener() {
            @Override
            public void onSaveFinishListener(boolean is_save) {
                mTishiDialog.show();
            }
        });
        mimgv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDigRebate.dismiss();
            }
        });

        mtv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rxWritePermission();
            }
        });
        setData();
    }

    private void setData() {
        UserBean userBean=new Gson().fromJson(mJSONObject.toString(),UserBean.class);
        UserBean.DataBean data = userBean.getData();
        String shoplogo = data.getHeadimg();
        RequestOptions requestOptions=new RequestOptions().error(R.drawable.icon_error).placeholder(R.drawable.icon_error);
        if (!TextUtils.isEmpty(shoplogo)) {
            if (shoplogo.startsWith("http") || shoplogo.startsWith("https")) {
                Glide.with(mContext).load(shoplogo).apply(requestOptions).into(mCircleImageView);
            } else {
                Glide.with(mContext).load(Constant.IMG_URL + shoplogo).apply(requestOptions).into(mCircleImageView);
            }
        } else {
            Glide.with(mContext).load(Constant.IMG_URL + shoplogo).apply(requestOptions).into(mCircleImageView);
        }
        mtv_title.setText(data.getRealname());
        mtv_guiyuan.setText(data.getAccount());
        mtv_zhongzhi.setText(data.getBankname());
        mtv_wangdian.setText(data.getSmallbankname());
        mtv_lianxi.setText(data.getMobilePhone());
        mtv_youxiang.setText(data.getEmail());
//        mImage = ZXingCodeUtils.getInstance().createQRCode("https://www.jianshu.com/p/f7a7a8765294", 600, 600, BitmapFactory.decodeResource(mContext.getResources(), R.drawable.icon_head));
        mImage = ZXingCodeUtils.getInstance().createQRCode("https://www.jianshu.com/p/f7a7a8765294", 600, 600);
        mImgv_erweim.setImageBitmap(mImage);

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

    public static class Builder {
        private int isWhat = 1;
        private JSONObject mJSONObject;
        private Activity activity;

        public Builder isWhat(int isWhat) {
            this.isWhat = isWhat;
            return this;
        }

        public Builder data(JSONObject jsonObject) {
            mJSONObject = jsonObject;
            return this;
        }

        public WorkCardDialog builder(Context context) {
            return new WorkCardDialog(context, this);
        }


        public Builder activity(Activity activity) {
            this.activity = activity;
            return this;
        }

    }

  /*  private void toSave(String msg) {
        Toast.makeText(mContext, "保存中...", Toast.LENGTH_LONG).show();
        mImage = ZXingCodeUtils.getInstance().createQRCode("https://www.jianshu.com/p/f7a7a8765294", 600, 600);
//        saveBitmap("");
    }*/

    /**
     * 保存图片
     * name:保存图片的名称
     */
    public void saveBitmap(String name) {

        mLinearLayout.setBackgroundResource(R.drawable.icon_beijing);
        mimgv_close.setVisibility(View.GONE);
        mtv_save.setVisibility(View.GONE);
        Bitmap bitmap = viewConversionBitmap(mLinearLayout); // 获取图片
        mInstance.saveImage(bitmap, name);
        mLinearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
        mimgv_close.setVisibility(View.VISIBLE);
        mtv_save.setVisibility(View.VISIBLE);
    }

    /**
     * view转bitmap
     */
    public Bitmap viewConversionBitmap(View v) {
        int w = v.getRight();
        int h = v.getBottom();
        int l = v.getLeft();
        int t = v.getTop();
        Bitmap bmp = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

//        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */

        v.layout(l, t, w, h);
        v.draw(c);

        return bmp;
    }

    private void rxWritePermission() {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) throws Exception {
                if (granted) {
                    saveBitmap("工作证");
                } else {
                    Toast.makeText(activity, "请手动开启存储权限", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
