package com.example.selltoy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selltoy.model.Model_Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter_Product extends RecyclerView.Adapter<MyAdapter_Product.ViewHolder> {

    private ArrayList<Model_Product> modelProductArrayList;
    private Context context;
    private int userID; // เพิ่มตัวแปร userID

    public MyAdapter_Product(ArrayList<Model_Product> modelProductArrayList, Context context, int userID) {
        this.modelProductArrayList = modelProductArrayList;
        this.context = context;
        this.userID = userID; // กำหนดค่า userID
    }

    @NonNull
    @Override
    public MyAdapter_Product.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter_Product.ViewHolder holder, int position) {
        Model_Product modelProduct = modelProductArrayList.get(position);
        Picasso.get().load(modelProduct.getProduct_image()).error(R.mipmap.ic_launcher).into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("Name", modelProduct.getProduct_Name());
                intent.putExtra("Image", modelProduct.getProduct_image());
                intent.putExtra("Price", modelProduct.getProduct_PriceUnit());
                intent.putExtra("productId", modelProduct.getProduct_ID());
                intent.putExtra("quantity", 1);
                intent.putExtra("userID", userID); // Pass the user ID to ProductDetailActivity
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelProductArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        CardView cardView;
        TextView textView1, textView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.Item_image);
            textView1 = itemView.findViewById(R.id.textView_Name_product);
            textView2 = itemView.findViewById(R.id.textView_Name_price);
            cardView = itemView.findViewById(R.id.cv);
        }
    }
}
