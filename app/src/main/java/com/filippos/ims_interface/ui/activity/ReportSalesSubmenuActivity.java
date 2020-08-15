package com.filippos.ims_interface.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.filippos.ims_interface.R;

public class ReportSalesSubmenuActivity extends AppCompatActivity {

    public void reportSalesSubmenuActivityNavigator(View view){

        Button reportSalesSubmenuOverallButton = findViewById(R.id.reportSalesSubmenuOverallButton);
        Button reportSalesSubmenuTopProductsButton = findViewById(R.id.reportSalesSubmenuTopProductsButton);
        Button reportSalesSubmenuReturnButton = findViewById(R.id.reportSalesSubmenuReturnButton);

        if (reportSalesSubmenuOverallButton.equals(view)) {
            Intent intent = new Intent(ReportSalesSubmenuActivity.this, ReportSalesActivity.class);
            startActivity(intent);
        }
        else if(reportSalesSubmenuTopProductsButton.equals(view)){
            Intent intent = new Intent(ReportSalesSubmenuActivity.this, ReportTopProductsActivity.class);
            startActivity(intent);
        }
        else if(reportSalesSubmenuReturnButton.equals(view)){
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_sales_submenu);
    }
}
