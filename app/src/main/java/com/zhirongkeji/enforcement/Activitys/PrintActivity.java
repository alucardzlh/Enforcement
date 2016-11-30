package com.zhirongkeji.enforcement.Activitys;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.zhirongkeji.enforcement.R;
import com.zhirongkeji.enforcement.Services.BluetoothService;
import com.zhirongkeji.enforcement.Utils.PicFromPrintUtils;
import com.zhirongkeji.enforcement.Views.Toast;

import java.io.UnsupportedEncodingException;
import java.util.Set;

import static com.zhirongkeji.enforcement.Fragments.TodoFragment.mService;


/**
 * Created by 章龙海 on 2016/11/25 15:08.
 *
 * @descript (打印界面)
 */

public class PrintActivity extends Activity implements View.OnClickListener {
    // Member object for the services
    public static BluetoothService mService = null;
    // Name of the connected device
    public static String mConnectedDeviceName = null;
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
    private static final String 打印机 = "ULT1131B";

    @ViewInject(R.id.returnT)
    ImageView returnT;
    @ViewInject(R.id.go)
    Button go;
    @ViewInject(R.id.print)
    Button print;
    @ViewInject(R.id.link)
    Button link;
    @ViewInject(R.id.info)
    TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);
        ViewUtils.inject(this);
        go.setOnClickListener(this);
        print.setOnClickListener(this);
        link.setOnClickListener(this);

        initBluetooth();
        initView();

    }

    private void initView() {
        returnT.setVisibility(View.VISIBLE);
        returnT.setOnClickListener(this);
        //info.setText("");//"┌──────────────────────┐\n"+"\n└──────────────────────┘"
        info.setText(getIntent().getStringExtra("info"));
    }

    private void initBluetooth() {
        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            android.widget.Toast.makeText(this, "您的设备不支持蓝牙", android.widget.Toast.LENGTH_SHORT).show();
            //finish();
            return;
        }
    }


    /**
     * 打印
     *
     * @param message
     */
    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (mService.getState() != BluetoothService.STATE_CONNECTED) {
            Toast.show("蓝牙没有链接");
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
            Toast.show("蓝牙没有链接");
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
            case R.id.go:
                sendMessage("\n");
                break;
            case R.id.print:
                if (TextUtils.isEmpty(info.getText())) {
                    android.widget.Toast.makeText(this, "无打印信息", android.widget.Toast.LENGTH_SHORT).show();
                } else {
                    sendMessage(info.getText().toString() + "\n" + "\n" );
                }
                break;
            case R.id.link:
                startActivityForResult(new Intent(this, DeviceListActivity.class), REQUEST_CONNECT_DEVICE);
                break;
            case R.id.returnT:
                finish();
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mService == null) {
            mService = new BluetoothService(this, mHandler);
        }
        if (!mBluetoothAdapter.isEnabled()) {
            //打开蓝牙
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
        else {
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            for (BluetoothDevice device : pairedDevices) {
                if(device.getName().equals(打印机)){
                    //setResult(Activity.RESULT_OK,new Intent().putExtra(EXTRA_DEVICE_ADDRESS,device.getAddress()));

                    mService.connect(mBluetoothAdapter.getRemoteDevice(device.getAddress()));
                }
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                    android.widget.Toast.makeText(this, "蓝牙已打开", android.widget.Toast.LENGTH_SHORT).show();
                } else {
                    android.widget.Toast.makeText(this, "蓝牙没有打开", android.widget.Toast.LENGTH_SHORT).show();
                    finish();
                }
        }


        super.onActivityResult(requestCode, resultCode, data);
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
                            android.widget.Toast.makeText(PrintActivity.this, "蓝牙已连接" + mConnectedDeviceName, android.widget.Toast.LENGTH_SHORT).show();
                            //print_connect_btn.append(mConnectedDeviceName);
                            link.setText("已连接");
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
                    android.widget.Toast.makeText(PrintActivity.this, "连接至"
                            + mConnectedDeviceName, android.widget.Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_TOAST:
                    android.widget.Toast.makeText(PrintActivity.this, msg.getData().getString(TOAST),
                            android.widget.Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

}
