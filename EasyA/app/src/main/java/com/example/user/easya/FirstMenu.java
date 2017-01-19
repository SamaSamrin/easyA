package com.example.user.easya;

import android.app.IntentService;
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

import org.w3c.dom.Text;

public class FirstMenu extends AppCompatActivity {

    public String username ;
    public String selectedItem ;

    public static final String TAG = "***FIRST MENU***";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_menu);

        Intent i = getIntent();
        username = i.getStringExtra("username");
        Log.e(TAG, " username received = "+username);
        String[] nameArrayTemp = username.split(" ");
        String firstName = nameArrayTemp[0];
        Log.e(TAG, " first name = "+firstName);

        TextView tv = (TextView) findViewById(R.id.usernameDisplay);
        tv.setText("Hello, "+firstName+"!");
        Toast.makeText(FirstMenu.this, username+" logged in", Toast.LENGTH_LONG).show();
        //listview
        menuFormat();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public void menuFormat(){//will show the options through listview
        String[] menu = {"Current Courses", "Class Schedule", "View Previous Result", "Active Chatrooms"};
        ListAdapter menuAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu);

        ListView studentsMenu = (ListView) findViewById(R.id.studentMenu);
        studentsMenu.setAdapter(menuAdapter);

        studentsMenu.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectedItem = String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(FirstMenu.this, selectedItem, Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void goNext(View v){///???????
        Intent in = new Intent(FirstMenu.this, FirstMenuTeacher.class);
        String teacherName = "Sohel Ahmed";
        in.putExtra(teacherName, "teacherName");
        startActivity(in);
    }

    public void goToCourses(View view){
        Intent i = new Intent(FirstMenu.this, CoursesMenu.class);
        startActivity(i);
    }
}
