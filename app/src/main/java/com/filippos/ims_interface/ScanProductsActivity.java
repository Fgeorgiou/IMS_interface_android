package com.filippos.ims_interface;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ScanProductsActivity extends AppCompatActivity {

    TextView scannedProductNameTextView;
    TextView scannedProductSupplierTextView;
    TextView scannedProductCategoryTextView;
    TextView scannedProductBarcodeValueTextView;
    TextView scannedProductPerPackValueTextView;
    TextView scannedProductNetValueTextView;
    TextView scannedProductGrossValueTextView;
    TextView scannedProductLeadValueTextView;
    TextView scannedProductStockValueTextView;

    public class FetchProductInfo extends AsyncTask<String, Void, String> {

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

            } catch (IOException e) {

                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {

                //Getting the whole json response
                JSONObject jsonResponseObject = new JSONObject(result).getJSONObject("response");

                String request_code = jsonResponseObject.getString("request_code");

                if(request_code.equals("200")){

                    Toast.makeText(getApplicationContext(), "Found!", Toast.LENGTH_SHORT).show();

                    //Extracting the necessary information from json response
                    JSONObject jsonProductObject = new JSONObject(jsonResponseObject.toString()).getJSONObject("product");
                    JSONObject jsonProductSupplier = new JSONObject(jsonProductObject.toString()).getJSONObject("supplier");
                    JSONObject jsonProductStock = new JSONObject(jsonProductObject.toString()).getJSONObject("stock");
                    JSONObject jsonProductCategory = new JSONObject(jsonProductObject.toString()).getJSONObject("category");

                    //Setting the TextView values
                    scannedProductNameTextView.setText(jsonProductObject.getString("name"));
                    scannedProductSupplierTextView.setText(jsonProductSupplier.getString("name"));
                    scannedProductCategoryTextView.setText(jsonProductCategory.getString("name"));
                    scannedProductBarcodeValueTextView.setText(jsonProductObject.getString("barcode"));
                    scannedProductPerPackValueTextView.setText(jsonProductObject.getString("unit_per_pack"));
                    scannedProductNetValueTextView.setText(jsonProductObject.getString("unit_net_weight_gr"));
                    scannedProductGrossValueTextView.setText(jsonProductObject.getString("unit_gross_weight_gr"));
                    scannedProductLeadValueTextView.setText(jsonProductObject.getString("lead_days"));
                    scannedProductStockValueTextView.setText(jsonProductStock.getString("quantity"));

                } else {

                    String product_barcode = jsonResponseObject.getString("barcode");

                    scannedProductNameTextView.setText("Scan a Product..");
                    scannedProductSupplierTextView.setText("");
                    scannedProductCategoryTextView.setText("");
                    scannedProductBarcodeValueTextView.setText(product_barcode);
                    scannedProductPerPackValueTextView.setText("");
                    scannedProductNetValueTextView.setText("");
                    scannedProductGrossValueTextView.setText("");
                    scannedProductLeadValueTextView.setText("");
                    scannedProductStockValueTextView.setText("");

                    Toast.makeText(getApplicationContext(), "Barcode not found..", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {

//                orderProductRecognitionTextView.setText("Unknown Barcode");
//                orderStockTextView.setText("");

                Toast.makeText(getApplicationContext(), "Something went wrong! Please re-scan.", Toast.LENGTH_LONG).show();

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_products);

        scannedProductNameTextView = findViewById(R.id.scannedProductNameTextView);
        scannedProductSupplierTextView = findViewById(R.id.scannedProductSupplierTextView);
        scannedProductCategoryTextView = findViewById(R.id.scannedProductCategoryTextView);
        scannedProductBarcodeValueTextView = findViewById(R.id.scannedProductBarcodeValueTextView);
        scannedProductPerPackValueTextView = findViewById(R.id.scannedProductPerPackValueTextView);
        scannedProductNetValueTextView = findViewById(R.id.scannedProductNetValueTextView);
        scannedProductGrossValueTextView = findViewById(R.id.scannedProductGrossValueTextView);
        scannedProductLeadValueTextView = findViewById(R.id.scannedProductLeadValueTextView);
        scannedProductStockValueTextView = findViewById(R.id.scannedProductStockValueTextView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT"); //this is the result

                if(contents.matches("[0-9]{12,13}")) {

                    if(contents.length() == 12) {

                        FetchProductInfo fetchProductInfo = new FetchProductInfo();
                        fetchProductInfo.execute(MainActivity.ngrokURL + "/api/products/show/0" + contents);

                    } else if(contents.length() == 13){

                        FetchProductInfo fetchProductInfo = new FetchProductInfo();
                        fetchProductInfo.execute(MainActivity.ngrokURL + "/api/products/show/" + contents);

                    }

                }
            } else
            if (resultCode == RESULT_CANCELED) {
                // Handle cancel
            }
        }
    }

    public void scannedProductNavigator(View view){

        Button scannedProductScanButton = findViewById(R.id.scannedProductScanButton);
        Button scannedProductAddButton = findViewById(R.id.scannedProductAddButton);
        Button scannedProductBackButton = findViewById(R.id.scannedProductBackButton);

        if (scannedProductScanButton.equals(view)) {
            try {

                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "PRODUCT_MODE");//for Qr code, its "QR_CODE_MODE" instead of "PRODUCT_MODE"
                intent.putExtra("SAVE_HISTORY", false);//this stops saving ur barcode in barcode scanner app's history
                startActivityForResult(intent, 0);

            }catch(Exception e){

                Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
                Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
                startActivity(marketIntent);

            }
        }
        else if(scannedProductAddButton.equals(view)){

            if(scannedProductBarcodeValueTextView != null && scannedProductBarcodeValueTextView.length() == 13) {
                Intent intent = new Intent(ScanProductsActivity.this, AddProductsActivity.class);
                intent.putExtra("barcode", scannedProductBarcodeValueTextView.getText());
                startActivity(intent);
            }
        }
        else if(scannedProductBackButton.equals(view)){
            finish();
        }

    }

}
