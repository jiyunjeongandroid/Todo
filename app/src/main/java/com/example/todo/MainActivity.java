package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        Calendar calendar = Calendar.getInstance (); // 오늘 날짜 출력
        String today = new SimpleDateFormat ("yyyy/MM/dd").format(new Date ());
        TextView txt_date = findViewById (R.id.today);
        txt_date.setText (today);
    }
}