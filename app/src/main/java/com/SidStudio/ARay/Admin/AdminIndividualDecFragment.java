package com.SidStudio.ARay.Admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.SidStudio.ARay.R;
import com.bumptech.glide.Glide;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminIndividualDecFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminIndividualDecFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    String glassDiscount, glassFeatures, glassId, glassImage, glassName, glassPrice ;
    Button updateChanges;

    public AdminIndividualDecFragment() {
        // Required empty public constructor
    }

    public AdminIndividualDecFragment(String glassDiscount, String glassFeatures, String glassId, String glassImage, String glassName, String glassPrice) {
        this.glassDiscount = glassDiscount;
        this.glassFeatures = glassFeatures;
        this.glassId = glassId;
        this.glassImage = glassImage;
        this.glassName = glassName;
        this.glassPrice = glassPrice;
    }

    public static AdminIndividualDecFragment newInstance(String param1, String param2) {
        AdminIndividualDecFragment fragment = new AdminIndividualDecFragment();
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

        ImageView imageView = view.findViewById(R.id.admin_individual_image_edit);
        EditText nameEdit = view.findViewById(R.id.admin_individual_name_edit);
        EditText priceEdit = view.findViewById(R.id.admin_individual_price_edit);
        EditText discountEdit = view.findViewById(R.id.admin_individual_discount_edit);
        EditText featureEdit = view.findViewById(R.id.admin_individual_features_edit);
        updateChanges = view.findViewById(R.id.updateProductDetails);

        Glide.with(getContext()).load(glassImage).into(imageView);
        nameEdit.setText(glassName);
        discountEdit.setText(glassDiscount+"off");
        priceEdit.setText(glassPrice);
        featureEdit.setText(glassFeatures);

        updateChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDataToFirebase();
            }
        });

        return view;

    }

    private void updateDataToFirebase() {

    }
}