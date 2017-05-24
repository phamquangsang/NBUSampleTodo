package com.phamsang.example.todo_android_architecture_components.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.phamsang.example.todo_android_architecture_components.DaggerApplicationComponent;
import com.phamsang.example.todo_android_architecture_components.models.Todo;
import com.phamsang.example.todo_android_architecture_components.modules.AppModule;
import com.phamsang.example.todo_android_architecture_components.modules.TodoRepoModule;
import com.phamsang.example.todo_android_architecture_components.repo.DataSource;
import com.phamsang.example.todo_android_architecture_components.repo.TodoRepo;

import java.util.List;

import javax.inject.Inject;

public class TodoListViewModel extends AndroidViewModel {

    private static final String TAG = TodoListViewModel.class.getSimpleName();

    private MutableLiveData<List<Todo>> mTodoList;

    private MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();

    @Inject
    TodoRepo mTodoRepo;


    public TodoListViewModel(Application application) {
        super(application);
        DaggerApplicationComponent.builder()
                .appModule(new AppModule(application))
                .todoRepoModule(new TodoRepoModule())
                .build()
                .inject(this);
    }


    public LiveData<List<Todo>> getTodoList() {
        if(mTodoList == null){
            mTodoList = new MutableLiveData<>();
            mTodoRepo.getListTodo(mTodoList);
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
