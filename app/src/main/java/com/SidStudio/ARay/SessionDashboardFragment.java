package com.SidStudio.ARay;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.SidStudio.ARay.Databases.SessionManager;

public class SessionDashboardFragment extends Fragment {

    ImageView backBtn;
    Button logout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_session_dashboard, container, false);

        logout = root.findViewById(R.id.logoutBtn);
        backBtn = root.findViewById(R.id.backBtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager sessionManager = new SessionManager(getActivity(), SessionManager.SESSION_USERSESSION);
                sessionManager.logoutUserFromSession();
                Intent intent =new Intent(getActivity(), DashboardActivity.class);
                startActivity(intent);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), DashboardActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

}