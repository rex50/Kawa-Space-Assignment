package com.rex50.kawaspaceassignment.data.repos.users;

import com.rex50.kawaspaceassignment.data.OnRequestResponseListener;
import com.rex50.kawaspaceassignment.data.models.UsersRequest;
import com.rex50.kawaspaceassignment.data.models.user.UsersResponse;

public interface UsersRepo {
    void fetchUsers(UsersRequest usersRequest, OnRequestResponseListener<UsersResponse> requestResponseListener);
}
