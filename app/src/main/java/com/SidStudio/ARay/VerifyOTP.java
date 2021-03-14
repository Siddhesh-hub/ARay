package com.SidStudio.ARay;

import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.SidStudio.ARay.Databases.UserHelperClass;
import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

import static com.google.firebase.auth.FirebaseAuth.*;

public class VerifyOTP extends AppCompatActivity {

    TextView phoneTxt;
    PinView pinFromUser;
    String codeBySystem;
    String fullName, username, email, password, gender, date, phoneNo, whatToDo, address, image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_verify_o_t_p);

        phoneTxt = findViewById(R.id.otp_phoneNumber);
        pinFromUser = findViewById(R.id.pin_view);

        //Get all the things from intent
        String phone = getIntent().getStringExtra("phoneNo");
        String countryCode = getIntent().getStringExtra("countryCode");

        fullName = getIntent().getStringExtra("fullname");
        username = getIntent().getStringExtra("username");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        gender = getIntent().getStringExtra("gender");
        date = getIntent().getStringExtra("birth_date");
        whatToDo = getIntent().getStringExtra("whatToDo");
        phoneNo = "+" + countryCode + phone;

        sendVerificationCodeToUser(phoneNo);

        phoneTxt.setText(phoneNo);
    }


    private void sendVerificationCodeToUser(String phoneNo) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNo,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,// Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

        Toast.makeText(VerifyOTP.this, "Code sent", Toast.LENGTH_SHORT).show();

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem = s;
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        pinFromUser.setText(code);
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(VerifyOTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem, code);
        Toast.makeText(VerifyOTP.this, "signing with", Toast.LENGTH_SHORT).show();
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        Toast.makeText(VerifyOTP.this, "taking auth", Toast.LENGTH_SHORT).show();
        FirebaseAuth firebaseAuth = getInstance();
        Toast.makeText(VerifyOTP.this, "getting task complete", Toast.LENGTH_SHORT).show();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(VerifyOTP.this, "task successfull", Toast.LENGTH_SHORT).show();
                            //Verification completed successfully here Either
                            // store the data or do whatever desire
                            if (whatToDo.equals("updateData")) {
                                updateOldUsersData();
                            } else if (whatToDo.equals("createNewUser")){
                                Toast.makeText(VerifyOTP.this, "storing new user", Toast.LENGTH_SHORT).show();
                                storeNewUsersData();
                            }
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(VerifyOTP.this, "Verification Not Completed! Try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void updateOldUsersData() {
        Intent intent = new Intent(getApplicationContext(), SetNewPassword.class);
        Toast.makeText(this, phoneNo, Toast.LENGTH_SHORT).show();
        intent.putExtra("phoneNo",phoneNo);
        startActivity(intent);
        finish();
    }

    private void storeNewUsersData() {
        Toast.makeText(this, "Creating rootnode", Toast.LENGTH_SHORT).show();
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        Toast.makeText(this, "Creating reference", Toast.LENGTH_SHORT).show();
        DatabaseReference reference = rootNode.getReference().child("Users");
        Toast.makeText(this, "interacting with userhelper class.", Toast.LENGTH_SHORT).show();
        //Create helperclass reference and store data using firebase
        UserHelperClass addNewUser = new UserHelperClass(fullName, username, email, phoneNo, password, date, gender, image, address);
        reference.child(phoneNo).setValue(addNewUser);

        //We will also create a Session here in next videos to keep the user logged In

        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
        finish();
    }

    public void callNextScreenFromOTP(View view) {
        String code = pinFromUser.getText().toString();
        if (!code.isEmpty()) {
            verifyCode(code);
        }
    }

}