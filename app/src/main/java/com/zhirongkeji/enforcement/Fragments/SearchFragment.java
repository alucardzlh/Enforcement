package com.zhirongkeji.enforcement.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhirongkeji.enforcement.Activitys.EntDetailsActivity;
import com.zhirongkeji.enforcement.Activitys.MainActivity;
import com.zhirongkeji.enforcement.QR_Code.QRCode_app.CaptureActivity;
import com.zhirongkeji.enforcement.R;

/**
 * Created by zhirongkeji on 2016/11/19.
 * <p>
 * 查询界面
 */

public class SearchFragment extends Fragment implements View.OnClickListener {
    EditText edit;
    ImageView clean;
    ImageView qrcode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_fragment, container, false);
        v.findViewById(R.id.text_search).setOnClickListener(this);
        v.findViewById(R.id.ewm_search).setOnClickListener(this);
        qrcode= (ImageView) v.findViewById(R.id.qrcode);
        clean = (ImageView) v.findViewById(R.id.search_et_cc);
        edit = ((EditText) v.findViewById(R.id.search_text));
        qrcode.setOnClickListener(this);
        clean.setOnClickListener(this);
        edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {

                    startActivity(new Intent(getContext(), EntDetailsActivity.class));
                    return true;
                }

                return false;
            }
        });


        edit.addTextChangedListener(new TextWatcher() {//动态判断输入框中的字数并显示隐藏图标
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start > 0) {
                    clean.setVisibility(View.VISIBLE);
                } else {
                    clean.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable t) {
                if (t.length() > 0) {
                    clean.setVisibility(View.VISIBLE);
                } else {
                    clean.setVisibility(View.GONE);
                }
            }
        });

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_search:

                break;
            case R.id.ewm_search:
                startActivity(new Intent(getContext(), EntDetailsActivity.class));
                break;
            case R.id.search_et_cc:
                edit.setText("");
                break;
            case R.id.qrcode:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        //申请WRITE_EXTERNAL_STORAGE权限
                        ActivityCompat.requestPermissions(getActivity(), new
                                        String[]{Manifest.permission.CAMERA},
                                1);
                    } else {
                        startActivityForResult(new Intent(getContext(), CaptureActivity.class), 0);
                    }
                    return;

                } else {
                    startActivityForResult(new Intent(getContext(), CaptureActivity.class), 0);
                }
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {// 从二维码照相机回主页
            com.zhirongkeji.enforcement.Views.Toast.show("扫码成功");
            startActivity(new Intent(getContext(),EntDetailsActivity.class));
//            if (resultCode == RESULT_OK) {
//
//                Bundle bundle = intent.getExtras();
//                // 显示扫描到的内容
//                String str = bundle.getString("SCAN_RESULT");
//                if (str.indexOf("KeyNo") != -1) {
//                    TestShow(str);
//                } else {
//                    com.example.credit.Utils.Toast.show("此二维码不是公司二维码");
//                }
//
//            }
//            if (resultCode == 300) {
//                Bundle bundle = intent.getExtras();
//                String str = bundle.getString("result");
//                if (str.indexOf("KeyNo") != -1) {
//                    TestShow(str);
//                } else {
//                    com.example.credit.Utils.Toast.show("此二维码不是公司二维码");
//                }
//            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }
}
