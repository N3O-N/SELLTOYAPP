package com.example.selltoy.model;

import com.google.gson.annotations.SerializedName;

public class Model_Product {
    private int Product_ID;
    private float Product_PriceUnit;

    @SerializedName("userflg")
    private String Product_UsedFlg;

    @SerializedName("Product_Name")
    private String Product_Name;

    @SerializedName("Product_image")
    private String Product_image;

    public Model_Product(int product_ID, float product_PriceUnit, String product_UsedFlg, String product_Name, String product_image) {
        this.Product_ID = product_ID;

        this.Product_PriceUnit = product_PriceUnit;

        this.Product_UsedFlg = product_UsedFlg;
        this.Product_Name = product_Name;
        this.Product_image = product_image;
    }

    public int getProduct_ID() {
        return Product_ID;
    }

    public void setProduct_ID(int product_ID) {
        Product_ID = product_ID;
    }

    public float getProduct_PriceUnit() {
        return Product_PriceUnit;
    }

    public void setProduct_PriceUnit(float product_PriceUnit) {
        Product_PriceUnit = product_PriceUnit;
    }


    public String getProduct_UsedFlg() {
        return Product_UsedFlg;
    }

    public void setProduct_UsedFlg(String product_UsedFlg) {
        Product_UsedFlg = product_UsedFlg;
    }

    public String getProduct_Name() {
        return Product_Name;
    }

    public void setProduct_Name(String product_Name) {
        Product_Name = product_Name;
    }

    public String getProduct_image() {
        return Product_image;
    }

    public void setProduct_image(String product_image) {
        Product_image = product_image;
    }
}

