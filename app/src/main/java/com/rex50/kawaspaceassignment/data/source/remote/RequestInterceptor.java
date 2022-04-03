package com.rex50.kawaspaceassignment.data.source.remote;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Request updatedRequest = request.newBuilder()
                .addHeader("content-type", "application/json")
                .addHeader("Accept", "application/json")
                .build();

        return chain.proceed(updatedRequest);
    }
}
