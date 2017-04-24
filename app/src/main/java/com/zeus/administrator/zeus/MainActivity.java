package com.zeus.administrator.zeus;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;
import com.zeus.administrator.database.QueryDataBaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements MenuListFragment.OnHeadlineSelectedListener {
    private SlidingMenu menu;
    private String stat;
    private TextView editName=null;
    private String username;
    private ListView listView;
    MyAdapter mAdapter;
    GetItemDateTask dTask=null;
    ArrayList<HashMap<String, Object>> listData=null;
    SimpleAdapter listItemAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActionBar();
        initSlidingMenu();
        initExtras();
        initListview();

    }

    private void initSlidingMenu() {

        // configure the SlidingMenu
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);

        // 设置滑动菜单视图的宽度
        //menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setBehindWidth(450);
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);
        /**
         * SLIDING_WINDOW will include the Title/ActionBar in the content
         * section of the SlidingMenu, while SLIDING_CONTENT does not.
         */
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //为侧滑菜单设置布局
        menu.setMenu(R.layout.menu_frame);
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new MenuListFragment()).commit();

    }
    private void initExtras(){

        editName = (TextView) findViewById(R.id.username);
        Intent i = new Intent();
        i = this.getIntent();
        Bundle b = i.getExtras();
        username = b.getString("username");
        stat = b.getString("stat");
        //  Toast.makeText(InitActivity.this, "hello! "+username, Toast.LENGTH_SHORT).show();
        editName.setText(username);
    }

    private void initActionBar(){

        listView= (ListView) findViewById(R.id.listView);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);
        getSupportActionBar().setHomeButtonEnabled(true);//actionbar��������Ա����
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//��ʾ�����ͼ��
       View customView = LayoutInflater.from(this).inflate(R.layout.actionbar_layout, new LinearLayout(this), false);
        //getSupportActionBar().setCustomView(LayoutInflater.from(this).inflate(R.layout.actionbar_layout, new LinearLayout(this), false) );
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(customView);
     /*   customView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!listView.isStackFromBottom()) {
                    listView.setStackFromBottom(true);
                }
                listView.setStackFromBottom(false);
                // Toast.makeText(InitActivity.this, "....", Toast.LENGTH_SHORT).show();
            }
        });*/





    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if ( id == android.R.id.home ){
            if( menu.isMenuShowing() )
                menu.showContent();
            else
                menu.showMenu();
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            System.exit(0);
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.menu_init, menu);
        return true;
    }

    public void onArticleSelected(int position) {
        // 用户选择了HeadlinesFragment中的头标题后
        Intent intent=new Intent();;
        Bundle b = new Bundle();
        b.putString("username", username);
        switch(position) {
            case 0:
                intent.setClass(getApplicationContext(), TestActivity.class);
                break;
            case 1:
                intent.setClass(getApplicationContext(), ExamActivity.class);
                break;
        }
        intent.putExtras(b);
        startActivityForResult(intent, 0);
        menu.showContent();
    }
    public void initListview(){
        listView= (ListView) findViewById(R.id.listView);
        //  mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.listView);
        //   mPullRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);



        Log.v("tag","线程："+String.valueOf( Thread.currentThread().getId()));
        mAdapter = new MyAdapter(this);//得到一个MyAdapter对象
        // listView.setAdapter(mAdapter);//为ListView绑定Adapter
        listView.setAdapter(mAdapter);
        dTask = new GetItemDateTask();
        try {
            listData = dTask.execute(username).get();
        } catch (Exception e) {
            listData=null;
        }
        // Log.v("tag",String.valueOf( Thread.currentThread().getId()));
    }

    /** 新建一个类继承BaseAdapter，实现视图与数据的绑定
     */
    private class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
        QueryDataBaseAdapter qd =new QueryDataBaseAdapter( MainActivity.this );
        // ArrayList<HashMap<String, Object>> listItem;

        /**构造函数*/
        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
            //     listItem = qd.getToolDtlData(username);
        }

        @Override
        public int getCount() {
            int ret=0;
            if ( listData!=null ){
                ret = listData.size();
            }
            return ret;//返回数组的长度
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        /**书中详细解释该方法*/
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            //观察convertView随ListView滚动情况
            if (convertView == null) {
                convertView = mInflater.inflate( R.layout.vlist ,null);
                holder = new ViewHolder();
                /**得到各个控件的对象*/
                //holder.img = (ImageView) convertView.findViewById(R.id.img);
                holder.trantime = (TextView) convertView.findViewById(R.id.trantime);
                holder.score = (TextView) convertView.findViewById(R.id.score);
                holder.using_time = (TextView) convertView.findViewById(R.id.using_time);
                holder.stars = (ImageButton) convertView.findViewById(R.id.stars);
                convertView.setTag(holder);//绑定ViewHolder对象
            }
            else{
                holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象
            }
            /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
            holder.trantime.setText(listData.get(position).get("trantime").toString());
            holder.score.setText(listData.get(position).get("score").toString());
            holder.using_time.setText(listData.get(position).get("using_time").toString());
      /*      if( !listData.get(position).get("sourcefrom").toString().equals("******")) {
                holder.button_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showInfo(position, listData);
                    }
                });
            }*/

            return convertView;
        }

    }

    /**存放控件*/
    public final class ViewHolder{
        public TextView trantime;
        public TextView score;
        public TextView using_time;
        public ImageButton stars;
    }
    class GetItemDateTask extends AsyncTask< String ,  Integer,  ArrayList<HashMap<String, Object>>> {//获取图片仍采用AsyncTask，这里的优化放到下篇再讨论

        String name;
        ArrayList<HashMap<String, Object>> listItem;
        QueryDataBaseAdapter qd =new QueryDataBaseAdapter( MainActivity.this );

        @Override
        protected void onPreExecute() {
            //第一个执行方法
            super.onPreExecute();
            showDialog(1);//打开等待对话框
        }
     /*  GetItemDateTask(String name) {
            this.name = name;
        }*/

        @Override
        protected  ArrayList<HashMap<String, Object>> doInBackground(String... params) {
            listItem = qd.getExamDtlData(params[0]);
            Log.v("tag","线程："+ String.valueOf(Thread.currentThread().getId()));
            return listItem;
        }
        protected void onPostExecute (ArrayList<HashMap<String, Object>> result) {
          /*  if(result != null) {
                Toast.makeText(InitActivity.this, "成功获取数据", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(InitActivity.this, "获取数据失败", Toast.LENGTH_LONG).show();
            }*/
            mAdapter.notifyDataSetChanged();//通知ui界面更新
            //  listView.setAdapter(mAdapter);
            removeDialog(1);//关闭等待对话框
        }

    }

}