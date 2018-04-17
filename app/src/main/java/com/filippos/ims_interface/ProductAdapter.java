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

public class ProductAdapter extends ArrayAdapter<Product> {

    Context mContext;
    int mLayoutResourceId;
    ArrayList<Product> mProduct = null;

    public ProductAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Product> data) {
        super(context, resource, data);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.mProduct = data;
    }

    @Nullable
    @Override
    public Product getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        //inflate the layout for a single product_row
        LayoutInflater inflater = LayoutInflater.from(mContext);
        row = inflater.inflate(mLayoutResourceId, parent, false);

        //get a reference to the different view elements we wish to update
        TextView rowProductName = row.findViewById(R.id.rowProductName);
        TextView rowProductBarcode = row.findViewById(R.id.rowProductBarcode);
        TextView rowProductSupplier = row.findViewById(R.id.rowProductSupplier);
        TextView rowProductStockLevel = row.findViewById(R.id.rowProductStockLevel);

        //get the data from the data array
        Product product = mProduct.get(position);

        //setting the view to reflect the data we need to display
        rowProductName.setText(product.name);
        rowProductBarcode.setText(product.barcode);
        rowProductSupplier.setText(product.supplier);
        rowProductStockLevel.setText(product.stock);

        //returning the product_row view
        return row;
    }

}
