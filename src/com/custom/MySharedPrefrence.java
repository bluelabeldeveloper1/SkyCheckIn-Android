package com.custom;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPrefrence
{
	private static final String PREF_LOGIN_INFO = "user_login_info";

	private static final String USER_ID = "id";
	private static final String SESSION_ID = "session_id";
	private static final String FACEBOOK_LOGIN = "facebook_login";
	private static final String IMAGE_BASE_URL = "image_base_url";

	// @SuppressWarnings("static-access")
	// public static void setUserLoginInfo(Context context, ClassUserDetail
	// userDetail)
	// {
	// SharedPreferences LoginPref = context.getSharedPreferences("LoginPref",
	// context.MODE_PRIVATE);
	// // LoginPref.getString("ID", defValue)
	// SharedPreferences.Editor editor = LoginPref.edit();
	// editor.putLong(ID, userDetail.id);
	//
	// editor.commit();
	// }

	// @SuppressWarnings("static-access")
	// public static ClassUserDetail getUserLoginInfo(Context context)
	// {
	// SharedPreferences LoginPref = context.getSharedPreferences("LoginPref",
	// context.MODE_PRIVATE);
	// ClassUserDetail userDetail = new ClassUserDetail();
	// userDetail.id = LoginPref.getLong(ID, 0);
	// userDetail.email_id = LoginPref.getString(EMAIL_ID, "");
	// userDetail.first_name = LoginPref.getString(FIRST_NAME, "");
	// userDetail.last_name = LoginPref.getString(LAST_NAME, "");
	// userDetail.password = LoginPref.getString(PASSWORD, "");
	// userDetail.country = LoginPref.getString(COUNTRY, "");
	// userDetail.zip = LoginPref.getString(ZIP, "");
	// userDetail.mobile_number = LoginPref.getString(MOBILE_NUMBER, "");
	// userDetail.profile_image = LoginPref.getString(PROFILE_IMAGE, "");
	// userDetail.profile_image_name = LoginPref.getString(PROFILE_IMAGE_NAME,
	// "");
	// return userDetail;
	// }

	@SuppressWarnings("static-access")
	public static long getUserId(Context context)
	{
		SharedPreferences LoginPref = context.getSharedPreferences(PREF_LOGIN_INFO, context.MODE_PRIVATE);
		return LoginPref.getLong(USER_ID, 0);
	}

	@SuppressWarnings("static-access")
	public static void setUserId(Context context, long user_id)
	{
		SharedPreferences LoginPref = context.getSharedPreferences(PREF_LOGIN_INFO, context.MODE_PRIVATE);
		SharedPreferences.Editor editor = LoginPref.edit();
		editor.putLong(USER_ID, user_id);
		editor.commit();
	}

	@SuppressWarnings("static-access")
	public static String getSessionId(Context context)
	{
		SharedPreferences LoginPref = context.getSharedPreferences(PREF_LOGIN_INFO, context.MODE_PRIVATE);
		return LoginPref.getString(SESSION_ID, "");
	}

	@SuppressWarnings("static-access")
	public static void setSessionId(Context context, String session_id)
	{
		SharedPreferences LoginPref = context.getSharedPreferences(PREF_LOGIN_INFO, context.MODE_PRIVATE);
		SharedPreferences.Editor editor = LoginPref.edit();
		editor.putString(SESSION_ID, session_id);
		editor.commit();
	}

	@SuppressWarnings("static-access")
	public static boolean getFacebookLogIn(Context context)
	{
		SharedPreferences LoginPref = context.getSharedPreferences(PREF_LOGIN_INFO, context.MODE_PRIVATE);
		return LoginPref.getBoolean(FACEBOOK_LOGIN, false);
	}

	@SuppressWarnings("static-access")
	public static void setFacebookLogIn(Context context, boolean isFacebookLogIn)
	{
		SharedPreferences LoginPref = context.getSharedPreferences(PREF_LOGIN_INFO, context.MODE_PRIVATE);
		SharedPreferences.Editor editor = LoginPref.edit();
		editor.putBoolean(FACEBOOK_LOGIN, isFacebookLogIn);
		editor.commit();
	}

	@SuppressWarnings("static-access")
	public static String getImageBaseUrl(Context context)
	{
		SharedPreferences LoginPref = context.getSharedPreferences(PREF_LOGIN_INFO, context.MODE_PRIVATE);
		return LoginPref.getString(IMAGE_BASE_URL, "");
	}

	@SuppressWarnings("static-access")
	public static void setImageBaseUrl(Context context, String url)
	{
		SharedPreferences LoginPref = context.getSharedPreferences(PREF_LOGIN_INFO, context.MODE_PRIVATE);
		SharedPreferences.Editor editor = LoginPref.edit();
		editor.putString(IMAGE_BASE_URL, url);
		editor.commit();
	}

}
