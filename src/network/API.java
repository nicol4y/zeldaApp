package com.example.workshop30.network;

import com.example.workshop30.models.WeaponResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {
        String BASE_URL = "https://botw-compendium.herokuapp.com/api/v2/category/";
@GET("equipment")
    public Call<WeaponResponse> getAllWeapons();

}
