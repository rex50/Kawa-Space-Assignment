package com.rex50.kawaspaceassignment.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.rex50.kawaspaceassignment.data.OnRequestResponseListener;
import com.rex50.kawaspaceassignment.data.models.UsersRequest;
import com.rex50.kawaspaceassignment.data.models.user.User;
import com.rex50.kawaspaceassignment.data.models.user.UsersResponse;
import com.rex50.kawaspaceassignment.data.repos.users.UsersRepo;
import com.rex50.kawaspaceassignment.utils.Data;
import com.rex50.kawaspaceassignment.utils.Status;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class FragHomeViewModel extends ViewModel {

    private final UsersRepo repo;

    @Inject
    public FragHomeViewModel(SavedStateHandle handle, UsersRepo repo) {
        this.repo = repo;
    }

    private final MutableLiveData<Data<ArrayList<User>>> _users = new MutableLiveData<>();
    public LiveData<Data<ArrayList<User>>> users = _users;

    public void getUsers(UsersRequest request) {
        _users.postValue(new Data<>(Status.LOADING));
        repo.fetchUsers(request, new OnRequestResponseListener<UsersResponse>() {
            @Override
            public void onSuccess(UsersResponse response) {
                // Post value to live data
                ArrayList<User> users = new ArrayList<>();
                if(response.getResults() != null)
                    users.addAll(response.getResults());
                _users.postValue(new Data<>(Status.SUCCESS, users));
            }

            @Override
            public void onFailed(Throwable e) {
                _users.postValue(new Data<>(Status.ERROR, new Exception("Error")));
            }
        });
    }

}