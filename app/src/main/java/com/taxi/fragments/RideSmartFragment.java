package com.taxi.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taxi.R;

public class RideSmartFragment extends Fragment{

	public static Fragment newInstance(Context context) {
		RideSmartFragment f = new RideSmartFragment();

		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_ridesmart, null);
		return root;
	}
}
