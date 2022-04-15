package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
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
        selectedday = findViewById (R.id.tv_day);
        date = new SimpleDateFormat ("yyyy/MM/dd").format (new Date ()); // 캘린더에서 날짜를 선택하지 않았을 경우에는 오늘 날짜 출력

        selectedday.setText (date);
        mCalendarView.setOnDateChangeListener (new CalendarView.OnDateChangeListener () { // 캘린더에서 선택된 날짜 출력
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                date = year + "/" + (month + 1) + "/" + dayOfMonth;
                selectedday.setText (date);
            }
        });
    }
}