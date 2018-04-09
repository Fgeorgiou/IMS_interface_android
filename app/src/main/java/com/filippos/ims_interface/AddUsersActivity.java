package com.filippos.ims_interface;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AddUsersActivity extends AppCompatActivity {

    public class AddUser extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection;

            try {

                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("POST");

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

            } catch (IOException e) {

                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {

                JSONObject jsonObject = new JSONObject(result);

                String jsonMessage = jsonObject.getString("message");
                String jsonStatusCode = jsonObject.getString("status");

                Toast.makeText(getApplicationContext(), jsonMessage, Toast.LENGTH_LONG).show();

                if (jsonStatusCode.equals(String.valueOf(201))){
                    finish();
                }

            } catch (JSONException e) {

                Toast.makeText(getApplicationContext(), "Couldn't get a response from the server..", Toast.LENGTH_LONG).show();

            }
        }
    }

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

            AddUser addUser = new AddUser();
            addUser.execute(MainActivity.ngrokURL + "/api/users/create?first_name=" + first_name.getText() + "&last_name=" + last_name.getText() + "&facility=" + facility_id.getText() + "&role=" + role_id.getText() + "&email=" + email.getText() +  "&password=" + password.getText());

        } else if(view.equals(findViewById(R.id.userFormBackButton))){
            finish();
        }

    }
}
