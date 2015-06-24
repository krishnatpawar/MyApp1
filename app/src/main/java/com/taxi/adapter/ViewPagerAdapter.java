package com.taxi.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.taxi.fragments.OlaCaresFragment;
import com.taxi.fragments.RideCashlessFragment;
import com.taxi.fragments.RideNowFragment;
import com.taxi.fragments.RideSmartFragment;
import com.taxi.fragments.StayInformedFragment;
import com.taxi.fragments.TaxiIconFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public static int totalPage = 7;
    private Context _context;

    public ViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        _context = context;

    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = new Fragment();
        switch (position) {
            case 0:
                f = TaxiIconFragment.newInstance(_context);
                break;
            case 1:
                f = RideNowFragment.newInstance(_context);
                break;
            case 2:
                f = RideSmartFragment.newInstance(_context);
                break;
            case 3:
                f = StayInformedFragment.newInstance(_context);
                break;
            case 4:
                f = RideCashlessFragment.newInstance(_context);
                break;
            case 5:
                f = OlaCaresFragment.newInstance(_context);
                break;
        }
        return f;
    }

    @Override
    public int getCount() {
        return totalPage;
    }

}
