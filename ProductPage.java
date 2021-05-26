package com.example.techapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class ProductPage extends AppCompatActivity {

    TextView tvProduct,tvProductPrice,tvProductDescription;

    ImageView imgShow;

    Button btnAsk,btnCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);
        tvProduct=findViewById(R.id.tvProduct);
        tvProductPrice=findViewById(R.id.tvProductPrice);
        tvProductDescription=findViewById(R.id.tvProductDescription);
        imgShow=findViewById(R.id.imgShow);
        btnAsk=findViewById(R.id.btnAsk);
        btnCart=findViewById(R.id.btnCart);


        //taking product name,product price and product image from ProductPage class
        Intent intent=getIntent();
        String prodName=intent.getStringExtra("prodName");
        String prodPrice=intent.getStringExtra("prodPrice");
        byte[] prodImage=getIntent().getByteArrayExtra("prodImage");
        Bitmap bmp= BitmapFactory.decodeByteArray(prodImage,0,prodImage.length);
        tvProduct.setText(prodName);
        tvProductPrice.setText(prodPrice);
        imgShow.setImageDrawable(new BitmapDrawable(getApplicationContext().getResources(),bmp));

        //ProductDatabase Object
        ProductDatabase pd=new ProductDatabase(this);


        btnAsk.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View v) {

                //emailIntent();

                Intent whatsappIntent=new Intent(Intent.ACTION_SEND);

                whatsappIntent.setType("text/plain");

                boolean installed=appInstalledORNot("com.whatsapp");

                if(installed){
                    whatsappIntent.setPackage("com.whatsapp");
                    whatsappIntent.putExtra(Intent.EXTRA_TEXT,"hello");
                }
                else{
                    Toast.makeText(getApplicationContext(),"whatsapp not installed",Toast.LENGTH_LONG).show();
                }
                startActivity(Intent.createChooser(whatsappIntent,"send message"));
            }
        });


        //convert imgShow to byte for storing in databse
        Bitmap bmp2=((BitmapDrawable)imgShow.getDrawable()).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bmp2.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray=stream.toByteArray();

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.insertProduct(tvProduct.getText().toString(),byteArray,tvProductDescription.getText().toString(),tvProductPrice.getText().toString());
                Intent productCart=new Intent(getApplicationContext(),ProductCart.class);
                startActivity(productCart);
            }
        });
    }


    private void emailIntent(){
        Intent emailIntent=new Intent(Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,"I Should buy or not?");
        emailIntent.putExtra(Intent.EXTRA_TEXT,"Hello,");
        startActivity(Intent.createChooser(emailIntent,"Send Message"));
    }
    private boolean appInstalledORNot(String url){
        PackageManager pm=getPackageManager();
        boolean app_installed;
        try {
            PackageInfo packageInfo = pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            app_installed=true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed=false;
        }

        return app_installed;
    }

}