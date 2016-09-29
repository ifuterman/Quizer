package ru.ppasoft.quizer;

import android.content.Context;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by ifuterman on 11.09.2016.
 */
public class QuizManager
{
    public final static String TAG_QUIZ = "quiz";
    private HashMap<String, Quiz> mQuizMap;
    public static QuizManager createQuizManager(Context context)
    {
        try
        {
            QuizManager manager = new QuizManager(context);
            return manager;
        }
        catch (Exception ex)
        {
            Log.wtf("QuizManager", "QuizManager (Context context) throws " + ex.toString());
        }
        return null;
    }
    private QuizManager (Context context) throws Exception
    {
        String str;
        //Loading quiz_manager.xml
        str = context.getString(R.string.path_quiz);
        String filePath = context.getFilesDir() + "/" + str + "/" + context.getString(R.string.filename_quiz_manager);
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
      /*  File file = new File(filePath);
        int res;
        boolean f;
        f = file.exists();
        f = file.isFile();
        f = file.canRead();
        FileInputStream inputStream = new FileInputStream(file);
        int size = inputStream.available();
        byte[] buf = new byte[size];
        res = inputStream.read(buf);
        str = new String(buf);
        InputStreamReader reader = new InputStreamReader(inputStream);

        parser.setInput(reader);
        int event = parser.getEventType();
        while (event != XmlPullParser.END_DOCUMENT)
        {
            str = parser.getName();
            event = parser.next();
        }
        event = 0;*/
        FileInputStream inputStream = new FileInputStream(filePath);
        int size = inputStream.available();
        inputStream.mark(0);
     //   inputStream.reset();
        size = inputStream.available();
        size = 0;
    }
}
