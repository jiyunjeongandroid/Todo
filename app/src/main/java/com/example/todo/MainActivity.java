package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
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

        ListView listView_campus = (ListView) findViewById (R.id.memo_list_campus);
        ListView listView_life = (ListView) findViewById (R.id.memo_list_life);
        ListView listView_soon = (ListView) findViewById (R.id.memo_list_soon);
        ListView listView_importance = (ListView) findViewById (R.id.memo_list_importance);

        Cursor cursor_campus = getMemoCursor_Campus(); // campus 리스트 출력
        Daily.mAdapter = new Daily.MemoAdapter (this, cursor_campus);
        listView_campus.setAdapter(Daily.mAdapter);

        Cursor cursor_life = getMemoCursor_Life(); // life 리스트 출력
        Daily.mAdapter = new Daily.MemoAdapter (this, cursor_life);
        listView_life.setAdapter(Daily.mAdapter);

        Cursor cursor_soon = getMemoCursor_Soon(); // 기한이 임박한 순으로 출력
        Daily.mAdapter = new Daily.MemoAdapter (this, cursor_soon);
        listView_soon.setAdapter(Daily.mAdapter);

        Cursor cursor_importance = getMemoCursor_Importance(); // 중요도 순으로 출력
        Daily.mAdapter = new Daily.MemoAdapter (this, cursor_importance);
        listView_importance.setAdapter(Daily.mAdapter);

    }

    private Cursor getMemoCursor_Campus() {
        MemoDbHelper dbHelper = MemoDbHelper.getInstance(this);
        return dbHelper.getReadableDatabase()
                .rawQuery("SELECT * FROM memo WHERE campus_or_life = 'CAMPUS'",null);
    }

    private Cursor getMemoCursor_Life() {
        MemoDbHelper dbHelper = MemoDbHelper.getInstance(this);
        return dbHelper.getReadableDatabase()
                .rawQuery("SELECT * FROM memo WHERE campus_or_life = 'LIFE'",null);
    }

    private Cursor getMemoCursor_Soon() {
        MemoDbHelper dbHelper = MemoDbHelper.getInstance(this);
        return dbHelper.getReadableDatabase()
                .rawQuery("SELECT * FROM memo ORDER BY date",null);
    }

    private Cursor getMemoCursor_Importance() {
        MemoDbHelper dbHelper = MemoDbHelper.getInstance(this);
        return dbHelper.getReadableDatabase()
                .rawQuery("SELECT * FROM memo ORDER BY importance DESC",null);
    }



}