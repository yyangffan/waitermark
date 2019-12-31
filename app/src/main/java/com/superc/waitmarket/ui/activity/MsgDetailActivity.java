package com.superc.waitmarket.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.throwable.HttpThrowable;
import com.superc.waitmarket.R;
import com.superc.waitmarket.base.ApiService;
import com.superc.waitmarket.httputil.EncryPtionHttp;
import com.superc.waitmarket.httputil.EncryPtionUtil;
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
    TextView mMsgDeetailContent;
    private String mUser_id;
    private String mId;

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

        getData();
    }


    @OnClick(R.id.imgv_back)
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
                    mMsgDeetailContent.setText(data.getString("content"));
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
