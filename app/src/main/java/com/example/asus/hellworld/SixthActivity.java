package com.example.asus.hellworld;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SixthActivity extends AppCompatActivity {

    private long clickTime = 0;
    private NavigationView mNavigationView;
    List<News> list = new ArrayList<>();
    ListView  tableListView;

    public class TableAdapter extends BaseAdapter{//适配器

        private List<News> list;
        private LayoutInflater inflater;

        public TableAdapter(Context context, List<News> list){
            this.list = list;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            int ret = 0;
            if(list!=null){
                ret = list.size();
            }
            return ret;
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {//得到布局的信息

            News news = (News) this.getItem(position);
            ViewHolder viewHolder;

            if(convertView == null){

                viewHolder = new ViewHolder();

                convertView = inflater.inflate(R.layout.list_item, null);
                viewHolder.title = convertView.findViewById(R.id.text_title);
                viewHolder.date = convertView.findViewById(R.id.text_date);
                viewHolder.source = convertView.findViewById(R.id.text_source);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.title.setText(news.getTitle());
            viewHolder.title.setTextSize(20);
            viewHolder.date.setText(news.getDate());
            viewHolder.date.setTextSize(14);
            viewHolder.source.setText(news.getSource());
            viewHolder.source.setTextSize(14);

            return convertView;
        }

        public  class ViewHolder{
            public TextView title;
            public TextView date;
            public TextView source;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sixth_layout);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null) {
            actionBar.hide();
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //绑定控件

        /*ViewGroup tableTitle = findViewById(R.id.table_title);设置标题栏颜色
        tableTitle.setBackgroundColor(Color.rgb(228, 255, 232));*/
        tableListView= findViewById(R.id.list);
        Button back_to_last = findViewById(R.id.back);
        mNavigationView = findViewById(R.id.nav_view);
        back_to_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SixthActivity.this.finish();
            }
        });


        sendRequestWithOkHttp();//解析数据，并且添加到listview中

        /*TableAdapter adapter = new TableAdapter(SixthActivity.this, list);
        tableListView.setAdapter(adapter);只能在RunOnUiThraed中完成适配器的设置*/
//个人中心
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_message:
                        Toast.makeText(SixthActivity.this,"没有消息哦(๑òᆺó๑)",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_user:
                        Toast.makeText(SixthActivity.this,"用户: 杜剑雄\n年龄: 21\n" +
                                "手机号:17505130365\n",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_password:
                        Toast.makeText(SixthActivity.this,"暂时不能修改哦(づ ●─● )づ\n",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_word:
                        Toast.makeText(SixthActivity.this,"功能目前很简单哦，不需要使用文档哒~",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_out:
                        Intent intent_exit = new Intent(SixthActivity.this,HelloWorldActivity.class);
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

    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://v.juhe.cn/toutiao/index?type=top&key=6ca4c9ae29e5faa92eca6aca9912c031")
                            .build();
                    Response response = client.newCall(request).execute();
                    final String responseData = response.body().string();
                    //Collections.reverse(list);
                   SixthActivity.this.runOnUiThread(new Runnable() {//指定更新UI的是哪一个活动
                        @Override
                        public void run() {
                            paraseJSONWithJSONObject(responseData);
                            Toast.makeText(SixthActivity.this,list.size()+"",Toast.LENGTH_SHORT).show();
                            //Toast.makeText(SixthActivity.this,responseData,Toast.LENGTH_LONG).show();
                            TableAdapter adapter = new TableAdapter(SixthActivity.this, list);
                            tableListView.setAdapter(adapter);//只能在主线程中完成适配器的设置
                            tableListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    News news = list.get(position);
                                    //app中嵌入浏览器
                                    setContentView(R.layout.activity_webview_acitivity);
                                    WebView webView = findViewById(R.id.web_view);
                                    webView.getSettings().setJavaScriptEnabled(true);
                                    webView.setWebViewClient(new WebViewClient());
                                    webView.loadUrl(news.url);
                                    final WebView webView1 = findViewById(R.id.web_view);
                                    Button back_to_last2= findViewById(R.id.back);
                                    back_to_last2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if(webView1.canGoBack()) {
                                                webView1.goBack();
                                            }
                                            else{
                                                Intent intent = new Intent(SixthActivity.this, SixthActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                                   /* Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse(news.url));
                                    startActivity(intent);//这是打开系统浏览器的*/
                                }
                            });
                        }
                    });
                }
               catch (Exception e) {
                    e.printStackTrace();
                }
           }
        }).start();
    }
   private void paraseJSONWithJSONObject(String jsonData) {
        try{
            JSONObject object  = new JSONObject(jsonData);
            JSONObject object2 = object.getJSONObject("result");
            JSONArray jsonArray = object2.getJSONArray("data");
            /*假如只嵌套了两层,要一层层进去获取数据，这是非常非常重要的，搞了我24小时学习时间！！！才发现错误；
            会查错也是很重要的，一步步试看看哪里有问题，Log不出来就Toast出来看！！！
            JSONObject object = new JSONObject("json数据源");
            JSONObject object2 = object.getJSONObject("键名的父级的父级");
            JSONObject object3 = object2.getJSONObject("键名的父级");
            String string = jsonObject3.getString("键名")*/
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString("title");
                String date = jsonObject.getString("date");
                String source = jsonObject.getString("author_name");
                String url = jsonObject.getString("url");
                list.add(new News(title,date,source,url));
               // Log.d("SixthActivity","haha");
            }


            /*TableAdapter adapter = new TableAdapter(this, list);
            tableListView.setAdapter(adapter);放在这里也可以不过不太容易让人理解，直接放到主线程中会更好，
            这是嵌套了的函数，其实还是在主线程中*/

        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(SixthActivity.this,"Failed to parase",Toast.LENGTH_SHORT).show();
        }
    }
//News类以及News类的构造函数
    public class News {
        private String title;
        private String date;;
        private String source;
        private String url;

        public News(String title,String date,String source,String url) {
            this.title= title;
            this.date = date;
            this.source = source;
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public String getDate(){
        return date;
    }


        public String getSource() {
            return source;
        }

        public String getUrl() {
            return url;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            /*点第一次back键就执行下面的if (SystemClock.uptimeMillis() - clickTime <= 1500)
            这个语句，因为此时clickTime为0，而SystemClock.uptimeMillis()是>=1.5毫秒的。
            第二次第三次点的时候，已经执行过 clickTime = SystemClock.uptimeMillis();这条语句
            所以第56行减掉的clickTime就变了，什么也不执行，然后执行第68行的语句，由Activity调用方法*/
                if (SystemClock.uptimeMillis() - clickTime <= 1500) {
                /*SystemClock.uptimeMillis()从开机到现在的毫秒数，不包括手机睡眠的时间
                 如果两次的时间差＜1.5就不执行操作，SystemClock.currenTimeMillis()从1970年1月1日
                 UTC 到现在的毫秒数*/
                    Intent startMain = new Intent(Intent.ACTION_MAIN);//显示主界面
                    startMain.addCategory(Intent.CATEGORY_HOME);
                    startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(startMain);
                    System.exit(0);
                } else {
                    //当前系统时间的毫秒值
                    clickTime = SystemClock.uptimeMillis();
                    Toast.makeText(SixthActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    return false;
                }
        }
        return super.onKeyDown(keyCode, event);
    }
}

