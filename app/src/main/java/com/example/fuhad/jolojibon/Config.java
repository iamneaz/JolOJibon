package com.example.fuhad.jolojibon;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Belal on 11/23/2015.
 */
public class Config {
    public static final String UPLOAD_URL= "http://www.kipailam.com/water/upload.php";//"http://192.168.1.102/mcme/upload.php";
    public static final String DATA_URL = "http://www.kipailam.com/water/connection.php";
    public static final String MAP_URL = "http://www.kipailam.com/water/map/index.php";
    public static final String APP_URL = "http://www.kipailam.com/app/check.php";
    public static final String APP_DOWNLOAD_URL = "http://www.kipailam.com/app/mcme.apk";
    public static final String PIECHART = "http://www.kipailam.com/pichart.php";
    public static final String NEWS_FEED = "http://www.kipailam.com/water/news_feed/news_feed.php";
    public static final String USER_REGISTER = "http://kipailam.com/water/userRegister.php";
    public static final String USER_LOGIN = "http://kipailam.com/water/userLogin.php";
    public static final String REPORT = "https://docs.google.com/forms/d/1Les5Qg6cCqYmz5W-gIqXja7JpSE0ktnAmMIXSCNVn10";
    public static final String GAME = "http://kipailam.com/water/game/game.html";
    public static final String Community = "http://kipailam.com/water/community/news_feed.php";

    public static final String TAG_URL = "url";
    public static final String TAG_SIZE = "size";

    public static void WriteToFile(String str) {
        //Context context = this;

//        try {
//            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
//            outputStreamWriter.write(str);
//            Toast.makeText(this,str,Toast.LENGTH_LONG).show();
//
//            outputStreamWriter.close();
//        } catch (IOException e) {
//            Log.e("Exception", "File write failed: " + e.toString());
//        }
        try {
            File root = Environment.getExternalStorageDirectory();
            if (root.canWrite()){
                File gpxfile = new File(root, "mcmepi.txt");
                if(gpxfile.exists())
                {
                    String line="\n\n"+str;
//                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("MyCityMyEnvironmentData.txt", Context.MODE_APPEND));
//                    outputStreamWriter.write(str);
//                    outputStreamWriter.close();

                    gpxfile.createNewFile();
                    FileWriter gpxwriter = new FileWriter(gpxfile);
                    BufferedWriter out = new BufferedWriter(gpxwriter);
                    out.write(str);
                    out.close();
                    Log.i("working:", "Could not write file " );
                    //Toast.makeText(,"appeding data",Toast.LENGTH_LONG).show();
                }
            }
        } catch (IOException e) {
            Log.e("exception:", "Could not write file " + e.getMessage());
        }
    }
    public static String ReadFile(){
        File sdcard = Environment.getExternalStorageDirectory();

        File file = new File(sdcard,"mcmepi.txt");

//Read text from file
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
            }
            br.close();
            Log.i("working:", "Could read file ");
            return String.valueOf(text);
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
            Log.e("exception:", "Could not read file " + e.getMessage());
            return null;
        }
    }
}
