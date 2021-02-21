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

public class SignupTabFragment extends Fragment {

    EditText email,password,mobile,cnfm_password;
    Button signup_btn;
    float v=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);

        email = root.findViewById(R.id.signup_email);
        mobile = root.findViewById(R.id.signup_mobile);
        password = root.findViewById(R.id.signup_password);
        cnfm_password = root.findViewById(R.id.signup_cnfm_password);

        signup_btn = root.findViewById(R.id.signup_btn);



        email.setTranslationX(800);
        mobile.setTranslationX(800);
        password.setTranslationX(800);
        cnfm_password.setTranslationX(800);
        signup_btn.setTranslationX(800);


        email.setAlpha(v);
        mobile.setAlpha(v);
        password.setAlpha(v);
        cnfm_password.setAlpha(v);
        signup_btn.setAlpha(v);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        mobile.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(400).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        cnfm_password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(600).start();
        signup_btn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        return root;
    }
}
