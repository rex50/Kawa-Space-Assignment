package com.rex50.kawaspaceassignment.di;

import com.rex50.kawaspaceassignment.data.source.remote.services.UsersService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;

@Module
@InstallIn(SingletonComponent.class)
public class ServiceModule {

    @Provides
    @Singleton
    public UsersService providesUsersService(Retrofit retrofit) {
        return retrofit.create(UsersService.class);
    }

}
