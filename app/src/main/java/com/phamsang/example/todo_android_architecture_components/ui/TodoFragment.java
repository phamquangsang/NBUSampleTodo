package com.phamsang.example.todo_android_architecture_components.ui;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phamsang.example.todo_android_architecture_components.R;
import com.phamsang.example.todo_android_architecture_components.databinding.FragmentTodoListBinding;
import com.phamsang.example.todo_android_architecture_components.models.Todo;
import com.phamsang.example.todo_android_architecture_components.viewmodels.TodoListViewModel;

import java.util.List;


public class TodoFragment extends LifecycleFragment {

    private static final String TAG = TodoFragment.class.getSimpleName();
    private TodoListViewModel mTodoViewModel;

    private FragmentTodoListBinding mBinding;
    private OnTodoListInteract mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TodoFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TodoFragment newInstance(int columnCount) {
        TodoFragment fragment = new TodoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_todo_list, container, false);
        mBinding.list.setLayoutManager(new GridLayoutManager(getContext(), 2));
        return mBinding.getRoot();
    }


    public interface OnTodoListInteract {
        void onTodoClicked(Todo item);

        void onDeleteClicked(Todo item);

        void onCheckboxClicked(Todo item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTodoViewModel = ViewModelProviders.of(getActivity()).get(TodoListViewModel.class);

        if(savedInstanceState == null){
            mTodoViewModel.setLoading(true);
        }

        mListener = new OnTodoListInteract() {
            @Override
            public void onTodoClicked(Todo item) {
                Log.i(TAG, "onTodoClicked: todoId: "+item.getId());
                Intent i = TodoDetailActivity.getIntent(getActivity(), item.getId());
                startActivity(i);
            }

            @Override
            public void onDeleteClicked(Todo item) {
                mTodoViewModel.deleteTodo(item.getId());
            }

            @Override
            public void onCheckboxClicked(Todo item) {
                item.setDone(!item.isDone());
                mTodoViewModel.updateTodo(item);
            }
        };
        mTodoViewModel.getTodoList().observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(@Nullable List<Todo> todos) {
                if(todos == null){
                    return;
                }
                mTodoViewModel.setLoading(false);
                mBinding.list.setAdapter(new TodoAdapter(todos, mListener));
            }
        });
    }

}
