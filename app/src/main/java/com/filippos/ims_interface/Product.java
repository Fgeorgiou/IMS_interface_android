package com.filippos.ims_interface;

public class Product {

    public int id;
    public int order_id;
    public String product_name;
    public int status_id;
    public int quantity;

    public Product(int id, int order_id, String product_name, int status_id, int quantity) {

        this.id = id;
        this.order_id = order_id;
        this.product_name = product_name;
        this.status_id = status_id;
        this.quantity = quantity;
    }


}
