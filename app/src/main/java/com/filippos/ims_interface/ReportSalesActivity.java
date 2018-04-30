package com.filippos.ims_interface;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReportSalesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_sales);
    }

    public void salesReportNavigator(View view){

        EditText salesReportFromEditText = findViewById(R.id.salesReportFromEditText);
        EditText salesReportToEditText = findViewById(R.id.salesReportToEditText);
        EditText salesReportLimitEditText = findViewById(R.id.salesReportLimitEditText);

        if(view.equals(findViewById(R.id.salesReportDailyButton))){

            Toast.makeText(getApplicationContext(), "Daily", Toast.LENGTH_LONG).show();

        }else if(view.equals(findViewById(R.id.salesReportWeeklyButton))){

            Toast.makeText(getApplicationContext(), "Weekly", Toast.LENGTH_LONG).show();

        }else if(view.equals(findViewById(R.id.salesReportMonthlyButton))){

            Toast.makeText(getApplicationContext(), "Monthly", Toast.LENGTH_LONG).show();

        }else if(view.equals(findViewById(R.id.salesReportYearlyButton))){

            Toast.makeText(getApplicationContext(), "Yearly", Toast.LENGTH_LONG).show();

        }else if(view.equals(findViewById(R.id.salesReportSubmitButton))){

            Pattern pattern = Pattern.compile("[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}");
            Matcher matcherFrom = pattern.matcher(salesReportFromEditText.getText());
            Matcher matcherTo = pattern.matcher(salesReportToEditText.getText());

            if(matcherFrom.matches() && matcherTo.matches()){
                Toast.makeText(getApplicationContext(), "We good" + salesReportLimitEditText.getText(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ReportSalesActivity.this, ReportSalesResultsActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(), "No.", Toast.LENGTH_LONG).show();
            }

        }else if(view.equals(findViewById(R.id.salesReportBackButton))){

            finish();

        }

    }
}
