package com.filippos.ims_interface;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReportTopProductsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_top_products);
    }

    public void topProductsReportNavigator(View view){

        EditText topProductsFromEditText = findViewById(R.id.topProductsReportFromEditText);
        EditText topProductsToEditText = findViewById(R.id.topProductsReportToEditText);
        EditText topProductsReportLimitEditText = findViewById(R.id.topProductsReportLimitEditText);

        if(view.equals(findViewById(R.id.topProductsReportDailyButton))){

            Toast.makeText(getApplicationContext(), "Daily", Toast.LENGTH_LONG).show();

        }else if(view.equals(findViewById(R.id.topProductsReportWeeklyButton))){

            Toast.makeText(getApplicationContext(), "Weekly", Toast.LENGTH_LONG).show();

        }else if(view.equals(findViewById(R.id.topProductsReportMonthlyButton))){

            Toast.makeText(getApplicationContext(), "Monthly", Toast.LENGTH_LONG).show();

        }else if(view.equals(findViewById(R.id.topProductsReportYearlyButton))){

            Toast.makeText(getApplicationContext(), "Yearly", Toast.LENGTH_LONG).show();

        }else if(view.equals(findViewById(R.id.topProductsReportSubmitButton))){

            Pattern pattern = Pattern.compile("[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}");
            Matcher matcherFrom = pattern.matcher(topProductsFromEditText.getText());
            Matcher matcherTo = pattern.matcher(topProductsToEditText.getText());

            if(matcherFrom.matches() && matcherTo.matches()){
                Toast.makeText(getApplicationContext(), "We good " + topProductsReportLimitEditText.getText(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ReportTopProductsActivity.this, ReportSalesResultsActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(), "No.", Toast.LENGTH_LONG).show();
            }

        }else if(view.equals(findViewById(R.id.topProductsReportBackButton))){

            finish();

        }

    }
}
