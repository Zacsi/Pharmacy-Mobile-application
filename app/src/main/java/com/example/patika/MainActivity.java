package com.example.patika;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();
    private static final String PREF_KEY = MainActivity.class.getPackage().toString();
    private static final int SECRET_KEY= 66;
    private ImageView imageView;

    private SharedPreferences preferences;
    private FirebaseAuth mAuth;
    private NotificationHandler mNotificationHandler;

    EditText userName;
    EditText password;
    Vibrator vibrator;
    Button btVibrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         userName = findViewById(R.id.username);
         password = findViewById(R.id.password);

        mNotificationHandler = new NotificationHandler(this);


        imageView = findViewById(R.id.Logo);

        @SuppressLint("CutPasteId") ImageButton buttonanimate = findViewById(R.id.Logo);
        buttonanimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YoYo.with(Techniques.RubberBand)
                        .duration(700)
                        .repeat(1)
                        .playOn(imageView);
            }
        });

        btVibrate = findViewById(R.id.vibrate_button);
        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);

        btVibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrator.vibrate(1000);
            }
        });





         preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();

        Button button = findViewById(R.id.loginAnomymus_button);
        new RandomAsyncTask(button).execute();


        Log.i(LOG_TAG, "onCreate");
    }

    public void login(View view) {
        EditText userName = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);

        String userNameStr = userName.getText().toString();
        String passwordStr = password.getText().toString();

        mAuth.signInWithEmailAndPassword(userNameStr, passwordStr).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d(LOG_TAG, "Login was successful");
                    mNotificationHandler.send("Login was successful");
                    startPatika();
                }else {
                    Log.d(LOG_TAG, "Login wasn't successful");
                    Toast.makeText(MainActivity.this, "User wasn't created" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    mNotificationHandler.send("Hibás adatok");
                }
            }
        });
    }

    private void startPatika(){
        Intent intent = new Intent(this, PatikaListActivity.class);
        startActivity(intent);
    }


    public void loginAsGuest(View view) {
        mAuth.signInAnonymously().addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d(LOG_TAG, "Anonymous Login was succesfull");
                    mNotificationHandler.send("Anonymous Login was succesfull");
                    startPatika();
                }else {
                    Log.d(LOG_TAG, "Anonymous Login wasn't succesfull");
                    mNotificationHandler.send("Anonymous Login wasn't succesfull");
                    Toast.makeText(MainActivity.this, "User wasn't created succesfully" + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                }

            }
        });
    }


    public void register(View view) {
        Intent intent = new Intent(this,RegisterActivity.class);
        intent.putExtra("SECRET_KEY",66);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOG_TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Email",userName.getText().toString());
        editor.putString("Password",password.getText().toString());
        editor.apply();

        Log.i(LOG_TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume");
    }


}