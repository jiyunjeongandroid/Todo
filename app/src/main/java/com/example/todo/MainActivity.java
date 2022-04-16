package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TextView tv_Todolist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        Calendar calendar = Calendar.getInstance ();
        String today = new SimpleDateFormat ("yyyy/MM/dd").format(new Date ());// 오늘 날짜 출력
        TextView txt_date = findViewById (R.id.today);
        txt_date.setText (today);

        tv_Todolist = findViewById (R.id.todolist);
        tv_Todolist.setOnClickListener (new View.OnClickListener () { // TO DO LIST 텍스트 클릭 시 Calendar화면으로 전환
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainActivity.this, com.example.todo.Calendar.class);
                startActivity (intent);
            }
        });



    }
}