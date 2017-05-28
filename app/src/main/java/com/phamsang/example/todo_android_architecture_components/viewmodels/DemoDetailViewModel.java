package com.phamsang.example.todo_android_architecture_components.viewmodels;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.phamsang.example.todo_android_architecture_components.models.Todo;
import com.phamsang.example.todo_android_architecture_components.ui.DemoDetailTodoActivity;

public class DemoDetailViewModel extends ViewModel {

    MutableLiveData<Todo> mTodo = new MutableLiveData<>();

    public MutableLiveData<Todo> getTodo() {
        return mTodo;
    }

    public void fetchTodo(String todoId) {
        Todo fakeTodo = new Todo();
        fakeTodo.setContent("Fake content");
        fakeTodo.setTitle("fake title");
        fakeTodo.setDone(true);
        fakeTodo.setId(String.valueOf(System.currentTimeMillis()));
        fakeTodo.setColor("green");

        mTodo.setValue(fakeTodo);

        //todo fetch todoId;
    }

}
