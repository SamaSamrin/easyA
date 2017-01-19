package com.example.user.easya;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class CoursesMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_menu);

        ListView coursesList = (ListView) findViewById(R.id.coursesList);
        String[] courses = {"CSE110", "MAT110", "ENG102", "PHY111"};
        ListAdapter coursesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, courses);
        coursesList.setAdapter(coursesAdapter);
    }
}
