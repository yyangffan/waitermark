package com.superc.waitmarket.ui.manager.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.superc.waitmarket.R;
import com.superc.waitmarket.adapter.MyGridImageAdapter;
import com.superc.waitmarket.utils.dialog.LoadingDialog;
import com.superc.waitmarket.utils.dialog.MiddleDialog;
import com.superc.waitmarket.utils.picselector.FullyGridLayoutManager;
import com.superc.yyfflibrary.base.BaseActivity;
import com.superc.yyfflibrary.utils.titlebar.TitleUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OpeningActivity extends BaseActivity {

    @BindView(R.id.screen_name)
    EditText mScreenName;
    @BindView(R.id.screen_mianji)
    EditText mScreenMianji;
    @BindView(R.id.screen_num)
    EditText mScreenNum;
    @BindView(R.id.screen_kedanjia)
    EditText mScreenKedanjia;
    @BindView(R.id.item_img_qianshou)
    ImageView mItemImgQianshou;
    @BindView(R.id.item_img_qianshou_delete)
    ImageView mItemImgQianshouDelete;
    @BindView(R.id.item_yinxiang_img)
    ImageView mItemYinxiangImg;
    @BindView(R.id.item_yinxiang_imgdelete)
    ImageView mItemYinxiangImgdelete;
    @BindView(R.id.item_mentie_img)
    ImageView mItemMentieImg;
    @BindView(R.id.item_mentie_delete)
    ImageView mItemMentieDelete;
    @BindView(R.id.item_yinliang_img)
    ImageView mItemYinliangImg;
    @BindView(R.id.item_yinliang_delete)
    ImageView mItemYinliangDelete;
    @BindView(R.id.item_yunshanfu_img)
    ImageView mItemYunshanfuImg;
    @BindView(R.id.item_yunshanfu_delete)
    ImageView mItemYunshanfuDelete;
    @BindView(R.id.item_zfb_img)
    ImageView mItemZfbImg;
    @BindView(R.id.item_zfb_delete)
    ImageView mItemZfbDelete;
    @BindView(R.id.item_weixin_img)
    ImageView mItemWeixinImg;
    @BindView(R.id.item_weixin_elete)
    ImageView mItemWeixinElete;
    @BindView(R.id.item_shoukuanlq_img)
    ImageView mItemShoukuanlqImg;
    @BindView(R.id.item_shoukuanlq_delete)
    ImageView mItemShoukuanlqDelete;
    @BindView(R.id.edtjichu_linear_huanjing)
    LinearLayout mLinearHuanjing;
    @BindView(R.id.item_hangjpiclook_recy)
    RecyclerView recyclerView;
    @BindView(R.id.textView111)
    TextView mFocus;
    @BindView(R.id.smartlayout)
    SmartRefreshLayout mSmartlayout;
    private MyGridImageAdapter adapter;
    private List<LocalMedia> selectList = new ArrayList<>();
    private LoadingDialog mLoadingDialog;
    private String qianshou_url, yinxiang_url, mentie_url, yinlian_url, yunshanf_url, zfb_url, weixin_url, shoukum_url;
    private static final int QINSHOU_CODE = 110, YINXIANG_CODE = 111, MENTIE_URL = 112, YINLIAN_URL = 113, YUNSHANF_URL = 114, ZFB_URL = 115, WEIXIN_URL = 116, SHOUKUANM_URL = 117;


    @Override
    public int getContentLayoutId() {
        return R.layout.activity_opening;
    }

    @Override
    public void init() {
        TitleUtils.setStatusTextColor(true, this);
        ButterKnife.bind(this);
        mSmartlayout.setEnableOverScrollDrag(true);
        mSmartlayout.setEnablePureScrollMode(true);
        mFocus.requestFocus();
        mLoadingDialog = LoadingDialog.getInstance(this);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new MyGridImageAdapter(this, onAddPicClickListener);
        selectList.clear();
        selectList.add(new LocalMedia());
        adapter.setList(selectList);
        recyclerView.setAdapter(adapter);
        adapter.setOnDeletClickListener(new MyGridImageAdapter.OnDeletClickListener() {
            @Override
            public void onDeletClickListener() {
              /*  if (selectList.size() > 1) {
                    toUpDEnviro();
                }*/
                toPanduan();

            }
        });

    }

    @OnClick({R.id.imgv_back, R.id.item_qianshou_ll, R.id.item_img_qianshou_delete, R.id.item_yinxiang_ll, R.id.item_yinxiang_imgdelete,
            R.id.item_mentie_ll, R.id.item_mentie_delete, R.id.item_yinliang_ll, R.id.item_yinliang_delete, R.id.item_yunshanfu_ll,
            R.id.item_yunshanfu_delete, R.id.item_zfb_ll, R.id.item_zfb_delete, R.id.item_weixin_ll, R.id.item_weixin_elete, R.id.item_shoukuanlq_ll,
            R.id.item_shoukuanlq_delete, R.id.edtjichu_linear_huanjing, R.id.shenhe})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgv_back:
                finish();
                break;
            case R.id.item_qianshou_ll:
                selectPic(QINSHOU_CODE);
                mFocus.requestFocus();
                break;
            case R.id.item_img_qianshou_delete:
                qianshou_url = "";
                mItemImgQianshouDelete.setVisibility(View.GONE);
                mItemImgQianshou.setVisibility(View.GONE);
                break;
            case R.id.item_yinxiang_ll:
                selectPic(YINXIANG_CODE);
                mFocus.requestFocus();
                break;
            case R.id.item_yinxiang_imgdelete:
                yinxiang_url = "";
                mItemYinxiangImgdelete.setVisibility(View.GONE);
                mItemYinxiangImg.setVisibility(View.GONE);
                break;
            case R.id.item_mentie_ll:
                selectPic(MENTIE_URL);
                mFocus.requestFocus();
                break;
            case R.id.item_mentie_delete:
                mentie_url = "";
                mItemMentieDelete.setVisibility(View.GONE);
                mItemMentieImg.setVisibility(View.GONE);
                break;
            case R.id.item_yinliang_ll:
                selectPic(YINLIAN_URL);
                mFocus.requestFocus();
                break;
            case R.id.item_yinliang_delete:
                yinlian_url = "";
                mItemYinliangDelete.setVisibility(View.GONE);
                mItemYinliangImg.setVisibility(View.GONE);
                break;
            case R.id.item_yunshanfu_ll:
                selectPic(YUNSHANF_URL);
                mFocus.requestFocus();
                break;
            case R.id.item_yunshanfu_delete:
                yunshanf_url = "";
                mItemYunshanfuDelete.setVisibility(View.GONE);
                mItemYunshanfuImg.setVisibility(View.GONE);
                break;
            case R.id.item_zfb_ll:
                selectPic(ZFB_URL);
                mFocus.requestFocus();
                break;
            case R.id.item_zfb_delete:
                zfb_url = "";
                mItemZfbDelete.setVisibility(View.GONE);
                mItemZfbImg.setVisibility(View.GONE);
                break;
            case R.id.item_weixin_ll:
                selectPic(WEIXIN_URL);
                mFocus.requestFocus();
                break;
            case R.id.item_weixin_elete:
                weixin_url = "";
                mItemWeixinElete.setVisibility(View.GONE);
                mItemWeixinImg.setVisibility(View.GONE);
                break;
            case R.id.item_shoukuanlq_ll:
                selectPic(SHOUKUANM_URL);
                mFocus.requestFocus();
                break;
            case R.id.item_shoukuanlq_delete:
                shoukum_url = "";
                mItemShoukuanlqDelete.setVisibility(View.GONE);
                mItemShoukuanlqImg.setVisibility(View.GONE);
                break;
            case R.id.edtjichu_linear_huanjing:
                selectPic(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.shenhe:
                toCommit();
                break;
        }
    }

    private void toCommit() {
        String phone = mScreenName.getText().toString();
        String mianji = mScreenMianji.getText().toString();
        String congyNum = mScreenNum.getText().toString();
        String kedanjia = mScreenKedanjia.getText().toString();
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(mianji) || TextUtils.isEmpty(congyNum) || TextUtils.isEmpty(kedanjia) || TextUtils.isEmpty(qianshou_url) || TextUtils.isEmpty(yinxiang_url) || TextUtils.isEmpty(mentie_url)
                || TextUtils.isEmpty(yinlian_url) || TextUtils.isEmpty(yunshanf_url) || TextUtils.isEmpty(zfb_url) || TextUtils.isEmpty(weixin_url) || TextUtils.isEmpty(shoukum_url) || selectList.size() == 1) {
            new MiddleDialog.Builder(this).title("有必填信息未填").build().show();
            return;
//             if (selectList.size() < 4) {
//                ToastShow("产品照至少为3张");
//                return;
//            } else {
//                new MiddleDialog.Builder(this).title("有必填信息未填").build().show();
//            }
        }
        MiddleDialog build = new MiddleDialog.Builder(this).img_id(R.drawable.icon_chenggong).title("提交成功，请等待审核").build();
        build.show();
        build.setOnMiddleDigFinishListener(new MiddleDialog.OnMiddleDigFinishListener() {
            @Override
            public void onMiddleDigfinishListener() {
                OpeningActivity.this.finish();
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
            RoundedCorners roundedCorners = new RoundedCorners(10);
            RequestOptions override = RequestOptions.bitmapTransform(roundedCorners).error(R.drawable.icon_error).placeholder(R.drawable.icon_error).centerCrop().override(300, 300);
            List<LocalMedia> selectList_other = PictureSelector.obtainMultipleResult(data);
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    selectList.addAll(PictureSelector.obtainMultipleResult(data));
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    toPanduan();
                    /*  toUpDEnviro();*/
                    break;
                case QINSHOU_CODE:
                    qianshou_url = selectList_other.get(0).getCompressPath();
                    Glide.with(this).load(qianshou_url).apply(override).into(mItemImgQianshou);
                    mItemImgQianshou.setVisibility(View.VISIBLE);
                    mItemImgQianshouDelete.setVisibility(View.VISIBLE);
//                    toUpDianp();
                    break;
                case YINXIANG_CODE:
                    yinxiang_url = selectList_other.get(0).getCompressPath();
                    Glide.with(this).load(yinxiang_url).apply(override).into(mItemYinxiangImg);
                    mItemYinxiangImg.setVisibility(View.VISIBLE);
                    mItemYinxiangImgdelete.setVisibility(View.VISIBLE);
                    break;
                case MENTIE_URL:
                    mentie_url = selectList_other.get(0).getCompressPath();
                    Glide.with(this).load(mentie_url).apply(override).into(mItemMentieImg);
                    mItemMentieImg.setVisibility(View.VISIBLE);
                    mItemMentieDelete.setVisibility(View.VISIBLE);
                    break;
                case YINLIAN_URL:
                    yinlian_url = selectList_other.get(0).getCompressPath();
                    Glide.with(this).load(yinlian_url).apply(override).into(mItemYinliangImg);
                    mItemYinliangImg.setVisibility(View.VISIBLE);
                    mItemYinliangDelete.setVisibility(View.VISIBLE);
                    break;
                case YUNSHANF_URL:
                    yunshanf_url = selectList_other.get(0).getCompressPath();
                    Glide.with(this).load(yunshanf_url).apply(override).into(mItemYunshanfuImg);
                    mItemYunshanfuImg.setVisibility(View.VISIBLE);
                    mItemYunshanfuDelete.setVisibility(View.VISIBLE);
                    break;
                case ZFB_URL:
                    zfb_url = selectList_other.get(0).getCompressPath();
                    Glide.with(this).load(zfb_url).apply(override).into(mItemZfbImg);
                    mItemZfbImg.setVisibility(View.VISIBLE);
                    mItemZfbDelete.setVisibility(View.VISIBLE);
                    break;
                case WEIXIN_URL:
                    weixin_url = selectList_other.get(0).getCompressPath();
                    Glide.with(this).load(weixin_url).apply(override).into(mItemWeixinImg);
                    mItemWeixinImg.setVisibility(View.VISIBLE);
                    mItemWeixinElete.setVisibility(View.VISIBLE);
                    break;
                case SHOUKUANM_URL:
                    shoukum_url = selectList_other.get(0).getCompressPath();
                    Glide.with(this).load(shoukum_url).apply(override).into(mItemShoukuanlqImg);
                    mItemShoukuanlqImg.setVisibility(View.VISIBLE);
                    mItemShoukuanlqDelete.setVisibility(View.VISIBLE);
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

}
