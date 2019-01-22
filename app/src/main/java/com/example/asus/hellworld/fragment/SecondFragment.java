package com.example.asus.hellworld.fragment;


import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.asus.hellworld.FifthActivity;
import com.example.asus.hellworld.FourthActivity;
import com.example.asus.hellworld.HelloWorldActivity;
import com.example.asus.hellworld.SecondActivity;
import com.example.asus.hellworld.SixthActivity;
import com.example.asus.hellworld.WebviewAcitivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.example.asus.hellworld.R;

/**
 *onStart()方法是必要的Activity中的onCreate()方法的函数等都要放在这里 控件的点击事件也要放在这里，因为
 * onCreateView方法在onCreate方法之后执行，否则会报空对象的错误！！！
 */
public class SecondFragment extends Fragment {
    private DrawerLayout mDrawerLayout;
    private ImageView openDrawerLayout;
    private NavigationView mNavigationView;//侧面导航栏
    //图片资源id为int类型
    private int[] imageIds = new int[]{
            R.mipmap.viewpager1,R.mipmap.viewpager2,R.mipmap.viewpager3,R.mipmap.viewpager4
    };
    private ViewPager mViewPager;
    private List<ImageView> images;//把需要滑动的页加载到List中
    private List<View> dots;
    private  MyAdapter adapter;
    private int currentItem;//当前位置
    private int oldPosition=0;//上一次位置
    private ScheduledExecutorService scheduledExecutorService;
    public SecondFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_layout, container, false);
        //绑定id
        mViewPager=view.findViewById(R.id.viewPager);//上方滚动图片
        openDrawerLayout = view.findViewById(R.id.openDrawerLayout);
        mDrawerLayout = view.findViewById(R.id.drawer_layout);
        mNavigationView = view.findViewById(R.id.nav_view);
        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    //自定义动画轮播的Adapter
    private class MyAdapter extends PagerAdapter {
        @Override
        public int getCount(){
            return images.size();
        }
        @Override
        public boolean isViewFromObject(View arg0,Object arg1){
            return arg0 == arg1;//官方提示这样写
        }
        @Override
        public void destroyItem(ViewGroup view, int position, Object object){
            view.removeView(images.get(position));
        }
        @Override
        public Object instantiateItem(ViewGroup view,int position){
            view.addView(images.get(position));
            return images.get(position);
        }
    }
    //利用线程池定时执行动画轮播
    @Override
    public void onStart(){
        super.onStart();
        openDrawerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        //个人中心
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_message:
                        Toast.makeText(getActivity(),"没有消息哦(๑òᆺó๑)",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_user:
                        Toast.makeText(getActivity(),"用户: 杜剑雄\n年龄: 21\n" +
                                "手机号:17505130365\n",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_password:
                        Toast.makeText(getActivity(),"暂时不能修改哦(づ ●─● )づ\n",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_word:
                        Toast.makeText(getActivity(),"功能目前很简单哦，不需要使用文档哒~",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_out:
                        Intent intent_exit = new Intent(getActivity(),HelloWorldActivity.class);
                        intent_exit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//关键，将Activity设置为栈顶
                        startActivity(intent_exit);
                        //System.exit(0);另一种退出方法
                        break;
                }
                return true;
            }
        });
        //上方广告
        images = new ArrayList<>();//创建
        for(int i=0;i<imageIds.length;i++){
            ImageView imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(imageIds[i]);
            images.add(imageView);
        }
        //显示小点
        dots=new ArrayList<>();
        dots.add(getView().findViewById(R.id.dot_0));
        dots.add(getView().findViewById(R.id.dot_1));
        dots.add(getView().findViewById(R.id.dot_2));
        dots.add(getView().findViewById(R.id.dot_3));
        //适配器
        adapter=new MyAdapter();
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageSelected(int position){
                dots.get(position).setBackgroundResource(R.mipmap.page_indicator_focused);
                dots.get(oldPosition).setBackgroundResource(R.mipmap.page_indicator_unfocused);
                oldPosition=position;
                //currentItem=position;
            }
            @Override
            public void onPageScrolled(int arg0,float arg1,int arg2){
            }
            @Override
            public void onPageScrollStateChanged(int arg0){
            }
        });

        scheduledExecutorService= Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(
                new ViewPageTask(),
                4,
                4,
                TimeUnit.SECONDS);
    }

    //图片轮播任务

    private class ViewPageTask implements Runnable{
        @Override
        public  void run(){
            currentItem=(currentItem+1)%imageIds.length;
            mHandler.sendEmptyMessage(0);
        }
    }

    //接收自线程传递过来的数据

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg){
            mViewPager.setCurrentItem(currentItem);
        }
    };
    @Override
    public void onStop(){
        super.onStop();
        if(scheduledExecutorService!=null){
            scheduledExecutorService.shutdown();
            scheduledExecutorService=null;
        }
    }
}
