package com.example.asus.hellworld.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.telephony.mbms.MbmsErrors;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.hellworld.HelloWorldActivity;
import com.example.asus.hellworld.R;
import com.example.asus.hellworld.SixthActivity;
import com.example.asus.hellworld.WebviewAcitivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SixthFragment extends Fragment {
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private ImageView openDrawerLayout;
    List<News> list = new ArrayList<>();
    ListView tableListView;
    public SixthFragment() {
        // Required empty public constructor
    }


    public class TableAdapter extends BaseAdapter {//适配器

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sixth_layout, container, false);
         /*ViewGroup tableTitle = findViewById(R.id.table_title);设置标题栏颜色
        tableTitle.setBackgroundColor(Color.rgb(228, 255, 232));*/
        tableListView= view.findViewById(R.id.list);
        mNavigationView = view.findViewById(R.id.nav_view);
        mDrawerLayout = view.findViewById(R.id.drawer_layout);
        openDrawerLayout = view.findViewById(R.id.openDrawerLayout);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        sendRequestWithOkHttp();//解析数据，并且添加到listview中

        /*TableAdapter adapter = new TableAdapter(SixthActivity.this, list);
        tableListView.setAdapter(adapter);只能在RunOnUiThraed中完成适配器的设置*/
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
                        startActivity(intent_exit);
                        //回到登录界面
                        // System.exit(0);另一种退出方法
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
                    getActivity().runOnUiThread(new Runnable() {//指定更新UI的是哪一个活动
                        @Override
                        public void run() {
                            paraseJSONWithJSONObject(responseData);
                            //Toast.makeText(getContext(),list.size()+"",Toast.LENGTH_SHORT).show();
                            //Toast.makeText(SixthActivity.this,responseData,Toast.LENGTH_LONG).show();
                            TableAdapter adapter = new TableAdapter(getContext(), list);
                            tableListView.setAdapter(adapter);//只能在主线程中完成适配器的设置
                            tableListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    News news = list.get(position);
                                    Intent intent = new Intent(getActivity(),WebviewAcitivity.class);
                                    intent.putExtra("url",news.url);
                                    startActivity(intent);
                                    /*Intent intent1 = new Intent(Intent.ACTION_VIEW);
                                    intent1.setData(Uri.parse(news.url));
                                    startActivity(intent1);//这是打开系统浏览器的*/
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
            //Toast.makeText(getContext(),"今天的浏览次数已经用完了哦(づ ●─● )づ",Toast.LENGTH_SHORT).show();
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
}
