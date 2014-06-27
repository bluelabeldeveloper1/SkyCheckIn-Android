package com.tab.checkin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.skycheckin.LogInActivity;
import com.skycheckin.R;
import com.skycheckin.UserProfileActivity;

public class DisplaySeatActivity extends Activity
{
	private TextView btnClose, btnEdit;
	private TextView tvTitle, tvMessage;
	private LinearLayout llSeat, llNoSeat;

	public static Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_seat);
		init();
	}

	public static void FinishActivity()
	{
		activity.finish();
	}

	private void init()
	{
		activity = DisplaySeatActivity.this;
		btnClose = (TextView) findViewById(R.id.btnClose);
		btnEdit = (TextView) findViewById(R.id.btnEdit);

		tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvMessage = (TextView) findViewById(R.id.tvMessage);

		llSeat = (LinearLayout) findViewById(R.id.llSeat);
		llNoSeat = (LinearLayout) findViewById(R.id.llNoSeat);

		btnClose.setOnClickListener(clickListener);
		btnEdit.setOnClickListener(clickListener);

		// llFlight = (LinearLayout) findViewById(R.id.llFlight);
		// llNoFlight = (LinearLayout) findViewById(R.id.llNoFlight);
		//
		// lvFlight = (ListView) findViewById(R.id.lvFlight);
		//
		// flightListAdapter = new
		// FlightListAdapter(SelectSeatTabActivity.this);
		// lvFlight.setAdapter(flightListAdapter);

		tvMessage.setVisibility(View.GONE);

		Bundle bundle = getIntent().getExtras();

		if (bundle != null)
		{
			if (bundle.getBoolean("isCheckInSuccess"))
			{
				tvMessage.setVisibility(View.VISIBLE);
			}
			else
			{
				tvMessage.setVisibility(View.GONE);
			}
		}

		tvMessage.setText(Html.fromHtml("<font color='" + getResources().getColor(R.color.btn_email_signin) + "'>" + getResources().getString(R.string.txt_success_message)
				+ " </font>" + "<font color='" + getResources().getColor(R.color.check_in_success_message) + "'>" + "Air canada 541" + " </font>" + " </font>" + "<font color='"
				+ getResources().getColor(R.color.btn_email_signin) + "'>" + getResources().getString(R.string.txt_success_message_seat) + " </font>" + "<font color='"
				+ getResources().getColor(R.color.check_in_success_message) + "'>" + "4D" + " </font>" + "<font color='" + getResources().getColor(R.color.btn_email_signin) + "'>"
				+ "." + " </font>"));

		setSeatDetail();
		setNoSeatDetail();

	}

	private void setNoSeatDetail()
	{
		llNoSeat.removeAllViews();
		HorizontalScrollView scrollView = new HorizontalScrollView(DisplaySeatActivity.this);
		scrollView.setHorizontalScrollBarEnabled(false);
		LinearLayout llRow = new LinearLayout(DisplaySeatActivity.this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		llRow.setOrientation(LinearLayout.HORIZONTAL);
		llRow.setLayoutParams(params);

		for (int i = 0; i < 2; i++)
		{

			View view = getLayoutInflater().inflate(R.layout.row_seat, null);
			ImageView ivUser = (ImageView) view.findViewById(R.id.ivSeatUser);
			ivUser.setImageResource(R.drawable.profile_pic);
			view.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{

				}
			});

			llRow.addView(view);

		}
		scrollView.addView(llRow);
		llNoSeat.addView(scrollView);
	}

	private void setSeatDetail()
	{

		llSeat.removeAllViews();
		for (int i = 0; i < 3; i++)
		{

			if (i == 2)
			{
				ImageView imageView = new ImageView(DisplaySeatActivity.this);
				imageView.setImageResource(R.drawable.double_divider);

				LinearLayout.LayoutParams ivParam = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				ivParam.gravity = Gravity.CENTER;
				imageView.setLayoutParams(ivParam);
				imageView.setPadding(0, 5, 0, 5);
				llSeat.addView(imageView);
			}
			else
			{

				HorizontalScrollView scrollView = new HorizontalScrollView(DisplaySeatActivity.this);

				scrollView.setHorizontalScrollBarEnabled(false);
				LinearLayout llRow = new LinearLayout(DisplaySeatActivity.this);

				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				llRow.setOrientation(LinearLayout.HORIZONTAL);
				llRow.setLayoutParams(params);

				for (int j = 0; j < 6; j++)
				{
					View view = getLayoutInflater().inflate(R.layout.row_seat, null);

					view.setTag(j);

					// For User
					if (i == 0 && j == 1)
					{
						TextView tvSeat = (TextView) view.findViewById(R.id.tvSeatNo);
						tvSeat.setText("7E");
						tvSeat.setTextColor(getResources().getColor(R.color.white));
						tvSeat.setBackgroundColor(getResources().getColor(R.color.user_seat_bg));
						ImageView ivUser = (ImageView) view.findViewById(R.id.ivSeatUser);
						ivUser.setImageResource(R.drawable.profile_pic);
						view.setOnClickListener(new OnClickListener()
						{
							@Override
							public void onClick(View v)
							{
								Intent intent = new Intent(DisplaySeatActivity.this, UserProfileActivity.class);
								startActivity(intent);
							}
						});
					}
					else if (i == 2 && j == 4 || i == 1 && j == 3 || i == 3 && j == 4)
					{
						TextView tvSeat = (TextView) view.findViewById(R.id.tvSeatNo);
						tvSeat.setText("7E");
						ImageView ivUser = (ImageView) view.findViewById(R.id.ivSeatUser);
						ivUser.setImageResource(R.drawable.profile_pic);
						view.setOnClickListener(new OnClickListener()
						{
							@Override
							public void onClick(View v)
							{
								Intent intent = new Intent(DisplaySeatActivity.this, OtherUserProfileActivity.class);
								startActivity(intent);
							}
						});
					}

					llRow.addView(view);
				}
				scrollView.addView(llRow);

				llSeat.addView(scrollView);
			}

			// create the layout params that will be used to define how your
			// button will be displayed
			// LinearLayout.LayoutParams params = new
			// LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
			// LayoutParams.WRAP_CONTENT);
		}
	}

	OnClickListener clickListener = new OnClickListener()
	{

		@Override
		public void onClick(View v)
		{
			if (v == btnClose)
			{
//				llNoSeat.removeAllViews();
//				llSeat.removeAllViews();
				finish();
			}
			else if (v == btnEdit)
			{
//				llNoSeat.removeAllViews();
//				llSeat.removeAllViews();
				if (FindFlightActivity.activity != null)
					FindFlightActivity.FinishActivity();
				if (SelectFlightActivity.activity != null)
					SelectFlightActivity.FinishActivity();
				if (SelectSeatActivity.activity != null)
					SelectSeatActivity.FinishActivity();
				finish();
			}
		}
	};

}
