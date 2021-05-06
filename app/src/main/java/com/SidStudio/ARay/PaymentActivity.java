package com.SidStudio.ARay;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {

    //Initialize variable
    Button btnPay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        //Assign variable
        btnPay = findViewById(R.id.pay_btn);

        //Initialize amount
        String amountToPay = getIntent().getStringExtra("totalAmount").toString();

        //Convert and round off
        int amount = Math.round(Float.parseFloat(amountToPay) * 100);

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize razorpay
                Checkout checkout = new Checkout();

                //Set key id
                checkout.setKeyID("rzp_test_1TqAXd7IjIt8q4");

                //Set image
                checkout.setImage(R.drawable.rzp_logo);


                //Initialize json
                JSONObject object = new JSONObject();


                try {
                    //put name
                    object.put("name", "ARay Specs");
                    //Put description
                    object.put("description", "Test payment");
                    //put theme color
                    object.put("theme.color", "#90e0ef");
                    //put currency
                    object.put("currency","INR");
                    //Put amount
                    object.put("amount", amount);
                    //put mobile no
                    object.put("prefill.contact", "+91 7208285864");
                    //put email
                    object.put("prefill.email", "siddhesh.s.pansare@gmail.com");
                    //open razorpay activity
                    checkout.open(PaymentActivity.this, object);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onPaymentSuccess(String s) {
        //Initialize alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //set title
        builder.setTitle("Payment ID");
        //set message
        builder.setMessage(s);
        //Show alert dialog
        builder.show();
        Toast.makeText(getApplicationContext(), "Your order has been placed successfully.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
    }

    @Override
    public void onPaymentError(int i, String s) {
        //Dialog toast
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}