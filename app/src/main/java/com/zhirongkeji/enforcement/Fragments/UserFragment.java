package com.zhirongkeji.enforcement.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.zhirongkeji.enforcement.Activitys.CompleteList;
import com.zhirongkeji.enforcement.Activitys.TodoList;
import com.zhirongkeji.enforcement.R;

/**
 * Created by zhirongkeji on 2016/11/19.
 *
 * 个人信息
 */

public class UserFragment extends Fragment implements View.OnClickListener {
    RelativeLayout userTodo, userComplete, userMy, userAbout, userIdea;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.user_fragment, container, false);
        userTodo = (RelativeLayout) v.findViewById(R.id.user_todo);
        userComplete = (RelativeLayout) v.findViewById(R.id.user_complete);
        userMy = (RelativeLayout) v.findViewById(R.id.user_my);
        userAbout = (RelativeLayout) v.findViewById(R.id.user_about);
        userIdea = (RelativeLayout) v.findViewById(R.id.user_idea);
        userTodo.setOnClickListener(this);
        userComplete.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_todo:
                startActivity(new Intent(getContext(), TodoList.class));
                break;
            case R.id.user_complete:
                startActivity(new Intent(getContext(), CompleteList.class));
                break;
            case R.id.user_my:
                break;
            case R.id.user_about:
                break;
            case R.id.user_idea:
                break;
        }
    }
}
