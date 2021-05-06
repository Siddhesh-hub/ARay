package com.SidStudio.ARay;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.SidStudio.ARay.Databases.SessionManager;
import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class IndividualDescFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    String state = "Normal";
    String userPhoneNumber;

    ElegantNumberButton quantityBtn;

    String glassAge, glassBrand, glassDiscount, glassFeatures, glassFrameType, glassGender, glassId, glassImage, glassMaterial, glassModel, glassName, glassPrice, glassRating, glassType, glassWarranty;

    public IndividualDescFragment() {
        // Required empty public constructor
    }

    public IndividualDescFragment(String glassAge, String glassBrand, String glassDiscount, String glassFeatures, String glassFrameType, String glassGender, String glassId, String glassImage, String glassMaterial, String glassModel, String glassName, String glassPrice, String glassRating, String glassType, String glassWarranty) {
        this.glassAge = glassAge;
        this.glassBrand = glassBrand;
        this.glassFeatures = glassFeatures;
        this.glassFrameType = glassFrameType;
        this.glassGender = glassGender;
        this.glassId = glassId;
        this.glassImage = glassImage;
        this.glassMaterial = glassMaterial;
        this.glassModel = glassModel;
        this.glassName = glassName;
        this.glassPrice = glassPrice;
        this.glassRating = glassRating;
        this.glassType = glassType;
        this.glassWarranty = glassWarranty;
        this.glassDiscount = glassDiscount;
    }

    public static IndividualDescFragment newInstance(String param1, String param2) {
        IndividualDescFragment fragment = new IndividualDescFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_individual_desc, container, false);

        ImageView imageView = view.findViewById(R.id.individual_image_edit);
        TextView nameView = view.findViewById(R.id.individual_name_edit);
        TextView warrantyView = view.findViewById(R.id.individual_warranty_edit);
        TextView priceView = view.findViewById(R.id.individual_price_edit);
        TextView brandView = view.findViewById(R.id.individual_brand_name_edit);
        TextView discountView = view.findViewById(R.id.individual_discount_edit);
        TextView frameTypeView = view.findViewById(R.id.individual_frame_type_edit);
        TextView frameMaterialView = view.findViewById(R.id.individual_frame_material_edit);
        TextView featureView = view.findViewById(R.id.individual_features_edit);
        Button proceedToDetailsBtn = view.findViewById(R.id.individual_add_to_cart_btn);
        Button tryAR = view.findViewById(R.id.individual_try_ar_btn);
        quantityBtn = view.findViewById(R.id.individual_quantity_btn);

        nameView.setText(glassName);
        brandView.setText(glassBrand);
        discountView.setText(glassDiscount+"off");
        priceView.setText(glassPrice);
        frameTypeView.setText(glassFrameType);
        frameMaterialView.setText(glassMaterial);
        warrantyView.setText(glassWarranty);
        featureView.setText(glassFeatures);
        Glide.with(getContext()).load(glassImage).into(imageView);

        proceedToDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (state.equals("Order Placed") || state.equals("Order Shipped")){
                    Toast.makeText(getContext(), "You can purchase more products once your order is shipped or confirmed.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    getPurchaseDetails();
                }

            }
        });

        tryAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callARActivity();
            }
        });
        SessionManager sessionManager = new SessionManager(getContext(), SessionManager.SESSION_USERSESSION);
        HashMap<String, String> SessionDetails = sessionManager.getUserDetailsFromSession();
        userPhoneNumber = SessionDetails.get(SessionManager.KEY_PHONENUMBER);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        CheckOrderState();
    }
    private void getPurchaseDetails() {
        Intent intent = new Intent(getContext(), GetPurchseDetailsActivity.class);
        intent.putExtra("Model Name", glassModel);
        intent.putExtra("glassId", glassId);
        intent.putExtra("glassName", glassName);
        intent.putExtra("glassDiscount", glassDiscount);
        intent.putExtra("glassFrameType", glassFrameType);
        intent.putExtra("quantity", "1");
        intent.putExtra("glassImage", glassImage);
        intent.putExtra("glassPrice", glassPrice);
        startActivity(intent);
    }

    private void callARActivity() {
        Intent intent = new Intent(getContext(), ARscreen.class);
        intent.putExtra("Model Name", glassModel);
        startActivity(intent);
    }


    public void goBack(View view){
        AppCompatActivity appCompatActivity = (AppCompatActivity)getContext();
        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new recFragment()).addToBackStack(null).commit();
    }

    private void CheckOrderState() {
        DatabaseReference orderRef;
        orderRef = FirebaseDatabase.getInstance().getReference()
                .child("Orders")
                .child(userPhoneNumber);

        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String shippingState = snapshot.child("progress").getValue().toString();
                    if (shippingState.equals("Shipped")){
                        state = "Order Shipped";
                    }else if (shippingState.equals("Not Shipped")){
                        state = "Order Placed";
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}