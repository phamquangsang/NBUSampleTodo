package com.phamsang.example.todo_android_architecture_components.repo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.Nullable;

import com.phamsang.example.todo_android_architecture_components.models.Todo;

import java.util.List;
import java.util.Map;

public interface DataSource {

    void addTodo(Todo todo,@Nullable CompleteCallback completeCallback);

    void getListTodo(MutableLiveData<List<Todo>> listTodo, boolean forceRefresh, @Nullable CompleteCallback completeCallback);

    LiveData<Map<String,Todo>> getListTodoSync();

    void getTodo(String todoId, MutableLiveData<Todo> liveTodo);

    void updateTodo(Todo todo, @Nullable CompleteCallback completeCallback);

    void deleteTodo(String todoId, @Nullable CompleteCallback completeCallback);

    void onCleared();


    interface CompleteCallback {

        void onSuccess();

        void onFailure(Throwable t);
    }
}
