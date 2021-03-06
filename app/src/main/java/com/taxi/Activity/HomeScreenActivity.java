package com.taxi.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.taxi.R;
import com.taxi.application.AppConfig;
import com.taxi.fragments.AboutFragment;
import com.taxi.fragments.FragmentDrawer;
import com.taxi.fragments.InviteEarnFragment;
import com.taxi.fragments.MapFragment;
import com.taxi.fragments.MyMoneyFragment;
import com.taxi.fragments.MyRidesFragment;

public class HomeScreenActivity extends ActionBarActivity implements FragmentDrawer.FragmentDrawerListener {

    private static String TAG = MainActivity.class.getSimpleName();
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private CharSequence mTitle;
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        AppConfig.setCurentContext(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);
    }

    public void setMapToolbar(Toolbar mapToolbar) {
        this.mToolbar = mapToolbar;
        setSupportActionBar(mToolbar);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new MyRidesFragment();
                title = getString(R.string.title_rides);
                break;
            case 1:
//                Intent intent = new Intent(getApplicationContext(), MapActicity.class);
//                startActivity(intent);
                fragment = new MapFragment();
//                title = "";
                break;
            case 2:
                fragment = new MyMoneyFragment();
                title = getString(R.string.title_mymoney);
                break;
            case 3:
                fragment = new AboutFragment();
                title = getString(R.string.title_about);
                break;
            case 4:
                fragment = new InviteEarnFragment();
                title = getString(R.string.title_invite);
                finish();
                break;
            default:
                break;
        }

        if (fragment != null) {
            gotoFragment(fragment, title);
        }
    }

    private void gotoFragment(Fragment fragment, String title) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.commit();

        // set the toolbar title
        getSupportActionBar().setTitle(title);
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
