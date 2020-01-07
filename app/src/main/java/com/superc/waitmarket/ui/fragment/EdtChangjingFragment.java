package com.superc.waitmarket.ui.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.observer.CommonObserver;
import com.ljy.devring.http.support.throwable.HttpThrowable;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.superc.waitmarket.R;
import com.superc.waitmarket.adapter.MyGridImageAdapter;
import com.superc.waitmarket.base.ApiService;
import com.superc.waitmarket.base.Constant;
import com.superc.waitmarket.base.WaitApplication;
import com.superc.waitmarket.httputil.EncryPtionHttp;
import com.superc.waitmarket.httputil.EncryPtionUtil;
import com.superc.waitmarket.ui.activity.EdtDetailActivity;
import com.superc.waitmarket.utils.dialog.LoadingDialog;
import com.superc.waitmarket.utils.picselector.FullyGridLayoutManager;
import com.superc.waitmarket.views.InConstranLayout;
import com.superc.yyfflibrary.base.BaseFragment;
import com.superc.yyfflibrary.utils.ShareUtil;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EdtChangjingFragment extends BaseFragment {
    private static final String TAG = "EdtChangjingFragment";
    @BindView(R.id.edtjichu_head)
    ImageView mEdtjichuHead;
    @BindView(R.id.item_hangjpiclook_recy)
    RecyclerView recyclerView;
    @BindView(R.id.mine_shangp_red)
    ImageView mImg_de;
    @BindView(R.id.edtjichu_linear_huanjing)
    LinearLayout mLinearHuanjing;
    @BindView(R.id.incon_con)
    InConstranLayout mIncon;
    Unbinder unbinder;
    private MyGridImageAdapter adapter;
    private List<LocalMedia> selectList = new ArrayList<>();
    private int themeId;
    private String head_url;
    private String mPicSmallPath, mEventPath = "";
    private String mEdtdetail_id;
    private String mIs_creat;
    private EdtDetailActivity mEdtDetailActivity;
    private String channel;
    private boolean is_evenCommit=false;
    private LoadingDialog mLoadingDialog;
    private String mStatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edt_changjing, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void init() {
        themeId = R.style.picture_default_style;
        mLoadingDialog = LoadingDialog.getInstance(EdtChangjingFragment.this.getActivity());
        mEdtDetailActivity = (EdtDetailActivity) getActivity();
        FullyGridLayoutManager manager = new FullyGridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new MyGridImageAdapter(getActivity(), onAddPicClickListener);
        selectList.clear();
        selectList.add(new LocalMedia());
        adapter.setList(selectList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyGridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
               /* LocalMedia media = selectList.get(position);
                String pictureType = media.getPictureType();
                int mediaType = PictureMimeType.pictureToVideo(pictureType);
                switch (mediaType) {
                    case 1:
                        // 预览图片
                        PictureSelector.create(EdtChangjingFragment.this).themeStyle(themeId).openExternalPreview(position, selectList);
                        break;
                    case 2:
                        // 预览视频
                        PictureSelector.create(EdtChangjingFragment.this).externalPictureVideo(media.getPath());
                        break;
                    case 3:
                        // 预览音频
                        PictureSelector.create(EdtChangjingFragment.this).externalPictureAudio(media.getPath());
                        break;
                }*/
            }
        });
        adapter.setOnDeletClickListener(new MyGridImageAdapter.OnDeletClickListener() {
            @Override
            public void onDeletClickListener() {
                if (selectList.size() > 1) {
                    toUpDEnviro();
                }
                toPanduan();

            }
        });
        mStatus = (String) ShareUtil.getInstance(WaitApplication.getInstance()).get("status", "");
        if(mStatus.equals("1")){//1 不可编辑  0可编辑
            mIncon.setmIsIntercept(true);
        }
    }

    /* mIs_creat  0新建1修改*/
    public void toJudge() {
        mEdtdetail_id = (String) ShareUtil.getInstance(WaitApplication.getInstance()).get("edtdetail_id", "");
        channel = (String) ShareUtil.getInstance(WaitApplication.getInstance()).get("channel", "");
        mStatus = (String) ShareUtil.getInstance(WaitApplication.getInstance()).get("status", "");
        if(mStatus.equals("1")&&mIncon!=null){//1 不可编辑  0可编辑
            mIncon.setmIsIntercept(true);
        }
        toGetEdtData();
    }

    /**
     * type//1基础信息2场景照片3资质信息4结算信息5老客6新客
     * channel 0待激活入口,已激活入口1待审核入口 3维护列表
     */
    private void toGetEdtData() {
        Map<String, Object> map = new HashMap<>();
        map.put("shopId", mEdtdetail_id);
        map.put("type", 2);
        map.put("channel", channel);
        map.put("staus", mStatus);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).merchantDetails(EncryPtionUtil.getInstance(getActivity()).toEncryption(map));
        EncryPtionHttp.getInstance(getActivity()).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    JSONObject j = result.getJSONObject("data").getJSONObject("merchantDetails");
                    if (j == null) {//创建
                        mIs_creat = "0";
                    } else {//修改
                        mIs_creat = "1";
                        setData(result.getJSONObject("data").getJSONObject("merchantDetails"));
                    }
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

    private void setData(JSONObject merchant) {
        mPicSmallPath = merchant.getString("mpicPath");
        selectList.clear();
        selectList.add(new LocalMedia());
        RoundedCorners roundedCorners = new RoundedCorners(10);
        RequestOptions override = RequestOptions.bitmapTransform(roundedCorners).error(R.drawable.icon_error).placeholder(R.drawable.icon_error).override(300, 300);
        RequestOptions requestOptions = new RequestOptions().error(R.drawable.icon_error).apply(override).placeholder(R.drawable.icon_error);
        if (!TextUtils.isEmpty(mPicSmallPath)) {
            mEdtjichuHead.setVisibility(View.VISIBLE);
            mImg_de.setVisibility(View.VISIBLE);
            if (mPicSmallPath.startsWith("http") || mPicSmallPath.startsWith("https")) {
                Glide.with(this).load(mPicSmallPath).apply(requestOptions).into(mEdtjichuHead);
            } else {
                Glide.with(this).load(Constant.IMG_URL + mPicSmallPath).apply(requestOptions).into(mEdtjichuHead);
            }
        } else {
//            mEdtjichuHead.setVisibility(View.VISIBLE);
//            mImg_de.setVisibility(View.VISIBLE);
//            Glide.with(this).load(Constant.IMG_URL + mPicSmallPath).apply(requestOptions).into(mEdtjichuHead);
        }
        JSONArray timeList = merchant.getJSONArray("timeList");
        for (int i = 0; i < timeList.size(); i++) {
            LocalMedia localMedia = new LocalMedia();
            String lpicPath = timeList.getJSONObject(i).getString("lpicPath");
            if (!TextUtils.isEmpty(lpicPath)) {
                if (lpicPath.startsWith("http") || lpicPath.startsWith("https")) {
                    localMedia.setCompressPath(lpicPath);
                } else {
                    localMedia.setCompressPath(Constant.IMG_URL + lpicPath);
                }
                selectList.add(localMedia);
            }
        }
        adapter.setList(selectList);
        adapter.notifyDataSetChanged();
        toPanduan();


    }


    @OnClick({R.id.edtjichu_head, R.id.edtjichu_tianjia, R.id.edtjichu_linear, R.id.mine_shangp_red, R.id.edtjichu_linear_huanjing})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edtjichu_head:
            case R.id.edtjichu_tianjia:
            case R.id.edtjichu_linear:
                selectPic(110);
                break;
            case R.id.mine_shangp_red:
                mPicSmallPath = "";
                mImg_de.setVisibility(View.GONE);
                mEdtjichuHead.setVisibility(View.GONE);
                break;
            case R.id.edtjichu_linear_huanjing:
                selectPic(PictureConfig.CHOOSE_REQUEST);
                break;
        }
    }

    public void toCommit() {
        if(is_evenCommit){
            ToastShow("正在上传环境照，请稍候重试");
            return;
        }
        String result_event = "";
        for (int i = 1; i < selectList.size(); i++) {
            LocalMedia localMedia = selectList.get(i);
            String compressPath = localMedia.getCompressPath();
            if (compressPath.startsWith("http") || compressPath.startsWith("https")) {
                if (compressPath.split(Constant.IMAGE_SPLIT).length > 1)
                    result_event += compressPath.split(Constant.IMAGE_SPLIT)[1] + ",";
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("shopId", mEdtdetail_id);
        map.put("picSmallPath", mPicSmallPath);
        map.put("envirPicSmallPath", TextUtils.isEmpty(mEventPath) ? (selectList.size() > 1 ? result_event.substring(0, result_event.length() - 1) : "") : result_event + mEventPath);
        map.put("type", mIs_creat);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).scenePhotos(EncryPtionUtil.getInstance(getActivity()).toEncryption(map));
        EncryPtionHttp.getInstance(getActivity()).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                Log.d("qqq", result.toJSONString());
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    if (!mEdtDetailActivity.is_tijiao) {
                        mEventPath = "";
                    }else{
                        mEventPath = "";
                        toJudge();
                    }
                    mEdtDetailActivity.toScroll();

                } else {
                    if (!TextUtils.isEmpty(msg)) {
                        ToastShow(msg);
                    }
                }
            }

            @Override
            public void onErrorResult(HttpThrowable httpThrowable) {
                ToastShow("接口访问异常" + httpThrowable.message);
                Log.d("qqq", httpThrowable.message);
            }
        });

    }


    private MyGridImageAdapter.onAddPicClickListener onAddPicClickListener = new MyGridImageAdapter.onAddPicClickListener() {

        @Override
        public void onAddPicClick() {
            selectPic(PictureConfig.CHOOSE_REQUEST);
        }
    };

    private void selectPic(int reque_code) {
        PictureSelectionModel pictureSelectionModel = PictureSelector.create(this).openGallery(PictureMimeType.ofImage());
        if (reque_code != 110) {
            int size = 0;
            if (selectList != null) {
                size = selectList.size();
            }
            pictureSelectionModel.maxSelectNum(10 - size);
        }

        // 进入相册 以下是例子：用不到的api可以不写
        pictureSelectionModel
//                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .previewImage(false)// 是否可预览图片 true or false
                .selectionMode(reque_code == 110 ? PictureConfig.SINGLE : PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .isCamera(true)// 是否显示拍照按钮 true or false
                .enableCrop(false)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .compressSavePath(getPath())//压缩图片保存地址
                .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .circleDimmedLayer(false)//是否圆形裁剪
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .isDragFrame(false)// 是否可拖动裁剪框(固定)
//                .selectionMedia(selectList)
                .forResult(reque_code);//结果回调onActivityResult code
    }

    /**
     * 自定义压缩存储地址
     *
     * @return
     */
    private String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/Luban/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    selectList.addAll(PictureSelector.obtainMultipleResult(data));
//                    selectList = PictureSelector.obtainMultipleResult(data);
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    toPanduan();
                    toUpDEnviro();
                    break;
                case 110:
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    RoundedCorners roundedCorners = new RoundedCorners(10);
                    RequestOptions override = RequestOptions.bitmapTransform(roundedCorners).error(R.drawable.icon_error).placeholder(R.drawable.icon_error).override(300, 300);
                    head_url = selectList.get(0).getCompressPath();
                    Glide.with(EdtChangjingFragment.this).load(head_url).apply(override).into(mEdtjichuHead);
                    mEdtjichuHead.setVisibility(View.VISIBLE);
                    mImg_de.setVisibility(View.VISIBLE);
                    toUpDianp();
                    break;
            }
        }
    }

    private void toPanduan() {
        if (selectList.size() > 1) {
            mLinearHuanjing.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            mLinearHuanjing.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    private void toUpDianp() {
        File img = new File(head_url);
        String names = img.getName();
        RequestBody requestFile = RequestBody.create(MediaType.parse(guessMimeType(img.getPath())), img);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", names, requestFile);
        Observable<JSONObject> observable = DevRing.httpManager().getService(ApiService.class).uploadDoorHead(body);
        DevRing.httpManager().commonRequest(observable, new CommonObserver<JSONObject>() {
            @Override
            public void onResult(JSONObject result) {
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    mPicSmallPath = result.getJSONObject("data").getString("picSmallPath");
                } else {
                    if (!TextUtils.isEmpty(msg)) {
                        ToastShow(msg);
                    }
                }
            }

            @Override
            public void onError(HttpThrowable httpThrowable) {
            }
        }, (LifecycleTransformer) null);
    }

    private void toUpDEnviro() {
        Map<String, RequestBody> map = new HashMap<>();
        for (int i = 1; i < selectList.size(); i++) {
            LocalMedia localMedia = selectList.get(i);
            String compressPath = localMedia.getCompressPath();
            if (compressPath.startsWith("http") || compressPath.startsWith("https")) {
                Log.e(TAG, "toUpDEnviro: " + compressPath);
            } else {
                File file = new File(compressPath);
                // create RequestBody instance from file
                // RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
                //注意：file就是与服务器对应的key,后面filename是服务器得到的文件名
                map.put("file\"; filename=\"" + file.getName(), requestFile);
            }
        }
        mLoadingDialog.show();
        is_evenCommit=true;
        Observable<JSONObject> observable = DevRing.httpManager().getService(ApiService.class).uploadEnvir(map);
        DevRing.httpManager().commonRequest(observable, new CommonObserver<JSONObject>() {
            @Override
            public void onResult(JSONObject result) {
                mLoadingDialog.dismiss();
                is_evenCommit=false;
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    StringBuilder stringBuilder = new StringBuilder();
                    JSONArray data = result.getJSONArray("data");
                    for (int i = 0; i < data.size(); i++) {
                        JSONObject jsonObject = data.getJSONObject(i);
                        String picSmallPath = jsonObject.getString("picSmallPath");
                        stringBuilder.append(picSmallPath);
                        stringBuilder.append(",");
                    }
                    mEventPath = stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);

                } else {
                    if (!TextUtils.isEmpty(msg)) {
                        ToastShow(msg);
                    }
                }
            }

            @Override
            public void onError(HttpThrowable httpThrowable) {
                mLoadingDialog.dismiss();
                is_evenCommit=false;
            }
        }, (LifecycleTransformer) null);
    }

    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = null;
        try {
            contentTypeFor = fileNameMap.getContentTypeFor(URLEncoder.encode(path, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
