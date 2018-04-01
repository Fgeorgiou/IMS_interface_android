package com.filippos.ims_interface;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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
import java.util.ArrayList;

import static java.util.Arrays.asList;

public class OrdersActivity extends AppCompatActivity {

    EditText barcodeEditText;
    EditText quantityEditText;

    public class FetchOrInitOrder extends AsyncTask<String, Void, String>{

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

                JSONObject jsonOrderObject = new JSONObject(result).getJSONObject("current_order");
                JSONObject jsonProductsObject = new JSONObject(result).getJSONObject("current_order");

                MainActivity.sharedPreferences.edit().putString("order_id", jsonOrderObject.getString("order_id")).apply();
                if(jsonProductsObject != null){

                    String orderProducts = jsonProductsObject.getString("order_products");

                    Log.i("orderProds", orderProducts);

                    JSONArray jsonProductsArray = new JSONArray(orderProducts);

                    for(int i = 0; i < jsonProductsArray.length(); i++) {

                        JSONObject jsonPart = jsonProductsArray.getJSONObject(i);

                        Log.i("JSONPart", jsonPart.getString("product_id"));
                        Log.i("JSONPart", jsonPart.getString("quantity"));
                    }
                }


            } catch (JSONException e) {

                Toast.makeText(getApplicationContext(), "Invalid request", Toast.LENGTH_LONG).show();

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        barcodeEditText = findViewById(R.id.orderBarcodeEditText);
        quantityEditText = findViewById(R.id.orderQuantityEditText);

        ListView orderListView = findViewById(R.id.orderListView);

        FetchOrInitOrder fetchOrInitOrder = new FetchOrInitOrder();
        fetchOrInitOrder.execute(MainActivity.ngrokURL + "/api/orders/create?user_id=" + MainActivity.sharedPreferences.getString("user_id", null));

        final ArrayList<String> products = new ArrayList<String>(asList("Cheese", "T-shirt", "Calgon", "Soupline", "Chocolate", "Coffee", "Syrup", "Tart"));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, products);

        orderListView.setAdapter(arrayAdapter);

        orderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getApplicationContext(), products.get(position),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
