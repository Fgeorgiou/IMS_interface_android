package com.filippos.ims_interface;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ReportTopProductsResultsActivity extends AppCompatActivity {

    ArrayList<String> xData = new ArrayList<>();
    float[] yData = {};
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_top_products_results);

        Bundle b = getIntent().getExtras();

        if (null != b) {

            xData = b.getStringArrayList("xData");
            yData = b.getFloatArray("yData");

        } else {

            Toast.makeText(getApplicationContext(), "No Data Found.", Toast.LENGTH_LONG).show();

        }

        pieChart = findViewById(R.id.pieChart);
        pieChart.setCenterText("Tap for product info");

        addDataSet();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                int pos1 = Integer.parseInt(h.toString().substring(14, 15));
                String soldProducts = e.toString().substring(17);
                String product = xData.get(pos1);

                Toast.makeText(getApplicationContext(), "Product Name: " + product + "\n Sold: " + soldProducts, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }

    private void addDataSet() {
        ArrayList<PieEntry> yEntries = new ArrayList<>();
        ArrayList<String> xEntries = new ArrayList<>();

        for(int i = 0; i < yData.length; i++){
            yEntries.add(new PieEntry(yData[i]));
        }

        for(int i = 0; i < xData.size(); i++){
            xEntries.add(xData.get(i));
        }

        //add colors to dataset
        PieDataSet pieDataSet = new PieDataSet(yEntries, "Top-selling Products w/ %");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //add colors to dataset
        pieDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);

        //add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setUsePercentValues(true);
        pieChart.setData(pieData);
        pieChart.invalidate();


    }
}
