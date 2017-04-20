package com.zeus.administrator.zeus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.zeus.administrator.database.LoginDataBaseAdapter;

public class LoginActivity extends AppCompatActivity {
    EditText editText;
    String sUsername=null,sPassword=null;
    private Button btlogin;
    CheckBox cb ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getuser();
        btlogin = (Button) findViewById(R.id.button1);
        btlogin.setOnClickListener (new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                editText = (EditText) findViewById(R.id.username);
                sUsername=editText.getText().toString();
                editText = (EditText) findViewById(R.id.password);
                sPassword=editText.getText().toString();

                // TODO Auto-generated method stub
                if( sUsername.isEmpty() ){
                    Toast.makeText( LoginActivity.this, "请输入用户名" , Toast.LENGTH_SHORT).show();
                }else {
                    if( sPassword.isEmpty()){
                        Toast.makeText( LoginActivity.this, "请输入密码" , Toast.LENGTH_SHORT).show();
                    }else {
                        //  Log.v("tag",Environment.getExternalStorageDirectory().getAbsolutePath());
                        //Log.v("tag",getExternalFilesDir("exter_test").getAbsolutePath());

                        LoginIn( sUsername, sPassword );
                    }
                }
            }
        });
    }
    public void getuser(){
        LoginDataBaseAdapter lg =new LoginDataBaseAdapter( this );
        cb = (CheckBox)this.findViewById(R.id.checkBox);
        sUsername = lg.getRemberUser();
        if(sUsername!=null){
            editText = (EditText) findViewById(R.id.username);
            editText.setText(sUsername.trim());
            cb.setChecked(true);
            editText = (EditText) findViewById(R.id.password);
            editText.requestFocus();
        }else {
            cb.setChecked(false);
            editText = (EditText) findViewById(R.id.username);
            editText.requestFocus();
        }
    }


    public void remberUser(){
        LoginDataBaseAdapter lg =new LoginDataBaseAdapter( this );
        cb = (CheckBox)this.findViewById(R.id.checkBox);
        if( cb.isChecked() ){
            lg.remberUser(sUsername);
        }else{
            lg.unremberUser(sUsername);
        }

    }

    public void LoginIn(String username,String password){
        String stat;

        LoginDataBaseAdapter lg =new LoginDataBaseAdapter( this );
        //  Log.v("tag",username+password);
        stat = lg.login(username, password);
        if( !stat.equals("False") ){
            Toast.makeText( LoginActivity.this, "Welcome "+username , Toast.LENGTH_SHORT).show();
            remberUser();
            starAct(stat);
        }else{
            Toast.makeText(LoginActivity.this, "密码不正确！", Toast.LENGTH_SHORT).show();
        }

    }

    private void starAct(String stat){
        Intent i=new Intent();
        i.setClass(getApplicationContext(), MainActivity.class);
        Bundle b = new Bundle();
        b.putString("username",sUsername);
        b.putString("stat",stat);
        i.putExtras(b);

        startActivity(i);
        finish();

    }


}
