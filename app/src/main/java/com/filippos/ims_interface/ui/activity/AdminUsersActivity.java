package com.filippos.ims_interface.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.filippos.ims_interface.R;
import com.filippos.ims_interface.data.remote.GetAsyncTask;
import com.filippos.ims_interface.ui.adapter.UserAdapter;
import com.filippos.ims_interface.data.model.User;
import com.filippos.ims_interface.util.HttpMethod;
import com.filippos.ims_interface.util.RestCallUtil;

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

    //TODO: Find a way to refresh the list from out of context. Possibly a callback(?).
    public class DownloadUsers extends GetAsyncTask {

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

    /*
    This AsyncTask is responsible for deleting records from the current order
    The doInBackground method will download all the data and hit the delete endpoint of the orders, marking as deleted the item.
    The onPostExecute will take over once the task is finished and will remove the item from the ArrayList, refresh the List view and inform the user with a toast.
    */
    //TODO: Find a way to refresh the list from out of context. Possibly a callback(?).
    public class DeleteTask extends GetAsyncTask {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Toast.makeText(getApplicationContext(), "User was marked as inactive successfully!", Toast.LENGTH_LONG).show();

            refreshListView();
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

    public void adminActionsNavigator(View view) {

        if (view.equals(findViewById(R.id.newUserButton))) {

            Intent intent = new Intent(this, AddUsersActivity.class);
            startActivity(intent);

        } else if (view.equals(findViewById(R.id.usersBackButton))){

            finish();

        }
    }
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
                            .setMessage("Mark user as inactive?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    DeleteTask deleteTask = new DeleteTask();
                                    deleteTask.execute(MainActivity.ngrokURL + "/api/users/delete/" + userArrayList.get(itemToDelete).id);

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
