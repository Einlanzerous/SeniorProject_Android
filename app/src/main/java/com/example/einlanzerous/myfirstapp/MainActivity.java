package com.example.einlanzerous.myfirstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.content.Intent;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.einlanzerous.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.summoner1);
        EditText summoner2 = (EditText) findViewById(R.id.summoner2);
        String primarySummoner = editText.getText().toString();
        String secondarySummoner = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, primarySummoner);
        startActivity(intent);
    }
}
