package com.SidStudio.ARay;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.SidStudio.ARay.Databases.ModelGlasses;
import com.SidStudio.ARay.HelperClasses.HomeAdapter.GlassItemsAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class recFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public String isAdmin;

    private String mParam1;
    private String mParam2;

    RecyclerView recview;
    GlassItemsAdapter itemsAdapter;

    public recFragment() {
        // Required empty public constructor
    }


    public static recFragment newInstance(String param1, String param2) {
        recFragment fragment = new recFragment();
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
        View view =  inflater.inflate(R.layout.fragment_rec, container, false);

        recview = (RecyclerView)view.findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<ModelGlasses> options =
                new FirebaseRecyclerOptions.Builder<ModelGlasses>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Glasses"), ModelGlasses.class)
                        .build();


        itemsAdapter = new GlassItemsAdapter(options);
        recview.setAdapter(itemsAdapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        itemsAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        itemsAdapter.stopListening();
    }

}