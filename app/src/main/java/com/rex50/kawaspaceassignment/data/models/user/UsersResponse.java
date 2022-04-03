
package com.rex50.kawaspaceassignment.data.models.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsersResponse implements Serializable {

    @SerializedName("results")
    @Expose
    private List<User> users = null;
    @SerializedName("info")
    @Expose
    private Info info;

    public List<User> getResults() {
        return users;
    }

    public void setResults(List<User> users) {
        this.users = users;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

}
