package com.SidStudio.ARay;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class LoginTabFragment extends Fragment {

    EditText email,password;
    Button login_btn;
    TextView forget_password;
    float v=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment,container, false);

        email = root.findViewById(R.id.login_email);
        password = root.findViewById(R.id.login_password);
        login_btn = root.findViewById(R.id.login_btn);
        forget_password = root.findViewById(R.id.login_forget_password);

        email.setTranslationX(800);
        password.setTranslationX(800);
        login_btn.setTranslationX(800);
        forget_password.setTranslationX(800);


        email.setAlpha(v);
        password.setAlpha(v);
        login_btn.setAlpha(v);
        forget_password.setAlpha(v);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login_btn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forget_password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        return root;
    }
}
