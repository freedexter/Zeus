package com.zeus.administrator.zeus;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.zeus.administrator.database.QueryDataBaseAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/9 0009.
 */

public class ChgPwdActivity extends AppCompatActivity {

    private Spinner userspinner;
    ArrayAdapter<String> soureadapter=null;
    QueryDataBaseAdapter qd;
    private TextView pwd1,pwd2;
    private Button comfirm, back;
    String spwd1,spwd2,username;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chgpwd);

        initSpinner();
        pwd1 = (TextView) findViewById(R.id.newpassword1);
        pwd2 = (TextView) findViewById(R.id.newpassword2);

        comfirm = (Button) findViewById(R.id.button);
        back = (Button)findViewById(R.id.back);

        getButton();

    }

    private void initSpinner(){

        ArrayList< String > data_list = new ArrayList< String >();
        qd = new QueryDataBaseAdapter(this);
        data_list = qd.getUser();
        userspinner = (Spinner)findViewById(R.id.userspinner);
        //适配器
        soureadapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        soureadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        userspinner.setAdapter(soureadapter);

    }
    private void getButton(){

        comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( chkPwd()< 0 ){
                    return;
                }
                resetPwd();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private int chkPwd(){
        int ret = 0;
        spwd1 = pwd1.getText().toString();
        spwd2 = pwd2.getText().toString();
        username = userspinner.getSelectedItem().toString();
        if(username.isEmpty()){
            Toast.makeText(ChgPwdActivity.this,"请选择用户名称！", Toast.LENGTH_LONG).show();
            return -1;
        }

        if(spwd1.isEmpty()|| spwd2.isEmpty()){
            Toast.makeText(ChgPwdActivity.this,"请输入密码！", Toast.LENGTH_LONG).show();
            return -1;
        }
        if( !spwd2.equals(spwd1) ){
            Toast.makeText(ChgPwdActivity.this,"两次密码不同，请重新输入！", Toast.LENGTH_LONG).show();
            return -1;
        }

        return ret;
    }
    private  void  resetPwd(){
        qd = new QueryDataBaseAdapter(this);
        Dialog alertDialog = new AlertDialog.Builder(this).
                setTitle("重置密码").
                setMessage("确认重置用户"+username+"的密码?").
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
                        qd.updateUserPwd(username,spwd1);
                        Toast.makeText(ChgPwdActivity.this,username+"的密码已重置！", Toast.LENGTH_LONG).show();
                    }
                }).create();
        alertDialog.show();
    }
}

