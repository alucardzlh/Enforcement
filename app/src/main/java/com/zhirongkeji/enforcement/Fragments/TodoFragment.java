package com.zhirongkeji.enforcement.Fragments;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.zhirongkeji.enforcement.Activitys.DeviceListActivity;
import com.zhirongkeji.enforcement.Activitys.MainActivity;
import com.zhirongkeji.enforcement.R;
import com.zhirongkeji.enforcement.Services.BluetoothService;
import com.zhirongkeji.enforcement.Utils.PicFromPrintUtils;

import java.io.UnsupportedEncodingException;

import static android.R.attr.button;

/**
 * Created by zhirongkeji on 2016/11/19.
 * <p>
 * 查询界面
 */

public class TodoFragment extends Fragment implements View.OnClickListener {
    // Message types sent from the BluetoothService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the services
    public static BluetoothService mService = null;
    // Name of the connected device
    public static String mConnectedDeviceName = null;
    Button submit,go,link;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.todo_fragment, container, false);
        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(getContext(), "您的设备不支持蓝牙", Toast.LENGTH_SHORT).show();
            //finish();
            //return;
        }
        submit= (Button) v.findViewById(R.id.submit);
        go= (Button) v.findViewById(R.id.go);
        link= (Button) v.findViewById(R.id.link);
        submit.setOnClickListener(this);
        go.setOnClickListener(this);
        link.setOnClickListener(this);

        return v;
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

    // The Handler that gets information back from the BluetoothService
    public final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
                            //print_connect_btn.setText("已连接:");
                            Toast.makeText(getContext(), "蓝牙已连接" + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                            //print_connect_btn.append(mConnectedDeviceName);
                            break;
                        case BluetoothService.STATE_CONNECTING:
                            //print_connect_btn.setText("正在连接...");
                            link.setText("正在连接...");
                            break;
                        case BluetoothService.STATE_LISTEN:
                        case BluetoothService.STATE_NONE:
                            //print_connect_btn.setText("无连接");
                            link.setText("无连接");
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
                    Toast.makeText(getContext(), "连接至"
                            + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(getContext(), msg.getData().getString(TOAST),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    /**
     * 打印
     * @param message
     */
    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (mService.getState() != BluetoothService.STATE_CONNECTED) {
            Toast.makeText(getContext(), "蓝牙没有连接", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothService to write
            byte[] send;
            try {
                send = message.getBytes("GB2312");
            } catch (UnsupportedEncodingException e) {
                send = message.getBytes();
            }

            mService.write(send);
        }
    }

    private void sendMessage(Bitmap bitmap) {
        // Check that we're actually connected before trying anything
        if (mService.getState() != BluetoothService.STATE_CONNECTED) {
            Toast.makeText(getContext(), "蓝牙没有连接", Toast.LENGTH_SHORT).show();
            return;
        }
        // 发送打印图片前导指令
        byte[] start = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x1B,
                0x40, 0x1B, 0x33, 0x00};
        mService.write(start);

        /**获取打印图片的数据**/
//		byte[] send = getReadBitMapBytes(bitmap);

        mService.printCenter();
        byte[] draw2PxPoint = PicFromPrintUtils.draw2PxPoint(bitmap);

        mService.write(draw2PxPoint);
        // 发送结束指令
        byte[] end = {0x1d, 0x4c, 0x1f, 0x00};
        mService.write(end);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                if(TextUtils.isEmpty(submit.getText())){
                    Toast.makeText(getContext(),"无打印信息",Toast.LENGTH_SHORT).show();
                }else {
                    sendMessage(submit.getText().toString()+"\n"+"\n"+"\n"+"\n");
                }
                break;
            case R.id.go:
                sendMessage("\n");
                break;
            case R.id.link:
                startActivityForResult(new Intent(getActivity(), DeviceListActivity.class),REQUEST_CONNECT_DEVICE);
                break;
            default:
                break;
        }

    }
}
