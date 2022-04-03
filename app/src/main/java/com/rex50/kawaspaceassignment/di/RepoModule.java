package com.rex50.kawaspaceassignment.di;

import com.rex50.kawaspaceassignment.data.repos.users.UsersRepo;
import com.rex50.kawaspaceassignment.data.repos.users.UsersRepoImpl;
import com.rex50.kawaspaceassignment.data.source.remote.services.UsersService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class RepoModule {

    @Singleton
    @Provides
    public UsersRepo providesUsersRepo(UsersService service) {
        return new UsersRepoImpl(service);
    }

}
