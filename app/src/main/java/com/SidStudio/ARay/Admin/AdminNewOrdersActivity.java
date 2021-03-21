package com.SidStudio.ARay.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.SidStudio.ARay.CartActivity;
import com.SidStudio.ARay.Databases.AdminOrdersModel;
import com.SidStudio.ARay.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminNewOrdersActivity extends AppCompatActivity {

    private RecyclerView ordersList;
    private DatabaseReference ordersRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_orders);



        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");

        ordersList = findViewById(R.id.new_orders_recycler);
        ordersList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<AdminOrdersModel> options = new FirebaseRecyclerOptions.Builder<AdminOrdersModel>()
                .setQuery(ordersRef, AdminOrdersModel.class)
                .build();

        FirebaseRecyclerAdapter<AdminOrdersModel, AdminOrdersViewHolder> adapter =
                new FirebaseRecyclerAdapter<AdminOrdersModel, AdminOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AdminOrdersViewHolder holder, int position, @NonNull AdminOrdersModel model) {
                        holder.userName.setText("Name :" + model.getName());
                        holder.userPhoneNumber.setText("Phone Number :" + model.getPhoneNo());
                        holder.userTotalPrice.setText(model.getTotalAmount());
                        holder.orderDate.setText(model.getDate());
                        holder.orderTime.setText(model.getTime());
                        holder.userShippingAddress.setText(model.getShippingAddress());
                        holder.userPinCode.setText(model.getPinCode());

                        holder.showOrderedProducts.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String uID = getRef(position).getKey();

                                Intent intent = new Intent(AdminNewOrdersActivity.this, AdminOrderedProductDetailsActivity.class);
                                intent.putExtra("uid", uID);
                                startActivity(intent);
                                finish();
                            }
                        });

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence options[] = new CharSequence[]
                                        {
                                                "Yes",
                                                "No"
                                        };
                                AlertDialog.Builder builder = new AlertDialog.Builder(AdminNewOrdersActivity.this);
                                builder.setTitle("Have you shipped this order products ?");

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (i == 0){
                                            String uID = getRef(position).getKey();
                                            RemoveOrder(uID);
                                        }else{
                                            finish();
                                        }
                                    }
                                });
                                builder.show();
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout, parent, false);
                        return new AdminOrdersViewHolder(view);
                    }
                };

        ordersList.setAdapter(adapter);
        adapter.startListening();
    }

    private void RemoveOrder(String uID) {
        ordersRef.child(uID).removeValue();
    }


    public static class AdminOrdersViewHolder extends RecyclerView.ViewHolder {

        public TextView userName, userPhoneNumber, userTotalPrice, orderDate, orderTime, userShippingAddress, userPinCode;
        public Button showOrderedProducts;
        ImageView remove;

        public AdminOrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.order_user_name);
            userPhoneNumber = itemView.findViewById(R.id.order_phone_number);
            userTotalPrice = itemView.findViewById(R.id.order_total_price);
            orderDate = itemView.findViewById(R.id.order_date);
            orderTime = itemView.findViewById(R.id.order_time);
            userShippingAddress = itemView.findViewById(R.id.order_shipping_address);
            userPinCode = itemView.findViewById(R.id.order_pin_code);
            showOrderedProducts = itemView.findViewById(R.id.show_ordered_products);
            remove = itemView.findViewById(R.id.cart_item_remove_btn);
        }
    }
}