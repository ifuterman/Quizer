package ru.ppasoft.quizer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

/**
 * Created by ifuterman on 11.09.2016.
 * Класс викторины
 */

class Quiz
{
    private final static String TAG_QUIZ = "quiz";
    private final static String TAGPROPERTY_TITLE = "title";
    private final static String TAGPROPERTY_IMAGE = "image";
    private final static String TAGPROPERTY_PATH = "path";
    private String quizTitle;//Title
    private Drawable quizDrawable;//title image
    private ArrayList<Question> quizQuestions;//Questions
    public String getTitle()
    {
        return quizTitle;
    }
    Drawable getQuizDrawable() {return quizDrawable;}

    public static Quiz createQuiz(XmlPullParser parser, Context context)
    {
        Quiz quiz = null;
        try
        {
            quiz = new Quiz(parser, context);
        }
        catch (Exception ex)
        {
            Log.wtf("Quiz.crateQuiz", "Quiz constructor throw exception", ex);
            return null;
        }
        return quiz;
    }
    private Quiz(XmlPullParser parser, Context context) throws Exception
    {
        String tag, property, value;
        String title = "";
        String image = "";
        String path = "";
        boolean flagStop = false;
        int count = 0;
        int event = parser.getEventType();
        while (event != XmlPullParser.END_DOCUMENT && !flagStop) {
            tag = parser.getName();
            if (event == XmlPullParser.START_TAG) {
                if (tag == null) {
                    event = parser.next();
                    continue;
                }
                if (tag.compareTo(TAG_QUIZ) == 0)
                {
                    count = parser.getAttributeCount();
                    for (int i = 0; i < count; i++)
                    {
                        property = parser.getAttributeName(i);
                        value = parser.getAttributeValue(i);
                        switch(property)
                        {
                            case TAGPROPERTY_TITLE:
                            {
                                title = value;
                                break;
                            }
                            case TAGPROPERTY_IMAGE:
                            {
                                image = value;
                                break;
                            }
                            case TAGPROPERTY_PATH:
                            {
                                path = value;
                                break;
                            }
                        }
                    }
                    String imagePath = context.getFilesDir() + "/" +
                            context.getString(R.string.path_quiz) + "/" + path + "/" + image;
                    //Loading quiz image
                    Bitmap bmp = BitmapFactory.decodeFile(imagePath);
                    quizDrawable = new BitmapDrawable(context.getResources(), bmp);
                    quizTitle = title;
                }
            }
            else if (event == XmlPullParser.END_TAG && tag.compareTo(TAG_QUIZ) == 0)
                flagStop = true;
            event = parser.next();
        }
    }

}
