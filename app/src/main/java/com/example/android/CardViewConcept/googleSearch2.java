package com.example.android.CardViewConcept;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by user on 3/27/18.
 */

public class googleSearch2 extends Activity {
    /*
    This class requires use of google's custom search API

    The onCreate method borrows some properties from the detail activity class

    This class uses some concepts from a project found here
    https://github.com/fanysoft/Android_Google_Custom_SearchDemo
    */


    private static final String TAG = "searchApp";
    static String result = null;
    WebView results;
    ImageView iv;
    TextView title;
    TextView News;
    Integer responseCode = null;
    String responseMessage = "";


    //Search Subject
    //TODO find a way to referecne the other card titles
    String eText = "Fc Barcelona";


    //this string will help transition tha search subject into the URL when the time comes
    String searchStringNoSpaces = eText.replace(" ", "+");

    //Your API key
    String key = "AIzaSyBAysEcS4hd7ziphINYGWfzoF2-FA17aCA";

    //Search Engine ID
    String cx = "005291217035565736318:teonhxgvluu";

    //Build a Url using your own custom search url plus the strings we already initialized
    String urlString = "https://cse.google.com:443/cse/publicurl?&key=" + key + "&cx=" + cx + "&q=" + searchStringNoSpaces;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Log.d(TAG, "**** APP START ****");


        //reference views in layout_search
        title = findViewById(R.id.titleSearch);
        News = findViewById(R.id.newsTitleSearch);
        iv = findViewById(R.id.TopicImagesSearch);

        //reference to webview
        results = findViewById(R.id.subtitleSearch);


        //Get the drawable
        Drawable drawable = ContextCompat.getDrawable
                (this, getIntent().getIntExtra(Topic.IMAGE_KEY, 0));

        //Create a placeholder gray scrim while the image loads
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(Color.GRAY);

        //Make it the same size as the image
        if (drawable != null) {
            gradientDrawable.setSize(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }

        //extract the text from the intent extra
        title.setText(getIntent().getStringExtra(Topic.TITLE_KEY));
        Glide.with(this).load(getIntent().getIntExtra(Topic.IMAGE_KEY, 0))
                .into(iv);



        final String searchString = eText;
        Log.d(TAG, "Searching for : " + searchString);
        title.setText("Searching for : " + searchString);

        //makes sure we validate our URL in a try and catch
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            Log.e(TAG, "ERROR converting String to URL " + e.toString());
        }
        Log.d(TAG, "Url = " + urlString);


        // start AsyncTask
        GoogleSearchAsyncTask searchTask = new GoogleSearchAsyncTask();
        searchTask.execute(url);

    }

    private class GoogleSearchAsyncTask extends AsyncTask<URL, Integer, String> {


        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];
            Log.d(TAG, "AsyncTask - doInBackground, url=" + url);

            // Http connection
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                Log.e(TAG, "Http connection ERROR " + e.toString());
            }


            try {
                responseCode = conn.getResponseCode();
                responseMessage = conn.getResponseMessage();
            } catch (IOException e) {
                Log.e(TAG, "Http getting response code ERROR " + e.toString());
            }

            Log.d(TAG, "Http response code =" + responseCode + " message=" + responseMessage);

            try {

                if (responseCode == 200) {

                    // response OK

                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;

                    while ((line = rd.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    rd.close();

                    conn.disconnect();

                    result = sb.toString();

                    Log.d(TAG, "result=" + result);

                    return result;

                } else {

                    // response problem

                    String errorMsg = "Http ERROR response " + responseMessage + "\n" + "Make sure to replace in code your own Google API key and Search Engine ID";
                    Log.e(TAG, errorMsg);
                    result = errorMsg;
                    return result;

                }
            } catch (IOException e) {
                Log.e(TAG, "Http Response ERROR " + e.toString());
            }


            return null;
        }


        protected void onPostExecute(String result) {

            Log.d(TAG, "AsyncTask - onPostExecute, result=" + result);

            // show result
            // results.getUrl();

            News.setText("Learn more about:");
            //results.loadData(urlString,null,null);
            results.loadUrl(urlString);

        }
    }


}















