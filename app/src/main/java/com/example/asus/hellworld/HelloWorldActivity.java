package com.example.asus.hellworld;

import android.app.ActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.content.SharedPreferences;
import android.content.Context;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
public class HelloWorldActivity extends AppCompatActivity {
    private EditText acountEdit;
    private EditText passwordEdit;
    private Button login;
    private String account;
    private String password;
    private long clickTime = 0;
    private CheckBox rememberPass;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_world);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null) {
            actionBar.hide();
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//下方导航栏


        //绑定控件id
        acountEdit = findViewById(R.id.edusername);
        passwordEdit = findViewById(R.id.edpassword);
        login =findViewById(R.id.button2);    //返回的是一个View对象，将它转型成Button对象
        rememberPass=findViewById(R.id.remember_pass);


        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember =mSharedPreferences.getBoolean("remember_password",false);
        //调用getBoolean()方法获取remember_password这个键对应的值
        if(isRemember){//将账号和密码都设置到文本框中
            String account =mSharedPreferences.getString("account","");
            String pass= mSharedPreferences.getString("password","");
            acountEdit.setText(account);
            passwordEdit.setText(pass);
            rememberPass.setChecked(true);
        }

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                account = acountEdit.getText().toString();
                password = passwordEdit.getText().toString();
                if (account.trim().equals("")) {
                    AlertDialog dialog = new AlertDialog.Builder(HelloWorldActivity.this)
                            .setTitle("")
                            .setMessage("请输入用户名")
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();

                                }
                            })
                            .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    android.os.Process.killProcess(android.os.Process.myPid());

                                }
                            }).create();
                    dialog.show();
                }
                else if (password.trim().equals("")) {
                    AlertDialog dialog= new AlertDialog.Builder(HelloWorldActivity.this)
                            .setTitle("")
                            .setMessage("请输入密码")
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();

                                }
                            })
                            .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    android.os.Process.killProcess(android.os.Process.myPid());

                                }
                            }).create();
                   dialog.show();

                }
                login();

            }
        });
    }
    public void login()
    {
        if ((account.trim().equals("1637955326")&&password.trim().equals("143121ngu@djx!!!"))
                ||(account.trim().equals("909274576")&&password.trim().equals("13222399886Cs*"))
                ||(account.trim().equals("10001")&&password.trim().equals("123456")))
        {
            editor =mSharedPreferences.edit();
            if(rememberPass.isChecked()) {//检查复选框是否被选中
                editor.putBoolean("remember_password", true);//用户想要记住密码,将remember_password设置为true
                editor.putString("account", account);
                editor.putString("password", password);
            }
            else{
                editor.clear();//复选框没有被选中，清除账号和密码
            }
            editor.apply();
            Intent intent = new Intent(HelloWorldActivity.this,SecondActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            editor =mSharedPreferences.edit();
            if(rememberPass.isChecked()) {//检查复选框是否被选中
                editor.putBoolean("remember_password", true);//用户想要记住密码,将remember_password设置为true
                editor.putString("account", account);
                editor.putString("password", password);
            }
            else{
                editor.clear();//复选框没有被选中，清除账号和密码
            }
            editor.apply();
            AlertDialog dialog = new AlertDialog.Builder(HelloWorldActivity.this)
                    .setTitle("")
                    .setMessage("账号或密码错误，请重新输入")
                    .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                        }
                    }).create();
            dialog.show();
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
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
                Toast.makeText(HelloWorldActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}