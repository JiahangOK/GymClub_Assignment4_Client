package edu.bjtu.gymclub.gymclub;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.io.FileOutputStream;
import java.io.IOException;


import edu.bjtu.gymclub.gymclub.Entity.Config;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    Dialog dialog;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button signInBtn = (Button) findViewById(R.id.signInBtn);
        Button signUpBtn = (Button) findViewById(R.id.signUpBtn);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                EditText userNameEdit = (EditText) findViewById(R.id.login_username);
                EditText userPasswordEdit = (EditText) findViewById(R.id.login_password);

                String username = userNameEdit.getText().toString();
                String password = userPasswordEdit.getText().toString();
                String url = "http://"+ Config.HOST+":8080/user";
                getCheckFromServer(url, username, password);
                StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent();
                intent.setClass(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 将用户名和密码发送到服务器进行比对，若成功则跳转到app主界面，若错误则刷新UI提示错误登录信息
     *
     * @param url      服务器地址
     * @param username 用户名
     * @param password 密码
     */
    private void getCheckFromServer(String url, final String username, String password) {

        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("username", username);
        formBuilder.add("password", password);
        Request request = new Request.Builder().url(url).post(formBuilder.build()).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "服务器错误", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String res = null;
                        try {
                            res = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String jsoninfo = null;
                        if (res.equals("0")) {
                            Toast.makeText(MainActivity.this, "无此账号,请先注册", Toast.LENGTH_SHORT).show();
                        } else if (res.equals("1")) {
                            Toast.makeText(MainActivity.this, "密码不正确", Toast.LENGTH_SHORT).show();
                        } else//成功
                        {
                            //获取json信息
                            jsoninfo = res;
                            //界面跳转，传递json信息
                            Intent intent;
                            intent = new Intent();
                            intent.setClass(MainActivity.this, MainInterfaceActivity.class);
                            intent.putExtra("jsoninfo",jsoninfo);

                            startActivity(intent);
                            closeDialog();
                        }

                    }
                });
            }
        });

    }

    //显示等待框
    private void showDialog() {
        if (dialog == null) {
            dialog = LoadingDialog.createLoadingDialog(MainActivity.this, "登录中...");
            dialog.show();
        }

    }

    //关闭等待框
    private void closeDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.drawable_menu:
                Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }


}
