package com.SidStudio.ARay.HelperClasses.HomeAdapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.SidStudio.ARay.Databases.CartModel;
import com.SidStudio.ARay.Databases.ModelGlasses;
import com.SidStudio.ARay.R;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ImageView img, remove;
    public ElegantNumberButton quantity;
    public TextView name, price, frame, discount;
    public CardView cardView;
    private ItemClickListener itemClickListener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);



        name =itemView.findViewById(R.id.cart_item_name);
        price =itemView.findViewById(R.id.cart_item_price);
        frame =itemView.findViewById(R.id.cart_item_lens_type_edit);
        img = itemView.findViewById(R.id.view_thumbnail);
        remove = itemView.findViewById(R.id.cart_item_remove_btn);
        //discount =itemView.findViewById(R.id.cart_item_price);
        quantity =itemView.findViewById(R.id.individual_quantity_btn);

    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
}
