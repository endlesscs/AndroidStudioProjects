package com.example.asus.hellworld;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;
import com.example.asus.hellworld.SixthActivity;
import com.example.asus.hellworld.fragment.SecondFragment;
import com.example.asus.hellworld.fragment.SixthFragment;

public class WebviewAcitivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_acitivity);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null) {
            actionBar.hide();
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        final WebView webView = findViewById(R.id.web_view);
        Button back_to_last = findViewById(R.id.back);
        back_to_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(webView.canGoBack())
                    webView.goBack();
                else {
                    Intent intent = new Intent(WebviewAcitivity.this,SecondActivity.class);
                    startActivity(intent);
                }
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        webView.loadUrl(url);
    }
    /*onKeyDown用来判断手机键盘被按下时的事件
    keyCode是用户按下手机键盘的键盘码,当用户按下按键，系统会自动将事件封装成KeyEvent供用户使用，
    按键事件keyEvent,有多种常量类型，如下面的KEYCODE_BACK
    该方法的返回值返回一个布尔变量，当返回 true时表示已经完整的处理了整个事件 ，并不希望其他回掉方法
    再次进行处理，而当返回false时，表示并没有完全处理完该事件，更需要其他回调方法进行处理，例如Activity
    中的回调方法*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        WebView webView = findViewById(R.id.web_view);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            }
            /*点第一次back键就执行下面的if (SystemClock.uptimeMillis() - clickTime <= 1500)
            这个语句，因为此时clickTime为0，而SystemClock.uptimeMillis()是>=1.5毫秒的。
            第二次第三次点的时候，已经执行过 clickTime = SystemClock.uptimeMillis();这条语句
            所以第56行减掉的clickTime就变了，什么也不执行，然后执行第68行的语句，由Activity调用方法*/
            else {
                Intent intent = new Intent(WebviewAcitivity.this, SecondActivity.class);
                startActivity(intent);
                return false;
                }
            }
            return super.onKeyDown(keyCode, event);
    }
}
/*实现的基本原理就是，当按下BACK键时，会被onKeyDown捕获，判断是BACK键，则执行exit方法。
判断用户两次按键的时间差是否在一个预期值之内，是的话直接直接退出，不是的话提示用户再按一次后退键退出。
1)onKeyDown方法，该方法是接口KeyEvent.Callback中的抽象方法，所有的View全部实现了该接口并重写了该方法，
该方法用来捕捉手机键盘被按下的事件。
2)参数keyCode，该参数指的是被按下的键的键盘码，手机键盘中每个按钮都会有其对应的键盘码，在应用程序都是
通过键盘码才知道用户按下的是哪个键。
3)当用户按下按键时，系统会自动将事件封装成KeyEvent对象供应用程序使用。按键事件KeyEvent按键事件有多种
常量类型，比如 KEYCODE_BACK 。
4)返回值，该方法的返回值为一个boolean类型的变量，当返回true时，表示已经完整地处理了这个事件，并不希望
其他的回调方法再次进行处理，而当返回false时，表示并没有完全处理完该事件，更希望其他回调方法继续对其
进行处理，例如Activity中的回调方法。*/