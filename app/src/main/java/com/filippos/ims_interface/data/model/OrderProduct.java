package com.filippos.ims_interface.data.model;

public class OrderProduct {

    public int id;
    public int order_id;
    public int product_id;
    public int status_id;
    public int quantity;
    public String product_name;

    public OrderProduct(int id, int order_id, int product_id , int status_id, int quantity, String product_name) {

        this.id = id;
        this.order_id = order_id;
        this.product_id = product_id;
        this.status_id = status_id;
        this.quantity = quantity;
        this.product_name = product_name;
    }
}
