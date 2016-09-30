package ru.ppasoft.quizer;

import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class MainActivity extends AppCompatActivity
{
    public static String APPINITED = "key";
    private int MAX_RECURSION_DEPTH = 10;
    private int RECURSION_COUNTER_ASSETS = 0;
    public QuizManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Init();
        test();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Initialisation of the app
    private void Init()

    {
        Context context = this.getApplicationContext();
        String preferencesFileName = getString(R.string.filename_app_preferences);
        SharedPreferences preferences = context.getSharedPreferences(preferencesFileName,context.MODE_PRIVATE);
        boolean fInited = false;
        fInited =  preferences.getBoolean(APPINITED, false);
        if(!fInited)
            CreateInitialFileStruct();//Creating initial file structure
       // manager = QuizManager.createQuizManager(getApplicationContext());
    }
    //Creating initial files
    private void CreateInitialFileStruct()
    {
        Context context = this.getApplicationContext();
        try
        {
            String str = "";
            str = getString(R.string.path_quiz);
            //Spelling directory structure
            File path = new File(context.getFilesDir(), str);
            boolean res = false;
            //if(!path.exists())
               // res = path.mkdirs();
            //Copy initial files from assets
            File file;
            AssetManager assets = getAssets();
            str = getString(R.string.path_assets_quiz);
            List<Pair<String, String>> pathList = new ArrayList<>();
            if(!recursionFillPathFromAsset(pathList,str,assets))
                Log.wtf("MainActivity","recursionFillPathFromAsset return false");
            String copyPath;
            String fileName;
            for (Pair<String, String>elem:pathList)
            {
                copyPath = elem.first;
                fileName = elem.second;
                InputStream is = assets.open(copyPath + "/" + fileName);
                path = new File(context.getFilesDir(), copyPath);
                if(!path.exists())
                    res = path.mkdirs();
                file = new File(path.getPath(), fileName);
                if(file.exists())
                    file.delete();
                res = file.createNewFile();
                if(!res)
                    Log.wtf("MainActivity", "MainActivity.CreateInitialFileStruct: file.createNewFile() return false");
                FileOutputStream os = new FileOutputStream(file,false);
                boolean flagStop = false;
                int readLength = 0;
                int readRes = 0;
                while(!flagStop)
                {
                    readLength = is.available();
                    if(readLength == 0)
                        flagStop = true;
                    else
                    {
                        byte[] buf = new byte[readLength];
                        readRes = is.read(buf);
                        os.write(buf);
                        os.flush();
                    }
                }
                os.close();
                is.close();
            }

        }
        catch (Exception ex)
        {
            Log.e("MainActivity", "CreateInitialFileStruct()", ex);
        }


    }

    //TODO
    private void test()
    {
        Context context = this.getApplicationContext();
        String str;
        //Loading quiz_manager.xml
        str = context.getString(R.string.path_quiz);
        String filePath = context.getFilesDir() + "/" + str + "/" + context.getString(R.string.filename_quiz_manager);

        File f = new File(filePath);

        if(f.exists()) {
            Log.i("FAFAFA", "ok");
        }else{
            Log.i("FAFAFA", "NOPE");
        }

        //XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        //XmlPullParser parser = factory.newPullParser();
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
        try {
            FileInputStream inputStream = new FileInputStream(filePath);

            int size = inputStream.available();
            inputStream.mark(0);
            //   inputStream.reset();
            size = inputStream.available();
            size = 0;
        }catch (Exception ex){
            Log.i("FAFAFA", ex.getLocalizedMessage());

        }
    }

    //Recursion for finding full paths and file names in assets section
    private boolean recursionFillPathFromAsset(List<Pair<String, String>> pairList, String currentPath, AssetManager assets) throws IOException {
        if (RECURSION_COUNTER_ASSETS > MAX_RECURSION_DEPTH)
            return false;
        RECURSION_COUNTER_ASSETS++;
        String[] list = assets.list(currentPath);
        boolean res = true;
        for(String elem:list)
        {
            if (elem.lastIndexOf('.') > -1 )
            {
                Pair <String, String>pair = new Pair<String, String>(currentPath, elem);
                pairList.add(pair);
            }
            else
            {
                if(!recursionFillPathFromAsset(pairList, currentPath + "/" + elem, assets))
                    res = false;
            }
        }
        RECURSION_COUNTER_ASSETS--;
        return res;
    }
}
