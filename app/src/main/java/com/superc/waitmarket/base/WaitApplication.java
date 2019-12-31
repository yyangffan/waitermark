package com.superc.waitmarket.base;

import android.app.Application;

import com.ljy.devring.DevRing;

import cn.jpush.android.api.JPushInterface;

public class WaitApplication extends Application {
    private static volatile WaitApplication mInstance;

    public static WaitApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initDerving();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

    }

    private void initDerving() {
        DevRing.init(this);
        DevRing.configureHttp().setBaseUrl(Constant.BASE_URL).setConnectTimeout(60).setIsUseLog(true);
        DevRing.configureOther().setIsUseCrashDiary(true);
        DevRing.configureImage();

        DevRing.create();
//        //2.根据你的需求进行相关模块的全局配置
//        //配置网络请求模块，如 BaseUrl,连接超时时长，Log，全局 Header，Cookie，缓存，失败重试等
//        DevRing.configureHttp().setXXX()...
//        //配置图片加载模块，如替换实现框架，加载中图片，加载失败图片，开启过渡效果，缓存等
//        DevRing.configureImage().setXXX()...
//        //配置事件总线模块，如替换实现框架，EventBus 的 index 加速
//        DevRing.configureBus().setXXX()...
//        //配置数据库模块、替换实现框架
//        DevRing.configureDB(dbManager);
//        //配置缓存模块，如磁盘缓存的地址、大小等
//        DevRing.configureCache().setXXX()...
//        //配置其他模块，如 Toast 样式，是否显示 RingLog，是否启用崩溃日志等
//        DevRing.configureOther().setXXX()...

    }

}
