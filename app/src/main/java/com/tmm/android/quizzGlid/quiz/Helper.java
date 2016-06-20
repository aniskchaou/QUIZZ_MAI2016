/**
 * 
 */
package com.tmm.android.quizzGlid.quiz;


import com.tmm.android.quizzGlid.R;


public class Helper {


	public static String getResultComment(int numCorrect, int numRounds, int diff)
	{
		String comm="";
		int percentage = calculatePercentage(numCorrect, numRounds);
		switch (diff)
		{
		case Constants.HARD:
			if (percentage > 90){
				comm = "You downloaded the Intersect!";
			}else if (percentage >= 80){
				comm="Nicely Done!";
			}else if (percentage >= 60){
				comm="Not too bad..";
			}else if (percentage >= 40){
				comm="Well, don't give up..";
			}else{
				comm="Next time, stay in the car..";
			}
			break;
			
		default:
			if (percentage > 90){
				comm = "Awesome!";
			}else if (percentage >= 80){
				comm="Nicely Done!";
			}else if (percentage >= 60){
				comm="Not too bad..";
			}else if (percentage >= 40){
				comm="Well, don't give up..";
			}else{
				comm="Next time, stay in the car..";
			}
		}
		
		return comm;
	}
	
	

	public static int getResultImage(int numCorrect, int numRounds, int diff){
		//calculate percentage
		int percentage = calculatePercentage(numCorrect, numRounds);
		
		//work out which image
		if (percentage > 90){
			if (diff == Constants.HARD){
				return R.drawable.hard_winner;
			}else{
				return R.drawable.easy_winner;
			}
		}else if (percentage >= 80){
			return R.drawable.prettygood;
		}else if (percentage >= 60){
			return R.drawable.notbad;
		}else if (percentage >= 40){
			return R.drawable.tryagain;
		}else{
			return R.drawable.loser;
		}
	}
	


	private static int calculatePercentage(int numCorrect, int numRounds) {
		double frac = (double)numCorrect/(double)numRounds;
		int percentage = (int) (frac*100);
		return percentage;
	}
}
