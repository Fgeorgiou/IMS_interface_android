package com.filippos.ims_interface;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.util.ArrayList;

public class ReportSalesResultsActivity extends AppCompatActivity {

    ArrayList<String> xData = new ArrayList<>();
    float[] yData = {};
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_sales_results);

        Bundle b = getIntent().getExtras();

        if (null != b) {

            xData = b.getStringArrayList("xData");
            yData = b.getFloatArray("yData");

        } else {

            Toast.makeText(getApplicationContext(), "No Data Found.", Toast.LENGTH_LONG).show();

        }

        barChart = findViewById(R.id.barChart);
        barChart.setDescription(null);

        addDataSet();

    }

    private void addDataSet() {

        ArrayList<BarEntry> yEntries = new ArrayList<>();

        for(int i = 0; i < yData.length; i++){
            yEntries.add(new BarEntry(i + 0.4f, yData[i]));
        }

        int groupCount = yEntries.size();

        //add colors to dataset
        BarDataSet barDataSet = new BarDataSet(yEntries, "Sales / Day");
        barDataSet.setValueTextSize(12);

        //add colors to dataset
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        //create bar data object
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.invalidate();

        //add legend to chart
        Legend legend = barChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new MyXAxisValueFormatter(xData));
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
    }

    public class MyXAxisValueFormatter implements IAxisValueFormatter{

        private  ArrayList<String> mDates;

        public MyXAxisValueFormatter(ArrayList<String> values) {
            this.mDates = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mDates.get((int)value);
        }
    }
}
