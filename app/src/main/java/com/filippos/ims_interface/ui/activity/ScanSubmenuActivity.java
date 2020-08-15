package com.filippos.ims_interface.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.filippos.ims_interface.R;

public class ScanSubmenuActivity extends AppCompatActivity {

    public void scanSubmenuNavigator(View view){

        Button scanProductsButton = findViewById(R.id.scanProductsButton);
        Button scanIncomingOrdersButton = findViewById(R.id.scanIncomingOrdersButton);
        Button scanSubmenuBackButton = findViewById(R.id.scanSubmenuBackButton);

        if (scanProductsButton.equals(view)) {
            Intent intent = new Intent(ScanSubmenuActivity.this, ScanProductsActivity.class);
            startActivity(intent);
        }
        else if(scanIncomingOrdersButton.equals(view)){
            Intent intent = new Intent(ScanSubmenuActivity.this, ScanIncomingOrdersActivity.class);
            startActivity(intent);
        }
        else if(scanSubmenuBackButton.equals(view)){
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_submenu);
    }
}
