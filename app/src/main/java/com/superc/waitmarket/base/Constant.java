package com.superc.waitmarket.base;

import com.superc.yyfflibrary.utils.ShareUtil;

public class Constant {
    public static final boolean IS_JIAMI = false;/*接口是否使用加密  true:使用  false:不使用*/
    public static final boolean OPEN_RIZHI = true;/*是否打印上传参数  true:打印  false：不打印*/
    /*
     * 正式服务器地址：http://life.qujie365.com/marketing/
     * 测试服务器地址：http://lifetest.qujie365.com/MarketingApp/
     * 本地测试服务器地址：http://192.168.1.200:8311/MarketingApp/
     * 新元服务器地址：http://192.168.1.135:9999/marketing/
     * 高升服务器地址：http://192.168.1.126:9999/marketing/
     * */
    public static final String BASE_URL = "http://life.qujie365.com/marketing/";
    /*
     * 测试图片地址：http://lifetest.qujie365.com/MarketingApp/
     * 新元图片地址：http://192.168.1.122:9999/marketing/
     * 正式图片地址：http://61.240.30.232:8890/   测试图片地址：http://lifetest.qujie365.com/MarketingApp/
     * */
    public static final String IMG_URL ="http://life.qujie365.com/marketing/";
    public static final String IMAGE_SPLIT = "m/marketing/";

    public static boolean isYihang() {
        return (boolean) ShareUtil.getInstance(WaitApplication.getInstance()).get("isYh", true);
    }

}
