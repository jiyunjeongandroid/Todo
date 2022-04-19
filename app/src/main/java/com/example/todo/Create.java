package com.example.todo;

import static com.example.todo.Daily.REQUEST_CODE_INSERT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class Create extends AppCompatActivity {
    private String date;
    private EditText mTitleEditText, mContentEditText;
    private RadioGroup campus_life;
    private RadioButton rbtn_campus, rbtn_life;
    private String title, content, campus_or_life, importance; // 인텐트 받은 값
    private RatingBar ratingBar;
    private long mMemoId = -1; // id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_create);

        TextView tv_date = findViewById (R.id.tv_date);
        mTitleEditText = findViewById (R.id.et_title);
        mContentEditText = findViewById (R.id.et_content);
        campus_life = findViewById (R.id.campus_life);
        rbtn_campus = findViewById (R.id.rbtn_campus);
        rbtn_life = findViewById (R.id.rbtn_life);
        ratingBar = findViewById (R.id.ratingBar);

        Intent getIntent = getIntent ();

        if (getIntent != null) { // Daily에서 전환되었을 경우 해당 데이터를 출력함
            mMemoId = getIntent.getLongExtra ("id", -1);
            title = getIntent.getStringExtra ("title");
            content = getIntent.getStringExtra ("content");
            date = getIntent.getStringExtra ("date");
            campus_or_life = getIntent.getStringExtra ("campus_or_life");
            importance = getIntent.getStringExtra ("importance");

            mTitleEditText.setText (title);
            mContentEditText.setText (content);
            tv_date.setText (date);

            if (campus_or_life != null) {
                if (campus_or_life.equals ("CAMPUS")) {
                    rbtn_campus.setChecked (true);
                } else {
                    rbtn_life.setChecked (true);
                }

                if (importance != null) {
                    ratingBar.setRating (Float.parseFloat (importance));
                }
            }
        }

        ImageButton btn_back = findViewById (R.id.btn_back);
        btn_back.setOnClickListener (new View.OnClickListener () { // btn_bach 클릭 시 Daily화면으로 전환
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Create.this, Daily.class);
                intent.putExtra ("date",date);
                startActivity (intent);
            }
        });

        campus_life.setOnCheckedChangeListener (new RadioGroup.OnCheckedChangeListener () { // campus, life 버튼
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbtn_campus) {
                    campus_or_life = rbtn_campus.getText ().toString ();
                } else {
                    campus_or_life = rbtn_life.getText ().toString ();
                }
            }
        });

        ImageButton btn_store = findViewById (R.id.btn_store);
        btn_store.setOnClickListener (new View.OnClickListener () { // 저장 버튼 클릭 시 store함수로 저장
            @Override
            public void onClick(View v) {
                store ();
            }
        });
    }

    private void store() { // db에 저장
        String str_title = mTitleEditText.getText ().toString ();
        String str_content = mContentEditText.getText ().toString ();
        String str_ratingBar = String.valueOf (ratingBar.getRating ());

        ContentValues contentValues = new ContentValues ();
        contentValues.put (Memo.MemoEntry.COLUMN_NAME_TITLE, str_title);
        contentValues.put (Memo.MemoEntry.COLUMN_NAME_CONTENT, str_content);
        contentValues.put (Memo.MemoEntry.COLUMN_NAME_DATE, date);
        contentValues.put (Memo.MemoEntry.COLUMN_NAME_CAMPUS_OR_LIFE, campus_or_life);
        contentValues.put (Memo.MemoEntry.COLUMN_NAME_IMPORTANCE, str_ratingBar);

        SQLiteDatabase db = MemoDbHelper.getInstance (this).getWritableDatabase ();

        if (mMemoId == -1) {
            long newRowId = db.insert (Memo.MemoEntry.TABLE_NAME, null, contentValues);

            if (newRowId == -1) {
                Toast.makeText (this, "저장에 실패했습니다", Toast.LENGTH_SHORT).show ();
            } else {
                Toast.makeText (this, "저장 완료!", Toast.LENGTH_SHORT).show ();
                Intent intent = new Intent (Create.this,Daily.class);
                startActivity (intent);
            }
        } else {
            int count = db.update (Memo.MemoEntry.TABLE_NAME, contentValues,
                    Memo.MemoEntry._ID + " = " + mMemoId, null);
            if (count == 0) {
                Toast.makeText (this, "수정에 문제가 발생하였습니다", Toast.LENGTH_SHORT).show ();
            } else {
                Toast.makeText (this, "메모가 수정되었습니다", Toast.LENGTH_SHORT).show ();
            }
        }
    }
}