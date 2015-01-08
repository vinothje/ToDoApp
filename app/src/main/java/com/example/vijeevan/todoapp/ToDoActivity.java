package com.example.vijeevan.todoapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class ToDoActivity extends ActionBarActivity {
    private ArrayList<String> todoitems;
    private ArrayAdapter<String> todoAdapter;
    private ListView lvitems;
    private EditText aditem;
    private Integer itempos;
    private String item;
    private Integer posn;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        lvitems = (ListView) findViewById(R.id.listView);
        aditem = (EditText) findViewById(R.id.addText);
        readItems();
        todoAdapter = new ArrayAdapter<String>(getBaseContext(),
                      android.R.layout.simple_list_item_1, todoitems);
        lvitems.setAdapter(todoAdapter);
        setupListViewListener();
        setupEditItemListener();
    }

    private void setupEditItemListener() {
        lvitems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int pos, long id) {
                item = todoitems.get(pos);
                Intent i = new Intent(ToDoActivity.this, EditListActivity.class);
                i.putExtra("pos", pos);
                i.putExtra("item", item);
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v("some4", "resulting");
        item = data.getExtras().getString("item");
        posn = data.getExtras().getInt("pos");
        Log.v("some5", "item:" + item + " pos:" + posn);
        todoitems.set(posn, item);
        todoAdapter.notifyDataSetChanged();
        writeItems();
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
        todoAdapter.add(item);
        aditem.setText("");
        writeItems();
    }

    private void readItems() {
        File fh = getFilesDir();
        File filelist = new File(fh, "todo.txt");
        try {
            BufferedReader br= new BufferedReader(new FileReader(filelist));
            String line = null;
            todoitems = new ArrayList<String>();
            while ((line = br.readLine()) != null) {
                todoitems.add(line);
            }
            br.close();
        } catch(Exception e) {
            todoitems = new ArrayList<String>();
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
