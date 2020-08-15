package com.filippos.ims_interface.ui.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.filippos.ims_interface.R;
import com.filippos.ims_interface.data.remote.AddProductTask;
import com.filippos.ims_interface.util.HttpMethod;
import com.filippos.ims_interface.util.RestCallUtil;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);

        Intent intent = getIntent();
        intent_barcode = intent.getStringExtra("barcode");

        if(intent_barcode != null) {

            EditText barcodeEditText = findViewById(R.id.productFormBarcodeEditText);
            barcodeEditText.setText(intent_barcode);

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

            AddProductTask addProduct = new AddProductTask(AddProductsActivity.this);
            addProduct.execute(MainActivity.ngrokURL + "/api/products/create?name=" + name.getText() + "&barcode=" + barcode.getText() + "&category=" + category_id.getText() + "&supplier=" + supplier_id.getText() + "&per_pack=" + per_pack.getText() +  "&net_weight=" + net_weight.getText() +  "&gross_weight=" + gross_weight.getText()+  "&lead_days=" + lead_days.getText());

        } else if(view.equals(findViewById(R.id.productFormBackButton))){
            finish();
        }

    }
}
