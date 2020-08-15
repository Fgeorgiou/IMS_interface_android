package com.filippos.ims_interface.data.remote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.filippos.ims_interface.ui.activity.HomepageActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class AddProductTask extends PostAsyncTask {

    private Activity initialContext;

    public AddProductTask(Activity initialContext){
        this.initialContext = initialContext;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        try {

            JSONObject jsonObject = new JSONObject(result);

            String jsonMessage = jsonObject.getString("message");
            String jsonStatusCode = jsonObject.getString("status");

            Toast.makeText(initialContext.getApplicationContext(), jsonMessage, Toast.LENGTH_LONG).show();

            if (jsonStatusCode.equals(String.valueOf(201))){
                initialContext.finish();
            }

        } catch (JSONException e) {

            Toast.makeText(initialContext.getApplicationContext(), "Couldn't get a response from the server..", Toast.LENGTH_LONG).show();

        }
    }
}
