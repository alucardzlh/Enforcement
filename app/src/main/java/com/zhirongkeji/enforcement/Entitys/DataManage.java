package com.zhirongkeji.enforcement.Entitys;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 章龙海 on 2016/11/23 16:09.
 *
 * @descript (Model管理类)
 */

public class DataManage {
    public static int 高,宽;
    public static List<Mission> MissioList = new ArrayList<>();

    public static class Mission {
        public String Title;
        public String Add;
        public int State;//0待办，1完成；
    }





    public static getMap getMapList = new getMap();//地图数据集合

    /**
     * 地图
     */
    public static class getMap {
        public String status;
        public String info;
        public String infocode;
        public String count;
        /**
         * formatted_address : 江西省南昌市青山湖区绿茵路|669号
         * province : 江西省
         * citycode : 0791
         * city : 南昌市
         * district : 青山湖区
         * township : []
         * neighborhood : {"name":[],"type":[]}
         * building : {"name":[],"type":[]}
         * adcode : 360111
         * street : []
         * number : []
         * location : 115.857949,28.698126
         * level : 门牌号
         */
        public List<GeocodesBean> geocodes;
        public static class GeocodesBean {
            public String formatted_address;
            public String province;
            public String citycode;
            public String city;
            public String adcode;
            public String location;
            public String level;

        }
    }

}
