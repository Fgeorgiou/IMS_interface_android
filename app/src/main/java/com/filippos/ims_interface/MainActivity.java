package com.filippos.ims_interface;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
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

    static SharedPreferences sharedPreferences;
    //Static variable holding the ngrok server url. It is set to static to avoid repetition inside the scripts
    //Set this to either a production server address or the ngrok instance running
    static String ngrokURL = "http://566d44cb.ngrok.io";

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public void login(View view){

        if(isOnline()) {

            LoginTask loginTask = new LoginTask();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                loginTask.execute(ngrokURL + "/api/login?email=" + loginEmailEditText.getText().toString() + "&password=" + loginPasswordEditText.getText().toString());
            }

        } else {

            Toast.makeText(getApplicationContext(), "Internet connection is required.", Toast.LENGTH_LONG).show();

        }
    }

    public class LoginTask extends AsyncTask<String, Void, String>{

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
                JSONObject userRole = new JSONObject(jsonObject.toString()).getJSONObject("role");

                sharedPreferences.edit().putString("user_id", jsonObject.getString("id")).apply();
                sharedPreferences.edit().putString("first_name", jsonObject.getString("first_name")).apply();
                sharedPreferences.edit().putString("last_name", jsonObject.getString("last_name")).apply();
                sharedPreferences.edit().putString("email", jsonObject.getString("email")).apply();
                sharedPreferences.edit().putString("api_token", jsonObject.getString("api_token")).apply();
                //sharedPreferences.edit().putString("facility_id", jsonObject.getString("facility_id")).apply();
                sharedPreferences.edit().putString("role_id", userRole.getString("access_level")).apply();

                Intent intent = new Intent(MainActivity.this, HomepageActivity.class);
                startActivity(intent);

            } catch (JSONException e) {

                Toast.makeText(getApplicationContext(), "Invalid email/password combination", Toast.LENGTH_LONG).show();

            }
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences("com.filippos.ims_interface", Context.MODE_PRIVATE);

        loginEmailEditText = findViewById(R.id.loginEmailEditText);
        loginPasswordEditText = findViewById(R.id.loginPasswordEditText);
    }
}
