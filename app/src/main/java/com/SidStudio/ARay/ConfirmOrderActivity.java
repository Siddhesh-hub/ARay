package com.SidStudio.ARay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.SidStudio.ARay.Databases.SessionManager;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfirmOrderActivity extends AppCompatActivity {

    private String totalAmount = "";
    TextInputLayout phoneNo, name, shippingAddress;
    TextView totalPrice;
    Button proceedToPayment;

    //pincode var
    private TextInputLayout pinCodeEdt;
    private Button getDataBtn;
    private TextView pinCodeDetailsTV;
    String pinCode;
    private RequestQueue mRequestQueue;
    String district, state, country;

    String userPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_confirm_order);

        phoneNo = findViewById(R.id.confirm_order_phone_number);
        name = findViewById(R.id.confirm_order_fullname);
        shippingAddress = findViewById(R.id.confirm_order_shipping_address);
        totalPrice = findViewById(R.id.confirm_order_total_price);
        proceedToPayment = findViewById(R.id.proceed_payment_button);
        // initializing our variables.
        pinCodeEdt = findViewById(R.id.idedtPinCode);
        getDataBtn = findViewById(R.id.idBtnGetCityandState);
        pinCodeDetailsTV = findViewById(R.id.idTVPinCodeDetails);

        // initializing our request que variable with request
        // queue and passing our context to it.
        mRequestQueue = Volley.newRequestQueue(ConfirmOrderActivity.this);

        totalAmount = getIntent().getStringExtra("Total Price");
        totalPrice.setText("Total (incl. GST) " + totalAmount);

        SessionManager sessionManager = new SessionManager(getApplicationContext(), SessionManager.SESSION_USERSESSION);
        HashMap<String, String> SessionDetails = sessionManager.getUserDetailsFromSession();
        userPhoneNumber = SessionDetails.get(SessionManager.KEY_PHONENUMBER);
        phoneNo.getEditText().setText(userPhoneNumber);

        proceedToPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckForm() | !checkPinCode(pinCodeEdt.getEditText().getText().toString())) {
                    return;
                }
                ConfirmOrder();
            }
        });
    }

//

    private boolean CheckForm() {
        if (TextUtils.isEmpty(phoneNo.getEditText().getText().toString())) {
            phoneNo.setError("Enter phone number");
            return false;
        } else if (TextUtils.isEmpty(name.getEditText().getText().toString())) {
            name.setError("Enter name");
            return false;
        } else if (TextUtils.isEmpty(shippingAddress.getEditText().getText().toString())) {
            shippingAddress.setError("Enter shipping address");
            return false;
        } else {
            phoneNo.setError(null);
            phoneNo.setErrorEnabled(false);
            name.setError(null);
            name.setErrorEnabled(false);
            shippingAddress.setError(null);
            shippingAddress.setErrorEnabled(false);
            return true;
        }
    }

    private boolean checkPinCode(String pinCode) {
        // Regex to check valid pin code of India.
        String regex
                = "^[1-9]{1}[0-9]{2}\\s{0,1}[0-9]{3}$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(pinCode);
        // If the pin code is empty
        // return false
        if (TextUtils.isEmpty(pinCodeEdt.getEditText().getText().toString())) {
            pinCodeEdt.setError("Pin code cannot be empty");
            return false;
        } else if (!m.matches()) {
            pinCodeEdt.setError("Not a valid pincode");
            return false;
        } else {
            pinCodeEdt.setError(null);
            pinCodeEdt.setErrorEnabled(false);
            return true;
        }
    }



    private void ConfirmOrder() {
        String saveCurrentTime, saveCurrentDate;

        Calendar callForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(callForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(callForDate.getTime());

        final DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference()
                .child("Orders")
                .child(userPhoneNumber);

        HashMap<String, Object> orderMap = new HashMap<>();
        orderMap.put("totalAmount", totalAmount);
        orderMap.put("name", name.getEditText().getText().toString());
        orderMap.put("phoneNo", phoneNo.getEditText().getText().toString());
        orderMap.put("shippingAddress", shippingAddress.getEditText().getText().toString());
        orderMap.put("pinCode", pinCodeEdt.getEditText().getText().toString());
        orderMap.put("date", saveCurrentDate);
        orderMap.put("time", saveCurrentTime);
        orderMap.put("progress", "Not Shipped");

        orderRef.updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    FirebaseDatabase.getInstance().getReference().child("Cart List")
                            .child("User View")
                            .child(userPhoneNumber)
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(ConfirmOrderActivity.this, PaymentActivity.class);
                                        intent.putExtra("totalAmount", totalAmount);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });
    }
}