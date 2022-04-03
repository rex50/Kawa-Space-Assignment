package com.rex50.kawaspaceassignment.data.models;

import com.rex50.kawaspaceassignment.data.models.user.User;

public class SelectedUserData {
    private final User user;
    private final int position;

    public SelectedUserData(User user, int position) {
        this.user = user;
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public User getUser() {
        return user;
    }
}
