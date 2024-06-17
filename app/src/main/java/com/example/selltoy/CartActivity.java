package com.example.selltoy;

import static com.example.selltoy.R.id.Item_rv_cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.selltoy.model.Model_Order;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView_cart;
    TextView textView_total_price,textView_name ,textView_price;
    Button button;

    ImageView imageView;

    MyAdapter_Cart myAdapterProduct;
    MyApi myApi;

    String BaseUrl = "https://n30apiapp.000webhostapp.com/product_data/";

    ArrayList<Model_Order> modelOrderArrayList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView_cart=findViewById(Item_rv_cart);

        textView_name=findViewById(R.id.Item_Name_Cart_detail);
        textView_price=findViewById(R.id.Item_price);
        imageView =findViewById(R.id.Item_In_Cart_detail);
        button=findViewById(R.id.Place_Order);

        displayJsonData();

    }

    private void displayJsonData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        myApi = retrofit.create(MyApi.class);
        Call<ArrayList<Model_Order>> modelCall = myApi.model_getOrderDetailArray();
        modelCall.enqueue(new Callback<ArrayList<Model_Order>>() {
            @Override
            public void onResponse(Call<ArrayList<Model_Order>> call, Response<ArrayList<Model_Order>> response) {
                if (response.isSuccessful()) {
                    modelOrderArrayList = response.body();

                    ArrayList<Model_Order> filteredList = new ArrayList<>();

                    // Modify the user_id below with the desired user_id to filter
                    Intent intent = getIntent();

                    if (intent != null) {
                        int userID = intent.getIntExtra("user_id",0);
                        Log.d("User ID", String.valueOf(userID));
                        for (Model_Order modelOrder : modelOrderArrayList) {
                            if (modelOrder.getCustomer_ID()  == userID ) {
                                filteredList.add(modelOrder);
                            }
                        }
                    }

                    myAdapterProduct = new MyAdapter_Cart(filteredList, CartActivity.this);

                    // Calculate and set the total price
                    float totalPrice = calculateTotalPrice();

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Calculate the total price
                            float totalPrice = calculateTotalPrice();

                            // Create an Intent to open the PlaceOrderActivity
                            Intent intent = new Intent(CartActivity.this, PlaceOrderActivity.class);
                            intent.putExtra("totalPrice", totalPrice);
                            startActivity(intent);
                        }
                    });

                    LinearLayoutManager layoutManager = new LinearLayoutManager(CartActivity.this, RecyclerView.VERTICAL, false);
                    recyclerView_cart.setLayoutManager(layoutManager);
                    recyclerView_cart.setAdapter(myAdapterProduct);
                } else {
                    Toast.makeText(CartActivity.this, "Failed to retrieve data from API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Model_Order>> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Failed to retrieve data from API", Toast.LENGTH_SHORT).show();
                Log.d("error", t.getMessage());
            }
        });
    }

    public float calculateTotalPrice() {
        float totalPrice = 0.0F;
        for (Model_Order modelOrder : modelOrderArrayList) {
            totalPrice += modelOrder.getProduct_PriceUnit() * modelOrder.getQuantity();
        }
        return totalPrice;
    }


}