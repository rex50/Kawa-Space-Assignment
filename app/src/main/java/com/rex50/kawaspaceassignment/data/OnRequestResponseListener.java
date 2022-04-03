package com.rex50.kawaspaceassignment.data;

public interface OnRequestResponseListener<T> {
    void onSuccess(T response);
    void onFailed(Throwable e);
}
