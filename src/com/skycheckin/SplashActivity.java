package com.skycheckin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.bugsense.trace.BugSenseHandler;
import com.custom.MySharedPrefrence;

public class SplashActivity extends Activity
{
	private final int SPLASH_DISPLAY_LENGTH = 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		BugSenseHandler.initAndStartSession(SplashActivity.this, "09bc2ff1");

		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				/* Create an Intent that will start the Menu-Activity. */
				long login = MySharedPrefrence.getUserId(SplashActivity.this);
//				long login = 0;
				
				if (login == 0)
				{
					Intent mainIntent = new Intent(SplashActivity.this, LogInActivity.class);
					startActivity(mainIntent);
					overridePendingTransition(0, 0);
					finish();
				}
				else
				{
					Intent mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
					startActivity(mainIntent);
					overridePendingTransition(0, 0);
					finish();
				}

			}

		}, SPLASH_DISPLAY_LENGTH);

	}

	@Override
	public void onBackPressed()
	{
		// TODO Auto-generated method stub
	}

}
