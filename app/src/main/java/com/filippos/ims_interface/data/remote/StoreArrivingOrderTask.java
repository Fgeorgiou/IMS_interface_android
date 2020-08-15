package com.filippos.ims_interface.data.remote;

import android.app.Activity;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class StoreArrivingOrderTask extends PostAsyncTask {

    private Activity initialContext;

    public StoreArrivingOrderTask(Activity initialContext){
        this.initialContext = initialContext;
    }

    @Override
    protected void onPostExecute(String result) {

        try {

            JSONObject jsonResponse = new JSONObject(result).getJSONObject("response");

            String jsonMessage = jsonResponse.getString("message");

            Toast.makeText(initialContext.getApplicationContext(), jsonMessage, Toast.LENGTH_LONG).show();

        } catch (JSONException e) {

            e.printStackTrace();

        }
    }
}
