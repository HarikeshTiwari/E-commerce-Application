package com.example.techapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class ProductCart extends AppCompatActivity {

    RecyclerView rvCartPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_cart);

        Intent intent=getIntent();
        String name=intent.getStringExtra("prod_name");

        rvCartPage=findViewById(R.id.rvCartPage);

        //Get data from database and store in arraylist

        ProductDatabase pd=new ProductDatabase(this);
        Cursor cursor=pd.getProductData();
        if(cursor.getCount()==0)
        {
            Toast.makeText(this, "No item present", Toast.LENGTH_SHORT).show();
            return;
        }
        ArrayList<ProductDetails> al = new ArrayList<ProductDetails>();
        while(cursor.moveToNext()){
            al.add(new ProductDetails(cursor.getString(1),cursor.getBlob(2),cursor.getString(3),cursor.getString(4)));
        }

        //Pull data from arraylist and convert into recycler view
        ProductAdapter productAdapter=new ProductAdapter(this, al, new ClickListener2() {
            @Override
            public void onPositionClicked(int position) {
                recreate();
            }
            @Override
            public void onLongClicked(int position) {

            }
        });
        rvCartPage.setAdapter(productAdapter);
        rvCartPage.setLayoutManager(new LinearLayoutManager(this));
    }
}