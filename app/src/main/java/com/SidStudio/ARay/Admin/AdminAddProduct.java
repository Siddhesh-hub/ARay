package com.SidStudio.ARay.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.SidStudio.ARay.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddProduct extends AppCompatActivity {

    private String getGlassType, saveCurrentDate, saveCurrentTime, productRandomKey, downloadImageUrl;
    private Spinner spinner;
    private static final String[] frameTypes = {"Pilot Frame", "Cat-eye frame", "Retro-Square frame", "Round frame", "Half frame", "Full frame", "Clubmaster frame"};
    private static final int GalleryPic = 1;
    private Uri ImageUri;
    ImageView glassImage;

    RadioGroup glassAge, glassGender;
    RadioButton selectedAge, selectedGender;
    String age, gender;

    Spinner glassFrameType;
    TextInputLayout glassBrand, glassFeatures, glassId, glassMaterial, glassModel, glassName, glassPrice, glassRating, glassType, glassWarranty;
    Button addGlass;
    RelativeLayout progressBar;
    private StorageReference GlassesImagesRef;
    private DatabaseReference GlassesRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_product);

        //Declare variables
        glassImage = findViewById(R.id.admin_add_photo);
        glassAge = findViewById(R.id.pref_age_group);
        glassGender = findViewById(R.id.pref_gender);
        glassBrand = findViewById(R.id.glassBrand);
        glassFeatures = findViewById(R.id.glassFeatures);
        glassFrameType = findViewById(R.id.glassFrameType);
        glassMaterial = findViewById(R.id.glassMaterial);
        //glassModel = findViewById(R.id.);
        glassName = findViewById(R.id.glassName);
        glassPrice = findViewById(R.id.glassPrice);
        glassRating = findViewById(R.id.glassRating);
        glassType = findViewById(R.id.glassType);
        glassWarranty = findViewById(R.id.glassWarranty);
        addGlass = findViewById(R.id.addGlassBtn);
        progressBar = findViewById(R.id.login_progress_bar);

        //Get glass type from last page
        getGlassType = getIntent().getExtras().get("glassType").toString();


        //Get the reference of firebase storage
        GlassesImagesRef = FirebaseStorage.getInstance().getReference().child("Glass_images");
        GlassesRef = FirebaseDatabase.getInstance().getReference().child("Glasses");

        //set the type of glass by option admin clicked.
        glassType.setHint(getGlassType);

        //Code for spinner
        spinner = findViewById(R.id.glassFrameType);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AdminAddProduct.this,
                android.R.layout.simple_spinner_item, frameTypes);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //Take InputImage
        glassImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });


    }

    public void callToAddGlass(View view) {
        //progressBar.setVisibility(View.VISIBLE);
        if (!validateGlassName()) {
            return;
        }
        if (!validateGlassBrand()) {
            return;
        }
        if (!validateGlassMaterial()) {
            return;
        }
        if (!validateGlassPrice()) {
            return;
        }
        if (!validateGlassAge()) {
            return;
        }
        if (!validateGlassGender()) {
            return;
        }
        if (!validateGlassRating()) {
            return;
        }
        if (!validateGlassWarranty()) {
            return;
        }
        if (!validateGlassFeatures()) {
            return;
        }
        storeGlassItem();

    }

    private void storeGlassItem() {
        progressBar.setVisibility(View.VISIBLE);

        //RadioButton
        selectedGender = findViewById(glassGender.getCheckedRadioButtonId());
        gender = selectedGender.getText().toString();

        selectedAge = findViewById(glassAge.getCheckedRadioButtonId());
        age = selectedAge.getText().toString();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        //Give a name to file
        StorageReference filePath = GlassesImagesRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".png");
        //Upload the image.
        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(AdminAddProduct.this, "message", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //Toast.makeText(AdminAddProduct.this, "Glass Image uploaded successfully.", Toast.LENGTH_SHORT).show();

                //Get the link of that uploaded image.
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();

                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadImageUrl = task.getResult().toString();

                            //Toast.makeText(AdminAddProduct.this, "Got the Glass image url successfully.", Toast.LENGTH_SHORT).show();

                            SaveGlassInfoToDatabase();
                        }
                    }
                });
            }
        });


    }

    private void SaveGlassInfoToDatabase() {
        HashMap<String, Object> glassMap = new HashMap<>();
        glassMap.put("glassId", productRandomKey);
        glassMap.put("AddedDate", saveCurrentDate);
        glassMap.put("AddedTime", saveCurrentTime);
        glassMap.put("glassType", getGlassType);
        glassMap.put("glassName", glassName.getEditText().getText().toString().trim());
        glassMap.put("glassBrand", glassBrand.getEditText().getText().toString().trim());
        glassMap.put("glassFrameType", glassFrameType.getSelectedItem().toString().trim());
        glassMap.put("glassMaterial", glassMaterial.getEditText().getText().toString().trim());
        glassMap.put("glassPrice", glassPrice.getEditText().getText().toString().trim());
        glassMap.put("glassAge", age);
        glassMap.put("glassGender", gender);
        glassMap.put("glassRating", glassRating.getEditText().getText().toString().trim());
        glassMap.put("glassWarranty", glassWarranty.getEditText().getText().toString().trim());
        glassMap.put("glassFeatures", glassFeatures.getEditText().getText().toString().trim());
        glassMap.put("glassImage", downloadImageUrl);
        //glassMap.put("glassId", productRandomKey);

        GlassesRef.child(productRandomKey).updateChildren(glassMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            Intent intent = new Intent(AdminAddProduct.this, AdminAddItem.class);
                            startActivity(intent);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(AdminAddProduct.this, "Glass is added successfully.", Toast.LENGTH_SHORT).show();
                        } else {
                            progressBar.setVisibility(View.GONE);
                            String message = task.getException().toString();
                            Toast.makeText(AdminAddProduct.this, "Error" + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validateGlassFeatures() {
        String val = glassFeatures.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            glassFeatures.setError("Glass features cannot be empty.");
            return false;
        } else {
            glassFeatures.setError(null);
            glassFeatures.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateGlassWarranty() {
        String val = glassRating.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            glassWarranty.setError("Warranty period cannot be empty.");
            return false;
        } else {
            glassWarranty.setError(null);
            glassWarranty.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateGlassRating() {
        double f1 = 0.0;
        double f2 = 5.0;
        Double val = Double.valueOf(10);
        try {
            val = Double.valueOf(glassRating.getEditText().getText().toString().trim());
        } catch (Exception e) {
            glassRating.setError("Enter rating for Glass.");
            return false;
        }

        if (val < f1) {
            glassRating.setError("Rating cannot be less than 0");
            return false;
        } else if (val > f2) {
            glassRating.setError("Rating cannot be greater than 5");
            return false;
        } else {
            glassRating.setError(null);
            glassRating.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateGlassGender() {
        if (glassGender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateGlassAge() {
        if (glassAge.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Age", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }

    private boolean validateGlassPrice() {
        String val = glassPrice.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            glassPrice.setError("Glass Price cannot be empty!");
            return false;
        } else {
            glassPrice.setError(null);
            glassPrice.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateGlassMaterial() {
        String val = glassMaterial.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            glassMaterial.setError("Glass Brand cannot be empty!");
            return false;
        } else {
            glassMaterial.setError(null);
            glassMaterial.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateGlassBrand() {
        String val = glassBrand.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            glassBrand.setError("Glass Brand cannot be empty!");
            return false;
        } else {
            glassBrand.setError(null);
            glassBrand.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateGlassName() {
        String val = glassName.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            glassName.setError("Glass name cannot be empty.");
            return false;
        } else {
            glassName.setError(null);
            glassName.setErrorEnabled(false);
            return true;
        }
    }

    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("Image/*");
        startActivityForResult(galleryIntent, GalleryPic);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalleryPic && resultCode == RESULT_OK && data != null) {
            ImageUri = data.getData();
            glassImage.setImageURI(ImageUri);
        }
    }
}