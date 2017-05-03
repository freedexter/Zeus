package com.zeus.administrator.zeus;

import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Chronometer;
import android.widget.TextView;

import com.zeus.administrator.database.QueryDataBaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import static com.zeus.administrator.zeus.R.id.timer;

public class TestActivity extends AppCompatActivity {

    private ArrayList<HashMap<String, Object>> arrayList;
    private TextView text1,text2,text3,text4,gram1,gram2,gram3,gram4;
    private String words_totcount,level,username;

    private Chronometer timeText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initTime();

        Intent intent = new Intent();
        intent = this.getIntent();
        Bundle b = intent.getExtras();
        level = b.getString("level").trim();
        username = b.getString("username").trim();





    }
    private void getwords(){
        QueryDataBaseAdapter qd = new QueryDataBaseAdapter(this);
        words_totcount = qd.getCountSettings();
        arrayList = qd.getWordDict(level,words_totcount);


    }

    private void initTime(){
        timeText = (Chronometer) findViewById(timer);
        timeText.setBase(SystemClock.elapsedRealtime());//计时器清零
        int hour = (int) ((SystemClock.elapsedRealtime() - timeText.getBase()) / 1000 / 60);
        timeText.setFormat("0"+String.valueOf(hour)+":%s");
        timeText.start();
    }

}
