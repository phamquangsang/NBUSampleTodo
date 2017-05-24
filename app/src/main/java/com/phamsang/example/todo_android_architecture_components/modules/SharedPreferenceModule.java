package com.phamsang.example.todo_android_architecture_components.modules;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SharedPreferenceModule {
    @Provides
    @Singleton
    SharedPreferences provideSharedPreference(Application application){
        return PreferenceManager.getDefaultSharedPreferences(application);
    }
}
