package com.filippos.ims_interface;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScanIncomingOrdersActivity extends AppCompatActivity {

    ArrayList<ArrivingProduct> arrivingProductsArrayList = new ArrayList<>();

    ListView arrivingOrderListView;
    ArrivingProductAdapter arrivingProductAdapter;

    public class CreateArrivingOrder extends AsyncTask<String, Void, String>{

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

            for(int i = 0; i < arrivingProductsArrayList.size(); i++) {
                StoreArrivingOrder storeArrivingOrder = new StoreArrivingOrder();
                storeArrivingOrder.execute(MainActivity.ngrokURL + "/api/arrival_products/store?barcode=" + arrivingProductsArrayList.get(i).ean +"&quantity=" + arrivingProductsArrayList.get(i).quantity);
            }
        }
    }

    public class StoreArrivingOrder extends AsyncTask<String, Void, String>{

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

                JSONObject jsonResponse = new JSONObject(result).getJSONObject("response");

                String jsonMessage = jsonResponse.getString("message");

                Toast.makeText(getApplicationContext(), jsonMessage, Toast.LENGTH_LONG).show();

            } catch (JSONException e) {

                e.printStackTrace();

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_incoming_orders);

        arrivingOrderListView = findViewById(R.id.arrivingOrderListView);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                if(data != null) {
                    String contents = data.getStringExtra("SCAN_RESULT"); //this is the result

                    Pattern Qrformat = Pattern.compile("[0-9]+[x][0-9]+[,]?");
                    Matcher matcher = Qrformat.matcher(contents);
                    if(matcher.lookingAt()) {
                        String[] splitContents = contents.split(",\n");

                        arrivingProductsArrayList.clear();

                        for (String splitContent : splitContents) {

                            String[] splitContentPart = splitContent.split("x");

                            arrivingProductsArrayList.add(new ArrivingProduct(splitContentPart[0], Integer.parseInt(splitContentPart[1])));

                        }

                        arrivingProductAdapter = new ArrivingProductAdapter(getApplicationContext(), R.layout.arriving_order_row, arrivingProductsArrayList);

                        arrivingOrderListView.setAdapter(arrivingProductAdapter);

                        arrivingOrderListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                            @Override
                            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                                final int itemToDelete = i;

                                new AlertDialog.Builder(ScanIncomingOrdersActivity.this)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setTitle("Are you sure?")
                                        .setMessage("Delete this item from arriving order?")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                arrivingProductsArrayList.remove(itemToDelete);
                                            }
                                        })
                                        .setNegativeButton("No", null)
                                        .show();

                                return true;
                            }
                        });

                        arrivingProductAdapter.notifyDataSetChanged();

                    } else {

                        arrivingProductsArrayList.clear();

                        arrivingProductAdapter.notifyDataSetChanged();

                        Toast.makeText(getApplicationContext(), "Unknown QR Format", Toast.LENGTH_LONG).show();

                    }
                }
            } else
            if (resultCode == RESULT_CANCELED) {
                // Handle cancel
            }
        }
    }

    public void scannedOrderNavigator(View view){

        Button scannedOrderScanButton = findViewById(R.id.scannedOrderScanButton);
        Button scannedOrderStockButton = findViewById(R.id.scannedOrderStockButton);
        Button scannedOrderBackButton = findViewById(R.id.scannedOrderBackButton);

        if (scannedOrderScanButton.equals(view)) {
            try {

                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");//for barcode, its "PRODUCT_MODE" instead of "QR_CODE_MODE"
                intent.putExtra("SAVE_HISTORY", false);//this stops saving ur barcode in barcode scanner app's history
                startActivityForResult(intent, 0);

            }catch(Exception e){

                Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
                Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
                startActivity(marketIntent);

            }
        }
        else if(scannedOrderStockButton.equals(view)){

            CreateArrivingOrder createArrivingOrder = new CreateArrivingOrder();
            createArrivingOrder.execute(MainActivity.ngrokURL + "/api/arrivals/create?order_id=11");

        }
        else if(scannedOrderBackButton.equals(view)){
            finish();
        }

    }
}
