package com.filippos.ims_interface;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ScanProductsActivity extends AppCompatActivity {

    public void scannedProductNavigator(View view){

        Button scannedProductScanButton = findViewById(R.id.scannedProductScanButton);
        Button scannedProductAddButton = findViewById(R.id.scannedProductAddButton);
        Button scannedProductBackButton = findViewById(R.id.scannedProductBackButton);

        if (scannedProductScanButton.equals(view)) {
            try {

                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "PRODUCT_MODE");//for Qr code, its "QR_CODE_MODE" instead of "PRODUCT_MODE"
                intent.putExtra("SAVE_HISTORY", false);//this stops saving ur barcode in barcode scanner app's history
                startActivityForResult(intent, 0);

            }catch(Exception e){

                Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
                Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
                startActivity(marketIntent);

            }
        }
        else if(scannedProductAddButton.equals(view)){
            Toast.makeText(getApplicationContext(), "Will Add To Selection", Toast.LENGTH_LONG).show();
//            Intent intent = new Intent(ScanProductsActivity.this, ScanProductsActivity.class);
//            startActivity(intent);
        }
        else if(scannedProductBackButton.equals(view)){
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_products);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT"); //this is the result
                Toast.makeText(getApplicationContext(), contents, Toast.LENGTH_LONG).show();
            } else
            if (resultCode == RESULT_CANCELED) {
                // Handle cancel
            }
        }
    }

}
