package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    private String title,content,radio;
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
                    radio = rbtn_campus.getText ().toString ();
                    Toast.makeText (Create.this,radio,Toast.LENGTH_SHORT).show ();
                } else {
                    radio = rbtn_life.getText ().toString ();
                    Toast.makeText (Create.this,radio,Toast.LENGTH_SHORT).show ();
                }
            }
        });

        btn_store.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                str_ratingBar = String.valueOf (ratingBar.getRating ());
                Toast.makeText (Create.this,str_ratingBar,Toast.LENGTH_SHORT).show ();
            }
        });
    }
}