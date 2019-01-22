package com.example.asus.hellworld.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.asus.hellworld.FourthActivity;
import com.example.asus.hellworld.HelloWorldActivity;
import com.example.asus.hellworld.R;

public class FourthFragment extends Fragment {
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private ImageView openDrawerLayout;
    public FourthFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fourth_layout, container, false);
        mNavigationView = view.findViewById(R.id.nav_view);
        mDrawerLayout = view.findViewById(R.id.drawer_layout);
        openDrawerLayout = view.findViewById(R.id.openDrawerLayout);
        return view;
    }
    public void onStart() {
        super.onStart();
        openDrawerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);//禁止侧边划出个人主页
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
                        Intent intent_exit = new Intent(getContext(),HelloWorldActivity.class);
                        intent_exit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//关键，将Activity设置为栈顶
                        startActivity(intent_exit);//回到登录界面
                        //System.exit(0);另一种退出方法
                        break;
                }
                return true;
            }
        });
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //id必须在布局加载后知后绑定
    }
}
