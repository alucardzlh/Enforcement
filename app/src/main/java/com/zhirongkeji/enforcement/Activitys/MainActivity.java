package com.zhirongkeji.enforcement.Activitys;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.zhirongkeji.enforcement.Fragments.MapFragment;
import com.zhirongkeji.enforcement.Fragments.SearchFragment;
import com.zhirongkeji.enforcement.Fragments.TodoFragment;
import com.zhirongkeji.enforcement.Fragments.UserFragment;
import com.zhirongkeji.enforcement.R;
import com.zhirongkeji.enforcement.Services.BluetoothService;
import com.zhirongkeji.enforcement.Views.MyViewPager;

import java.util.ArrayList;
import java.util.List;

import static com.zhirongkeji.enforcement.Fragments.TodoFragment.mConnectedDeviceName;
import static com.zhirongkeji.enforcement.Fragments.TodoFragment.mService;

/**
 *
 */
public class MainActivity extends FragmentActivity {


    @ViewInject(R.id.search)
    private RadioButton search_button;
    @ViewInject(R.id.todo)
    private RadioButton todo_button;
    @ViewInject(R.id.map)
    private RadioButton map_button;
    @ViewInject(R.id.user)
    private RadioButton user_button;
    @ViewInject(R.id.groups)
    RadioGroup rg;
    @ViewInject(R.id.content)
    private MyViewPager mViewPager;
    @ViewInject(R.id.ewm)
    private ImageView ewm;
    private List<Fragment> Fragments;
    private FragmentPagerAdapter FPadapter;
    // Intent request codes
    public static final int REQUEST_CONNECT_DEVICE = 1;
    public static final int REQUEST_ENABLE_BT = 2;
    // Local Bluetooth adapter
    public static BluetoothAdapter mBluetoothAdapter = null;
    // Message types sent from the BluetoothService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    // Key names received from the BluetoothService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);


        initView();
        initControl();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!mBluetoothAdapter.isEnabled()) {
            //打开蓝牙
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
        if (mService == null) {
            mService = new BluetoothService(this, mHandler);
        }
    }

    private void initControl() {
        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "您的设备不支持蓝牙", Toast.LENGTH_SHORT).show();
        //finish();
            return;
        }
    }

    private void initView() {
        mViewPager.setCurrentItem(0);
        //mViewPager.setScanScroll(false);//禁止横滑；
        Fragments = new ArrayList<Fragment>();
        Fragment searchFragment = new SearchFragment();
        Fragment todoFragment = new TodoFragment();
        Fragment mapFragment = new MapFragment();
        Fragment userFragment = new UserFragment();
        Fragments.add(searchFragment);
        Fragments.add(todoFragment);
        Fragments.add(mapFragment);
        Fragments.add(userFragment);
        FPadapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return Fragments.get(position);
            }

            @Override
            public int getCount() {
                return Fragments.size();
            }
        };
        mViewPager.setAdapter(FPadapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        search_button.setChecked(true);
                        search_button.setTextColor(getResources().getColor(R.color.bottom_checked));
                        todo_button.setTextColor(getResources().getColor(R.color.bottom));
                        map_button.setTextColor(getResources().getColor(R.color.bottom));
                        user_button.setTextColor(getResources().getColor(R.color.bottom));

                        break;
                    case 1:
                        todo_button.setChecked(true);
                        search_button.setTextColor(getResources().getColor(R.color.bottom));
                        todo_button.setTextColor(getResources().getColor(R.color.bottom_checked));
                        map_button.setTextColor(getResources().getColor(R.color.bottom));
                        user_button.setTextColor(getResources().getColor(R.color.bottom));

                        break;
                    case 2:
                        map_button.setChecked(true);
                        search_button.setTextColor(getResources().getColor(R.color.bottom));
                        todo_button.setTextColor(getResources().getColor(R.color.bottom));
                        map_button.setTextColor(getResources().getColor(R.color.bottom_checked));
                        user_button.setTextColor(getResources().getColor(R.color.bottom));

                        break;
                    case 3:
                        user_button.setChecked(true);
                        search_button.setTextColor(getResources().getColor(R.color.bottom));
                        todo_button.setTextColor(getResources().getColor(R.color.bottom));
                        map_button.setTextColor(getResources().getColor(R.color.bottom));
                        user_button.setTextColor(getResources().getColor(R.color.bottom_checked));

                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @OnClick({R.id.search, R.id.todo, R.id.map, R.id.user,R.id.ewm})
    public void onclik(View v) {
        switch (v.getId()) {
            case R.id.search:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.todo:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.map:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.user:
                mViewPager.setCurrentItem(3);
                break;
            case R.id.ewm:
                startActivityForResult(new Intent(this, DeviceListActivity.class), REQUEST_CONNECT_DEVICE);
                break;
        }
    }

    // The Handler that gets information back from the BluetoothService
    public final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
                            //print_connect_btn.setText("已连接:");
                            Toast.makeText(MainActivity.this, "蓝牙已连接" + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                            //print_connect_btn.append(mConnectedDeviceName);
                            TodoFragment.link.setText("已连接");
                            break;
                        case BluetoothService.STATE_CONNECTING:
                            //print_connect_btn.setText("正在连接...");
                            TodoFragment.link.setText("正在连接...");
                            break;
                        case BluetoothService.STATE_LISTEN:
                        case BluetoothService.STATE_NONE:
                            //print_connect_btn.setText("无连接");
                            TodoFragment.link.setText("无连接");
                            break;
                    }
                    break;
                case MESSAGE_WRITE:
                    //byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    //String writeMessage = new String(writeBuf);
                    break;
                case MESSAGE_READ:
                    //byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    //String readMessage = new String(readBuf, 0, msg.arg1);
                    break;
                case MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    Toast.makeText(MainActivity.this, "连接至"
                            + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(MainActivity.this, msg.getData().getString(TOAST),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

}
