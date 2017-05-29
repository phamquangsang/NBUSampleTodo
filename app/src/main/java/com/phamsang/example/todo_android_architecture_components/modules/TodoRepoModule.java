package com.phamsang.example.todo_android_architecture_components.modules;

import android.app.Application;

import com.phamsang.example.todo_android_architecture_components.repo.DataSource;
import com.phamsang.example.todo_android_architecture_components.repo.FakePersistentDataSource;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TodoRepoModule {

    @Singleton
    @Provides
    DataSource provideTodoRepo(Application app){
        return new FakePersistentDataSource(app);
    }
}
