package com.phamsang.example.todo_android_architecture_components;

import com.phamsang.example.todo_android_architecture_components.modules.AppModule;
import com.phamsang.example.todo_android_architecture_components.modules.TodoRepoModule;
import com.phamsang.example.todo_android_architecture_components.viewmodels.TodoDetailViewModel;
import com.phamsang.example.todo_android_architecture_components.viewmodels.TodoListViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={AppModule.class, TodoRepoModule.class})
public interface ApplicationComponent {

    void inject(TodoListViewModel todoListViewModel);

    void inject(TodoDetailViewModel todoDetailViewModel);
}
