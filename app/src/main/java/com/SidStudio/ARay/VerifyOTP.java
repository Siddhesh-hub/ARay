package com.SidStudio.ARay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class VerifyOTP extends AppCompatActivity {

    TextView phoneTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_verify_o_t_p);

        phoneTxt = findViewById(R.id.otp_phoneNumber);

        String phoneNumber = getIntent().getStringExtra("phoneNumber");
        String countryCode = getIntent().getStringExtra("countryCode");

        String phone = "+" + countryCode + phoneNumber;
        phoneTxt.setText(phone);
    }
}