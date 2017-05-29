package com.phamsang.example.todo_android_architecture_components.repo;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.Nullable;
import android.util.Log;

import com.phamsang.example.todo_android_architecture_components.R;
import com.phamsang.example.todo_android_architecture_components.models.Todo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FakePersistentDataSource implements DataSource {

    private static final String TAG = FakePersistentDataSource.class.getSimpleName();
    private final MutableLiveData<List<Todo>> mFakeListTodo;
    private final String[] mArrayColor;


    public FakePersistentDataSource(Application application) {
        mArrayColor = application.getResources().getStringArray(R.array.color_list);
        mFakeListTodo = new MutableLiveData<>();
        initializeFakeListTodo(mFakeListTodo);
    }

    private void initializeFakeListTodo(MutableLiveData<List<Todo>> fakeListTodo) {
        List<Todo> todoList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Todo todo = new Todo();
            todo.setId(String.valueOf(i));
            todo.setContent("fake content " + i);
            todo.setTitle("fake title: " +i);
            todo.setColor(getRandomColor());
            todoList.add(todo);
        }
        Log.i(TAG, "initializeFakeListTodo: ");
        mFakeListTodo.setValue(todoList);
    }

    public String getRandomColor(){
        Random random = new Random();
        int index = random.nextInt(mArrayColor.length);
        return mArrayColor[index];
    }


    @Override
    public void addTodo(Todo todo, @Nullable CompleteCallback completeCallback) {
        List<Todo> todoList = mFakeListTodo.getValue();
        todoList.add(todo);
        mFakeListTodo.setValue(todoList);
        if(completeCallback != null){
            completeCallback.onSuccess();
        }
    }

    @Override
    public void getListTodo(MutableLiveData<List<Todo>> listTodo, boolean forceRefresh, @Nullable CompleteCallback completeCallback) {
        listTodo.setValue(mFakeListTodo.getValue());
        if(completeCallback != null){
            completeCallback.onSuccess();
        }
    }

    @Override
    public void getTodo(String todoId, MutableLiveData<Todo> liveTodo) {
        List<Todo> list = mFakeListTodo.getValue();
        for (int i = 0 ; i< list.size(); ++i) {
            Todo todo1 = list.get(i);
            if (todo1.getId().equals(todoId)) {
                liveTodo.setValue(todo1);
                break;
            }
        }

    }

    @Override
    public void updateTodo(Todo todo, @Nullable CompleteCallback completeCallback) {
        List<Todo> list = mFakeListTodo.getValue();
        for (int i = 0 ; i< list.size(); ++i) {
            Todo todo1 = list.get(i);
            if (todo1.getId().equals(todo.getId())) {
                list.set(i, todo);
                mFakeListTodo.setValue(list);
                break;
            }
        }
        if(completeCallback != null){
            completeCallback.onSuccess();
        }
    }

    @Override
    public void deleteTodo(String todoId, @Nullable CompleteCallback completeCallback) {
        List<Todo> list = mFakeListTodo.getValue();
        for (int i = 0 ; i< list.size(); ++i) {
            Todo todo1 = list.get(i);
            if (todo1.getId().equals(todoId)) {
                list.remove(i);
                mFakeListTodo.setValue(list);
                break;
            }
        }
        if(completeCallback != null){
            completeCallback.onSuccess();
        }
    }

    @Override
    public void onCleared() {
    }

    @Override
    public LiveData<Map<String, Todo>> getListTodoSync() {
        return null;
    }
}
