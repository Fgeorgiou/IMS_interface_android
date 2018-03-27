package com.filippos.ims_interface;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText loginEmailEditText;
    EditText loginPasswordEditText;

    public void login(View view){

        Log.i("Email", loginEmailEditText.getText().toString());
        Log.i("Password", loginPasswordEditText.getText().toString());

        DownloadTask loginTask = new DownloadTask();
        loginTask.execute("http://67b30367.ngrok.io/api/login?email=" + loginEmailEditText.getText().toString() + "&password=" + loginPasswordEditText.getText().toString());
        //loginTask.execute("https://api.androidhive.info/contacts/");


    }

    public class DownloadTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection;

            try {

                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("POST");

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {

                    char current = (char) data;

                    result += current;

                    data = reader.read();

                }

                return result;

            } catch (MalformedURLException e) {

                e.printStackTrace();

            } catch (IOException e){

                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {

                JSONObject jsonObject = new JSONObject(result).getJSONObject("data");

                Log.i("Email", jsonObject.getString("email"));
                Log.i("Password", jsonObject.getString("password"));

                Toast.makeText(getApplicationContext(), "You are logged in", Toast.LENGTH_LONG).show();

            } catch (JSONException e) {

                Toast.makeText(getApplicationContext(), "Invalid email/password combination", Toast.LENGTH_LONG).show();

            }
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("Project state", "running");

        loginEmailEditText = findViewById(R.id.loginEmailEditText);
        loginPasswordEditText = findViewById(R.id.loginPasswordEditText);
    }
}
