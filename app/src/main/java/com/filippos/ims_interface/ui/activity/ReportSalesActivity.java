package com.filippos.ims_interface.ui.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.filippos.ims_interface.R;
import com.filippos.ims_interface.data.remote.GetAsyncTask;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReportSalesActivity extends AppCompatActivity {

    public class FetchOverallSales extends GetAsyncTask {

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {

                JSONObject jsonObject = new JSONObject(result);

                String jsonResponse = jsonObject.getString("response");

                JSONArray jsonArray = new JSONArray(jsonResponse);

                ArrayList<String> xData = new ArrayList<>();
                ArrayList<Float> yDataArrayList = new ArrayList<>();

                for(int i = 0; i <jsonArray.length(); i++){

                    JSONObject jsonPart = jsonArray.getJSONObject(i);

                    xData.add(jsonPart.getString("Day"));
                    yDataArrayList.add(Float.parseFloat(jsonPart.getString("Sales")));

                }

                float[] yData = new float[yDataArrayList.size()];

                for (int i = 0; i < yDataArrayList.size(); i++) {
                    yData[i] = yDataArrayList.get(i);
                }

                Intent intent = new Intent(ReportSalesActivity.this, ReportSalesResultsActivity.class);
                intent.putExtra("xData", xData);
                intent.putExtra("yData", yData);
                startActivity(intent);

            } catch (JSONException e) {

                e.printStackTrace();

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_sales);
    }

    public void salesReportNavigator(View view){

        EditText salesReportFromEditText = findViewById(R.id.salesReportFromEditText);
        EditText salesReportToEditText = findViewById(R.id.salesReportToEditText);
        EditText salesReportLimitEditText = findViewById(R.id.salesReportLimitEditText);

        FetchOverallSales fetchOverallSales = new FetchOverallSales();

        if(view.equals(findViewById(R.id.salesReportDailyButton))){

            fetchOverallSales.execute(MainActivity.ngrokURL + "/api/sales_reports/periodic_sales?sub_days=" + 1);

        } else if(view.equals(findViewById(R.id.salesReportWeeklyButton))){

            fetchOverallSales.execute(MainActivity.ngrokURL + "/api/sales_reports/periodic_sales?sub_days=" + 7);

        } else if(view.equals(findViewById(R.id.salesReportMonthlyButton))){

            fetchOverallSales.execute(MainActivity.ngrokURL + "/api/sales_reports/periodic_sales?sub_days=" + 30);

        } else if(view.equals(findViewById(R.id.salesReportYearlyButton))){

            fetchOverallSales.execute(MainActivity.ngrokURL + "/api/sales_reports/periodic_sales?sub_days=" + 365);

        } else if(view.equals(findViewById(R.id.salesReportSubmitButton))){

            Pattern pattern = Pattern.compile("[0-9]{4}-[0-1][0-2]-[0-3][0-9]");
            Matcher matcherFrom = pattern.matcher(salesReportFromEditText.getText());
            Matcher matcherTo = pattern.matcher(salesReportToEditText.getText());

            //If the user is querying for specific start/end dates, this will run
            if(matcherFrom.matches() && matcherTo.matches()){

                if(!salesReportLimitEditText.getText().toString().matches("")) {

                    fetchOverallSales.execute(MainActivity.ngrokURL + "/api/sales_reports/periodic_sales?start_date=" + salesReportFromEditText.getText() + "&end_date=" + salesReportToEditText.getText() + "&result_limit=" + salesReportLimitEditText.getText());

                } else {

                    fetchOverallSales.execute(MainActivity.ngrokURL + "/api/sales_reports/periodic_sales?start_date=" + salesReportFromEditText.getText() + "&end_date=" + salesReportToEditText.getText());

                }

            }
            //If the user wishes to provide only a date limit this if will run
            else if(matcherFrom.matches() && salesReportToEditText.getText().toString().matches("")){

                if(!salesReportLimitEditText.getText().toString().matches("")) {

                    fetchOverallSales.execute(MainActivity.ngrokURL + "/api/sales_reports/periodic_sales?date_limit=" + salesReportFromEditText.getText() + "&result_limit=" + salesReportLimitEditText.getText());

                } else {

                    fetchOverallSales.execute(MainActivity.ngrokURL + "/api/sales_reports/periodic_sales?date_limit=" + salesReportFromEditText.getText());

                }

            }
            //If the user wants a pie chart from the start of records, this if will run
            else if(salesReportFromEditText.getText().toString().matches("") && salesReportToEditText.getText().toString().matches("")){

                if(salesReportLimitEditText.getText().toString().matches("")) {

                    fetchOverallSales.execute(MainActivity.ngrokURL + "/api/sales_reports/periodic_sales");

                } else {

                    fetchOverallSales.execute(MainActivity.ngrokURL + "/api/sales_reports/periodic_sales?result_limit=" + salesReportLimitEditText.getText());

                }
            }
            //Finally, if the date format that is inserted is wrong, a message will appear.
            else {

                Toast.makeText(getApplicationContext(), "Wrong Date Format", Toast.LENGTH_LONG).show();

            }

        } else if(view.equals(findViewById(R.id.salesReportBackButton))){

            finish();

        }
    }
}
