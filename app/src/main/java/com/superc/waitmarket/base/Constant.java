package com.superc.waitmarket.base;

import com.superc.yyfflibrary.utils.ShareUtil;

public class Constant {
    public static final boolean IS_JIAMI = false;/*接口是否使用加密  true:使用  false:不使用*/
    public static final boolean OPEN_RIZHI = true;/*是否打印上传参数  true:打印  false：不打印*/
    /*
     * 测试服务器地址：http://lifetest.qujie365.com/MarketingApp/
     * 新元服务器地址：http://192.168.1.135:9999/marketing/
     * 佳磊服务器地址：http://192.168.1.147:9999/marketing/
     * */
    public static final String BASE_URL = "http://lifetest.qujie365.com/MarketingApp/";
    /*
     * 测试图片地址：http://lifetest.qujie365.com/MarketingApp/
     * 新元图片地址：http://192.168.1.122:9999/marketing/
     * */
    public static final String IMG_URL = BASE_URL;
    public static final String IMAGE_SPLIT = "m/MarketingApp/";

    public static boolean isYihang() {
        return (boolean) ShareUtil.getInstance(WaitApplication.getInstance()).get("isYh", true);
    }

}
