package edu.bjtu.gymclub.gymclub;

import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


import edu.bjtu.gymclub.gymclub.Entity.Config;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_Register);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//返回
            }
        });

        Button registOK = (Button) findViewById(R.id.submit_register);


        //点击注册按钮，做出相应事件，向服务器发送用户信息，进行验证
        registOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //电话号
                EditText userPhoneNumberEdit = (EditText) findViewById(R.id.et_phoneNumber);
                //电子邮箱
                EditText userEmailEdit = (EditText) findViewById(R.id.et_email);
                //用户名
                EditText userNameEdit = (EditText) findViewById(R.id.et_username);
                //密码
                TextInputEditText userPasswordEdit = (TextInputEditText) findViewById(R.id.tiet_password);
                //密码确认
                TextInputEditText userPasswordConfirmEdit = (TextInputEditText) findViewById(R.id.tiet_password_confirm);

                String userPhoneNumber = userPhoneNumberEdit.getText().toString();
                String userEmail = userEmailEdit.getText().toString();
                String userName = userNameEdit.getText().toString();
                String userPassword = userPasswordEdit.getText().toString();
                String userPasswordConfirm = userPasswordConfirmEdit.getText().toString();



                String url = "http://"+ Config.HOST+":8080/register";

                if(!checkEmail(userEmail)||!checkPhoneNumber(userPhoneNumber)||!checkUserName(userName)
                        ||!checkPasswords(userPassword,userPasswordConfirm)){
                    return;
                }

                registerNameWordToServer(url, userPhoneNumber, userEmail, userName, userPassword);


            }
        });


    }

    //验证邮箱是否合法
    private boolean checkEmail(String email){
        TextInputLayout til_email=findViewById(R.id.til_email);
        til_email.setErrorEnabled(true);
        String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";

        if(TextUtils.isEmpty(email)){
            showerror(til_email,"Email can not be empty!");
            return false;
        }else if(!email.matches(strPattern)){
            showerror(til_email,"Email is illegal!");
            return false;
        }else{
            til_email.setErrorEnabled(false);
            return true;
        }
    }

    //检查电话号
    private boolean checkPhoneNumber(String phoneNumber){
        TextInputLayout til_phoneNumber=findViewById(R.id.til_phoneNumber);
        til_phoneNumber.setErrorEnabled(true);

        String telRegex = "[1][34578]\\d{9}" ;

        if(TextUtils.isEmpty(phoneNumber)){
            showerror(til_phoneNumber,"Phone number can not be empty!");
            return false;
        }else if(!phoneNumber.matches(telRegex)){
            showerror(til_phoneNumber,"Phone number is not in a correct format!");
            return false;
        }
        else{
            til_phoneNumber.setErrorEnabled(false);
            return true;
        }
    }

    //检查用户名
    private boolean checkUserName(String name){
        TextInputLayout til_name=findViewById(R.id.til_username);
        til_name.setErrorEnabled(true);
        if(TextUtils.isEmpty(name)){
            showerror(til_name,"Username can not be empty!");
            return false;
        }else{
            til_name.setErrorEnabled(false);
            return true;
        }
    }
    //检查密码
    private boolean checkPasswords(String password,String password_con){

        TextInputLayout til_password=findViewById(R.id.til_password);
        TextInputLayout til_passwordCon=findViewById(R.id.til_password_confirm);

        til_password.setErrorEnabled(true);
        til_passwordCon.setErrorEnabled(true);
       if(password.length()<6){
            showerror(til_password,"Enter a password with at least 6 characters!");
            return false;
        } else if (!password.equals(password_con)){
            showerror(til_passwordCon,"The two passwords do not match!");
           til_password.setErrorEnabled(false);
            return false;
        }else{
           til_password.setErrorEnabled(false);
           til_passwordCon.setErrorEnabled(false);
            return true;
        }

    }

    //显示错误
    /**
     * 显示错误提示，并获取焦点
     * @param textInputLayout
     * @param error
     */
    private void showerror(TextInputLayout textInputLayout,String error){
        textInputLayout.setError(error);
        textInputLayout.getEditText().setFocusable(true);
        textInputLayout.getEditText().setFocusableInTouchMode(true);
        textInputLayout.getEditText().requestFocus();
    }


    //后端验证
    private void registerNameWordToServer(String url, String userPhoneNumber, String userEmail, String userName, String userPassword) {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBuilder = new FormBody.Builder();

        formBuilder.add("user_phone_number", userPhoneNumber);
        formBuilder.add("user_email", userEmail);
        formBuilder.add("username", userName);
        formBuilder.add("password", userPassword);

        Request request = new Request.Builder().url(url).post(formBuilder.build()).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegisterActivity.this,"服务器错误",Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String res = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (res.equals("0")) {
                            Toast.makeText(RegisterActivity.this,"regist failed!",Toast.LENGTH_SHORT).show();

                            //注册失败

                        } else {
                            //注册成功
                            Intent intent;
                            intent = new Intent();
                            intent.setClass(RegisterActivity.this,MainActivity.class);
                            startActivity(intent);
                        }

                    }
                });
            }
        });

    }


}
