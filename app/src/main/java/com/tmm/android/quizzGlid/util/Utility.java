/**
 * 
 */
package com.tmm.android.quizzGlid.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.tmm.android.quizzGlid.database.DBContentProvider;
import com.tmm.android.quizzGlid.database.tables.LanguageTable;
import com.tmm.android.quizzGlid.quiz.Constants;
import com.tmm.android.quizzGlid.quiz.Question;

/**
 * @author rob
 *
 */
public class Utility {
	
	/**
	 * Method to capitalise the first letter of a given string
	 * 
	 * @param s
	 * @return
	 */
	public static String capitalise(String s){
		if (s==null || s.length()==0) return s;
		
		String s1 = s.substring(0, 1).toUpperCase() + s.substring(1);
		return s1;
	}

	/**
	 * Method to get set of answers for a list of questions
	 * @param questions
	 * @return
	 */
	public static String getAnswers(List<Question> questions) {
		int question = 1;
		StringBuffer sb = new StringBuffer();
		
		for (Question q : questions){
			sb.append("Q").append(question).append(") ").append(q.getQuestion()).append("? \n");
			sb.append("Answer: ").append(q.getAnswer()).append("\n\n");
			question ++;
		}
		
		return sb.toString();
	}

	public static byte[] drawableToByteArray(Context context, int drawable) {
		BitmapDrawable bitDw = ((BitmapDrawable) context.getResources().getDrawable(drawable));
		Bitmap bitmap = bitDw.getBitmap();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		byte[] imageInByte = stream.toByteArray();
		return  imageInByte;
	}

	public static Bitmap byteArrayToBitmap(byte[] byteArrayToBeCOnvertedIntoBitMap)
	{
		Bitmap bitmapImage = BitmapFactory.decodeByteArray(
				byteArrayToBeCOnvertedIntoBitMap, 0,
				byteArrayToBeCOnvertedIntoBitMap.length);
		return bitmapImage;
	}

	public static String getLocalized(Context context, String key) {

		Integer lang = getLanguagesSettings(context);

		Cursor cursor = null;

		try{

			cursor = context.getContentResolver().query(
					DBContentProvider.LANGUAGE_CONTENT_URI,
					LanguageTable.PROJECTION_ALL,
					LanguageTable.KEY + " = ? AND " + LanguageTable.LANG + " = ?", new String[]{key, lang.toString() }, null);

			if(cursor.getCount() > 0) {

				cursor.moveToFirst();
				return Utility.capitalise(cursor.getString(cursor.getColumnIndex(LanguageTable.VALUE)));
			}

		} finally {

			cursor.close();
		}

		return key;
	}

	private static int getLanguagesSettings(Context context) {
		SharedPreferences settings = context.getSharedPreferences(Constants.SETTINGS, 0);
		int lang = settings.getInt(Constants.LANGUAGES, Constants.FR);
		return lang;
	}
}
