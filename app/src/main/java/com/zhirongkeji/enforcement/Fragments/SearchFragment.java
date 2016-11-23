package com.zhirongkeji.enforcement.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhirongkeji.enforcement.Activitys.EntDetailsActivity;
import com.zhirongkeji.enforcement.Activitys.MainActivity;
import com.zhirongkeji.enforcement.R;

/**
 * Created by zhirongkeji on 2016/11/19.
 * <p>
 * 查询界面
 */

public class SearchFragment extends Fragment implements View.OnClickListener {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_fragment, container, false);
        v.findViewById(R.id.text_search).setOnClickListener(this);
        v.findViewById(R.id.ewm_search).setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_search:
                startActivity(new Intent(getContext(), EntDetailsActivity.class));
                break;
            case R.id.ewm_search:
                startActivity(new Intent(getContext(), EntDetailsActivity.class));
                break;

        }
    }
}
