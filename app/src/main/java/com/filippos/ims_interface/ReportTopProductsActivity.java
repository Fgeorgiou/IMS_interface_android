package com.filippos.ims_interface;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

public class ReportTopProductsActivity extends AppCompatActivity {

    public class FetchTopProducts extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection;

            try {

                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("GET");

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {

                    char current = (char) data;

                    result += current;

                    data = reader.read();

                }

                return result;

            } catch (MalformedURLException e) {

                e.printStackTrace();

            } catch (IOException e){

                e.printStackTrace();

            }
            return null;
        }

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

                    String productName = jsonPart.getJSONObject("product").getString("name");

                    xData.add(productName);
                    yDataArrayList.add(Float.parseFloat(jsonPart.getString("quantity")));

                }

                float[] yData = new float[yDataArrayList.size()];

                for (int i = 0; i < yDataArrayList.size(); i++) {
                    yData[i] = yDataArrayList.get(i);
                }

                Intent intent = new Intent(ReportTopProductsActivity.this, ReportTopProductsResultsActivity.class);
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
        setContentView(R.layout.activity_report_top_products);
    }

    public void topProductsReportNavigator(View view){

        EditText topProductsFromEditText = findViewById(R.id.topProductsReportFromEditText);
        EditText topProductsToEditText = findViewById(R.id.topProductsReportToEditText);
        EditText topProductsReportLimitEditText = findViewById(R.id.topProductsReportLimitEditText);

        FetchTopProducts fetchTopProducts = new FetchTopProducts();

        if(view.equals(findViewById(R.id.topProductsReportDailyButton))){

            fetchTopProducts.execute(MainActivity.ngrokURL + "/api/sales_reports/top_products?sub_days=" + 1);

        }else if(view.equals(findViewById(R.id.topProductsReportWeeklyButton))){

            fetchTopProducts.execute(MainActivity.ngrokURL + "/api/sales_reports/top_products?sub_days=" + 7);

        }else if(view.equals(findViewById(R.id.topProductsReportMonthlyButton))){

            fetchTopProducts.execute(MainActivity.ngrokURL + "/api/sales_reports/top_products?sub_days=" + 30);

        }else if(view.equals(findViewById(R.id.topProductsReportYearlyButton))){

            fetchTopProducts.execute(MainActivity.ngrokURL + "/api/sales_reports/top_products?sub_days=" + 365);

        }else if(view.equals(findViewById(R.id.topProductsReportSubmitButton))){

            Pattern pattern = Pattern.compile("[0-9]{4}-[0-1][0-2]-[0-3][0-9]");
            Matcher matcherFrom = pattern.matcher(topProductsFromEditText.getText());
            Matcher matcherTo = pattern.matcher(topProductsToEditText.getText());

            //If the user has a specific start/end date couple in mind, this will run
            if(matcherFrom.matches() && matcherTo.matches()){

                if(!topProductsReportLimitEditText.getText().toString().matches("")) {

                    fetchTopProducts.execute(MainActivity.ngrokURL + "/api/sales_reports/top_products?start_date=" + topProductsFromEditText.getText() + "&end_date=" + topProductsToEditText.getText() + "&result_limit=" + topProductsReportLimitEditText.getText());

                } else {

                    fetchTopProducts.execute(MainActivity.ngrokURL + "/api/sales_reports/top_products?start_date=" + topProductsFromEditText.getText() + "&end_date=" + topProductsToEditText.getText());

                }

            }
            //If he user wishes to provide only a date limit this if will run
            else if(matcherFrom.matches() && topProductsToEditText.getText().toString().matches("")){

                if(!topProductsReportLimitEditText.getText().toString().matches("")) {

                    fetchTopProducts.execute(MainActivity.ngrokURL + "/api/sales_reports/top_products?date_limit=" + topProductsFromEditText.getText() + "&result_limit=" + topProductsReportLimitEditText.getText());

                } else {

                    fetchTopProducts.execute(MainActivity.ngrokURL + "/api/sales_reports/top_products?date_limit=" + topProductsFromEditText.getText());

                }

                //If the user wants a pie chart from the start of records, this if will run
            }
            else if(topProductsFromEditText.getText().toString().matches("") && topProductsToEditText.getText().toString().matches("")){

                fetchTopProducts.execute(MainActivity.ngrokURL + "/api/sales_reports/top_products");

            }
            //Finally, if the date format that is inserted is wrong, a message will appear.
            else{

                Toast.makeText(getApplicationContext(), "Wrong Date Format", Toast.LENGTH_LONG).show();

            }

        }else if(view.equals(findViewById(R.id.topProductsReportBackButton))){

            finish();

        }

    }
}
