package com.example.techapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

interface ClickListener2 {

    void onPositionClicked(int position);

    void onLongClicked(int position);
}

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {

    private Context ctx;

    private ArrayList<ProductDetails> al;

    ClickListener2 listener;

    public ProductAdapter(Context ctx, ArrayList<ProductDetails> al,ClickListener2 listener) {
        this.al=al;
        this.ctx=ctx;
        this.listener=listener;
    }
    @NonNull
    @Override
    public ProductAdapter.ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater=LayoutInflater.from(ctx);
        View v=mInflater.inflate(R.layout.cartproduct,parent,false);
        return new ProductHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        ProductDetails pdName=al.get(position);
        byte[] bytes=pdName.getProduct_image();
        Bitmap bmp= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        holder.tv1.setText(pdName.getProduct_name());
        holder.tv2.setText(pdName.getProduct_description());
        holder.tvPriceValue.setText(pdName.getProduct_price());
        holder.iv1.setImageDrawable(new BitmapDrawable(ctx.getResources(),bmp));
    }
    @Override
    public int getItemCount() {
        return al.size();
    }

    public  class ProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tv1,tv2,tvQuantityNumber,tvPriceValue;

        Button b1,b2;

        ImageButton imgBtnIncrement,imgBtnDecrement;

        ImageView iv1;

        int increment=1;

        String mainString="";

        @SuppressLint("SetTextI18n")
        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tvProductName);
            tv2=itemView.findViewById(R.id.tvProductDescription);
            tvPriceValue=itemView.findViewById(R.id.tvPriceValue);
            tvQuantityNumber=itemView.findViewById(R.id.tvQuantityNumber);
            iv1=itemView.findViewById(R.id.ivProduct);
            b1 = itemView.findViewById(R.id.btnDelete);
            b2=itemView.findViewById(R.id.btnBuy);
            imgBtnIncrement=itemView.findViewById(R.id.imgBtnIncrement);
            imgBtnDecrement=itemView.findViewById(R.id.imgBtnDecrement);
            b1.setOnClickListener(this);
            b2.setOnClickListener(this);

            tvQuantityNumber.setText(" " + 1);
            //increment quantity in cart

            imgBtnIncrement.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {
                    if (increment >= 10) {
                        Toast.makeText(ctx, "You Exceeded Maximum Quantity", Toast.LENGTH_LONG).show();
                    } else {
                        if(increment==1){
                            mainString=tvPriceValue.getText().toString();
                        }
                        tvQuantityNumber.setText(" " + ++increment);
                        StringBuilder newString = new StringBuilder();
                        //separate $ symbol and price
                        for (int i = 0; i < mainString.length(); i++) {
                            if (i == 0) {
                                continue;
                            }
                            else {
                                newString.append(mainString.charAt(i));
                            }
                        }
                        int price = Integer.parseInt(newString.toString());
                        tvPriceValue.setText("$" + price * increment);
                        ProductDatabase pd=new ProductDatabase(ctx);
                        pd.updateProduct(tv1.getText().toString(),tvPriceValue.getText().toString(),increment);
                    }
                }
            });
            //decrement quantity in cart
            imgBtnDecrement.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {
                    if(increment<=1) {
                        tvQuantityNumber.setText(" " + 1);
                    }
                    else {
                        if(increment==1){
                            mainString=tvPriceValue.getText().toString();
                        }
                        StringBuilder newString=new StringBuilder();
                        for(int i=0;i<mainString.length();i++){
                            if(i==0){
                                continue;

                            }
                            else {
                                newString.append(mainString.charAt(i));
                            }
                        }
                        int price=Integer.parseInt(newString.toString());
                        tvPriceValue.setText("$" + ((price * increment) - price));
                        tvQuantityNumber.setText(" " + --increment);
                        ProductDatabase pd=new ProductDatabase(ctx);
                        pd.updateProduct(tv1.getText().toString(),tvPriceValue.getText().toString(),increment);
                    }
                }
            });

        }
        @Override
        public void onClick(View v) {

            if(v.getId()==b1.getId()) {
                ProductDatabase pd = new ProductDatabase(ctx);
                boolean b = pd.deletedata(tv1.getText().toString());
                listener.onPositionClicked(getAdapterPosition());
            }
            else if(v.getId()==b2.getId()){
                Intent dashboardIntent=new Intent(ctx,Cart.class);
                ProductDatabase pd = new ProductDatabase(ctx);
                String value=tvPriceValue.getText().toString();
                String newString = "";
                for (int i = 0; i < value.length(); i++) {
                    if (i == 0) {
                        continue;
                    } else {
                        newString += value.charAt(i);
                    }
                }
                int total=Integer.parseInt(newString);
                int singleItemPrice=total/increment;
                pd.insertDeleivery(tv1.getText().toString(),tvPriceValue.getText().toString(),increment,singleItemPrice,10,total+10);
                ctx.startActivity(dashboardIntent);
            }
        }
    }
}
