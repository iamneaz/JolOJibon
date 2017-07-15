package com.example.fuhad.jolojibon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

    SharedPreferences dataSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView  name  = (TextView) findViewById(R.id.userName);
        TextView email = (TextView) findViewById(R.id.userEmail);

        dataSave = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        name.setText(dataSave.getString("username", "no user"));
        email.setText(dataSave.getString("email","no email"));




    }

    public void onBackPressed() {
        Intent intent = new Intent(Profile.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}
