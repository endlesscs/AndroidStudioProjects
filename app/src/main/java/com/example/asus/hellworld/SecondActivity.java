package com.example.asus.hellworld;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.asus.hellworld.adapter.BottomnavigationViewPagerAdapter;
import com.example.asus.hellworld.fragment.FifthFragment;
import com.example.asus.hellworld.fragment.FourthFragment;
import com.example.asus.hellworld.fragment.SecondFragment;
import com.example.asus.hellworld.fragment.SixthFragment;

import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.Unbinder;
public class SecondActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        ViewPager.OnPageChangeListener,ViewPager.OnTouchListener{
    private static int m = 1;
    private long clickTime = 0;
    //Fragment类
    SecondFragment secondFragment;
    FourthFragment fourthFragment;
    FifthFragment fifthFragment;
    SixthFragment sixthFragment;
    Unbinder unbinder;
    BottomnavigationViewPagerAdapter pagerAdapter;
    List<Fragment> fragments;
    ViewPager viewPager;
    public BottomNavigationView bottomNavigationView;//下面导航栏
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null) {
            actionBar.hide();
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION); //透明导航栏
        viewPager = findViewById(R.id.viewpager2);
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setItemTextColor(null);
        unbinder = ButterKnife.bind(this);
        initUI();
    }
    private void initUI() {
        fragments = new ArrayList<>();
        secondFragment = new SecondFragment();
        fourthFragment = new FourthFragment();
        fifthFragment = new FifthFragment();
        sixthFragment = new SixthFragment();
        if(!fragments.contains(secondFragment)){
            fragments.add(secondFragment);
        }
        if(!fragments.contains(fourthFragment)){
            fragments.add(fourthFragment);
        }
        if(!fragments.contains(fifthFragment)){
            fragments.add(fifthFragment);
        }
        if(!fragments.contains(sixthFragment)){
            fragments.add(sixthFragment);
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(this);//事件监听
        bottomNavigationView.setSelectedItemId(R.id.Home);

        pagerAdapter = new BottomnavigationViewPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        unbinder.unbind();
    }
    //NavigationItemSelected 事件监听
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Home:
                viewPager.setCurrentItem(0);
                return false;
            case  R.id.Message:
                viewPager.setCurrentItem(1);
               return false;
            case  R.id.Fruit:
                viewPager.setCurrentItem(2);
                return false;
            case R.id.News:
                m = 2;
                viewPager.setCurrentItem(3);
                return false;
        }
        return true;
    }
    @Override
    public void onStart(){
        super.onStart();
        if(m==2) {
            viewPager.setCurrentItem(3);
        }
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    @Override
    public void onPageSelected(int position) {
        bottomNavigationView.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }
}
/*
public class SecondActivity extends AppCompatActivity {
    private static int m = 1;
    private long clickTime = 0;
    //Fragment类
    SecondFragment secondFragment;
    FourthFragment fourthFragment;
    FifthFragment fifthFragment;
    SixthFragment sixthFragment;
    //Fragment管理
    private FragmentManager fragmentManager;
    public BottomNavigationView bottomNavigationView;//下面导航栏

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null) {
            actionBar.hide();
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION); //透明导航栏
        //绑定id
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setItemTextColor(null);
        bottomNavigationView.setSelectedItemId(bottomNavigationView.getMenu().getItem(0).getItemId());
        initUI();
    }
    private void initUI() {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        secondFragment = new SecondFragment();
        transaction.addToBackStack(null);
        transaction.add(R.id.main_fragment_container, secondFragment).show(secondFragment).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);//事件监听
        //BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragmentManager = getSupportFragmentManager();//通过getSupportFragmentManager()获取fragmentManager
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            hideAllFragment(transaction);
            switch (item.getItemId()) {
                case R.id.Home:
                    secondFragment = new SecondFragment();
                    transaction.add(R.id.main_fragment_container, secondFragment).show(secondFragment).commit();
                    return true;
                case R.id.Message:
                    fourthFragment = new FourthFragment();
                    transaction.add(R.id.main_fragment_container, fourthFragment).show(fourthFragment).commit();
                    return true;
                case R.id.Fruit:
                    fifthFragment = new FifthFragment();
                    transaction.add(R.id.main_fragment_container,fifthFragment).show(fifthFragment).commit();
                    return true;
                case R.id.News:
                    m = 2;
                    sixthFragment = new SixthFragment();
                    transaction.add(R.id.main_fragment_container,sixthFragment).show(sixthFragment).commit();
                    return true;
            }
            return false;
        }
    };
    public void hideAllFragment(FragmentTransaction transaction){
        if(secondFragment!=null){
            transaction.hide(secondFragment);
        }
        if(fourthFragment!=null){
            transaction.hide(fourthFragment);
        }
        if(fifthFragment!=null){
            transaction.hide(fifthFragment);
        }
        if(sixthFragment!=null){
            transaction.hide(sixthFragment);
        }
    }
    @Override
    public void onStart(){
        super.onStart();
        if(m==2) {
            fragmentManager = getSupportFragmentManager();//通过getSupportFragmentManager()获取fragmentManager
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            bottomNavigationView.setSelectedItemId(bottomNavigationView.getMenu().getItem(3).getItemId());
            transaction.show(sixthFragment).commit();
        }
    }
}*/
/*
public class SecondActivity extends AppCompatActivity {
    private long clickTime = 0;
    private DrawerLayout mDrawerLayout;
    private Button openDrawerLayout;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null) {
            actionBar.hide();
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //绑定id
        mViewPager=findViewById(R.id.viewPager);//上方滚动图片
        openDrawerLayout = findViewById(R.id.openDrawerLayout);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        openDrawerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        //上方广告
        images = new ArrayList<>();//创建
        for(int i=0;i<imageIds.length;i++){
            ImageView imageView = new ImageView(SecondActivity.this);
            imageView.setBackgroundResource(imageIds[i]);
            images.add(imageView);
        }
        //显示小点
        dots=new ArrayList<>();
        dots.add(findViewById(R.id.dot_0));
        dots.add(findViewById(R.id.dot_1));
        dots.add(findViewById(R.id.dot_2));
        dots.add(findViewById(R.id.dot_3));
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

        //个人中心
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_message:
                        Toast.makeText(SecondActivity.this,"没有消息哦(๑òᆺó๑)",Toast.LENGTH_SHORT).show();
                        break;
                        case R.id.nav_user:
                            Toast.makeText(SecondActivity.this,"用户: 杜剑雄\n年龄: 21\n" +
                                    "手机号:17505130365\n",Toast.LENGTH_LONG).show();
                            break;
                            case R.id.nav_password:
                                Toast.makeText(SecondActivity.this,"暂时不能修改哦(づ ●─● )づ\n",Toast.LENGTH_SHORT).show();
                                break;
                                case R.id.nav_word:
                                    Toast.makeText(SecondActivity.this,"功能目前很简单哦，不需要使用文档哒~",Toast.LENGTH_SHORT).show();
                                    break;
                                    case R.id.nav_out:
                                        Intent intent_exit = new Intent(SecondActivity.this,HelloWorldActivity.class);
                                        intent_exit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//关键，将Activity设置为栈顶
                                        startActivity(intent_exit);
                                        finish();//回到登录界面
                                        //System.exit(0);另一种退出方法
                                        break;
                }
                return true;
            }
        });
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
    @Override
    public boolean onKeyDown ( int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (SystemClock.uptimeMillis() - clickTime <= 1500) {
                Intent startMain = new Intent(Intent.ACTION_MAIN);//显示主界面
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
                System.exit(0);
            } else {
                //当前系统时间的毫秒值
                clickTime = SystemClock.uptimeMillis();
                Toast.makeText(SecondActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}*/