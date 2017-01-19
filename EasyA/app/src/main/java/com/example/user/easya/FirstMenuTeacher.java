package com.example.user.easya;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FirstMenuTeacher extends AppCompatActivity {

    public String teacherName ;
    public String selectedItem ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_menu);

        Intent i = getIntent();
        //teacherName = i.getStringExtra("teacherName");

        //TextView tv = (TextView) findViewById(R.id.teachersNameDisplay);
        //tv.setText(username);
        //Toast.makeText(FirstMenu.this, username, Toast.LENGTH_LONG).show();
        //listview
        //menuFormat();
        ListView teacherMenu = (ListView) findViewById(R.id.teachersMenu);
        String[] menu2 = {"Current Courses", "Class Schedule", "Unresolved Queries"};
        ListAdapter adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, menu2);
        teacherMenu.setAdapter(adapter2);


        /*teachersMenu.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectedItem = String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(FirstMenuTeacher.this, selectedItem, Toast.LENGTH_LONG).show();
                    }
                }
        );*/
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public void menuFormat(){


        teachersMenu = (ListView) findViewById(R.id.teachersMenu);
        String[] menu = {"Current Courses", "Class Schedule", "Unresolved Queries"};
        ListAdapter menuAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu);
        teachersMenu.setAdapter(menuAdapter);

        teachersMenu.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectedItem = String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(FirstMenuTeacher.this, selectedItem, Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    /*public void goBack(View v){
        Intent i = new Intent(FirstMenu.this, Welcome.class);

    }*/
}


