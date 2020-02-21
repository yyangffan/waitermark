package com.superc.waitmarket.ui.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.observer.CommonObserver;
import com.ljy.devring.http.support.throwable.HttpThrowable;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.superc.waitmarket.R;
import com.superc.waitmarket.base.ApiService;
import com.superc.waitmarket.base.Constant;
import com.superc.waitmarket.base.WaitApplication;
import com.superc.waitmarket.bean.UserBean;
import com.superc.waitmarket.httputil.EncryPtionHttp;
import com.superc.waitmarket.httputil.EncryPtionUtil;
import com.superc.waitmarket.ui.activity.AllMarketActivity;
import com.superc.waitmarket.ui.activity.AllMarketdetailActivity;
import com.superc.waitmarket.ui.activity.MsgActivity;
import com.superc.waitmarket.ui.activity.SafeCenterActivity;
import com.superc.waitmarket.ui.activity.WalletActivity;
import com.superc.waitmarket.ui.manager.activity.PartGroupActivity;
import com.superc.waitmarket.ui.manager.activity.WholeDepartActivity;
import com.superc.waitmarket.utils.BigDecimalUtils;
import com.superc.waitmarket.utils.dialog.MiddleDialog;
import com.superc.waitmarket.utils.dialog.WorkCardDialog;
import com.superc.yyfflibrary.base.BaseFragment;
import com.superc.yyfflibrary.utils.ShareUtil;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;
import static com.ljy.devring.util.ImageUtil.calculateInSampleSize;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends BaseFragment {
    private static final String TAG = "UserFragment";

    @BindView(R.id.user_head)
    CircleImageView mUserHead;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.user_zhengjian)
    ImageView mUserZhengjian;
    @BindView(R.id.user_guiyuan)
    TextView mUserGuiyuan;
    @BindView(R.id.user_zhongzhi)
    TextView mUserZhongzhi;
    @BindView(R.id.user_zhihname)
    TextView mUserZhihname;
    @BindView(R.id.user_one_money)
    TextView mUserOneMoney;
    @BindView(R.id.user_two_msg)
    TextView mUserTwoMsg;
    @BindView(R.id.user_three)
    TextView mUserYingXiao;
    @BindView(R.id.textView15)
    TextView guiOne;
    @BindView(R.id.textView17)
    TextView fenqu;
    @BindView(R.id.textView23)
    TextView xiaozu;
    @BindView(R.id.user_smart)
    SmartRefreshLayout mSmartRefreshLayout;


    Unbinder unbinder;
    private String head_url;
    private String mUser_id;
    private String mHead_url;
    private WorkCardDialog mWorkCardDialog;
    private String mType, mJlType;
    private String num = "- -";
    private boolean mYihang;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userk, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void init() {
        mUser_id = (String) ShareUtil.getInstance(this.getActivity()).get("user_id", "");
        mHead_url = (String) ShareUtil.getInstance(getActivity()).get("head_url", "");
        mType = (String) ShareUtil.getInstance(getActivity()).get("type", "");
        mJlType = (String) ShareUtil.getInstance(getActivity()).get("jltype", "");
        mYihang = Constant.isYihang();
        mSmartRefreshLayout.setEnableOverScrollDrag(true);
        mSmartRefreshLayout.setEnablePureScrollMode(true);
        RequestOptions requestOptions = new RequestOptions().error(R.drawable.icon_mourentouxiang).placeholder(R.drawable.icon_mourentouxiang);
        Glide.with(this).load(mHead_url).apply(requestOptions).into(mUserHead);
        if (mYihang) {
            switch (mType) {
                case "0":
                    mUserYingXiao.setText("网点营销数据");
                    break;
                case "1":
                    mUserYingXiao.setText("中支行营销数据");
                    break;
                case "2":
                    mUserYingXiao.setText("全行营销数据");
                    break;
                case "3":
                    mUserYingXiao.setVisibility(View.GONE);
                    break;
            }
        } else {
            mUserZhengjian.setVisibility(View.GONE);
            mUserGuiyuan.setText("工号：");
            fenqu.setText("分区：");
            xiaozu.setText("小组：");
            switch (mJlType) {
                case "0":
                    mUserYingXiao.setText("小组营销数据");
                    break;
                case "1":
                    mUserYingXiao.setText("分区营销数据");
                    break;
                case "2":
                    mUserYingXiao.setText("全司营销数据");
                    break;
                case "3":
                    mUserYingXiao.setVisibility(View.GONE);
                    break;
            }
        }
        getData();
        getMsg();
    }

    @OnClick({R.id.user_head, R.id.user_zhengjian, R.id.user_one, R.id.user_one_money, R.id.user_two, R.id.user_two_msg, R.id.textView47, R.id.user_three,
            R.id.user_four, R.id.imageView3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_head:
            case R.id.imageView3:
                selectPic();
                break;
            case R.id.user_zhengjian:
                if (mWorkCardDialog != null)
                    mWorkCardDialog.show();
                break;
            case R.id.user_one:
            case R.id.user_one_money:
                Intent wa_int = new Intent(getActivity(), WalletActivity.class);
                wa_int.putExtra("num", num);
                startActivity(wa_int);
                break;
            case R.id.user_two:
            case R.id.user_two_msg:
            case R.id.textView47:
                statActivity(MsgActivity.class);
                break;
            case R.id.user_three:
                if (mYihang) {
                if (mType.equals("1")) {
                    Intent intent = new Intent(this.getActivity(), AllMarketdetailActivity.class);
                    intent.putExtra("id", "空");
                    startActivity(intent);
                } else {
                    statActivity(AllMarketActivity.class);
                }
                }else{
                    if(mJlType.equals("2")){
                        statActivity(WholeDepartActivity.class);
                    }else{
                        Intent intent=new Intent(getActivity(),PartGroupActivity.class);
                        intent.putExtra("id","空");
                        startActivity(intent);
                    }
                }
                break;
            case R.id.user_four:
                statActivity(SafeCenterActivity.class);
                break;
        }
    }

    public void getMsg() {
        if (TextUtils.isEmpty(mUser_id)) {
            mUser_id = (String) ShareUtil.getInstance(WaitApplication.getInstance()).get("user_id", "");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", mUser_id);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).userInfoNew(EncryPtionUtil.getInstance(getActivity()).toEncryption(map));
        EncryPtionHttp.getInstance(getActivity()).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    JSONObject data = result.getJSONObject("data");
                    num = BigDecimalUtils.bigUtil(data.getString("amount"));
                    mUserOneMoney.setText("¥" + num);
                    mUserTwoMsg.setVisibility(TextUtils.isEmpty(BigDecimalUtils.bigUtil(data.getString("messagecount"))) ? View.GONE : (BigDecimalUtils.bigUtil(data.getString("messagecount")).equals("0") ? View.GONE : View.VISIBLE));
                    mUserTwoMsg.setText(BigDecimalUtils.bigUtil(data.getString("messagecount")));
                } else {
                    mUserTwoMsg.setVisibility(View.GONE);
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

    /*用户个人信息页面*/
    public void getData() {
        if (TextUtils.isEmpty(mUser_id)) {
            mUser_id = (String) ShareUtil.getInstance(WaitApplication.getInstance()).get("user_id", "");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", mUser_id);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).userInfoPage(EncryPtionUtil.getInstance(getActivity()).toEncryption(map));
        EncryPtionHttp.getInstance(getActivity()).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    UserBean userBean = new Gson().fromJson(result.toString(), UserBean.class);
                    try {
                        mWorkCardDialog = new WorkCardDialog.Builder().data(result).activity(UserFragment.this.getActivity()).builder(UserFragment.this.getActivity());

                    } catch (NullPointerException e) {
                        Log.e(TAG, "onSuccessResult: 个人中心" + e.toString());
                    }
                    mUserName.setText(userBean.getData().getRealname());
                    mUserGuiyuan.setText(userBean.getData().getAccount());
                    mUserZhongzhi.setText(userBean.getData().getBankname());
                    mUserZhihname.setText(userBean.getData().getSmallbankname());
                    String shoplogo = userBean.getData().getHeadimg();
                    RequestOptions requestOptions = new RequestOptions().error(R.drawable.icon_mourentouxiang).placeholder(R.drawable.icon_mourentouxiang);
                    if (!TextUtils.isEmpty(shoplogo)) {
                        if (shoplogo.startsWith("http") || shoplogo.startsWith("https")) {
                            Glide.with(UserFragment.this.getActivity()).load(shoplogo).apply(requestOptions).into(mUserHead);
                        } else {
                            Glide.with(UserFragment.this.getActivity()).load(Constant.IMG_URL + shoplogo).apply(requestOptions).into(mUserHead);
                        }
                    } else {
                        Glide.with(UserFragment.this.getActivity()).load(Constant.IMG_URL + shoplogo).apply(requestOptions).into(mUserHead);
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

    /*头像修改--上传图片*/
    private void toUpHead() {
        File img = new File(head_url);
        String names = img.getName();
        RequestBody requestFile = RequestBody.create(MediaType.parse(guessMimeType(img.getPath())), img);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", names, requestFile);
        Observable<JSONObject> observable = DevRing.httpManager().getService(ApiService.class).updateHeadImg(body);
        DevRing.httpManager().commonRequest(observable, new CommonObserver<JSONObject>() {
            @Override
            public void onResult(JSONObject result) {
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    String picPath = result.getJSONObject("data").getString("picPath");
                    RequestOptions requestOptions = new RequestOptions().error(R.drawable.icon_mourentouxiang).placeholder(R.drawable.icon_mourentouxiang);
                    if (!TextUtils.isEmpty(picPath)) {
                        if (picPath.startsWith("http") || picPath.startsWith("https")) {
                            Glide.with(UserFragment.this.getActivity()).load(picPath).apply(requestOptions).into(mUserHead);
                        } else {
                            Glide.with(UserFragment.this.getActivity()).load(Constant.IMG_URL + picPath).apply(requestOptions).into(mUserHead);
                        }
                    } else {
                        Glide.with(UserFragment.this.getActivity()).load(Constant.IMG_URL + picPath).apply(requestOptions).into(mUserHead);
                    }

                    String picSmallPath = result.getJSONObject("data").getString("picSmallPath");
                    toEndUpHead(picSmallPath);
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

    /*头像修改--路径存数据库*/
    private void toEndUpHead(String result_img) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", mUser_id);
        map.put("picSmallPath", result_img);
        Observable<JSONObject> jsonObjectObservable = DevRing.httpManager().getService(ApiService.class).updateHeadImgPath(EncryPtionUtil.getInstance(getActivity()).toEncryption(map));
        EncryPtionHttp.getInstance(getActivity()).getHttpResult(jsonObjectObservable, new EncryPtionHttp.OnHttpResult() {
            @Override
            public void onSuccessResult(JSONObject result) {
                boolean code = result.getBoolean("code");
                String msg = result.getString("message");
                if (code) {
                    new MiddleDialog.Builder(UserFragment.this.getActivity()).title("头像修改成功").img_id(R.drawable.icon_chenggong).build().show();
                    getData();
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

    private void selectPic() {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(this).openGallery(PictureMimeType.ofImage())
                .maxSelectNum(10)// 最大图片选择数量 int
//                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .previewImage(false)// 是否可预览图片 true or false
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .isCamera(true)// 是否显示拍照按钮 true or false
                .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .compressSavePath(getPath())//压缩图片保存地址
                .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .circleDimmedLayer(true)
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .isDragFrame(false)// 是否可拖动裁剪框(固定)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    head_url = selectList.get(0).getCompressPath();
                    Glide.with(UserFragment.this).load(head_url).into(mUserHead);
                    toUpHead();
                    break;
            }
        }
    }

    /**
     * 通过Base32将Bitmap转换成Base64字符串
     *
     * @return
     */
    public String Bitmap2StrByBase64(String filePath) {
        Bitmap bit = getSmallBitmap(filePath);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 40, bos);//参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    // 根据路径获得图片并压缩，返回bitmap用于显示
    public Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
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

    /**
     * 照片转byte二进制
     *
     * @param imagepath 需要转byte的照片路径
     * @return 已经转成的byte
     * @throws Exception
     */
    public static byte[] readStream(String imagepath) throws Exception {
        FileInputStream fs = new FileInputStream(imagepath);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while (-1 != (len = fs.read(buffer))) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        fs.close();
        return outStream.toByteArray();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
