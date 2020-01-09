package com.superc.waitmarket.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.throwable.HttpThrowable;
import com.superc.waitmarket.R;
import com.superc.waitmarket.base.ApiService;
import com.superc.waitmarket.httputil.EncryPtionHttp;
import com.superc.waitmarket.httputil.EncryPtionUtil;
import com.superc.waitmarket.utils.MJavascriptInterface;
import com.superc.waitmarket.utils.MyWebViewClient;
import com.superc.waitmarket.utils.dialog.DialogPicbig;
import com.superc.yyfflibrary.base.BaseActivity;
import com.superc.yyfflibrary.utils.ShareUtil;
import com.superc.yyfflibrary.utils.titlebar.TitleUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;

public class MsgDetailActivity extends BaseActivity {


    @BindView(R.id.msg_deetail_title)
    TextView mMsgDeetailTitle;
    @BindView(R.id.msg_deetail_time)
    TextView mMsgDeetailTime;
    @BindView(R.id.msg_deetail_content)
    WebView mMsgDeetailContent;
//    @BindView(R.id.msg_detail_photo)
//    PhotoView mPhotoView;
    private String mUser_id;
    private String mId;
    private MJavascriptInterface mMJavascriptInterface;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_msg_detail;
    }

    @Override
    public void init() {
        TitleUtils.setStatusTextColor(true, this);
        ButterKnife.bind(this);
        mUser_id = (String) ShareUtil.getInstance(this).get("user_id", "");
        Intent intent = this.getIntent();
        if (intent != null) {
            mId = intent.getStringExtra("id");
        }
        mMJavascriptInterface = new MJavascriptInterface(this);
        mMsgDeetailContent.getSettings().setJavaScriptEnabled(true);
        mMsgDeetailContent.addJavascriptInterface(mMJavascriptInterface, "imagelistener");
        mMsgDeetailContent.setWebViewClient(new MyWebViewClient());
        mMJavascriptInterface.setOnPicClickListener(new MJavascriptInterface.OnPicClickListener() {
            @Override
            public void onPicClickListener(String url) {
                new DialogPicbig(MsgDetailActivity.this, url).show();
            }
        });
        getData();
    }


    @OnClick({R.id.imgv_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgv_back:
                finish();
                break;

        }
    }

    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", mUser_id);
        map.put("messageId", mId);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).viewMessageByOne(EncryPtionUtil.getInstance(this).toEncryption(map));
        EncryPtionHttp.getInstance(this).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    JSONObject data = result.getJSONObject("data");
                    mMsgDeetailTitle.setText(data.getString("title"));
                    mMsgDeetailTime.setText(data.getString("addtime"));
//                    String content_result=data.getString("content").replace("<img", "<img height=\"auto\"; width=\"100%\"");
                    mMsgDeetailContent.loadDataWithBaseURL(null, data.getString("content"), "text/html", "utf-8", null);
                }
                if (!TextUtils.isEmpty(msg)) {
                    ToastShow(msg);
                }
            }

            @Override
            public void onErrorResult(HttpThrowable httpThrowable) {
                ToastShow("接口访问异常" + httpThrowable.message);
                Log.d("qqq", httpThrowable.message);
            }
        });

    }

}
