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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class IndividualDescFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    ElegantNumberButton quantityBtn;

    String glassAge, glassBrand, glassFeatures, glassFrameType, glassGender, glassId, glassImage, glassMaterial, glassModel, glassName, glassPrice, glassRating, glassType, glassWarranty;

    public IndividualDescFragment() {
        // Required empty public constructor
    }

    public IndividualDescFragment(String glassAge, String glassBrand, String glassFeatures, String glassFrameType, String glassGender, String glassId, String glassImage, String glassMaterial, String glassModel, String glassName, String glassPrice, String glassRating, String glassType, String glassWarranty) {

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

        ImageView imageView = view.findViewById(R.id.individual_imageview);
        TextView nameView = view.findViewById(R.id.individual_name_view);
        TextView warrantyView = view.findViewById(R.id.individual_warranty_view);
        TextView priceView = view.findViewById(R.id.individual_price_view);
        Button addToCartBtn = view.findViewById(R.id.individual_add_to_cart_btn);
        quantityBtn = view.findViewById(R.id.individual_quantity_btn);

        nameView.setText(glassName);
        warrantyView.setText(glassWarranty);
        priceView.setText(glassPrice);
        Glide.with(getContext()).load(glassImage).into(imageView);

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingToCartList();
            }
        });

        return view;
    }

    private void addingToCartList() {
        String saveCurrentTime, saveCurrentDate;

        Calendar callForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(callForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(callForDate.getTime());

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String, Object> cartMap =  new HashMap<>();
        cartMap.put("glassId", glassId);
        cartMap.put("glassName", glassName);
        cartMap.put("glassDiscount", "");
        cartMap.put("glassFrameType", glassFrameType);
        cartMap.put("quantity", quantityBtn.getNumber());
        cartMap.put("glassImage", glassImage);
        cartMap.put("date", saveCurrentDate);
        cartMap.put("glassPrice", glassPrice);
        cartMap.put("time", saveCurrentTime);

        SessionManager sessionManager = new SessionManager(getContext(), SessionManager.SESSION_USERSESSION);
        HashMap<String, String> SessionDetails = sessionManager.getUserDetailsFromSession();
        String userPhoneNumber = SessionDetails.get(SessionManager.KEY_PHONENUMBER);

        cartListRef.child("User View").child(userPhoneNumber)
                .child("Products").child(glassId)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            cartListRef.child("Admin View").child(userPhoneNumber)
                                    .child("Products").child(glassId)
                                    .updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(getContext(), "Added to cart list.", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getContext(), GlassesList.class));
                                        }
                                    });
                        }
                    }
                });

    }

    public void onBackPressed(){
        AppCompatActivity appCompatActivity = (AppCompatActivity)getContext();
        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new recFragment()).addToBackStack(null).commit();
    }
}