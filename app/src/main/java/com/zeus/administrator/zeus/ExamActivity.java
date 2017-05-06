package com.zeus.administrator.zeus;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zeus.administrator.database.QueryDataBaseAdapter;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class ExamActivity extends AppCompatActivity {
    private TextView time;
    private TextView text1,text2,text3,text4,gram1,gram2,gram3,gram4;
    private long remainTime;
    private int position = 0;
    int totcount=0;
    private ArrayList<HashMap<String, Object>> arrayList;
    String[] flag;
    private Button know,unknow,end;
    private String words_totcount,level,username,limt_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        setTextView();

        Intent intent = new Intent();
        intent = this.getIntent();
        Bundle b = intent.getExtras();
        level = b.getString("level").trim();
        username = b.getString("username").trim();

        QueryDataBaseAdapter qd = new QueryDataBaseAdapter(this);
        limt_time = qd.getLimtTimeSettings();
       // Log.v("tag" ,limt_time);
        remainTime = Integer.parseInt(limt_time.split(":")[0])*60*60*1000+Integer.parseInt(limt_time.split(":")[1])*60*1000+Integer.parseInt(limt_time.split(":")[2])*1000;
       // timer.start();
     //   Log.v("tag" ,remainTime+"");
        words_totcount = qd.getCountSettings();
        totcount = Integer.parseInt(words_totcount);
        flag = new String[totcount];
        arrayList = qd.getWordDict(level,words_totcount);
        getwords(position);
        changeMillisInFuture(remainTime+1000);
        timer.start();
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
        long lasttime=0,usedtime=0;
        int totNum=0;
        int i;
        timer.cancel();

        totNum = position ;
        Log.v("tag",totNum+"");
        for( i = 0; i < position; i++ ){
            if(flag[i].equals("1")) sucCont++;
        }
        hour = time.getText().toString().split(":")[0];
        min = time.getText().toString().split(":")[1];
        sec = time.getText().toString().split(":")[2];
        lasttime=(Integer.parseInt(hour)*60*60+Integer.parseInt(min)*60+Integer.parseInt(sec))*1000;
        usedtime = remainTime - lasttime;


        if( totNum == 0 ){
            totProb = 0;
        }else
            totProb = ((float) sucCont/(float) totNum)*100;

        //    Toast.makeText(TestActivity.this,"成功数"+sucCont+" 总时间"+totTime+" 总笔数"+totNum, Toast.LENGTH_LONG).show();

        Intent intent=new Intent();
        intent.setClass(getApplicationContext(), ExamDailActivity.class);
        Bundle b = new Bundle();
        b.putString("username",username);
        b.putString("level",level);
        b.putString("totnum",totNum+"");
        b.putString("usedtime",(usedtime/1000)+"");
        b.putString("totsucc",sucCont+"");
        b.putFloat("totprob",totProb);
        intent.putExtras(b);

        startActivity(intent);
        finish();

    }
    private void changeMillisInFuture(long time) {
        try {
            // 反射父类CountDownTimer的mMillisInFuture字段，动态改变定时总时间
            Class clazz = Class.forName("android.os.CountDownTimer");
            Field field = clazz.getDeclaredField("mMillisInFuture");
            field.setAccessible(true);
            field.set(timer, time);
        } catch (Exception e) {
            Log.e("Ye", "反射类android.os.CountDownTimer.mMillisInFuture失败： "+ e);
        }
    }
    private CountDownTimer timer = new CountDownTimer(100000, 1000) {
        String hms;

        @Override
        public void onTick(long millisUntilFinished) {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            hms = formatter.format(millisUntilFinished);
            if( millisUntilFinished<= 10000)
                time.setTextColor(Color.rgb(255, 0, 0));
            time.setText(hms);
        }

        @Override
        public void onFinish() {
            Toast.makeText(ExamActivity.this,"时间到！", Toast.LENGTH_LONG).show();
            finshTest();
        }
    };
    private void setTextView(){
        time = (TextView)findViewById(R.id.timer);
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
//           ┏┓　　　┏┓
//        ┏┛┻━━━┛┻┓
//       ┃　　　　　　　┃ 　
//      ┃　　　━　　　┃
//     ┃　┳┛　┗┳　┃
//    ┃　　　　　　　┃
//   ┃　　　┻　　　┃
//  ┃　　　　　　　┃
// ┗━┓　　　┏━┛
//     ┃　　　┃   神兽保佑　　　　　　　　
//    ┃　　　┃   代码无BUG！
//   ┃　　　┗━━━┓
//  ┃　　　　　　　┣┓
// ┃　　　　　　　┏┛
//┗┓┓┏━┳┓┏┛
// ┃┫┫　┃┫┫
//┗┻┛　┗┻┛
}
