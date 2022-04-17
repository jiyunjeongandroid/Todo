package com.example.todo;

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
    private RadioButton rbtn_campus,rbtn_life;
    private String title,content,campus_or_life;
    private RatingBar ratingBar;
    private String str_ratingBar;
    private long mMemoId = -1; // id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_create);

        TextView tv_date = findViewById (R.id.tv_date);
        ImageButton btn_back = findViewById (R.id.btn_back);
        ImageButton btn_store = findViewById (R.id.btn_store);
        mTitleEditText = findViewById (R.id.et_title);
        mContentEditText = findViewById (R.id.et_content);
        campus_life = findViewById (R.id.campus_life);
        rbtn_campus = findViewById (R.id.rbtn_campus);
        rbtn_life = findViewById (R.id.rbtn_life);
        ratingBar = findViewById (R.id.ratingBar);

        Intent getIntent = getIntent ();
        date = getIntent.getStringExtra ("date");
        tv_date.setText (date); // 선택된 날짜 출력

        btn_back.setOnClickListener (new View.OnClickListener () { // btn_bach 클릭 시 Daily화면으로 전환
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Create.this,Daily.class);
                startActivity (intent);
            }
        });

        campus_life.setOnCheckedChangeListener (new RadioGroup.OnCheckedChangeListener () { // campus / life 버튼
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rbtn_campus ) {
                    campus_or_life = rbtn_campus.getText ().toString ();
                    Toast.makeText (Create.this,campus_or_life,Toast.LENGTH_SHORT).show ();
                } else {
                    campus_or_life = rbtn_life.getText ().toString ();
                    Toast.makeText (Create.this,campus_or_life,Toast.LENGTH_SHORT).show ();
                }
            }
        });

        btn_store.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                str_ratingBar = String.valueOf (ratingBar.getRating ());
                Toast.makeText (Create.this,str_ratingBar,Toast.LENGTH_SHORT).show ();
                store();
            }
        });
    }

    private void store() { // db에 저장
        String title = mTitleEditText.getText ().toString ();
        String content = mContentEditText.getText ().toString ();

        ContentValues contentValues = new ContentValues ();
        contentValues.put (Memo.MemoEntry.COLUMN_NAME_TITLE,title);
        contentValues.put (Memo.MemoEntry.COLUMN_NAME_CONTENT,content);
        contentValues.put (Memo.MemoEntry.COLUMN_NAME_DATE,date);
        contentValues.put (Memo.MemoEntry.COLUMN_NAME_TITLE,campus_or_life);
        contentValues.put (Memo.MemoEntry.COLUMN_NAME_TITLE,str_ratingBar);

        SQLiteDatabase db = MemoDbHelper.getInstance (this).getWritableDatabase ();

        if(mMemoId == -1) {
            long newRowId = db.insert (Memo.MemoEntry.TABLE_NAME,null,contentValues);

            if(newRowId == -1) {
                Toast.makeText (this, "저장에 실패했습니다", Toast.LENGTH_SHORT).show ();
            } else {
                Toast.makeText (this, "저장 완료!", Toast.LENGTH_SHORT).show ();
                setResult (RESULT_OK);
            }
        }
    }
}