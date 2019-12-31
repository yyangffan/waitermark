package com.superc.waitmarket.base;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface ApiService {
    /*登录*/
    @POST("v1/personalCenter/login")
    Observable<JSONObject> login(@Body RequestBody map);
    /*成绩首页*/
    @POST("v1/resultManager/resultManagerIndex")
    Observable<JSONObject> resultManagerIndex(@Body RequestBody map);
    /*商户拓展数详情*/
    @POST("v1/resultManager/bussinessInfo")
    Observable<JSONObject> bussinessInfo(@Body RequestBody map);
    /*商户拓展按日*/
    @POST("v1/resultManager/selectBusinessCountByDay")
    Observable<JSONObject> selectBusinessCountByDay(@Body RequestBody map);
    /*交易流水详情*/
    @POST("v1/resultManager/businessRecordInfo")
    Observable<JSONObject> businessRecordInfo(@Body RequestBody map);
    /*交易流水按日*/
    @POST("v1/resultManager/businessRecorDetail")
    Observable<JSONObject> businessRecorDetail(@Body RequestBody map);
    /*流量详情*/
    @POST("v1/resultManager/businessFlowDetail")
    Observable<JSONObject> businessFlowDetail(@Body RequestBody map);
    /*头像修改--上传图片*/
    @Multipart
    @POST("v1/personalCenter/updateHeadImg")
    Observable<JSONObject> updateHeadImg(@Part MultipartBody.Part map);
    /*头像修改--路径存数据库*/
    @POST("v1/personalCenter/updateHeadImgPath")
    Observable<JSONObject> updateHeadImgPath(@Body RequestBody map);
    /*用户个人信息页面*/
    @POST("v1/personalCenter/userInfoPage")
    Observable<JSONObject> userInfoPage(@Body RequestBody map);
    /*修改密码---发送验证码*/
    @POST("v1/personalCenter/getPhoneCode")
    Observable<JSONObject> getPhoneCode(@Body RequestBody map);
    /*修改密码---验证短信验证码*/
    @POST("v1/personalCenter/verificationPhoneCode")
    Observable<JSONObject> verificationPhoneCode(@Body RequestBody map);
    /*修改密码---传送密码*/
    @POST("v1/personalCenter/updatePassword")
    Observable<JSONObject> updatePassword(@Body RequestBody map);
    /*用户个人收益查询*/
    @POST("v1/personalCenter/profitInfo")
    Observable<JSONObject> profitInfo(@Body RequestBody map);
    /*查看全部消息*/
    @POST("v1/personalCenter/viewMessage")
    Observable<JSONObject> viewMessage(@Body RequestBody map);
    /*查看单条消息*/
    @POST("v1/personalCenter/viewMessageByOne")
    Observable<JSONObject> viewMessageByOne(@Body RequestBody map);
    /*查看网点营销数据*/
    @POST("v1/personalCenter/smallBranchBankData")
    Observable<JSONObject> smallBranchBankData(@Body RequestBody map);
    /*查看中支营销数据*/
    @POST("v1/personalCenter/branchBankData")
    Observable<JSONObject> branchBankData(@Body RequestBody map);
    /*查看全行营销数据*/
    @POST("v1/personalCenter/bankData")
    Observable<JSONObject> bankData(@Body RequestBody map);
    /*刷新个人页面*/
    @POST("v1/personalCenter/userInfoNew")
    Observable<JSONObject> userInfoNew(@Body RequestBody map);
    /*按支行查看营销数据*/
    @POST("v1/personalCenter/branchBankDataByOne")
    Observable<JSONObject> branchBankDataByOne(@Body RequestBody map);
    /*商户管理列表*/
    @POST("v1/shopManager/shopManagerIndex")
    Observable<JSONObject> shopManagerIndex(@Body RequestBody map);
    /*一级行业*/
    @POST("v1/shopManager/secondaryIndustry")
    Observable<JSONObject> secondaryIndustry(@Body RequestBody map);
    /*全部一级行业*/
    @POST("v1/shopManager/getFirsttierIndustry")
    Observable<JSONObject> getFirsttierIndustry(@Body RequestBody map);
    /*全部二级行业*/
    @POST("v1/shopManager/getSecondaryIndustry")
    Observable<JSONObject> getSecondaryIndustry(@Body RequestBody map);
    /*获取城市*/
    @POST("v1/shopManager/getCity")
    Observable<JSONObject> getCity(@Body RequestBody map);
    /*获取区域*/
    @POST("v1/shopManager/getDistrict")
    Observable<JSONObject> getDistrict(@Body RequestBody map);
    /*获取商圈*/
    @POST("v1/shopManager/getBusinessCircle")
    Observable<JSONObject> getBusinessCircle(@Body RequestBody map);
    /*上传店铺头像*/
    @Multipart
    @POST("v1/shopManager/uploadShopLogo")
    Observable<JSONObject> uploadShopLogo(@Part MultipartBody.Part map);
    /*维护列表*/
    @POST("v1/shopManager/maintenanceList")
    Observable<JSONObject> maintenanceList(@Body RequestBody map);
    /*待审核列表*/
    @POST("v1/shopManager/pendingList")
    Observable<JSONObject> pendingList(@Body RequestBody map);
    /*待激活列表*/
    @POST("v1/shopManager/activatedList")
    Observable<JSONObject> activatedList(@Body RequestBody map);
    /*商户池释放*/
    @POST("v1/shopManager/merchantPoolSearchfreed")
    Observable<JSONObject> merchantPoolSearchfreed(@Body RequestBody map);
    /*商户池领取*/
    @POST("v1/shopManager/merchantPoolSearchreceive")
    Observable<JSONObject> merchantPoolSearchreceive(@Body RequestBody map);
   /*商户池搜索*/
    @POST("v1/shopManager/merchantPoolSearch")
    Observable<JSONObject> merchantPoolSearch(@Body RequestBody map);
    /*商户池状态栏*/
    @POST("v1/shopManager/merchantPoolStatusBar")
    Observable<JSONObject> merchantPoolStatusBar(@Body RequestBody map);
    /*商户池*/
    @POST("v1/shopManager/merchantPool")
    Observable<JSONObject> merchantPool(@Body RequestBody map);
    /*商户池搜索列表*/
    @POST("v1/shopManager/merchantPoolSearchList")
    Observable<JSONObject> merchantPoolSearchList(@Body RequestBody map);
    /*上传店铺门头照*/
    @Multipart
    @POST("v1/shopManager/uploadDoorHead")
    Observable<JSONObject> uploadDoorHead(@Part MultipartBody.Part map);
     /*上传身份证正反面*/
    @Multipart
    @POST("v1/shopManager/uploadLegalPersonCard")
    Observable<JSONObject> uploadLegalPersonCard(@Part MultipartBody.Part map);
    /*上传gps定位截图*/
    @Multipart
    @POST("v1/shopManager/uploadGPS")
    Observable<JSONObject> uploadGPS(@Part MultipartBody.Part map);
    /*上传营业执照*/
    @Multipart
    @POST("v1/shopManager/uploadLicenseLogo")
    Observable<JSONObject> uploadLicenseLogo(@Part MultipartBody.Part map);
    /*上传银行卡正面照*/
    @Multipart
    @POST("v1/shopManager/uploadBankCardFront")
    Observable<JSONObject> uploadBankCardFront(@Part MultipartBody.Part map);
    /*上传开户行照片*/
    @Multipart
    @POST("v1/shopManager/uploadBankPermission")
    Observable<JSONObject> uploadBankPermission(@Part MultipartBody.Part map);
    /*上传承诺书*/
    @Multipart
    @POST("v1/shopManager/uploadLetterForPromise")
    Observable<JSONObject> uploadLetterForPromise(@Part MultipartBody.Part map);
    /*上传收款人身份证 正面，反面，商户资金清算授权书，法人手持商户资金清算授权书与法人身份证正面照片*/
    @Multipart
    @POST("v1/shopManager/uploadSettlementLegalPersonCard")
    Observable<JSONObject> uploadSettlementLegalPersonCard(@Part MultipartBody.Part map);
    /*上传店铺环境照*/
    @Multipart
    @POST("v1/shopManager/uploadEnvir")
    Observable<JSONObject> uploadEnvir(@PartMap Map<String, RequestBody> maps);
    /*新建商户资源--基础信息*/
    @POST("v1/shopManager/newMerchantResources")
    Observable<JSONObject> newMerchantResources(@Body RequestBody map);
    /*新建商户资源--场景照片*/
    @POST("v1/shopManager/scenePhotos")
    Observable<JSONObject> scenePhotos(@Body RequestBody map);
    /*新建商户资源--资质信息*/
    @POST("v1/shopManager/newQualificationInformation")
    Observable<JSONObject> newQualificationInformation(@Body RequestBody map);
    /*新建商户资源--结算信息*/
    @POST("v1/shopManager/newSettlementInformation")
    Observable<JSONObject> newSettlementInformation(@Body RequestBody map);
     /*提交*/
    @POST("v1/shopManager/submit")
    Observable<JSONObject> submit(@Body RequestBody map);
    /*商户详情*/
    @POST("v1/shopManager/merchantDetails")
    Observable<JSONObject> merchantDetails(@Body RequestBody map);
    /*支行信息*/
    @POST("v1/shopManager/tjBranch")
    Observable<JSONObject> tjBranch(@Body RequestBody map);
    /*支付营销活动设置*/
    @POST("v1/shopManager/campaignSettings")
    Observable<JSONObject> campaignSettings(@Body RequestBody map);
   /*已创建营销券返显*/
    @POST("v1/shopManager/currentActivity")
    Observable<JSONObject> currentActivity(@Body RequestBody map);
    /*活动列表*/
    @POST("v1/shopManager/eventsList")
    Observable<JSONObject> eventsList(@Body RequestBody map);
    /*商户管理上方数量*/
    @POST("v1/shopManager/getCount")
    Observable<JSONObject> getCount(@Body RequestBody map);



}
