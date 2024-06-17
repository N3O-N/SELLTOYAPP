package com.example.selltoy.model;

import com.google.gson.annotations.SerializedName;

public class Moddel_user {

    private String Customer_Name ,Customer_Password ,Customer_Sex ,Customer_BirthDay ,CusType_Name;
    private float DiscountPercent;

    @SerializedName("Customer_ID")
    private int Customer_ID;

    public Moddel_user(String customer_Name, String customer_Password, String customer_Sex, String customer_BirthDay, String cusType_Name, float discountPercent, int customer_ID) {
        this.Customer_Name = customer_Name;
        this.Customer_Password = customer_Password;
        this.Customer_Sex = customer_Sex;
        this.Customer_BirthDay = customer_BirthDay;
        this.CusType_Name = cusType_Name;
        this.DiscountPercent = discountPercent;
        this.Customer_ID = customer_ID;
    }

    public String getCustomer_Name() {
        return Customer_Name;
    }

    public void setCustomer_Name(String customer_Name) {
        Customer_Name = customer_Name;
    }

    public String getCustomer_Password() {
        return Customer_Password;
    }

    public void setCustomer_Password(String customer_Password) {
        Customer_Password = customer_Password;
    }

    public String getCustomer_Sex() {
        return Customer_Sex;
    }

    public void setCustomer_Sex(String customer_Sex) {
        Customer_Sex = customer_Sex;
    }

    public String getCustomer_BirthDay() {
        return Customer_BirthDay;
    }

    public void setCustomer_BirthDay(String customer_BirthDay) {
        Customer_BirthDay = customer_BirthDay;
    }

    public String getCusType_Name() {
        return CusType_Name;
    }

    public void setCusType_Name(String cusType_Name) {
        CusType_Name = cusType_Name;
    }

    public float getDiscountPercent() {
        return DiscountPercent;
    }

    public void setDiscountPercent(float discountPercent) {
        DiscountPercent = discountPercent;
    }

    public int getCustomer_ID() {
        return Customer_ID;
    }

    public void setCustomer_ID(int customer_ID) {
        Customer_ID = customer_ID;
    }
}
