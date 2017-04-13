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

    private String TAG = MainActivity.class.getSimpleName();

    private String summonerName = "";
    private double summonerScore = 0;
    private int summonerKills = 0;
    private int summonerDeaths = 0;

    private TextView textView;

    public find_summoner(TextView textView) {
        this.textView = textView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        textView.setText("Accessing Summoner Information");

    }

    @Override
    protected String doInBackground(String... strings) {
        HttpHandler grab = new HttpHandler();

        String url = "https://api.mlab.com/api/1/databases/seniorproject/collections/summoners?apiKey=zMgb9JjJAAXubWeCZVQGWOFaEzAcPT8h";
        String jsonStr = grab.makeServiceCall(url);

        //Log.e(TAG, "Response from url request: " + jsonStr);

        if (jsonStr != null) {
            try {
                JSONArray summonerInfo = new JSONArray(jsonStr);
                JSONObject summonerDetails = summonerInfo.getJSONObject(0);

                summonerName = summonerDetails.getString("summoner");
                summonerScore = summonerDetails.getDouble("score");
                summonerKills = summonerDetails.getInt("kills");
                summonerDeaths = summonerDetails.getInt("deaths");

                Log.e(TAG, "Name attempt is: " + summonerName + " | with a score of: " + summonerScore + " with " + summonerKills + " kills.");
            } catch (final JSONException e) {
                Log.e(TAG, "JSON parsing error: " + e.getMessage());
            }
        }

        return "Yes";
    }

    @Override
    protected void onPostExecute(String temp) {
        textView.setText("Did it work? " + temp + "\nLooked up " + summonerName +"\n Found score of: " + summonerScore);
    }
}