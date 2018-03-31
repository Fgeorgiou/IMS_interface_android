package com.filippos.ims_interface;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
            Log.i("Button pressed", "confirm order");
//            Intent intent = new Intent(OrdersSubmenuActivity.this, ScanItemsActivity.class);
//            startActivity(intent);
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
