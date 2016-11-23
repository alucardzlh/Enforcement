package com.zhirongkeji.enforcement.Entitys;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 章龙海 on 2016/11/23 16:09.
 *
 * @descript (Model管理类)
 */

public class DataManage {

    public static List<Mission> MissioList = new ArrayList<>();

    public static class Mission {
        public String Title;
        public String Add;
        public int State;//0待办，1完成；
    }
}
