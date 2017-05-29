package com.phamsang.example.todo_android_architecture_components.lifecycleawarecomponent;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

public class FirebaseLiveData<T> extends LiveData<T> {

    private static final String TAG = FirebaseLiveData.class.getSimpleName();
    private final DatabaseReference mReference;
    private final GenericTypeIndicator<T> mType;
    private ValueEventListener mListener;

    public FirebaseLiveData(DatabaseReference ref, GenericTypeIndicator<T> type) {
        mReference = ref;
        mType = type;
    }

    @Override
    protected void onActive() {
        mListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setValue(dataSnapshot.getValue(mType));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mReference.addValueEventListener(mListener);
        super.onActive();
    }

    @Override
    protected void onInactive() {
        Log.i(TAG, "onInactive: ");
        mReference.removeEventListener(mListener);
    }
}
