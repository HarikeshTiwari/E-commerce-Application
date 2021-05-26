package com.example.techapp;

public class OrderDetails {


    private String prod_name,prod_price;

    private int prod_quantity,prod_singlePrice,prod_charges,prod_totalAmount;

    public OrderDetails(String prod_name, String prod_price, int prod_quantity,int prod_singlePrice,int prod_charges,int prod_totalAmount) {
        this.prod_name = prod_name;
        this.prod_price = prod_price;
        this.prod_quantity = prod_quantity;
        this.prod_singlePrice=prod_singlePrice;
        this.prod_charges=prod_charges;
        this.prod_totalAmount=prod_totalAmount;
    }


    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public String getProd_price() {
        return prod_price;
    }

    public void setProd_price(String prod_price) {
        this.prod_price = prod_price;
    }

    public int getProd_quantity() {
        return prod_quantity;
    }

    public void setProd_quantity(int prod_quantity) {
        this.prod_quantity = prod_quantity;
    }

    public int getProd_singlePrice() {
        return prod_singlePrice;
    }

    public void setProd_singlePrice(int prod_singlePrice) {
        this.prod_singlePrice = prod_singlePrice;
    }

    public int getProd_charges() {
        return prod_charges;
    }

    public void setProd_charges(int prod_charges) {
        this.prod_charges = prod_charges;
    }

    public int getProd_totalAmount() {
        return prod_totalAmount;
    }

    public void setProd_totalAmount(int prod_totalAmount) {
        this.prod_totalAmount = prod_totalAmount;
    }
}
