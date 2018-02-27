package com.example.administrator.walkerstoursapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

import android.annotation.TargetApi;

import android.content.Context;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.text.method.LinkMovementMethod;

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by Administrator on 11/28/2017.
 */

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    String json_string ;
    private Context context;
    String url = "http://jwmobileapi.azurewebsites.net/api/Res/Authenticate";
    String Response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        String userId = user.get("userId").toString();
        String categoryId = user.get("catId").toString();
        String categoryType = user.get("catType").toString();
        String batchId= user.get("batchId").toString();

        Intent intent = getIntent();

        //intent.putExtra("username", "Pavithra");
        //intent.putExtra("password", "pass#word1");

        Bundle intentBundle = intent.getExtras();

        /*System.out.println(intentBundle);
        if (intentBundle == null) {
            finish();
            return;
        }*/

        //It gets the Username from MainActivity
        final String loggedUser = intentBundle.getString("username");
        final String loggedUser1 = intentBundle.getString("password");

        EditText loginUsername = (EditText) findViewById(R.id.email);
        EditText loginPassword = (EditText) findViewById(R.id.password);

        loginUsername.setText(loggedUser);

        Button UserAccount = (Button)findViewById(R.id.btnLogin);

        //If user push the UserAccount button it calls the doInBackground task
        UserAccount.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {
                new BackgroundTask(getBaseContext()).execute(loggedUser);
            }
        });
    }


    public class BackgroundTask extends AsyncTask<String, Void, String> {

        Context context;
        AlertDialog alertDialog;

        public BackgroundTask(Context userContext) {
            this.context = userContext;
        }

        String JSON_STRING;
        String result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {


            if (android.os.Debug.isDebuggerConnected())
                android.os.Debug.waitForDebugger();

            String json_url = "http://jwmobileapi.azurewebsites.net/api/Res/Authenticate";
            try {
                String username = params[0];
                String password = params[1];

                URL url = new URL(json_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);


                OutputStream outputStream = httpURLConnection.getOutputStream();

                // Building the message to the PHP script
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                //Making the POST URL to send to PHP code and get the result.
                String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

                // Writing the data
                bufferedWriter.write(post_data);

                // Closing all writing structures
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                // Building the Reading Infrastructure
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));


                // Reading the data
                //StringBuilder stringBuilder = new StringBuilder();
                StringBuilder stringBuilder = new StringBuilder();

                while ((JSON_STRING = bufferedReader.readLine()) != null) {

                    stringBuilder.append(JSON_STRING + "\n");

                }

                // Closing all reading structures

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                result = stringBuilder.toString();
                return result;


            } catch (MalformedURLException e) {
                e.printStackTrace();


            } catch (IOException e) {
                Log.e("log_tag", "Error converting result " + e.toString());

            }

            return null;

        }

        @Override
        protected void onProgressUpdate(Void... values) {

            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(String result) {

            //This is removing the double quotation from string result so we can compare
            json_string = result.replace("\"", "");

            //Trim removes the unwanted spaces from the result.


            if (json_string.trim().contentEquals("admin")) {

                // Create your intent.

                Intent adminIntent = new Intent(LoginActivity.this, WelcomeActivity.class);
                // Start the Student page activity.
                startActivity(adminIntent);

            } else if (json_string.trim().contentEquals("user")) {

                // Create your intent.

                Intent userIntent = new Intent(LoginActivity.this, com.example.administrator.walkerstoursapp.Hotel.SplashScreen.class);
                // Start the teacher page activity.
                startActivity(userIntent);
            }
        }
    }

}

