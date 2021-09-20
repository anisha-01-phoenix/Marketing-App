package com.example.marketingapp.shopkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;

import com.example.marketingapp.R;
import com.example.marketingapp.databinding.ActivityShopTypeBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Array;
import java.util.ArrayList;


public class shop_type extends AppCompatActivity {
    ActivityShopTypeBinding binding;
    String shopselected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShopTypeBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);
        //Paper.init(this);

        binding.garmrntsobj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.o1.setText("Saree");
                binding.o2.setText("Trouser");
                binding.o3.setText("chaddi");
                binding.o4.setText("jeans");
                binding.o5.setText("T-shirts");
                binding.o6.setText("shirts");
                binding.o7.setText("shorts");
                binding.o8.setText("laggi");
                binding.o9.setText("inner wear");
                binding.o10.setText("Saree");
                binding.o11.setText("Saree");
                binding.o12.setText("Saree");
                binding.o13.setText("Saree");
                binding.o14.setText("Saree");

            }
        });
        binding.medicalobj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.o1.setText("paracetamol");
                binding.o2.setText("Trouser");
                binding.o3.setText("chaddi");
                binding.o4.setText("jeans");
                binding.o5.setText("T-shirts");
                binding.o6.setText("shirts");
                binding.o7.setText("shorts");
                binding.o8.setText("laggi");
                binding.o9.setText("inner wear");
                binding.o10.setText("Saree");
                binding.o11.setText("Saree");
                binding.o12.setText("Saree");
                binding.o13.setText("Saree");
                binding.o14.setText("Saree");

            }
        });
        binding.stationaryobj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.o1.setText("pen");
                binding.o2.setText("Trouser");
                binding.o3.setText("chaddi");
                binding.o4.setText("jeans");
                binding.o5.setText("T-shirts");
                binding.o6.setText("shirts");
                binding.o7.setText("shorts");
                binding.o8.setText("laggi");
                binding.o9.setText("inner wear");
                binding.o10.setText("Saree");
                binding.o11.setText("Saree");
                binding.o12.setText("Saree");
                binding.o13.setText("Saree");
                binding.o14.setText("Saree");

            }
        });
        String languagelist[] = {"vegetable", "furniture", "hardware", "electrical equipments", "hotels", "juice", "jwellery", "tv-shops", "dhh", "d", "e", "dhh", "d", "e", "dhh"};
        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, languagelist);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        binding.moreshops.setAdapter(adapter);
        binding.moreshops.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                shopselected = parent.getItemAtPosition(position).toString();
                // Toast.makeText(parent.getContext(), "Selected: " +languageselected, Toast.LENGTH_LONG).show();
                // Log.v("xx","ddddddd0");
                if (shopselected.equals("vegetable")) {
                    binding.o1.setText("onion");
                    binding.o2.setText("Trouser");
                    binding.o3.setText("chaddi");
                    binding.o4.setText("jeans");
                    binding.o5.setText("T-shirts");
                    binding.o6.setText("shirts");
                    binding.o7.setText("shorts");
                    binding.o8.setText("laggi");
                    binding.o9.setText("inner wear");
                    binding.o10.setText("Saree");
                    binding.o11.setText("Saree");
                    binding.o12.setText("Saree");
                    binding.o13.setText("Saree");
                    binding.o14.setText("Saree");
                } else if (shopselected.equals("furniture")) {
                    binding.o1.setText("bed");
                    binding.o2.setText("Trouser");
                    binding.o3.setText("chaddi");
                    binding.o4.setText("jeans");
                    binding.o5.setText("T-shirts");
                    binding.o6.setText("shirts");
                    binding.o7.setText("shorts");
                    binding.o8.setText("laggi");
                    binding.o9.setText("inner wear");
                    binding.o10.setText("Saree");
                    binding.o11.setText("Saree");
                    binding.o12.setText("Saree");
                    binding.o13.setText("Saree");
                    binding.o14.setText("Saree");
                } else if (shopselected.equals("hardware")) {
                    binding.o1.setText("pipe");
                    binding.o2.setText("Trouser");
                    binding.o3.setText("chaddi");
                    binding.o4.setText("jeans");
                    binding.o5.setText("T-shirts");
                    binding.o6.setText("shirts");
                    binding.o7.setText("shorts");
                    binding.o8.setText("laggi");
                    binding.o9.setText("inner wear");
                    binding.o10.setText("Saree");
                    binding.o11.setText("Saree");
                    binding.o12.setText("Saree");
                    binding.o13.setText("Saree");
                    binding.o14.setText("Saree");
                } else if (shopselected.equals("electrical equipments")) {
                    binding.o1.setText("wire");
                    binding.o2.setText("Trouser");
                    binding.o3.setText("chaddi");
                    binding.o4.setText("jeans");
                    binding.o5.setText("T-shirts");
                    binding.o6.setText("shirts");
                    binding.o7.setText("shorts");
                    binding.o8.setText("laggi");
                    binding.o9.setText("inner wear");
                    binding.o10.setText("Saree");
                    binding.o11.setText("Saree");
                    binding.o12.setText("Saree");
                    binding.o13.setText("Saree");
                    binding.o14.setText("Saree");
                } else if (shopselected.equals("hotels")) {
                    binding.o1.setText("sweets");
                    binding.o2.setText("Trouser");
                    binding.o3.setText("chaddi");
                    binding.o4.setText("jeans");
                    binding.o5.setText("T-shirts");
                    binding.o6.setText("shirts");
                    binding.o7.setText("shorts");
                    binding.o8.setText("laggi");
                    binding.o9.setText("inner wear");
                    binding.o10.setText("Saree");
                    binding.o11.setText("Saree");
                    binding.o12.setText("Saree");
                    binding.o13.setText("Saree");
                    binding.o14.setText("Saree");
                } else if (shopselected.equals("juice")) {
                    binding.o1.setText("papaya");
                    binding.o2.setText("Trouser");
                    binding.o3.setText("chaddi");
                    binding.o4.setText("jeans");
                    binding.o5.setText("T-shirts");
                    binding.o6.setText("shirts");
                    binding.o7.setText("shorts");
                    binding.o8.setText("laggi");
                    binding.o9.setText("inner wear");
                    binding.o10.setText("Saree");
                    binding.o11.setText("Saree");
                    binding.o12.setText("Saree");
                    binding.o13.setText("Saree");
                    binding.o14.setText("Saree");
                } else if (shopselected.equals("jwellery")) {
                    binding.o1.setText("gold");
                    binding.o2.setText("Trouser");
                    binding.o3.setText("chaddi");
                    binding.o4.setText("jeans");
                    binding.o5.setText("T-shirts");
                    binding.o6.setText("shirts");
                    binding.o7.setText("shorts");
                    binding.o8.setText("laggi");
                    binding.o9.setText("inner wear");
                    binding.o10.setText("Saree");
                    binding.o11.setText("Saree");
                    binding.o12.setText("Saree");
                    binding.o13.setText("Saree");
                    binding.o14.setText("Saree");
                } else if (shopselected.equals("tv-shops")) {
                    binding.o1.setText("sonytv");
                    binding.o2.setText("Trouser");
                    binding.o3.setText("chaddi");
                    binding.o4.setText("jeans");
                    binding.o5.setText("T-shirts");
                    binding.o6.setText("shirts");
                    binding.o7.setText("shorts");
                    binding.o8.setText("laggi");
                    binding.o9.setText("inner wear");
                    binding.o10.setText("Saree");
                    binding.o11.setText("Saree");
                    binding.o12.setText("Saree");
                    binding.o13.setText("Saree");
                    binding.o14.setText("Saree");
                } else if (shopselected.equals("dhh")) {
                    binding.o1.setText("random");
                    binding.o2.setText("Trouser");
                    binding.o3.setText("chaddi");
                    binding.o4.setText("jeans");
                    binding.o5.setText("T-shirts");
                    binding.o6.setText("shirts");
                    binding.o7.setText("shorts");
                    binding.o8.setText("laggi");
                    binding.o9.setText("inner wear");
                    binding.o10.setText("Saree");
                    binding.o11.setText("Saree");
                    binding.o12.setText("Saree");
                    binding.o13.setText("Saree");
                    binding.o14.setText("Saree");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        binding.continuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("shopid");
                modelcontent_fbase modelcontentFbase = new modelcontent_fbase();
                if (binding.o1.isChecked()) {
                    modelcontentFbase.setPrice("not setted");
                    modelcontentFbase.setName(binding.o1.getText().toString());
                    modelcontentFbase.setQt_available("not setted");
                    ref1.child(binding.o1.getText().toString()).setValue(modelcontentFbase);
                }

                if (binding.o2.isChecked()) {
                    modelcontentFbase.setPrice("not setted");
                    modelcontentFbase.setName(binding.o2.getText().toString());
                    modelcontentFbase.setQt_available("not setted");
                    ref1.child(binding.o2.getText().toString()).setValue(modelcontentFbase);
                }

                if (binding.o3.isChecked()) {
                    modelcontentFbase.setPrice("not setted");
                    modelcontentFbase.setName(binding.o3.getText().toString());
                    modelcontentFbase.setQt_available("not setted");
                    ref1.child(binding.o3.getText().toString()).setValue(modelcontentFbase);
                }


                if (binding.o4.isChecked()) {
                    modelcontentFbase.setPrice("not setted");
                    modelcontentFbase.setName(binding.o4.getText().toString());
                    modelcontentFbase.setQt_available("not setted");
                    ref1.child(binding.o4.getText().toString()).setValue(modelcontentFbase);
                }

                if (binding.o5.isChecked()) {
                    modelcontentFbase.setPrice("not setted");
                    modelcontentFbase.setName(binding.o5.getText().toString());
                    modelcontentFbase.setQt_available("not setted");
                    ref1.child(binding.o5.getText().toString()).setValue(modelcontentFbase);
                }

                if (binding.o6.isChecked()) {
                    modelcontentFbase.setPrice("not setted");
                    modelcontentFbase.setName(binding.o6.getText().toString());
                    modelcontentFbase.setQt_available("not setted");
                    ref1.child(binding.o6.getText().toString()).setValue(modelcontentFbase);
                }

                if (binding.o7.isChecked()) {
                    modelcontentFbase.setPrice("not setted");
                    modelcontentFbase.setName(binding.o7.getText().toString());
                    modelcontentFbase.setQt_available("not setted");
                    ref1.child(binding.o7.getText().toString()).setValue(modelcontentFbase);
                }

                if (binding.o8.isChecked()) {
                    modelcontentFbase.setPrice("not setted");
                    modelcontentFbase.setName(binding.o8.getText().toString());
                    modelcontentFbase.setQt_available("not setted");
                    ref1.child(binding.o8.getText().toString()).setValue(modelcontentFbase);
                }

                if (binding.o9.isChecked()) {
                    modelcontentFbase.setPrice("not setted");
                    modelcontentFbase.setName(binding.o9.getText().toString());
                    modelcontentFbase.setQt_available("not setted");
                    ref1.child(binding.o9.getText().toString()).setValue(modelcontentFbase);
                }

                if (binding.o10.isChecked()) {
                    modelcontentFbase.setPrice("not setted");
                    modelcontentFbase.setName(binding.o10.getText().toString());
                    modelcontentFbase.setQt_available("not setted");
                    ref1.child(binding.o10.getText().toString()).setValue(modelcontentFbase);
                }

                if (binding.o11.isChecked()) {
                    modelcontentFbase.setPrice("not setted");
                    modelcontentFbase.setName(binding.o11.getText().toString());
                    modelcontentFbase.setQt_available("not setted");
                    ref1.child(binding.o11.getText().toString()).setValue(modelcontentFbase);
                }

                if (binding.o12.isChecked()) {
                    modelcontentFbase.setPrice("not setted");
                    modelcontentFbase.setName(binding.o12.getText().toString());
                    modelcontentFbase.setQt_available("not setted");
                    ref1.child(binding.o12.getText().toString()).setValue(modelcontentFbase);
                }

                if (binding.o13.isChecked()) {
                    modelcontentFbase.setPrice("not setted");
                    modelcontentFbase.setName(binding.o13.getText().toString());
                    modelcontentFbase.setQt_available("not setted");
                    ref1.child(binding.o13.getText().toString()).setValue(modelcontentFbase);
                }

                if (binding.o14.isChecked()) {
                    modelcontentFbase.setPrice("not setted");
                    modelcontentFbase.setName(binding.o14.getText().toString());
                    modelcontentFbase.setQt_available("not setted");
                    ref1.child(binding.o14.getText().toString()).setValue(modelcontentFbase);
                }

                Intent intent = new Intent(getApplicationContext(), shopkeepercontent.class);
                startActivity(intent);
            }
        });
    }


}