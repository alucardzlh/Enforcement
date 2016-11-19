package com.zhirongkeji.enforcement.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhirongkeji.enforcement.R;

/**
 *
 * Created by zhirongkeji on 2016/11/19.
 *
 * 查询界面
 */

public class SearchFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.search_fragment,container,false);
    }
}
