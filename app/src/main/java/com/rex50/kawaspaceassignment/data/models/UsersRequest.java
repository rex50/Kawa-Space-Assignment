package com.rex50.kawaspaceassignment.data.models;

public class UsersRequest {
    private String include;
    private int count;

    public UsersRequest(String include, int count) {
        this.include = include;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public String getInclude() {
        return include;
    }
}
