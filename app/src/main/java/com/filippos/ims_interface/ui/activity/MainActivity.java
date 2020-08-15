package com.filippos.ims_interface.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.filippos.ims_interface.R;
import com.filippos.ims_interface.data.remote.LoginTask;
import com.filippos.ims_interface.data.remote.PostAsyncTask;
import com.filippos.ims_interface.util.HttpMethod;
import com.filippos.ims_interface.util.RestCallUtil;

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
    //TODO: Move the link to a props file.
    static String ngrokURL = "http://c4eae7088e22.ngrok.io";

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public void login(View view){

        if(isOnline()) {

            LoginTask loginTask = new LoginTask(MainActivity.this, sharedPreferences);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                loginTask.execute(ngrokURL + "/api/login?email=" + loginEmailEditText.getText().toString() + "&password=" + loginPasswordEditText.getText().toString());
            }

        } else {

            Toast.makeText(getApplicationContext(), "Internet connection is required.", Toast.LENGTH_LONG).show();

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
