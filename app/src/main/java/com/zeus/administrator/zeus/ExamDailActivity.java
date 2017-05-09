package com.zeus.administrator.zeus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zeus.administrator.database.QueryDataBaseAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ExamDailActivity extends AppCompatActivity {
    private TextView vusername,vlevel,vtotnum,vtotsucc,vtottime,vtotprob,vcomment;
    private Button button;
    private String username,level,totnum,totsucc,tottime,stars;
    private int totprob;
    private ImageView redstar;
    QueryDataBaseAdapter qd;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_dail);

    //    qd = new QueryDataBaseAdapter(this);

        initTextView();
        initIntent();
        initSetText();
        inserDB();
        initButton();

    }
    private void initTextView(){
        vusername = (TextView)findViewById(R.id.username);
        redstar = (ImageView)findViewById(R.id.redstars);
        vlevel = (TextView)findViewById(R.id.level);
        vtotnum = (TextView)findViewById(R.id.totnum);
        vtotsucc = (TextView)findViewById(R.id.totsucc);
        vtottime = (TextView)findViewById(R.id.tottime);
        vtotprob = (TextView)findViewById(R.id.totprob);
        vcomment = (TextView)findViewById(R.id.comment);
        button = (Button)findViewById(R.id.button);
    }
    private void initIntent(){
        intent  = new Intent();
        intent = this.getIntent();
        Bundle b = intent.getExtras();
        username = b.getString("username").trim();
        level = b.getString("level").trim();
        totnum = b.getString("totnum").trim();
        tottime = b.getString("usedtime").trim();
        totsucc = b.getString("totsucc").trim();
        totprob = b.getInt("totprob");

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
        vtotprob.setText(totprob+"");
        if( totprob ==0  ) {
            vcomment.setText("哎呀！没有成绩！");
            redstar.setImageDrawable(getResources().getDrawable(R.drawable.redstars1));
            stars = "1";
        }
        if( totprob >0 &&totprob < 60 ) {
            vcomment.setText("没关系，请再接再厉！");
            redstar.setImageDrawable(getResources().getDrawable(R.drawable.redstars2));
            stars = "2";
        }
        if( totprob >=60 && totprob < 80 ) {
            vcomment.setText("不错，继续努力！");
            redstar.setImageDrawable(getResources().getDrawable(R.drawable.redstars3));
            stars = "3";
        }
        if( totprob >=80 && totprob < 100 ) {
            vcomment.setText("非常好，离胜利已经不远了！");
            redstar.setImageDrawable(getResources().getDrawable(R.drawable.redstars4));
            stars = "4";
        }
        if( totprob == 100  ) {
            vcomment.setText("太棒了，大家以你为荣！");
            redstar.setImageDrawable(getResources().getDrawable(R.drawable.redstars5));
            stars = "5";
        }
    }

    private void initButton(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
    private void inserDB(){
        String trantime,usedtime;
        SimpleDateFormat sdformat;
        ArrayList<HashMap<String, Object>> listData;
        HashMap<String, Object> map = new HashMap<String, Object>();
        listData = new ArrayList<HashMap<String, Object>>();

        Date date = new Date();
        sdformat = new SimpleDateFormat("yyMMddHHmmss");//24小时制
        trantime = sdformat.format(date);

        sdformat = new SimpleDateFormat("HH:mm:ss");
        usedtime = sdformat.format(Integer.parseInt(tottime)*1000);

        qd = new QueryDataBaseAdapter(this);

        map = new HashMap<String, Object>();

        map.put("trantime",trantime );
        map.put("level", level ) ;
        map.put("score", totprob+"" ) ;
        map.put("stars",stars ) ;
        map.put("usedtime", usedtime ) ;
        listData.add(map);
     //   Toast.makeText(ExamDailActivity.this,trantime+" "+level+" "+totprob+" "+stars+" "+ usedtime, Toast.LENGTH_LONG).show();

        qd.insertExamdtl(username,listData);
    }
}
