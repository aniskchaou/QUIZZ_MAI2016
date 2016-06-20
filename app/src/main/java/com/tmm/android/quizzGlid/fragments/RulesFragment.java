/**
 *
 */
package com.tmm.android.quizzGlid.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.tmm.android.quizzGlid.MainActivity;
import com.tmm.android.quizzGlid.R;


public class RulesFragment extends Fragment implements OnClickListener {

    public RulesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.rules, null);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button backBtn = (Button) view.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        ((MainActivity)getActivity()).goBack();
    }



}
