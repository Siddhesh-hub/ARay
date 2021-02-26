package com.SidStudio.ARay;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ScrollView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

public class SignUp3rdClass extends AppCompatActivity {

    ScrollView scrollView;
    TextInputLayout getPhoneNumber;
    CountryCodePicker getCountryCode, country_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up3rd_class);

        scrollView = findViewById(R.id.signup_3rd_screen_scroll_view);
        getCountryCode = findViewById(R.id.country_code_picker);
        getPhoneNumber = findViewById(R.id.signup_phone_number);
    }

    public void callOTPVerification(View view) {
        if (!validatePhoneNumber()) {
            return;
        }

        String countryCode = getCountryCode.getSelectedCountryCode();
        String phoneNumber = getPhoneNumber.getEditText().getText().toString();

        String fullnameF = getIntent().getStringExtra("fullname");
        String usernameF = getIntent().getStringExtra("username");
        String emailF = getIntent().getStringExtra("email");
        String passwordF = getIntent().getStringExtra("password");
        String genderF = getIntent().getStringExtra("gender");
        String birth_dateF = getIntent().getStringExtra("birthDate");

        Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);

        intent.putExtra("fullname", fullnameF);
        intent.putExtra("email", usernameF);
        intent.putExtra("username", emailF);
        intent.putExtra("password", passwordF);
        intent.putExtra("gender", genderF);
        intent.putExtra("birth_date", birth_dateF);
        intent.putExtra("countryCode", countryCode);
        intent.putExtra("phoneNumber", phoneNumber);

//Add Transition
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(scrollView, "transition_OTP_screen");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp3rdClass.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    private boolean validatePhoneNumber() {
        String val = getPhoneNumber.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,20}\\z";
        if (val.isEmpty()) {
            getPhoneNumber.setError("Enter valid phone number");
            return false;
        } else if (val.length() != 10 ) {
            getPhoneNumber.setError("Enter valid phone number");
            return false;
        } else if (!val.matches(checkspaces)) {
            getPhoneNumber.setError("No White spaces are allowed!");
            return false;
        } else {
            getPhoneNumber.setError(null);
            getPhoneNumber.setErrorEnabled(false);
            return true;
        }
    }
}