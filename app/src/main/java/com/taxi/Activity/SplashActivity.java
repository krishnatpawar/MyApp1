package com.taxi.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.taxi.R;
import com.taxi.application.AppConfig;
import com.taxi.utils.Preferences;

public class SplashActivity extends AbstractTaxiActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AppConfig.setCurentContext(this);
        if (!Preferences.getUserId(SplashActivity.this).isEmpty()) {
            loginIntoApp(HomeScreenActivity.class);
        } else {
            loginIntoApp(MainActivity.class);
        }
    }

    private void loginIntoApp(final Class<?> toClass) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    startScreen(toClass);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppConfig.setCurentContext(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        AppConfig.setCurentContext(this);
    }
}
