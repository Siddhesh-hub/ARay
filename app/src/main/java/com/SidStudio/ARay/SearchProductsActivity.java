package com.SidStudio.ARay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.SidStudio.ARay.Databases.ModelGlasses;
import com.SidStudio.ARay.HelperClasses.HomeAdapter.GlassItemsAdapter;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SearchProductsActivity extends AppCompatActivity {


    private Button searchBtn;
    private EditText inputText;
    private RecyclerView searchList;
    private String searchInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search_products);

        searchBtn = findViewById(R.id.search_btn);
        inputText = findViewById(R.id.search_product_name);
        searchList = findViewById(R.id.search_list);

        searchList.setLayoutManager(new LinearLayoutManager(SearchProductsActivity.this));

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchInput = inputText.getText().toString();

                onStart();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Glasses");

        FirebaseRecyclerOptions<ModelGlasses> options =
                new FirebaseRecyclerOptions.Builder<ModelGlasses>()
                .setQuery(reference.orderByChild("glassName").startAt(searchInput).endAt(searchInput), ModelGlasses.class)
                .build();


        FirebaseRecyclerAdapter<ModelGlasses, GlassItemsAdapter.myViewHolder> adapter =
                new FirebaseRecyclerAdapter<ModelGlasses, GlassItemsAdapter.myViewHolder>(options) {
                    @NonNull
                    @Override
                    public GlassItemsAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.glass_item_view, parent, false);
                        GlassItemsAdapter.myViewHolder holder = new GlassItemsAdapter.myViewHolder(view);
                        return holder;
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull GlassItemsAdapter.myViewHolder holder, int position, @NonNull ModelGlasses model) {
                        holder.name.setText(model.getGlassName());
                        holder.price.setText(model.getGlassPrice());
                        holder.frame.setText(model.getGlassFrameType());
                        Glide.with(holder.img.getContext()).load(model.getGlassImage()).into(holder.img);

                        holder.img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AppCompatActivity appCompatActivity = (AppCompatActivity)v.getContext();
                                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.search_list,
                                        new IndividualDescFragment(model.getGlassAge(), model.getGlassBrand(), model.getGlassDiscount(), model.getGlassFeatures(),
                                                model.getGlassFrameType(), model.getGlassGender(), model.getGlassId(), model.getGlassImage(),
                                                model.getGlassMaterial(), model.getGlassModel(), model.getGlassName(), model.getGlassPrice(),
                                                model.getGlassRating(), model.getGlassType(), model.getGlassWarranty())).addToBackStack(null).commit();
                            }
                        });
                    }


                };

        searchList.setAdapter(adapter);
        adapter.startListening();
    }
}