package com.zhirongkeji.enforcement.Https;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.yolanda.nohttp.rest.Response;
import com.zhirongkeji.enforcement.Views.Toast;

import java.util.Map;

/**
 * Created by 章龙海 on 2016/11/24 15:05.
 *
 * @descript (单例回调结果处理)
 */

public class MyhttpCallBack implements HttpCallBack {

    Gson gson;
    static Map<String, Object> map;
    String jsonString;

    private static MyhttpCallBack instance;

    public static MyhttpCallBack getInstance() {
        if (instance == null) {
            instance = new MyhttpCallBack();
        }
        return instance;
    }



    @Override
    public void onSucceed(int what, Response response) {
        gson= new Gson();
        try {
            switch (what){

                default:break;
            }
        } catch (NullPointerException e) {
            //showdisplay(what);
            Toast.show("后台数据空返回!");
        }
        catch (IndexOutOfBoundsException e) {
            //showdisplay(what);
            Toast.show("后台数据结构变更下标越界!");
        } catch (ClassCastException e) {
            //showdisplay(what);
            Toast.show("后台数据变更类型转换出错!");
        }catch (NumberFormatException e) {
            //showdisplay(what);
            Toast.show("字符串转换为数字异常!");
        }catch (JsonSyntaxException e) {
            //showdisplay(what);
            Toast.show("后台数据变更json解析出错!");
        }

    }

    @Override
    public void onFailed(int what, Response response) {

    }
}
