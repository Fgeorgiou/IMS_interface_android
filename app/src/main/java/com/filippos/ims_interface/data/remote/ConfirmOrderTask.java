package com.filippos.ims_interface.data.remote;

import android.app.Activity;
import android.widget.TextView;
import android.widget.Toast;

import com.filippos.ims_interface.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ConfirmOrderTask extends GetAsyncTask {

    private Activity initialContext;

    public ConfirmOrderTask(Activity initialContext){
        this.initialContext = initialContext;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        try {

            JSONObject jsonResponse = new JSONObject(result).getJSONObject("response");

            String jsonMessage = jsonResponse.getString("message");

            Toast.makeText(initialContext.getApplicationContext(), jsonMessage, Toast.LENGTH_LONG).show();


        } catch (JSONException e) {

            Toast.makeText(initialContext.getApplicationContext(), "Oops, something went wrong!", Toast.LENGTH_LONG).show();

        }
    }
}
