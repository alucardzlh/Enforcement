package com.zhirongkeji.enforcement.Activitys;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.zhirongkeji.enforcement.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit_details);
        ViewUtils.inject(this);
        initView();
    }

    private void initView() {
        returnT.setVisibility(View.VISIBLE);
        returnT.setOnClickListener(this);
        title.setText("反馈详情");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.returnT:
                finish();
                break;
            default:
                break;
        }

    }
}
