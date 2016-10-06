package ru.ppasoft.quizer;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static String APPINITED = "key";
    public static QuizManager manager;
    private int MAX_RECURSION_DEPTH = 10;
    private int RECURSION_COUNTER_ASSETS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        init();
        // test();
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
    private void init() {
        Context context = this.getApplicationContext();
        String preferencesFileName = getString(R.string.filename_app_preferences);
        SharedPreferences preferences = context.getSharedPreferences(preferencesFileName, MODE_PRIVATE);
        boolean fInited = preferences.getBoolean(APPINITED, false);
        if (!fInited)
            createInitialFileStruct();//Creating initial file structure
        manager = QuizManager.createQuizManager(getApplicationContext());
    }

    //Creating initial files
    private void createInitialFileStruct() {
        Context context = this.getApplicationContext();
        try {
            String str = getString(R.string.path_quiz);
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
            if (!recursionFillPathFromAsset(pathList, str, assets))
                Log.wtf("MainActivity", "recursionFillPathFromAsset return false");
            String copyPath;
            String fileName;
            for (Pair<String, String> elem : pathList) {
                copyPath = elem.first;
                fileName = elem.second;
                InputStream is = assets.open(copyPath + "/" + fileName);
                path = new File(context.getFilesDir(), copyPath);
                if (!path.exists())
                    res = path.mkdirs();
                file = new File(path.getPath(), fileName);
                if (file.exists())
                    file.delete();
                res = file.createNewFile();
                if (!res)
                    Log.wtf("MainActivity", "MainActivity.createInitialFileStruct: file.createNewFile() return false");
                FileOutputStream os = new FileOutputStream(file, false);
                boolean flagStop = false;
                int readLength = 0;
                int readRes = 0;
                while (!flagStop) {
                    readLength = is.available();
                    if (readLength == 0)
                        flagStop = true;
                    else {
                        byte[] buf = new byte[readLength];
                        readRes = is.read(buf);
                        os.write(buf);
                        os.flush();
                    }
                }
                os.close();
                is.close();
            }

        } catch (Exception ex) {
            Log.e("MainActivity", "createInitialFileStruct()", ex);
        }


    }


    //Recursion for finding full paths and file names in assets section
    private boolean recursionFillPathFromAsset(List<Pair<String, String>> pairList, String currentPath, AssetManager assets) throws IOException {
        if (RECURSION_COUNTER_ASSETS > MAX_RECURSION_DEPTH)
            return false;
        RECURSION_COUNTER_ASSETS++;
        String[] list = assets.list(currentPath);
        boolean res = true;
        for (String elem : list) {
            if (elem.lastIndexOf('.') > -1) {
                Pair<String, String> pair = new Pair<String, String>(currentPath, elem);
                pairList.add(pair);
            } else {
                if (!recursionFillPathFromAsset(pairList, currentPath + "/" + elem, assets))
                    res = false;
            }
        }
        RECURSION_COUNTER_ASSETS--;
        return res;
    }
}
