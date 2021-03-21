package com.SidStudio.ARay.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.SidStudio.ARay.DashboardActivity;
import com.SidStudio.ARay.R;

public class AdminAddItem extends AppCompatActivity {

    LinearLayout eyeglasses, sunglasses, readingglasses, powerglasses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_item);

        eyeglasses = findViewById(R.id.add_eyeglasses);
        sunglasses = findViewById(R.id.add_sunglasses);
        readingglasses = findViewById(R.id.add_readingglasses);
        powerglasses = findViewById(R.id.add_powerglasses);


        eyeglasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminAddProduct.class);
                intent.putExtra("glassType","Eyeglasses");
                startActivity(intent);
            }
        });

        sunglasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminAddProduct.class);
                intent.putExtra("glassType","Sunglasses");
                startActivity(intent);
            }
        });

        readingglasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminAddProduct.class);
                intent.putExtra("glassType","Reading Glasses");
                startActivity(intent);
            }
        });

        powerglasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminAddProduct.class);
                intent.putExtra("glassType","Power Glasses");
                startActivity(intent);
            }
        });
    }

}