package com.taxi.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taxi.R;

public class StayInformedFragment extends Fragment{

	public static Fragment newInstance(Context context) {
		StayInformedFragment f = new StayInformedFragment();

		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_stayinformed, null);
		return root;
	}
}
