package com.SidStudio.ARay.HelperClasses.HomeAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.SidStudio.ARay.R;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>{
    ArrayList<CategoriesHelperClass> categoryLocations;

    public CategoriesAdapter(ArrayList<CategoriesHelperClass> categoryLocations) {
        this.categoryLocations = categoryLocations;
    }


    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_card_design, parent, false);
        CategoriesViewHolder categoriesViewHolder = new CategoriesViewHolder(view);
        return categoriesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        CategoriesHelperClass categoriesHelperClass = categoryLocations.get(position);

        holder.image.setImageResource(categoriesHelperClass.getImage());
        holder.title.setText(categoriesHelperClass.getTitle());
        holder.desc.setText(categoriesHelperClass.getDesc());
    }

    @Override
    public int getItemCount() {
        return categoryLocations.size();
    }

    public static class CategoriesViewHolder extends RecyclerView.ViewHolder{


        ImageView image;
        TextView title, desc;


        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            //Hoooks for design

            image = itemView.findViewById(R.id.cat_image);
            title = itemView.findViewById(R.id.cat_title);
            desc = itemView.findViewById(R.id.cat_desc);
        }
    }
}
