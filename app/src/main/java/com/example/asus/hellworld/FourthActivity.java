package com.example.asus.hellworld;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FourthActivity extends AppCompatActivity {
    private long clickTime = 0;
    private NavigationView  mNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fourth_layout);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null) {
            actionBar.hide();
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        //id必须在布局加载后知后绑定
        mNavigationView = findViewById(R.id.nav_view);

        //个人中心
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_message:
                        Toast.makeText(FourthActivity.this,"没有消息哦(๑òᆺó๑)",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_user:
                        Toast.makeText(FourthActivity.this,"用户: 杜剑雄\n年龄: 21\n" +
                                "手机号:17505130365\n",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_password:
                        Toast.makeText(FourthActivity.this,"暂时不能修改哦(づ ●─● )づ\n",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_word:
                        Toast.makeText(FourthActivity.this,"功能目前很简单哦，不需要使用文档哒~",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_out:
                        Intent intent_exit = new Intent(FourthActivity.this,HelloWorldActivity.class);
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

    @Override
    public boolean onKeyDown ( int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (SystemClock.uptimeMillis() - clickTime <= 1500) {
                //显示主界面
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
                System.exit(0);
            } else {
                //当前系统时间的毫秒值
                clickTime = SystemClock.uptimeMillis();
                Toast.makeText(FourthActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}

