package com.example.todo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Carousel;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Daily extends AppCompatActivity {
    public static final int REQUEST_CODE_INSERT = 1000;
    public static MemoAdapter mAdapter;
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

        Cursor cursor = getMemoCursor();
        mAdapter = new MemoAdapter (this, cursor); // Cursor로 ListView에 데이터 출력
        lv_memolist.setAdapter (mAdapter);

        lv_memolist.setOnItemClickListener (new AdapterView.OnItemClickListener () { // ListView의 아이템을 클릭 시 Create화면에 해당 데이터를 출력해줌
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent (Daily.this,Create.class);
                Cursor cursor = (Cursor) mAdapter.getItem (position);

                String title = cursor.getString (cursor.getColumnIndexOrThrow (Memo.MemoEntry.COLUMN_NAME_TITLE));
                String content = cursor.getString (cursor.getColumnIndexOrThrow (Memo.MemoEntry.COLUMN_NAME_CONTENT));
                String date = cursor.getString (cursor.getColumnIndexOrThrow (Memo.MemoEntry.COLUMN_NAME_DATE));
                String campus_or_life = cursor.getString (cursor.getColumnIndexOrThrow (Memo.MemoEntry.COLUMN_NAME_CAMPUS_OR_LIFE));
                String importance = cursor.getString (cursor.getColumnIndexOrThrow (Memo.MemoEntry.COLUMN_NAME_IMPORTANCE));

                intent.putExtra ("id", id);
                intent.putExtra ("title",title);
                intent.putExtra ("content",content);
                intent.putExtra ("date",date);
                intent.putExtra ("campus_or_life",campus_or_life);
                intent.putExtra ("importance",importance);

                startActivity (intent);
            }
        });

        lv_memolist.setOnItemLongClickListener (new AdapterView.OnItemLongClickListener () { // ListView의 아이템을 롱클릭 시 해당 데이터를 삭제
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final long deleteId = id;
                AlertDialog.Builder builder = new AlertDialog.Builder (Daily.this);
                builder.setTitle ("메모 삭제");
                builder.setMessage ("메모를 삭제하시겠습니까?");
                builder.setPositiveButton ("삭제", new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = MemoDbHelper.getInstance (Daily.this).getWritableDatabase ();
                        int deletedCount = db.delete (Memo.MemoEntry.TABLE_NAME,
                                Memo.MemoEntry._ID + " = " + deleteId, null);
                        if(deletedCount == 0) {
                            Toast.makeText (Daily.this, "삭제에 실패했습니다", Toast.LENGTH_SHORT).show ();
                        } else {
                            mAdapter.swapCursor (getMemoCursor ());
                            Toast.makeText (Daily.this, "삭제되었습니다", Toast.LENGTH_SHORT).show ();
                        }
                    }
                });
                builder.setNegativeButton ("취소", null);
                builder.show ();
                return true;
            }
        });
    }

    private Cursor getMemoCursor() {
        MemoDbHelper dbHelper = MemoDbHelper.getInstance (this);
        return dbHelper.getReadableDatabase ().rawQuery ("SELECT * FROM memo WHERE date = ?", new String[] { date });
    }

    static class MemoAdapter extends CursorAdapter {
        public MemoAdapter(Context context, Cursor cursor) {
            super (context, cursor, false);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from (context)
                    .inflate (android.R.layout.simple_list_item_1, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView titleView = view.findViewById (android.R.id.text1);
            titleView.setText (cursor.getString (cursor.getColumnIndexOrThrow (Memo.MemoEntry.COLUMN_NAME_TITLE)));
            titleView.setTextColor (Color.parseColor ("#6d5972"));
        }
    }
}