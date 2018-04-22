package com.filippos.ims_interface;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class OrdersSubmenuActivity extends AppCompatActivity {

    public class ConfirmOrder extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection;

            try {

                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("GET");

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

                JSONObject jsonResponse = new JSONObject(result).getJSONObject("response");

                String jsonMessage = jsonResponse.getString("message");

                Toast.makeText(getApplicationContext(), jsonMessage, Toast.LENGTH_LONG).show();


            } catch (JSONException e) {

                Toast.makeText(getApplicationContext(), "Oops, something went wrong!", Toast.LENGTH_LONG).show();

            }
        }
    }

    public void navigateToOrderActivity(View view){

        Button orderSubmenuNewButton = findViewById(R.id.orderSubmenuNewButton);
        Button orderSubmenuConfirmButton = findViewById(R.id.orderSubmenuConfirmButton);
        Button orderSubmenuReturnButton = findViewById(R.id.orderSubmenuReturnButton);

        if (orderSubmenuNewButton.equals(view)) {
            Intent intent = new Intent(OrdersSubmenuActivity.this, OrdersActivity.class);
            startActivity(intent);
        }
        else if(orderSubmenuConfirmButton.equals(view)){
            ConfirmOrder confirmOrder = new ConfirmOrder();
            confirmOrder.execute(MainActivity.ngrokURL + "/api/orders/store");
        }
        else if(orderSubmenuReturnButton.equals(view)){
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_submenu);
    }
}
