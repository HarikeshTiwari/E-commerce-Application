package com.example.techapp;

import android.graphics.Bitmap;

public class Model {

    private String prod_name;
    private int prod_image;

    public Model(String prod_name, int prod_image) {
        this.prod_name = prod_name;
        this.prod_image = prod_image;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public int getProd_image() {
        return prod_image;
    }

    public void setProd_image(int prod_image) {
        this.prod_image = prod_image;
    }
}
