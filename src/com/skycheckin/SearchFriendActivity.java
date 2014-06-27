package com.skycheckin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SearchFriendActivity extends Activity
{
	private TextView tvClose, tvSend;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_friend);
		init();
	}

	private void init()
	{

		tvClose = (TextView) findViewById(R.id.tvClose);
		tvSend = (TextView) findViewById(R.id.tvSend);

		tvClose.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				finish();
			}
		});
		tvSend.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				finish();
			}
		});

	}

}
