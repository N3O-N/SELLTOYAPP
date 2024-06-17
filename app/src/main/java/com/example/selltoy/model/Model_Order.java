package com.example.selltoy.model;

import com.google.gson.annotations.SerializedName;

public class Model_Order {

    @SerializedName("Seq")
    private int Seq;

    @SerializedName("Customer_ID")
    private int Customer_ID;

    @SerializedName("Order_ID")
    private int Order_ID;

    @SerializedName("order_ID")
    private int order_ID;

    @SerializedName("Quantity")
    private int  Quantity;

    @SerializedName("Product_Name")
    private String Product_Name;
    @SerializedName("Product_image")
    private String Product_image ;

    @SerializedName("Product_PriceUnit")
    private float Product_PriceUnit;



    // Constructor
    public Model_Order(int Seq,int Customer_ID, int order_ID,int Order_ID,int Quantity, String Product_Name, String Product_image, float Product_PriceUnit) {
        this.Seq = Seq;
        this.Customer_ID = Customer_ID;
        this.order_ID = order_ID;
        this.Order_ID = Order_ID;
        this.Quantity = Quantity;
        this.Product_Name = Product_Name;
        this.Product_image = Product_image;
        this.Product_PriceUnit = Product_PriceUnit;
    }

    public int getSeq() {
        return Seq;
    }

    public void setSeq(int seq) {
        this.Seq = seq;
    }

    public int getCustomer_ID() {
        return Customer_ID;
    }

    public void setCustomer_ID(int customer_ID) {
        this.Customer_ID = customer_ID;
    }

    public int get_Order_ID() {
        return order_ID;
    }

    public void set_Order_ID(int order_ID) {
        this.order_ID = order_ID;
    }

    public int getOrder_ID() {
        return Order_ID;
    }

    public void setOrder_ID(int order_ID) {
        Order_ID = order_ID;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getProduct_Name() {
        return Product_Name;
    }

    public void setProduct_Name(String product_Name) {
        this.Product_Name = product_Name;
    }

    public String getProduct_image() {
        return Product_image;
    }

    public void setProduct_image(String product_image) {
        this.Product_image = product_image;
    }

    public double getProduct_PriceUnit() {
        return Product_PriceUnit;
    }

    public void setProduct_PriceUnit(float product_PriceUnit) {
        this.Product_PriceUnit = product_PriceUnit;
    }
}
