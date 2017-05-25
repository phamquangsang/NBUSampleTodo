package com.phamsang.example.todo_android_architecture_components.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.phamsang.example.todo_android_architecture_components.DaggerApplicationComponent;
import com.phamsang.example.todo_android_architecture_components.TodoApplication;
import com.phamsang.example.todo_android_architecture_components.models.Todo;
import com.phamsang.example.todo_android_architecture_components.modules.AppModule;
import com.phamsang.example.todo_android_architecture_components.modules.TodoRepoModule;
import com.phamsang.example.todo_android_architecture_components.repo.TodoRepo;

import javax.inject.Inject;

public class TodoDetailViewModel extends ViewModel {

    private MutableLiveData<Todo> mTodo;

    private MutableLiveData<String> mColorString = new MutableLiveData<>();

    private String mTodoId;

    @Inject
    TodoRepo mTodoRepo;

    public TodoDetailViewModel() {
        super();
        TodoApplication.getApplicationComponentInstance().inject(this);
    }

    public void initTodoId(@Nullable String todoId){
        mTodoId = todoId;
    }

    public void initColor(String color){
        mColorString.setValue(color);
    }

    public LiveData<Todo> getTodo(){
        if(mTodo == null ){
            mTodo = new MutableLiveData<>();
        }
        if(mTodoId != null){
            mTodoRepo.getTodo(mTodoId, mTodo);
        }
        return mTodo;
    }


    public void saveOrCreateTodo(Todo todo) {
        if(todo.getId() == null){
            mTodoRepo.addTodo(todo, null);
        }else{
            mTodoRepo.updateTodo(todo, null);
        }
    }

    public MutableLiveData<String> getColorString() {
        return mColorString;
    }

}

