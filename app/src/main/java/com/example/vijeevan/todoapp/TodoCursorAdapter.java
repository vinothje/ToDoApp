package com.example.vijeevan.todoapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by vijeevan on 1/13/2015.
 */
public class TodoCursorAdapter extends CursorAdapter {
    public TodoCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.todo_items, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tvItem = (TextView) view.findViewById(R.id.tvItem);
        TextView tvDate = (TextView) view.findViewById(R.id.tvDate);
        // Extract properties from cursor
        String item = cursor.getString(cursor.getColumnIndexOrThrow("item"));
        String cdate = cursor.getString(cursor.getColumnIndexOrThrow("cdate"));
        // Populate fields with extracted properties
        tvItem.setText(item);
        tvDate.setText(cdate);
    }
}
