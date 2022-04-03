package com.rex50.kawaspaceassignment.data.source.remote.services;

import com.rex50.kawaspaceassignment.data.models.user.UsersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UsersService {

    String DEFAULT_INCLUDE = "gender,name,nat,location,picture,email";

    @GET("api/")
    Call<UsersResponse> getUsers(@Query("inc") String include, @Query("results") int count);

}
