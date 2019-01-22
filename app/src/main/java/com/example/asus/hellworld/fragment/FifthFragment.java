package com.example.asus.hellworld.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
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
import com.example.asus.hellworld.HelloWorldActivity;
import com.example.asus.hellworld.R;

import java.util.ArrayList;
import java.util.List;

public class FifthFragment extends Fragment {
    private DrawerLayout mDrawerLayout;
    private ImageView openDrawerLayout;
    ListView listView;
    public FifthFragment() {
        // Required empty public constructor
    }
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
            FruitAdapter.ViewHolder viewHolder;
            if (convertView == null) {
                view = LayoutInflater.from(getContext()).inflate(resourcedId, parent, false);
                viewHolder = new FruitAdapter.ViewHolder();
                viewHolder.fruitImage = view.findViewById(R.id.fruit_image);
                viewHolder.fruitName = view.findViewById(R.id.fruit_name);
                view.setTag(viewHolder);//将ViewHolder存储在View中
            } else {
                view = convertView;
                viewHolder = (FruitAdapter.ViewHolder) view.getTag();//重新获取ViewHolder
            }
            viewHolder.fruitImage.setImageResource(fruit.getImagedId());
            viewHolder.fruitName.setText(fruit.getName());
            return view;

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fifth_layout, container, false);
        //绑定id
        listView = view.findViewById(R.id.list_view);
        mNavigationView = view.findViewById(R.id.nav_view);
        mDrawerLayout = view.findViewById(R.id.drawer_layout);
        openDrawerLayout = view.findViewById(R.id.openDrawerLayout);
        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public void onStart() {
        super.onStart();
        openDrawerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);//禁止侧边划出个人主页
        initFruits();
        FruitAdapter adapter = new FruitAdapter(getContext(), R.layout.fruit_item, fruitList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fruit fruit = fruitList.get(position);
                Toast.makeText(getContext(), fruit.getName(), Toast.LENGTH_SHORT).show();

            }
        });
        //个人中心
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_message:
                        Toast.makeText(getActivity(), "没有消息哦(๑òᆺó๑)", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_user:
                        Toast.makeText(getActivity(), "用户: 杜剑雄\n年龄: 21\n" +
                                "手机号:17505130365\n", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_password:
                        Toast.makeText(getActivity(), "暂时不能修改哦(づ ●─● )づ\n", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_word:
                        Toast.makeText(getActivity(), "功能目前很简单哦，不需要使用文档哒~", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_out:
                        Intent intent_exit = new Intent(getContext(), HelloWorldActivity.class);
                        intent_exit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//关键，将Activity设置为栈顶
                        startActivity(intent_exit);//回到登录界面
                        //System.exit(0);另一种退出方法
                        break;
                }
                return true;
            }
        });
    }
}
