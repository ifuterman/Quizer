package ru.ppasoft.quizer.ru.ppasoft.quizer.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ifuterman on 10.10.2016.
 *
 */

class QuizerDataHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "quizer.db";
    private static final int DATABASE_VERSION = 1;
    QuizerDataHelper(Context context){
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
}
    @Override
    public void onCreate(SQLiteDatabase db){
        //Request for table creation
        String request;
        request = DatabaseDescription.LocalizationTable.getCreationRequest();
        db.execSQL(request);
        request = DatabaseDescription.QuizTable.getCreationRequest();
        db.execSQL(request);
    }
    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
