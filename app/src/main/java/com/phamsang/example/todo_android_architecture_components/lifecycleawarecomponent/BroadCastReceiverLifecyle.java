package com.phamsang.example.todo_android_architecture_components.lifecycleawarecomponent;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.util.Log;


public abstract class BroadCastReceiverLifecyle extends BroadcastReceiver implements LifecycleObserver{

    private static final String TAG = BroadCastReceiverLifecyle.class.getSimpleName();
    private final Lifecycle mLifeCycle;
    private final Context mContext;
    private final IntentFilter mIntentFilter;

    public BroadCastReceiverLifecyle(Context c, Lifecycle lifeCycle, IntentFilter intentFilter) {
        super();
        mContext = c;
        mLifeCycle = lifeCycle;
        mIntentFilter = intentFilter;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void start() {
        mContext.registerReceiver(this, mIntentFilter);
        Log.i(TAG, "start: registerReceiver");
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void stop() {
        // disconnect if connected
        mContext.unregisterReceiver(this);
        Log.i(TAG, "stop: unregisterReceiver");
    }
}
