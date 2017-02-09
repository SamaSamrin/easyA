package com.example.user.easya;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

//import com.firebase.client.core.Context;
//import com.google.android.gms.auth.*;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
//import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
//import com.firebase.client.Firebase.*;

//basic
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
//analytics
import com.google.firebase.analytics.FirebaseAnalytics;
//authentication
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
//database
import com.google.firebase.database.FirebaseDatabase;
//android imports
import android.text.TextUtils;

public class Welcome extends Loading implements GoogleApiClient.OnConnectionFailedListener
{
    //ui
    String email = "default@mail.com";
    String username = "John Doe";
    String password = "";
    private EditText emailInputBox;
    private EditText passwordBox;
    static Button signOut ;
    static ImageButton signIn;
    //analytics
    private FirebaseAnalytics fanalytics;
    //authentication
    public static final String EmailProvider = EmailAuthProvider.PROVIDER_ID;
    public static final String GoogleProvider = GoogleAuthProvider.PROVIDER_ID;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser user;

    private FirebaseApp mApp /*= FirebaseApp.getInstance()*/;

    //private Button signupButton ;
    private static final int RC_SIGN_IN = 9001;//request code
    private static GoogleApiClient mGoogleApiClient;
   // private Firebase mRef;
    private static FirebaseDatabase mDatabase;
    public static final String TAG = "***WELCOME SCREEN***";

