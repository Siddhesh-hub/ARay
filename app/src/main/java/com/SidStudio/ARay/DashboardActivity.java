package com.SidStudio.ARay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.WindowManager;

import com.SidStudio.ARay.HelperClasses.HomeAdapter.CategoriesAdapter;
import com.SidStudio.ARay.HelperClasses.HomeAdapter.CategoriesHelperClass;
import com.SidStudio.ARay.HelperClasses.HomeAdapter.FeaturedAdapter;
import com.SidStudio.ARay.HelperClasses.HomeAdapter.FeaturedHelperClass;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    RecyclerView featuredRecycler;
    RecyclerView categoriesRecycler;
    RecyclerView.Adapter featured_adapter;
    RecyclerView.Adapter categories_adapter;

    //Drawer menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);
        //Hooks
        featuredRecycler = findViewById(R.id.featured_recycler);
        categoriesRecycler = findViewById(R.id.cat_recycler);

        //Menu hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        //Recycler views function calls
        featuredRecycler();
        categoriesRecycler();
    }

    private void categoriesRecycler() {
        categoriesRecycler.setHasFixedSize(true);
        categoriesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<CategoriesHelperClass> categoriesLocations = new ArrayList<>();

        categoriesLocations.add(new CategoriesHelperClass(R.drawable.category_men, "Men", "Learn to flaunt your looks!"));
        categoriesLocations.add(new CategoriesHelperClass(R.drawable.category_women, "Women", "Be classy, be women!"));
        categoriesLocations.add(new CategoriesHelperClass(R.drawable.category_kids, "Kids", "Why should adults have all fun!"));

        categories_adapter = new CategoriesAdapter(categoriesLocations);
        categoriesRecycler.setAdapter(categories_adapter);
    }

    private void featuredRecycler() {

        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();

        featuredLocations.add(new FeaturedHelperClass(R.drawable.card_frame_1, "Pilot Frames", 4.5f));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.card_frame_2, "Cateye Frames", 4.8f));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.card_frame_3, "Retro-Square Frames", 4.1f));

        featured_adapter = new FeaturedAdapter(featuredLocations);
        featuredRecycler.setAdapter(featured_adapter);

        GradientDrawable featured_gradient = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffeff400, 0xffaff600});

    }
}