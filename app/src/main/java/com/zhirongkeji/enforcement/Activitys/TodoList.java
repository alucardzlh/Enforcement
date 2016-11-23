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
 * Created by 章龙海 on 2016/11/23 15:55.
 *
 * @descript (待办列表)
 */

public class TodoList extends Activity {
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

    private void initData() {
        for (int i = 0; i < 10; i++) {
            DataManage.Mission mission = new DataManage.Mission();
            mission.Title = "江西瑞臻科技有限公司" + i;
            mission.Add = "江西省南昌市红谷滩新区绿地外滩公馆19栋25楼";
            mission.State = 1;
            DataManage.MissioList.add(mission);
        }
    }

    private void initView() {
        title.setText("待查清单");
        ListView ListV = (ListView) findViewById(R.id.lv);
        ListV.setAdapter(new Mission_Adapter(this, DataManage.MissioList));
        //DataManage.MissioList.clear();
        ListV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(TodoList.this, SubmitActivity.class));
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
}
