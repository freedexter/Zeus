package com.zeus.administrator.zeus;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

public class MainActivity extends AppCompatActivity implements MenuListFragment.OnHeadlineSelectedListener {
    private SlidingMenu menu;
    private String stat;
    private TextView editName=null;
    private String username;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActionBar();
        initSlidingMenu();
        initExtras();

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

       // listView= (ListView) findViewById(R.id.listView);
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

}