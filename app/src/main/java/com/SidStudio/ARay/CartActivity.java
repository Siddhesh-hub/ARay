package com.SidStudio.ARay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.SidStudio.ARay.Databases.CartModel;
import com.SidStudio.ARay.Databases.SessionManager;
import com.SidStudio.ARay.HelperClasses.HomeAdapter.CartViewHolder;
import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button proceedBtn;
    TextView totalAmountTxtView;
    String userPhoneNumber;

    int overTotalPrice = 0;
    int individualPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cart_list_recycler);
        proceedBtn = findViewById(R.id.cart_proceed_btn);
        totalAmountTxtView = findViewById(R.id.cart_total_price_txtview);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        SessionManager sessionManager = new SessionManager(getApplicationContext(), SessionManager.SESSION_USERSESSION);
        HashMap<String, String> SessionDetails = sessionManager.getUserDetailsFromSession();
        userPhoneNumber = SessionDetails.get(SessionManager.KEY_PHONENUMBER);

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the new activity for confirming the order.
                Intent intent = new Intent(CartActivity.this, ConfirmOrderActivity.class);
                intent.putExtra("Total Price", String.valueOf(overTotalPrice));
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {

        super.onStart();

        overTotalPrice = 0;

        //Create database reference
        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<CartModel> options = new FirebaseRecyclerOptions.Builder<CartModel>()
                .setQuery(cartListRef.child("User View")
                        .child(userPhoneNumber).child("Products"), CartModel.class)
                .build();
        FirebaseRecyclerAdapter<CartModel, CartViewHolder> adapter
                = new FirebaseRecyclerAdapter<CartModel, CartViewHolder>(options) {
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
                overTotalPrice = overTotalPrice + Integer.parseInt(model.getGlassPrice()) * Integer.parseInt(model.getQuantity());
                totalAmountTxtView.setText("Total (incl.GST) :" + String.valueOf(overTotalPrice));
                holder.quantity.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
                    @Override
                    public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                        //Start total price as 0
                        overTotalPrice = 0;

                        //Get the price and quantity of item from database
                        // Also update the quantity at real time
                        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
                        cartListRef.child("User View").child(userPhoneNumber)
                                .child("Products").child(model.getGlassId()).child("quantity").setValue(String.valueOf(newValue));

                        //Individual price of every glass
                        individualPrice = Integer.parseInt(model.getGlassPrice()) * Integer.parseInt(holder.quantity.getNumber());
                        holder.price.setText("Rs. " + individualPrice);

                        //call activity again to get the Total price at real time
                        onStart();
                    }
                });


                holder.remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cartListRef.child("User View")
                                .child(userPhoneNumber)
                                .child("Products")
                                .child(model.getGlassId())
                                .removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(CartActivity.this, "Item Removed", Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(CartActivity.this, "Task failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;

            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        startActivity(intent);
    }
}