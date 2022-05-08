package com.example.patika;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private static final String LOG_TAG = RegisterActivity.class.getName();
    private static final String PREF_KEY = MainActivity.class.getPackage().toString();
    private static final int SECRET_KEY = 66;


    private SharedPreferences preferences;
    private FirebaseAuth mAuth;

    EditText userNameEditText;
    EditText userEmailEditText;
    EditText passwordEditText;
    EditText passwordAgainEditText;
    RadioGroup accountTypeGroup;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        //Bundle bundle = getIntent().getExtras();
        //bundle.getInt("SECRET_KEY");
        int secret_key=getIntent().getIntExtra("SECRET_KEY", 0);

        if (secret_key!=66){
            finish();
        }
        userNameEditText = findViewById(R.id.usernameEditText);
        userEmailEditText = findViewById(R.id.userEmailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordAgainEditText = findViewById(R.id.passwordAgainEditText);
        accountTypeGroup = findViewById(R.id.accountTypeGroup);
        accountTypeGroup.check(R.id.DoctorRadioButton);

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        String userName = preferences.getString("Email", "");
        String password = preferences.getString("Password", "");

        userEmailEditText.setText(userName);
        passwordEditText.setText(password);

        mAuth = FirebaseAuth.getInstance();

        Log.i(LOG_TAG, "onCreate");
    }

    public void register(View view) {
        String userNameStr = userNameEditText.getText().toString();
        String emailStr = userEmailEditText.getText().toString();
        String passwordStr = passwordEditText.getText().toString();
        String passwordAgainStr = passwordAgainEditText.getText().toString();

        if (!passwordStr.equals(passwordAgainStr)){
            Log.e(LOG_TAG, "NEM EGYEZNEK A MEGADOTT JELSZAVAK!");
        }

        int checkedid = accountTypeGroup.getCheckedRadioButtonId();
        RadioButton radioButton = accountTypeGroup.findViewById(checkedid);
        String accountType = radioButton.getText().toString();



        Log.i(LOG_TAG, "Regisztrált: " + userNameStr + ", email: " + emailStr);
        //startPatika();
        mAuth.createUserWithEmailAndPassword(emailStr,passwordStr).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task){
                if (task.isSuccessful()){
                    Log.d(LOG_TAG, "User created successfully");
                    startPatika();
                }else {
                    Log.d(LOG_TAG, "User wasn't created succesfully");
                    Toast.makeText(RegisterActivity.this, "User wasn't created succesfully" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });



    }

    public void cancel(View view) {
        finish();
    }
    private void startPatika(/*regisztrált user data */){
        Intent intent = new Intent(this, PatikaListActivity.class);
        //intent.putExtra("SECRET_KEY", SECRET_KEY);
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
        Log.i(LOG_TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume");
    }
}