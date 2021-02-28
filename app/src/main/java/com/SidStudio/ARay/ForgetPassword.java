package com.SidStudio.ARay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.SidStudio.ARay.HelperClasses.HomeAdapter.CheckInternet;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class ForgetPassword extends AppCompatActivity {

    //Vaiables
    private ImageView screenIcon;
    private TextView title, description;
    private TextInputLayout phoneNumberTextfield;
    private CountryCodePicker countryCodePicker;
    private Button nextBtn;
    private Animation animation;
    RelativeLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_forget_password);

        //Hooks
        screenIcon = findViewById(R.id.forget_password_icon);
        title = findViewById(R.id.forget_password_title);
        description = findViewById(R.id.forget_password_description);
        phoneNumberTextfield = findViewById(R.id.forget_password_phone_number);
        countryCodePicker = findViewById(R.id.country_code_picker);
        nextBtn = findViewById(R.id.forget_password_next_btn);
        progressBar = findViewById(R.id.forget_password_progress_bar);

        //Animation hook
        animation = AnimationUtils.loadAnimation(this, R.anim.slide_animation);

        //Set animation to all the elements
        screenIcon.setAnimation(animation);
        title.setAnimation(animation);
        description.setAnimation(animation);
        phoneNumberTextfield.setAnimation(animation);
        countryCodePicker.setAnimation(animation);
        nextBtn.setAnimation(animation);
    }
    /*
    Call the OTP screen
    and pass the phone number
    for verification
     */

    public void verifyPhoneNumber(View view){

        //Check the Internet connection
        CheckInternet checkInternet = new CheckInternet();
        if (!checkInternet.isConnected(this)){
            showCustomDialog();
            return;
        }

        //Validate phone number
        if (!validateFields()) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        //Get data from fields
        String _phoneNumber = phoneNumberTextfield.getEditText().getText().toString().trim();
        if (_phoneNumber.charAt(0) == '0') {
            _phoneNumber = _phoneNumber.substring(1);
        }


        final String _completePhoneNumber = "+" + countryCodePicker.getFullNumber()  + _phoneNumber;

        //Check whether user exists in database or not
        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(_completePhoneNumber);
        final String final_phoneNumber = _phoneNumber;
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Check if phone number exists
                if (snapshot.exists()) {
                    phoneNumberTextfield.setError(null);
                    phoneNumberTextfield.setErrorEnabled(false);

                    Intent verifyOTPIntent = new Intent(getApplicationContext(), VerifyOTP.class);
                    verifyOTPIntent.putExtra("phoneNo", final_phoneNumber);
                    verifyOTPIntent.putExtra("countryCode", countryCodePicker.getFullNumber());
                    verifyOTPIntent.putExtra("whatToDo","updateData");
                    startActivity(verifyOTPIntent);
                    finish();

                    progressBar.setVisibility(View.GONE);

                } else {
                    progressBar.setVisibility(View.GONE);
                    phoneNumberTextfield.setError("User does not exist!");
                    phoneNumberTextfield.requestFocus();
                }
            }
            //If any database error has occur this function will roll out.
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ForgetPassword.this);
        builder.setMessage("Please connect to internet to proceed further.")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    }
                });
    }


    private boolean validateFields() {
        String _phoneNumber = phoneNumberTextfield.getEditText().getText().toString().trim();

        if (_phoneNumber.isEmpty()) {
            phoneNumberTextfield.setError("Phone number cannot be empty");
            phoneNumberTextfield.requestFocus();
            return false;
        }  else {
            return true;
        }
    }
}