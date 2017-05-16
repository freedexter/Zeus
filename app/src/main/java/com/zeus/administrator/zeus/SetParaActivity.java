package com.zeus.administrator.zeus;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zeus.administrator.database.QueryDataBaseAdapter;

public class SetParaActivity extends AppCompatActivity {
    private static final String STR_FORMAT = "00";
    private EditText hour,min,sec,count;
    private String shour,newhour,smin,newmin,ssec,newsec,scount,newcount;
    private Button comfirm,back;
    QueryDataBaseAdapter qd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_para);

        initSetView();

        initEdit();
        initButton();

    }
    private void initSetView(){
        hour = (EditText)findViewById(R.id.hour);
        min = (EditText)findViewById(R.id.min);
        sec = (EditText)findViewById(R.id.sec);
        count = (EditText)findViewById(R.id.count);
        comfirm = (Button)findViewById(R.id.comfirm);
        back = (Button)findViewById(R.id.back);

    }
    private void initEdit(){
        String time;
        String totcount;
        qd = new QueryDataBaseAdapter(this);
        time = qd.getLimtTimeSettings();
        totcount = qd.getCountSettings();

        shour = time.split(":")[0];
        hour.setText(shour);
        smin = time.split(":")[1];
        min.setText(smin);
        ssec = time.split(":")[2];
        sec.setText(ssec);
        scount = totcount;
        count.setText(scount);
    }

    private void initButton(){
        comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chkedit()){
                    buttonComfirm();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean chkedit(){
        int ihour=0,imin=0,isec=0,icount=0;

        newhour = hour.getText().toString();
        newmin = min.getText().toString();
        newsec = sec.getText().toString();
        newcount = count.getText().toString();
//Log.v("tag",newhour+" "+newmin+" "+newsec+" "+newcount);
        if( newhour.isEmpty() || newmin.isEmpty() || newsec.isEmpty() || newcount.isEmpty()){
            Toast.makeText(SetParaActivity.this,"输入项不能为空！", Toast.LENGTH_LONG).show();
            return false;
        }
        ihour = Integer.parseInt(newhour);
        newhour = String.format("%02d",ihour);
        imin = Integer.parseInt(newmin);
        newmin = String.format("%02d",imin);
        isec = Integer.parseInt(newsec);
        newsec = String.format("%02d",isec);
        icount = Integer.parseInt(newcount);

        if((ihour<0)||(imin<0||imin>59)||(isec<0||isec>59)){
            Toast.makeText(SetParaActivity.this,"时间输入有误！", Toast.LENGTH_LONG).show();
            return false;
        }

        if( imin > 0 || isec > 0 ){
            if(ihour >= 2) {
                Toast.makeText(SetParaActivity.this, "时间不能大于2小时！", Toast.LENGTH_LONG).show();
                return false;
            }
        }else{
            if(ihour>2){
                Toast.makeText(SetParaActivity.this,"时间不能大于2小时！", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        if(icount<0||icount>200){
            Toast.makeText(SetParaActivity.this,"个数输入有误！", Toast.LENGTH_LONG).show();
            return false;
        }
        if(newhour.equals(shour) && newmin.equals(smin) && newsec.equals(ssec)&& newcount.equals(scount) ){
            Toast.makeText(SetParaActivity.this,"请至少变更一项参数！", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
    private void buttonComfirm(){
        final String time;
        qd = new QueryDataBaseAdapter(this);
        time = newhour +":"+ newmin +":"+ newsec;
        Dialog alertDialog = new AlertDialog.Builder(this).
                setTitle("更新参数").
                setMessage("确认更新系统参数吗?").
                setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).
                setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Toast.makeText(ChgPwdActivity.this,username+" "+spwd1+" "+spwd2, Toast.LENGTH_LONG).show();
                        qd.updateSysParm( time,newcount);
                        Toast.makeText(SetParaActivity.this,"系统参数已更新！", Toast.LENGTH_LONG).show();
                    }
                }).create();
        alertDialog.show();
    }
}
