package ru.ppasoft.quizer;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import ru.ppasoft.quizer.ru.ppasoft.quizer.data.QuizerContentProvider;

/**
 * Created by ifuterman on 11.09.2016.
 * class for quiz handling
 */
class QuizManager {
    public final static String TAG_QUIZ = "quiz";
    public final static String TAG_LOCALSTRING = "localstring";
    public final static String TAG_LOCALIZATION = "localization";
    public final static String TAG_QUIZCOLLECTION = "quizcollection";
    public final static String TAGPROPERTY_KEY = "key";
    private HashMap<String, HashMap<String, String>> localizationMap;
    private HashMap<String, Quiz> mQuizMap;
    private QuizerDataWrapper dataWrapper = new QuizerDataWrapper();

    private QuizManager() {
        dataWrapper.initDataManager();

    }

    static QuizManager createQuizManager(Context context)//class factory
    {
        try {
            QuizManager manager = new QuizManager();
            return manager;
        } catch (Exception ex) {
            Log.wtf("QuizManager", "QuizManager (Context context) throws " + ex.toString());
        }
        return null;
    }
   /* protected boolean init(Context context) throws Exception {
        boolean res = true;
        localizationMap = new HashMap<>();
        mQuizMap = new HashMap<>();
        String str;
        //Loading quiz_manager.xml
        str = context.getString(R.string.path_quiz);
        String filePath = context.getFilesDir() + "/" + str + "/" + context.getString(R.string.filename_quiz_manager);
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        File file = new File(filePath);
        FileInputStream inputStream = new FileInputStream(file);
        InputStreamReader reader = new InputStreamReader(inputStream);
        parser.setInput(reader);
        //parsing quiz_manager.xml
        int event = parser.getEventType();
        while (event != XmlPullParser.END_DOCUMENT) {
            if (event == XmlPullParser.START_TAG) {
                str = parser.getName();
                switch (str) {
                    case TAG_QUIZCOLLECTION://Quiz info
                    {
                        parseQuizCollection(parser, context);
                        break;
                    }
                    case TAG_LOCALIZATION://Localization table
                    {
                        parseLocalization(parser);
                        break;
                    }
                }
            }
            event = parser.next();
        }
        reader.close();
        return res;
    }*/

    /*protected void parseLocalization(XmlPullParser parser) throws Exception    //pars localization section
    {
        String tag, property, key, value;
        key = "";
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
                if (tag.compareTo(TAG_LOCALSTRING) == 0) {
                    HashMap<String, String> mapPair = new HashMap<>();
                    count = parser.getAttributeCount();
                    for (int i = 0; i < count; i++) {
                        property = parser.getAttributeName(i);
                        value = parser.getAttributeValue(i);
                        if (property.compareTo(TAGPROPERTY_KEY) == 0)
                            key = value;
                        else
                            mapPair.put(property, value);
                    }
                    localizationMap.put(key, mapPair);
                }
            }
            else if (event == XmlPullParser.END_TAG && tag.compareTo(TAG_LOCALIZATION) == 0)
                flagStop = true;
            event = parser.next();
        }
    }

    protected void parseQuizCollection(XmlPullParser parser, Context context) throws Exception
    {
        String tag, property, key, value;
        key = "";
        boolean flagStop = false;
        int count = 0;
        int event = parser.getEventType();
        while (event != XmlPullParser.END_DOCUMENT && !flagStop)
        {
            tag = parser.getName();
            if (event == XmlPullParser.START_TAG) {
                if (tag == null) {
                    event = parser.next();
                    continue;
                }
                if (tag.compareTo(TAG_QUIZ) == 0)
                {
                    Quiz quiz = Quiz.createQuiz(parser,context);
                    if(quiz != null)
                        mQuizMap.put(quiz.getTitle(), quiz);
                }
            }
            else if (event == XmlPullParser.END_TAG && tag.compareTo(TAG_QUIZCOLLECTION) == 0)
                flagStop = true;
            event = parser.next();
        }
    }*/
    public Set<String> getQuizTitleSet()
    {
        return mQuizMap.keySet();
    }
    public Quiz getQuiz(String title)
    {
        Quiz res = null;
        if(mQuizMap.containsKey(title))
            res = mQuizMap.get(title);
        return res;
    }

    List<Quiz> asList() {
        return new ArrayList<>(mQuizMap.values());
    }

    /**
     * Created by ifuterman on 16.10.2016.
     */

    private class QuizerDataWrapper {
        QuizerContentProvider provider = new QuizerContentProvider();
        boolean initDataManager() {//Initialization of QuizManager
            boolean res = true;
            try {
                Cursor cursor = provider.getAllQuizInfo();
            }
            catch (Exception ex){
                Log.wtf("QuizerDataWrapper", "initDataManager", ex);
                res = false;
            }
            return res;
        }
    }
}
