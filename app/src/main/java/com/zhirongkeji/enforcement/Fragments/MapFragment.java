package com.zhirongkeji.enforcement.Fragments;

import android.graphics.Color;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Poi;
import com.amap.api.maps.overlay.PoiOverlay;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.zhirongkeji.enforcement.Entitys.DataManage;
import com.zhirongkeji.enforcement.R;
import com.zhirongkeji.enforcement.Views.Toast;
import com.zhirongkeji.enforcement.Views.WaitDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.zhirongkeji.enforcement.R.id.result;

/**
 * Created by 章龙海 on 2016/11/19.
 * <p>
 * 地图界面
 */

public class MapFragment extends Fragment implements LocationSource, AMapLocationListener, PoiSearch.OnPoiSearchListener {
    WaitDialog wd;


    //AMap是地图对象
    private AMap aMap;
    private MapView mapView;
    //声明AMapLocationClient类对象，定位发起端
    private AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象，定位参数
    public AMapLocationClientOption mLocationOption = null;
    //声明mListener对象，定位监听器
    private OnLocationChangedListener mListener = null;
    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;
    double lng, lat, nlng, nlat;
    String city;
    Button walkbtn, drivbtn, busbtn;
    public static android.os.Handler handler;
    CheckBox maptyp1e;
    List<String> list;
    List<Double> list1, list2;
    List<Double> list1t, list2t;
    boolean flag = false;
    LinearLayout Textbtn;
    TextView Text1, Text2;
    MarkerOptions markerOption;
    UiSettings mUiSettings;
    String km = "";
    String time = "";
    int index;
    boolean TrFlag = false;
    RadioGroup.OnCheckedChangeListener radioButtonListener;
    List<String> listtx = new ArrayList<>();//路线途径点


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //View v = inflater.inflate(R.layout.map_fragment, container, false);
        View v = inflater.inflate(R.layout.map_fragment2, container, false);
        initView(v);
        mapView.onCreate(savedInstanceState);//初始化地图控件
//        WebView wv = (WebView) v.findViewById(R.id.wv);
//        wd = new WaitDialog(getContext());
//        wd.show();
//        WebSettings ws = wv.getSettings();//网页设置
//        //设置 缓存模式
//        ws.setCacheMode(WebSettings.LOAD_DEFAULT);
//        // 开启 DOM storage API 功能
//        ws.setDomStorageEnabled(true);
//        ws.setJavaScriptEnabled(true);
//        ws.setRenderPriority(WebSettings.RenderPriority.HIGH);//提高渲染优先级
//        //ws.setSupportZoom(true);
//        //ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        ws.setUseWideViewPort(true);//自适应
//        ws.setLoadWithOverviewMode(true);//自适应
//        ws.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//网页缓存
//        wv.loadUrl("http://m.amap.com/");
//        wv.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//
//        });
//        wv.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//
//                if (newProgress == 100&&null==wd) {
//                    wd.dismiss();
//                }
//            }
//        });


