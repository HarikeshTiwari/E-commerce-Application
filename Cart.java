package com.example.techapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    ImageView img,img1,img2;

    TextView tvNameInsert;

    RecyclerView rvRecyclerOrderPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //Initialize all views
        img=findViewById(R.id.img);
        img1=findViewById(R.id.img1);
        img2=findViewById(R.id.img2);

        rvRecyclerOrderPage=findViewById(R.id.rvRecyclerOrderPage);

        //Get data from database and store in arraylist
        ProductDatabase pd=new ProductDatabase(this);
        Cursor cursor=pd.getDataFromDeleivery();
        if(cursor.getCount()==0)
        {
            Toast.makeText(this, "No item present", Toast.LENGTH_SHORT).show();
            return;
        }
        ArrayList<OrderDetails> al2 = new ArrayList<>();
        while(cursor.moveToNext()){
            al2.add(new OrderDetails(cursor.getString(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3),cursor.getInt(4),cursor.getInt(5)));
        }

        //Pull data from arraylist and convert into recycler view
        OrderPageAdapter orderPageAdapter=new OrderPageAdapter(this, al2, new ClickListener() {
            @Override
            public void onPositionClicked(int position) {
                recreate();
            }
            @Override
            public void onLongClicked(int position) {

            }
        });

        rvRecyclerOrderPage.setAdapter(orderPageAdapter);
        rvRecyclerOrderPage.setLayoutManager(new LinearLayoutManager(this));

        //Navigation on explore,dashboard and profile section

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),DashBoard.class);
                startActivity(intent);
            }
        });
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"You are at dashboard section",Toast.LENGTH_SHORT).show();
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Profile.class);
                startActivity(intent);
            }
        });
    }
}