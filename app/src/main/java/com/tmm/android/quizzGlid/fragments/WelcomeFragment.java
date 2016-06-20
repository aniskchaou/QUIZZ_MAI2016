package com.tmm.android.quizzGlid.fragments;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.tmm.android.quizzGlid.MainActivity;
import com.tmm.android.quizzGlid.QuizActivity;
import com.tmm.android.quizzGlid.R;
import com.tmm.android.quizzGlid.database.tables.LanguageTable;
import com.tmm.android.quizzGlid.database.tables.QuestionTable;
import com.tmm.android.quizzGlid.quiz.Constants;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class WelcomeFragment extends Fragment implements OnClickListener {

    public WelcomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.welcome, null);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // GAME MENU
        Button playBtn = (Button) view.findViewById(R.id.playBtn);
        playBtn.setOnClickListener(this);
        Button settingsBtn = (Button) view.findViewById(R.id.levelsBtn);
        settingsBtn.setOnClickListener(this);
        Button rulesBtn = (Button) view.findViewById(R.id.rulesBtn);
        rulesBtn.setOnClickListener(this);

        Button langBtn = (Button) view.findViewById(R.id.langBtn);
        langBtn.setOnClickListener(this);

        initDatabase();
    }

    private void initDatabase()
    {
        QuestionTable.initialize(getActivity());
        LanguageTable.initialize(getActivity());
    }


    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.playBtn:

                i = new Intent(getActivity(), QuizActivity.class);
                startActivityForResult(i, Constants.PLAYBUTTON);
                break;

            case R.id.rulesBtn:
                ((MainActivity)getActivity()).goToFragment(new RulesFragment());
                break;
            case R.id.levelsBtn:
                ((MainActivity)getActivity()).goToFragment(new LevelsFragment());
                break;
            case R.id.langBtn:
                ((MainActivity)getActivity()).goToFragment(new LanguagesFragment());
                break;
        }

    }
}