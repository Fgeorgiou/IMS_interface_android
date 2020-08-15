package com.filippos.ims_interface.ui.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.filippos.ims_interface.R;
import com.filippos.ims_interface.data.remote.AddUserTask;
import com.filippos.ims_interface.util.HttpMethod;
import com.filippos.ims_interface.util.RestCallUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AddUsersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_users);
    }

    public void userFormNavigator(View view){

        if(view.equals(findViewById(R.id.userFormSubmitButton))){

            EditText first_name = findViewById(R.id.userFormFirstNameEditText);
            EditText last_name = findViewById(R.id.userFormLastNameEditText);
            EditText facility_id = findViewById(R.id.userFormFacilityIdEditText);
            EditText role_id = findViewById(R.id.userFormRoleIdEditText);
            EditText email = findViewById(R.id.userFormEmailEditText);
            EditText password = findViewById(R.id.userFormPasswordEditText);

            AddUserTask addUser = new AddUserTask(AddUsersActivity.this);
            addUser.execute(MainActivity.ngrokURL + "/api/users/create?first_name=" + first_name.getText() + "&last_name=" + last_name.getText() + "&facility=" + facility_id.getText() + "&role=" + role_id.getText() + "&email=" + email.getText() +  "&password=" + password.getText());

        } else if(view.equals(findViewById(R.id.userFormBackButton))){
            finish();
        }

    }
}
