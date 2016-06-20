/**
 *
 */
package com.tmm.android.quizzGlid.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tmm.android.quizzGlid.QuizActivity;
import com.tmm.android.quizzGlid.R;
import com.tmm.android.quizzGlid.quiz.Constants;
import com.tmm.android.quizzGlid.quiz.Helper;
import com.tmm.android.quizzGlid.quiz.Quizz;


public class EndgameFragment extends Fragment implements OnClickListener {

    private static final String ARG_QUIZZ = "quizz";

    private Quizz currentGame;


    public EndgameFragment() {
        // Required empty public constructor
    }

    public static EndgameFragment newInstance(Quizz quizz) {
        EndgameFragment fragment = new EndgameFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_QUIZZ, quizz);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentGame = (Quizz) getArguments().getSerializable(ARG_QUIZZ);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.endgame, null);
        return v;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        String result = "You Got " + currentGame.getRight() + "/" + currentGame.getNumRounds() + ".. ";
        String comment = Helper.getResultComment(currentGame.getRight(), currentGame.getNumRounds(), getDifficultySettings());

        TextView results = (TextView) view.findViewById(R.id.endgameResult);
        results.setText(result + comment);

        int image = Helper.getResultImage(currentGame.getRight(), currentGame.getNumRounds(), getDifficultySettings());
        ImageView resultImage = (ImageView) view.findViewById(R.id.resultPage);
        resultImage.setImageResource(image);

        Button finishBtn = (Button) view.findViewById(R.id.finishBtn);
        finishBtn.setOnClickListener(this);
        Button answerBtn = (Button) view.findViewById(R.id.answerBtn);
        answerBtn.setOnClickListener(this);

    }


    private int getDifficultySettings() {
        SharedPreferences settings = getActivity().getSharedPreferences(Constants.SETTINGS, 0);
        int diff = settings.getInt(Constants.DIFFICULTY, 2);
        return diff;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.finishBtn:
                getActivity().finish();
                break;

            case R.id.answerBtn:
                Intent i = new Intent(getActivity(), QuizActivity.class);
                startActivityForResult(i, Constants.PLAYBUTTON);
                break;
        }
    }

}
