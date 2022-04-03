package com.rex50.kawaspaceassignment.utils;

public class Data<T> {
    private Status status;
    private T data;
    private Exception error;

    public Data(Status status) {
        this.status = status;
    }

    public Data(Status status, T data) {
        this.status = status;
        this.data = data;
    }

    public Data(Status status, Exception error) {
        this.status = status;
        this.error = error;
    }

    public Status getStatus() {
        return status;
    }

    public Exception getError() {
        return error;
    }

    public T getData() {
        return data;
    }
}

