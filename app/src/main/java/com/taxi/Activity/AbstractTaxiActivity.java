package com.taxi.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.taxi.R;

public class AbstractTaxiActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void showMessage(String message) {
        Toast.makeText(AbstractTaxiActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    protected void startScreen(Class<?> activity) {
        Intent intent = new Intent(AbstractTaxiActivity.this, activity);
        startActivity(intent);
    }
}
