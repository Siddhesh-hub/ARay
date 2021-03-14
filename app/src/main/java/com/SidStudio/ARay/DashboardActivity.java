package com.SidStudio.ARay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.SidStudio.ARay.Databases.SessionManager;
import com.SidStudio.ARay.HelperClasses.HomeAdapter.CategoriesAdapter;
import com.SidStudio.ARay.HelperClasses.HomeAdapter.CategoriesHelperClass;
import com.SidStudio.ARay.HelperClasses.HomeAdapter.FeaturedAdapter;
import com.SidStudio.ARay.HelperClasses.HomeAdapter.FeaturedHelperClass;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    RecyclerView featuredRecycler;
    RecyclerView categoriesRecycler;
    RecyclerView.Adapter featured_adapter;
    RecyclerView.Adapter categories_adapter;


    //Drawer menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    ImageView menuIcon, eyeGlasses, sunGlasses, readingGlasses, powerGlasses;
    static final float END_SCALE = 0.7f;
    LinearLayout contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);
        //Hooks
        featuredRecycler = findViewById(R.id.featured_recycler);
        categoriesRecycler = findViewById(R.id.cat_recycler);
        menuIcon = findViewById(R.id.nav_menu_icon);
        contentView = findViewById(R.id.content);

        //Imageview hooks
        eyeGlasses = findViewById(R.id.dashboard_eyeglasses_imgview);
        sunGlasses = findViewById(R.id.dashboard_sunglasses_imgview);
        readingGlasses = findViewById(R.id.dashboard_readingglasses_imgview);
        powerGlasses = findViewById(R.id.dashboard_powerglasses_imgview);

        //Menu hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);


        //Hide or show items
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_logout).setVisible(false);
        menu.findItem(R.id.nav_profile).setVisible(false);


        navigationDrawer();


        //Recycler views function calls
        featuredRecycler();
        categoriesRecycler();
    }

    //Navigation drawer functions
    private void navigationDrawer() {
        //Navigation drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        animateNavigationDrawer();
    }

    private void animateNavigationDrawer() {
        //Add any color or remove it to use the default one!
        //To make it transparent use Color.Transparent in side setScrimColor();
        drawerLayout.setScrimColor(getResources().getColor(R.color.colorPrimaryDark));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerVisible(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_login:
                Intent intent = new Intent(DashboardActivity.this, LoginStartupScreen.class);
                startActivity(intent);
                break;
            case R.id.nav_setting:
                //intent = new Intent(DashboardActivity.this, Settings.class);
                //startActivity(intent);
                Toast.makeText(this, "Disabled for further development", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_home:
                break;
            case R.id.nav_logout:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_orders:
                Toast.makeText(this, "Orders", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_profile:
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_rate_us:
                Toast.makeText(this, "Rate us", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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

    public void callLoginStartup(View view){
        SessionManager sessionManager = new SessionManager(getApplicationContext(), SessionManager.SESSION_USERSESSION);
        if (sessionManager.checkLogin()){
            startActivity(new Intent(getApplicationContext(), SessionDashboard.class));
        }else {
            startActivity(new Intent(getApplicationContext(), LoginStartupScreen.class));
        }
        finish();
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

    // Call to Next screen
    public void callGlassesList(View view){
        startActivity(new Intent(getApplicationContext(), GlassesList.class));
        finish();
    }


}