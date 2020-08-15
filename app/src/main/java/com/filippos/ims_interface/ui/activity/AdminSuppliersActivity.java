package com.filippos.ims_interface.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.filippos.ims_interface.R;

public class AdminSuppliersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_suppliers);
    }

    public void backToAdminActions(View view){
        finish();
    }
}
