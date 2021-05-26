package com.example.techapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

interface ClickListener {

    void onPositionClicked(int position);

    void onLongClicked(int position);
}

public class OrderPageAdapter extends RecyclerView.Adapter<OrderPageAdapter.OrderDetailsHolder>{

    private Context ctx;

    private ArrayList<OrderDetails> al2;

    ClickListener listener;

    public OrderPageAdapter(Context ctx, ArrayList<OrderDetails> al2, ClickListener listener) {
        this.ctx = ctx;
        this.al2 = al2;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderPageAdapter.OrderDetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater=LayoutInflater.from(ctx);
        View v=mInflater.inflate(R.layout.ordersummary,parent,false);
        return new OrderPageAdapter.OrderDetailsHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderDetailsHolder holder, int position) {
        OrderDetails orderDetails=al2.get(position);
        holder.tvNameValue.setText(orderDetails.getProd_name());
        holder.tvPriceValue.setText("$"+orderDetails.getProd_singlePrice());
        holder.tvQuantityValue.setText(" "+orderDetails.getProd_quantity());
        holder.tvTotalValue.setText(orderDetails.getProd_price());
        holder.tvSubtotalValue.setText(orderDetails.getProd_price());
        holder.tvDeliveryValue.setText("$"+orderDetails.getProd_charges());
        holder.tvTotalpayValue.setText("$"+orderDetails.getProd_totalAmount());
    }

    @Override
    public int getItemCount() {
        return al2.size();
    }

    class OrderDetailsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button btnCancel;

        TextView tvNameValue,tvQuantityValue,tvPriceValue,tvTotalValue,tvSubtotalValue, tvDeliveryValue,tvTotalpayValue;

        @SuppressLint("SetTextI18n")
        public OrderDetailsHolder(@NonNull View itemView) {
            super(itemView);
            tvNameValue=itemView.findViewById(R.id.tvNameValue);
            tvPriceValue = itemView.findViewById(R.id.tvPriceValue);
            tvQuantityValue=itemView.findViewById(R.id.tvQuantityValue);
            tvTotalValue=itemView.findViewById(R.id.tvTotalValue);
            tvSubtotalValue=itemView.findViewById(R.id.tvSubtotalValue);
            tvDeliveryValue=itemView.findViewById(R.id.tvDeliveryValue);
            tvTotalpayValue=itemView.findViewById(R.id.tvTotalpayValue);
            btnCancel=itemView.findViewById(R.id.btnCancel);
            btnCancel.setOnClickListener(this);

            /*

                int quantity = Integer.parseInt(tvQuantityValue.getText().toString());
                String newString = "";
                for (int i = 0; i < tvTotalValue.getText().toString().length(); i++) {
                    if (i == 0) {
                        continue;
                    } else {
                        newString += tvTotalValue.getText().toString().charAt(i);
                    }
                }

                int total = Integer.parseInt(newString);

                singleItemPrice(total, quantity);

             */

        }
        //calculate single item price
        /*
        @SuppressLint("SetTextI18n")
        public void singleItemPrice(int total, int quantity){
            int singleItemRs=total * quantity;
            tvPriceValue.setText(" "+singleItemRs);
        }

         */

        @Override
        public void onClick(View v) {
           if(v.getId()==R.id.btnCancel){
               ProductDatabase pd = new ProductDatabase(ctx);
               pd.deletedataFromDeleivery(tvNameValue.getText().toString());
               listener.onPositionClicked(getAdapterPosition());
           }
        }
    }
}
