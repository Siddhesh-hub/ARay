package com.SidStudio.ARay;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.SidStudio.ARay.HelperClasses.HomeAdapter.CheckInternet;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetNewPassword extends AppCompatActivity {

    TextInputLayout newPassword, confirmPassword;
    RelativeLayout progressBar;
    String phoneNo;
    TextView debug_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_set_new_password);

        newPassword = findViewById(R.id.new_password);
        confirmPassword = findViewById(R.id.confirm_password);
        progressBar = findViewById(R.id.new_password_progress_bar);
        debug_txt = findViewById(R.id.debug_txt);

        phoneNo= getIntent().getStringExtra("phoneNo");

        debug_txt.setText(phoneNo);
    }

    public void setNewPasswordBtn(View view){
        Toast.makeText(this, "Inside fn", Toast.LENGTH_SHORT).show();
        //Check the Internet connection
        CheckInternet checkInternet = new CheckInternet();
        if (!checkInternet.isConnected(this)){
            showCustomDialog();
            return;
        }
        Toast.makeText(this, "Internet checked", Toast.LENGTH_SHORT).show();
        //Validate password and confirm password
        if (!validatePassword() | !validateConfirmPassword()) {
            Toast.makeText(this, "Password validator", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Start", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.VISIBLE);

        //Get data from fields
        String _newPassword = newPassword.getEditText().getText().toString().trim();


        //Update data in firebase and sessions
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(phoneNo).child("password").setValue(_newPassword);

        startActivity(new Intent(getApplicationContext(), ForgetPWDSuccessMessage.class));
        finish();
    }

    private boolean validateConfirmPassword() {
        String val = confirmPassword.getEditText().getText().toString().trim();
        String _newPassword = newPassword.getEditText().getText().toString().trim();
        if (!val.equals(_newPassword)){
            confirmPassword.setError("Password do not match!");
            return false;
        }else{
            confirmPassword.setError(null);
            confirmPassword.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = newPassword.getEditText().getText().toString().trim();
//        String checkPassword = "^" +
//                "(?=.*[0-9])" +         //at least 1 digit
//                //"(?=.*[a-z])" +         //at least 1 lower case letter
//                "(?=.*[A-Z])" +         //at least 1 upper case letter
//                "(?=.*[a-zA-Z])" +      //any letter
//                "(?=.*[@#$%^&+=])" +    //at least 1 special character
//                "(?=S+$)" +           //no white spaces
//                ".{4,}" +               //at least 4 characters
//                "$";


        if (val.isEmpty()) {
            newPassword.setError("Field can not be empty");
            return false;
        } else if (val.length() >= 8) {
            //number
            if (val.matches("(.*[0-9].*)")) {
                //upper case
                if (val.matches("(.*[A-Z].*)")) {
                    //symbol
                    if (val.matches("^(?=.*[_.()$&@]).*$")) {
                        newPassword.setError(null);
                        newPassword.setErrorEnabled(false);
                        return true;
                    } else {
                        newPassword.setError("Password should contain at least 1 special character.");
                        return false;
                    }
                } else {
                    newPassword.setError("Password should contain at least 1 Uppercase letter.");
                    return false;
                }
            } else {
                newPassword.setError("Password should contain at least 1 digit.");
                return false;
            }
        } else{
            newPassword.setError("Password must contain 8 letters.");
            return false;
        }
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
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
                        startActivity(new Intent(getApplicationContext(), LoginStartupScreen.class));
                    }
                });
    }

}