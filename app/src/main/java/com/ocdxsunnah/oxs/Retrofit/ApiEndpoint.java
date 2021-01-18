package com.ocdxsunnah.oxs.Retrofit;

import com.ocdxsunnah.oxs.Models.ImsakModels;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiEndpoint {
    @GET("today.json?city=bandung")
    Call<ImsakModels> getData();
}
