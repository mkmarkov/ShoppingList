package com.sirsdev.shoppinglist.shoppinglist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.ListPreference;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Context context;
    LinearLayout linLayout;
    List<CheckBox> cbList;
    int fontSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context=getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cbList = new ArrayList<>();
        linLayout = (LinearLayout) findViewById(R.id.linear);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Add an item:");
                final EditText input = new EditText(context);
                input.setTextColor(Color.BLACK);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CheckBox cb = new CheckBox(context);
                        cb.setTextColor(Color.BLACK);
                        cb.setText(input.getText().toString());
                        cbList.add(cb);
                        updateScreen();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
        FloatingActionButton fab_clear = (FloatingActionButton) findViewById(R.id.fab_clear);
        fab_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Remove checked items?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Iterator<CheckBox> itr = cbList.iterator();
                        int index=-1;
                        while(itr.hasNext()){
                            index++;
                            CheckBox temp = itr.next();
                            if (temp.isChecked()) {
                                itr.remove();
                            }
                        }
                        updateScreen();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }
    void updateScreen()
    {
        linLayout.removeAllViews();
        Iterator<CheckBox> iterator = cbList.iterator();
        while(iterator.hasNext())
            {
                linLayout.addView(iterator.next());
            }
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
        if (id == R.id.action_fontsize) {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Font Size:");
            final NumberPicker input = new NumberPicker(context);
            input.setMinValue(25);
            input.setValue(55);
            input.setMaxValue(80);

            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    fontSize = input.getValue();
                    Iterator<CheckBox> itr = cbList.iterator();
                    while(itr.hasNext())
                    {
                        itr.next().setTextSize(fontSize);
                    }
                    updateScreen();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();

            return true;
        }
        if (id == R.id.clearAll) {
            cbList.clear();
            updateScreen();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
