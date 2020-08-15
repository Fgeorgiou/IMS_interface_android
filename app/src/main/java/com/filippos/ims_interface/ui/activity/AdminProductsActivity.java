package com.filippos.ims_interface.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.filippos.ims_interface.data.remote.GetAsyncTask;
import com.filippos.ims_interface.ui.adapter.ProductAdapter;
import com.filippos.ims_interface.R;
import com.filippos.ims_interface.data.model.Product;
import com.filippos.ims_interface.util.HttpMethod;
import com.filippos.ims_interface.util.RestCallUtil;

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

public class AdminProductsActivity extends AppCompatActivity {

    ArrayList<Product> productsArrayList = new ArrayList<>();

    ListView productsListView;
    ProductAdapter productAdapter;

    //TODO: Find a way to refresh the list from out of context. Possibly a callback(?).
    public class DownloadProducts extends GetAsyncTask {

        @Override
        protected void onPostExecute(String result) {

            try {

                JSONObject jsonObject = new JSONObject(result);

                if(jsonObject != null){

                    JSONArray jsonProductsArray = jsonObject.getJSONArray("products");

                    for(int i = 0; i < jsonProductsArray.length(); i++) {

                        JSONObject jsonPart = jsonProductsArray.getJSONObject(i);

                        JSONObject jsonSupplier = new JSONObject(jsonPart.toString()).getJSONObject("supplier");
                        JSONObject jsonStock = new JSONObject(jsonPart.toString()).getJSONObject("stock");

                        String supplierName = jsonSupplier.getString("name");
                        String stock = jsonStock.getString("quantity");

                        productsArrayList.add(new Product(
                                jsonPart.getString("name"),
                                jsonPart.getString("barcode"),
                                supplierName,
                                stock
                        ));
                    }
                    refreshListView();
                }

            } catch (JSONException e) {

                Toast.makeText(getApplicationContext(), "Invalid request", Toast.LENGTH_LONG).show();

            }
        }
    }

    /*
    This AsyncTask is responsible for deleting records from the current order
    The doInBackground method will download all the data and hit the delete endpoint of the orders, marking as deleted the item.
    The onPostExecute will take over once the task is finished and will remove the item from the ArrayList, refresh the List view and inform the user with a toast.
    */
    //TODO: Find a way to refresh the list from out of context. Possibly a callback(?).
    public class DeleteTask extends GetAsyncTask {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Toast.makeText(getApplicationContext(), "Product was marked as inactive successfully!", Toast.LENGTH_LONG).show();

            refreshListView();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_products);

        productsListView = findViewById(R.id.productsListView);

        DownloadProducts downloadProducts = new DownloadProducts();
        downloadProducts.execute(MainActivity.ngrokURL + "/api/products");
    }

    public void adminActionsNavigator(View view) {

        if (view.equals(findViewById(R.id.newProductButton))) {

            Intent intent = new Intent(this, AddProductsActivity.class);
            startActivity(intent);

        } else if (view.equals(findViewById(R.id.productsBackButton))){

            finish();

        }
    }

    public void refreshListView(){

        productAdapter = new ProductAdapter(getApplicationContext(), R.layout.product_row, productsArrayList);

        if(productAdapter != null) {

            productsListView.setAdapter(productAdapter);

            productsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                    final int itemToDelete = i;

                    new AlertDialog.Builder(AdminProductsActivity.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Are you sure?")
                            .setMessage("Mark product as inactive?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    AdminProductsActivity.DeleteTask deleteTask = new AdminProductsActivity.DeleteTask();
                                    deleteTask.execute(MainActivity.ngrokURL + "/api/products/delete/" + productsArrayList.get(itemToDelete).barcode);

                                    productsArrayList.remove(itemToDelete);
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();

                    return true;
                }
            });
            productAdapter.notifyDataSetChanged();

        }
    }
}
