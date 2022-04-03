package com.rex50.kawaspaceassignment.data.repos.users;

import androidx.annotation.NonNull;

import com.rex50.kawaspaceassignment.data.OnRequestResponseListener;
import com.rex50.kawaspaceassignment.data.models.UsersRequest;
import com.rex50.kawaspaceassignment.data.models.user.UsersResponse;
import com.rex50.kawaspaceassignment.data.source.remote.services.UsersService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersRepoImpl implements UsersRepo {

    private final UsersService remoteUserService;

    @Inject
    public UsersRepoImpl(UsersService remoteUserService) {
        this.remoteUserService = remoteUserService;
    }

    @Override
    public void fetchUsers(UsersRequest usersRequest, OnRequestResponseListener<UsersResponse> requestResponseListener) {
        remoteUserService.getUsers(usersRequest.getInclude(), usersRequest.getCount()).enqueue(new Callback<UsersResponse>() {
            @Override
            public void onResponse(@NonNull Call<UsersResponse> call, @NonNull Response<UsersResponse> response) {
                requestResponseListener.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<UsersResponse> call, @NonNull Throwable t) {
                requestResponseListener.onFailed(t);
            }
        });
    }
}
