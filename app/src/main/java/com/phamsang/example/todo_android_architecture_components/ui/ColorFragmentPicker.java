package com.phamsang.example.todo_android_architecture_components.ui;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phamsang.example.todo_android_architecture_components.R;
import com.phamsang.example.todo_android_architecture_components.models.Todo;
import com.phamsang.example.todo_android_architecture_components.viewmodels.TodoDetailViewModel;


public class ColorFragmentPicker extends LifecycleFragment {

    private OnListColorPickedListener mListener;
    private TodoDetailViewModel mViewModel;
    private RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ColorFragmentPicker() {
    }

    public static ColorFragmentPicker newInstance() {
        ColorFragmentPicker fragment = new ColorFragmentPicker();
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(TodoDetailViewModel.class);

        final String[] colorArray = getResources().getStringArray(R.array.color_list);

        mViewModel.getTodo().observe(this, new Observer<Todo>() {
            @Override
            public void onChanged(@Nullable Todo todo) {
                if(todo != null){
                    mViewModel.initColor(todo.getColor() != null ? todo.getColor() : colorArray[0]);
                }
            }
        });

        mListener = new OnListColorPickedListener() {
            @Override
            public void onColorPicked(String item) {
                mViewModel.getColorString().setValue(item);
            }
        };
        recyclerView.setAdapter(new ColorAdapter(colorArray, mListener));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_color_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setHasFixedSize(true);
        }
        return view;
    }

    public interface OnListColorPickedListener {
        void onColorPicked(String item);
    }
}
