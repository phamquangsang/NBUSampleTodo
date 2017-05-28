package com.phamsang.example.todo_android_architecture_components.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.phamsang.example.todo_android_architecture_components.R;
import com.phamsang.example.todo_android_architecture_components.databinding.ActivityDemoDetailTodoBinding;
import com.phamsang.example.todo_android_architecture_components.models.Todo;
import com.phamsang.example.todo_android_architecture_components.repo.TodoRepo;
import com.phamsang.example.todo_android_architecture_components.viewmodels.DemoDetailViewModel;

public class DemoDetailTodoActivity extends LifecycleActivity {

    private static final String ARG_TODO_ID = "arg-todo-id";
    private ActivityDemoDetailTodoBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_demo_detail_todo);
        registerListener();



        String todoId = "fakeTodoId";

        DemoDetailViewModel viewModel = ViewModelProviders.of(this).get(DemoDetailViewModel.class);
        viewModel.fetchTodo(todoId);
        viewModel.getTodo().observe(this, new Observer<Todo>() {
            @Override
            public void onChanged(@Nullable Todo todo) {
                if(todo!=null){
                    mBinding.content.setTodo(todo);
                }
            }
        });
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


}
