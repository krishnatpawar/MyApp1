package com.taxi.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.taxi.R;

public class AbstractTaxiActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void startIntent(Context context, Class<?> activity) {
        Intent intent = new Intent(context, activity);
        startActivity(intent);
    }
}
