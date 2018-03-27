package com.example.android.CardViewConcept;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by user on 3/27/18.
 */

public class googleSearch extends Activity {


    String eText = Topic.TITLE_KEY;

    final String searchString = eText;

    // String searchUrl = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";
    String searchItem = searchString;
    //String searchQuery = searchUrl + searchItem;

    String searchStringNoSpaces = searchString.replace(" ", "+");

    // Your API key
    // TODO replace with your value
    String key = "AIzaSyBAysEcS4hd7ziphINYGWfzoF2-FA17aCA";

    // Your Search Engine ID
    // TODO replace with your value
    String cx = "005291217035565736318:teonhxgvluu";

    String urlString = "https://cse.google.com:443/cse/publicurl?cx=005291217035565736318:teonhxgvluu" + searchStringNoSpaces + "&key=" + key + "&cx=" + cx + "&alt=json";

    TextView results;
    String searchResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        results = findViewById(R.id.subtitleDetail);

        searchResult = results.toString();

        TextView topicTitle = findViewById(R.id.titleDetail);
        ImageView topicImage = findViewById(R.id.TopicImagesDetail);


        topicTitle.setText(getIntent().getStringExtra(Topic.TITLE_KEY));
        Glide.with(this).load(getIntent().getIntExtra(Topic.IMAGE_KEY, 0))
                .into(topicImage);

        new JsonSearchTask().execute();
    }

    private String sendQuery(String query) throws IOException {
        String result = "";
        URL sUrl = new URL(query);

        HttpURLConnection httpURLConnection = (HttpURLConnection) sUrl.openConnection();

        if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStreamReader inputStream = new InputStreamReader(httpURLConnection.getInputStream());

            BufferedReader bufferedReader = new BufferedReader(inputStream, 8192);

            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }

            bufferedReader.close();
        }

        return result;
    }

    private String ParseStringResult(String json) throws JSONException {
        String parsedResult = "";

        JSONObject jsonObject = new JSONObject(json);
        JSONObject jsonObject_responseData = jsonObject.getJSONObject("responseData");
        JSONArray jsonArray_result = jsonObject_responseData.getJSONArray("results");


        parsedResult += "Google Search for: " + searchItem + "\n";
        parsedResult += "Number of results returned = " + jsonArray_result.length() + "\n\n";

        for (int i = 0; i < jsonArray_result.length(); i++) {
            JSONObject jsonObject_i = jsonArray_result.getJSONObject(i);
            parsedResult += "title: " + jsonObject_i.getString("title") + "\n";
            parsedResult += "content: " + jsonObject_i.getString("content") + "\n";
            parsedResult += "url: " + jsonObject_i.getString("url") + "\n\n";
        }

        return parsedResult;
    }

    private class JsonSearchTask extends AsyncTask<Void, Void, Void> {
        String searchResultString = "";

        @Override
        protected Void doInBackground(Void... params) {
            try {
                searchResultString = ParseStringResult(sendQuery(urlString));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            results.setText(searchResultString);
            super.onPostExecute(aVoid);
        }
    }

}
