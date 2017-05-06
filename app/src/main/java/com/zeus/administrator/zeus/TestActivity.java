package com.zeus.administrator.zeus;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.zeus.administrator.database.QueryDataBaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import static com.zeus.administrator.zeus.R.id.timer;

public class TestActivity extends AppCompatActivity {

    private ArrayList<HashMap<String, Object>> arrayList;
    String[] flag;
    private TextView text1,text2,text3,text4,gram1,gram2,gram3,gram4;
    private String words_totcount,level,username;
    int totcount=0;
    private int position = 0;
    private Chronometer timeText;
    private Button know,unknow,end;
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
        setTextView();
        QueryDataBaseAdapter qd = new QueryDataBaseAdapter(this);
        words_totcount = qd.getCountSettings();
        totcount = Integer.parseInt(words_totcount);
        flag = new String[totcount];
        arrayList = qd.getWordDict(level,words_totcount);
        getwords(position);
        getButton();


    }
    private void getButton(){
        know = (Button)findViewById(R.id.knowButt);
        unknow = (Button)findViewById(R.id.unknowButt);
        end = (Button)findViewById(R.id.endButt);

        know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag[position]="1";

                if((position+1)>=totcount){
                    finshTest();
                }else {
                    position++;
                    getwords(position);
                }
            }
        });
        unknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag[position]="0";

                if((position+1)>=totcount){
                    finshTest();
                } else {
                    position++;
                    getwords(position);
                }
            }
        });
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finshTest();
            }
        });
    }
    private void finshTest(){
        int sucCont=0;
        String hour,min,sec;
        float totProb;
        int totTime=0;
        int totNum=0;
        int i;
        timeText.stop();

        totNum = position ;
        Log.v("tag",totNum+"");
        for( i = 0; i < position; i++ ){
            if(flag[i].equals("1")) sucCont++;
        }
        hour = timeText.getText().toString().split(":")[0];
        min = timeText.getText().toString().split(":")[1];
        sec = timeText.getText().toString().split(":")[2];
        totTime=Integer.parseInt(hour)*60*60+Integer.parseInt(min)*60+Integer.parseInt(sec);

        if( totNum == 0 ){
            totProb = 0;
        }else
            totProb = ((float) sucCont/(float) totNum)*100;

    //    Toast.makeText(TestActivity.this,"成功数"+sucCont+" 总时间"+totTime+" 总笔数"+totNum, Toast.LENGTH_LONG).show();

        Intent intent=new Intent();
        intent.setClass(getApplicationContext(), TestDailActivity.class);
        Bundle b = new Bundle();
        b.putString("username",username);
        b.putString("level",level);
        b.putString("totnum",totNum+"");
        b.putString("tottime",totTime+"");
        b.putString("totsucc",sucCont+"");
        b.putFloat("totprob",totProb);
        intent.putExtras(b);

        startActivity(intent);
        finish();

    }
    private void setTextView(){
        text1 = (TextView)findViewById(R.id.words1);
        text2 = (TextView)findViewById(R.id.words2);
        text3 = (TextView)findViewById(R.id.words3);
        text4 = (TextView)findViewById(R.id.words4);
        gram1 = (TextView)findViewById(R.id.grammar1);
        gram2 = (TextView)findViewById(R.id.grammar2);
        gram3 = (TextView)findViewById(R.id.grammar3);
        gram4 = (TextView)findViewById(R.id.grammar4);
    }

    private void getwords(int position){
        String[] word;
        String[] grama;

        word = arrayList.get(position).get("word").toString().split("\\s+");
        grama = arrayList.get(position).get("grammar").toString().split("\\s+");

        if( word.length > 2 ){
            text1.setText(word[0]);
            text2.setText(word[1]);
            text3.setText(word[2]);
            text4.setText(word[3]);

            gram1.setText(grama[0]);
            gram2.setText(grama[1]);
            gram3.setText(grama[2]);
            gram4.setText(grama[3]);

        }else {

            text1.setText("");
            text2.setText(word[0]);
            text3.setText(word[1]);
            text4.setText("");

            gram1.setText("");
            gram2.setText(grama[0]);
            gram3.setText(grama[1]);
            gram4.setText("");
        }

    }

    private void initTime(){
        timeText = (Chronometer) findViewById(timer);
        timeText.setBase(SystemClock.elapsedRealtime());//计时器清零
        int hour = (int) ((SystemClock.elapsedRealtime() - timeText.getBase()) / 1000 / 60);
        timeText.setFormat("0"+String.valueOf(hour)+":%s");
        timeText.start();
    }

}
