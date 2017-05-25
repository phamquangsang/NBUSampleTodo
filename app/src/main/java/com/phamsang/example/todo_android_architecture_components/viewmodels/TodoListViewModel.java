package com.phamsang.example.todo_android_architecture_components.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.phamsang.example.todo_android_architecture_components.DaggerApplicationComponent;
import com.phamsang.example.todo_android_architecture_components.TodoApplication;
import com.phamsang.example.todo_android_architecture_components.models.Todo;
import com.phamsang.example.todo_android_architecture_components.modules.AppModule;
import com.phamsang.example.todo_android_architecture_components.modules.TodoRepoModule;
import com.phamsang.example.todo_android_architecture_components.repo.DataSource;
import com.phamsang.example.todo_android_architecture_components.repo.TodoRepo;

import java.util.List;

import javax.inject.Inject;

public class TodoListViewModel extends ViewModel {

    private static final String TAG = TodoListViewModel.class.getSimpleName();

    private MutableLiveData<List<Todo>> mTodoList = new MutableLiveData<>();

    private MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();

    @Inject
    TodoRepo mTodoRepo;



    public TodoListViewModel(){
        super();
        TodoApplication.getApplicationComponentInstance().inject(this);
    }



    public LiveData<List<Todo>> getTodoList(boolean forceRefresh) {
        if(mTodoList.getValue() == null || forceRefresh){
            mIsLoading.setValue(true);

            mTodoRepo.getListTodo(mTodoList, forceRefresh, new DataSource.CompleteCallback() {
                @Override
                public void onSuccess() {
                    mIsLoading.setValue(false);
                }

                @Override
                public void onFailure(Throwable t) {
                    mIsLoading.setValue(false);
                }
            });
        }
        return mTodoList;
    }

    public LiveData<Boolean> isLoading() {
        return mIsLoading;
    }

    public void setLoading(boolean loading) {
        Log.i(TAG, "setLoading: "+loading);
        mIsLoading.setValue(loading);
    }

    public void addTodo(Todo todo){
        mTodoRepo.addTodo(todo, null);
    }

    @Override
    protected void onCleared() {
        mTodoRepo.onCleared();
        super.onCleared();
    }

    public void deleteTodo(String todoId) {
        mTodoRepo.deleteTodo(todoId, null);
    }

    public void updateTodo(Todo todo){
        mTodoRepo.updateTodo(todo, null);
    }
}
