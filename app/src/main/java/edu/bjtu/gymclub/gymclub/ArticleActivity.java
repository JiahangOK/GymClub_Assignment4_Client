package edu.bjtu.gymclub.gymclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ArticleActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_detail_content);
        TextView title = findViewById(R.id.title);
        TextView content = findViewById(R.id.content);

        Intent intent;
        intent=getIntent();

        String article_name = intent.getStringExtra("article_name");
        String article_content = intent.getStringExtra("article_content");
        title.setText(article_name);
        content.setText(article_content);
        //添加toolbar
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar_article);
        setSupportActionBar(toolbar);

        //设置toolbar中的返回按钮
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//返回
            }
        });

    }
}
