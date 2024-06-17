package com.example.selltoy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.selltoy.model.Model_Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductListActivity extends AppCompatActivity {

    MyAdapter_Product myAdapterProduct;
    MyApi myApi;

    String BaseUrl = "https://n30apiapp.000webhostapp.com/product_data/";

    RecyclerView recyclerView;

    ArrayList<Model_Product> modelProductArrayList;

    private BottomNavigationView bottomNavigationView;

    private int userID; // Declare the userID variable

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        modelProductArrayList = new ArrayList<>();

        Intent intent = getIntent();
        userID = intent.getIntExtra("user_id", 0);
        setUserID(userID);
        Log.d("userID", String.valueOf(userID));


        // Initialize the bottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_cart) {
                // Start the CartActivity
                Intent cartIntent = new Intent(ProductListActivity.this, CartActivity.class);
                cartIntent.putExtra("user_id", userID); // Pass the user ID to CartActivity
                startActivity(cartIntent);
                return true;
            }
            return false;
        });

        // Fetch and display JSON data
        displayJsonData();
    }

    private void displayJsonData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BaseUrl).addConverterFactory(GsonConverterFactory.create()).build();

        myApi = retrofit.create(MyApi.class);
        Call<ArrayList<Model_Product>> modelCall = myApi.model_ProductArray();
        modelCall.enqueue(new Callback<ArrayList<Model_Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Model_Product>> call, Response<ArrayList<Model_Product>> response) {
                if (response.isSuccessful()) {
                    recyclerView = findViewById(R.id.Item_rv);
                    modelProductArrayList = response.body();
                    myAdapterProduct = new MyAdapter_Product(modelProductArrayList, ProductListActivity.this ,userID);
                    recyclerView.setLayoutManager(new GridLayoutManager(ProductListActivity.this, 2));
                    recyclerView.setAdapter(myAdapterProduct);
                } else {
                    Toast.makeText(ProductListActivity.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Model_Product>> call, Throwable t) {
                Toast.makeText(ProductListActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("error", t.getMessage());
            }
        });
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
