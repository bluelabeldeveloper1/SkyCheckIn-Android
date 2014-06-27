package com.skycheckin;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class InstagramFeedActivity extends Activity
{
	private TextView tvClose, tvSend;
	private DisplayImageOptions imageOptions;
	private ImageLoader imageLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_instagram);
		init();
	}

	private void init()
	{

		imageLoader = ImageLoader.getInstance();

		imageOptions = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.ic_launcher).showStubImage(R.drawable.ic_launcher).build();
		
		

		tvClose = (TextView) findViewById(R.id.tvClose);

		tvClose.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				finish();
			}
		});

	}

}
