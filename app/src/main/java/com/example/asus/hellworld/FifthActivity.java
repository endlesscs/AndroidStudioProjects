package com.example.asus.hellworld;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FifthActivity extends AppCompatActivity {

    private long clickTime = 0;
    private NavigationView mNavigationView;
    private List<Fruit> fruitList = new ArrayList<>();
    public class Fruit {
        private String name;
        private int imagedId;

        public Fruit(String name, int imagedId) {
            this.name = name;
            this.imagedId = imagedId;
        }

        public String getName() {
            return name;
        }

        public int getImagedId() {
            return imagedId;
        }
    }

    public class FruitAdapter extends ArrayAdapter<Fruit> {
        public int resourcedId;

        public FruitAdapter(Context context, int textViewResourceId, List<Fruit> objects) {
            super(context, textViewResourceId, objects);
            resourcedId = textViewResourceId;
        }

        class ViewHolder {
            ImageView fruitImage;
            TextView fruitName;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Fruit fruit = getItem(position);//获取当前项的Fruit实例
            View view;
            ViewHolder viewHolder;
            if (convertView == null) {
                view = LayoutInflater.from(getContext()).inflate(resourcedId, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.fruitImage = view.findViewById(R.id.fruit_image);
                viewHolder.fruitName = view.findViewById(R.id.fruit_name);
                view.setTag(viewHolder);//将ViewHolder存储在View中
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();//重新获取ViewHolder
            }
            viewHolder.fruitImage.setImageResource(fruit.getImagedId());
            viewHolder.fruitName.setText(fruit.getName());
            return view;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fifth_layout);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null) {
            actionBar.hide();
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        //绑定id
        ListView listView = findViewById(R.id.list_view);
        Button back_to_last = findViewById(R.id.back);
        mNavigationView = findViewById(R.id.nav_view);
        initFruits();
        FruitAdapter adapter = new FruitAdapter(FifthActivity.this, R.layout.fruit_item, fruitList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fruit fruit = fruitList.get(position);
                Toast.makeText(FifthActivity.this, fruit.getName(), Toast.LENGTH_SHORT).show();

            }
        });

        back_to_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FifthActivity.this.finish();
            }
        });

        //个人中心
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_message:
                        Toast.makeText(FifthActivity.this,"没有消息哦(๑òᆺó๑)",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_user:
                        Toast.makeText(FifthActivity.this,"用户: 杜剑雄\n年龄: 21\n" +
                                "手机号:17505130365\n",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_password:
                        Toast.makeText(FifthActivity.this,"暂时不能修改哦(づ ●─● )づ\n",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_word:
                        Toast.makeText(FifthActivity.this,"功能目前很简单哦，不需要使用文档哒~",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_out:
                        Intent intent_exit = new Intent(FifthActivity.this,HelloWorldActivity.class);
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
    private void initFruits() {
        for (int i = 0; i < 2; i++) {
            Fruit apple = new Fruit("Apple", R.mipmap.apple);
            fruitList.add(apple);
            Fruit banana = new Fruit("Banana", R.mipmap.banana);
            fruitList.add(banana);
            Fruit orange = new Fruit("Orange", R.mipmap.orange);
            fruitList.add(orange);
            Fruit watermelon = new Fruit("Watermelon", R.mipmap.watermelon);
            fruitList.add(watermelon);
            Fruit pear = new Fruit("Pear", R.mipmap.pear);
            fruitList.add(pear);
            Fruit grape = new Fruit("Grape", R.mipmap.grape);
            fruitList.add(grape);
            Fruit pineapple = new Fruit("Pineapple", R.mipmap.pineapple);
            fruitList.add(pineapple);
            Fruit strawberry = new Fruit("Strawberry", R.mipmap.strawberry);
            fruitList.add(strawberry);
            Fruit cherry = new Fruit("Cherry", R.mipmap.cherry);
            fruitList.add(cherry);
            Fruit mango = new Fruit("Mango", R.mipmap.mango);
            fruitList.add(mango);
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
                Toast.makeText(FifthActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}

