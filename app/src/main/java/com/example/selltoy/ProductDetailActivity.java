package com.example.selltoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductDetailActivity extends AppCompatActivity {

    TextView tv1, tv2;
    ImageView imageView;

    Button button_cart, button_buy;

    MyApi myApi;

    String BaseUrl = "https://n30apiapp.000webhostapp.com/product_data/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        imageView = findViewById(R.id.Item_image_detail);
        tv1 = findViewById(R.id.textView_Name_product);
        tv2 = findViewById(R.id.textView_Name_price);
        button_buy = findViewById(R.id.buttonBuy);
        button_cart = findViewById(R.id.buttonCart);

        // Retrieve the userID from the Intent
        Intent intent = getIntent();
        String Image = intent.getExtras().getString("Image");
        String Name = intent.getExtras().getString("Name");
        float price = intent.getExtras().getFloat("Price");
        int productId = intent.getExtras().getInt("productId");
        int quantity = intent.getExtras().getInt("quantity");
        int customerId = intent.getExtras().getInt("userID"); // Use a default value if "user_id" is not found

        Log.d("TAG", String.valueOf(price));

        Picasso.get()
                .load(Image)
                .error(R.mipmap.ic_launcher)
                .into(imageView);

        tv1.setText(Name);
        tv2.setText("$" + String.valueOf(price));

        button_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use the userID here as needed
                Log.d("TAG", "userID: " +customerId +"productidID: "+productId+"qty: "+quantity);
                Intent intent_to_cart = new Intent(ProductDetailActivity.this,CartActivity.class);
                intent_to_cart.putExtra("user_id", customerId);
                sendToDatabase(customerId, productId, quantity);
            }
        });

    }

    private void sendToDatabase(int customerId, int productId, int quantity) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        myApi = retrofit.create(MyApi.class);

        Call<Void> call = myApi.sendToDatabase(customerId, productId, quantity);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // ส่งค่าไปยังฐานข้อมูลสำเร็จ
                    Toast.makeText(ProductDetailActivity.this, "Data sent to database successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // เกิดข้อผิดพลาดในการส่งค่าไปยังฐานข้อมูล
                    Toast.makeText(ProductDetailActivity.this, "Failed to send data to database", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // เกิดข้อผิดพลาดในการเชื่อมต่อหรือส่งค่า
                Toast.makeText(ProductDetailActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
