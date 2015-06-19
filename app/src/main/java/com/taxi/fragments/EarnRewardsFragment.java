package com.taxi.fragments;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taxi.R;

/**
 * Created by koti on 6/18/2015.
 */
public class EarnRewardsFragment extends Fragment {

    public static Fragment newInstance(Context context) {
        EarnRewardsFragment f = new EarnRewardsFragment();

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_earnrewards, null);
        return root;
    }
}
