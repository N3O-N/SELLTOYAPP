package com.example.selltoy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selltoy.model.Model_Order;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyAdapter_Cart extends RecyclerView.Adapter<MyAdapter_Cart.ViewHolder> {

    private ArrayList<Model_Order> modelOrderArrayList;
    private Context context;

    private String BASE_URL = "https://n30apiapp.000webhostapp.com/product_data/";

    public MyAdapter_Cart(ArrayList<Model_Order> modelOrderArrayList, Context context) {
        this.modelOrderArrayList = modelOrderArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapter_Cart.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter_Cart.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Model_Order modelOrder = modelOrderArrayList.get(position);
        Picasso.get().load(modelOrder.getProduct_image()).error(R.mipmap.ic_launcher).into(holder.Item_In_Cart_detail);

        Log.d("ig",modelOrder.getProduct_image());
        holder.textView1.setText(String.format("%.2f", modelOrder.getProduct_PriceUnit()));
        holder.textView2.setText(modelOrder.getProduct_Name());
        holder.item_count.setText(String.valueOf(modelOrder.getQuantity()));
        Log.d("ODSEQ",String.valueOf(modelOrder.get_Order_ID() )+"   "+String.valueOf(modelOrder.getSeq()));


        holder.button_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDataFromDatabase(String.valueOf(modelOrder.get_Order_ID() ),String.valueOf(modelOrder.getSeq() ), position);
            }
        });

        holder.button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                increaseQuantity(modelOrder, position);
            }
        });

        holder.button_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decreaseQuantity(modelOrder, position);
            }
        });

    }


    private void deleteDataFromDatabase(String orderID ,String Seq, int position) {

        Log.d("ODSEQ",orderID+"   "+Seq);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyApi apiService = retrofit.create(MyApi.class);
        Call<Void> call = apiService.deleteData(orderID ,Seq);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // การลบข้อมูลสำเร็จ
                    modelOrderArrayList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, modelOrderArrayList.size());
                } else {
                    // เกิดข้อผิดพลาดในการลบข้อมูล
                    Log.e("DeleteData", "Failed to delete data. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // เกิดข้อผิดพลาดในการเชื่อมต่อกับ API
                Log.e("DeleteData", "Failed to delete data. Error: " + t.getMessage());
            }
        });
    }

    private void increaseQuantity(Model_Order modelOrder, int position) {
        int Seq = modelOrder.getSeq(),order_ID =modelOrder.get_Order_ID();
        int newQuantity = modelOrder.getQuantity() + 1;
        modelOrder.setQuantity(newQuantity); // Update the quantity locally
        updateQuantityInDatabase( Seq , order_ID , newQuantity, position);

    }

    private void decreaseQuantity(Model_Order modelOrder, int position) {
        int Seq = modelOrder.getSeq(),order_ID =modelOrder.get_Order_ID();
        int newQuantity = modelOrder.getQuantity() - 1;
        if (newQuantity >= 0) {
            modelOrder.setQuantity(newQuantity); // Update the quantity locally
            updateQuantityInDatabase( Seq , order_ID , newQuantity, position);
        }
    }



    private void updateQuantityInDatabase( int Seq, int order_ID, int newQuantity, int position) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyApi apiService = retrofit.create(MyApi.class);

        Log.d("FIX",Seq+"    "+order_ID +"   "+newQuantity);

        Call<Void> call = apiService.updateQuantity(Seq, order_ID, newQuantity);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Update the quantity locally
                    modelOrderArrayList.get(position).setQuantity(newQuantity);
                    notifyItemChanged(position);
                } else {
                    // Handle the error
                    Log.e("UpdateQuantity", "Failed to update quantity. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle the failure
                Log.e("UpdateQuantity", "Failed to update quantity. Error: " + t.getMessage());
            }
        });
    }




    @Override
    public int getItemCount() {
        return modelOrderArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView Item_In_Cart_detail;
        TextView textView1, textView2 ,item_count;
        ImageButton button_Delete,button_add,button_remove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Item_In_Cart_detail = itemView.findViewById(R.id.Item_In_Cart_detail);
            textView1 = itemView.findViewById(R.id.Item_price);
            textView2 = itemView.findViewById(R.id.Item_Name_Cart_detail);
            button_Delete =itemView.findViewById(R.id.imageButton_delete);
            item_count =itemView.findViewById(R.id.item_count);
            button_add =itemView.findViewById(R.id.imageButton_add);
            button_remove =itemView.findViewById(R.id.imageButton_remove);

        }
    }
}

