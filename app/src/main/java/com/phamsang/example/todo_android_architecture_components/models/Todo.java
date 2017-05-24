package com.phamsang.example.todo_android_architecture_components.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;


public class Todo extends BaseObservable {

    private String id;

    private String title;

    private String content;

    private long timeCreated;

    private boolean isDone;

    private String color;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyChange();
    }

    @Bindable
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        notifyChange();
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(long timeCreated) {
        this.timeCreated = timeCreated;
    }

    @Bindable
    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
        notifyChange();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
