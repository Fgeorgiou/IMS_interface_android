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
import com.filippos.ims_interface.data.model.OrderProduct;

import java.util.ArrayList;

public class OrderProductAdapter extends ArrayAdapter<OrderProduct> {

    Context mContext;
    int mLayoutResourceId;
    ArrayList<OrderProduct> mOrderProduct = null;

    public OrderProductAdapter(@NonNull Context context, int resource, @NonNull ArrayList<OrderProduct> data) {
        super(context, resource, data);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.mOrderProduct = data;
    }

    @Nullable
    @Override
    public OrderProduct getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        //inflate the layout for a single order_product_row
        LayoutInflater inflater = LayoutInflater.from(mContext);
        row = inflater.inflate(mLayoutResourceId, parent, false);

        //get a reference to the different view elements we wish to update
        TextView rowOrderProductName = row.findViewById(R.id.rowOrderProductName);
        TextView rowOrderProductQuantity = row.findViewById(R.id.rowOrderProductQuantity);
        TextView rowOrderProductStatus = row.findViewById(R.id.rowOrderProductStatus);

        //get the data from the data array
        OrderProduct orderProduct = mOrderProduct.get(position);

        //setting the view to reflect the data we need to display
        rowOrderProductName.setText(orderProduct.product_name);
        rowOrderProductQuantity.setText(Integer.toString(orderProduct.quantity));
        rowOrderProductStatus.setText(Integer.toString(orderProduct.status_id));

        //returning the order_product_row view
        return row;
    }
}
