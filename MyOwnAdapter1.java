package com.example.techapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;

public class MyOwnAdapter1 extends RecyclerView.Adapter<MyOwnAdapter1.MyOwnHolder>  implements Filterable {

    Context ctx;

    ArrayList<Model> arrayList;

    ArrayList<Model> backup;

    private final ClickListener listener;

    public MyOwnAdapter1(Context ct, ArrayList<Model> arrayList, ClickListener listener) {
        ctx=ct;
        this.arrayList=arrayList;
        backup= new ArrayList<>();
        backup.addAll(arrayList);
        this.listener = listener;
    }
    @NonNull
    @Override
    public MyOwnAdapter1.MyOwnHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater=LayoutInflater.from(ctx);
        View myview=mInflater.inflate(R.layout.product,parent,false);
        return new MyOwnHolder(myview);
    }
    @Override
    public void onBindViewHolder(@NonNull MyOwnAdapter1.MyOwnHolder holder, int position) {
        Model model=arrayList.get(position);
        holder.tv1.setText(model.getProd_name());
       // byte[] img=model.getProd_image();
        //Bitmap bitmap=BitmapFactory.decodeByteArray(img,0,img.length);
        holder.i1.setImageResource(model.getProd_image());

    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Model> filteredata=new ArrayList<>();
            if(constraint.toString().isEmpty()){
                filteredata.addAll(backup);
            }
            else{
                for(Model obj:backup){
                    if(obj.getProd_name().toString().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredata.add(obj);
                    }
                }

            }
            FilterResults results=new FilterResults();
            results.values=filteredata;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            arrayList.clear();
            arrayList.addAll((Collection<? extends Model>) results.values);
            notifyDataSetChanged();
        }
    };

    //Recycler View Holder Class
    public class MyOwnHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tv1;

        Button b1;

        ImageView i1;

        WeakReference<ClickListener> listenerRef;

        public MyOwnHolder(@NonNull View itemView) {
            super(itemView);
            listenerRef = new WeakReference<>(listener);
            tv1=itemView.findViewById(R.id.prod_name);
            i1=itemView.findViewById(R.id.prod_image);
            b1=itemView.findViewById(R.id.buy_product);

            itemView.setOnClickListener(this);
            b1.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if (v.getId() == b1.getId()) {
                int position=getAdapterPosition();
                Bitmap bmp=((BitmapDrawable)i1.getDrawable()).getBitmap();
                ByteArrayOutputStream stream=new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG,100,stream);
                byte[] byteArray=stream.toByteArray();

                String[] prodName=tv1.getText().toString().split(" ");
                String productName=prodName[0];
                String productPrice=prodName[1];

                //sending product name,product price,product description and product image
                Intent productPage = new Intent(ctx.getApplicationContext(), ProductPage.class);
                productPage.putExtra("prodName", productName);
                productPage.putExtra("prodPrice",productPrice);
                productPage.putExtra("prodImage", byteArray);
                ctx.startActivity(productPage);

            }
            listener.onPositionClicked(getAdapterPosition());
        }
    }
}
