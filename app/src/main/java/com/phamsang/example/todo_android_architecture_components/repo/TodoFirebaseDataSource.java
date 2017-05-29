package com.phamsang.example.todo_android_architecture_components.repo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.phamsang.example.todo_android_architecture_components.lifecycleawarecomponent.FirebaseLiveData;
import com.phamsang.example.todo_android_architecture_components.models.Todo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TodoFirebaseDataSource implements DataSource {

    private static final String TAG = TodoFirebaseDataSource.class.getSimpleName();


    private ValueEventListener mGetListTodoValueListener;

    private DatabaseReference mGetListTodoReference;

    private ValueEventListener mGetTodoListener;

    private DatabaseReference mGetTodoRef;

    public TodoFirebaseDataSource() {
    }

    @Override
    public void addTodo(Todo todo,@Nullable final CompleteCallback completeCallback){
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference().child("todos").push();
        todo.setId(ref.getKey());
        ref.setValue(todo, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(completeCallback == null){
                    return;
                }
                if(databaseError != null){
                    completeCallback.onFailure(databaseError.toException());
                }else{
                    completeCallback.onSuccess();
                }
            }
        });
    }

    @Override
    public void getListTodo(final MutableLiveData<List<Todo>> todoList, boolean forceRefresh,final @Nullable CompleteCallback completeCallback){

        mGetListTodoValueListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Todo> list = new ArrayList<>();
                for (DataSnapshot todoSnap :
                        dataSnapshot.getChildren()) {
                    Todo todo = todoSnap.getValue(Todo.class);
                    list.add(todo);
                }
                todoList.setValue(list);

                if(completeCallback!=null){
                    completeCallback.onSuccess();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if(completeCallback!=null){
                    completeCallback.onFailure(databaseError.toException());
                }
            }
        };
        mGetListTodoReference = FirebaseDatabase.getInstance().getReference()
                .child("todos");
        mGetListTodoReference.addListenerForSingleValueEvent(mGetListTodoValueListener);
    }

    @Override
    public LiveData<Map<String, Todo>> getListTodoSync(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child("todos");
        return new FirebaseLiveData<Map<String,Todo>>(ref, new GenericTypeIndicator<Map<String, Todo>>() {});
    }

    @Override
    public void onCleared() {
        if(mGetListTodoReference != null){
            mGetListTodoReference.removeEventListener(mGetListTodoValueListener);
            Log.i(TAG, "onCleared: mGetListTodoReference remove listener");
        }

        if(mGetTodoRef != null){
            mGetTodoRef.removeEventListener(mGetTodoListener);
        }

    }

    @Override
    public void getTodo(String todoId, final MutableLiveData<Todo> todo) {
        mGetTodoRef = FirebaseDatabase.getInstance().getReference()
                .child("todos").child(todoId);
        mGetTodoListener = (new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        todo.setValue(dataSnapshot.getValue(Todo.class));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        mGetTodoRef.addValueEventListener(mGetTodoListener);
    }

    @Override
    public void updateTodo(Todo todo,@Nullable final CompleteCallback completeCallback) {
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference().child("todos").child(todo.getId());
        ref.setValue(todo, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(completeCallback == null){
                    return;
                }
                if(databaseError != null){
                    completeCallback.onFailure(databaseError.toException());
                }else{
                    completeCallback.onSuccess();
                }
            }
        });
    }

    @Override
    public void deleteTodo(String todoId,@Nullable final CompleteCallback completeCallback) {
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference().child("todos").child(todoId);
        ref.setValue(null, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(completeCallback == null){
                    return;
                }
                if(databaseError != null){
                    completeCallback.onFailure(databaseError.toException());
                }else{
                    completeCallback.onSuccess();
                }
            }
        });
    }
}
