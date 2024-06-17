package com.example.selltoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.selltoy.model.Moddel_user;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button buttonGoToProductList;
    private MyApi myApi;
    private EditText editText_Name, editText_pass;

    private String BaseUrl = "https://n30apiapp.000webhostapp.com/product_data/";

    private ArrayList<Moddel_user> moddel_userArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        buttonGoToProductList = findViewById(R.id.button);

        buttonGoToProductList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display();
            }
        });
    }

    private void display() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        myApi = retrofit.create(MyApi.class);
        Call<ArrayList<Moddel_user>> modelCall = myApi.model_UserArray();

        editText_Name = findViewById(R.id.User);
        editText_pass = findViewById(R.id.Password);

        modelCall.enqueue(new Callback<ArrayList<Moddel_user>>() {
            @Override
            public void onResponse(Call<ArrayList<Moddel_user>> call, Response<ArrayList<Moddel_user>> response) {
                if (response.isSuccessful()) {
                    moddel_userArrayList = response.body();
                    boolean userFound = false;


                    for (Moddel_user user : moddel_userArrayList) {
                        if (user.getCustomer_Name().equals(editText_Name.getText().toString()) && user.getCustomer_Password().equals(editText_pass.getText().toString())) {
                            userFound = true;
                            int userID = user.getCustomer_ID(); // Get the user ID
                            Log.d("userID", String.valueOf(userID));
                            Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
                            intent.putExtra("user_id", userID); // Pass the user ID to ProductListActivity
                            startActivity(intent);
                            break;
                        }
                    }

                    if (!userFound) {
                        Log.d("Invalid Credentials", "Please check your username and password");
                    }
                } else {
                    Log.d("error", response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Moddel_user>> call, Throwable t) {
                Log.d("error", t.getMessage());
            }
        });
    }
}
