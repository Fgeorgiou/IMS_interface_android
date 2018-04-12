package com.filippos.ims_interface;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ScanIncomingOrdersActivity extends AppCompatActivity {

    ArrayList<ArrivingProduct> arrivingProductsArrayList = new ArrayList<>();

    ListView arrivingOrderListView;
    ArrivingProductAdapter arrivingProductAdapter;

    public void scannedOrderNavigator(View view){

        Button scannedOrderScanButton = findViewById(R.id.scannedOrderScanButton);
        Button scannedOrderStockButton = findViewById(R.id.scannedOrderStockButton);
        Button scannedOrderBackButton = findViewById(R.id.scannedOrderBackButton);

        if (scannedOrderScanButton.equals(view)) {
            try {

                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");//for barcode, its "PRODUCT_MODE" instead of "QR_CODE_MODE"
                intent.putExtra("SAVE_HISTORY", false);//this stops saving ur barcode in barcode scanner app's history
                startActivityForResult(intent, 0);

            }catch(Exception e){

                Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
                Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
                startActivity(marketIntent);

            }
        }
        else if(scannedOrderStockButton.equals(view)){
            Toast.makeText(getApplicationContext(), "Will Add To Stock", Toast.LENGTH_LONG).show();
//            Intent intent = new Intent(ScanProductsActivity.this, ScanProductsActivity.class);
//            startActivity(intent);
        }
        else if(scannedOrderBackButton.equals(view)){
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_incoming_orders);

        arrivingOrderListView = findViewById(R.id.arrivingOrderListView);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                if(data != null) {
                    String contents = data.getStringExtra("SCAN_RESULT"); //this is the result
                    String[] splitContents = contents.split(",");

                    arrivingProductsArrayList.clear();

                    for (String splitContent : splitContents) {

                        String[] splitContentPart = splitContent.split("x");

                        arrivingProductsArrayList.add(new ArrivingProduct(splitContentPart[0], Integer.parseInt(splitContentPart[1])));

                    }

                    arrivingProductAdapter = new ArrivingProductAdapter(getApplicationContext(), R.layout.arriving_order_row, arrivingProductsArrayList);

                    arrivingOrderListView.setAdapter(arrivingProductAdapter);

                    arrivingOrderListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                            final int itemToDelete = i;

                            new AlertDialog.Builder(ScanIncomingOrdersActivity.this)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setTitle("Are you sure?")
                                    .setMessage("Delete this item from arriving order?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            arrivingProductsArrayList.remove(itemToDelete);
                                        }
                                    })
                                    .setNegativeButton("No", null)
                                    .show();

                            return true;
                        }
                    });
                    arrivingProductAdapter.notifyDataSetChanged();

                }
            } else
            if (resultCode == RESULT_CANCELED) {
                // Handle cancel
            }
        }
    }
}
