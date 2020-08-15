package com.filippos.ims_interface.data.remote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.filippos.ims_interface.ui.activity.HomepageActivity;
import com.filippos.ims_interface.ui.activity.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginTask extends PostAsyncTask {

    private SharedPreferences sharedPreferences;
    private Activity initialContext;

    public LoginTask(Activity initialContext, SharedPreferences sharedPreferences){
        this.initialContext = initialContext;
        this.sharedPreferences = sharedPreferences;
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
            sharedPreferences.edit().putString("role_id", userRole.getString("access_level")).apply();

            Intent intent = new Intent(initialContext, HomepageActivity.class);
            initialContext.startActivity(intent);

        } catch (JSONException e) {

            Toast.makeText(initialContext.getApplicationContext(), "Invalid email/password combination", Toast.LENGTH_LONG).show();

        }
    }
}
