package com.example.user.easya;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.core.Context;

import static com.firebase.client.Firebase.*;

public class Welcome extends AppCompatActivity {

    String username = "default";
    public EditText usernameInputBox;
    //private Button signupButton ;
    private Firebase mRef;
    //private static int userNo;


    public static final String TAG = "***WELCOME SCREEN***";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        setAndroidContext(this);

        Log.e(TAG, "default username = "+username);//WORKING

        usernameInputBox = (EditText) findViewById(R.id.usernameInputField);
        /*if (usernameInputBox.getText() != null){
            username = usernameInputBox.getText().toString();//NOT WORKING, blank data being saved in username
            Log.e(TAG, "input is NOT null");
        }//editText theke null pele default username ta use korbe. null nahole editText er value username hishebe save korbe

        Log.e(TAG, " username input= "+username);*/

        mRef = new Firebase("https://easya-c2f38.firebaseio.com/");//link to my database


    }

    /*public void signUp(View view){

        Toast.makeText(this, username+" !", Toast.LENGTH_LONG).show();

        signupButton = (Button) findViewById(R.id.signupButt);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase mRefChild = mRef.child(username);
            }


        });
    }*/

    public void goToNext(View view){

        if (usernameInputBox.getText() != null){
            username = usernameInputBox.getText().toString();// WORKING
            Log.e(TAG, "input is NOT null");
        }//editText theke null pele default username ta use korbe. null nahole editText er value username hishebe save korbe

        Log.e(TAG, " username input= "+username);


        Intent i = new Intent(Welcome.this, FirstMenu.class);
        i.putExtra("username", username);
        Log.e(TAG, " username passing to FirstMenu.java = "+username);
        startActivity(i);
    }
}
