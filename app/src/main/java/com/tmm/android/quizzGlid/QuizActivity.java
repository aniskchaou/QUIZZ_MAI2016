package com.tmm.android.quizzGlid;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.tmm.android.quizzGlid.database.DBContentProvider;
import com.tmm.android.quizzGlid.database.tables.QuestionTable;
import com.tmm.android.quizzGlid.fragments.EndgameFragment;
import com.tmm.android.quizzGlid.fragments.QuestionFragment;
import com.tmm.android.quizzGlid.quiz.Constants;
import com.tmm.android.quizzGlid.quiz.Question;
import com.tmm.android.quizzGlid.quiz.Quizz;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends Activity  implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int QUESTION_TABLE_ID = 1;
    private android.app.LoaderManager mLoaderManager;
    private Quizz quizz = new Quizz();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizz);


        mLoaderManager = getLoaderManager();

        if (savedInstanceState == null) {
            mLoaderManager.initLoader(QUESTION_TABLE_ID, null, this);
        }

        Intent in=new Intent(this,ServiceMusique.class);

        startService(in);

    }

   

    public void loadQuestion(Question question) {
        getFragmentManager().beginTransaction().add(R.id.container, QuestionFragment.newInstance(question)).commit();
    }

    public void showEndQuizz() {
        getFragmentManager().beginTransaction().add(R.id.container, EndgameFragment.newInstance(quizz)).commit();
    }

    boolean doubleBackToExitPressedOnce = false;



    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();

            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to finish quizz", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }



    private int getDifficultySettings() {
        SharedPreferences settings = this.getSharedPreferences(Constants.SETTINGS, 0);
        int diff = settings.getInt(Constants.DIFFICULTY, Constants.MEDIUM);
        return diff;
    }

    /****/

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        CursorLoader cursorLoader = null;

        if (i == QUESTION_TABLE_ID) {
            cursorLoader = new CursorLoader(this, DBContentProvider.QUESTION_CONTENT_URI,
                    QuestionTable.PROJECTION_ALL, null, null, null);
        }

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {


        if (loader.getId() == QUESTION_TABLE_ID) {

            String question,rep,opt1,opt2,opt3;
            int diff,lang,round;
            List<Question> questions = new ArrayList<Question>();
            byte[] image;

            while (!cursor.isAfterLast() && (cursor.moveToNext())) {

                diff = (int) cursor.getLong(cursor.getColumnIndex(QuestionTable.DIFF));
                lang = (int) cursor.getLong(cursor.getColumnIndex(QuestionTable.TYPE));
                image = cursor.getBlob(cursor.getColumnIndex(QuestionTable.IMAGE));

                question = cursor.getString(cursor.getColumnIndex(QuestionTable.Question));
                rep = cursor.getString(cursor.getColumnIndex(QuestionTable.REPONSE));
                opt1 = cursor.getString(cursor.getColumnIndex(QuestionTable.PROP1));
                opt2 = cursor.getString(cursor.getColumnIndex(QuestionTable.PROP2));
                opt3 = cursor.getString(cursor.getColumnIndex(QuestionTable.PROP3));

                if (diff == getDifficultySettings()) {
                    questions.add(new Question(question, rep, opt1, opt2, opt3, diff,lang,image));
                }
            }

            quizz = new Quizz();
            quizz.setQuestions(questions);
        }
        cursor.close();
        mLoaderManager.destroyLoader(QUESTION_TABLE_ID);

        if(quizz.getQuestions().size() == 0) {
            Toast.makeText(this, "No question found, try to choose another level", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Handler handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    loadQuestion(quizz.getQuestion());
                }
            };
            handler.sendEmptyMessage(1);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public Quizz getQuizz() {
        return quizz;
    }


}
