package com.zhirongkeji.enforcement.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.zhirongkeji.enforcement.R;
import com.zhirongkeji.enforcement.Utils.WaitDialog;

/**
 * Created by 章龙海 on 2016/11/19.
 * <p>
 * 地图界面
 */

public class MapFragment extends Fragment {
    WaitDialog wd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.map_fragment, container, false);
        WebView wv = (WebView) v.findViewById(R.id.wv);
        wd = new WaitDialog(getContext());
        wd.show();
        WebSettings ws = wv.getSettings();//网页设置
        //设置 缓存模式
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        ws.setDomStorageEnabled(true);
        ws.setJavaScriptEnabled(true);
        ws.setRenderPriority(WebSettings.RenderPriority.HIGH);//提高渲染优先级
        //ws.setSupportZoom(true);
        //ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        ws.setUseWideViewPort(true);//自适应
        ws.setLoadWithOverviewMode(true);//自适应
        ws.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//网页缓存
        wv.loadUrl("http://m.amap.com/");
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });
        wv.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                if (newProgress == 100) {
                    wd.dismiss();
                }
            }
        });
        return v;
    }
}
