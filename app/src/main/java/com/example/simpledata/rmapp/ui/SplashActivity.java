package com.example.simpledata.rmapp.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.simpledata.rmapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mAuth = FirebaseAuth.getInstance();
        startTimer();
    }

    private void startTimer(){
        TimerTask task = task();
        Timer timer = new Timer();
        timer.schedule(task, 3000);
    }

    private TimerTask task(){
        return new TimerTask() {
            @Override
            public void run() {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if(currentUser != null)
                    goToMain();
                else
                    goToLogin();

                finish();
            }
        };
    }

    private void goToMain() {
        startActivity(new Intent().setClass(SplashActivity.this, MainActivity.class));
    }

    private void goToLogin() {
        startActivity(new Intent().setClass(SplashActivity.this, AuthActivity.class));
    }
}
