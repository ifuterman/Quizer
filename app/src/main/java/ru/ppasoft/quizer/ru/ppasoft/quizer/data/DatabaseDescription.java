package ru.ppasoft.quizer.ru.ppasoft.quizer.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ifuterman on 10.10.2016.
 * Wrapper for database
 */

class DatabaseDescription {
   private static final String AUTHORITY = "ru.ppasoft.quizer.data";
   private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    static final class QuizTable implements BaseColumns{//Teble for quiz settings
        static final String TABLE_NAME = "quiz";//Table name

        static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();//URI for table quiz

        static final String COLUMN_TITLE = "title_localization_id";
        static final String COLUMN_IMAGE = "image";
        static final String COLUMN_HIGHSCORE = "highscore";
        static final String COLUMN_ANSWERTYPE = "answer";
        static final String COLUMN_LOCALIZATIONID = "localization_id";
        static final String COLUMN_QUESTIONTYPE = "question";

        static String getCreationRequest(){
            String req;
            req = "PRAGMA foreign_keys = off;\n" +
                    "BEGIN TRANSACTION;\n" +
                    "DROP TABLE IF EXISTS" + TABLE_NAME + ";\n" +
                    "CREATE TABLE " + TABLE_NAME + "(" +
                    _ID + " integer primary key, " +
                    COLUMN_LOCALIZATIONID + " INTEGER REFERENCES " + LocalizationTable.TABLE_NAME + "(" +LocalizationTable._ID + "), " +
                    COLUMN_TITLE + " integer, " +
                    COLUMN_HIGHSCORE + " integer, " +
                    COLUMN_IMAGE + " BLOB, " +
                    COLUMN_ANSWERTYPE + " TEXT, " +
                    COLUMN_QUESTIONTYPE + " TEXT " +
                    ")\n" +
            "COMMIT TRANSACTION;\n" +
            "PRAGMA foreign_keys = on;";
            return req;
        }

    }

   static final class LocalizationTable implements BaseColumns{//Table for localizations
        static final String TABLE_NAME = "localization";//Table name

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();//URI for table quiz

       static final String COLUMN_ENGLISH = "en";
       static final String COLUMN_RUSSIAN = "rus";

       static String getCreationRequest() {//creation request
            String req;
            req = "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " integer primary key, " +
                    COLUMN_ENGLISH + " TEXT, " +
                    COLUMN_RUSSIAN + " TEXT" +
                    ")";
            return req;
        }
    }

}
