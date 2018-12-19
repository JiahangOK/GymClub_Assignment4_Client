package edu.bjtu.gymclub.gymclub;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import forcoach.Coach;
import forcoach.CoachAdapter;

public class CoachActivity extends AppCompatActivity {

//    private String[] data = { "Bailie", "Calvin", "Carolyn", "Catharine",
//            "Daisy", "Damon", "Kermit", "Jacob", "Cherry", "Tony","Nathan","Gale","Olivia","Shane","Owen" };
private List<Coach> coachList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach);

        //上方蓝色框
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


        //控制listview
        initCoaches();
        CoachAdapter adapter = new CoachAdapter
                (CoachActivity.this, R.layout.coach_member,coachList);
        ListView listView=(ListView)findViewById(R.id.coachlist);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Coach coach=coachList.get(position);

                SpannableString ss = new SpannableString(coach.getName());
                ss.setSpan(new ForegroundColorSpan(Color.RED), 0, 1,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                Toast toast = Toast.makeText(getApplicationContext(),ss, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();



            }
        });

         }



    private void initCoaches(){
        Coach c2=new Coach(" Jack ","He is a boxing coach",R.drawable.c2);
        coachList.add(c2);
        Coach c3=new Coach(" Carolyn ","She is a yoga coach",R.drawable.c3);
        coachList.add(c3);
        Coach c4=new Coach(" Daisy ","She is a boxing coach",R.drawable.c4);
        coachList.add(c4);
        Coach c5=new Coach( " Damon ","He is a shaping coach",R.drawable.c5);
        coachList.add(c5);

        Coach c7=new Coach(" Olivia ","She is a tennis coach",R.drawable.c7);
        coachList.add(c7);

        Coach c9=new Coach(" Tony ","He is a swimming coach",R.drawable.c9);
        coachList.add(c9);

        Coach c10=new Coach(" Jay ","He is a boxing coach",R.drawable.c2);
        coachList.add(c10);







    }

}
