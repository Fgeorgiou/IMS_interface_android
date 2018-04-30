package com.filippos.ims_interface;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ReportSubmenuActivity extends AppCompatActivity {

    public void reportActivityNavigator(View view){

        Button reportOrdersButton = findViewById(R.id.reportSubmenuOrdersButton);
        Button reportSalesButton = findViewById(R.id.reportSubmenuSalesButton);
        Button reportOrderAnomaliesButton = findViewById(R.id.reportSubmenuOrderAnomaliesButton);
        Button reportInventoryLevelsButton = findViewById(R.id.reportSubmenuInventoryLevelsButton);
        Button reportSubmenuReturnButton = findViewById(R.id.reportSubmenuReturnButton);

        if (reportOrdersButton.equals(view)) {
            Log.i("Button pressed", "orders");
        }
        else if(reportSalesButton.equals(view)){
            Intent intent = new Intent(ReportSubmenuActivity.this, ReportSalesSubmenuActivity.class);
            startActivity(intent);
        }
        else if(reportOrderAnomaliesButton.equals(view)){
            Log.i("Button pressed", "arrivals");
        }
        else if(reportInventoryLevelsButton.equals(view)){
            Log.i("Button pressed", "inventory");
        }else if(reportSubmenuReturnButton.equals(view)){
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_submenu);
    }
}