        return v;
    }

    private void initView(View v) {
        mapView = (MapView) v.findViewById(R.id.mapview);
        maptyp1e = (CheckBox) v.findViewById(R.id.maptyp1e);//交通路况


        if (aMap == null) {
            aMap = mapView.getMap();
            LatLng latLng = new LatLng(lat, lng);
            markerOption = new MarkerOptions();
        }
        mUiSettings = aMap.getUiSettings();
        mUiSettings.setCompassEnabled(true);//显示指南针
        mUiSettings.setScaleControlsEnabled(true);//显示比例尺
        mUiSettings.setMyLocationButtonEnabled(true);// 是否显示定位按钮
//            //设置缩放级别
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        aMap.setLocationSource(this);//设置了定位的监听
        aMap.setMyLocationEnabled(true);//显示定位层并且可以触发定位,默认是flase

        //PoiSearch.Query query = new PoiSearch.Query("绿地外滩公馆", "", cityCode);
        //poiSearch = new PoiSearch(this, query);
        //poiSearch.setOnPoiSearchListener(this);


    }

    private void location() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为Hight_Accuracy高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        mListener = null;
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
//            if (aMapLocation.getErrorCode() == 0) {
//                企业位置经纬度
            //GPS定位自己
            nlng = aMapLocation.getLongitude();
            nlat = aMapLocation.getLatitude();


            //定位成功回调信息，设置相关消息
            aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
            if (!flag) {
                aMapLocation.setLatitude(lat);//获取纬度
                aMapLocation.setLongitude(lng);//获取经度
                MyLocationStyle locationStyle = new MyLocationStyle();
                locationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.amap_icon_gcoding));
                locationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
                locationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
                locationStyle.strokeWidth(0);
                aMap.setMyLocationStyle(locationStyle);
            } else {
                //绘制起点
                LatLng x = new LatLng(nlat, nlng);//第一个参数是：latitude，第二个参数是longitude
                //添加标记
                markerOption.position(x);
                markerOption.title("我在这");
                markerOption.snippet("我在这");
                markerOption.perspective(true);
                markerOption.draggable(true);
                markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.amap_start));//设置图标
                aMap.addMarker(markerOption);
                //绘制终点
                LatLng x1 = new LatLng(lat, lng);//第一个参数是：latitude，第二个参数是longitude
                //添加标记
                markerOption.position(x1);
                markerOption.title("目的地");
                markerOption.snippet("目的地");
                markerOption.perspective(true);
                markerOption.draggable(true);
                markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.amap_end));//设置图标
                aMap.addMarker(markerOption);

                if (list1t != null && list1t.size() > 0) {
                    for (int y = 0; y < list1t.size(); y++) {
                        LatLng x12 = new LatLng(list2t.get(y), list1t.get(y));//第一个参数是：latitude，第二个参数是longitude
                        //添加标记
                        markerOption.position(x12);
                        markerOption.title("目的地");
                        markerOption.snippet("目的地");
                        markerOption.perspective(true);
                        markerOption.draggable(true);
                        markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.amap_way));//设置图标
                        aMap.addMarker(markerOption);
                    }

                }
            }
            aMapLocation.getAccuracy();//获取精度信息
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(aMapLocation.getTime());
            df.format(date);//定位时间
            aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
            aMapLocation.getCountry();//国家信息
            aMapLocation.getProvince();//省信息
            aMapLocation.getCity();//城市信息
            aMapLocation.getDistrict();//城区信息
            aMapLocation.getStreet();//街道信息
            aMapLocation.getStreetNum();//街道门牌号信息
            city = aMapLocation.getCityCode();//城市编码
            aMapLocation.getAdCode();//地区编码
            // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
            if (isFirstLoc) {
                //将地图移动到定位点
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
                //点击定位按钮 能够将地图的中心移动到定位点
                mListener.onLocationChanged(aMapLocation);

                //添加图钉
//                      aMap.addMarker(getMarkerOptions(amapLocation));
                //获取定位信息
                StringBuffer buffer = new StringBuffer();
                buffer.append(aMapLocation.getCountry() + ""
                        + aMapLocation.getProvince() + ""
                        + aMapLocation.getCity() + ""
                        + aMapLocation.getProvince() + ""
                        + aMapLocation.getDistrict() + ""
                        + aMapLocation.getStreet() + ""
                        + aMapLocation.getStreetNum());
//                    Toast.makeText(getApplicationContext(), buffer.toString(), Toast.LENGTH_LONG).show();GPS定位
                Text1.setText(DataManage.getMapList.geocodes.get(0).formatted_address + "");
                //Toast.makeText(getApplicationContext(),  DataManager.getMapList.geocodes.get(0).formatted_address+"", Toast.LENGTH_LONG).show();//企业经纬度定位
                com.zhirongkeji.enforcement.Views.Toast.show(DataManage.getMapList.geocodes.get(0).formatted_address + "");

                isFirstLoc = false;
            }


//            } else {
//
//                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
//                Log.e("AmapError", "location Error, ErrCode:"
//                        + aMapLocation.getErrorCode() + ", errInfo:"
//                        + aMapLocation.getErrorInfo());
//                Toast.makeText(getApplicationContext(), "定位失败", Toast.LENGTH_LONG).show();
//            }
        }
    }

    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
//        //dissmissProgressDialog();// 隐藏对话框
//        if (rCode == 1000) {
//            if (result != null && result.getQuery() != null) {// 搜索poi的结果
//                if (result.getQuery().equals(query)) {// 是否是同一条
//                   PoiResult poiResult = result;
//                    // 取得搜索到的poiitems有多少页
//                    List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
//                    List<SuggestionCity> suggestionCities = poiResult
//                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
//
//                    if (poiItems != null && poiItems.size() > 0) {
//                        aMap.clear();// 清理之前的图标
//                        PoiOverlay poiOverlay = new PoiOverlay(aMap, poiItems);
//                        poiOverlay.removeFromMap();
//                        poiOverlay.addToMap();
//                        poiOverlay.zoomToSpan();
//                    } else if (suggestionCities != null
//                            && suggestionCities.size() > 0) {
//                        showSuggestCity(suggestionCities);
//                    } else {
////                        ToastUtil.show(PoiKeywordSearchActivity.this,
////                                R.string.no_result);
//                        Toast.show("no_result");
//                    }
//                }
//            } else {
////                ToastUtil.show(PoiKeywordSearchActivity.this,
////                        R.string.no_result);
//                Toast.show("no_result");
//            }
//        } else {
//            //ToastUtil.showerror(this, rCode);
//            Toast.show(rCode);
//        }
    }

    @Override
    public void onPoiItemSearched(PoiItem item, int rCode) {
//        if (rCode == 1000) {
//            if (item != null) {
//                PoiItem mPoi = item;
//                detailMarker.setPosition(AMapUtil.convertToLatLng(mPoi.getLatLonPoint()));
//            }
//        } else {
//            ToastUtil.showerror(this, rCode);
//        }
    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery() {
//        String keyWord = mSearchText.getText().toString().trim();
//        int currentPage = 0;
//        PoiSearch.Query query = new PoiSearch.Query(keyWord, "", "南昌市");
//        query.setPageSize(10);// 设置每页最多返回多少条poiitem
//        query.setPageNum(currentPage);// 设置查第一页
//
//        if (lp != null) {
//            PoiSearch poiSearch = new PoiSearch(getContext(), query);
//            poiSearch.setOnPoiSearchListener(this);
//            poiSearch.setBound(new PoiSearch.SearchBound(lp, 5000, true));//
//            poiSearch.searchPOIAsyn();// 异步搜索
//        }
    }
}
