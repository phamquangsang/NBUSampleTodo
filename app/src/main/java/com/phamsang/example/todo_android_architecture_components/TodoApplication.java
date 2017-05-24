package com.phamsang.example.todo_android_architecture_components;

import android.app.Application;

import com.phamsang.example.todo_android_architecture_components.modules.AppModule;
import com.phamsang.example.todo_android_architecture_components.modules.TodoRepoModule;

public class TodoApplication extends Application {
    static private ApplicationComponent sApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplicationComponent = DaggerApplicationComponent.builder()
                .appModule(new AppModule(this))
                .todoRepoModule(new TodoRepoModule())
                .build();
    }

    public static ApplicationComponent getApplicationComponentInstance(){
        return sApplicationComponent;
    }
}
