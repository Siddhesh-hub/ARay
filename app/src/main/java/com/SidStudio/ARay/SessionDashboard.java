package com.SidStudio.ARay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.SidStudio.ARay.Databases.SessionManager;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.HashMap;

public class SessionDashboard extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;
    TextView session_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_session_dashboard);

        //session_txt = findViewById(R.id.session_txt);


//        SessionManager sessionManager = new SessionManager(this, SessionManager.SESSION_USERSESSION);
//        HashMap<String, String> userDetails = sessionManager.getUserDetailsFromSession();
//
//        String fullName = userDetails.get(SessionManager.KEY_FULLNAME);
//        String username = userDetails.get(SessionManager.KEY_USERNAME);
//        String email = userDetails.get(SessionManager.KEY_EMAIL);
//        String password = userDetails.get(SessionManager.KEY_PASSWORD);
//        String gender = userDetails.get(SessionManager.KEY_GENDER);
//        String date = userDetails.get(SessionManager.KEY_DATE);
//        String phoneNo = userDetails.get(SessionManager.KEY_PHONENUMBER);

        //session_txt.setText(fullName+"\n"+username+"\n"+email+"\n"+password+"\n"+gender+"\n"+date+"\n"+phoneNo);

        chipNavigationBar = findViewById(R.id.bottom_nav_menu);
        chipNavigationBar.setItemSelected(R.id.bottom_nav_dashboard, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SessionDashboardFragment()).commit();
        bottomMenu();

    }

    private void bottomMenu() {

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i){
                    case R.id.bottom_nav_dashboard:
                        fragment = new SessionDashboardFragment();
                        break;
                    case R.id.bottom_nav_profile:
                        fragment = new SessionProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
    }
}