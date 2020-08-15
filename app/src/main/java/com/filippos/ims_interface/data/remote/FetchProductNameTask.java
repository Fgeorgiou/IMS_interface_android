package com.filippos.ims_interface.data.remote;

import android.app.Activity;
import android.widget.TextView;
import android.widget.Toast;

import com.filippos.ims_interface.R;

import org.json.JSONException;
import org.json.JSONObject;

public class FetchProductNameTask extends GetAsyncTask {

    private Activity initialContext;

    public FetchProductNameTask(Activity initialContext){
        this.initialContext = initialContext;
    }

    @Override
    protected void onPostExecute(String result) {

        TextView orderProductRecognitionTextView = initialContext.findViewById(R.id.orderProductRecognitionTextView);
        TextView orderStockTextView = initialContext.findViewById(R.id.orderStockTextView);
        TextView orderSuggestedTextView = initialContext.findViewById(R.id.orderSuggestedTextView);

        try {

            //Getting the whole json response
            JSONObject jsonResponseObject = new JSONObject(result).getJSONObject("response");

            JSONObject jsonProductObject = new JSONObject(jsonResponseObject.toString()).getJSONObject("product");

            String productName = jsonProductObject.getString("name");
            String productSuggestion = jsonResponseObject.getString("suggested_order");

            //Getting the nested stock object
            JSONObject jsonProductStockObject = new JSONObject(jsonProductObject.toString()).getJSONObject("stock");

            String productStock = jsonProductStockObject.getString("quantity");

            //Updating the UI wit response info
            orderProductRecognitionTextView.setText(productName);
            orderStockTextView.setText(productStock);
            orderSuggestedTextView.setText(productSuggestion);

        } catch (JSONException e) {

            orderProductRecognitionTextView.setText("Unknown Barcode");
            orderStockTextView.setText("");
            orderSuggestedTextView.setText("");

            Toast.makeText(initialContext.getApplicationContext(), "Unknown barcode number", Toast.LENGTH_LONG).show();

        }
    }
}
