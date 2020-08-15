package com.filippos.ims_interface.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.filippos.ims_interface.R;

public class HomepageActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    public void navigateToActivity(View view){

        Button ordersButton = findViewById(R.id.ordersButton);
        Button scanItemsButton = findViewById(R.id.scanItemsButton);
        Button reportsButton = findViewById(R.id.reportsButton);
        Button adminActionsButton = findViewById(R.id.adminActionsButton);

        if (ordersButton.equals(view)) {
            Intent intent = new Intent(HomepageActivity.this, OrdersSubmenuActivity.class);
            startActivity(intent);
        }
        else if(scanItemsButton.equals(view)){
            Intent intent = new Intent(HomepageActivity.this, ScanSubmenuActivity.class);
            startActivity(intent);
        }
        else if(reportsButton.equals(view)){
            Intent intent = new Intent(HomepageActivity.this, ReportSubmenuActivity.class);
            startActivity(intent);
        }
        else if(adminActionsButton.equals(view)){
            Intent intent = new Intent(HomepageActivity.this, AdminSubmenuActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        sharedPreferences = this.getSharedPreferences("com.filippos.ims_interface", Context.MODE_PRIVATE);

        TextView welcomeTextView = findViewById(R.id.welcomeTextView);
        TextView accessLevelTextView = findViewById(R.id.accessLevelTextView);

        welcomeTextView.setText("Welcome, " +  sharedPreferences.getString("first_name", "Anon"));
        accessLevelTextView.setText("Access Level: " + sharedPreferences.getString("role_id", "Undefined"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.logout:
                sharedPreferences.edit().clear().commit();

                Intent intent = new Intent(HomepageActivity.this, MainActivity.class);
                startActivity(intent);
            default:
                return false;
        }
    }
}
