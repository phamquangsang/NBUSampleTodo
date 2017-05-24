package com.phamsang.example.todo_android_architecture_components.modules;

import android.app.Application;

import com.phamsang.example.todo_android_architecture_components.repo.TodoRepo;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TodoRepoModule {
    @Provides
    TodoRepo provideTodoRepo(Application app){
        return new TodoRepo(app);
    }
}
