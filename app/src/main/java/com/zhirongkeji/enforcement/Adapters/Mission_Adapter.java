package com.zhirongkeji.enforcement.Adapters;

import android.content.Context;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhirongkeji.enforcement.Entitys.DataManage;
import com.zhirongkeji.enforcement.R;


import java.util.List;

/**
 * Created by 章龙海 on 2016/11/23 15:59.
 *
 * @descript (任务适配器)
 */

public class Mission_Adapter extends BaseAdapter {
    private Context context;
    private List<DataManage.Mission> Datalist;

    public Mission_Adapter(Context context, List<DataManage.Mission> Datalist) {
        this.context = context;
        this.Datalist = Datalist;
    }

    @Override
    public int getCount() {
        return Datalist.size();
    }

    @Override
    public Object getItem(int position) {
        return Datalist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder Vh = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.mission_item, null);
            Vh = new ViewHolder();
            Vh.title = (TextView) convertView.findViewById(R.id.title);
            Vh.Add = (TextView) convertView.findViewById(R.id.add);
            Vh.state = (TextView) convertView.findViewById(R.id.state);
            convertView.setTag(Vh);
        } else {
            Vh = (ViewHolder) convertView.getTag();
        }
        Vh.title.setText(Datalist.get(position).Title);
        Vh.Add.setText(Datalist.get(position).Add);
        switch (Datalist.get(position).State) {
            case 0:Vh.state.setText("待检查");
                break;
            case 1:Vh.state.setText("已完成");
                Vh.state.setTextColor(context.getResources().getColor(R.color.green));
                break;
        }


        return convertView;
    }

    class ViewHolder {
        TextView title;
        TextView Add;
        TextView state;
    }
}
