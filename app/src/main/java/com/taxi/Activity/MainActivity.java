package com.taxi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.taxi.R;
import com.taxi.adapter.ViewPagerAdapter;


public class MainActivity extends FragmentActivity {

    //Constants

    //Views
    private ViewPager myViewPager;
    private ViewPagerAdapter adapter;
    private Button circle1, circle2, circle3, circle4, circle5, circle6, circle7;
    private int minWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
//		instance = this;
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        minWidth = width / 48;
        Log.d("", "screen width: " + width);
        init();
        setTab();

    }

    public void registerUser(View v) {
        startScreen(RegistrationActivity.class);
    }

    public void loginUser(View v) {
        startScreen(LoginActivity.class);
    }

    private void startScreen(Class<?> activity) {
        Intent intent = new Intent(MainActivity.this, activity);
        startActivity(intent);
        finish();
    }

    private void setTab() {
        myViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int position) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                btnAction(position);
            }

        });

    }

    private void btnAction(int action) {
        switch (action) {
            case 0:
                setButton(circle1, "", minWidth + 3, minWidth + 3, R.drawable.selected_circle);
                setButton(circle2, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle3, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle4, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle5, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle6, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle7, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                break;

            case 1:
                setButton(circle2, "", minWidth + 3, minWidth + 3, R.drawable.selected_circle);
                setButton(circle1, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle3, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle4, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle5, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle6, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle7, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                break;

            case 2:
                setButton(circle3, "", minWidth + 3, minWidth + 3, R.drawable.selected_circle);
                setButton(circle1, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle2, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle4, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle5, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle6, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle7, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                break;
            case 3:
                setButton(circle4, "", minWidth + 3, minWidth + 3, R.drawable.selected_circle);
                setButton(circle1, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle2, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle3, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle5, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle6, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle7, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                break;
            case 4:
                setButton(circle5, "", minWidth + 3, minWidth + 3, R.drawable.selected_circle);
                setButton(circle1, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle2, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle3, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle4, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle6, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle7, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                break;

            case 5:
                setButton(circle6, "", minWidth + 3, minWidth + 3, R.drawable.selected_circle);
                setButton(circle1, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle2, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle3, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle4, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle5, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle7, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                break;

            case 6:
                setButton(circle7, "", minWidth + 3, minWidth + 3, R.drawable.selected_circle);
                setButton(circle1, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle2, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle3, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle4, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle5, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                setButton(circle6, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
                break;


        }
    }

    /**
     * initializing views
     */
    private void init() {
        myViewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new ViewPagerAdapter(getApplicationContext(), getSupportFragmentManager());
        myViewPager.setAdapter(adapter);
        myViewPager.setCurrentItem(0);
        initButton();
    }

    private void initButton() {
        circle1 = (Button) findViewById(R.id.btn1);
        circle2 = (Button) findViewById(R.id.btn2);
        circle3 = (Button) findViewById(R.id.btn3);
        circle4 = (Button) findViewById(R.id.btn4);
        circle5 = (Button) findViewById(R.id.btn5);
        circle6 = (Button) findViewById(R.id.btn6);
        circle7 = (Button) findViewById(R.id.btn7);
        setButton(circle1, "", minWidth + 3, minWidth + 3, R.drawable.selected_circle);
        setButton(circle2, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
        setButton(circle3, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
        setButton(circle4, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
        setButton(circle5, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
        setButton(circle6, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
        setButton(circle7, "", minWidth + 2, minWidth + 2, R.drawable.unselected_circle);
    }

    private void setButton(Button btn, String text, int h, int w, int selectedCircle) {
        btn.setWidth(w);
        btn.setHeight(h);
        btn.setBackgroundResource(selectedCircle);

        btn.setText(text);
    }
}