package com.superc.waitmarket.bean;

import java.util.List;

public class SearchBean {

    /**
     * code : true
     * data : [{"auditstatus":"3","shopid":103,"ShopName":"大西家好味烧","ShopAddress":"天津市和平区鞍山道110号"},{"auditstatus":"3","shopid":137,"ShopName":"美好客麻辣烫","ShopAddress":"天津市红桥区光荣道栖霞里1-103"},{"auditstatus":"3","shopid":227,"ShopName":"臻好时冻酸奶","ShopAddress":"欧乐广场鹿客西街B1"},{"auditstatus":"3","shopid":278,"ShopName":"臻好时冻酸奶（环球店）","ShopAddress":"天津市西青区环球购物中心b1层"},{"auditstatus":"3","shopid":294,"ShopName":"鳗好","ShopAddress":"恒隆广场5蹭层5031"},{"auditstatus":"3","shopid":321,"ShopName":"粤来香·好粥道","ShopAddress":"时代奥城商业广场A3一层"},{"auditstatus":"3","shopid":457,"ShopName":"味好家","ShopAddress":"南开区迎水道138号物美超市二楼"},{"auditstatus":"3","shopid":603,"ShopName":"好记炸货铺","ShopAddress":"河北区金海道22号"},{"auditstatus":"3","shopid":918,"ShopName":"三好龙虾","ShopAddress":"天津市和平区和平路308号"},{"auditstatus":"3","shopid":2445,"ShopName":"一定好烘焙坊","ShopAddress":"河东区爱琴海购物公园玫瑰天街322"},{"auditstatus":"3","shopid":2550,"ShopName":"百年好合婚纱摄影","ShopAddress":"中山东路东尚东塔六楼610室"},{"auditstatus":"3","shopid":2810,"ShopName":"好鸡饭好鸡汤（中冶和悦汇店）","ShopAddress":"天津市河西区黑牛城道与内江北路交口西北侧七贤南里112号底商"},{"auditstatus":"3","shopid":3191,"ShopName":"好时刻炸串","ShopAddress":"天津市南开区清化祠大街42号-B部分"},{"auditstatus":"3","shopid":3222,"ShopName":"好来顺快餐店","ShopAddress":"天津自贸试验区（中心北路）上海道94号滨海购物广场内小吃街7-2"},{"auditstatus":"3","shopid":3256,"ShopName":"好煮意重庆小面","ShopAddress":"天津市河西区南昌路广发楼1-3号23门"},{"auditstatus":"3","shopid":3816,"ShopName":"好食汇快餐","ShopAddress":"天津市河西区南北大街4号"},{"auditstatus":"3","shopid":4470,"ShopName":"好再来面馆（商业大学店）","ShopAddress":"北辰区刘房子荣国路泌芳里10号公寓1层底商"},{"auditstatus":"3","shopid":4671,"ShopName":"薯你好烤吧（均强路店）","ShopAddress":"天津市北辰区普东街强宜里11-1-104"},{"auditstatus":"3","shopid":4817,"ShopName":"好煮意土豆粉","ShopAddress":"天津市河西区新城小区10号楼底商"},{"auditstatus":"3","shopid":5046,"ShopName":"YouBeauty你好漂亮皮肤管理中心","ShopAddress":"天津河西区晶采大厦2号楼805"},{"auditstatus":"3","shopid":5285,"ShopName":"红利好运水果店","ShopAddress":"天津市滨海乐氏商贸有限公司新林苑市场D5-2号"}]
     * message :
     * returnTime : 201912271649120158
     */

    private boolean code;
    private String message;
    private String returnTime;
    private List<DataBean> data;

    public boolean isCode() {
        return code;
    }

    public void setCode(boolean code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * auditstatus : 3
         * shopid : 103
         * ShopName : 大西家好味烧
         * ShopAddress : 天津市和平区鞍山道110号
         */

        private String auditstatus;
        private int shopid;
        private String ShopName;
        private String ShopAddress;

        public String getAuditstatus() {
            return auditstatus;
        }

        public void setAuditstatus(String auditstatus) {
            this.auditstatus = auditstatus;
        }

        public int getShopid() {
            return shopid;
        }

        public void setShopid(int shopid) {
            this.shopid = shopid;
        }

        public String getShopName() {
            return ShopName;
        }

        public void setShopName(String ShopName) {
            this.ShopName = ShopName;
        }

        public String getShopAddress() {
            return ShopAddress;
        }

        public void setShopAddress(String ShopAddress) {
            this.ShopAddress = ShopAddress;
        }
    }
}
