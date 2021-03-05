package com.SidStudio.ARay.HelperClasses.HomeAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.SidStudio.ARay.Databases.ModelGlasses;
import com.SidStudio.ARay.IndividualDescFragment;
import com.SidStudio.ARay.R;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class GlassItemsAdapter extends FirebaseRecyclerAdapter<ModelGlasses, GlassItemsAdapter.myViewHolder> {

    public GlassItemsAdapter(@NonNull FirebaseRecyclerOptions<ModelGlasses> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull ModelGlasses model) {
        holder.name.setText(model.getGlassName());
        holder.price.setText(model.getGlassPrice());
        holder.frame.setText(model.getGlassFrameType());
        Glide.with(holder.img.getContext()).load(model.getGlassImage()).into(holder.img);

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity appCompatActivity = (AppCompatActivity)v.getContext();
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,
                        new IndividualDescFragment(model.getGlassAge(), model.getGlassBrand(), model.getGlassFeatures(),
                                model.getGlassFrameType(), model.getGlassGender(), model.getGlassId(), model.getGlassImage(),
                                model.getGlassMaterial(), model.getGlassModel(), model.getGlassName(), model.getGlassPrice(),
                                model.getGlassRating(), model.getGlassType(), model.getGlassWarranty())).addToBackStack(null).commit();
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.glass_item_view,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView name, price, frame;
        CardView cardView;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.view_thumbnail);
            name = (TextView) itemView.findViewById(R.id.view_Name);
            price = (TextView) itemView.findViewById(R.id.view_Price);
            frame = (TextView) itemView.findViewById(R.id.view_Frame);
        }
    }
}
