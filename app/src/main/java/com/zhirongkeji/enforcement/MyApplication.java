package com.zhirongkeji.enforcement;

import android.app.Application;

import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;

/**
 * Created by 章龙海 on 2016/11/21 16:28.
 *
 * @descript (Application)
 */

public class MyApplication extends Application {

    private static Application instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        NoHttp.initialize(this);
        //打开Nohttp的调试模式
        Logger.setDebug(true);
        //设置Nohttp的日志tag
        Logger.setTag("forNoHttp");
    }
    /**
     * 得到应用程序的application
     *
     * @return {@link Application}
     */
    public static Application getInstance() {
        return instance;
    }
}
