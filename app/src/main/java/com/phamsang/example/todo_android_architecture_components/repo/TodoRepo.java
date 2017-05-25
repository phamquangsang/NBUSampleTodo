package com.phamsang.example.todo_android_architecture_components.repo;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.Nullable;

import com.phamsang.example.todo_android_architecture_components.models.Todo;

import java.util.ArrayList;
import java.util.List;

public class TodoRepo implements DataSource{

    private TodoFirebaseDataSource mFirebaseDataSource;

    public static final MutableLiveData<List<Todo>> sFakeListTodo;

    static{
        sFakeListTodo = new MutableLiveData<>();
        List<Todo> todoList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Todo todo = new Todo();
            todo.setId(String.valueOf(i));
            todo.setContent("content " + i);
            todo.setTitle("title: " +i);
            todoList.add(todo);
        }
        sFakeListTodo.setValue(todoList);
    }


    public TodoRepo(Application c) {
        mFirebaseDataSource = new TodoFirebaseDataSource();
    }

    public LiveData<List<Todo>> getFakeListTodo(){
        return sFakeListTodo;
    }

    //fetching list todo async
    @Override
    public void getListTodo(MutableLiveData<List<Todo>> listTodo, boolean forceRefresh, @Nullable CompleteCallback completeCallback){
        mFirebaseDataSource.getListTodo(listTodo, forceRefresh, completeCallback);
    }

    @Override
    public void getTodo(String todoId, MutableLiveData<Todo> todo){
        mFirebaseDataSource.getTodo(todoId, todo);
    }


    @Override
    public void addTodo(Todo todo, final CompleteCallback completeCallback){
        mFirebaseDataSource.addTodo(todo, completeCallback);
    }


    @Override
    public void onCleared() {
        mFirebaseDataSource.onCleared();
    }

    @Override
    public void updateTodo(Todo todo, final CompleteCallback completeCallback) {
        if(todo.getId() == null){
            throw new IllegalStateException("Cannot save todo, passed todo does not has ID");
        }
        mFirebaseDataSource.updateTodo(todo, completeCallback);
    }

    public void generateFakeTodos(int number){
        for (int i = 0; i < number; i++) {
            Todo todo = new Todo();
            todo.setId(String.valueOf(System.currentTimeMillis()));
            todo.setTitle("Testing todo title");
            todo.setContent("Testing todo content");
            addTodo(todo, null);
        }
    }

    @Override
    public void deleteTodo(String todoId, CompleteCallback completeCallback) {
        mFirebaseDataSource.deleteTodo(todoId, completeCallback);
    }
}
