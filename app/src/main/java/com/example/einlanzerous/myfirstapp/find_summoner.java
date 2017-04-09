package com.example.einlanzerous.myfirstapp;

import android.os.AsyncTask;
import android.widget.TextView;

/**
 * Created by Einlanzerous on 3/31/2017.
 */

class find_summoner extends AsyncTask<String, Void, String> {

    private static final String riotDev = System.getenv("RIOT_DEV");

    private TextView textView;

    public find_summoner(TextView textView) {
        this.textView = textView;
    }

    @Override
    protected String doInBackground(String... strings) {
        /*MongoClientURI mongoURI = new MongoClientURI("mongodb://ruler:Katarina7!@ds135700.mlab.com:35700/summonertest");

        MongoClient mongoClient = new MongoClient(mongoURI);

        DB db = mongoClient.getDB("summonertest");

        Set<String> collectionNames = db.getCollectionNames();

        //Document getSumm = collection.find(eq("summoner","Einlanzerous")).first();
        Log.i(collectionNames.toString(), "found");*/

        return "Yes";
    }

    @Override
    protected void onPostExecute(String temp) {
        textView.setText("Did it work? " + temp);
    }
}