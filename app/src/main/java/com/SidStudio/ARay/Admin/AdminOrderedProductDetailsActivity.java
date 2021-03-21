package com.SidStudio.ARay.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.SidStudio.ARay.Databases.CartModel;
import com.SidStudio.ARay.HelperClasses.HomeAdapter.CartViewHolder;
import com.SidStudio.ARay.R;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

public class AdminOrderedProductDetailsActivity extends AppCompatActivity {


    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView detailedOrderList;

    private String userPhoneNo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_admin_ordered_product_details);

        userPhoneNo = getIntent().getStringExtra("uid");
        Toast.makeText(this, userPhoneNo, Toast.LENGTH_SHORT).show();

        detailedOrderList = findViewById(R.id.order_details_recycler);
        detailedOrderList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        detailedOrderList.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();


        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<CartModel> options = new FirebaseRecyclerOptions.Builder<CartModel>()
                .setQuery(cartListRef.child("Admin View")
                        .child(userPhoneNo).child("Products"), CartModel.class)
                .build();
        FirebaseRecyclerAdapter<CartModel, CartViewHolder> adapter
                = new FirebaseRecyclerAdapter<CartModel, CartViewHolder>(options) {

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }

            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull CartModel model) {
                holder.name.setText(model.getGlassName());
                holder.quantity.setNumber(model.getQuantity());
                Glide.with(holder.img.getContext()).load(model.getGlassImage()).into(holder.img);
                holder.frameWidth.setText(model.getGlassFrameWidth());
                holder.prescriptionType.setText(model.getGlassPrescriptionType());
                holder.lensType.setText(model.getGlassLensType());
                holder.lensMaterial.setText(model.getGlassLensMaterial());


                holder.price.setText("Rs. " + Integer.parseInt(model.getGlassPrice()) * Integer.parseInt(model.getQuantity()));

            }
        };

        detailedOrderList.setAdapter(adapter);
        adapter.startListening();
    }
}