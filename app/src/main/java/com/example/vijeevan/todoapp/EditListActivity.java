package com.example.vijeevan.todoapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;


public class EditListActivity extends ActionBarActivity {
    private EditText etitem;
    private final int RESULT_OK = 20;
    private ArrayList<String> todoitems;
    private String item;
    private Long pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list);

        item = getIntent().getStringExtra("item");
        pos = getIntent().getLongExtra("id",0);
        etitem = (EditText) findViewById(R.id.editText);
        etitem.setText(item);
    }

    public void onEditItem(View v) {
        etitem = (EditText) findViewById(R.id.editText);
        Intent data = new Intent();
        data.putExtra("item",etitem.getText().toString());
        data.putExtra("id", pos);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_list, menu);
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
