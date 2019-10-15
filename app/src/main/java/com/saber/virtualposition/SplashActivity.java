package com.saber.virtualposition;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.saber.virtualposition.service.BgmService;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        toMainActivity();
        //startBgmService();
    }

    private void toMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        SplashActivity.this.finish();
    }

    private void startBgmService() {
        Intent forGroundService = new Intent(this, BgmService.class);
        startService(forGroundService);
    }
}
