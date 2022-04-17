package com.example.todo;

import android.provider.BaseColumns;

public class Memo {
    private Memo() { }

    public static class MemoEntry implements BaseColumns {
        public static final String TABLE_NAME = "memo";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_CAMPUS_OR_LIFE = "campus_or_life";
        public static final String COLUMN_NAME_IMPORTANCE = "importance";

    }
}
