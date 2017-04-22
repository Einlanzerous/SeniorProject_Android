package com.example.einlanzerous.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        Bundle summoners = intent.getExtras();

        String summoner1 = summoners.getString("FirstSummoner");
        String summoner2 = summoners.getString("SecondSummoner");

        TextView textView2 = (TextView) findViewById(R.id.textView2);
        TextView textView = (TextView) findViewById(R.id.textView);
        new find_summoner(textView, summoner1).execute("test");
        TextView secondaryView = (TextView) findViewById(R.id.textView3);
        new find_summoner(secondaryView, summoner2).execute("test");

        textView2.setText(summoner1 + " versus " + summoner2);
    }
}
