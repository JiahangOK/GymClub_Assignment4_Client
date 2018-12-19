package edu.bjtu.gymclub.gymclub;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//返回
            }
        });

        Button button1=(Button) findViewById(R.id.b1);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SpannableString ss = new SpannableString("Tennis class");

                ss.setSpan(new ForegroundColorSpan(Color.RED), 0, 6,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                Toast toast = Toast.makeText(getApplicationContext(),ss, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
        Button button2=(Button) findViewById(R.id.b2);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SpannableString ss = new SpannableString("Yoga class");

                ss.setSpan(new ForegroundColorSpan(Color.RED), 0, 4,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                Toast toast = Toast.makeText(getApplicationContext(),ss, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });

        Button button3=(Button) findViewById(R.id.b3);
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SpannableString ss = new SpannableString("Boxing class");

                ss.setSpan(new ForegroundColorSpan(Color.RED), 0, 6,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                Toast toast = Toast.makeText(getApplicationContext(),ss, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });

        Button button4=(Button) findViewById(R.id.b4);
        button4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SpannableString ss = new SpannableString("Swimming class");

                ss.setSpan(new ForegroundColorSpan(Color.RED), 0, 8,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                Toast toast = Toast.makeText(getApplicationContext(),ss, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });

        Button button5=(Button) findViewById(R.id.b5);
        button5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SpannableString ss = new SpannableString("Climbing class");

                ss.setSpan(new ForegroundColorSpan(Color.RED), 0, 8,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                Toast toast = Toast.makeText(getApplicationContext(),ss, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });

        Button button6=(Button) findViewById(R.id.b6);
        button6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SpannableString ss = new SpannableString("PingPong class");

                ss.setSpan(new ForegroundColorSpan(Color.RED), 0, 12,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                Toast toast = Toast.makeText(getApplicationContext(),ss, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });

        Button button7=(Button) findViewById(R.id.b7);
        button7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SpannableString ss = new SpannableString("Football class");

                ss.setSpan(new ForegroundColorSpan(Color.RED), 0, 8,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                Toast toast = Toast.makeText(getApplicationContext(),ss, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }
}
