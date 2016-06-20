package com.tmm.android.quizzGlid.quiz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Quizz implements Serializable {

    private List<Question> questions;
    private int currentIndex = 0;
    private int right = 0;
    private int wrong = 0;

    public Quizz() {

    }

    public boolean isGameOver(){
        return currentIndex + 1 >= getQuestions().size();
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Question getQuestion() {
        return questions.get(currentIndex);
    }

    public Question getNextQuestion() {
        return questions.get(++currentIndex);
    }

    public void incrementRightAnswers() {
        right++;
    }

    public void incrementWrongAnswers() {
        wrong++;
    }

    public int getRight() {
        return right;
    }

    public int getNumRounds() {
        return questions.size();
    }
}
