package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Calendar extends AppCompatActivity {
    public CalendarView mCalendarView;
    public TextView selectedday;
    public String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_calendar);

        mCalendarView = findViewById (R.id.calendar);
        selectedday = findViewById (R.id.tv_date);
        ImageButton btn_home = findViewById (R.id.btn_home);
        ImageButton btn_more = findViewById (R.id.btn_more);

        date = new SimpleDateFormat ("yyyy/MM/dd").format (new Date ()); // 캘린더에서 날짜를 선택하지 않았을 경우에는 오늘 날짜 출력

        selectedday.setText (date);

        mCalendarView.setOnDateChangeListener (new CalendarView.OnDateChangeListener () { // 캘린더에서 선택된 날짜 출력
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                if(month > 10) {
                    date = year + "/" + (month + 1) + "/" + dayOfMonth;
                } else if(month < 10){
                    date = year + "/0" + (month + 1) + "/" + dayOfMonth;
                }
                ListView lv_calendar = findViewById (R.id.lv_calendar);
                Cursor cursor_campus = getMemoCursor_Calendar ();
                Daily.mAdapter = new Daily.MemoAdapter (Calendar.this, cursor_campus);
                lv_calendar.setAdapter(Daily.mAdapter);
                selectedday.setText (date);
            }
        });

        btn_home.setOnClickListener (new View.OnClickListener () { // btn_back 클릭 시 MainActivity로 전환
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Calendar.this, MainActivity.class);
                startActivity (intent);
            }
        });

        btn_more.setOnClickListener (new View.OnClickListener () { // btn_check 클릭 시 Daily로 전환
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Calendar.this, Daily.class);
                intent.putExtra ("date", date);
                startActivity (intent);
            }
        });
    }

    private Cursor getMemoCursor_Calendar() {
        MemoDbHelper dbHelper = MemoDbHelper.getInstance(this);
        return dbHelper.getReadableDatabase()
                .rawQuery("SELECT * FROM memo WHERE date = ?", new String[] { date });
    }
}