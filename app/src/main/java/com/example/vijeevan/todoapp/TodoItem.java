package com.example.vijeevan.todoapp;

/**
 * Created by vijeevan on 1/11/2015.
 */
public class TodoItem {
    private int id;
    private String item;
    private String cdate;

    public TodoItem(String item, String cdate) {
        super();
        this.item = item;
        this.cdate = cdate;
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

    public String getCdate() { return cdate; }

    public void setCdate(String cdate) { this.cdate = cdate; }
}
