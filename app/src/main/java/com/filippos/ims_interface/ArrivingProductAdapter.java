package com.filippos.ims_interface;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ArrivingProductAdapter extends ArrayAdapter<ArrivingProduct> {

    Context mContext;
    int mLayoutResourceId;
    ArrayList<ArrivingProduct> mArrivingProduct = null;

    public ArrivingProductAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ArrivingProduct> data) {
        super(context, resource, data);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.mArrivingProduct = data;
    }

    @Nullable
    @Override
    public ArrivingProduct getItem(int position) {
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
        TextView arrivingOrderProductEan = row.findViewById(R.id.arrivingOrderProductEan);
        TextView arrivingOrderProductQuantity = row.findViewById(R.id.arrivingOrderProductQuantity);

        //get the data from the data array
        ArrivingProduct arrivingProduct = mArrivingProduct.get(position);

        //setting the view to reflect the data we need to display
        arrivingOrderProductEan.setText(arrivingProduct.ean);
        arrivingOrderProductQuantity.setText(Integer.toString(arrivingProduct.quantity));

        //returning the order_product_row view
        return row;
    }

}
