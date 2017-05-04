package com.zeus.administrator.zeus;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class TestDailActivity extends AppCompatActivity {

    private TextView vusername,vlevel,vtotnum,vtotsucc,vtottime,vtotprob,vcomment;
    private Button button;
    private String username,level,totnum,totsucc,tottime;
    private float totprob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_dail);

        initTextView();

        initIntent();
        initSetText();

        initButton();

    }
    private void initTextView(){
        vusername = (TextView)findViewById(R.id.username);
        vlevel = (TextView)findViewById(R.id.level);
        vtotnum = (TextView)findViewById(R.id.totnum);
        vtotsucc = (TextView)findViewById(R.id.totsucc);
        vtottime = (TextView)findViewById(R.id.tottime);
        vtotprob = (TextView)findViewById(R.id.totprob);
        vcomment = (TextView)findViewById(R.id.comment);
        button = (Button)findViewById(R.id.button);
    }
    private void initIntent(){
        Intent intent = new Intent();
        intent = this.getIntent();
        Bundle b = intent.getExtras();
        username = b.getString("username").trim();
        level = b.getString("level").trim();
        totnum = b.getString("totnum").trim();
        tottime = b.getString("tottime").trim();
        totsucc = b.getString("totsucc").trim();
        totprob = b.getFloat("totprob");

    }
    private void initSetText(){
        vusername.setText(username);
        if( level.equals("1"))
            vlevel.setText("简单模式");
        if( level.equals("2"))
            vlevel.setText("普通模式");
        if( level.equals("3"))
            vlevel.setText("困难模式");
        vtotnum.setText(totnum);
        vtotsucc.setText(totsucc);
        vtottime.setText(tottime);
        vtotprob.setText(totprob+"%");
        if( totprob ==0  )
            vcomment.setText("哎呀！没有成绩！");
        if( totprob >0 &&totprob < 60 )
            vcomment.setText("没关系，请再接再厉！");
        if( totprob >=60 && totprob < 80 )
            vcomment.setText("不错，继续努力！");
        if( totprob >=80 && totprob < 100 )
            vcomment.setText("非常好，离胜利已经不远了！");
        if( totprob == 100  )
            vcomment.setText("太棒了，大家以你为荣！");
    }
    private void initButton(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
