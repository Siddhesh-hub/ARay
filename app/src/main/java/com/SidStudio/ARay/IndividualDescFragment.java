package com.SidStudio.ARay;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class IndividualDescFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    String glassAge, glassBrand, glassFeatures, glassFrameType, glassGender, glassId, glassImage, glassMaterial, glassModel, glassName, glassPrice, glassRating, glassType, glassWarranty;

    public IndividualDescFragment() {
        // Required empty public constructor
    }

    public IndividualDescFragment(String glassAge, String glassBrand, String glassFeatures, String glassFrameType, String glassGender, String glassId, String glassImage, String glassMaterial, String glassModel, String glassName, String glassPrice, String glassRating, String glassType, String glassWarranty) {

        this.glassAge = glassAge;
        this.glassBrand = glassBrand;
        this.glassFeatures = glassFeatures;
        this.glassFrameType = glassFrameType;
        this.glassGender = glassGender;
        this.glassId = glassId;
        this.glassImage = glassImage;
        this.glassMaterial = glassMaterial;
        this.glassModel = glassModel;
        this.glassName = glassName;
        this.glassPrice = glassPrice;
        this.glassRating = glassRating;
        this.glassType = glassType;
        this.glassWarranty = glassWarranty;

    }


    public static IndividualDescFragment newInstance(String param1, String param2) {
        IndividualDescFragment fragment = new IndividualDescFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_individual_desc, container, false);

        ImageView imageView = view.findViewById(R.id.individual_imageview);
        TextView nameView = view.findViewById(R.id.individual_nameview);
        TextView warrantyView = view.findViewById(R.id.individual_warrantyview);

        nameView.setText(glassName);
        warrantyView.setText(glassWarranty);
        Glide.with(getContext()).load(glassImage).into(imageView);
        return view;
    }

    public void onBackPressed(){
        AppCompatActivity appCompatActivity = (AppCompatActivity)getContext();
        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new recFragment()).addToBackStack(null).commit();
    }
}