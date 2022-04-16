package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class Daily extends AppCompatActivity {
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_daily);

        ImageButton btn_back = findViewById (R.id.btn_back);
        ImageButton btn_check = findViewById (R.id.btn_check);
        TextView tv_date = findViewById (R.id.tv_date);
        ListView lv_memolist = findViewById (R.id.lv_memolist);

        Intent getIntent = getIntent ();
        date = getIntent.getStringExtra ("date");
        tv_date.setText (date); // 선택된 날짜 출력

        btn_back.setOnClickListener (new View.OnClickListener () { // btn_back 클릭 시 Calendar화면으로 전환
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Daily.this,Calendar.class);
                startActivity (intent);
            }
        });

        btn_check.setOnClickListener (new View.OnClickListener () { // btn_check 클릭 시 Create화면으로 전환
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Daily.this,Create.class);
                intent.putExtra ("date",date);
                startActivity (intent);
            }
        });

    }
}