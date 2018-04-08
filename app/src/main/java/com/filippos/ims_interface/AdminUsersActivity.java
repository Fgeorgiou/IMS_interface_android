package com.filippos.ims_interface;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AdminUsersActivity extends AppCompatActivity {

    ArrayList<User> userArrayList = new ArrayList<>();

    ListView userListView;
    UserAdapter userAdapter;

    public class DownloadUsers extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection;

            try {

                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("GET");

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

                if(jsonObject != null){

                    JSONArray jsonUsersArray = jsonObject.getJSONArray("users");

                    for(int i = 0; i < jsonUsersArray.length(); i++) {

                        JSONObject jsonPart = jsonUsersArray.getJSONObject(i);

                        JSONObject jsonFacility = new JSONObject(jsonPart.toString()).getJSONObject("facility");
                        JSONObject jsonRole = new JSONObject(jsonPart.toString()).getJSONObject("role");

                        String facilityName = jsonFacility.getString("name");
                        String roleDescription = jsonRole.getString("description");
                        int access_level = jsonRole.getInt("access_level");

                        userArrayList.add(new User(
                                jsonPart.getInt("id"),
                                jsonPart.getString("first_name"),
                                jsonPart.getString("last_name"),
                                jsonPart.getString("email"),
                                facilityName,
                                roleDescription,
                                access_level
                        ));
                    }
                    refreshListView();
                }


            } catch (JSONException e) {

                Toast.makeText(getApplicationContext(), "Invalid request", Toast.LENGTH_LONG).show();

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_users);

        userListView = findViewById(R.id.usersListView);

        DownloadUsers downloadUsers = new DownloadUsers();
        downloadUsers.execute(MainActivity.ngrokURL + "/api/users");
    }

    public void backToAdminActions(View view){ finish(); }

    public void refreshListView(){

        userAdapter = new UserAdapter(getApplicationContext(), R.layout.user_row, userArrayList);

        if(userAdapter != null) {

            userListView.setAdapter(userAdapter);

            userListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                    final int itemToDelete = i;

                    new AlertDialog.Builder(AdminUsersActivity.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Are you sure?")
                            .setMessage("Delete this item from current order?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    //OrdersActivity.DeleteTask deleteTask = new OrdersActivity.DeleteTask();
                                    //deleteTask.execute(MainActivity.ngrokURL + "/api/users/delete?id="+ userArrayList.get(itemToDelete).id);

                                    userArrayList.remove(itemToDelete);
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();

                    return true;
                }
            });
            userAdapter.notifyDataSetChanged();

        }
    }
}