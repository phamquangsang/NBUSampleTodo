package com.phamsang.example.todo_android_architecture_components.ui;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.phamsang.example.todo_android_architecture_components.R;
import com.phamsang.example.todo_android_architecture_components.databinding.ActivityTodoListBinding;
import com.phamsang.example.todo_android_architecture_components.lifecycleawarecomponent.BroadCastReceiverLifecyle;
import com.phamsang.example.todo_android_architecture_components.viewmodels.TodoListViewModel;

public class TodoTodoListActivity extends AppCompatActivity
        implements View.OnClickListener, LifecycleRegistryOwner{

    private static final String TAG = TodoTodoListActivity.class.getSimpleName();
    private TodoListViewModel mTodoListViewModel;
    private TodoFragment mFragment;
    private ActivityTodoListBinding mBinding;

    LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    @Override
    public LifecycleRegistry getLifecycle() {
        return mLifecycleRegistry;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_todo_list);
        setSupportActionBar(mBinding.toolbar);

        mTodoListViewModel = ViewModelProviders.of(this).get(TodoListViewModel.class);

        mTodoListViewModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean == null){
                    return;
                }
                mBinding.swipeToRefresh.setRefreshing(aBoolean);
            }
        });


        mBinding.fab.setOnClickListener(this);

        mBinding.swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mTodoListViewModel.getTodoList(true);
            }
        });

        if(savedInstanceState == null){
            mFragment = TodoFragment.newInstance(2);
            getSupportFragmentManager().beginTransaction().add(R.id.container, mFragment).commit();
        }


        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

        BroadCastReceiverLifecyle broadCastReceiver = new BroadCastReceiverLifecyle(this, getLifecycle(), intentFilter) {
            @Override
            public void onReceive(Context context, Intent intent) {
                Snackbar.make(mBinding.getRoot(), "Network Changed!", Snackbar.LENGTH_SHORT).show();
            }
        };

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                Intent i = TodoDetailActivity.getIntent(TodoTodoListActivity.this, null);
                startActivity(i);
                break;
        }
    }
}
