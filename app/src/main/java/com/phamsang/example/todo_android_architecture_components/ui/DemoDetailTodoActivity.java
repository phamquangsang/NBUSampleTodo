package com.phamsang.example.todo_android_architecture_components.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.phamsang.example.todo_android_architecture_components.R;
import com.phamsang.example.todo_android_architecture_components.databinding.ActivityDemoDetailTodoBinding;
import com.phamsang.example.todo_android_architecture_components.models.Todo;
import com.phamsang.example.todo_android_architecture_components.repo.TodoRepo;

public class DemoDetailTodoActivity extends AppCompatActivity implements LifecycleRegistryOwner {

    LifecycleRegistry mLifecycle = new LifecycleRegistry(this);

    Todo mTodo;

    @Override
    public LifecycleRegistry getLifecycle() {
        return mLifecycle;
    }

    private static final String ARG_TODO_ID = "arg-todo-id";
    private ActivityDemoDetailTodoBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_demo_detail_todo);
        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        registerListener();

        String todoId = getIntent().getStringExtra(ARG_TODO_ID);
        if(todoId != null){
            fetchTodo(todoId, new Response<Todo>() {
                @Override
                public void onSuccess(Todo data) {
                    //todo update UI
                }

                @Override
                public void onFailed(Throwable error) {
                    //show error
                }
            });
        }
    }

    private void fetchTodo(String todoId, Response<Todo> callback) {
        TodoRepo todoRepo = new TodoRepo(getApplication());
        //todo fetch todoId;
    }

    private void registerListener() {
        mBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public static Intent getIntent(Context context, String todoId) {
        Intent intent = new Intent(context, DemoDetailTodoActivity.class);
        intent.putExtra(ARG_TODO_ID, todoId);
        return intent;
    }

    static interface Response<T>{
        void onSuccess(T data);
        void onFailed(Throwable error);
    }
}
