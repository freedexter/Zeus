package com.zeus.administrator.zeus;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.provider.Contacts;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.TabHost;

public class SetupActivity extends TabActivity  {

    private TabHost mTabHost;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setup);
      //  getSupportActionBar().hide();

        Intent intent;
        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;




        intent = new Intent(this,ChgPwdActivity.class);//新建一个Intent用作Tab1显示的内容
        spec = tabHost.newTabSpec("tab1")//新建一个 Tab
                .setIndicator("密码重置", res.getDrawable(android.R.drawable.ic_media_play))//设置名称以及图标
                .setContent(intent);//设置显示的intent，这里的参数也可以是R.id.xxx
        tabHost.addTab(spec);//添加进tabHost
        intent = new Intent(this,SetParaActivity.class);//新建一个Intent用作Tab1显示的内容
        spec = tabHost.newTabSpec("tab2")//新建一个 Tab
                .setIndicator("参数设置", res.getDrawable(android.R.drawable.ic_media_play))//设置名称以及图标
                .setContent(intent);//设置显示的intent，这里的参数也可以是R.id.xxx
        tabHost.addTab(spec);//添加进tabHost
        intent = new Intent(this,ImportDtlActivity.class);//新建一个Intent用作Tab1显示的内容
        spec = tabHost.newTabSpec("tab3")//新建一个 Tab
                .setIndicator("导入词库", res.getDrawable(android.R.drawable.ic_media_play))//设置名称以及图标
                .setContent(intent);//设置显示的intent，这里的参数也可以是R.id.xxx
        tabHost.addTab(spec);//添加进tabHost
        tabHost.setCurrentTab(0);
    }

}
