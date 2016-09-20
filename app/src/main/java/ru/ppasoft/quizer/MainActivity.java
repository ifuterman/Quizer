package ru.ppasoft.quizer;

import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;

import android.content.Context;

public class MainActivity extends AppCompatActivity
{
    public static String APPINITED = "key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CreateInitialFileStruct();


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
        if(fInited)
            return;
        CreateInitialFileStruct();//Creating initial file structure
    }
    //Creating initial files
    private void CreateInitialFileStruct()
    {
        Context context = this.getApplicationContext();
        AssetManager assets = getAssets();
        try
        {
            String str = "";
            str = getString(R.string.path_quiz);
            //Spelling directory structure
            File path = new File(context.getFilesDir(), str);
            boolean res = false;
            if(!path.exists())
                res = path.mkdirs();
            //Copy initial files from assets
            File file = new File(path, getString(R.string.filename_quiz_manager));
            if(file.exists())
                file.delete();
            res = file.createNewFile();
            if(!res)
                Log.wtf("MainActivity", "MainActivity.CreateInitialFileStruct: file.mkdirs return false");

            XmlResourceParser xmlRes = getResources().getXml(R.xml.quiz_manager);
            str = xmlRes.getText();
            FileWriter writer = new FileWriter(file,false);
            writer.write(str);
            writer.close();

        }
        catch (Exception ex)
        {
            Log.e("MainActivity", "CreateInitialFileStruct()", ex);
        }
    }
}
