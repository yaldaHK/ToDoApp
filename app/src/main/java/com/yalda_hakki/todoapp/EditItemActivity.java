package com.yalda_hakki.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class EditItemActivity extends AppCompatActivity {
    EditText etEditItemField;
    // REQUEST_CODE can be any value we like, used to determine the result type later
    private final int REQUEST_CODE = 1;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        etEditItemField =(EditText) findViewById(R.id.etEditItemField);

        String value=getIntent().getStringExtra("itemValue");
        position = getIntent().getIntExtra("position", 0);
        etEditItemField.setText(value, TextView.BufferType.EDITABLE);
        //Be sure the user's cursor in the text field is at the end of the current text value
        etEditItemField.setSelection(etEditItemField.getText().length());
        //setContentView(etEditItemField);
    }
    public void onSave(View view) {
        String value = etEditItemField.getText().toString();
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("itemValue", value);
        i.putExtra("position", position);
        // set result code and bundle data for response
        setResult(RESULT_OK, i);
        // closes the activity and returns to first screen
        this.finish();
    }
}
