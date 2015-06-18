package com.taxi.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.taxi.R;
import com.taxi.fragments.AboutFragment;
import com.taxi.fragments.BookMyRideFragment;
import com.taxi.fragments.CallSupportFragment;
import com.taxi.fragments.EmergencyContactfragment;
import com.taxi.fragments.FragmentDrawer;
import com.taxi.fragments.InviteEarnFragment;
import com.taxi.fragments.MyMoneyFragment;
import com.taxi.fragments.MyRidesFragment;
import com.taxi.fragments.RateCardFragment;
import com.taxi.fragments.ReportIssueFragment;

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
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
                fragment = new BookMyRideFragment();
                title = getString(R.string.title_bookmyride);
                break;
            case 1:
                fragment = new MyRidesFragment();
                title = getString(R.string.title_rides);
                break;
            case 2:
                fragment = new MyMoneyFragment();
                title = getString(R.string.title_mymoney);
                break;
            case 3:
                fragment = new InviteEarnFragment();
                title = getString(R.string.title_invite);
                break;
            case 4:
                fragment = new RateCardFragment();
                title = getString(R.string.title_ratecard);
                break;
            case 5:
                fragment = new EmergencyContactfragment();
                title = getString(R.string.title_emeregency);
                break;
            case 6:
                fragment = new ReportIssueFragment();
                title = getString(R.string.title_reportissues);
                break;
            case 7:
                fragment = new CallSupportFragment();
                title = getString(R.string.title_call_support);
                break;
            case 8:
                fragment = new AboutFragment();
                title = getString(R.string.title_about);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }


}
