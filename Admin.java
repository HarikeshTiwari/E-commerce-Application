package com.example.techapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Admin extends AppCompatActivity {

    EditText etProductNameAndPrice;

    ImageView imgProduct,imgProduct2;

    Button btnSelectFromGallery,btnAddProduct,btnUpdateProduct,btnDeleteProduct;

    AdminDatabase adminDatabase=new AdminDatabase(this);

    final int REQUEST_CODE=999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        //initilaize all views
        init();

        //get username and validate
        Intent newintent=getIntent();
        String uname=newintent.getStringExtra("user-name");
        if(uname.equals("")) {
            Toast.makeText(getApplicationContext(), "username cannot be empty", Toast.LENGTH_LONG).show();
        }

        //handle choose button from gallery
        btnSelectFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(Admin.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE);
            }
        });
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etProductNameAndPrice.length()!=0){
                    String prod_name=etProductNameAndPrice.getText().toString();
                    byte[] prod_img=convertImageToByte(imgProduct);
                    BitmapDrawable drawable = (BitmapDrawable) imgProduct.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();

                    /*
                    File sdCardDirectory = Environment.getExternalStorageDirectory();
                    // Create a new folder in SD Card
                    File dir = new File(sdCardDirectory.getAbsolutePath() + "/Save Image Tutorial/");
                    dir.mkdirs();
                    File image = new File(dir, "/prod.png");
                    boolean success = false;
                    // Encode the file as a PNG image.
                    FileOutputStream outStream;
                    try {
                        outStream = new FileOutputStream(image);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                        /* 100 to keep full quality of the image */
                    /*
                        outStream.flush();
                        outStream.close();
                        success = true;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (success) {
                        Toast.makeText(getApplicationContext(), "Image saved with success", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Error during image saving", Toast.LENGTH_LONG).show();
                    }
                    */
                   AddData(prod_name,prod_img);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please enter product name",Toast.LENGTH_LONG).show();
                }
            }
        });
        btnUpdateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent exploreIntent=new Intent(getApplicationContext(),DashBoard.class);
                Snackbar.make(v, "Username cannot be empty", Snackbar.LENGTH_LONG);
                startActivity(exploreIntent);
            }
        });
        btnDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AdminDatabase admin2=new AdminDatabase(getApplicationContext());
                boolean b=admin2.deletedata("Jaapam");
                if(b) {
                    Toast.makeText(getApplicationContext(), "deleted record", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Not deleted record", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void AddData(String prod_name,byte[] prod_img) {

        boolean result=adminDatabase.addData(prod_name,prod_img);
        if(result){
            Toast.makeText(getApplicationContext(),"Data inserted successfully",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"Error while inserting",Toast.LENGTH_LONG).show();
        }
    }

    private byte[] convertImageToByte(ImageView imgProduct) {
        Bitmap bitmap=((BitmapDrawable)imgProduct.getDrawable()).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,60,stream);
        byte[] byteArray=stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE);
            }
            else{
                Toast.makeText(getApplicationContext(),"You dont have permission",Toast.LENGTH_LONG).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Uri uri=data.getData();
        try{
            InputStream inputStream=getContentResolver().openInputStream(uri);
            Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
            imgProduct.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void init() {
        etProductNameAndPrice=findViewById(R.id.etProductNameAndPrice);
        imgProduct=findViewById(R.id.imgProduct);
        imgProduct2=findViewById(R.id.imgProduct2);
        btnSelectFromGallery=findViewById(R.id.btnSelectFromGallery);
        btnAddProduct=findViewById(R.id.btnAddProduct);
        btnUpdateProduct=findViewById(R.id.btnUpdateProduct);
        btnDeleteProduct=findViewById(R.id.btnDeleteProduct);
    }
}