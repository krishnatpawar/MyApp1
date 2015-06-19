package com.taxi.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taxi.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OlaCaresFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OlaCaresFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OlaCaresFragment extends Fragment {

    public static Fragment newInstance(Context context) {
        OlaCaresFragment f = new OlaCaresFragment();

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_ridecashless, null);
        return root;
    }
}