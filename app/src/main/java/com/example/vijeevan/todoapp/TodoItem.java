package com.example.vijeevan.todoapp;

/**
 * Created by vijeevan on 1/11/2015.
 */
public class TodoItem {
    private int id;
    private String item;

    public TodoItem(String item) {
        super();
        this.item = item;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
