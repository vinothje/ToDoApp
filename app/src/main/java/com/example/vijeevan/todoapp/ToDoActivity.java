package com.example.vijeevan.todoapp;

import android.content.Intent;
import android.database.CharArrayBuffer;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;


public class ToDoActivity extends ActionBarActivity {
    private ArrayList<String> todoitems;
    private ArrayAdapter<String> todoAdapter;
    private ListView lvitems;
    private EditText aditem;
    private EditText addate;
    private Integer itempos;
    private String item;
    private String cdate;
    private Long id;
    private Integer posn;
    private final int REQUEST_CODE = 20;
    TodoItemDatabase db = new TodoItemDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        lvitems = (ListView) findViewById(R.id.listView);
        aditem = (EditText) findViewById(R.id.addText);
        addate = (EditText) findViewById(R.id.addDate);

        // TodoDatabaseHandler is a SQLiteOpenHelper class connecting to SQLite
        TodoItemDatabase handler = new TodoItemDatabase(this);
        // Get access to the underlying writeable database
        SQLiteDatabase db1 = handler.getWritableDatabase();
        handler.onUpgrade(db1, 0, 1);

        // Query for items from the database and get a cursor back
        Cursor todoCursor = db1.rawQuery("SELECT  * FROM todo_items", null);

        // Setup cursor adapter using cursor from last step
        TodoCursorAdapter todoAdapter = new TodoCursorAdapter(this, todoCursor);
        // Attach cursor adapter to the ListView
        lvitems.setAdapter(todoAdapter);

        setupListViewListener();
        setupEditItemListener();
    }

    private void setupEditItemListener() {
        lvitems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int pos, long id) {
                SQLiteDatabase db1 = db.getWritableDatabase();
                Cursor record = db1.rawQuery("SELECT  * FROM todo_items", null);
                int ind = 0;
                if(record.getCount()!=0){
                    if(record.moveToFirst()){
                        do{
                            if(ind == pos) {
                                item = record.getString(record.getColumnIndex("item"));
                                cdate = record.getString(record.getColumnIndex("cdate"));
                            }
                            ind++;
                        }while(record.moveToNext());
                    }
                    record.close();
                }
                posn = pos;
                Intent i = new Intent(ToDoActivity.this, EditListActivity.class);
                i.putExtra("id", id);
                i.putExtra("item", item);
                i.putExtra("cdate", cdate);
                Log.v("edit", "id = " + id);
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v("some4", "resulting");
        item = data.getExtras().getString("item");
        cdate = data.getExtras().getString("cdate");
        id = data.getExtras().getLong("id");
        //id = id + 1;
        Log.v("some5", "item:" + item + " date:" + cdate + " pos:" + id);
        TodoItem item_obj = new TodoItem(item, cdate);
        item_obj.setId((int) (long) id);
        item_obj.setItem(item);
        item_obj.setCdate(cdate);
        db.updateTodoItem(item_obj);
        SQLiteDatabase db1 = db.getWritableDatabase();
        Cursor todoCursor = db1.rawQuery("SELECT  * FROM todo_items", null);
        TodoCursorAdapter todoAdapter = new TodoCursorAdapter(this, todoCursor);
        lvitems.setAdapter(todoAdapter);
    }

    private void setupListViewListener() {
        lvitems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                todoitems.remove(pos);
                todoAdapter.notifyDataSetChanged();
                writeItems();
                return false;
            }
        });
    }

    public void onAddItem(View v) {
        String item = aditem.getText().toString();
        String cdate = addate.getText().toString();
        aditem.setText("");
        addate.setText("");
        db.addTodoItem(new TodoItem(item, cdate));

        // Query for items from the database and get a cursor back
        TodoItemDatabase handler = new TodoItemDatabase(this);
        // Get access to the underlying writeable database
        SQLiteDatabase db1 = handler.getWritableDatabase();
        Cursor todoCursor = db1.rawQuery("SELECT  * FROM todo_items", null);

        // Setup cursor adapter using cursor from last step
        TodoCursorAdapter todoAdapter_new = new TodoCursorAdapter(this, todoCursor);
        // Attach cursor adapter to the ListView
        lvitems.setAdapter(todoAdapter_new);
    }

    private void readItems() {
        List<TodoItem> todoitemlist = new ArrayList<TodoItem>();
        todoitems = new ArrayList<String>();
        TodoItemDatabase db = new TodoItemDatabase(this);
        todoitemlist = db.getAllTodoItems();
        for(TodoItem t: todoitemlist) {
            todoitems.add(t.getItem());
        }
    }

    private void writeItems() {
        File fh = getFilesDir();
        File filelist = new File(fh, "todo.txt");
        try {
            BufferedWriter br= new BufferedWriter(new FileWriter(filelist));
            for (String s : todoitems) {
                br.write(s);
                br.newLine();
            }
            br.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_to_do, menu);
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
}

