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
import android.widget.Button;
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
import java.util.Arrays;

import static java.util.Arrays.asList;

public class OrdersActivity extends AppCompatActivity {

    EditText barcodeEditText;
    EditText quantityEditText;

    ArrayList<Product> productsArray = new ArrayList<>();

    ListView orderListView;
    ProductAdapter productAdapter;

    public void navigateToOrderSubmenu(View view){

        Button orderBackButton = findViewById(R.id.orderBackButton);

        if(orderBackButton.equals(view)){
            finish();
        }

    }

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

                if(jsonOrderObject != null){

                    String orderProductsObj = jsonOrderObject.getString("order_products");

                    JSONArray jsonProductsArray = new JSONArray(orderProductsObj);

                    for(int i = 0; i < jsonProductsArray.length(); i++) {

                        JSONObject jsonPart = jsonProductsArray.getJSONObject(i);
                        JSONObject jsonProd = new JSONObject(jsonPart.toString()).getJSONObject("product");

                        String productName = jsonProd.getString("name");

                        productsArray.add(new Product(
                                jsonPart.getInt("id"),
                                jsonPart.getInt("order_id"),
                                productName,
                                jsonPart.getInt("status_id"),
                                jsonPart.getInt("quantity")
                        ));
                    }
                    refreshListView();
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
        orderListView = findViewById(R.id.orderListView);

        FetchOrInitOrder fetchOrInitOrder = new FetchOrInitOrder();
        fetchOrInitOrder.execute(MainActivity.ngrokURL + "/api/orders/create?user_id=" + MainActivity.sharedPreferences.getString("user_id", null));

        refreshListView();
    }

    public void refreshListView(){

        productAdapter = new ProductAdapter(getApplicationContext(), R.layout.row, productsArray);

        if(productAdapter != null) {

            orderListView.setAdapter(productAdapter);

            productAdapter.notifyDataSetChanged();

        }
    }
}
