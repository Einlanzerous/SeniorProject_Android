package com.example.einlanzerous.myfirstapp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Einlanzerous on 3/31/2017.
 */

class find_summoner extends AsyncTask<String, Void, String> {

    private static final String riotDev = System.getenv("RIOT_DEV");
    private String TAG = MainActivity.class.getSimpleName();

    private TextView textView;

    public find_summoner(TextView textView) {
        this.textView = textView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        textView.setText("Asscessing Summoner Information");
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpHandler grab = new HttpHandler();

        //String url = "https://na.api.riotgames.com/api/lol/NA/v2.2/match/2464506643?api_key=RGAPI-825f93dd-bf9e-4bdc-acd7-690bc2ba0669";
        String url = "https://api.mlab.com/api/1/databases/seniorproject/collections/summoners?apiKey=zMgb9JjJAAXubWeCZVQGWOFaEzAcPT8h";
        String jsonStr = grab.makeServiceCall(url);

        Log.e(TAG, "Response from url request: " + jsonStr);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                JSONArray summonerInfo = jsonObj.getJSONArray("summonerInfo");

                for (int i = 0; i < summonerInfo.length(); i++){

                }
            } catch (final JSONException e) {
                Log.e(TAG, "JSON parsing error: " + e.getMessage());
            }
        }

        return "Yes";
    }

    @Override
    protected void onPostExecute(String temp) {
        textView.setText("Did it work? " + temp);
    }
}