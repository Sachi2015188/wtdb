package com.example.administrator.walkerstoursapp;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.administrator.walkerstoursapp.Depature.Main2Activity;

/**
 * Created by Administrator on 11/28/2017.
 */

public class WelcomeActivity extends Activity {
    Button button3;
    Button button4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from activity_main.xml
        setContentView(R.layout.activity_welcome);

        // Locate the button in activity_main.xml
        button3 = (Button) findViewById(R.id.SeArr);
        button4 = (Button) findViewById(R.id.SeDep);

        // Capture button clicks
        button3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(WelcomeActivity.this, MainActivity.class);
                // currentContext.startActivity(activityChangeIntent);
                WelcomeActivity.this.startActivity(activityChangeIntent);
            }
        });

        button4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Start NewActivity.class
                Intent myIntent1 = new Intent(WelcomeActivity.this, Main2Activity.class);
                startActivity(myIntent1);
            }
        });
    }
}