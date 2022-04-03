package com.rex50.kawaspaceassignment.ui.home;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rex50.kawaspaceassignment.data.models.user.User;
import com.rex50.kawaspaceassignment.utils.Data;
import com.rex50.kawaspaceassignment.utils.Status;

import java.util.ArrayList;
import java.util.Random;

public class FragHomeViewModel extends ViewModel {

    private MutableLiveData<Data<ArrayList<User>>> _users = new MutableLiveData<>();
    public LiveData<Data<ArrayList<User>>> users = _users;

    public void getUsers() {
        _users.postValue(new Data<>(Status.LOADING));
        new Handler().postDelayed(() -> {
            if(new Random().nextBoolean())
                _users.postValue(new Data<>(Status.SUCCESS, getDummyUsers()));
            else
                _users.postValue(new Data<>(Status.ERROR, new Exception("Error")));
        }, 2000);
    }

    private ArrayList<User> getDummyUsers() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("User 1"));
        users.add(new User("User 2"));
        users.add(new User("User 3"));
        users.add(new User("User 4"));
        users.add(new User("User 5"));
        users.add(new User("User 6"));
        users.add(new User("User 7"));
        return users;
    }

}