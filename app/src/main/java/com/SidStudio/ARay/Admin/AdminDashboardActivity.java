package com.SidStudio.ARay.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.SidStudio.ARay.DashboardActivity;
import com.SidStudio.ARay.Databases.SessionManager;
import com.SidStudio.ARay.GlassesList;
import com.SidStudio.ARay.LoginActivity;
import com.SidStudio.ARay.LoginStartupScreen;
import com.SidStudio.ARay.R;
import com.google.android.material.navigation.NavigationView;

public class AdminDashboardActivity extends AppCompatActivity {


    NavigationView navigationView;
    DrawerLayout drawerLayout;

    static final float END_SCALE = 0.7f;

    ImageView menuIcon, addGlass, updateGlass, deleteGlass;
    LinearLayout contentView;
    private Button LogoutButton, CheckOrdersButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_admin_dashboard);

        navigationView = findViewById(R.id.navigation_view);
        menuIcon = findViewById(R.id.nav_menu_icon);
        drawerLayout = findViewById(R.id.drawer_layout);
        contentView = findViewById(R.id.content);
        LogoutButton = findViewById(R.id.admin_logout_btn);
        CheckOrdersButton = findViewById(R.id.check_orders_btn);
        addGlass = findViewById(R.id.add_image);
        updateGlass = findViewById(R.id.update_image);
        deleteGlass = findViewById(R.id.delete_image);

        LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager sessionManager = new SessionManager(getApplicationContext(), SessionManager.SESSION_USERSESSION);
                sessionManager.logoutUserFromSession();
                Intent intent = new Intent(AdminDashboardActivity.this, LoginStartupScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        CheckOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboardActivity.this, AdminNewOrdersActivity.class);
                startActivity(intent);
            }
        });

        addGlass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAddGlasses();
            }
        });

        updateGlass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callUpdateGlasses();
            }
        });

        deleteGlass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDeleteGlasses();
            }
        });
        navigationDrawer();

    }



    //Navigation drawer functions
    private void navigationDrawer() {
        //Navigation drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
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

    private boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_logout:
                SessionManager sessionManager = new SessionManager(getApplicationContext(), SessionManager.SESSION_USERSESSION);
                sessionManager.logoutUserFromSession();
                Intent intent =new Intent(getApplicationContext(), LoginStartupScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.nav_orders:
                Toast.makeText(this, "Orders", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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

    public void callAddGlasses(){
        Intent intent = new Intent(getApplicationContext(), AdminAddItem.class);
        startActivity(intent);
        finish();
    }

    public void callUpdateGlasses(){
        Intent intent = new Intent(getApplicationContext(), GlassesList.class);
        intent.putExtra("Admin", "Admin");
        startActivity(intent);
        finish();
    }


    public void callDeleteGlasses(){
        //startActivity(new Intent(getApplicationContext(), AdminAddItem.class));
        finish();
    }


}