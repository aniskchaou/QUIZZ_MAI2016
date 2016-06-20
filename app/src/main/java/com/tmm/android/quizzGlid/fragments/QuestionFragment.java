package com.tmm.android.quizzGlid.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tmm.android.quizzGlid.QuizActivity;
import com.tmm.android.quizzGlid.R;
import com.tmm.android.quizzGlid.database.tables.QuestionTable;
import com.tmm.android.quizzGlid.quiz.Question;
import com.tmm.android.quizzGlid.quiz.Quizz;
import com.tmm.android.quizzGlid.util.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_QUESTION = "question";

    private Question question;


    public QuestionFragment() {
        // Required empty public constructor
    }

    public static QuestionFragment newInstance(Question question) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_QUESTION, question);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            question = (Question) getArguments().getSerializable(ARG_QUESTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = null;

        switch (question.getType()) {
            case QuestionTable.TYPE_RADIO :
                view = inflater.inflate(R.layout.fragment_question_radio, container, false);
                break;
            case QuestionTable.TYPE_CHECKBOX:
                view = inflater.inflate(R.layout.fragment_question_checkbox, container, false);

                break;
        }

        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //set the question text from current question
        TextView qText = (TextView) view.findViewById(R.id.question);
        qText.setText(Utility.getLocalized(getActivity(), question.getQuestion()) + "?");

        //set the available options
        List<String> answers = question.getQuestionOptions();

        TextView option1 = (TextView) view.findViewById(R.id.answer1);
        option1.setText(Utility.capitalise(Utility.getLocalized(getActivity(), answers.get(0))));
        option1.setTag(answers.get(0));

        TextView option2 = (TextView) view.findViewById(R.id.answer2);
        option2.setText(Utility.capitalise(Utility.getLocalized(getActivity(), answers.get(1))));
        option2.setTag(answers.get(1));

        TextView option3 = (TextView) view.findViewById(R.id.answer3);
        option3.setText(Utility.capitalise(Utility.getLocalized(getActivity(), answers.get(2))));
        option3.setTag(answers.get(2));

        if (question.getImage() != null) {
            ImageView image = (ImageView) view.findViewById(R.id.image);
            image.setImageBitmap(Utility.byteArrayToBitmap(question.getImage()));
            image.setVisibility(View.VISIBLE);
        }

        Button nextBtn = (Button) view.findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {

        /**
         * validate a checkbox has been selected
         */
        if (!checkAnswer()) {
            Toast.makeText(getActivity(), "Please answer to the question first", Toast.LENGTH_LONG).show();
            return;
        }

        Quizz quizz = ((QuizActivity) getActivity()).getQuizz();

        if (quizz.isGameOver()) {
            ((QuizActivity) getActivity()).showEndQuizz();
        } else {
            ((QuizActivity) getActivity()).loadQuestion(quizz.getNextQuestion());
        }


    }

    private boolean checkAnswer() {

        String answer = getSelectedAnswer();

        if (answer == null) {
            return false;
        } else {

            if (question.getType() == QuestionTable.TYPE_RADIO) {

                if (question.getAnswer().equals(answer)) {
                    ((QuizActivity) getActivity()).getQuizz().incrementRightAnswers();
                } else {
                    ((QuizActivity) getActivity()).getQuizz().incrementWrongAnswers();
                }

            } else if (question.getType() == QuestionTable.TYPE_CHECKBOX) {
                String[] userAnswers = answer.split(",");
                String[] answers = question.getAnswer().split(",");

                if (intersection(Arrays.asList(userAnswers), Arrays.asList(answers)).size() == answers.length) {
                    ((QuizActivity) getActivity()).getQuizz().incrementRightAnswers();
                } else {
                    ((QuizActivity) getActivity()).getQuizz().incrementWrongAnswers();
                }

            }

            return true;
        }
    }

    private String getSelectedAnswer() {

        if (question.getType() == QuestionTable.TYPE_RADIO) {

            RadioGroup rGroup = (RadioGroup) getView().findViewById(R.id.group1);
            RadioButton radioButton = (RadioButton) getView().findViewById(rGroup.getCheckedRadioButtonId());
            if(radioButton == null) {
                return null;
            }
            return radioButton.getTag().toString();

        } else if (question.getType() == QuestionTable.TYPE_CHECKBOX) {
            List<String> answers = new ArrayList<>();

            CheckBox option1 = (CheckBox) getView().findViewById(R.id.answer1);
            CheckBox option2 = (CheckBox) getView().findViewById(R.id.answer2);
            CheckBox option3 = (CheckBox) getView().findViewById(R.id.answer3);

            if(option1.isChecked())
                answers.add(option1.getTag().toString());

            if(option2.isChecked())
                answers.add(option2.getTag().toString());

            if(option3.isChecked())
                answers.add(option3.getTag().toString());


            return convertToString(answers);
        };

        return null;
    }

    public <T> List<T> intersection(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<T>();

        for (T t : list1) {
            if(list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }

    static String convertToString(List<String> numbers) {
        StringBuilder builder = new StringBuilder();
        // Append all Integers in StringBuilder to the StringBuilder.
        for (String number : numbers) {
            builder.append(number);
            builder.append(",");
        }
        // Remove last delimiter with setLength.
        builder.setLength(builder.length() - 1);
        return builder.toString();
    }
}
