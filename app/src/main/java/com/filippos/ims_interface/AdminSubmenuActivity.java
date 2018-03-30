package com.filippos.ims_interface;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class AdminSubmenuActivity extends AppCompatActivity {

    public void navigateToAdminActivity(View view){

        Button adminUsersButton = findViewById(R.id.adminUsersButton);
        Button adminSuppliersButton = findViewById(R.id.adminSuppliersButton);
        Button adminProductsButton = findViewById(R.id.adminProductsButton);
        Button adminCategoriesButton = findViewById(R.id.adminCategoriesButton);
        Button adminSubmenuReturnButton = findViewById(R.id.adminSubmenuReturnButton);

        if (adminUsersButton.equals(view)) {
            Log.i("Button pressed", "users");
//            Intent intent = new Intent(AdminSubmenuActivity.this, OrdersSubmenuActivity.class);
//            startActivity(intent);
        }
        else if(adminSuppliersButton.equals(view)){
            Log.i("Button pressed", "suppliers");
//            Intent intent = new Intent(AdminSubmenuActivity.this, ScanItemsActivity.class);
//            startActivity(intent);
        }
        else if(adminProductsButton.equals(view)){
            Log.i("Button pressed", "products");
//            Intent intent = new Intent(AdminSubmenuActivity.this, ReportsSubmenuActivity.class);
//            startActivity(intent);
        }
        else if(adminCategoriesButton.equals(view)){
            Log.i("Button pressed", "categories");
//            Intent intent = new Intent(AdminSubmenuActivity.this, AdminSubmenuActivity.class);
//            startActivity(intent);
        }else if(adminSubmenuReturnButton.equals(view)){
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_submenu);
    }
}
