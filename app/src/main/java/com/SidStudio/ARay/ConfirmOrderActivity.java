package com.SidStudio.ARay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class ConfirmOrderActivity extends AppCompatActivity {

    private String totalAmount = "" ;
    TextView totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_confirm_order);

        totalPrice = findViewById(R.id.total_price);
        totalAmount = getIntent().getStringExtra("Total Price");
        totalPrice.setText(totalAmount);
    }
}