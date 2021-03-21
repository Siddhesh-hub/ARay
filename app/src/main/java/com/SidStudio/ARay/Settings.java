package com.SidStudio.ARay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.SidStudio.ARay.Databases.SessionManager;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.hbb20.CountryCodePicker;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class Settings extends AppCompatActivity {

    TextView closeBtn, updateBtn, changeProfileBtn;
    CircleImageView profileImage;
    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNumber, fullName, shippingAddress;
    String userPhoneNumber;

    private Uri imageUri;
    private String myUrl = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePrictureRef;
    private String checker = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Hooks for variables
        closeBtn = findViewById(R.id.close_settings_btn);
        updateBtn = findViewById(R.id.update_account_settings_btn);
        profileImage = findViewById(R.id.settings_profile_image);
        countryCodePicker = findViewById(R.id.settings_country_code_picker);
        phoneNumber = findViewById(R.id.settings_phone_number);
        fullName = findViewById(R.id.settings_full_name);
        shippingAddress = findViewById(R.id.settings_address);
        changeProfileBtn = findViewById(R.id.profile_image_change_btn);

        userInfoDisplay(profileImage, fullName, countryCodePicker, phoneNumber, shippingAddress);


        SessionManager sessionManager = new SessionManager(getApplicationContext(), SessionManager.SESSION_USERSESSION);
        HashMap<String, String> SessionDetails = sessionManager.getUserDetailsFromSession();
        userPhoneNumber = SessionDetails.get(SessionManager.KEY_PHONENUMBER);


        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checker.equals("clicked")) {
                    userInfoSaved();
                } else {
                    updateOnlyUserInfo();
                }
            }
        });

        changeProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checker = "clicked";

                CropImage.activity(imageUri)
                        .setAspectRatio(1, 1)
                        .start(Settings.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            profileImage.setImageURI(imageUri);
        } else {
            Toast.makeText(this, "Error, Try Again.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Settings.this, Settings.class));
            finish();
        }
    }

    private void updateOnlyUserInfo() {

        String _phoneNo = "+" + countryCodePicker.getSelectedCountryCode() + phoneNumber.getEditText().toString().trim();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
        storageProfilePrictureRef = FirebaseStorage.getInstance().getReference().child("profile pictures");

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("fulName", fullName.getEditText().getText().toString());
        userMap.put("address", shippingAddress.getEditText().getText().toString());
        userMap.put("phoneOrder", _phoneNo);
        ref.child(userPhoneNumber).updateChildren(userMap);

        startActivity(new Intent(Settings.this, DashboardActivity.class));
        Toast.makeText(Settings.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void userInfoSaved() {
        if (TextUtils.isEmpty(fullName.getEditText().getText().toString())) {
            Toast.makeText(this, "Name is mandatory.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(shippingAddress.getEditText().getText().toString())) {
            Toast.makeText(this, "Address is mandatory.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(phoneNumber.getEditText().getText().toString())) {
            Toast.makeText(this, "phone Number is mandatory.", Toast.LENGTH_SHORT).show();
        } else if (checker.equals("clicked")) {
            uploadImage();
        }
    }

    private void uploadImage() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Update Profile");
        progressDialog.setMessage("Please wait, while we are updating your account information");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        if (imageUri != null) {
            final StorageReference fileRef = storageProfilePrictureRef
                    .child(userPhoneNumber+ ".jpg");

            uploadTask = fileRef.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUrl = task.getResult();
                        myUrl = downloadUrl.toString();

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

                        HashMap<String, Object> userMap = new HashMap<>();
                        userMap.put("fullName", fullName.getEditText().getText().toString());
                        userMap.put("address", shippingAddress.getEditText().getText().toString());
                        userMap.put("phoneNo", phoneNumber.getEditText().getText().toString());
                        userMap.put("image", myUrl);
                        ref.child(userPhoneNumber).updateChildren(userMap);

                        progressDialog.dismiss();

                        startActivity(new Intent(Settings.this, DashboardActivity.class));
                        Toast.makeText(Settings.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(Settings.this, "Error.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "image is not selected.", Toast.LENGTH_SHORT).show();
        }
    }

    private void userInfoDisplay(CircleImageView profileImage, TextInputLayout fullName, CountryCodePicker countryCodePicker, TextInputLayout phoneNumber, TextInputLayout shippingAddress) {
        SessionManager sessionManager = new SessionManager(getApplicationContext(), SessionManager.SESSION_USERSESSION);
        HashMap<String, String> SessionDetails = sessionManager.getUserDetailsFromSession();
        String userPhoneNo = SessionDetails.get(SessionManager.KEY_PHONENUMBER);
        DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userPhoneNo);

        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.child("image").exists()) {
                        String image = snapshot.child("image").getValue().toString();
                        String name = snapshot.child("fullName").getValue().toString();
                        String phone = snapshot.child("phoneNo").getValue().toString();
                        String address = snapshot.child("address").getValue().toString();


                        Picasso.get().load(image).into(profileImage);
                        fullName.getEditText().setText(name);
                        phoneNumber.getEditText().setText(phone);
                        shippingAddress.getEditText().setText(address);
                    }
                } else {
                    Toast.makeText(Settings.this, "Datasnapshot do not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}