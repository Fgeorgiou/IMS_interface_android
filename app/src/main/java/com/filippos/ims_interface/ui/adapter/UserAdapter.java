package com.filippos.ims_interface.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.filippos.ims_interface.R;
import com.filippos.ims_interface.data.model.User;

import java.util.ArrayList;

public class UserAdapter extends ArrayAdapter<User> {

    Context mContext;
    int mLayoutResourceId;
    ArrayList<User> mUser = null;

    public UserAdapter(@NonNull Context context, int resource, @NonNull ArrayList<User> data) {
        super(context, resource, data);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.mUser = data;
    }

    @Nullable
    @Override
    public User getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        //inflate the layout for a single user_row
        LayoutInflater inflater = LayoutInflater.from(mContext);
        row = inflater.inflate(mLayoutResourceId, parent, false);

        //get a reference to the different view elements we wish to update
        TextView rowUserFullName = row.findViewById(R.id.rowUserFullName);
        TextView rowUserFacility = row.findViewById(R.id.rowUserFacility);
        TextView rowUserRoleAndAccessLevel = row.findViewById(R.id.rowUserRoleAndAccessLevel);

        //get the data from the data array
        User user = mUser.get(position);

        //setting the view to reflect the data we need to display
        rowUserFullName.setText(user.first_name + " " + user.last_name);
        rowUserFacility.setText(user.facility_name);
        rowUserRoleAndAccessLevel.setText(user.role_description + ", " + Integer.toString(user.access_level));

        //returning the user_row view
        return row;
    }
}
