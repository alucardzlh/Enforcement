package com.zhirongkeji.enforcement.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.zhirongkeji.enforcement.Adapters.Mission_Adapter;
import com.zhirongkeji.enforcement.Entitys.DataManage;
import com.zhirongkeji.enforcement.R;


/**
 * Created by 章龙海 on 2016/11/23 15:52.
 *
 * @descript (已完成列表)
 */

public class CompleteList extends Activity {
    @ViewInject(R.id.lv)
    ListView ListV;

    @ViewInject(R.id.returnT)
    ImageView returnT;
    @ViewInject(R.id.title)
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);
        ViewUtils.inject(this);

        initData();
        initView();
    }

    private void initView() {
        title.setText("历史清单");
        ListView ListV= (ListView) findViewById(R.id.lv);
        ListV.setAdapter(new Mission_Adapter(this, DataManage.MissioList));
        //DataManage.MissioList.clear();
        ListV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(CompleteList.this, SubmitActivity.class));
            }
        });
        returnT.setVisibility(View.VISIBLE);
        returnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManage.MissioList.clear();
                finish();
            }
        });
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            DataManage.Mission mission = new DataManage.Mission();
            mission.Title = "江西智容科技有限公司" + i;
            mission.Add = "江西省南昌市高新技术产业开发区高新区高新二路建昌工业园金庐软件园海外大厦北楼306室";
            mission.State = 0;
            DataManage.MissioList.add(mission);
        }
    }
}
