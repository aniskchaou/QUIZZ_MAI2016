package com.tmm.android.quizzGlid.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import com.tmm.android.quizzGlid.MainActivity;
import com.tmm.android.quizzGlid.R;
import com.tmm.android.quizzGlid.quiz.Constants;

import java.util.Locale;

public class LevelsFragment extends Fragment implements OnClickListener {

    public LevelsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.levels, null);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button updateBtn = (Button) view.findViewById(R.id.nextBtn);
        updateBtn.setOnClickListener(this);
        updateButtonWithPreferences();
    }

    private void updateButtonWithPreferences() {
        RadioButton c1 = (RadioButton) getView().findViewById(R.id.easySetting);
        RadioButton c2 = (RadioButton) getView().findViewById(R.id.mediumSetting);
        RadioButton c3 = (RadioButton) getView().findViewById(R.id.hardSetting);

        SharedPreferences settings = getActivity().getSharedPreferences(Constants.SETTINGS, 0);
        int diff = settings.getInt(Constants.DIFFICULTY, Constants.MEDIUM);

        switch (diff) {
            case Constants.EASY:
                c1.toggle();
                break;

            case Constants.MEDIUM:
                c2.toggle();
                break;

            case Constants.HARD:
                c3.toggle();
                break;
        }
    }



    @Override
    public void onClick(View arg0) {

        if (!checkSelected()) {
            return;
        } else {
            SharedPreferences settings = getActivity().getSharedPreferences(Constants.SETTINGS, 0);
            Editor e = settings.edit();
            e.putInt(Constants.DIFFICULTY, getSelectedSetting());
            e.commit();

            ((MainActivity)getActivity()).goBack();
        }

    }


    private boolean checkSelected() {
        RadioButton c1 = (RadioButton) getView().findViewById(R.id.easySetting);
        RadioButton c2 = (RadioButton) getView().findViewById(R.id.mediumSetting);
        RadioButton c3 = (RadioButton) getView().findViewById(R.id.hardSetting);
        return (c1.isChecked() || c2.isChecked() || c3.isChecked());
    }


    private int getSelectedSetting() {
        RadioButton c1 = (RadioButton) getView().findViewById(R.id.easySetting);
        RadioButton c2 = (RadioButton) getView().findViewById(R.id.mediumSetting);
        if (c1.isChecked()) {
            return Constants.EASY;
        }
        if (c2.isChecked()) {
            return Constants.MEDIUM;
        }

        return Constants.HARD;
    }

}
