package com.zhirongkeji.enforcement.Fragments;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
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
import android.widget.EditText;
import android.widget.Toast;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.zhirongkeji.enforcement.Activitys.CompleteList;
import com.zhirongkeji.enforcement.Activitys.DeviceListActivity;
import com.zhirongkeji.enforcement.Activitys.MainActivity;
import com.zhirongkeji.enforcement.Activitys.SubmitActivity;
import com.zhirongkeji.enforcement.Activitys.TodoList;
import com.zhirongkeji.enforcement.R;
import com.zhirongkeji.enforcement.Services.BluetoothService;
import com.zhirongkeji.enforcement.Utils.PicFromPrintUtils;

import java.io.UnsupportedEncodingException;

import static android.R.attr.breadCrumbShortTitle;
import static android.R.attr.button;
import static com.zhirongkeji.enforcement.Activitys.MainActivity.REQUEST_CONNECT_DEVICE;
import static com.zhirongkeji.enforcement.Activitys.MainActivity.REQUEST_ENABLE_BT;
import static com.zhirongkeji.enforcement.Activitys.MainActivity.mBluetoothAdapter;

/**
 * Created by zhirongkeji on 2016/11/19.
 * <p>
 * 反馈界面
 */

public class TodoFragment extends Fragment implements View.OnClickListener {




    // Member object for the services
    public static BluetoothService mService = null;
    // Name of the connected device
    public static String mConnectedDeviceName = null;
    public static Button submit, go, link,dbrw,wcrw;
    private EditText info;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.todo_fragment, container, false);
        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(getContext(), "您的设备不支持蓝牙", Toast.LENGTH_SHORT).show();
            //finish();
            //return;
        }
        submit = (Button) v.findViewById(R.id.submit);
        go = (Button) v.findViewById(R.id.go);
        link = (Button) v.findViewById(R.id.link);
        info= (EditText) v.findViewById(R.id.info);
        submit.setOnClickListener(this);
        go.setOnClickListener(this);
        link.setOnClickListener(this);
        v.findViewById(R.id.todomission).setOnClickListener(this);
        v.findViewById(R.id.complete).setOnClickListener(this);

        return v;
    }


    /**
     * 打印
     *
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
                if (TextUtils.isEmpty(submit.getText())) {
                    Toast.makeText(getContext(), "无打印信息", Toast.LENGTH_SHORT).show();
                } else {
                    sendMessage(info.getText().toString() + "\n" + "\n" + "\n" + "\n");
                }
                break;
            case R.id.go:
                sendMessage("\n");
                break;
            case R.id.link:
                startActivityForResult(new Intent(getActivity(), DeviceListActivity.class), REQUEST_CONNECT_DEVICE);
                break;
            case R.id.todomission:
                startActivity(new Intent(getContext(), TodoList.class));
                break;
            case R.id.complete:
                startActivity(new Intent(getContext(), CompleteList.class));
                break;
            default:
                break;
        }

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    // Get the device MAC address
                    String address = data.getExtras()
                            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    // Get the BLuetoothDevice object
                    BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                    // Attempt to connect to the device
                    mService.connect(device);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
//                setupChat();
                    Toast.makeText(getContext(), "蓝牙已打开", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "蓝牙没有打开", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
