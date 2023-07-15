package com.example.workshop30;


import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.example.workshop30.databinding.ActivityMainBinding;
import com.example.workshop30.models.Weapon;
import com.example.workshop30.models.WeaponResponse;
import com.example.workshop30.network.API;
import com.example.workshop30.network.RetrofitClient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private API api;
    private Rowadapter adapter;
    private Boolean isUserAction = false;

    private int onrep = 0;
    private ArrayList<Weapon> itemsList = new ArrayList<Weapon>();
    private List<Weapon> weaponList = new ArrayList<Weapon>();
    private static Set<MediaPlayer> activePlayers = new HashSet<MediaPlayer>();
    MediaPlayer.OnCompletionListener releaseOnFinishListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mp) {
            mp.release();
            activePlayers.remove(mp);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final MediaPlayer mp = MediaPlayer.create(this,R.raw.shiekah_slate);
        final MediaPlayer mp2 = MediaPlayer.create(this,R.raw.botw_item_discovery);
        activePlayers.add(mp);
        activePlayers.add(mp2);
        mp2.setOnCompletionListener(releaseOnFinishListener);
mp2.start();


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        super.onCreate(savedInstanceState);

        String TAG="APIDEMO";
        mp2.start();

        adapter = new Rowadapter(this,itemsList);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));


        this.api = RetrofitClient.getInstance().getAPI();
        Call<WeaponResponse> requested = api.getAllWeapons();
        binding.searchB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isUserAction = false;

                search();

                binding.scSpinner.setSelection(0);
                mp.start();

binding.searchT.setText("");


            }
        });

        requested.enqueue(new Callback<WeaponResponse>() {
            @Override
            public void onResponse(Call<WeaponResponse> call, Response<WeaponResponse> response) {
                if(response.isSuccessful()==false){
                    Log.d(TAG, "Error from API with response code: " + response.code());

                }
                WeaponResponse obj = response.body();

                weaponList = obj.getResults();
                itemsList.clear();
                itemsList.addAll(weaponList);
                ArrayList<String> locationList = new ArrayList<String>();
                locationList.add("Choose a location");
                ArrayList<String> x;

                for(int i = 0; i <itemsList.size();i++){
                String toUpper = itemsList.get(i).getName();
                String firstLetter = toUpper.substring(0, 1);
                    String remainingLetters = toUpper.substring(1);
                    firstLetter = firstLetter.toUpperCase();
                    itemsList.get(i).setName(firstLetter+remainingLetters);
                    x = itemsList.get(i).getLoc();
                    if(x!=null) {

                        for (int j = 0; j < x.size(); j++) {

                            if (!locationList.contains(x.get(j))) {
                                locationList.add(x.get(j));
                            }
                        }
                    }
                }

                ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, locationList);
                locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                binding.scSpinner.setAdapter(locationAdapter);

                adapter.notifyDataSetChanged();






            }

            @Override
            public void onFailure(Call<WeaponResponse> call, Throwable t){

                Log.d(TAG, "The request failed");
                Log.d(TAG, t.getMessage());
            }
        });
        binding.scSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    isUserAction = true;
                }

                return false;
            }
        });
        binding.scSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String x = (String) parent.getItemAtPosition(position);
                if(isUserAction){


                    itemsList.clear();
                    binding.searchT.setText("");


                if (x.compareTo("Choose a location") != 0) {

                    for (Weapon w : weaponList) {
                        if (w.getLoc() != null) {
                            if (w.getLoc().contains(x)) itemsList.add(w);
                        }
                    }

                    adapter.notifyDataSetChanged();

                } else  {

                    for (Weapon w : weaponList) {
                        itemsList.add(w);

                    }
                    adapter.notifyDataSetChanged();


                }
                    mp.start();

            }
                isUserAction = true;





            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    void search(){

        String temp = "";
        itemsList.clear();

        temp = binding.searchT.getText().toString();
temp = temp.toLowerCase(Locale.ROOT);


        if(temp.length()==0) {
            for (Weapon w : weaponList) {
    itemsList.add(w);
            }
        }
        else{

    for(Weapon w : weaponList){
        if (w.getName().toLowerCase(Locale.ROOT).contains(temp)){
            itemsList.add(w);
        }
    }
        }
        adapter.notifyDataSetChanged();

    }
    }
