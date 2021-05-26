package com.example.techapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;

public class MyOwnAdapter2 extends RecyclerView.Adapter<MyOwnAdapter2.MyOwnHolder> implements Filterable {

    Context ctx;

    ArrayList<Model> arrayList2,backup;

    private final ClickListener listener;

    public MyOwnAdapter2(Context ctx,ArrayList<Model> arrayList2, ClickListener listener) {
        this.ctx = ctx;
        this.arrayList2=arrayList2;
        backup=new ArrayList<Model>();
        backup.addAll(arrayList2);
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyOwnAdapter2.MyOwnHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater=LayoutInflater.from(ctx);
        View myview=mInflater.inflate(R.layout.product,parent,false);
        return new MyOwnHolder(myview);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOwnHolder holder, int position) {

        Model model=arrayList2.get(position);
        holder.tv1.setText(model.getProd_name());
        //Bitmap img=model.getProd_image();
       // Bitmap bitmap=BitmapFactory.decodeByteArray(img,0,img.length);
        holder.i1.setImageResource(model.getProd_image());
    }

    @Override
    public int getItemCount() {
        return arrayList2.size();
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
            arrayList2.clear();
            arrayList2.addAll((Collection<? extends Model>) results.values);
            notifyDataSetChanged();
        }
    };

    public class MyOwnHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv1,tv2;
        Button b1,b2;
        ImageView i1,i2;
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
                if(position==0){
                    Toast.makeText(v.getContext(), "Band clicked " , Toast.LENGTH_SHORT).show();
                }
                else if(position==1){
                    Toast.makeText(v.getContext(), "Sensor clicked " , Toast.LENGTH_SHORT).show();
                }
            }

            listenerRef.get().onPositionClicked(getAdapterPosition());
        }

    }
}
