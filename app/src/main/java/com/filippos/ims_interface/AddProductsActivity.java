package com.filippos.ims_interface;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AddProductsActivity extends AppCompatActivity {

    String intent_barcode;

    public class AddProduct extends AsyncTask<String, Void, String> {
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

            } catch (IOException e) {

                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {

                JSONObject jsonObject = new JSONObject(result);

                String jsonMessage = jsonObject.getString("message");
                String jsonStatusCode = jsonObject.getString("status");

                Toast.makeText(getApplicationContext(), jsonMessage, Toast.LENGTH_LONG).show();

                if (jsonStatusCode.equals(String.valueOf(201))){
                    finish();
                }

            } catch (JSONException e) {

                Toast.makeText(getApplicationContext(), "Couldn't get a response from the server..", Toast.LENGTH_LONG).show();

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);

        Intent intent = getIntent();
        intent_barcode = intent.getStringExtra("barcode");

        if(intent_barcode != null) {

            EditText barcodeEditTect = findViewById(R.id.productFormBarcodeEditText);
            barcodeEditTect.setText(intent_barcode);

        }
    }

    public void productFormNavigator(View view){

        if(view.equals(findViewById(R.id.productFormSubmitButton))){

            EditText name = findViewById(R.id.productFormNameEditText);
            EditText barcode = findViewById(R.id.productFormBarcodeEditText);
            EditText category_id = findViewById(R.id.productFormCategoryEditText);
            EditText supplier_id = findViewById(R.id.productFormSupplierEditText);
            EditText per_pack = findViewById(R.id.productFormPerPackEditText);
            EditText net_weight = findViewById(R.id.productFormNetWeightEditText);
            EditText gross_weight = findViewById(R.id.productFormGrossWeightEditText);
            EditText lead_days = findViewById(R.id.productFormLeadDaysEditText);

            AddProduct addProduct = new AddProduct();
            addProduct.execute(MainActivity.ngrokURL + "/api/products/create?name=" + name.getText() + "&barcode=" + barcode.getText() + "&category=" + category_id.getText() + "&supplier=" + supplier_id.getText() + "&per_pack=" + per_pack.getText() +  "&net_weight=" + net_weight.getText() +  "&gross_weight=" + gross_weight.getText()+  "&lead_days=" + lead_days.getText());

        } else if(view.equals(findViewById(R.id.productFormBackButton))){
            finish();
        }

    }
}
