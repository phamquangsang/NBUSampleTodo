package com.phamsang.example.todo_android_architecture_components.ui;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.phamsang.example.todo_android_architecture_components.R;
import com.phamsang.example.todo_android_architecture_components.databinding.ActivityTodoDetailBinding;
import com.phamsang.example.todo_android_architecture_components.models.Todo;
import com.phamsang.example.todo_android_architecture_components.viewmodels.TodoDetailViewModel;

public class TodoDetailActivity extends AppCompatActivity implements LifecycleRegistryOwner {

    private static final String TAG = TodoDetailActivity.class.getSimpleName();
    LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    private String mTodoId;
    private BroadcastReceiver mReceiver;

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }

    private static String ARG_TODO_ID = "arg-todo-id";
    private ActivityTodoDetailBinding mBinding;
    private TodoDetailViewModel mViewModel;

    public static Intent getIntent(Context c, @Nullable String todoId){
        Intent i = new Intent(c, TodoDetailActivity.class);
        Bundle b = new Bundle();
        b.putString(ARG_TODO_ID, todoId);
        i.putExtras(b);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_todo_detail);
        mViewModel = ViewModelProviders.of(this).get(TodoDetailViewModel.class);
        mTodoId = getIntent().getExtras().getString(ARG_TODO_ID);
        mViewModel.initTodoId(mTodoId);
        mViewModel.getTodo().observe(this, new Observer<Todo>() {
            @Override
            public void onChanged(@Nullable Todo todo) {
                mBinding.setTodo(todo);
                mBinding.container.setTodo(todo);
            }
        });
        mViewModel.getColorString().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String color) {
                mBinding.getRoot().setBackgroundColor(Color.parseColor(color));
            }
        });

        setSupportActionBar(mBinding.toolbar);


        mBinding.fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Todo todo = new Todo();
                todo.setId(mTodoId);
                todo.setTitle(mBinding.container.title.getText().toString());
                todo.setContent(mBinding.container.content.getText().toString());
                todo.setColor(String.valueOf(mViewModel.getColorString().getValue()));
                todo.setTimeCreated(System.currentTimeMillis());
                todo.setDone(mBinding.container.checkbox.isChecked());
                mViewModel.saveOrCreateTodo(todo);
                finish();
            }
        });

        registerNetworkChange(this);
    }

    private void registerNetworkChange(Context todoDetailActivity) {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ConnectivityManager cm =
                        (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();

                getResources().getStringArray(R.array.color_list);
                Toast.makeText(context, "network changed isCOnnected = "+isConnected, Toast.LENGTH_SHORT).show();
            }
        };
        todoDetailActivity.registerReceiver(mReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    public static interface OnSaveButtonClicked{
        void onClick(Todo todo);
    }

}
