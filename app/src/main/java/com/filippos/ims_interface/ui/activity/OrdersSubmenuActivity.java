package com.filippos.ims_interface.ui.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.filippos.ims_interface.R;
import com.filippos.ims_interface.data.remote.ConfirmOrderTask;
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

public class OrdersSubmenuActivity extends AppCompatActivity {

    public void navigateToOrderActivity(View view){

        Button orderSubmenuNewButton = findViewById(R.id.orderSubmenuNewButton);
        Button orderSubmenuConfirmButton = findViewById(R.id.orderSubmenuConfirmButton);
        Button orderSubmenuReturnButton = findViewById(R.id.orderSubmenuReturnButton);

        if (orderSubmenuNewButton.equals(view)) {
            Intent intent = new Intent(OrdersSubmenuActivity.this, OrdersActivity.class);
            startActivity(intent);
        }
        else if(orderSubmenuConfirmButton.equals(view)){
            ConfirmOrderTask confirmOrder = new ConfirmOrderTask(OrdersSubmenuActivity.this);
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
