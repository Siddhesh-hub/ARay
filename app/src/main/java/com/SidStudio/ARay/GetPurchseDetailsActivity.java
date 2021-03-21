package com.SidStudio.ARay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.SidStudio.ARay.Databases.SessionManager;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class GetPurchseDetailsActivity extends AppCompatActivity {

    RadioGroup widthRadioGroup, prescriptionRadioGroup, lensTypeRadioGroup, lensMaterialRadioGroup;
    RadioButton selectedWidth, selectedPrescription, selectedLensType, selectedLensMaterial;
    TextInputLayout lens_prescription;
    ImageView detailsImage;
    Button addToCart;
    //Previous data hooks
    String modelName, glassId, glassName, glassDiscount, glassFrameType, quantity, glassImage, glassPrice;
    String state = "Normal";
    String userPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_purchse_details);

        widthRadioGroup = findViewById(R.id.details_width_radiogroup);
        prescriptionRadioGroup = findViewById(R.id.details_prescription_radiogroup);
        lensTypeRadioGroup = findViewById(R.id.details_lens_radiogroup);
        lensMaterialRadioGroup = findViewById(R.id.details_material_radiogroup);
        lens_prescription = findViewById(R.id.detail_prescription_layout);
        detailsImage = findViewById(R.id.details_image);
        addToCart = findViewById(R.id.addToCart);

        modelName = getIntent().getStringExtra("Model Name");
        glassId = getIntent().getStringExtra("glassId");
        glassName = getIntent().getStringExtra("glassName");
        glassDiscount = getIntent().getStringExtra("glassDiscount");
        glassFrameType = getIntent().getStringExtra("glassFrameType");
        quantity = getIntent().getStringExtra("quantity");
        glassImage = getIntent().getStringExtra("glassImage");
        glassPrice = getIntent().getStringExtra("glassPrice");

        Glide.with(getApplicationContext()).load(glassImage).into(detailsImage);

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingToCartList();
            }
        });
    }



    private void addingToCartList() {


        String saveCurrentTime, saveCurrentDate;

        Calendar callForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(callForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(callForDate.getTime());

        selectedWidth = findViewById(widthRadioGroup.getCheckedRadioButtonId());
        String _width = selectedWidth.getText().toString();

        selectedPrescription = findViewById(prescriptionRadioGroup.getCheckedRadioButtonId());
        String _prescriptionType = selectedPrescription.getText().toString();

        selectedLensMaterial = findViewById(lensMaterialRadioGroup.getCheckedRadioButtonId());
        String _lensMaterial = selectedLensMaterial.getText().toString();

        selectedLensType = findViewById(lensTypeRadioGroup.getCheckedRadioButtonId());
        String _lensType = selectedLensType.getText().toString();

        String _prescriptionGiven = lens_prescription.getEditText().getText().toString();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("glassId", glassId);
        cartMap.put("glassName", glassName);
        cartMap.put("glassDiscount", "0");
        cartMap.put("glassFrameWidth", _width);
        cartMap.put("glassPrescriptionType", _prescriptionType);
        cartMap.put("glassPrescription", _prescriptionGiven);
        cartMap.put("glassLensType", _lensType);
        cartMap.put("glassLensMaterial", _lensMaterial);
        cartMap.put("glassFrameType", glassFrameType);
        cartMap.put("quantity", quantity);
        cartMap.put("glassImage", glassImage);
        cartMap.put("date", saveCurrentDate);
        cartMap.put("glassPrice", glassPrice);
        cartMap.put("time", saveCurrentTime);

        SessionManager sessionManager = new SessionManager(getApplicationContext(), SessionManager.SESSION_USERSESSION);
        HashMap<String, String> SessionDetails = sessionManager.getUserDetailsFromSession();
        userPhoneNumber = SessionDetails.get(SessionManager.KEY_PHONENUMBER);

        cartListRef.child("User View").child(userPhoneNumber)
                .child("Products").child(glassId)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            cartListRef.child("Admin View").child(userPhoneNumber)
                                    .child("Products").child(glassId)
                                    .updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(getApplicationContext(), "Added to cart list.", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), CartActivity.class));
                                        }
                                    });
                        }
                    }
                });
    }

    public void getPrescription(View view) {
        lens_prescription.setVisibility(View.VISIBLE);
    }

    public void doNotGetPrescription(View view) {
        lens_prescription.setVisibility(View.GONE);
    }


}