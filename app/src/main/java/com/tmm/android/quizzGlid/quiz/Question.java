package com.tmm.android.quizzGlid.quiz;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question implements Serializable {
	
	private String question;
	private String answer;
	private String option1;
	private String option2;
	private String option3;
	private int diff;
	private int type;
	private byte[] image;

	public Question(String question, String answer, String option1, String option2, String option3, int diff, int type, byte[] image) {
		this.question = question;
		this.answer = answer;
		this.option1 = option1;
		this.option2 = option2;
		this.option3 = option3;
		this.diff = diff;
		this.type=type;
		this.image=image;
	}

	public String getQuestion() {
		return question;
	}

	public String getAnswer() {
		return answer;
	}

	public String getOption1() {
		return option1;
	}

	public String getOption2() {
		return option2;
	}

	public String getOption3() {
		return option3;
	}

	public int getDiff() {
		return diff;
	}

	public int getType() {
		return type;
	}

	public byte[] getImage() {
		return image;
	}

	public List<String> getQuestionOptions(){
		List<String> shuffle = new ArrayList<String>();
		shuffle.add(option1);
		shuffle.add(option2);
		shuffle.add(option3);
		// Collections.shuffle(shuffle);
		return shuffle;
	}
}
