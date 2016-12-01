package com.zhirongkeji.enforcement.Activitys;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.zhirongkeji.enforcement.R;
import com.zhirongkeji.enforcement.Views.Toast;

/**
 * Created by 章龙海 on 2016/11/23 13:32.
 *
 * @descript (提交反馈详情)
 */

public class SubmitActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.returnT)
    ImageView returnT;
    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.submit)
    TextView submit;
    @ViewInject(R.id.entname)
    TextView entname;
    @ViewInject(R.id.entadd)
    TextView entadd;
    @ViewInject(R.id.entno)
    TextView entno;

    @ViewInject(R.id.ps1)
    Button ps1;
    @ViewInject(R.id.ps2)
    Button ps2;
    @ViewInject(R.id.ps3)
    Button ps3;
    @ViewInject(R.id.ps4)
    Button ps4;
    @ViewInject(R.id.ps5)
    Button ps5;
    @ViewInject(R.id.ps6)
    Button ps6;
    @ViewInject(R.id.ps7)
    Button ps7;
    @ViewInject(R.id.ps8)
    Button ps8;
    @ViewInject(R.id.edit_result1)
    Button er1;
    @ViewInject(R.id.edit_result2)
    Button er2;
    @ViewInject(R.id.edit_result3)
    Button er3;
    @ViewInject(R.id.edit_result4)
    Button er4;
    @ViewInject(R.id.rg1)
    RadioGroup rg1;
    @ViewInject(R.id.rg2)
    RadioGroup rg2;
    @ViewInject(R.id.rg3)
    RadioGroup rg3;
    @ViewInject(R.id.rg4)
    RadioGroup rg4;
    @ViewInject(R.id.rg5)
    RadioGroup rg5;
    @ViewInject(R.id.rg6)
    RadioGroup rg6;
    @ViewInject(R.id.rg7)
    RadioGroup rg7;
    @ViewInject(R.id.rg8)
    RadioGroup rg8;
    @ViewInject(R.id.det)
    EditText det;
    @ViewInject(R.id.custom_1)
    EditText custom1;
    @ViewInject(R.id.custom_2)
    EditText custom2;
    @ViewInject(R.id.custom_3)
    EditText custom3;
    @ViewInject(R.id.question1)
    TextView question1;
    static String rgr1, rgr2, rgr3, rgr4, rgr5, rgr6, rgr7, rgr8,psr1="",psr2="",psr3="",psr4="",psr5="",psr6="",psr7="",psr8="",psr9,psr10,psr11,psr12;

    RadioGroup.OnCheckedChangeListener onCheckedChangeListener;
    AlertDialog.Builder builder = null;

    StringBuffer subinfo = new StringBuffer("               (抽)检查2016第0008期\n");

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit_details);
        ViewUtils.inject(this);
        initGroup();
        initView();

    }

    private void initGroup() {

        onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener()

        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.permit_y:
                        rgr1 = "是";
                        break;
                    case R.id.permit_n:
                        rgr1 = "否";
                        break;
                    case R.id.approve_y:
                        rgr2 = "是";
                        break;
                    case R.id.approve_n:
                        rgr2 = "否";
                        break;
                    case R.id.alter_y:
                        rgr3 = "是";
                        break;
                    case R.id.alter_n:
                        rgr3 = "否";
                        break;
                    case R.id.illegal_y:
                        rgr4 = "是";
                        break;
                    case R.id.illegal_n:
                        rgr4 = "否";
                        break;
                    case R.id.valid_y:
                        rgr5 = "是";
                        break;
                    case R.id.valid_n:
                        rgr5 = "否";
                        break;
                    case R.id.safe_y:
                        rgr6 = "是";
                        break;
                    case R.id.safe_n:
                        rgr6 = "否";
                        break;
                    case R.id.danger_y:
                        rgr7 = "是";
                        break;
                    case R.id.danger_n:
                        rgr7 = "否";
                        break;
                    case R.id.norm_y:
                        rgr8 = "是";
                        break;
                    case R.id.norm_n:
                        rgr8 = "否";
                        break;


                }
            }
        };
    }

    private void initView() {

        returnT.setVisibility(View.VISIBLE);
        returnT.setOnClickListener(this);
        submit.setOnClickListener(this);
        title.setText("反馈详情");
        rg1.setOnCheckedChangeListener(onCheckedChangeListener);
        rg2.setOnCheckedChangeListener(onCheckedChangeListener);
        rg3.setOnCheckedChangeListener(onCheckedChangeListener);
        rg4.setOnCheckedChangeListener(onCheckedChangeListener);
        rg5.setOnCheckedChangeListener(onCheckedChangeListener);
        rg6.setOnCheckedChangeListener(onCheckedChangeListener);
        rg7.setOnCheckedChangeListener(onCheckedChangeListener);
        rg8.setOnCheckedChangeListener(onCheckedChangeListener);

        builder = new AlertDialog.Builder(this)
                .setIcon(R.drawable.my_icon2)
                .setTitle("请输入备注")
                .setMessage("测试？")
                .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        psr1=det.getText().toString();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.returnT:
                finish();
                break;
            case R.id.submit:
                subinfo.append("企业名称：" + entname.getText().toString() + "\n");
                subinfo.append("地　　址：" + entadd.getText().toString() + "\n");
                subinfo.append("注 册 号：" + entno.getText().toString() + "\n\n");
                subinfo.append("证照是否齐全：" + "" + "\n");
                subinfo.append("结果：" + rgr1 + "\n");
                subinfo.append("备注：" + psr1 + "\n\n");
                subinfo.append("是否取得相关审批文件：" + "" + "\n");
                subinfo.append("结果：" + rgr2 + "\n");
                subinfo.append("备注：" + psr2 + "\n\n");
                subinfo.append("登记事项是否发生变化：" + "" + "\n");
                subinfo.append("结果：" + rgr3 + "\n");
                subinfo.append("备注：" + psr3 + "\n\n");
                subinfo.append("原违法记录记载的违法行为是否已更正：" + "" + "\n");
                subinfo.append("结果：" + rgr4 + "\n");
                subinfo.append("备注：" + psr4 + "\n\n");
                subinfo.append("相关许可是否有效需要作出哪些整改：" + "" + "\n");
                subinfo.append("结果：" + rgr5 + "\n");
                subinfo.append("备注：" + psr5 + "\n\n");
                subinfo.append("安全规章制度是否完备：" + "" + "\n");
                subinfo.append("结果：" + rgr6 + "\n");
                subinfo.append("备注：" + psr6 + "\n\n");
                subinfo.append("现场发现哪些安全隐患：" + "" + "\n");
                subinfo.append("结果：" + rgr7 + "\n");
                subinfo.append("备注：" + psr7 + "\n\n");
                subinfo.append("操作流程是否规范：" + "" + "\n");
                subinfo.append("结果：" + rgr8 + "\n");
                subinfo.append("备注：" + psr8 + "\n\n");


                subinfo.append(question1.getText() + "" + "\n");
                subinfo.append("结果：" + psr9 + "\n\n");
                if(!TextUtils.isEmpty(custom1.getText())){
                    subinfo.append(custom1.getText() + "" + "\n");
                    subinfo.append("结果：" + psr10 + "\n\n");
                }
                if(!TextUtils.isEmpty(custom2.getText())){
                    subinfo.append(custom2.getText() + "" + "\n");
                    subinfo.append("结果：" + psr10 + "\n\n");
                }
                if(!TextUtils.isEmpty(custom3.getText())){
                    subinfo.append(custom3.getText() + "" + "\n");
                    subinfo.append("结果：" + psr10 + "\n\n");
                }

                subinfo.append("检查人员：" + "XXX" + "\n");
                subinfo.append("抽查时间：" + "2018年8月8日" + "\n\n");
                subinfo.append("当事人签章：____________" + "\n");
                startActivity(new Intent(this, PrintActivity.class).putExtra("info", subinfo.toString()));
                finish();
                break;
            default:
                break;
        }

    }


    @OnClick({R.id.ps1, R.id.ps2, R.id.ps3, R.id.ps4, R.id.ps5, R.id.ps6, R.id.ps7, R.id.ps8, R.id.edit_result1, R.id.edit_result2, R.id.edit_result3, R.id.edit_result4})
    public void onclick(View v) {
        switch (v.getId()) {
            case R.id.ps1:
                det=new EditText(this);
                det.setText(psr1);
                builder.setView(det);
                builder.setMessage("证照是否齐全？");
                builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        psr1=det.getText().toString();
                    }
                });
                builder.show();

                break;
            case R.id.ps2:
                det=new EditText(this);
                det.setText(psr2);
                builder.setView(det);
                builder.setMessage("是否取得相关审批文件？");
                builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        psr2=det.getText().toString();
                    }
                });
                builder.show();
                break;
            case R.id.ps3:
                det=new EditText(this);
                det.setText(psr3);
                builder.setView(det);
                builder.setMessage("登记事项是否发生变化？");
                builder.setPositiveButton("提交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        psr3=det.getText().toString();
                    }
                });
                builder.show();
                break;
            case R.id.ps4:
                det=new EditText(this);
                det.setText(psr4);
                builder.setView(det);
                builder.setMessage("原违法记录记载的违法行为是否已改正？");
                builder.setPositiveButton("提交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        psr4=det.getText().toString();
                    }
                });
                builder.show();
                break;
            case R.id.ps5:
                det=new EditText(this);
                det.setText(psr5);
                builder.setView(det);
                builder.setMessage("相关许可是否有效，需要作出哪些整改？");
                builder.setPositiveButton("提交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        psr5=det.getText().toString();
                    }
                });
                builder.show();
                break;
            case R.id.ps6:
                det=new EditText(this);
                det.setText(psr6);
                builder.setView(det);
                builder.setMessage("安全规章制度是否完备？");
                builder.setPositiveButton("提交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        psr6=det.getText().toString();
                    }
                });
                builder.show();
                break;
            case R.id.ps7:
                det=new EditText(this);
                det.setText(psr7);
                builder.setView(det);
                builder.setMessage("现场发现哪些安全隐患？");
                builder.setPositiveButton("提交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        psr7=det.getText().toString();
                    }
                });
                builder.show();
                break;
            case R.id.ps8:
                det=new EditText(this);
                det.setText(psr8);
                builder.setView(det);
                builder.setMessage("操作规程是否规范？");
                builder.setPositiveButton("提交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        psr8=det.getText().toString();
                    }
                });
                builder.show();
                break;
            case R.id.edit_result1:
                det=new EditText(this);
                det.setText(psr9);
                builder.setView(det);
                builder.setMessage(question1.getText());
                builder.setPositiveButton("提交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        psr9=det.getText().toString();
                    }
                });
                builder.show();
                break;
            case R.id.edit_result2:
                det=new EditText(this);
                det.setText(psr10);
                builder.setView(det);
                if(TextUtils.isEmpty(custom1.getText())){
                    Toast.show("请输入自定义标题");
                    break;
                }else {
                    builder.setMessage(custom1.getText());
                }
                builder.setPositiveButton("提交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        psr10=det.getText().toString();
                    }
                });
                builder.show();
                break;
            case R.id.edit_result3:
                det=new EditText(this);
                det.setText(psr11);
                builder.setView(det);
                if(TextUtils.isEmpty(custom2.getText())){
                    Toast.show("请输入自定义标题");
                    break;
                }else {
                    builder.setMessage(custom2.getText());
                }
                builder.setPositiveButton("提交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        psr11=det.getText().toString();
                    }
                });
                builder.show();
                break;
            case R.id.edit_result4:
                det=new EditText(this);
                det.setText(psr12);
                builder.setView(det);
                if(TextUtils.isEmpty(custom3.getText())){
                    Toast.show("请输入自定义标题");
                    break;
                }else {
                    builder.setMessage(custom3.getText());
                }
                builder.setPositiveButton("提交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        psr12=det.getText().toString();
                    }
                });
                builder.show();
                break;

        }
    }


}
