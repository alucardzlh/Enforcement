package com.zhirongkeji.enforcement.Activitys;

import android.bluetooth.BluetoothAdapter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.zhirongkeji.enforcement.Fragments.MapFragment;
import com.zhirongkeji.enforcement.Fragments.SearchFragment;
import com.zhirongkeji.enforcement.Fragments.TodoFragment;
import com.zhirongkeji.enforcement.Fragments.UserFragment;
import com.zhirongkeji.enforcement.R;
import com.zhirongkeji.enforcement.Services.BluetoothService;

import java.util.ArrayList;
import java.util.List;

import static com.zhirongkeji.enforcement.Fragments.TodoFragment.DEVICE_NAME;
import static com.zhirongkeji.enforcement.Fragments.TodoFragment.MESSAGE_DEVICE_NAME;
import static com.zhirongkeji.enforcement.Fragments.TodoFragment.MESSAGE_READ;
import static com.zhirongkeji.enforcement.Fragments.TodoFragment.MESSAGE_STATE_CHANGE;
import static com.zhirongkeji.enforcement.Fragments.TodoFragment.MESSAGE_TOAST;
import static com.zhirongkeji.enforcement.Fragments.TodoFragment.MESSAGE_WRITE;
import static com.zhirongkeji.enforcement.Fragments.TodoFragment.TOAST;
import static com.zhirongkeji.enforcement.Fragments.TodoFragment.mConnectedDeviceName;

/**
 *
 */
public class MainActivity extends FragmentActivity {


    @ViewInject(R.id.search)
    private RadioButton search_button;
    @ViewInject(R.id.todo)
    private RadioButton todo_button;
    @ViewInject(R.id.map)
    private RadioButton map_button;
    @ViewInject(R.id.user)
    private RadioButton user_button;
    @ViewInject(R.id.groups)
    RadioGroup rg;
    @ViewInject(R.id.content)
    private ViewPager mViewPager;
    private List<Fragment> Fragments;
    private FragmentPagerAdapter FPadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        mViewPager.setCurrentItem(0);
        initView();
        initControl();
    }

    private void initControl() {

    }

    private void initView() {
        Fragments = new ArrayList<Fragment>();
        Fragment searchFragment = new SearchFragment();
        Fragment todoFragment = new TodoFragment();
        Fragment mapFragment = new MapFragment();
        Fragment userFragment = new UserFragment();
        Fragments.add(searchFragment);
        Fragments.add(todoFragment);
        Fragments.add(mapFragment);
        Fragments.add(userFragment);
        FPadapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return Fragments.get(position);
            }

            @Override
            public int getCount() {
                return Fragments.size();
            }
        };
        mViewPager.setAdapter(FPadapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        search_button.setChecked(true);
                        search_button.setTextColor(getResources().getColor(R.color.bottom_checked));
                        todo_button.setTextColor(getResources().getColor(R.color.bottom));
                        map_button.setTextColor(getResources().getColor(R.color.bottom));
                        user_button.setTextColor(getResources().getColor(R.color.bottom));
                        break;
                    case 1:
                        todo_button.setChecked(true);
                        search_button.setTextColor(getResources().getColor(R.color.bottom));
                        todo_button.setTextColor(getResources().getColor(R.color.bottom_checked));
                        map_button.setTextColor(getResources().getColor(R.color.bottom));
                        user_button.setTextColor(getResources().getColor(R.color.bottom));
                        break;
                    case 2:
                        map_button.setChecked(true);
                        search_button.setTextColor(getResources().getColor(R.color.bottom));
                        todo_button.setTextColor(getResources().getColor(R.color.bottom));
                        map_button.setTextColor(getResources().getColor(R.color.bottom_checked));
                        user_button.setTextColor(getResources().getColor(R.color.bottom));
                        break;
                    case 3:
                        user_button.setChecked(true);
                        search_button.setTextColor(getResources().getColor(R.color.bottom));
                        todo_button.setTextColor(getResources().getColor(R.color.bottom));
                        map_button.setTextColor(getResources().getColor(R.color.bottom));
                        user_button.setTextColor(getResources().getColor(R.color.bottom_checked));

                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @OnClick({R.id.search, R.id.todo, R.id.map, R.id.user})
    public void onclik(View v) {
        switch (v.getId()) {
            case R.id.search:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.todo:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.map:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.user:
                mViewPager.setCurrentItem(3);
                break;
        }
    }


}
