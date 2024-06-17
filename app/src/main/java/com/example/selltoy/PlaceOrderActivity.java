package com.example.selltoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.squareup.picasso.Picasso;

public class PlaceOrderActivity extends AppCompatActivity {

    private TextView textView;
    private EditText editText;
    private Button button;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        textView = findViewById(R.id.textView_total_price);
        editText = findViewById(R.id.editText_address);

        button = findViewById(R.id.button_CONFIRM);
        imageView = findViewById(R.id.imageView_payment);

        Intent intent = getIntent();

        float totalPrice = intent.getExtras().getFloat("totalPrice");
        textView.setText("$"+String.valueOf(totalPrice));

        String imageUrl = "https://n30apiapp.000webhostapp.com/product_data/photo/SELLTOY_qrcode.png";
        Picasso.get().load(imageUrl).into(imageView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // สร้าง AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(PlaceOrderActivity.this);
                builder.setTitle("การสั่งซื้อเสร็จสมบูรณ์");
                builder.setMessage("ขอบคุณที่สั่งซื้อสินค้า");
                builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // ปิดหน้าต่าง AlertDialog
                        dialog.dismiss();

                        // เรียกใช้เมทอด finish() เพื่อปิด Activity ปัจจุบัน (PlaceOrderActivity)
                        finish();

                        Intent intent = new Intent(PlaceOrderActivity.this, ProductListActivity.class);
                        startActivity(intent);
                    }
                });

                // แสดงหน้าต่าง AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
}