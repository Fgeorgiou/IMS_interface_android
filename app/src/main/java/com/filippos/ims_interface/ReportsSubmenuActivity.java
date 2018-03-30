package com.filippos.ims_interface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ReportsSubmenuActivity extends AppCompatActivity {

    public void navigateToReportActivity(View view){

        Button reportOrdersButton = findViewById(R.id.reportSubmenuOrdersButton);
        Button reportSalesButton = findViewById(R.id.reportSubmenuSalesButton);
        Button reportOrderAnomaliesButton = findViewById(R.id.reportSubmenuOrderAnomaliesButton);
        Button reportInventoryLevelsButton = findViewById(R.id.reportSubmenuInventoryLevelsButton);
        Button reportSubmenuReturnButton = findViewById(R.id.reportSubmenuReturnButton);

        if (reportOrdersButton.equals(view)) {
            Log.i("Button pressed", "orders");
//            Intent intent = new Intent(AdminSubmenuActivity.this, OrdersSubmenuActivity.class);
//            startActivity(intent);
        }
        else if(reportSalesButton.equals(view)){
            Log.i("Button pressed", "sales");
//            Intent intent = new Intent(AdminSubmenuActivity.this, ScanItemsActivity.class);
//            startActivity(intent);
        }
        else if(reportOrderAnomaliesButton.equals(view)){
            Log.i("Button pressed", "anomalies");
//            Intent intent = new Intent(AdminSubmenuActivity.this, ReportsSubmenuActivity.class);
//            startActivity(intent);
        }
        else if(reportInventoryLevelsButton.equals(view)){
            Log.i("Button pressed", "inventory");
//            Intent intent = new Intent(AdminSubmenuActivity.this, AdminSubmenuActivity.class);
//            startActivity(intent);
        }else if(reportSubmenuReturnButton.equals(view)){
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_submenu);
    }
}
