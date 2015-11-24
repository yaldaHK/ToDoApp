package com.yalda_hakki.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewDebug;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> ToDoItems;
    ArrayAdapter<String> aToDoAdapter;
    ListView lvItems;
    EditText etNewItem;
    private final int REQUEST_CODE = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView )findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);
        etNewItem =(EditText) findViewById(R.id.etNewItem);
        setupListViewListner();

    }
    public void populateArrayItems(){
        ToDoItems = new ArrayList<String>();
        //ToDoItems.add("Item 1");
        //ToDoItems.add("Item 2");
        //ToDoItems.add("Item 3");
        readItems();
        aToDoAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,ToDoItems);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void onAddItem(View view) {
        String itemText = etNewItem.getText().toString();
        aToDoAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();

    }

    private void setupListViewListner(){
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                        ToDoItems.remove(pos);
                        aToDoAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                });
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                        launchEditItemView(pos);

                    }
                });
    }
    private void readItems(){
        File filesDir = getFilesDir();
        File toDoFile = new File(filesDir,"toDo.txt");
        try{
            ToDoItems = new ArrayList<String>(FileUtils.readLines(toDoFile));
        }catch (IOException e){
            ToDoItems = new ArrayList<String>();
        }
    }
    private void writeItems(){
        File filesDir = getFilesDir();
        File todoFile = new File (filesDir,"toDo.txt");
        try{
            FileUtils.writeLines(todoFile, ToDoItems);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    private void launchEditItemView(int pos) {
        // first parameter is the context, second is the class of the activity to launch
        Intent i = new Intent(this, EditItemActivity.class);
        //pass along the text and position of that item to the second activity
        i.putExtra("itemValue", ToDoItems.get(pos));
        i.putExtra("position", pos);
        i.putExtra("mode", 2);
        startActivityForResult(i, REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String value=i.getExtras().getString("itemValue");
            int pos = i.getExtras().getInt("position",0);
            ToDoItems.set(pos,value);
            aToDoAdapter.notifyDataSetChanged();
            writeItems();
        }
    }

}
