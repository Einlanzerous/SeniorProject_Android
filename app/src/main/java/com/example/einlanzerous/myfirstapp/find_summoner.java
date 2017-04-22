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

    public String getSummonerName() {
        return summonerName;
    }

    public double getSummonerScore() {
        return summonerScore;
    }

    private String summonerName = "";
    private double summonerScore = 0;
    private double summonerCSPG = 0;
    private double summonerCSPM = 0;
    private double summonerTime = 0;
    private int summonerKills = 0;
    private int summonerDeaths = 0;
    private int summonerAssists = 0;
    private int summonerLevel = 0;
    private int summonerDbl = 0;
    private int summonerTrp = 0;
    private int summonerQuad = 0;
    private int summonerPenta = 0;
    private int summonerMP = 0;
    private int firstSummonerIndex = -1;
    private int secondSummonerIndex = -1;

    private TextView textView;

    public find_summoner(TextView textView, String summonerName) {
        this.summonerName = summonerName;
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

        if (jsonStr != null) {
            try {
                JSONArray summonerInfo = new JSONArray(jsonStr);
                Log.e(TAG, "Looking for: " + this.summonerName);

                for (int i = 0; i < summonerInfo.length(); i++){
                    JSONObject verifySummoner = summonerInfo.getJSONObject(i);
                    String testName = verifySummoner.getString("summoner").toLowerCase();

                    if (testName.equalsIgnoreCase(summonerName)){
                        Log.e(TAG, "Found summoner at index: " + i);
                        firstSummonerIndex = i;
                        break;
                    }
                }

                if (firstSummonerIndex >= 0) {

                    generateSummonerStats(firstSummonerIndex, summonerInfo);

                    Log.e(TAG, "Name attempt is: " + summonerName + " | with a score of: " + summonerScore + " with " + summonerKills + " kills.");
                }

                else {
                    return "No";
                }
            } catch (final JSONException e) {
                Log.e(TAG, "JSON parsing error: " + e.getMessage());
                return "No";
            }
        }

        return "Yes";
    }

    @Override
    protected void onPostExecute(String temp) {
        if (firstSummonerIndex < 0) {
            textView.setText("Error looking up " + summonerName + ", not found in database.");
        }
        else {
            textView.setText("Looked up " + summonerName +
                    "\nFound score of: " + summonerScore + "\nBased on;\n\tKills: " + summonerKills +
                    "\n\tDeaths: " + summonerDeaths + "\n\tAssists: " + summonerAssists +
                    "\n\tCS per Game: " + summonerCSPG + "\n\tCS per minute: " + summonerCSPM +
                    "\n\tDouble Kills: " + summonerDbl + "\n\tTriple Kills: " + summonerTrp +
                    "\n\tQuadra Kills: " + summonerQuad + "\n\tPenta Kills: " + summonerPenta +
                    "\n\tMatches polled: " + summonerMP + "\n\tAverage Time: " + summonerTime);
        }
    }

    public JSONObject generateSummonerStats(int summonerIndex, JSONArray summonerInfo) {
        try {
            JSONObject summonerDetails = summonerInfo.getJSONObject(summonerIndex);

            summonerName = summonerDetails.getString("summoner");
            summonerScore = summonerDetails.getDouble("score");
            summonerCSPG = summonerDetails.getJSONObject("cs").getDouble("per_game");
            summonerCSPM = summonerDetails.getJSONObject("cs").getDouble("per_minute");
            summonerKills = summonerDetails.getInt("kills");
            summonerDeaths = summonerDetails.getInt("deaths");
            summonerAssists = summonerDetails.getInt("assists");
            summonerDbl = summonerDetails.getInt("dbl_kills");
            summonerTrp = summonerDetails.getInt("trp_kills");
            summonerQuad = summonerDetails.getInt("quad_kills");
            summonerPenta = summonerDetails.getInt("penta_kills");
            summonerMP = summonerDetails.getInt("matches_polled");
            summonerTime = summonerDetails.getDouble("avg_time");

            return summonerDetails;
        }

        catch (final JSONException e) {
            Log.e(TAG, "JSON parsing error: " + e.getMessage());
        }

        return null;
    }
}