    //constructor
    public Welcome(){
        super();
        mApp = FirebaseApp.getInstance();
    }
    public Welcome(FirebaseApp app) {
        mApp = app;
        //mAuth = FirebaseAuth.getInstance(mApp);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        /*
        //FirebaseApp.initializeApp(this);
       // Firebase.setAndroidContext(this);
        if(mDatabase==null) {
            mDatabase =  FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        //Firebase.getDefaultConfig().setPers*/

        Log.e(TAG, "default email = "+email);//WORKING
        //ui references
        emailInputBox = (EditText) findViewById(R.id.emailInput);
        passwordBox = (EditText) findViewById(R.id.passwordInputField);
        signIn = (ImageButton) findViewById(R.id.googleSignIn);
        //analytics
        fanalytics = FirebaseAnalytics.getInstance(this);
        //authentication
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        setmAuthListener();

       // mRef = new Firebase("https://easya-c2f38.firebaseio.com/");//link to my database
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
       // GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        //mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this /* FragmentActivity */, this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
        //signOut = (Button) findViewById(R.id.logOutButton);
    }

    public void onStart(){
        super.onStart();
        //for authentication, registering a listener for the currently signed-in user
        mAuth.addAuthStateListener(mAuthListener);
        //Toast.makeText(this, "registered", Toast.LENGTH_LONG).show();
    }

    //commented out since amra chaina ei activity chere next activity-te gelei user unregistered hoye jak
    /*
    public void onStop(){
        super.onStop();
        //for authentication, to unregister a listener for the currently signed-in user
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
            //Toast.makeText(this, "unregistered", Toast.LENGTH_LONG).show();
        }
    }*/

    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void setmAuthListener(){
        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth fbauth) {
                user = fbauth.getCurrentUser();
                if(user != null){
                    //user is signed in
                    Log.e(TAG, "signed in = "+ user.getUid());
                }else{
                    //user is logged out
                    Log.e(TAG, "signed out");
                }
            }
        };
    }

    private void getSignInInfo(){
        if ((emailInputBox.getText() == null) && (passwordBox.getText() == null)){
            emailInputBox.setError("Required");
            passwordBox.setError("Required");
            Toast.makeText(this, "You haven't entered username or password!", Toast.LENGTH_LONG).show();
        } else {
            if (emailInputBox.getText() != null){
                email = emailInputBox.getText().toString();// WORKING
                Log.e(TAG, "input is NOT null");
            }//editText theke null pele default username ta use korbe. null nahole editText er value username hishebe save korbe
            else{
                emailInputBox.setError("Required");
                //Toast.makeText(this, "You haven't entered the username!", Toast.LENGTH_LONG).show();
            }
            if(passwordBox.getText() != null){
                password = passwordBox.getText().toString();
            }else{
                passwordBox.setError("Required");
                //Toast.makeText(this, "You haven't entered the password!", Toast.LENGTH_LONG).show();
            }
        }
    }
    ///*
   // ETA ADMIN KORBE, TAR KACHE NECESARY INFO SHOHO REQUEST GELE, TO MAKE A NEW ACCOUNT
    private void createNewUser(){

        if(!validateUser()) return;
        showProgressDialog();

        Task<AuthResult> task1 = mAuth.createUserWithEmailAndPassword(email, password);
        task1.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.e(TAG, "is sign in successful? - "+task.isSuccessful());
                if(! task.isSuccessful()){
                    Log.e(TAG, "sign in failed, details: "+task.getException());
                    Toast.makeText(Welcome.this, "Sign in failed", Toast.LENGTH_LONG).show();
                }
                hideProgressDialog();
            }
        });
    }//*/

    private void signInExistingUsers(){
        if(!validateUser()) return;

        showProgressDialog();

        Task<AuthResult> task1 = mAuth.signInWithEmailAndPassword(email, password);
        task1.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.e(TAG, "is sign in successful? - "+task.isSuccessful());
                if(! task.isSuccessful()){
                    Log.e(TAG, "sign in failed, details: "+task.getException());
                    Toast.makeText(Welcome.this, "Sign in failed", Toast.LENGTH_LONG).show();
                }
                hideProgressDialog();
            }
        });
    }

    private boolean validateUser(){
        boolean valid = true;
        //box-gula empty rakhle invalid hoye jabe, empty na hole error=null
        if(TextUtils.isEmpty(email))
           valid = false;
        else emailInputBox.setError(null);

        if(TextUtils.isEmpty(password))
            valid = false;
        else passwordBox.setError(null);

        if(user==null)
            valid = false;

        return valid;
    }

    private void signOut(){
        mAuth.signOut();
    }

    public void goToNext(View view){
        //setmAuthListener();
        getSignInInfo();

        if(validateUser()){
            username = user.getDisplayName();
            email = user.getEmail();
        }else {
            getSignInInfo();
            if((email.equalsIgnoreCase("default@mail.com") == false)&&(username.equalsIgnoreCase("John Doe") == false) ) {
                signInExistingUsers();
            }
            //email ba password empty rakhle next screen e jabena
        }
        Log.e(TAG, " email input= "+email);
        /*
        handleFirebase();
        */
        Intent i = new Intent(Welcome.this, FirstMenu.class);
        i.putExtra("username", username);
        Log.e(TAG, " username passing to FirstMenu.java = "+username);
        startActivity(i);
    }
/*
    public void signIntoGoogle(View v){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            username = acct.getDisplayName();
            handleFirebase();
            //sending to the next activity
            Intent i = new Intent(Welcome.this, FirstMenu.class);
            i.putExtra("username", username);
            Log.e(TAG, " username passing to FirstMenu.java = "+username);
            startActivity(i);
        } else {
            // Signed out, show unauthenticated UI.
        }
    }

    public static void signOut(){
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        updateUI(false);//sign out hole login button dekhabe n logout button muche dibe
                    }
                }
        );
    }

    private void handleFirebase(){
        //handling firebase
        Firebase mRefChildName = mRef.child("Name");
        mRefChildName.setValue(username);
        Firebase mRefChildID = mRef.child("ID");
        mRefChildID.setValue(10101);
    }
    public static GoogleApiClient getmGoogleApiClient(){
        return mGoogleApiClient;
    }

    public static void updateUI(boolean signedIn){
        if(signedIn){
            signOut.setVisibility(View.GONE);
            signIn.setVisibility(View.VISIBLE);
        }else{
            signIn.setVisibility(View.GONE);
            signOut.setVisibility(View.VISIBLE);
        }
    }*/


}
